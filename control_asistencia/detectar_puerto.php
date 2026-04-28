<?php
/**
 * Script para detectar puertos COM disponibles
 */
echo "=== DETECTOR DE PUERTOS COM ===\n\n";

$ports = [];
for ($i = 1; $i <= 10; $i++) {
    $port = "COM$i";
    $handle = @fopen("\\\\.\\$port", "r");
    
    if ($handle) {
        echo "✅ $port - DISPONIBLE\n";
        fclose($handle);
        $ports[] = $port;
    } else {
        echo "❌ $port - No disponible\n";
    }
}

if (empty($ports)) {
    echo "\n❌ No se encontraron puertos COM disponibles\n";
    echo "Verifique que el Arduino esté conectado\n";
} else {
    echo "\n✅ Puertos disponibles: " . implode(', ', $ports) . "\n";
    echo "Use: php serial_reader.php " . $ports[0] . "\n";
}
?>