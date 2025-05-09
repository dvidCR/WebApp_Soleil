async function login() {
    const usuario = document.getElementById('user').value;
    const contrasena = document.getElementById('password').value;

    const response = await fetch('/empleado/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ usuario, contrasena })
    });

    const message = document.getElementById('message');
}