<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "control_asistencia";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    echo json_encode(["success" => false, "message" => "Error BD"]);
    exit;
}

$hoy = date('Y-m-d');

// 1. TOTAL REGISTROS HOY
$sql_total = "SELECT COUNT(*) as total FROM registros_asistencia WHERE DATE(fecha_hora) = '$hoy'";
$result_total = $conn->query($sql_total);
$total_hoy = $result_total ? $result_total->fetch_assoc()['total'] : 0;

// 2. TOTAL USUARIOS ACTIVOS
$sql_usuarios = "SELECT COUNT(*) as total FROM usuarios WHERE activo = 1";
$result_usuarios = $conn->query($sql_usuarios);
$total_usuarios = $result_usuarios ? $result_usuarios->fetch_assoc()['total'] : 0;

// 3. ARDUINO ONLINE
$arduino_online = ($total_hoy > 0);

// 4. ÚLTIMOS 5 REGISTROS
$sql_ultimos = "SELECT r.id, r.fecha_hora, r.tipo, u.nombre, u.uid 
                FROM registros_asistencia r 
                JOIN usuarios u ON r.usuario_id = u.id 
                ORDER BY r.fecha_hora DESC 
                LIMIT 5";
$result_ultimos = $conn->query($sql_ultimos);
$ultimos_registros = [];
if ($result_ultimos) {
    while($row = $result_ultimos->fetch_assoc()) {
        $ultimos_registros[] = $row;
    }
}

// 5. USUARIOS PRESENTES - MÉTODO DIRECTO Y FIABLE
$presentes = 0;
if ($total_hoy > 0) {
    // Obtener todos los usuarios que tienen registros hoy
    $sql_todos_usuarios = "SELECT DISTINCT usuario_id FROM registros_asistencia WHERE DATE(fecha_hora) = '$hoy'";
    $result_usuarios_hoy = $conn->query($sql_todos_usuarios);
    
    if ($result_usuarios_hoy && $result_usuarios_hoy->num_rows > 0) {
        while($usuario = $result_usuarios_hoy->fetch_assoc()) {
            $usuario_id = $usuario['usuario_id'];
            
            // Obtener el ÚLTIMO registro de este usuario hoy
            $sql_ultimo = "SELECT tipo FROM registros_asistencia 
                           WHERE usuario_id = $usuario_id 
                           AND DATE(fecha_hora) = '$hoy' 
                           ORDER BY fecha_hora DESC 
                           LIMIT 1";
            
            $result_ultimo = $conn->query($sql_ultimo);
            if ($result_ultimo && $result_ultimo->num_rows > 0) {
                $ultimo = $result_ultimo->fetch_assoc();
                // Si el último registro es 'entrada', está presente
                if ($ultimo['tipo'] == 'entrada') {
                    $presentes++;
                }
            }
        }
    }
}

echo json_encode([
    "success" => true,
    "total_hoy" => (int)$total_hoy,
    "total_usuarios" => (int)$total_usuarios,
    "presentes" => (int)$presentes,
    "ultimos_registros" => $ultimos_registros,
    "arduino_online" => $arduino_online,
    "timestamp" => date('H:i:s'),
    "debug" => "Presentes calculados: $presentes" // ← Para debugging
]);

$conn->close();
?>