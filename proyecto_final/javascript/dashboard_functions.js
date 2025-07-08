document.addEventListener('DOMContentLoaded', () => {
    const user = JSON.parse(localStorage.getItem('stockgain_user'));
    const token = localStorage.getItem('stockgain_token');
    if (!user || !token) {
        window.location.href = 'acceso.html';
        return;
    }
    const welcomeMessage = document.getElementById('welcome-message');
    if (welcomeMessage) {
        welcomeMessage.textContent = `Hola, ${user.nombre}`;
    }
    const ALPHA_VANTAGE_API_KEY = "VT4TPAIRFO16L7Y8"; 
    const API_BASE_URL = 'http://localhost:8080/api';
    const PREDEFINED_STOCKS = [
        { ticker: "EC", name: "Ecopetrol S.A.", esg: "C" },
        { ticker: "CIB", name: "Bancolombia S.A.", esg: "A" },
        { ticker: "AAPL", name: "Apple Inc.", esg: "A" },
        { ticker: "MSFT", name: "Microsoft Corp.", esg: "A" },
        { ticker: "GOOGL", name: "Alphabet Inc. (Google)", esg: "B" },
        { ticker: "AMZN", name: "Amazon.com, Inc.", esg: "C" },
        { ticker: "NVDA", name: "NVIDIA Corp.", esg: "B" },
        { ticker: "TSLA", name: "Tesla, Inc.", esg: "C" },
    ];
    let userPortfolio = [];
    let portfolioValueChart, portfolioEsgChart;
    const dashboardView = document.getElementById('dashboard-view');
    const settingsView = document.getElementById('settings-view');
    const navDashboard = document.getElementById('nav-dashboard');
    const navSettings = document.getElementById('nav-settings');
    const assetsTableBody = document.getElementById('assets-table-body');
    const totalPortfolioValueEl = document.getElementById('total-portfolio-value');
    const assetTickerSelect = document.getElementById('asset-ticker-select');
    const addAssetForm = document.getElementById('add-asset-form');
    const currentAssetPriceEl = document.getElementById('current-asset-price');
    const logoutButton = document.getElementById('logout-button');
    const showDashboard = () => {
        dashboardView.style.display = 'block';
        settingsView.style.display = 'none';
        navDashboard.classList.add('bg-white/10', 'text-white');
        navSettings.classList.remove('bg-white/10', 'text-white');
    };
    const showSettings = () => {
        dashboardView.style.display = 'none';
        settingsView.style.display = 'block';
        navDashboard.classList.remove('bg-white/10', 'text-white');
        navSettings.classList.add('bg-white/10', 'text-white');
    };
    navDashboard.addEventListener('click', (e) => { e.preventDefault(); showDashboard(); });
    navSettings.addEventListener('click', (e) => { e.preventDefault(); showSettings(); });
    logoutButton.addEventListener('click', () => {
        localStorage.removeItem('stockgain_user');
        localStorage.removeItem('stockgain_token');
        window.location.href = 'acceso.html';
    });
    async function fetchStockGainAPI(endpoint, options = {}) {
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        };
        try {
            const response = await fetch(`${API_BASE_URL}${endpoint}`, {
                ...options,
                headers: { ...headers, ...options.headers },
            });
            if (!response.ok) {
                console.error(`Error en API StockGain: ${response.status} ${response.statusText}`);
                if (response.status === 401 || response.status === 403) {
                    alert("Tu sesión ha expirado. Por favor, inicia sesión de nuevo.");
                    logoutButton.click();
                }
                const errorText = await response.text();
                throw new Error(errorText || 'Error del servidor');
            }
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.indexOf("application/json") !== -1) {
                return response.json();
            } else {
                return response.text();
            }
        } catch (error) {
            console.error(`Fallo de conexión con la API de StockGain:`, error);
            throw error;
        }
    }
    async function fetchStockPriceApiData(url) {
        if (!ALPHA_VANTAGE_API_KEY) {
            console.error("Error Crítico: La clave de API de Alpha Vantage no está configurada en dashboard_functions.js.");
            alert("Error de configuración: La clave de API para los precios de mercado no está definida. Por favor, obtén una en alphavantage.co y añádela al código.");
            return null;
        }
        try {
            const response = await fetch(url);
            if (!response.ok) {
                console.error(`Error de Red para ${url}: ${response.status} ${response.statusText}`);
                return null;
            }
            const data = await response.json();
            if (data["Note"]) {
                console.warn("Nota de la API de Alpha Vantage:", data["Note"]);
                alert("Se ha alcanzado el límite de peticiones a la API de precios. Por favor, espera un minuto antes de volver a intentarlo.");
                return null;
            }
            if (data["Error Message"]) {
                console.error("Error de la API de Alpha Vantage:", data["Error Message"]);
                return null;
            }
            return data;
        } catch (error) {
            console.error(`Fallo total al hacer fetch a la API de precios para la URL ${url}:`, error);
            return null;
        }
    }
    const renderAssetsTable = () => {
        assetsTableBody.innerHTML = '';
        let totalValue = 0;
        if (userPortfolio.length === 0) {
            assetsTableBody.innerHTML = `<tr><td colspan="7" class="text-center p-6 text-gray-400">Aún no tienes activos en tu portafolio. ¡Añade el primero!</td></tr>`;
        } else {
            userPortfolio.forEach(asset => {
                asset.valorTotal = asset.cantidad * (asset.precioActual || 0);
                totalValue += asset.valorTotal;
                assetsTableBody.innerHTML += `
                    <tr class="border-b border-gray-700 table-row">
                        <td class="p-3 font-bold">${asset.ticker}</td>
                        <td class="p-3">${asset.cantidad.toLocaleString('es-CO', {minimumFractionDigits: 2, maximumFractionDigits: 8})}</td>
                        <td class="p-3">$${asset.precioCompraPromedio.toLocaleString('es-CO', {minimumFractionDigits: 2, maximumFractionDigits: 2})}</td>
                        <td class="p-3 font-semibold text-white">$${(asset.precioActual || 0).toLocaleString('es-CO', {minimumFractionDigits: 2, maximumFractionDigits: 2})}</td>
                        <td class="p-3 font-bold text-highlight">$${asset.valorTotal.toLocaleString('es-CO', {minimumFractionDigits: 2, maximumFractionDigits: 2})}</td>
                        <td class="p-3 text-center"><span class="esg-score-badge esg-${asset.esgCalificacionGeneral}">${asset.esgCalificacionGeneral || 'N/A'}</span></td>
                        <td class="p-3 text-center">
                            <button class="sell-btn bg-red-500 hover:bg-red-600 text-white font-bold py-1 px-3 rounded-lg text-xs" data-id="${asset.id_posicion}">Vender</button>
                        </td>
                    </tr>
                `;
            });
        }
        totalPortfolioValueEl.textContent = `$${totalValue.toLocaleString('es-CO', {minimumFractionDigits: 2, maximumFractionDigits: 2})}`;
        return totalValue;
    };
    const populateAssetDropdown = () => {
        PREDEFINED_STOCKS.forEach(stock => {
            const option = document.createElement('option');
            option.value = stock.ticker;
            option.textContent = `${stock.name} (${stock.ticker})`;
            assetTickerSelect.appendChild(option);
        });
    };
    const updatePriceForSelectedAsset = async () => {
        const selectedTicker = assetTickerSelect.value;
        if (!selectedTicker) return;
        currentAssetPriceEl.textContent = 'Cargando...';
        const url = `https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${selectedTicker}&apikey=${ALPHA_VANTAGE_API_KEY}`;
        const result = await fetchStockPriceApiData(url);
        if (result && result['Global Quote'] && result['Global Quote']['05. price']) {
            const price = parseFloat(result['Global Quote']['05. price']);
            currentAssetPriceEl.textContent = `$${price.toLocaleString('es-CO', {minimumFractionDigits: 2, maximumFractionDigits: 2})}`;
        } else {
            currentAssetPriceEl.textContent = 'No disponible';
        }
    };
    assetTickerSelect.addEventListener('change', updatePriceForSelectedAsset);
    const initializeCharts = () => {
        document.querySelector('thead tr').innerHTML += '<th class="p-3 text-center">Acciones</th>';
        const portfolioValueOptions = {
            chart: { type: 'area', height: 300, toolbar: { show: false }, foreColor: '#EEEEEE' },
            series: [{ name: 'Valor', data: [] }],
            xaxis: { type: 'datetime', labels: { style: { colors: '#EEEEEE' } } },
            yaxis: { labels: { formatter: (val) => `$${(val/1000).toFixed(0)}K`, style: { colors: '#EEEEEE' } } },
            dataLabels: { enabled: false }, stroke: { curve: 'smooth', width: 3 },
            colors: ['#00ADB5'], grid: { borderColor: '#393E46' },
            fill: { type: 'gradient', gradient: { opacityFrom: 0.6, opacityTo: 0.1, colorStops: [{ offset: 0, color: "#00ADB5", opacity: 0.5 }, { offset: 100, color: "#222831", opacity: 0.1 }] } },
            tooltip: { theme: 'dark', x: { format: 'dd MMM yy' } },
            noData: { text: 'No hay datos para mostrar el gráfico', align: 'center', verticalAlign: 'middle', style: { color: '#EEEEEE', fontSize: '14px' } }
        };
        portfolioValueChart = new ApexCharts(document.querySelector("#portfolio-value-chart"), portfolioValueOptions);
        portfolioValueChart.render();
        const portfolioEsgOptions = {
            chart: { type: 'donut', height: 220, foreColor: '#EEEEEE' },
            series: [], labels: ['Calificación A', 'Calificación B', 'Calificación C'],
            colors: ['#00ADB5', '#EEEEEE', '#FF5252'], dataLabels: { enabled: false },
            legend: { show: false },
            plotOptions: { pie: { donut: { size: '75%', labels: { show: true, total: { show: true, label: 'Activos', color: '#EEEEEE' } } } } },
            noData: { text: 'Sin datos ESG', style: { color: '#EEEEEE' } }
        };
        portfolioEsgChart = new ApexCharts(document.querySelector("#portfolio-esg-chart"), portfolioEsgOptions);
        portfolioEsgChart.render();
    };
    const updatePortfolioValueChart = (currentTotalValue) => {
        if (!portfolioValueChart || currentTotalValue <= 0) {
            portfolioValueChart.updateSeries([{ data: [] }]);
            return;
        }
        const seriesData = [];
        let previousValue = currentTotalValue;
        for (let i = 0; i < 7; i++) {
            const date = new Date();
            date.setDate(date.getDate() - i);
            const fluctuation = (Math.random() - 0.5) * 0.06;
            const dayValue = previousValue * (1 - fluctuation);
            seriesData.push([date.getTime(), dayValue]);
            previousValue = dayValue;
        }
        seriesData.reverse();
        portfolioValueChart.updateSeries([{ name: 'Valor', data: seriesData }]);
    };
    const updatePortfolioEsgChart = () => {
        if (userPortfolio.length === 0) {
            portfolioEsgChart.updateSeries([0, 0, 0]);
            return;
        }
        const esgCounts = userPortfolio.reduce((acc, stock) => {
            const calificacion = stock.esgCalificacionGeneral?.toUpperCase();
            if (calificacion && acc.hasOwnProperty(calificacion)) {
                acc[calificacion]++;
            }
            return acc;
        }, { 'A': 0, 'B': 0, 'C': 0 });
        if (portfolioEsgChart) {
            portfolioEsgChart.updateSeries([esgCounts['A'], esgCounts['B'], esgCounts['C']]);
        }
    };
    const updateAllAssetPrices = async () => {
        if (userPortfolio.length === 0) {
            renderAssetsTable(); 
            updatePortfolioValueChart(0);
            updatePortfolioEsgChart();
            return;
        }
        for (const asset of userPortfolio) {
            const url = `https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${asset.ticker}&apikey=${ALPHA_VANTAGE_API_KEY}`;
            const result = await fetchStockPriceApiData(url);
            if (result && result['Global Quote'] && result['Global Quote']['05. price']) {
                asset.precioActual = parseFloat(result['Global Quote']['05. price']);
            } else {
                console.warn(`No se pudo obtener el precio para ${asset.ticker}.`);
                asset.precioActual = asset.precioActual || 0; 
            }
            const totalValue = renderAssetsTable();
            updatePortfolioValueChart(totalValue);
        }
        updatePortfolioEsgChart();
    };
    addAssetForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const ticker = document.getElementById('asset-ticker-select').value;
        const quantity = parseFloat(document.getElementById('asset-quantity').value);
        const priceText = document.getElementById('current-asset-price').textContent;
        if (priceText === 'No disponible' || priceText === 'Cargando...') {
            alert("No se puede añadir el activo porque el precio de mercado no está disponible.");
            return;
        }
        const price = parseFloat(priceText.replace('$', '').replace(/\./g, '').replace(',', '.'));
        if (ticker && quantity > 0 && price > 0) {
            const newPositionRequest = {
                userId: user.id_usuario,
                ticker: ticker,
                cantidad: quantity,
                precioCompra: price
            };
            try {
                const updatedPosition = await fetchStockGainAPI('/portfolio/add', {
                    method: 'POST',
                    body: JSON.stringify(newPositionRequest)
                });
                if (updatedPosition && updatedPosition.id_posicion) {
                    const index = userPortfolio.findIndex(p => p.id_posicion === updatedPosition.id_posicion);
                    updatedPosition.precioActual = price;
                    if (index > -1) {
                        userPortfolio[index] = updatedPosition;
                    } else {
                        userPortfolio.push(updatedPosition);
                    }
                    await updateAllAssetPrices();
                    addAssetForm.reset();
                    await updatePriceForSelectedAsset();
                    alert(`¡Operación completada para ${updatedPosition.nombreEmpresa}!`);
                }
            } catch (error) {
                 alert(`Error en la operación: ${error.message}`);
            }
        } else {
            alert("Por favor, asegúrate de que la cantidad sea válida y que el precio de mercado esté disponible.");
        }
    });
    assetsTableBody.addEventListener('click', async (e) => {
        if (e.target && e.target.classList.contains('sell-btn')) {
            const button = e.target;
            const posicionId = button.dataset.id;
            const assetRow = button.closest('tr');
            const assetTicker = assetRow.querySelector('td:first-child').textContent;
            if (confirm(`¿Estás seguro de que quieres vender todas tus posiciones de ${assetTicker}?`)) {
                try {
                    button.disabled = true;
                    button.textContent = '...';
                    const responseText = await fetchStockGainAPI(`/portfolio/sell/${posicionId}`, {
                        method: 'DELETE'
                    });
                    alert(responseText);
                    userPortfolio = userPortfolio.filter(p => p.id_posicion != posicionId);
                    await updateAllAssetPrices();
                } catch (error) {
                    alert(`Error al vender el activo: ${error.message}`);
                    button.disabled = false;
                    button.textContent = 'Vender';
                }
            }
        }
    });
    const updateUserForm = document.getElementById('update-user-form');
    const updatePasswordForm = document.getElementById('update-password-form');
    const userNameInput = document.getElementById('user-name');
    const userEmailInput = document.getElementById('user-email');
    if (user) {
        userNameInput.value = user.nombre;
        userEmailInput.value = user.email;
    }
    updateUserForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const button = updateUserForm.querySelector('button');
        button.disabled = true;
        button.textContent = 'Guardando...';
        const updatedData = {
            nombre: userNameInput.value,
            email: userEmailInput.value
        };
        try {
            const responseText = await fetchStockGainAPI(`/user/${user.id_usuario}`, {
                method: 'PUT',
                body: JSON.stringify(updatedData)
            });
            alert(responseText);
            user.nombre = updatedData.nombre;
            user.email = updatedData.email;
            localStorage.setItem('stockgain_user', JSON.stringify(user));
            welcomeMessage.textContent = `Hola, ${user.nombre}`;
        } catch (error) {
            alert(`Error al actualizar los datos: ${error.message}`);
        } finally {
            button.disabled = false;
            button.textContent = 'Guardar Cambios';
        }
    });
    updatePasswordForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const button = updatePasswordForm.querySelector('button');
        const newPasswordInput = document.getElementById('new-password');
        const newPassword = newPasswordInput.value;
        if (newPassword.length < 6) {
            alert('La nueva contraseña debe tener al menos 6 caracteres.');
            return;
        }
        button.disabled = true;
        button.textContent = 'Actualizando...';
        try {
            const responseText = await fetchStockGainAPI(`/user/${user.id_usuario}/password`, {
                method: 'PUT',
                body: JSON.stringify({ password: newPassword })
            });
            alert(responseText);
            updatePasswordForm.reset();
        } catch (error) {
            alert(`Error al actualizar la contraseña: ${error.message}`);
        } finally {
            button.disabled = false;
            button.textContent = 'Actualizar Contraseña';
        }
    });
    async function initializeDashboard() {
        console.log("Inicializando el dashboard...");
        try {
            const portfolioData = await fetchStockGainAPI(`/portfolio/${user.id_usuario}`);
            if (portfolioData) {
                userPortfolio = portfolioData.map(asset => ({
                    ...asset,
                    precioActual: 0,
                    valorTotal: 0
                }));
            }
        } catch (error) {
            console.error("No se pudo cargar el portafolio inicial:", error);
            alert("Hubo un error al cargar tu portafolio. Intenta refrescar la página.");
        }
        populateAssetDropdown();
        initializeCharts();
        await updateAllAssetPrices();
        await updatePriceForSelectedAsset();
    }
    initializeDashboard();
});