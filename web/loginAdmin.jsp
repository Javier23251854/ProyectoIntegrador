<%-- 
    Document   : loginAdmin
    Created on : 19 may. 2026, 9:56:07 a. m.
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Portal Administrativo - Santa Clara</title>
        <style>
            * {
                box-sizing: border-box;
                font-family: 'Segoe UI', system-ui, sans-serif;
                margin: 0;
                padding: 0;
            }
            body {
                background-color: #1a1d20;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                padding: 20px;
            }
            .tarjeta-admin {
                background: white;
                width: 100%;
                max-width: 440px;
                border-radius: 24px;
                padding: 40px;
                box-shadow: 0 10px 30px rgba(0,0,0,0.3);
                text-align: center;
            }
            .titulo {
                color: #dc3545;
                font-size: 26px;
                font-weight: 700;
                margin-bottom: 8px;
            }
            .subtitulo {
                color: #6c757d;
                font-size: 14px;
                margin-bottom: 30px;
            }
            .grupo-campo {
                margin-bottom: 20px;
                text-align: left;
                display: flex;
                flex-direction: column;
                gap: 8px;
            }
            .grupo-campo label {
                font-size: 13px;
                font-weight: 600;
                color: #333;
            }
            .input-admin {
                width: 100%;
                padding: 14px;
                border: 1px solid #dcdfe6;
                border-radius: 12px;
                font-size: 14px;
                outline: none;
                transition: 0.2s;
            }
            .input-admin:focus {
                border-color: #dc3545;
                box-shadow: 0 0 0 3px rgba(220, 53, 69, 0.15);
            }
            .btn-ingresar {
                width: 100%;
                background: #dc3545;
                color: white;
                border: none;
                padding: 14px;
                border-radius: 12px;
                font-size: 15px;
                font-weight: 600;
                cursor: pointer;
                transition: 0.2s;
                margin-top: 10px;
                box-shadow: 0 4px 12px rgba(220, 53, 69, 0.2);
            }
            .btn-ingresar:hover {
                background: #bb2d3b;
            }
            .alerta-error {
                background-color: #f8d7da;
                color: #842029;
                padding: 12px;
                border-radius: 12px;
                font-size: 14px;
                margin-top: 20px;
                border: 1px solid #f5c2c7;
                display: none;
                text-align: center;
            }
            .enlace-volver {
                display: inline-block;
                margin-top: 25px;
                color: #0d6efd;
                text-decoration: none;
                font-size: 14px;
                font-weight: 600;
            }
            .enlace-volver:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>

        <div class="tarjeta-admin">
            <h1 class="titulo">Portal Administrativo</h1>
            <p class="subtitulo">Solo personal autorizado - Santa Clara</p>

            <form id="formLoginAdmin">
                <div class="grupo-campo">
                    <label>Correo Institucional</label>
                    <input type="email" id="txtCorreoAdmin" class="input-admin" placeholder="admin@municipio.gob.pe" required>
                </div>

                <div class="grupo-campo">
                    <label>Contraseña</label>
                    <input type="password" id="txtPasswordAdmin" class="input-admin" placeholder="••••••••" required>
                </div>

                <button type="submit" class="btn-ingresar">Ingresar al Panel de Control</button>
            </form>

            <div class="alerta-error" id="mensajeError">
                Error de credenciales o conexión con el servidor interno.
            </div>

            <a href="login.jsp" class="enlace-volver">← Volver al portal ciudadano</a>
        </div>

        <script>
            document.getElementById('formLoginAdmin').addEventListener('submit', function (e) {
                e.preventDefault();
                document.getElementById('mensajeError').style.display = 'none';

                const correo = document.getElementById('txtCorreoAdmin').value;
                const pass = document.getElementById('txtPasswordAdmin').value;

                // ¡CAMBIO CLAVE AQUÍ! Usamos "clave="
                let payload = "correo=" + encodeURIComponent(correo) + "&clave=" + encodeURIComponent(pass);

                // ¡RUTA CORREGIDA! Debe decir api/loginAdmin
                fetch('/ProyectoIntegrador/api/loginAdmin', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: payload
                })
                        // ... (el resto del código sigue igual)
                        .then(res => res.json())
                        .then(data => {
                            if (data.status === 'success') {
                                // Guardamos la sesión en el localStorage
                                localStorage.setItem('adminLogueado', JSON.stringify(data.usuario || data.admin));
                                window.location.href = 'dashboardAdmin.jsp';
                            } else {
                                document.getElementById('mensajeError').innerText = data.message;
                                document.getElementById('mensajeError').style.display = 'block';
                            }
                        })
                        .catch(err => {
                            console.error(err);
                            document.getElementById('mensajeError').innerText = "Error de comunicación con el servidor interno.";
                            document.getElementById('mensajeError').style.display = 'block';
                        });
            });
        </script>
    </body>
</html>