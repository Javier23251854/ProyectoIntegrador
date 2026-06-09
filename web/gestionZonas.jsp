<%-- 
    Document   : gestionZonas
    Created on : 19 may. 2026, 11:15:30 a. m.
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Zonas - Santa Clara</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #f4f7f9;
                margin: 0;
                padding: 40px;
                color: #333;
            }
            .tarjeta {
                background: white;
                border-radius: 10px;
                padding: 30px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.05);
                margin-bottom: 30px;
            }
            .tarjeta-azul {
                border-top: 4px solid #0d6efd;
            }
            .tarjeta-gris {
                border-top: 4px solid #6c757d;
            }
            h2 {
                margin-top: 0;
                margin-bottom: 20px;
            }
            .h2-azul {
                color: #0d6efd;
            }

            .formulario-zona {
                display: flex;
                gap: 15px;
                align-items: center;
            }
            .input-estandar {
                flex: 1;
                padding: 12px;
                border: 1px solid #ced4da;
                border-radius: 6px;
                font-size: 14px;
                outline: none;
            }
            .input-estandar:focus {
                border-color: #0d6efd;
            }
            .btn-guardar {
                background-color: #198754;
                color: white;
                border: none;
                padding: 12px 24px;
                border-radius: 6px;
                font-weight: bold;
                cursor: pointer;
                transition: 0.2s;
                white-space: nowrap;
            }
            .btn-guardar:hover {
                background-color: #157347;
            }

            .tabla-estandar {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
            }
            .tabla-estandar th {
                background-color: #f8f9fa;
                padding: 15px;
                text-align: left;
                font-weight: 600;
                color: #495057;
                border-bottom: 2px solid #dee2e6;
            }
            .tabla-estandar td {
                padding: 15px;
                border-bottom: 1px solid #e9ecef;
                vertical-align: middle;
            }
            .badge {
                background-color: #e2e3e5;
                color: #383d41;
                padding: 6px 12px;
                border-radius: 20px;
                font-size: 12px;
                font-weight: bold;
            }
            .btn-editar {
                background-color: #ffc107;
                color: #000;
                border: none;
                padding: 6px 15px;
                border-radius: 4px;
                font-weight: bold;
                cursor: pointer;
                display: inline-flex;
                align-items: center;
                gap: 5px;
            }
            .btn-editar:hover {
                background-color: #ffca2c;
            }

            .btn-volver {
                display: inline-block;
                margin-bottom: 20px;
                color: #6c757d;
                text-decoration: none;
                font-weight: bold;
            }
            .btn-volver:hover {
                color: #343a40;
                text-decoration: underline;
            }
        </style>
    </head>
    <body>

        <a href="dashboardAdmin.jsp" class="btn-volver">← Volver al Panel de Control</a>

        <div class="tarjeta tarjeta-azul">
            <h2 class="h2-azul">Añadir Nuevo Sector a Santa Clara</h2>
            <form id="formNuevaZona" class="formulario-zona">
                <input type="text" id="nombreZona" class="input-estandar" placeholder="Ej. Urbanización Los Pinos" required>
                <input type="text" id="tipoSector" class="input-estandar" placeholder="Ej. Residencial / Comercial" required>
                <button type="submit" class="btn-guardar">+ Guardar Zona</button>
            </form>
        </div>

        <div class="tarjeta tarjeta-gris">
            <h2 style="color: #495057;">Zonas Registradas en la Base de Datos</h2>
            <table class="tabla-estandar">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre de la Zona/Sector</th>
                        <th>Tipo de Área</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody id="tablaZonasCuerpo">
                    <tr><td colspan="4" style="text-align: center;">Cargando zonas...</td></tr>
                </tbody>
            </table>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                // Validar que un administrador real esté viendo esta página
                const adminActivo = JSON.parse(localStorage.getItem('adminLogueado'));
                if (!adminActivo) {
                    window.location.href = 'loginAdmin.jsp';
                }

                cargarTablaZonas();
            });

            // 1. FUNCIÓN PARA CARGAR LA TABLA (A PRUEBA DE JSP)
            function cargarTablaZonas() {
                fetch('/ProyectoIntegrador/api/zonas')
                        .then(res => res.json())
                        .then(datos => {
                            const tbody = document.getElementById('tablaZonasCuerpo');
                            tbody.innerHTML = '';

                            if (datos.length === 0) {
                                tbody.innerHTML = '<tr><td colspan="4" style="text-align: center;">No hay zonas registradas.</td></tr>';
                                return;
                            }

                            datos.forEach(z => {
                                // Atrapamos las variables sin importar cómo las envíe Java
                                let idVal = z.idZona || z.id_zona || z.id || "N/A";
                                let nombreVal = z.nombreZona || z.nombre_zona || z.nombre || "Desconocido";
                                let tipoVal = z.tipoSector || z.tipo_sector || z.tipo || "Sin definir";

                                // EL TRUCO: Usamos el signo '+' para concatenar, evadiendo el borrado de Tomcat
                                let fila = "<tr>" +
                                        "<td>#" + idVal + "</td>" +
                                        "<td>" + nombreVal + "</td>" +
                                        "<td><span class='badge'>" + tipoVal + "</span></td>" +
                                        "<td><button class='btn-editar' onclick='alert(\"Módulo de edición en desarrollo para la entrega final\")'>✏️ Editar</button></td>" +
                                        "</tr>";

                                tbody.innerHTML += fila;
                            });
                        })
                        .catch(err => {
                            console.error("Error:", err);
                            document.getElementById('tablaZonasCuerpo').innerHTML = '<tr><td colspan="4" style="text-align: center; color: red;">Error al cargar los datos desde el servidor.</td></tr>';
                        });
            }

            // 2. FUNCIÓN PARA ENVIAR UNA NUEVA ZONA AL CONTROLADOR
            document.getElementById('formNuevaZona').addEventListener('submit', function (e) {
                e.preventDefault();

                const nombre = document.getElementById('nombreZona').value;
                const tipo = document.getElementById('tipoSector').value;

                let payload = "nombreZona=" + encodeURIComponent(nombre) + "&tipoSector=" + encodeURIComponent(tipo);

                fetch('/ProyectoIntegrador/api/zonas', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: payload
                })
                        .then(res => res.json())
                        .then(datos => {
                            if (datos.status === 'success') {
                                alert("¡Sector registrado correctamente en la base de datos!");
                                this.reset();
                                cargarTablaZonas(); // Actualiza la tabla automáticamente
                            } else {
                                alert("Error BD: " + datos.message);
                            }
                        })
                        .catch(err => alert("Error interno al intentar guardar."));
            });
        </script>
    </body>
</html>