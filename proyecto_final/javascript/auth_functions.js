document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');
    const showRegisterLink = document.getElementById('show-register-link');
    const showLoginLink = document.getElementById('show-login-link');
    const API_BASE_URL = 'http://localhost:8080/api/auth';
    showRegisterLink.addEventListener('click', (e) => {
        e.preventDefault();
        loginForm.style.display = 'none';
        registerForm.style.display = 'block';
    });
    showLoginLink.addEventListener('click', (e) => {
        e.preventDefault();
        registerForm.style.display = 'none';
        loginForm.style.display = 'block';
    });
    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const button = registerForm.querySelector('button');
        const errorMessage = document.getElementById('register-error');
        button.disabled = true;
        button.textContent = 'Creando cuenta...';
        errorMessage.style.display = 'none';
        const formData = new FormData(registerForm);
        const data = Object.fromEntries(formData.entries());
        try {
            const response = await fetch(`${API_BASE_URL}/registro`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data),
            });
            if (response.ok) {
                alert('¡Cuenta creada exitosamente! Por favor, inicia sesión para continuar.');
                showLoginLink.click();
                registerForm.reset();
            } else {
                const errorText = await response.text();
                errorMessage.textContent = errorText || 'Ocurrió un error en el registro.';
                errorMessage.style.display = 'block';
            }
        } catch (error) {
            errorMessage.textContent = 'No se pudo conectar con el servidor. Inténtalo más tarde.';
            errorMessage.style.display = 'block';
        } finally {
            button.disabled = false;
            button.textContent = 'Crear Cuenta';
        }
    });
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const button = loginForm.querySelector('button');
        const errorMessage = document.getElementById('login-error');
        button.disabled = true;
        button.textContent = 'Ingresando...';
        errorMessage.style.display = 'none';
        const formData = new FormData(loginForm);
        const data = Object.fromEntries(formData.entries());
        try {
            const response = await fetch(`${API_BASE_URL}/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data),
            });
            const responseData = await response.json();
            if (response.ok) {
                localStorage.setItem('stockgain_token', responseData.token);
                localStorage.setItem('stockgain_user', JSON.stringify(responseData.usuario));
                window.location.href = 'dashboard.html';
            } else {
                errorMessage.textContent = responseData.message || 'Credenciales incorrectas.';
                errorMessage.style.display = 'block';
            }
        } catch (error) {
            errorMessage.textContent = 'No se pudo conectar con el servidor.';
            errorMessage.style.display = 'block';
        } finally {
            button.disabled = false;
            button.textContent = 'Ingresar';
        }
    });
});