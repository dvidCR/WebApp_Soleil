async function createUser() {
    var user = document.getElementById("user").value;
    var password = document.getElementById("password").value;
    
    try {
        await fetch("../PHP/temporal.php", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ user, password })
        });

        document.getElementById("message").textContent = "Usuario creado";
    } catch (error) {
        console.error("Error en la autenticación", error);
        document.getElementById("message").textContent = "Error al crear el usuario";
    }
}