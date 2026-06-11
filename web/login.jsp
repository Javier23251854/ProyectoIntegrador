<%-- 
    Document   : login
    Created on : 14 may. 2026, 8:24:35 a. m.
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Santa Clara Conecta - Iniciar Sesión</title>
        <style>
            * {
                box-sizing: border-box;
                font-family: 'Segoe UI', system-ui, -apple-system, sans-serif;
                margin: 0;
                padding: 0;
            }

            body {
                background: linear-gradient(135deg, #1e52fd 0%, #0b3cb3 100%);
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                padding: 20px;
            }

            /* Contenedor de Marca Superior */
            .marca-contenedor {
                text-align: center;
                color: white;
                margin-bottom: 24px;
            }

            .logo-app {
                background: white;
                width: 60px;
                height: 60px;
                border-radius: 16px;
                display: flex;
                justify-content: center;
                align-items: center;
                margin: 0 auto 16px auto;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }

            .logo-app svg {
                width: 32px;
                height: 32px;
                fill: #1e52fd;
            }

            .marca-contenedor h1 {
                font-size: 24px;
                font-weight: 700;
                margin-bottom: 6px;
                letter-spacing: 0.5px;
            }

            .marca-contenedor p {
                font-size: 14px;
                color: rgba(255, 255, 255, 0.8);
            }

            /* Tarjeta Blanca del Formulario */
            .tarjeta-login {
                background: white;
                width: 100%;
                max-width: 440px;
                border-radius: 24px;
                padding: 32px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
            }

            /* Selector de pestañas (Tabs) */
            .contenedor-tabs {
                display: flex;
                background: #f1f3f7;
                padding: 4px;
                border-radius: 12px;
                margin-bottom: 28px;
            }

            .tab-btn {
                flex: 1;
                border: none;
                background: transparent;
                padding: 10px;
                font-size: 14px;
                font-weight: 600;
                color: #6c757d;
                cursor: pointer;
                border-radius: 8px;
                transition: 0.2s;
            }

            .tab-btn.activo {
                background: white;
                color: #1e52fd;
                box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
            }

            /* Formulario y Campos */
            .grupo-campo {
                margin-bottom: 20px;
                display: flex;
                flex-direction: column;
                gap: 8px;
            }

            .grupo-campo label {
                font-size: 13px;
                font-weight: 600;
                color: #333333;
            }

            .contenedor-input {
                position: relative;
                display: flex;
                align-items: center;
            }

            .contenedor-input svg {
                position: absolute;
                left: 14px;
                width: 18px;
                height: 18px;
                fill: #999999;
            }

            .input-login {
                width: 100%;
                padding: 14px 14px 14px 42px;
                border: 1px solid #dcdfe6;
                border-radius: 12px;
                font-size: 14px;
                outline: none;
                transition: 0.2s;
                color: #333;
            }

            .input-login:focus {
                border-color: #1e52fd;
                box-shadow: 0 0 0 3px rgba(30, 82, 253, 0.15);
            }

            .olvido-contrasena {
                display: block;
                text-align: right;
                font-size: 12px;
                color: #1e52fd;
                text-decoration: none;
                font-weight: 600;
                margin-top: -8px;
                margin-bottom: 24px;
            }

            .olvido-contrasena:hover {
                text-decoration: underline;
            }

            /* Botón Principal */
            .btn-ingresar {
                width: 100%;
                background: #1e52fd;
                color: white;
                border: none;
                padding: 14px;
                border-radius: 12px;
                font-size: 15px;
                font-weight: 600;
                cursor: pointer;
                transition: 0.2s;
                box-shadow: 0 4px 12px rgba(30, 82, 253, 0.2);
            }

            .btn-ingresar:hover {
                background: #1646dd;
            }

            /* Divisor "O continúa con" */
            .divisor-social {
                display: flex;
                align-items: center;
                text-align: center;
                color: #909399;
                font-size: 12px;
                margin: 24px 0;
            }

            .divisor-social::before, .divisor-social::after {
                content: '';
                flex: 1;
                border-bottom: 1px solid #e4e7ed;
            }

            .divisor-social:not(:empty)::before {
                margin-right: .5em;
            }
            .divisor-social:not(:empty)::after {
                margin-left: .5em;
            }

            /* Botones de Redes Sociales */
            .grid-social {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 12px;
            }

            .btn-social {
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 8px;
                background: white;
                border: 1px solid #dcdfe6;
                padding: 12px;
                border-radius: 12px;
                font-size: 14px;
                font-weight: 600;
                color: #444;
                cursor: pointer;
                transition: 0.2s;
            }

            .btn-social:hover {
                background: #f5f7fa;
                border-color: #c0c4cc;
            }

            .btn-social img {
                width: 18px;
                height: 18px;
            }

            /* Enlaces Inferiores de Navegación */
            .enlaces-inferiores {
                margin-top: 24px;
                display: flex;
                flex-direction: column;
                gap: 12px;
                text-align: center;
            }

            .enlace-footer {
                color: rgba(255, 255, 255, 0.85);
                font-size: 13px;
                text-decoration: none;
                font-weight: 600;
                transition: 0.2s;
            }

            .enlace-footer:hover {
                color: white;
                text-decoration: underline;
            }
        </style>
    </head>
    <body>

        <div class="marca-contenedor">
            <div class="logo-app">
                <svg viewBox="0 0 24 24">
                <path d="M12 7V3H2v18h20V7H12zm-6 12H4v-2h2v2zm0-4H4v-2h2v2zm0-4H4V9h2v2zm0-4H4V5h2v2zm6 12h-2v-2h2v2zm0-4h-2v-2h2v2zm0-4h-2V9h2v2zm0-4h-2V5h2v2zm8 12h-2v-2h2v2zm0-4h-2v-2h2v2zm0-4h-2V9h2v2z"/>
                </svg>
            </div>
            <h1>Santa Clara Conecta</h1>
            <p>Conectando ciudadanos con su ciudad</p>
        </div>

        <div class="tarjeta-login">

            <div class="contenedor-tabs">
                <button type="button" class="tab-btn activo" onclick="location.href = 'login.jsp'">Iniciar Sesión</button>
                <button type="button" class="tab-btn" onclick="location.href = 'registro.jsp'">Registrarse</button>
            </div>

            <form id="formLogin">
                <div class="grupo-campo">
                    <label>Correo Electrónico</label>
                    <div class="contenedor-input">
                        <svg viewBox="0 0 24 24"><path d="M20 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 4l-8 5-8-5V6l8 5 8-5v2z"/></svg>
                        <input type="email" id="txtCorreo" class="input-login" placeholder="correo@ejemplo.com" required>
                    </div>
                </div>

                <div class="grupo-campo">
                    <label>Contraseña</label>
                    <div class="contenedor-input">
                        <svg viewBox="0 0 24 24"><path d="M18 8h-1V6c0-2.76-2.24-5-5-5S7 3.24 7 6v2H6c-1.1 0-2 .9-2 2v10c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V10c0-1.1-.9-2-2-2zm-6 9c-1.1 0-2-.9-2-2s.9-2 2-2 2 .9 2 2-.9 2-2 2zm3.1-9H8.9V6c0-1.71 1.39-3.1 3.1-3.1 1.71 0 3.1 1.39 3.1 3.1v2z"/></svg>
                        <input type="password" id="txtPassword" class="input-login" placeholder="••••••••" required>
                    </div>
                </div>

                <a href="#" class="olvido-contrasena" onclick="alert('Funcionalidad de recuperación en desarrollo para la siguiente entrega.')">¿Olvidaste tu contraseña?</a>

                <button type="submit" class="btn-ingresar">Iniciar Sesión</button>
            </form>

            <div class="divisor-social">O continúa con</div>

            <div class="grid-social">
                <button type="button" class="btn-social" onclick="alert('Autenticación con Google simulada para fines académicos.')">
                    <img src="https://fonts.gstatic.com/s/i/productlogos/googleg/v6/web-24dp/logo_googleg_color_24dp.png" alt="Google">
                    Google
                </button>
                <button type="button" class="btn-social" onclick="alert('Autenticación con Facebook simulada para fines académicos.')">
                    <img src="https://upload.wikimedia.org/wikipedia/commons/b/b8/2021_Facebook_icon.svg" alt="Facebook">
                    Facebook
                </button>
            </div>
        </div>

        <div class="enlaces-inferiores">
            <a href="index.jsp" class="enlace-footer">Ver Portal Ciudadano →</a>
            <a href="loginAdmin.jsp" class="enlace-footer">Acceso Institucional →</a>
        </div>

        <script>
            document.getElementById('formLogin').addEventListener('submit', function (e) {
                e.preventDefault();
                const correo = document.getElementById('txtCorreo').value;
                const pass = document.getElementById('txtPassword').value;

                // ¡CAMBIO CLAVE AQUÍ! Usamos "clave=" en lugar de "password="
                let payload = "correo=" + encodeURIComponent(correo) + "&clave=" + encodeURIComponent(pass);

                fetch('/ProyectoIntegrador/api/login', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: payload
                })
                        // ... (el resto del código sigue igual)
                        .then(res => res.json())
                        .then(data => {
                            if (data.status === 'success') {
                                // Guardamos la sesión en el navegador como lo tenías planeado
                                localStorage.setItem('usuarioLogueado', JSON.stringify(data.usuario));
                                window.location.href = 'index.jsp';
                            } else {
                                alert('Error: ' + data.message);
                            }
                        })
                        .catch(err => alert('Error de comunicación con el controlador.'));
            });
        </script>
    </body>
</html>