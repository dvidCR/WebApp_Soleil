async function login() {
    var user = document.getElementById("user").value;
    var password = document.getElementById("password").value;
    
    try {
        let response = await fetch("../PHP/login.php", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ user, password })
        });

        let result = await response.json();
        
        if (result.success) {
            // Redirigir según el usuario
            if (user.toLowerCase() === "admin") {
                window.location.href = "admin.html";
            } else if (user.toLowerCase() === "usuario") {
                window.location.href = "user.html";
            } else {
                alert("Usuario no tiene una página asignada");
            }
        } else {
            document.getElementById("message").textContent = "Usuario o contraseña incorrectos";
        }
    } catch (error) {
        console.error("Error en la autenticación", error);
        document.getElementById("message").textContent = "Error en la conexión con el servidor";
    }
}