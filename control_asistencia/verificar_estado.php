<?php
/**
 * Verificar estado completo del sistema
 */

echo "=== VERIFICACIÓN DEL SISTEMA ===\n\n";

// 1. Verificar XAMPP
echo "1. Verificando XAMPP... ";
exec('tasklist /FI "IMAGENAME eq httpd.exe" 2>nul', $apache);
if (count($apache) > 2) {
    echo "✅ Apache ejecutándose\n";
} else {
    echo "❌ Apache no está ejecutándose\n";
}

// 2. Verificar MySQL
echo "2. Verificando MySQL... ";
exec('tasklist /FI "IMAGENAME eq mysqld.exe" 2>nul', $mysql);
if (count($mysql) > 2) {
    echo "✅ MySQL ejecutándose\n";
} else {
    echo "❌ MySQL no está ejecutándose\n";
}

// 3. Verificar Arduino
echo "3. Verificando Arduino... ";
$port = 'COM4';
$handle = @fopen("\\\\.\\$port", "r");
if ($handle) {
    echo "✅ Arduino conectado en COM4\n";
    fclose($handle);
} else {
    echo "❌ Arduino no encontrado en COM4\n";
}

// 4. Verificar proceso lector serial
echo "4. Verificando lector serial... ";
exec('tasklist /FI "WINDOWTITLE eq Lector RFID" 2>nul', $lector);
if (count($lector) > 2) {
    echo "✅ Lector serial ejecutándose\n";
} else {
    echo "❌ Lector serial no está ejecutándose\n";
}

echo "\n🎯 RECOMENDACIONES:\n";
if (count($lector) <= 2) {
    echo " - Ejecutar: php serial_reader.php COM4\n";
}
if (count($apache) <= 2) {
    echo " - Iniciar Apache desde XAMPP\n";
}

echo "\nURL: http://localhost/control_asistencia\n";
?>