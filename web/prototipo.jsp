<%-- 
    Document   : prototipo
    Created on : 20 may. 2026, 11:00:16 p. m.
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Santa Clara Conecta - Dashboard</title>
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
        <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>

        <style>
            * {
                box-sizing: border-box;
                font-family: 'Segoe UI', system-ui, sans-serif;
                margin: 0;
                padding: 0;
            }
            body {
                background-color: #f4f7fa;
                color: #333;
            }

            /* Navbar */
            .navbar {
                background: white;
                padding: 15px 40px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            }
            .logo {
                font-size: 20px;
                font-weight: bold;
                color: #1e52fd;
                display: flex;
                align-items: center;
                gap: 10px;
            }
            .btn-reportar {
                background: #1e52fd;
                color: white;
                padding: 10px 24px;
                border-radius: 20px;
                text-decoration: none;
                font-weight: 600;
                border: none;
                cursor: pointer;
                transition: 0.2s;
            }
            .btn-reportar:hover {
                background: #1646dd;
                box-shadow: 0 4px 12px rgba(30,82,253,0.3);
            }

            /* Barra de Estadísticas Azul */
            .stats-banner {
                background: #1e52fd;
                color: white;
                padding: 30px 40px;
                display: flex;
                gap: 20px;
                justify-content: center;
                flex-wrap: wrap;
            }
            .stat-card {
                background: rgba(255,255,255,0.1);
                border-radius: 12px;
                padding: 20px;
                flex: 1;
                min-width: 200px;
                max-width: 250px;
                border: 1px solid rgba(255,255,255,0.2);
            }
            .stat-valor {
                font-size: 32px;
                font-weight: bold;
                margin-bottom: 5px;
            }
            .stat-label {
                font-size: 14px;
                opacity: 0.9;
            }

            /* Contenedor Principal (Grid) */
            .contenedor-main {
                padding: 40px;
                display: grid;
                grid-template-columns: 2.5fr 1fr;
                gap: 30px;
                max-width: 1400px;
                margin: 0 auto;
            }

            /* Panel del Mapa */
            .panel-mapa {
                background: white;
                border-radius: 16px;
                padding: 20px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.03);
            }
            .panel-header {
                display: flex;
                justify-content: space-between;
                margin-bottom: 20px;
            }
            .panel-header h2 {
                font-size: 18px;
                color: #2c3e50;
            }
            #mapaSantaClara {
                width: 100%;
                height: 500px;
                border-radius: 12px;
                z-index: 1;
            }

            /* Sidebar Derecha (Categorías y Recientes) */
            .sidebar {
                display: flex;
                flex-direction: column;
                gap: 30px;
            }
            .tarjeta-side {
                background: white;
                border-radius: 16px;
                padding: 25px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.03);
            }
            .tarjeta-side h3 {
                font-size: 16px;
                color: #2c3e50;
                margin-bottom: 20px;
            }

            .lista-categorias {
                list-style: none;
            }
            .lista-categorias li {
                display: flex;
                justify-content: space-between;
                padding: 12px 0;
                border-bottom: 1px solid #eee;
                font-size: 14px;
                color: #555;
            }
            .lista-categorias li:last-child {
                border-bottom: none;
            }
            .badge-numero {
                background: #f0f4ff;
                color: #1e52fd;
                padding: 4px 10px;
                border-radius: 12px;
                font-weight: bold;
                font-size: 12px;
            }

        </style>
    </head>
    <body>

        <nav class="navbar">
            <div class="logo">
                📍 Santa Clara Conecta <span style="font-size: 12px; font-weight: normal; color: #888;">Portal Ciudadano</span>
            </div>
            <div>
                <button class="btn-reportar" onclick="alert('¡Aquí abriremos tu formulario de Nuevo Reporte!')">+ Nuevo Reporte</button>
            </div>
        </nav>

        <div class="stats-banner">
            <div class="stat-card">
                <div class="stat-valor">5</div>
                <div class="stat-label">Reportes Totales</div>
            </div>
            <div class="stat-card">
                <div class="stat-valor">1</div>
                <div class="stat-label">Casos Atendidos</div>
            </div>
            <div class="stat-card">
                <div class="stat-valor">2</div>
                <div class="stat-label">En Proceso</div>
            </div>
            <div class="stat-card">
                <div class="stat-valor">5</div>
                <div class="stat-label">Zonas Activas</div>
            </div>
        </div>

        <div class="contenedor-main">

            <div class="panel-mapa">
                <div class="panel-header">
                    <h2>Mapa de Incidencias en Vivo</h2>
                </div>
                <div id="mapaSantaClara"></div>
            </div>

            <div class="sidebar">
                <div class="tarjeta-side">
                    <h3>Categorías</h3>
                    <ul class="lista-categorias">
                        <li style="color: #1e52fd; font-weight: bold;">Todos <span class="badge-numero">5</span></li>
                        <li>🚨 Seguridad <span class="badge-numero">1</span></li>
                        <li>💡 Alumbrado <span class="badge-numero">1</span></li>
                        <li>🕳️ Vialidad (Baches) <span class="badge-numero">1</span></li>
                        <li>🗑️ Residuos <span class="badge-numero">1</span></li>
                        <li>💧 Fuga de Agua <span class="badge-numero">1</span></li>
                    </ul>
                </div>

                <div class="tarjeta-side">
                    <h3>Reportes Recientes</h3>
                    <div style="background: #f8f9fa; padding: 15px; border-radius: 8px; font-size: 13px; margin-bottom: 10px; border-left: 4px solid #ffc107;">
                        <strong>Fuga de agua</strong><br>
                        <span style="color: #777;">Av. Nicolás de Ayllón km 10</span>
                    </div>
                    <div style="background: #f8f9fa; padding: 15px; border-radius: 8px; font-size: 13px; border-left: 4px solid #198754;">
                        <strong>Alumbrado</strong><br>
                        <span style="color: #777;">Calle Los Laureles cdra 2</span>
                    </div>
                </div>
            </div>
        </div>

        <script>
            // Coordenadas exactas de Santa Clara, Ate, Lima
            var mapa = L.map('mapaSantaClara').setView([-12.016, -76.883], 15);

            // Capa visual de las calles (Estilo estándar de OpenStreetMap)
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '© OpenStreetMap contributors',
                maxZoom: 19
            }).addTo(mapa);

            // Ejemplo: Añadir un marcador simulando un reporte
            L.marker([-12.015, -76.885]).addTo(mapa)
                    .bindPopup("<b>Fuga de agua</b><br>Av. Nicolás de Ayllón.")
                    .openPopup();

            L.marker([-12.018, -76.880]).addTo(mapa)
                    .bindPopup("<b>Acumulación de Basura</b><br>Mercado de Santa Clara.");
        </script>

    </body>
</html>