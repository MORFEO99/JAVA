<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Control de Asistencia RFID</title>
    
    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    
    <!-- Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    
    <style>
        :root {
            --color-primary: #2c3e50;
            --color-secondary: #3498db;
            --color-success: #27ae60;
            --color-warning: #f39c12;
            --color-danger: #e74c3c;
            --color-light: #ecf0f1;
            --color-dark: #2c3e50;
        }
        
        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        .sidebar {
            background: linear-gradient(180deg, var(--color-primary) 0%, #1a252f 100%);
            color: white;
            min-height: 100vh;
            box-shadow: 3px 0 10px rgba(0,0,0,0.1);
        }
        
        .sidebar .nav-link {
            color: rgba(255,255,255,0.8);
            padding: 12px 20px;
            margin: 5px 0;
            border-radius: 8px;
            transition: all 0.3s;
        }
        
        .sidebar .nav-link:hover {
            background-color: rgba(255,255,255,0.1);
            color: white;
            transform: translateX(5px);
        }
        
        .sidebar .nav-link.active {
            background-color: var(--color-secondary);
            color: white;
            font-weight: bold;
        }
        
        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
            transition: transform 0.3s;
            overflow: hidden;
        }
        
        .card:hover {
            transform: translateY(-5px);
        }
        
        .card-header {
            background: linear-gradient(135deg, var(--color-secondary), #2980b9);
            color: white;
            border-bottom: none;
            padding: 15px 20px;
            font-weight: 600;
        }
        
        .stats-card {
            text-align: center;
            padding: 25px 15px;
            border-radius: 15px;
            color: white;
            margin-bottom: 20px;
        }
        
        .stats-card h4 {
            font-size: 2.5rem;
            font-weight: 700;
            margin: 10px 0;
        }
        
        .stats-card p {
            font-size: 1rem;
            opacity: 0.9;
            margin-bottom: 15px;
        }
        
        .stats-card i {
            font-size: 2.5rem;
            opacity: 0.8;
        }
        
        .card-1 { background: linear-gradient(135deg, #3498db, #2980b9); }
        .card-2 { background: linear-gradient(135deg, #2ecc71, #27ae60); }
        .card-3 { background: linear-gradient(135deg, #9b59b6, #8e44ad); }
        .card-4 { background: linear-gradient(135deg, #e74c3c, #c0392b); }
        
        .online {
            color: var(--color-success);
            font-weight: bold;
        }
        
        .offline {
            color: var(--color-danger);
            font-weight: bold;
        }
        
        .badge-entrada {
            background-color: var(--color-success);
            color: white;
        }
        
        .badge-salida {
            background-color: var(--color-warning);
            color: white;
        }
        
        .table th {
            background-color: var(--color-light);
            border-top: none;
            font-weight: 600;
            color: var(--color-dark);
        }
        
        .table-striped tbody tr:nth-of-type(odd) {
            background-color: rgba(52, 152, 219, 0.05);
        }
        
        .btn-primary {
            background: linear-gradient(135deg, var(--color-secondary), #2980b9);
            border: none;
            border-radius: 8px;
            padding: 10px 20px;
            font-weight: 600;
        }
        
        .btn-primary:hover {
            background: linear-gradient(135deg, #2980b9, var(--color-secondary));
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(52, 152, 219, 0.3);
        }
        
        .modal-content {
            border-radius: 15px;
            border: none;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
        }
        
        .modal-header {
            background: linear-gradient(135deg, var(--color-secondary), #2980b9);
            color: white;
            border-radius: 15px 15px 0 0;
            padding: 20px;
        }
        
        .modal-title {
            font-weight: 600;
        }
        
        .form-control, .form-select {
            border-radius: 8px;
            border: 2px solid #e9ecef;
            padding: 12px 15px;
            transition: all 0.3s;
        }
        
        .form-control:focus, .form-select:focus {
            border-color: var(--color-secondary);
            box-shadow: 0 0 0 0.25rem rgba(52, 152, 219, 0.25);
        }
        
        /* Animaciones */
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
        
        .fade-in {
            animation: fadeIn 0.5s ease-out;
        }
        
        /* Scroll personalizado */
        ::-webkit-scrollbar {
            width: 8px;
        }
        
        ::-webkit-scrollbar-track {
            background: #f1f1f1;
            border-radius: 10px;
        }
        
        ::-webkit-scrollbar-thumb {
            background: var(--color-secondary);
            border-radius: 10px;
        }
        
        ::-webkit-scrollbar-thumb:hover {
            background: #2980b9;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3 col-lg-2 px-0 sidebar">
                <div class="p-4">
                    <h2 class="text-center mb-4">
                        <i class="fas fa-fingerprint me-2"></i>
                        Control Asistencia
                    </h2>
                    <hr class="bg-light mb-4">
                    
                    <ul class="nav nav-pills flex-column">
                        <li class="nav-item">
                            <a href="#dashboard" class="nav-link active" data-bs-toggle="tab">
                                <i class="fas fa-tachometer-alt me-2"></i> Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="#usuarios" class="nav-link" data-bs-toggle="tab">
                                <i class="fas fa-users me-2"></i> Usuarios
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="#registros" class="nav-link" data-bs-toggle="tab">
                                <i class="fas fa-history me-2"></i> Registros
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="#configuracion" class="nav-link" data-bs-toggle="tab">
                                <i class="fas fa-cog me-2"></i> Configuración
                            </a>
                        </li>
                    </ul>
                    
                    <div class="mt-5 pt-5">
                        <div class="text-center">
                            <div class="mb-3">
                                <i class="fas fa-microchip fa-3x text-info"></i>
                            </div>
                            <h6>Sistema RFID</h6>
                            <small class="text-muted">v1.0</small>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Main Content -->
            <div class="col-md-9 col-lg-10 ms-sm-auto px-4">
                <div class="tab-content">
                    <!-- Dashboard -->
                    <div class="tab-pane fade show active fade-in" id="dashboard">
                        <div class="py-4">
                            <div class="d-flex justify-content-between align-items-center mb-4">
                                <h3 class="mb-0">
                                    <i class="fas fa-tachometer-alt text-primary me-2"></i>
                                    Dashboard
                                </h3>
                                <div class="d-flex align-items-center">
                                    <span class="me-3">
                                        <i class="fas fa-clock me-1"></i>
                                        <span id="hora-actual">--:--:--</span>
                                    </span>
                                    <span class="badge bg-secondary">
                                        <i class="fas fa-calendar me-1"></i>
                                        <span id="fecha-actual">--/--/----</span>
                                    </span>
                                </div>
                            </div>
                            
                            <!-- Cards de Estadísticas -->
                            <div class="row mb-4">
                                <div class="col-md-3">
                                    <div class="stats-card card-1">
                                        <!-- CUADRO AZUL - Registros Hoy -->
                                        <h4 id="total-hoy">0</h4>
                                        <p>Registros Hoy</p>
                                        <i class="fas fa-calendar-day"></i>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="stats-card card-2">
                                        <!-- CUADRO VERDE - Usuarios Activos -->
                                        <h4 id="total-usuarios">0</h4>
                                        <p>Usuarios Activos</p>
                                        <i class="fas fa-users"></i>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="stats-card card-3">
                                        <!-- CUADRO MORADO - Presentes Ahora -->
                                        <h4 id="presentes">0</h4>
                                        <p>Presentes Ahora</p>
                                        <i class="fas fa-user-check"></i>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="stats-card card-4">
                                        <!-- CUADRO ROJO - Estado Sistema -->
                                        <h4 id="estado-arduino">-</h4>
                                        <p>Estado Sistema</p>
                                        <i class="fas fa-microchip" id="arduino-icon"></i>
                                    </div>
                                </div>
                            </div>

                            <!-- Últimos Registros y Gráfico -->
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="card">
                                        <div class="card-header">
                                            <h5 class="mb-0">
                                                <i class="fas fa-history me-2"></i>
                                                Últimos Registros
                                            </h5>
                                        </div>
                                        <div class="card-body">
                                            <div id="ultimos-registros" style="max-height: 300px; overflow-y: auto;">
                                                <p class="text-muted text-center my-4">
                                                    <i class="fas fa-spinner fa-spin me-2"></i>
                                                    Cargando registros...
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="col-md-6">
                                    <div class="card">
                                        <div class="card-header">
                                            <h5 class="mb-0">
                                                <i class="fas fa-chart-bar me-2"></i>
                                                Registros por Hora (Hoy)
                                            </h5>
                                        </div>
                                        <div class="card-body">
                                            <canvas id="graficoHoras" width="400" height="200"></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Usuarios -->
                    <div class="tab-pane fade fade-in" id="usuarios">
                        <div class="py-4">
                            <div class="d-flex justify-content-between align-items-center mb-4">
                                <h3>
                                    <i class="fas fa-users text-primary me-2"></i>
                                    Gestión de Usuarios
                                </h3>
                                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalUsuario">
                                    <i class="fas fa-plus me-2"></i> Nuevo Usuario
                                </button>
                            </div>
                            
                            <div id="tabla-usuarios">
                                <div class="text-center my-5">
                                    <div class="spinner-border text-primary" role="status">
                                        <span class="visually-hidden">Cargando...</span>
                                    </div>
                                    <p class="mt-3 text-muted">Cargando usuarios...</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Registros -->
                    <div class="tab-pane fade fade-in" id="registros">
                        <div class="py-4">
                            <h3 class="mb-4">
                                <i class="fas fa-history text-primary me-2"></i>
                                Historial de Registros
                            </h3>
                            
                            <!-- Filtros -->
                            <div class="card mb-4">
                                <div class="card-body">
                                    <h5 class="card-title mb-3">
                                        <i class="fas fa-filter me-2"></i>Filtrar Registros
                                    </h5>
                                    <div class="row g-3">
                                        <div class="col-md-3">
                                            <label class="form-label">Fecha Inicio</label>
                                            <input type="date" id="fecha-inicio" class="form-control" value="<?php echo date('Y-m-d'); ?>">
                                        </div>
                                        <div class="col-md-3">
                                            <label class="form-label">Fecha Fin</label>
                                            <input type="date" id="fecha-fin" class="form-control" value="<?php echo date('Y-m-d'); ?>">
                                        </div>
                                        <div class="col-md-3">
                                            <label class="form-label">Tipo</label>
                                            <select id="filtro-tipo" class="form-select">
                                                <option value="">Todos</option>
                                                <option value="entrada">Entrada</option>
                                                <option value="salida">Salida</option>
                                            </select>
                                        </div>
                                        <div class="col-md-3 d-flex align-items-end">
                                            <button class="btn btn-primary w-100" onclick="cargarRegistros()">
                                                <i class="fas fa-search me-2"></i> Buscar
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Tabla de Registros -->
                            <div id="tabla-registros">
                                <div class="text-center my-5">
                                    <div class="spinner-border text-primary" role="status">
                                        <span class="visually-hidden">Cargando...</span>
                                    </div>
                                    <p class="mt-3 text-muted">Cargando registros...</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Configuración -->
                    <div class="tab-pane fade fade-in" id="configuracion">
                        <div class="py-4">
                            <h3 class="mb-4">
                                <i class="fas fa-cog text-primary me-2"></i>
                                Configuración del Sistema
                            </h3>
                            
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="card mb-4">
                                        <div class="card-header">
                                            <h5 class="mb-0">
                                                <i class="fas fa-microchip me-2"></i>
                                                Configuración Arduino
                                            </h5>
                                        </div>
                                        <div class="card-body">
                                            <div class="mb-3">
                                                <label class="form-label">Puerto COM</label>
                                                <input type="text" class="form-control" value="COM4" readonly>
                                                <small class="text-muted">Puerto serial del Arduino</small>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Baud Rate</label>
                                                <input type="text" class="form-control" value="9600" readonly>
                                            </div>
                                            <button class="btn btn-outline-primary w-100">
                                                <i class="fas fa-sync me-2"></i> Probar Conexión
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="col-md-6">
                                    <div class="card mb-4">
                                        <div class="card-header">
                                            <h5 class="mb-0">
                                                <i class="fas fa-bell me-2"></i>
                                                Configuración Notificaciones
                                            </h5>
                                        </div>
                                        <div class="card-body">
                                            <div class="form-check form-switch mb-3">
                                                <input class="form-check-input" type="checkbox" id="notificaciones-activas" checked>
                                                <label class="form-check-label" for="notificaciones-activas">
                                                    Notificaciones activas
                                                </label>
                                            </div>
                                            <div class="form-check form-switch mb-3">
                                                <input class="form-check-input" type="checkbox" id="sonido-activo" checked>
                                                <label class="form-check-label" for="sonido-activo">
                                                    Sonido de notificaciones
                                                </label>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Duración notificaciones (segundos)</label>
                                                <input type="range" class="form-range" min="3" max="10" value="5" id="duracion-notificaciones">
                                                <div class="d-flex justify-content-between">
                                                    <small>3s</small>
                                                    <small id="duracion-valor">5s</small>
                                                    <small>10s</small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="mb-0">
                                        <i class="fas fa-database me-2"></i>
                                        Base de Datos
                                    </h5>
                                </div>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <button class="btn btn-outline-success w-100 mb-3">
                                                <i class="fas fa-download me-2"></i> Exportar Datos
                                            </button>
                                        </div>
                                        <div class="col-md-6">
                                            <button class="btn btn-outline-danger w-100">
                                                <i class="fas fa-trash me-2"></i> Limpiar Historial
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Usuario -->
    <div class="modal fade" id="modalUsuario" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">
                        <i class="fas fa-user-plus me-2"></i>Nuevo Usuario
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form id="formUsuario">
                        <div class="mb-3">
                            <label class="form-label">UID de la Tarjeta</label>
                            <input type="text" class="form-control" id="uid" placeholder="Ej: A2845551" required>
                            <small class="text-muted">Pase la tarjeta por el lector para obtener el UID</small>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Nombre Completo</label>
                            <input type="text" class="form-control" id="nombre" placeholder="Ej: Juan Pérez" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Estado</label>
                            <select class="form-select" id="estado">
                                <option value="1" selected>Activo</option>
                                <option value="0">Inactivo</option>
                            </select>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        <i class="fas fa-times me-2"></i>Cancelar
                    </button>
                    <button type="button" class="btn btn-primary" onclick="guardarUsuario()">
                        <i class="fas fa-save me-2"></i>Guardar Usuario
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Sistema de Notificaciones Toast -->
    <div aria-live="polite" aria-atomic="true" class="position-fixed" style="top: 20px; right: 20px; z-index: 9999;">
        <div id="toast-container" class="toast-container">
            <!-- Las notificaciones aparecerán aquí -->
        </div>
    </div>

    <!-- Sonidos para notificaciones -->
    <audio id="sound-entrada" preload="auto">
        <source src="https://assets.mixkit.co/sfx/preview/mixkit-correct-answer-tone-2870.mp3" type="audio/mpeg">
    </audio>
    <audio id="sound-salida" preload="auto">
        <source src="https://assets.mixkit.co/sfx/preview/mixkit-retro-game-emergency-alarm-1000.mp3" type="audio/mpeg">
    </audio>

    <style>
    /* Estilos adicionales para notificaciones */
    .notificacion-toast {
        min-width: 300px;
        max-width: 350px;
        border-radius: 12px;
        box-shadow: 0 6px 20px rgba(0,0,0,0.2);
        border: none;
        margin-bottom: 15px;
        overflow: hidden;
        backdrop-filter: blur(10px);
    }

    .notificacion-toast .toast-header {
        border-bottom: 2px solid rgba(255,255,255,0.3);
        font-weight: bold;
        padding: 12px 15px;
    }

    .notificacion-toast.entrada .toast-header {
        background: linear-gradient(135deg, #27ae60, #219653);
    }

    .notificacion-toast.salida .toast-header {
        background: linear-gradient(135deg, #f39c12, #e67e22);
    }

    .notificacion-toast .toast-body {
        background: rgba(255, 255, 255, 0.95);
        color: #2c3e50;
        font-size: 0.95rem;
        padding: 15px;
    }

    .notificacion-toast .toast-body h5 {
        color: #2c3e50;
        margin-bottom: 8px;
        font-weight: 600;
    }

    .notificacion-toast .toast-body p {
        color: #34495e;
        margin-bottom: 5px;
    }

    .notificacion-toast .toast-body small {
        color: #7f8c8d;
        font-size: 0.8rem;
    }

    /* Animación de entrada */
    @keyframes slideInRight {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }

    @keyframes slideOutRight {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(100%);
            opacity: 0;
        }
    }

    .toast.show {
        animation: slideInRight 0.4s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    }

    .toast.hiding {
        animation: slideOutRight 0.4s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    }

    /* Hora y fecha en dashboard */
    #hora-actual {
        font-family: 'Courier New', monospace;
        font-weight: bold;
        color: var(--color-secondary);
    }

    #fecha-actual {
        font-family: 'Segoe UI', sans-serif;
    }
    </style>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Nuestro JavaScript -->
    <script src="js/app.js"></script>
    
    <script>
    // Actualizar hora y fecha en tiempo real
    function actualizarHora() {
        const ahora = new Date();
        const hora = ahora.toLocaleTimeString('es-ES');
        const fecha = ahora.toLocaleDateString('es-ES', {
            weekday: 'long',
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
        
        document.getElementById('hora-actual').textContent = hora;
        document.getElementById('fecha-actual').textContent = fecha.charAt(0).toUpperCase() + fecha.slice(1);
    }
    
    // Inicializar hora
    actualizarHora();
    setInterval(actualizarHora, 1000);
    
    // Configuración de notificaciones
    document.getElementById('duracion-notificaciones').addEventListener('input', function() {
        document.getElementById('duracion-valor').textContent = this.value + 's';
    });
    
    // Efectos visuales
    document.querySelectorAll('.nav-link').forEach(link => {
        link.addEventListener('click', function() {
            document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
            this.classList.add('active');
        });
    });
    
    // Efecto de carga
    document.addEventListener('DOMContentLoaded', function() {
        document.body.style.opacity = '0';
        document.body.style.transition = 'opacity 0.5s';
        
        setTimeout(() => {
            document.body.style.opacity = '1';
        }, 100);
        
        // Depuración: Verificar elementos
        console.log('🔍 Verificando elementos del dashboard:');
        console.log('1. total-hoy:', document.getElementById('total-hoy'));
        console.log('2. total-usuarios:', document.getElementById('total-usuarios'));
        console.log('3. presentes:', document.getElementById('presentes'));
        console.log('4. estado-arduino:', document.getElementById('estado-arduino'));
        console.log('5. arduino-icon:', document.getElementById('arduino-icon'));
    });
    </script>
</body>
</html>