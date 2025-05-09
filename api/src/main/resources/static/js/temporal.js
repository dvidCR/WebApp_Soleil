async function createUser() {
    const dni = document.getElementById("dni").value;
    const nombre = document.getElementById("nombre").value;
    const apellidos = document.getElementById("apellidos").value;
    const correo = document.getElementById("correo").value;
    const usuario = document.getElementById("user").value;
    const contrasena = document.getElementById("password").value;
    const rol = document.getElementById("rol").value;

    // Validación básica
    if (!dni || !nombre || !apellidos || !correo || !usuario || !contrasena || !rol) {
        document.getElementById("message").textContent = "Por favor, completa todos los campos";
        return;
    }

    try {
        const response = await fetch("/api/empleado", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                dni,
                nombre,
                apellidos,
                correo,
                usuario,
                contrasena,
                rol
            })
        });

        if (response.ok) {
            document.getElementById("message").textContent = "Usuario creado correctamente";
        } else {
            const errorText = await response.text();
            console.error("Error:", errorText);
            document.getElementById("message").textContent = "Error al crear el usuario";
        }
    } catch (error) {
        console.error("Error al crear usuario:", error);
        document.getElementById("message").textContent = "Error en la conexión con el servidor";
    }
}