function createUser() {
    const empleado = {
        dni: document.getElementById("dni").value,
        nombre: document.getElementById("nombre").value,
        apellidos: document.getElementById("apellidos").value,
        correo: document.getElementById("correo").value,
        usuario: document.getElementById("user").value,
        contrasena: document.getElementById("password").value,
        rol: document.getElementById("rol").value
    };

    fetch("/empleado", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(empleado)
    })
    .then(response => {
        if (response.ok) {
            document.getElementById("message").textContent = "Usuario creado correctamente.";
            document.getElementById("message").style.color = "green";
        } else {
            return response.text().then(text => { throw new Error(text); });
        }
    })
    .catch(error => {
        console.error("Error al crear el usuario:", error);
        document.getElementById("message").textContent = "Error al crear el usuario.";
        document.getElementById("message").style.color = "red";
    });
}