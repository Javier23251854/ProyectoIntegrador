    <%-- 
    Document   : dashboardAdmin
    Created on : 20 may. 2026, 10:15:44 p. m.
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Centro de Control - Autoridades</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #1e2226;
                color: white;
                margin: 0;
                padding: 40px;
            }
            .cabecera {
                border-bottom: 2px solid #dc3545;
                padding-bottom: 20px;
                margin-bottom: 30px;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .grid-menu {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                gap: 20px;
            }
            .tarjeta-modulo {
                background: #2b3035;
                padding: 30px;
                border-radius: 10px;
                text-align: center;
                text-decoration: none;
                color: white;
                border: 1px solid #495057;
                transition: 0.3s;
            }
            .tarjeta-modulo:hover {
                transform: translateY(-5px);
                border-color: #dc3545;
                box-shadow: 0 10px 20px rgba(220,53,69,0.2);
            }
            .icono {
                font-size: 40px;
                margin-bottom: 15px;
            }
            .btn-salir {
                background: transparent;
                color: #dc3545;
                border: 1px solid #dc3545;
                padding: 8px 15px;
                border-radius: 5px;
                cursor: pointer;
                text-decoration: none;
                font-weight: bold;
            }
            .btn-salir:hover {
                background: #dc3545;
                color: white;
            }
        </style>
    </head>
    <body>

        <div class="cabecera">
            <div>
                <h1 style="margin:0; color:#dc3545;">Panel de Control Administrativo</h1>
                <p style="margin:5px 0 0 0; color:#adb5bd;">Municipalidad de Ate - Zona Santa Clara</p>
            </div>
            <div>
                <span id="nombreAdminUI" style="margin-right: 20px; color: #adb5bd; font-weight: bold;">Cargando...</span>
                <button onclick="cerrarSesionAdmin()" class="btn-salir">Cerrar Sesión</button>
            </div>
        </div>

        <div class="grid-menu">
            <a href="gestionZonas.jsp" class="tarjeta-modulo">
                <div class="icono">🗺️</div>
                <h3>Gestión de Zonas</h3>
                <p style="color:#adb5bd; font-size:14px;">Añadir o editar sectores y urbanizaciones operativas.</p>
            </a>

            <a href="bandejaIncidentes.jsp" class="tarjeta-modulo">
                <div class="icono">🚨</div>
                <h3>Bandeja de Incidentes</h3>
                <p style="color:#adb5bd; font-size:14px;">Revisar reportes de vecinos y cambiar estados.</p>
            </a>
        </div>

        <script>
            // Verificación de seguridad
            const adminActivo = JSON.parse(localStorage.getItem('adminLogueado'));
            if (!adminActivo) {
                window.location.href = 'loginAdmin.jsp';
            } else {
                // Mostramos el nombre del administrador validado
                document.getElementById('nombreAdminUI').innerText = "Bienvenido, " + adminActivo.usuario;
            }

            function cerrarSesionAdmin() {
                localStorage.removeItem('adminLogueado');
                window.location.href = 'loginAdmin.jsp';
            }
        </script>
    </body>
</html>