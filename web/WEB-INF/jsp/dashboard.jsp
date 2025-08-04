<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="modelo.Usuario" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("login");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Mejores repartos</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/pemex.css">
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                padding: 8px;
                border: 1px solid #ccc;
                text-align: center;
            }
            .contenedor {
                max-width: 600px;
                margin: 0 auto;
            }

            input[type="submit"] {
                margin-top: 25px;
            }
        </style>



    </head>
    <body>
        <div class="contenedor">
            <h2>Bienvenido, <%= usuario.getNombreCompleto()%>!</h2>

            <h2>Registrar entregas</h2>
            <textarea id="excelInput" rows="5" cols="100" placeholder="Pega aquí desde Excel..."></textarea>
            <br>
            <button onclick="procesarPegado()">Procesar Datos</button>

            <table id="tablaExcel" border="1" style="margin-top: 20px; width: 100%; table-layout: auto;">
                <thead>
                    <tr>
                        <th >ID Registro</th>
                        <th style="min-width: 100px;">Fecha</th>
                        <th>Vehículo</th>
                        <th>Cliente</th>
                        <th>Destino</th>
                        <th>Producto</th>
                        <th>Cantidad Litros</th>
                        <th>Tiempo Entrega (horas)</th>
                        <th>Acción</th>
                    </tr>
                </thead>
                <tbody></tbody>
            </table>

            <br>
            <button onclick="agregarFila()">Agregar fila</button>
            <button onclick="compararConTiempos()">Comparar con Tiempos</button>
            <button onclick="guardarEntregasValidadas()">Guardar validaciones</button> <br><br>
            <button onclick="generarReporte()">Reporte: Entregas más largas</button>


            <script>

                function agregarFila() {
                    const tabla = document.getElementById("tablaExcel").getElementsByTagName("tbody")[0];
                    const fila = tabla.insertRow();


                    let celda = fila.insertCell();
                    let input = document.createElement("input");
                    input.type = "number";
                    input.min = "1";
                    celda.appendChild(input);


                    celda = fila.insertCell();
                    input = document.createElement("input");
                    input.type = "date";
                    celda.appendChild(input);


                    celda = fila.insertCell();
                    input = document.createElement("input");
                    input.type = "number";
                    input.min = "1";
                    celda.appendChild(input);


                    celda = fila.insertCell();
                    input = document.createElement("input");
                    input.type = "number";
                    input.min = "1";
                    celda.appendChild(input);


                    celda = fila.insertCell();
                    input = document.createElement("input");
                    input.type = "number";
                    input.min = "1";
                    celda.appendChild(input);


                    celda = fila.insertCell();
                    input = document.createElement("input");
                    input.type = "number";
                    input.min = "1";
                    celda.appendChild(input);


                    celda = fila.insertCell();
                    input = document.createElement("input");
                    input.type = "number";
                    input.min = "0.01";
                    input.step = "0.01";
                    celda.appendChild(input);

                    celda = fila.insertCell();
                    input = document.createElement("input");
                    input.type = "number";
                    input.min = "0.01";
                    input.step = "0.01";
                    celda.appendChild(input);

                    celda = fila.insertCell();
                    const btnEliminar = document.createElement("button");
                    btnEliminar.textContent = "Eliminar";
                    btnEliminar.onclick = () => {
                        if (confirm("¿Estás seguro de eliminar esta fila?")) {
                            fila.remove();
                        }
                    };
                    celda.appendChild(btnEliminar);
                }

                function compararConTiempos() {
                    const filas = document.querySelectorAll("#tablaExcel tbody tr");
                    const entregas = [];

                    filas.forEach(fila => {
                        const celdas = fila.querySelectorAll("td");
                        if (celdas.length >= 7) {
                            const cliente = celdas[3].textContent.trim();
                            const destino = celdas[4].textContent.trim();
                            entregas.push({cliente, destino});
                        }
                    });

                    fetch("CompararEntregasServlet", {
                        method: "POST",
                        headers: {'Content-Type': 'application/json'},
                        body: JSON.stringify(entregas)
                    })
                            .then(res => res.json())
                            .then(resultados => {

                                const filas = document.querySelectorAll("#tablaExcel tbody tr");

                                resultados.forEach((res, i) => {
                                    console.log(res);


                                    const fila = filas[i];
                                    const celdaCliente = fila.children[3];
                                    const celdaDestino = fila.children[4];
                                    const celdaTiempo = fila.children[7];
                                    const nombreCliente = res.cliente;
                                    const nombreDestino = res.destino;
                                    const tiempoEntrega = res.tiempo_entrega;

                                    if (res.valido) {
                                        celdaCliente.innerHTML = '<span style="color: green; font-weight: bold;" data-id="' + res.idcliente + '">' + nombreCliente + '</span>';
                                        celdaDestino.innerHTML = '<span style="color: green; font-weight: bold;" data-id="' + res.iddestino + '">' + nombreDestino + '</span>';
                                        celdaTiempo.innerHTML = tiempoEntrega;
                                    } else {
                                        celdaCliente.innerHTML = `<span style="color: red; font-weight: bold;">Cliente no existe</span>`;
                                        celdaDestino.innerHTML = `<span style="color: red; font-weight: bold;">Destino no existe</span>`;
                                    }
                                });

                                alert("Comparación completada.");
                            })
                            .catch(err => {
                                console.error("Error:", err);
                                alert("Error al comparar con tiempos.");
                            });
                }

                function procesarPegado() {
                    const input = document.getElementById("excelInput").value.trim();
                    const filas = input.split('\n');
                    const tbody = document.querySelector("#tablaExcel tbody");
                    tbody.innerHTML = '';

                    filas.forEach(linea => {
                        const columnas = linea.split('\t'); // TAB del portapapeles
                        const fila = document.createElement("tr");

                        columnas.forEach(valor => {
                            const celda = document.createElement("td");
                            celda.textContent = valor;
                            fila.appendChild(celda);
                        });

                        // Crear celda para botón eliminar
                        const celdatiempo = document.createElement("td");
                        const celdaAcciones = document.createElement("td");

                        const btnEliminar = document.createElement("button");
                        btnEliminar.textContent = "Eliminar";
                        btnEliminar.onclick = () => fila.remove();
                        celdaAcciones.appendChild(btnEliminar);
                        fila.appendChild(celdatiempo);
                        fila.appendChild(celdaAcciones);

                        tbody.appendChild(fila);
                    });
                }

                document.addEventListener('DOMContentLoaded', () => {
                    /////////////ordena columnas
                    const tabla = document.getElementById("tablaExcel");
                    const headers = tabla.querySelectorAll("th");
                    let asc = true;

                    headers.forEach((th, colIndex) => {
                        th.style.cursor = "pointer";
                        th.addEventListener("click", () => {
                            const rows = Array.from(tabla.querySelector("tbody").rows);
                            rows.sort((a, b) => {
                                const valA = a.cells[colIndex].innerText.trim();
                                const valB = b.cells[colIndex].innerText.trim();

                                const numA = parseFloat(valA);
                                const numB = parseFloat(valB);
                                const esNumero = !isNaN(numA) && !isNaN(numB);

                                if (esNumero) {
                                    return asc ? numA - numB : numB - numA;
                                } else {
                                    return asc
                                            ? valA.localeCompare(valB)
                                            : valB.localeCompare(valA);
                                }
                            });

                            // Reordenar las filas
                            const tbody = tabla.querySelector("tbody");
                            rows.forEach(row => tbody.appendChild(row));
                            asc = !asc;
                        });
                    });
                });

                function guardarEntregasValidadas() {
                    const filas = document.querySelectorAll("#tablaExcel tbody tr");
                    const entregasValidadas = [];

                    filas.forEach(fila => {
                        const celdaCliente = fila.children[3];
                        const celdaDestino = fila.children[4];
                        const spanCliente = celdaCliente.querySelector("span");
                        const spanDestino = celdaDestino.querySelector("span");

                        const esValidado = spanCliente?.style.color === "green" && spanDestino?.style.color === "green";

                        if (esValidado) {
                            const entrega = {
                                idRegistro: fila.children[0].textContent.trim(),
                                fecha: fila.children[1].textContent.trim(),
                                idVehiculo: fila.children[2].textContent.trim(),
                                idCliente: spanCliente.dataset.id,
                                idDestino: spanDestino.dataset.id,
                                idProducto: fila.children[5].textContent.trim(),
                                cantidad: fila.children[6].textContent.trim()
                            };
                            entregasValidadas.push(entrega);
                        }
                    });

                    if (entregasValidadas.length === 0) {
                        alert("No hay entregas validadas para guardar.");
                        return;
                    }

                    fetch("RegistroEntregasServlet", {
                        method: "POST",
                        headers: {'Content-Type': 'application/json'},
                        body: JSON.stringify(entregasValidadas)
                    })
                            .then(res => res.text())
                            .then(msg => {
                                alert("Entregas guardadas correctamente.");
                                console.log(msg);
                            })
                            .catch(err => {
                                console.error("Error al guardar:", err);
                                alert("Error al guardar las entregas.");
                            });
                }


                function generarReporte() {
                    fetch("ReporteEntregasLentasServlet")
                            .then(res => res.json())
                            .then(data => {
                                if (data.length === 0) {
                                    alert("No se encontraron entregas.");
                                    return;
                                }

                                let tabla = "<table border='1' style='width:100%; margin-top:20px;'>";
                                tabla += "<tr><th>ID Registro</th><th>Fecha</th><th>Cliente</th><th>Destino</th><th>Vehículo</th><th>Producto</th><th>Cantidad</th><th>Duración (horas)</th></tr>";

                                data.forEach(e => {
                                    console.log(e);
                                    tabla += '<tr>' +
                                            '<td>' + e.id_registro + '</td>' +
                                            '<td>' + e.fecha + '</td>' +
                                            '<td>' + e.cliente + '</td>' +
                                            '<td>' + e.destino + '</td>' +
                                            '<td>' + e.vehiculo + '</td>' +
                                            '<td>' + e.producto + '</td>' +
                                            '<td>' + e.cantidad + '</td>' +
                                            '<td>' + e.tiempo + '</td>' +
                                            '</tr>';
                                });

                                tabla += "</table>";
                                const div = document.createElement("div");
                                div.innerHTML = "<h3>Entregas con mayor duración estimada</h3>" + tabla;
                                document.body.appendChild(div);
                            })
                            .catch(err => {
                                console.error(err);
                                alert("Error al generar el reporte.");
                            });
                }

            </script>


    </body>
</html>