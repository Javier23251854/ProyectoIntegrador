<%-- 
    Document   : bandejaIncidentes
    Created on : 20 may. 2026, 11:18:38 p. m.
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bandeja de Incidentes - Autoridades</title>

        <style>
            * {
                box-sizing: border-box;
                font-family: 'Segoe UI', system-ui, sans-serif;
                margin: 0;
                padding: 0;
            }
            /* Tema Oscuro Institucional */
            body {
                background-color: #1e2226;
                color: #e9ecef;
                padding: 30px;
            }

            .cabecera {
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-bottom: 2px solid #dc3545;
                padding-bottom: 15px;
                margin-bottom: 30px;
            }
            .titulo h1 {
                color: #dc3545;
                font-size: 24px;
                margin-bottom: 5px;
            }
            .titulo p {
                color: #adb5bd;
                font-size: 14px;
            }

            .btn-volver {
                background: transparent;
                color: #adb5bd;
                border: 1px solid #6c757d;
                padding: 8px 15px;
                border-radius: 6px;
                text-decoration: none;
                font-weight: bold;
                transition: 0.2s;
            }
            .btn-volver:hover {
                background: #6c757d;
                color: white;
            }

            /* Contenedor de la Tabla */
            .panel-tabla {
                background: #2b3035;
                border-radius: 12px;
                padding: 20px;
                box-shadow: 0 10px 30px rgba(0,0,0,0.5);
                border: 1px solid #495057;
                overflow-x: auto;
            }

            .tabla-admin {
                width: 100%;
                border-collapse: collapse;
                min-width: 800px;
            }
            .tabla-admin th {
                background-color: #212529;
                color: #adb5bd;
                padding: 15px;
                text-align: left;
                font-weight: 600;
                border-bottom: 2px solid #495057;
            }
            .tabla-admin td {
                padding: 15px;
                border-bottom: 1px solid #343a40;
                vertical-align: middle;
                color: #f8f9fa;
            }
            .tabla-admin tr:hover {
                background-color: #343a40;
            }

            /* Controles de Estado */
            .select-estado {
                background: #212529;
                color: white;
                border: 1px solid #495057;
                padding: 8px;
                border-radius: 6px;
                outline: none;
                font-weight: bold;
                cursor: pointer;
            }
            .select-estado:focus {
                border-color: #0d6efd;
            }

            .btn-actualizar {
                background: #0d6efd;
                color: white;
                border: none;
                padding: 8px 15px;
                border-radius: 6px;
                cursor: pointer;
                font-weight: bold;
                transition: 0.2s;
                margin-left: 10px;
            }
            .btn-actualizar:hover {
                background: #0b5ed7;
            }

            /* Badges visuales de prioridad */
            .badge {
                padding: 4px 8px;
                border-radius: 6px;
                font-size: 12px;
                font-weight: bold;
            }
            .p-alta {
                background: rgba(220, 53, 69, 0.2);
                color: #ff6b6b;
                border: 1px solid #dc3545;
            }
            .p-media {
                background: rgba(255, 193, 7, 0.2);
                color: #ffc107;
                border: 1px solid #ffc107;
            }
            .p-baja {
                background: rgba(25, 135, 84, 0.2);
                color: #20c997;
                border: 1px solid #198754;
            }
        </style>
    </head>
    <body>

        <div class="cabecera">
            <div class="titulo">
                <h1>Bandeja de Incidentes Activos</h1>
                <p>Gestión y seguimiento de reportes ciudadanos.</p>
            </div>
            <a href="dashboardAdmin.jsp" class="btn-volver">← Volver al Panel</a>
        </div>
        
       <div style="display:flex; gap:10px; margin-bottom:20px; flex-wrap:wrap;">
    <select id="filtroEstado" class="select-estado">
        <option value="">Todos los estados</option>
        <option value="Pendiente">Pendiente</option>
        <option value="En proceso">En proceso</option>
        <option value="Atendido">Atendido</option>
    </select>

    <input type="text" id="filtroCategoria" class="select-estado" placeholder="Buscar categoría">

    <button class="btn-actualizar" onclick="cargarBandeja()">Filtrar</button>
    <button class="btn-actualizar" onclick="limpiarFiltros()">Limpiar</button>
        </div> 
        
        <div class="panel-tabla">
            <table class="tabla-admin">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Categoría</th>
                        <th>Ubicación</th>
                        <th>Prioridad</th>
                        <th>Estado Actual</th>
                        <th>Acción</th>
                    </tr>
                </thead>
                <tbody id="cuerpoTablaAdmin">
                    <tr><td colspan="6" style="text-align: center; color: #adb5bd;">Cargando reportes del servidor...</td></tr>
                </tbody>
            </table>
        </div>

        <script>
            // Validación de Seguridad
            const ctx = '<%=request.getContextPath()%>';

document.addEventListener("DOMContentLoaded", function () {
    cargarBandeja();
});

function cargarBandeja() {
    const estado = document.getElementById('filtroEstado').value;
    const categoria = document.getElementById('filtroCategoria').value.trim();

    const params = new URLSearchParams();
    if (estado) params.append('estado', estado);
    if (categoria) params.append('categoria', categoria);

    fetch(ctx + '/api/incidencias?' + params.toString())
        .then(res => res.json())
        .then(datos => {
            const tbody = document.getElementById('cuerpoTablaAdmin');
            tbody.innerHTML = '';

            if (datos.length === 0) {
                tbody.innerHTML = '<tr><td colspan="6" style="text-align: center; color: #adb5bd;">No hay incidentes con ese filtro.</td></tr>';
                return;
            }

            datos.forEach(inc => {
                let idVal = inc.idIncidencia || inc.id || inc.id_incidencia;
                let catVal = inc.categoria || "Desconocida";
                let ubiVal = inc.ubicacion || "Sin ubicación";
                let prioVal = inc.prioridad || "Media";
                let estVal = inc.estado || "Pendiente";
                let clasePrio = prioVal === 'Alta' ? 'p-alta' : (prioVal === 'Media' ? 'p-media' : 'p-baja');

                let selPendiente = estVal === 'Pendiente' ? 'selected' : '';
                let selProceso = estVal === 'En proceso' ? 'selected' : '';
                let selAtendido = estVal === 'Atendido' ? 'selected' : '';

                let fila = "<tr>" +
                    "<td>#" + idVal + "</td>" +
                    "<td style='font-weight:bold;'>" + catVal + "</td>" +
                    "<td>" + ubiVal + "</td>" +
                    "<td><span class='badge " + clasePrio + "'>" + prioVal + "</span></td>" +
                    "<td>" +
                    "<select class='select-estado' id='estado_" + idVal + "'>" +
                    "<option value='Pendiente' " + selPendiente + ">Pendiente</option>" +
                    "<option value='En proceso' " + selProceso + ">En proceso</option>" +
                    "<option value='Atendido' " + selAtendido + ">Atendido</option>" +
                    "</select>" +
                    "</td>" +
                    "<td><button class='btn-actualizar' onclick='actualizarEstado(" + idVal + ")'>Guardar</button></td>" +
                    "</tr>";

                tbody.innerHTML += fila;
            });
        });
}

            // 2. ENVIAR EL NUEVO ESTADO A JAVA
            function actualizarEstado(idIncidencia) {
                let nuevoEstado = document.getElementById('estado_' + idIncidencia).value;

                // Creamos un payload indicando explícitamente que la acción es actualizar
                let payload = "accion=actualizarEstado&idIncidencia=" + idIncidencia + "&nuevoEstado=" + encodeURIComponent(nuevoEstado);

                fetch('/ProyectoIntegrador/api/incidencias', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: payload
                })
                        .then(res => res.json())
                        .then(data => {
                            if (data.status === 'success') {
                                alert('Estado actualizado correctamente a: ' + nuevoEstado);
                                cargarBandeja(); // Recargamos para refrescar la vista
                            } else {
                                alert('Error BD: No se pudo actualizar el estado.');
                            }
                        })
                        .catch(err => alert('Error interno al intentar comunicarse con el servidor.'));
            }
            
            function limpiarFiltros() {
            document.getElementById('filtroEstado').value = '';
            document.getElementById('filtroCategoria').value = '';
            cargarBandeja();
            }
        </script>
    </body>
</html>