<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE');
header('Access-Control-Allow-Headers: Content-Type');

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "control_asistencia";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die(json_encode(["success" => false, "message" => "Error de conexión"]));
}

// Determinar el método HTTP
$method = $_SERVER['REQUEST_METHOD'];

if ($method == 'GET') {
    // Obtener todos los usuarios
    $sql = "SELECT * FROM usuarios ORDER BY nombre";
    $result = $conn->query($sql);
    
    $usuarios = [];
    while($row = $result->fetch_assoc()) {
        $usuarios[] = $row;
    }
    
    echo json_encode(["success" => true, "usuarios" => $usuarios]);
    
} elseif ($method == 'POST') {
    // Crear o actualizar usuario
    $input = json_decode(file_get_contents('php://input'), true);
    $action = $input['action'] ?? 'create';
    
    if ($action == 'create') {
        // Crear nuevo usuario
        $uid = $conn->real_escape_string($input['uid']);
        $nombre = $conn->real_escape_string($input['nombre']);
        $activo = isset($input['activo']) ? (int)$input['activo'] : 1;
        
        // Verificar si el UID ya existe
        $sql_check = "SELECT id FROM usuarios WHERE uid = '$uid'";
        $result_check = $conn->query($sql_check);
        
        if ($result_check->num_rows > 0) {
            echo json_encode(["success" => false, "message" => "El UID ya está registrado"]);
        } else {
            $sql_insert = "INSERT INTO usuarios (uid, nombre, activo) VALUES ('$uid', '$nombre', $activo)";
            if ($conn->query($sql_insert)) {
                echo json_encode([
                    "success" => true, 
                    "message" => "Usuario creado exitosamente",
                    "id" => $conn->insert_id
                ]);
            } else {
                echo json_encode(["success" => false, "message" => "Error al crear usuario: " . $conn->error]);
            }
        }
        
    } elseif ($action == 'update') {
        // Actualizar usuario existente
        $id = (int)$input['id'];
        $nombre = $conn->real_escape_string($input['nombre']);
        $activo = isset($input['activo']) ? (int)$input['activo'] : 1;
        
        $sql_update = "UPDATE usuarios SET nombre = '$nombre', activo = $activo WHERE id = $id";
        if ($conn->query($sql_update)) {
            echo json_encode(["success" => true, "message" => "Usuario actualizado exitosamente"]);
        } else {
            echo json_encode(["success" => false, "message" => "Error al actualizar usuario: " . $conn->error]);
        }
        
    } elseif ($action == 'delete') {
        // Eliminar usuario (borrado lógico)
        $id = (int)$input['id'];
        
        $sql_delete = "UPDATE usuarios SET activo = 0 WHERE id = $id";
        if ($conn->query($sql_delete)) {
            echo json_encode(["success" => true, "message" => "Usuario desactivado exitosamente"]);
        } else {
            echo json_encode(["success" => false, "message" => "Error al desactivar usuario: " . $conn->error]);
        }
    }
    
} elseif ($method == 'PUT') {
    // Método PUT para actualizar (alternativa)
    parse_str(file_get_contents("php://input"), $put_vars);
    $id = (int)$put_vars['id'];
    $nombre = $conn->real_escape_string($put_vars['nombre']);
    $activo = isset($put_vars['activo']) ? (int)$put_vars['activo'] : 1;
    
    $sql_update = "UPDATE usuarios SET nombre = '$nombre', activo = $activo WHERE id = $id";
    if ($conn->query($sql_update)) {
        echo json_encode(["success" => true, "message" => "Usuario actualizado exitosamente"]);
    } else {
        echo json_encode(["success" => false, "message" => "Error al actualizar usuario"]);
    }
}

$conn->close();
?>