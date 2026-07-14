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

            .btn-limpiar {
                background: #FF5D00;
                color: white;
                border: none;
                padding: 8px 15px;
                border-radius: 6px;
                cursor: pointer;
                font-weight: bold;
                transition: 0.2s;
                margin-left: 10px;
            }
            .btn-limpiar:hover {
                background: #E05100;
            }

            .btn-pdf {
                background: #198754;
                color: white;
                border: none;
                padding: 8px 15px;
                border-radius: 6px;
                cursor: pointer;
                font-weight: bold;
                transition: 0.2s;
                margin-left: 10px;
            }
            .btn-pdf:hover {
                background: #157347
            }

            .btn-pdf-secundario {
                background: #6c757d;
            }
            .btn-pdf-secundario:hover {
                background: #5c636a;
            }

            .btn-pdf-terciario {
                background: #dc3545;
            }
            .btn-pdf-terciario:hover {
                background: #bb2d3b;
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
            <input type="text" id="filtroId" class="select-estado" placeholder="Buscar ID"> 

            <select id="filtroEstado" class="select-estado">
                <option value="">Todos los estados</option>
                <option value="Pendiente">Pendiente</option>
                <option value="En proceso">En proceso</option>
                <option value="Atendido">Atendido</option>
            </select>

            <input type="text" id="filtroCategoria" class="select-estado" placeholder="Buscar categoría">

            <select id="filtroPrioridad" class="select-estado">
                <option value="">Todas las prioridades</option>
                <option value="Alta">Alta</option>
                <option value="Media">Media</option>
                <option value="Baja">Baja</option>
            </select>

            <select id="filtroUbicacion" class="select-estado">
                <option value="">Todas las ubicaciones</option>
            </select>

            <button type="button" class="btn-actualizar" onclick="cargarBandeja()">Filtrar</button>
            <button type="button" class="btn-limpiar" onclick="limpiarFiltros()">Limpiar</button>
            <button type="button" class="btn-pdf" onclick="window.location.href = '<%=request.getContextPath()%>/api/reportes/incidencias?tipo=detalle'">
                PDF Detallado
            </button>
            <button type="button" class="btn-pdf btn-pdf-secundario" onclick="window.location.href = '<%=request.getContextPath()%>/api/reportes/incidencias?tipo=estado'">
                PDF por Estado
            </button>
            <button type="button" class="btn-pdf btn-pdf-terciario" onclick="window.location.href = '<%=request.getContextPath()%>/api/reportes/incidencias?tipo=zona'">
                PDF por Zona
            </button>
            <button class="btn-actualizar" onclick="window.location.href = '<%=request.getContextPath()%>/api/incidencias/csv'">
                Descargar CSV
            </button>
            <button id="btnPdfConsulta" type="button" class="btn-pdf" style="display:none;" onclick="abrirPdfConsulta()">
                Ver PDF de la consulta
            </button>
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
            const ctx = '<%=request.getContextPath()%>';

            document.addEventListener("DOMContentLoaded", function () {
                cargarBandeja();
                cargarZonas();
            });

            function obtenerIdNumerico(inc) {
                const idVal = inc.idIncidencia || inc.id || inc.id_incidencia;
                const idNum = parseInt(idVal, 10);
                return isNaN(idNum) ? null : idNum;
            }

            function parsearFiltroId(texto) {
                const valor = (texto || '').trim();

                if (valor === '') {
                    return {tipo: 'vacio'};
                }

                if (valor.includes('-')) {
                    const partes = valor.split('-').map(p => p.trim()).filter(p => p !== '');
                    if (partes.length !== 2) {
                        return {tipo: 'invalido'};
                    }

                    const min = parseInt(partes[0], 10);
                    const max = parseInt(partes[1], 10);

                    if (isNaN(min) || isNaN(max)) {
                        return {tipo: 'invalido'};
                    }

                    return {
                        tipo: 'rango',
                        min: Math.min(min, max),
                        max: Math.max(min, max)
                    };
                }

                const exacto = parseInt(valor, 10);
                if (isNaN(exacto)) {
                    return {tipo: 'invalido'};
                }

                return {tipo: 'exacto', exacto: exacto};
            }

            function construirParametrosConsulta() {
                const params = new URLSearchParams();

                const filtroIdTexto = document.getElementById('filtroId').value.trim();
                const filtroId = parsearFiltroId(filtroIdTexto);

                const estado = document.getElementById('filtroEstado').value;
                const categoria = document.getElementById('filtroCategoria').value.trim();
                const prioridad = document.getElementById('filtroPrioridad').value;
                const ubicacion = document.getElementById('filtroUbicacion').value;

                if (filtroId.tipo === 'exacto') {
                    params.append('idIncidencia', filtroId.exacto);
                } else if (filtroId.tipo === 'rango') {
                    params.append('idMin', filtroId.min);
                    params.append('idMax', filtroId.max);
                }

                if (estado)
                    params.append('estado', estado);
                if (categoria)
                    params.append('categoria', categoria);
                if (prioridad)
                    params.append('prioridad', prioridad);
                if (ubicacion)
                    params.append('ubicacion', ubicacion);

                return {params, filtroId};
            }

            function cargarBandeja() {
                const {params, filtroId} = construirParametrosConsulta();

                if (filtroId.tipo === 'invalido') {
                    alert('Escribe un ID válido o un rango tipo 15-32');
                    return;
                }

                fetch(ctx + '/api/incidencias?' + params.toString())
                        .then(async res => {
                            const text = await res.text();
                            if (!res.ok)
                                throw new Error(text);
                            return JSON.parse(text);
                        })
                        .then(datos => {
                            const tbody = document.getElementById('cuerpoTablaAdmin');
                            const btnPdf = document.getElementById('btnPdfConsulta');
                            tbody.innerHTML = '';

                            if (!Array.isArray(datos)) {
                                btnPdf.style.display = 'none';
                                tbody.innerHTML = '<tr><td colspan="6" style="text-align:center; color:#adb5bd;">Respuesta inválida del servidor.</td></tr>';
                                return;
                            }

                            const filtrados = datos.slice().sort((a, b) => {
                                const idA = obtenerIdNumerico(a);
                                const idB = obtenerIdNumerico(b);

                                if (idA === null && idB === null)
                                    return 0;
                                if (idA === null)
                                    return 1;
                                if (idB === null)
                                    return -1;

                                return idB - idA;
                            });

                            if (filtrados.length === 0) {
                                btnPdf.style.display = 'none';
                                tbody.innerHTML = '<tr><td colspan="6" style="text-align:center; color:#adb5bd;">No hay incidentes con ese filtro.</td></tr>';
                                return;
                            }

                            btnPdf.style.display = 'inline-block';

                            filtrados.forEach(inc => {
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
                        })
                        .catch(err => {
                            console.error('Error cargando bandeja:', err);
                            document.getElementById('btnPdfConsulta').style.display = 'none';
                            document.getElementById('cuerpoTablaAdmin').innerHTML =
                                    '<tr><td colspan="6" style="text-align:center; color:#adb5bd;">No se pudo cargar la tabla.</td></tr>';
                        });
            }

            function abrirPdfConsulta() {
                const {params, filtroId} = construirParametrosConsulta();

                if (filtroId.tipo === 'invalido') {
                    alert('El filtro de ID no es válido.');
                    return;
                }

                const url = ctx + '/api/reportes/incidencias?tipo=consulta&' + params.toString();
                window.open(url, '_blank', 'noopener');
            }

            function actualizarEstado(idIncidencia) {
                let nuevoEstado = document.getElementById('estado_' + idIncidencia).value;
                let payload = "accion=actualizarEstado&idIncidencia=" + idIncidencia + "&nuevoEstado=" + encodeURIComponent(nuevoEstado);

                fetch(ctx + '/api/incidencias', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: payload
                })
                        .then(res => res.json())
                        .then(data => {
                            if (data.status === 'success') {
                                alert('Estado actualizado correctamente a: ' + nuevoEstado);
                                cargarBandeja();
                            } else {
                                alert('Error BD: No se pudo actualizar el estado.');
                            }
                        })
                        .catch(err => alert('Error interno al intentar comunicarse con el servidor.'));
            }

            function limpiarFiltros() {
                document.getElementById('filtroId').value = '';
                document.getElementById('filtroEstado').value = '';
                document.getElementById('filtroCategoria').value = '';
                document.getElementById('filtroPrioridad').value = '';
                document.getElementById('filtroUbicacion').selectedIndex = 0;
                document.getElementById('btnPdfConsulta').style.display = 'none';
                cargarBandeja();
            }

            function cargarZonas() {
                fetch(ctx + '/api/zonas')
                        .then(res => res.json())
                        .then(datos => {
                            const select = document.getElementById('filtroUbicacion');
                            select.innerHTML = '<option value="">Todas las ubicaciones</option>';

                            datos.forEach(zona => {
                                const nombreZona = zona.nombreZona || zona.nombre_zona;
                                if (nombreZona) {
                                    const option = document.createElement('option');
                                    option.value = nombreZona;
                                    option.textContent = nombreZona;
                                    select.appendChild(option);
                                }
                            });
                        })
                        .catch(err => console.error('Error cargando zonas:', err));
            }
        </script>
    </body>
</html>