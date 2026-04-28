<?php
/**
 * Test continuo del RFID
 */
echo "=== TEST CONTINUO RFID ===\n";
echo "Conectando a COM4...\n";

$handle = @fopen("\\\\.\\COM4", "r+");
if (!$handle) {
    die("❌ No se pudo abrir COM4\n");
}

stream_set_timeout($handle, 0, 100000);

echo "✅ Conectado - Monitoreando...\n";
echo "Acerca tarjetas al lector RC522\n";
echo "================================\n";

$lastMessage = time();

while (true) {
    $data = fgets($handle);
    
    if ($data !== false && trim($data) !== '') {
        echo "[" . date('H:i:s') . "] " . trim($data) . "\n";
        $lastMessage = time();
    }
    
    // Mostrar mensaje cada 10 segundos si no hay actividad
    if (time() - $lastMessage > 10) {
        echo "⏰ Esperando tarjetas... (" . date('H:i:s') . ")\n";
        $lastMessage = time();
    }
    
    usleep(100000);
}

fclose($handle);
?>