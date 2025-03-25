document.addEventListener("DOMContentLoaded", function () {
    fetch("http://localhost/Soleil/PHP/contabilidad.php")
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                let tablaIngresos = document.getElementById("tabla-ingresos");
                let totalIngresos = document.getElementById("total-ingresos");
                let tablaGastos = document.getElementById("tabla-gastos");
                let totalGastos = document.getElementById("total-gastos");
                let beneficios = document.getElementById("beneficios");

                data.data.ingresos.forEach(ingreso => {
                    let fila = `<tr>
                        <td>${ingreso.id}</td>
                        <td>${ingreso.servicio}</td>
                        <td>${ingreso.dia}</td>
                        <td>${ingreso.fisioterapeuta}</td>
                        <td>${ingreso.paciente}</td>
                        <td>${ingreso.pago}</td>
                        <td>${ingreso.tarifa}</td>
                        <td>${ingreso.cantidad}</td>
                    </tr>`;
                    tablaIngresos.innerHTML += fila;
                });

                totalIngresos.textContent = `Ingreso: ${data.data.totalIngresos}€`;

                data.data.gastos.forEach(gasto => {
                    let fila = `<tr>
                        <td>${gasto.id}</td>
                        <td>${gasto.cantidad}</td>
                        <td>${gasto.motivo}</td>
                        <td>${gasto.proveedor}</td>
                    </tr>`;
                    tablaGastos.innerHTML += fila;
                });

                totalGastos.textContent = `Gasto: ${data.data.totalGastos}€`;

                beneficios.textContent = `Beneficios: ${data.data.beneficio}€`;
            } else {
                console.error("Error al cargar la contabilidad");
            }
        })
        .catch(error => console.error("Error en la petición", error));
});