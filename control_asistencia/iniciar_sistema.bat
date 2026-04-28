@echo off
title Sistema Control de Asistencia RFID
echo ========================================
echo    SISTEMA CONTROL DE ASISTENCIA RFID
echo ========================================
echo.

echo 1. Cerrando programas conflictivos...
taskkill /f /im arduin*.exe 2>nul
timeout /t 2

echo 2. Iniciando servicios XAMPP...
cd C:\xampp
start /B apache_start.bat
timeout /t 3
start /B mysql_start.bat
timeout /t 3

echo 3. Verificando Arduino en COM4...
cd C:\xampp\htdocs\control_asistencia
php -r "$h = @fopen('\\\\.\\COM4', 'r'); if($h) { echo '✅ Arduino detectado en COM4'; fclose($h); } else { echo '❌ Arduino no encontrado'; }"
echo.

echo 4. Abriendo interfaz web...
start http://localhost/control_asistencia
timeout /t 2

echo 5. Iniciando lector RFID...
start "Lector RFID" cmd /k "cd C:\xampp\htdocs\control_asistencia && echo LECTOR RFID INICIADO && echo =================== && php serial_reader.php COM4"

echo.
echo ========================================
echo    ✅ SISTEMA INICIADO CORRECTAMENTE
echo ========================================
echo 📊 Interfaz: http://localhost/control_asistencia
echo 📍 Arduino: COM4 
echo 🎯 Estado: Se actualiza automaticamente
echo.
pause