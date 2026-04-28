<?php
echo "🎯 LECTOR RFID SIMPLE\n";
echo "===================\n";

$port = 'COM4';
$apiUrl = 'http://localhost/control_asistencia/api/registrar.php';

while (true) {
    echo "Conectando a $port... ";
    
    $handle = @fopen("\\\\.\\{$port}", "r");
    if (!$handle) {
        echo "FALLÓ\n";
        sleep(5);
        continue;
    }
    
    echo "OK\nLeyendo...\n";
    
    stream_set_timeout($handle, 1);
    
    // Leer por 5 minutos, luego reconectar
    $startTime = time();
    while (time() - $startTime < 300) { // 5 minutos
        $data = fgets($handle);
        
        if ($data !== false && trim($data) !== '') {
            echo "[" . date('H:i:s') . "] " . trim($data) . "\n";
            
            if (preg_match('/"uid":"([0-9A-F]{8})"/', $data, $matches)) {
                $uid = $matches[1];
                echo "Enviando: $uid - ";
                
                $post = http_build_query(['uid' => $uid]);
                $context = stream_context_create(['http' => [
                    'method' => 'POST',
                    'header' => 'Content-Type: application/x-www-form-urlencoded',
                    'content' => $post,
                    'timeout' => 3
                ]]);
                
                $response = @file_get_contents($apiUrl, false, $context);
                if ($response) {
                    $result = json_decode($response, true);
                    if ($result['success']) {
                        echo $result['tipo'] . " - " . $result['nombre'] . "\n";
                    } else {
                        echo $result['message'] . "\n";
                    }
                } else {
                    echo "Error API\n";
                }
                echo "--------\n";
            }
        }
        
        // Verificar si el puerto sigue conectado
        if (feof($handle)) {
            echo "Puerto desconectado\n";
            break;
        }
        
        usleep(100000); // 100ms
    }
    
    fclose($handle);
    echo "Reconectando en 3 segundos...\n";
    sleep(3);
}
?>