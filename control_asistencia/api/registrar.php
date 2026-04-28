<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Content-Type');

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "control_asistencia";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    echo json_encode(["success" => false, "message" => "Error de conexión BD"]);
    exit;
}

$input = file_get_contents('php://input');
$data = [];

if (!empty($input)) {
    parse_str($input, $data);
}

$uid = isset($data['uid']) ? trim($data['uid']) : '';
$uid = str_replace(' ', '', $uid);

if (empty($uid)) {
    echo json_encode(["success" => false, "message" => "UID no proporcionado"]);
    exit;
}

// Buscar usuario
$sql = "SELECT id, nombre FROM usuarios WHERE uid = ? AND activo = 1";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $uid);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $usuario = $result->fetch_assoc();
    $usuario_id = $usuario['id'];
    $nombre = $usuario['nombre'];

    // Verificar último registro
    $sql_ultimo = "SELECT tipo FROM registros_asistencia 
                  WHERE usuario_id = ? 
                  AND DATE(fecha_hora) = CURDATE()
                  ORDER BY fecha_hora DESC 
                  LIMIT 1";
    
    $stmt_ultimo = $conn->prepare($sql_ultimo);
    $stmt_ultimo->bind_param("i", $usuario_id);
    $stmt_ultimo->execute();
    $result_ultimo = $stmt_ultimo->get_result();

    $tipo = 'entrada';
    
    if ($result_ultimo->num_rows > 0) {
        $ultimo_registro = $result_ultimo->fetch_assoc();
        $tipo = ($ultimo_registro['tipo'] == 'entrada') ? 'salida' : 'entrada';
    }

    // Insertar registro
    $sql_insert = "INSERT INTO registros_asistencia (usuario_id, tipo) VALUES (?, ?)";
    $stmt_insert = $conn->prepare($sql_insert);
    $stmt_insert->bind_param("is", $usuario_id, $tipo);

    if ($stmt_insert->execute()) {
        // Obtener el ID del nuevo registro
        $nuevo_id = $stmt_insert->insert_id;
        
        echo json_encode([
            "success" => true,
            "message" => "Asistencia registrada",
            "nombre" => $nombre,
            "tipo" => $tipo,
            "fecha" => date('Y-m-d H:i:s'),
            "uid" => $uid,
            "registro_id" => $nuevo_id,  // ← IMPORTANTE para notificaciones
            "notificacion" => true,
            "icono" => $tipo == 'entrada' ? '✅' : '🚪',
            "color" => $tipo == 'entrada' ? 'success' : 'warning'
        ]);
    } else {
        echo json_encode(["success" => false, "message" => "Error al registrar"]);
    }
    
    $stmt_insert->close();
    $stmt_ultimo->close();
} else {
    echo json_encode(["success" => false, "message" => "Usuario no registrado"]);
}

$stmt->close();
$conn->close();
?>