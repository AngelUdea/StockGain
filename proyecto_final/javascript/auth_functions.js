/**
 * AUTH_FUNCTIONS.JS
 * 
 * Archivo JavaScript que maneja toda la funcionalidad de autenticación de la aplicación StockGain.
 * Incluye registro de usuarios, inicio de sesión, validación de formularios y manejo de errores.
 * 
 * Funcionalidades principales:
 * - Registro de nuevos usuarios
 * - Inicio de sesión con validación
 * - Alternancia entre formularios de login y registro
 * - Almacenamiento seguro de tokens JWT en localStorage
 * - Manejo de errores de red y del servidor
 * - Redirección automática al dashboard después del login exitoso
 * 
 * Dependencias:
 * - Backend API REST en http://localhost:8080/api/auth
 * - DOM elements: login-form, register-form, show-register-link, show-login-link
 * - LocalStorage para persistencia de sesión
 * 
 * @author StockGain Team
 * @version 1.0
 */

// Event listener principal que se ejecuta cuando el DOM está completamente cargado
document.addEventListener('DOMContentLoaded', () => {
    // Obtención de referencias a elementos del DOM necesarios para la autenticación
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');
    const showRegisterLink = document.getElementById('show-register-link');
    const showLoginLink = document.getElementById('show-login-link');
    
    // Configuración de la URL base del API de autenticación
    const API_BASE_URL = 'http://localhost:8080/api/auth';

    /**
     * Event listener para mostrar el formulario de registro
     * Oculta el formulario de login y muestra el de registro
     */
    showRegisterLink.addEventListener('click', (e) => {
        e.preventDefault();
        loginForm.style.display = 'none';
        registerForm.style.display = 'block';
    });

    /**
     * Event listener para mostrar el formulario de login
     * Oculta el formulario de registro y muestra el de login
     */
    showLoginLink.addEventListener('click', (e) => {
        e.preventDefault();
        registerForm.style.display = 'none';
        loginForm.style.display = 'block';
    });

    /**
     * Manejo del formulario de registro
     * Procesa el registro de nuevos usuarios enviando datos al backend
     * Incluye validación, manejo de errores y feedback visual
     */
    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        // Referencias a elementos del formulario de registro
        const button = registerForm.querySelector('button');
        const errorMessage = document.getElementById('register-error');
        
        // Estado de carga: deshabilitando botón y cambiando texto
        button.disabled = true;
        button.textContent = 'Creando cuenta...';
        errorMessage.style.display = 'none';

        // Extracción de datos del formulario
        const formData = new FormData(registerForm);
        const data = Object.fromEntries(formData.entries());

        try {
            // Envío de petición POST al endpoint de registro
            const response = await fetch(`${API_BASE_URL}/registro`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data),
            });

            // Verificación de respuesta exitosa
            if (response.ok) {
                alert('¡Cuenta creada exitosamente! Por favor, inicia sesión para continuar.');
                showLoginLink.click(); // Cambio automático al formulario de login
                registerForm.reset(); // Limpieza del formulario
            } else {
                // Manejo de errores del servidor
                const errorText = await response.text();
                errorMessage.textContent = errorText || 'Ocurrió un error en el registro.';
                errorMessage.style.display = 'block';
            }
        } catch (error) {
            // Manejo de errores de red
            errorMessage.textContent = 'No se pudo conectar con el servidor. Inténtalo más tarde.';
            errorMessage.style.display = 'block';
        } finally {
            // Restauración del estado original del botón
            button.disabled = false;
            button.textContent = 'Crear Cuenta';
        }
    });

    /**
     * Manejo del formulario de login
     * Procesa el inicio de sesión, autentica usuarios y gestiona la sesión
     * Almacena el token JWT y los datos del usuario en localStorage
     */
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        // Referencias a elementos del formulario de login
        const button = loginForm.querySelector('button');
        const errorMessage = document.getElementById('login-error');
        
        // Estado de carga: deshabilitando botón y cambiando texto
        button.disabled = true;
        button.textContent = 'Ingresando...';
        errorMessage.style.display = 'none';

        // Extracción de datos del formulario
        const formData = new FormData(loginForm);
        const data = Object.fromEntries(formData.entries());

        try {
            // Envío de petición POST al endpoint de login
            const response = await fetch(`${API_BASE_URL}/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data),
            });

            const responseData = await response.json();

            // Verificación de login exitoso
            if (response.ok) {
                // Almacenamiento seguro del token JWT y datos del usuario
                localStorage.setItem('stockgain_token', responseData.token);
                localStorage.setItem('stockgain_user', JSON.stringify(responseData.usuario));
                
                // Redirección al dashboard principal
                window.location.href = 'dashboard.html';
            } else {
                // Manejo de credenciales incorrectas
                errorMessage.textContent = responseData.message || 'Credenciales incorrectas.';
                errorMessage.style.display = 'block';
            }
        } catch (error) {
            // Manejo de errores de conexión
            errorMessage.textContent = 'No se pudo conectar con el servidor.';
            errorMessage.style.display = 'block';
        } finally {
            // Restauración del estado original del botón
            button.disabled = false;
            button.textContent = 'Ingresar';
        }
    });
});