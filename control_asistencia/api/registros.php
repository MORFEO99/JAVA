<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "control_asistencia";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die(json_encode(["success" => false, "message" => "Error de conexión"]));
}

$fecha_inicio = isset($_GET['fecha_inicio']) ? $_GET['fecha_inicio'] : date('Y-m-d');
$fecha_fin = isset($_GET['fecha_fin']) ? $_GET['fecha_fin'] : date('Y-m-d');

$sql = "SELECT r.*, u.nombre, u.uid 
        FROM registros_asistencia r 
        JOIN usuarios u ON r.usuario_id = u.id 
        WHERE DATE(r.fecha_hora) BETWEEN '$fecha_inicio' AND '$fecha_fin'
        ORDER BY r.fecha_hora DESC";

$result = $conn->query($sql);
$registros = [];

while($row = $result->fetch_assoc()) {
    $registros[] = $row;
}

echo json_encode([
    "success" => true,
    "registros" => $registros
]);

$conn->close();
?>