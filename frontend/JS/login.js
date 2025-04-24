async function login() {
    var user = document.getElementById("user").value;
    var password = document.getElementById("password").value;
    
    try {
        let response = await fetch("/api/empleado/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ usuario: user, contrasena: password })
        });

        if (response.ok) {
            let result = await response.json();

            if (result.length > 0) {
                const empleado = result[0]; // si hay mas de uno, usamos el primero

                if (empleado.rol.toLowerCase() === "admin") {
                    window.location.href = "admin.html";
                } else if (empleado.rol.toLowerCase() === "usuario") {
                    window.location.href = "user.html";
                } else {
                    alert("Usuario sin rol asignada");
                }
            } else {
                document.getElementById("message").textContent = "Usuario o contraseña incorrectos";
            }
        } else {
            document.getElementById("message").textContent = "Error al iniciar sesion";
        }
    } catch (error) {
        console.error("Error en la autenticarte:", error);
        document.getElementById("message").textContent = "Error en la conexion con el servidor";
    }
}