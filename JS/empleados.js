document.addEventListener("DOMContentLoaded", function () {
    fetch("http://localhost/Soleil/PHP/ingresos.php") // Asegúrate de que la ruta es correcta
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                let tabla = document.getElementById("tabla-ingresos");
                data.data.forEach(ingreso => {
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
                    tabla.innerHTML += fila;
                });
            } else {
                console.error("Error al cargar los ingresos");
            }
        })
        .catch(error => console.error("Error en la petición", error));
});