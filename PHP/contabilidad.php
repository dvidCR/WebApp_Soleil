<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *"); // Permite peticiones desde cualquier origen

$conn = new mysqli("localhost", "root", "root", "Fisioterapia", 3306);

if ($conn->connect_error) {
    echo json_encode(["success" => false, "message" => "Error de conexión"]);
    exit();
}

$data = ["ingresos" => [], "totalIngresos" => 0, "gastos" => [], "totalGastos" => 0];

// Obtener ingresos
$sqlIngresos = "SELECT * FROM Ingresos";
$resultIngresos = $conn->query($sqlIngresos);

if ($resultIngresos->num_rows > 0) {
    while ($row = $resultIngresos->fetch_assoc()) {
        $data["ingresos"][] = $row;
        $data["totalIngresos"] += $row["tarifa"] * $row["cantidad"];
    }
}

// Obtener gastos
$sqlGastos = "SELECT * FROM Gastos";
$resultGastos = $conn->query($sqlGastos);

if ($resultGastos->num_rows > 0) {
    while ($row = $resultGastos->fetch_assoc()) {
        $data["gastos"][] = $row;
        $data["totalGastos"] += $row["cantidad"];
    }
}

$data["beneficio"] = $data["totalIngresos"] - $data["totalGastos"];

echo json_encode(["success" => true, "data" => $data]);

$conn->close();
?>