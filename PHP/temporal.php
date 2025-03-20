<?php
header("Content-Type: application/json");
$conn = new mysqli("localhost", "root", "root", "../SQL/Fisioterapia.sql", 3306);

if ($conn->connect_error) {
    echo json_encode(["success" => false, "message" => "Error de conexión"]);
    exit();
}

$data = json_decode(file_get_contents("php://input"), true);
$user = $conn->real_escape_string($data["user"]);
$password = $conn->real_escape_string($data["password"]);

$sql = "INSERT INTO Usuarios VALUES($user, $password, NULL)";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    echo json_encode(["success" => true]);
} else {
    echo json_encode(["success" => false, "message" => "Usuario o contraseña mal escritos"]);
}

$conn->close();
?>