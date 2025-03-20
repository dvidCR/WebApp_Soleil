<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");  // Permite peticiones desde cualquier origen

$conn = new mysqli("localhost", "root", "root", "Fisioterapia", 3306);

if ($conn->connect_error) {
    echo json_encode(["success" => false, "message" => "Error de conexión"]);
    exit();
}

$sql = "SELECT * FROM Ingresos";
$result = $conn->query($sql);

$ingresos = [];

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $ingresos[] = $row;
    }
}

echo json_encode(["success" => true, "data" => $ingresos]);

$conn->close();
?>