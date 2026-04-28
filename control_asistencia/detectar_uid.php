<?php
/**
 * Script para detectar el UID de tarjetas nuevas
 * Ejecutar: php detectar_uid.php COM3
 */

if (php_sapi_name() !== 'cli') {
    die("Ejecutar desde línea de comandos\n");
}

$port = $argv[1] ?? 'COM3';
echo "Conectando a $port - Pase una tarjeta RFID...\n";

$handle = fopen("\\\\.\\{$port}", "r+");
if (!$handle) {
    die("Error abriendo puerto\n");
}

while (true) {
    $data = fgets($handle);
    if ($data && trim($data) !== '') {
        if (preg_match('/"uid":"([0-9A-F]{8})"/', $data, $matches)) {
            $uid = $matches[1];
            echo "\n✅ NUEVA TARJETA DETECTADA:\n";
            echo "UID: " . $uid . "\n";
            echo "Para registrar, usa este UID en la interfaz web\n";
            echo "----------------------------------------\n";
        }
    }
    usleep(100000);
}
?>