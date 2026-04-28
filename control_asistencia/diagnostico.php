<?php
echo "=== DIAGNÓSTICO COMPLETO ===\n\n";

// 1. Verificar puerto
$port = 'COM4';
echo "1. Probando puerto $port... ";
$handle = @fopen("\\\\.\\$port", "r+b");
if ($handle) {
    echo "✅ ABIERTO\n";
    
    // Configurar
    stream_set_timeout($handle, 1);
    
    // Leer por 5 segundos
    echo "2. Leyendo datos (5 segundos)...\n";
    $start = time();
    $dataReceived = false;
    
    while (time() - $start < 5) {
        $data = fgets($handle);
        if ($data !== false && trim($data) !== '') {
            echo "   📨: " . trim($data) . "\n";
            $dataReceived = true;
        }
        usleep(100000);
    }
    
    if (!$dataReceived) {
        echo "   ❌ NO HAY DATOS\n";
        echo "   🔧 SOLUCIÓN: El Arduino no está enviando datos\n";
    }
    
    fclose($handle);
} else {
    echo "❌ ERROR\n";
}

// 3. Verificar API
echo "3. Probando API... ";
$response = @file_get_contents('http://localhost/control_asistencia/api/registrar.php');
if ($response !== false) {
    echo "✅ FUNCIONA\n";
    echo "   Respuesta: " . $response . "\n";
} else {
    echo "❌ ERROR\n";
}

echo "\n🎯 RESUMEN:\n";
echo "Si ves 'UID:' en los datos, el Arduino SÍ está enviando\n";
echo "Si no ves nada, el problema es el Arduino\n";
?>