class ControlAsistencia {
    constructor() {
        this.apiBase = 'api/';
        this.ultimoRegistroId = 0;
        this.init();
    }

    init() {
        this.cargarDashboard();
        // Cargar usuarios y registros después
        setTimeout(() => this.cargarUsuarios(), 1000);
        setTimeout(() => this.cargarRegistros(), 2000);
        
        // Actualizar dashboard más frecuente para notificaciones
        setInterval(() => this.cargarDashboard(), 5000); // Cada 5 segundos
        
        // Inicializar sonidos
        this.sonidoEntrada = document.getElementById('sound-entrada');
        this.sonidoSalida = document.getElementById('sound-salida');
        
        console.log('✅ Sistema de notificaciones activado');
    }

    async cargarDashboard() {
        try {
            const response = await fetch(this.apiBase + 'dashboard.php?t=' + Date.now());
            const data = await response.json();
            
            if (data.success) {
                this.actualizarDashboard(data);
                this.verificarNuevosRegistros(data);
            }
        } catch (error) {
            console.log('Dashboard no disponible');
        }
    }

    actualizarDashboard(data) {
        // Actualizar estadísticas básicas
        if (document.getElementById('total-hoy')) {
            document.getElementById('total-hoy').textContent = data.total_hoy;
        }
        if (document.getElementById('total-usuarios')) {
            document.getElementById('total-usuarios').textContent = data.total_usuarios;
        }
        if (document.getElementById('presentes')) {
            document.getElementById('presentes').textContent = data.presentes;
        }
        
        // Estado Arduino
        const estadoArduino = document.getElementById('estado-arduino');
        const arduinoIcon = document.getElementById('arduino-icon');
        if (estadoArduino && arduinoIcon) {
            if (data.arduino_online) {
                estadoArduino.textContent = 'Conectado';
                estadoArduino.className = 'online';
                arduinoIcon.className = 'fas fa-microchip fa-2x online';
            } else {
                estadoArduino.textContent = 'Desconectado';
                estadoArduino.className = 'offline';
                arduinoIcon.className = 'fas fa-microchip fa-2x offline';
            }
        }
        
        // Últimos registros
        if (document.getElementById('ultimos-registros')) {
            this.mostrarUltimosRegistros(data.ultimos_registros);
        }
    }

    verificarNuevosRegistros(data) {
        // Verificar si hay nuevos registros para mostrar notificación
        if (data.ultimos_registros && data.ultimos_registros.length > 0) {
            const ultimoRegistro = data.ultimos_registros[0];
            
            // Si es el primer registro o es un nuevo registro
            if (this.ultimoRegistroId === 0) {
                // Inicializar con el primer registro
                this.ultimoRegistroId = ultimoRegistro.id;
            } else if (ultimoRegistro.id > this.ultimoRegistroId) {
                // ¡NUEVO REGISTRO DETECTADO!
                this.mostrarNotificacion(ultimoRegistro);
                this.ultimoRegistroId = ultimoRegistro.id;
            }
        }
    }

    mostrarNotificacion(registro) {
        const container = document.getElementById('toast-container');
        if (!container) return;
        
        const tipo = registro.tipo;
        const icono = tipo === 'entrada' ? '✅' : '🚪';
        const titulo = tipo === 'entrada' ? 'ENTRADA REGISTRADA' : 'SALIDA REGISTRADA';
        const claseTipo = tipo === 'entrada' ? 'entrada' : 'salida';
        const fechaFormateada = this.formatearFecha(registro.fecha_hora);
        
        // Crear ID único para el toast
        const toastId = 'toast-' + Date.now();
        
        // Crear HTML del toast
        const toastHTML = `
            <div id="${toastId}" class="toast notificacion-toast ${claseTipo}" role="alert" aria-live="assertive" aria-atomic="true" data-bs-delay="5000">
                <div class="toast-header">
                    <strong class="me-auto">${icono} ${titulo}</strong>
                    <small class="text-white">${this.getTiempoRelativo(registro.fecha_hora)}</small>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast"></button>
                </div>
                <div class="toast-body">
                    <h5>👤 ${registro.nombre}</h5>
                    <p class="mb-1">📅 ${fechaFormateada}</p>
                    <small class="d-block">🔑 UID: ${registro.uid}</small>
                    <small class="text-muted">ID: ${registro.id}</small>
                </div>
            </div>
        `;
        
        // Agregar al contenedor
        container.insertAdjacentHTML('afterbegin', toastHTML);
        
        // Inicializar y mostrar el toast de Bootstrap
        const toastElement = document.getElementById(toastId);
        const toast = new bootstrap.Toast(toastElement);
        toast.show();
        
        // Reproducir sonido según el tipo
        this.reproducirSonido(tipo);
        
        // Remover del DOM después de ocultarse
        toastElement.addEventListener('hidden.bs.toast', () => {
            toastElement.remove();
        });
        
        console.log(`🔔 Notificación mostrada: ${registro.nombre} - ${tipo}`);
    }

    reproducirSonido(tipo) {
        try {
            if (tipo === 'entrada' && this.sonidoEntrada) {
                this.sonidoEntrada.currentTime = 0;
                this.sonidoEntrada.play().catch(e => console.log('Error reproduciendo sonido:', e));
            } else if (tipo === 'salida' && this.sonidoSalida) {
                this.sonidoSalida.currentTime = 0;
                this.sonidoSalida.play().catch(e => console.log('Error reproduciendo sonido:', e));
            }
        } catch (error) {
            console.log('Error con sonido:', error);
        }
    }

    formatearFecha(fechaString) {
        const fecha = new Date(fechaString);
        return fecha.toLocaleTimeString('es-ES', {
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit',
            hour12: false
        }) + ' ' + fecha.toLocaleDateString('es-ES');
    }

    getTiempoRelativo(fechaString) {
        const ahora = new Date();
        const fecha = new Date(fechaString);
        const diffMs = ahora - fecha;
        const diffSec = Math.floor(diffMs / 1000);
        
        if (diffSec < 10) return 'Ahora mismo';
        if (diffSec < 60) return `Hace ${diffSec} segundos`;
        if (diffSec < 3600) return `Hace ${Math.floor(diffSec / 60)} minutos`;
        return `Hace ${Math.floor(diffSec / 3600)} horas`;
    }

    mostrarUltimosRegistros(registros) {
        const container = document.getElementById('ultimos-registros');
        if (!container) return;
        
        let html = '';
        
        if (registros.length === 0) {
            html = '<p class="text-muted">No hay registros hoy</p>';
        } else {
            registros.forEach(reg => {
                const tipoClass = reg.tipo === 'entrada' ? 'text-success' : 'text-danger';
                const icon = reg.tipo === 'entrada' ? 'fa-sign-in-alt' : 'fa-sign-out-alt';
                const tipoTexto = reg.tipo === 'entrada' ? 'ENTRADA' : 'SALIDA';
                const fecha = this.formatearFecha(reg.fecha_hora);
                
                html += `
                    <div class="d-flex justify-content-between align-items-center border-bottom py-2">
                        <div>
                            <i class="fas ${icon} ${tipoClass} me-2"></i>
                            <strong>${reg.nombre}</strong>
                            <small class="text-muted ms-2">(${tipoTexto})</small>
                        </div>
                        <div>
                            <small class="text-muted">${fecha}</small>
                        </div>
                    </div>
                `;
            });
        }
        
        container.innerHTML = html;
    }

    async cargarUsuarios() {
        try {
            const response = await fetch(this.apiBase + 'usuarios.php');
            const data = await response.json();
            
            if (data.success) {
                this.mostrarUsuarios(data.usuarios);
            }
        } catch (error) {
            console.error('Error cargando usuarios:', error);
        }
    }

    mostrarUsuarios(usuarios) {
        const container = document.getElementById('tabla-usuarios');
        if (!container) return;
        
        let html = `
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>UID</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
        `;
        
        if (usuarios.length === 0) {
            html += '<tr><td colspan="5" class="text-center">No hay usuarios registrados</td></tr>';
        } else {
            usuarios.forEach(user => {
                const estado = user.activo == 1 ? 
                    '<span class="badge bg-success">Activo</span>' : 
                    '<span class="badge bg-danger">Inactivo</span>';
                
                html += `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.nombre}</td>
                        <td><code>${user.uid}</code></td>
                        <td>${estado}</td>
                        <td>
                            <button class="btn btn-sm btn-warning me-2" onclick="app.editarUsuario(${user.id})">
                                <i class="fas fa-edit"></i> Editar
                            </button>
                            <button class="btn btn-sm btn-danger" onclick="app.eliminarUsuario(${user.id}, '${this.escapeHtml(user.nombre)}')">
                                <i class="fas fa-trash"></i> Eliminar
                            </button>
                        </td>
                    </tr>
                `;
            });
        }
        
        html += '</tbody></table>';
        container.innerHTML = html;
    }

    async cargarRegistros(fechaInicio = null, fechaFin = null) {
        try {
            let url = this.apiBase + 'registros.php';
            if (fechaInicio && fechaFin) {
                url += `?fecha_inicio=${fechaInicio}&fecha_fin=${fechaFin}`;
            }
            
            const response = await fetch(url);
            const data = await response.json();
            
            if (data.success) {
                this.mostrarRegistros(data.registros);
            }
        } catch (error) {
            console.error('Error cargando registros:', error);
        }
    }

    mostrarRegistros(registros) {
        const container = document.getElementById('tabla-registros');
        if (!container) return;
        
        let html = `
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Fecha/Hora</th>
                        <th>Usuario</th>
                        <th>Tipo</th>
                        <th>UID</th>
                    </tr>
                </thead>
                <tbody>
        `;
        
        if (registros.length === 0) {
            html += '<tr><td colspan="4" class="text-center">No hay registros</td></tr>';
        } else {
            registros.forEach(reg => {
                const tipoText = reg.tipo === 'entrada' ? 
                    '<span class="badge bg-success">Entrada</span>' : 
                    '<span class="badge bg-danger">Salida</span>';
                const fecha = this.formatearFecha(reg.fecha_hora);
                
                html += `
                    <tr>
                        <td>${fecha}</td>
                        <td>${reg.nombre}</td>
                        <td>${tipoText}</td>
                        <td><code>${reg.uid}</code></td>
                    </tr>
                `;
            });
        }
        
        html += '</tbody></table>';
        container.innerHTML = html;
    }

    // Función guardarUsuario actualizada
    async guardarUsuario(uid, nombre, activo = 1) {
        try {
            const response = await fetch(this.apiBase + 'usuarios.php', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    action: 'create',
                    uid: uid,
                    nombre: nombre,
                    activo: parseInt(activo)
                })
            });
            
            const data = await response.json();
            
            if (data.success) {
                alert('Usuario creado exitosamente');
                this.cargarUsuarios();
                
                // Cerrar modal
                var modal = bootstrap.Modal.getInstance(document.getElementById('modalUsuario'));
                modal.hide();
                
                // Limpiar formulario
                document.getElementById('uid').value = '';
                document.getElementById('nombre').value = '';
                
                // Actualizar dashboard
                this.cargarDashboard();
            } else {
                alert('Error: ' + data.message);
            }
        } catch (error) {
            console.error('Error guardando usuario:', error);
            alert('Error al guardar usuario');
        }
    }

    // Funciones de edición y eliminación
    async editarUsuario(id) {
        try {
            // Obtener datos del usuario
            const response = await fetch(this.apiBase + 'usuarios.php');
            const data = await response.json();
            
            if (data.success) {
                const usuario = data.usuarios.find(u => u.id == id);
                if (usuario) {
                    this.mostrarModalEditar(usuario);
                }
            }
        } catch (error) {
            console.error('Error obteniendo usuario:', error);
            alert('Error al obtener datos del usuario');
        }
    }

    mostrarModalEditar(usuario) {
        // Crear modal de edición dinámicamente
        const modalHTML = `
            <div class="modal fade" id="modalEditarUsuario" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">
                                <i class="fas fa-edit me-2"></i>Editar Usuario
                            </h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <form id="formEditarUsuario">
                                <input type="hidden" id="editar-id" value="${usuario.id}">
                                <div class="mb-3">
                                    <label class="form-label">UID de la Tarjeta</label>
                                    <input type="text" class="form-control" id="editar-uid" value="${usuario.uid}" readonly>
                                    <small class="text-muted">El UID no se puede modificar</small>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Nombre Completo</label>
                                    <input type="text" class="form-control" id="editar-nombre" value="${usuario.nombre}" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Estado</label>
                                    <select class="form-select" id="editar-activo">
                                        <option value="1" ${usuario.activo == 1 ? 'selected' : ''}>Activo</option>
                                        <option value="0" ${usuario.activo == 0 ? 'selected' : ''}>Inactivo</option>
                                    </select>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                <i class="fas fa-times me-2"></i>Cancelar
                            </button>
                            <button type="button" class="btn btn-primary" onclick="app.guardarCambiosUsuario()">
                                <i class="fas fa-save me-2"></i>Guardar Cambios
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;
        
        // Remover modal anterior si existe
        const modalExistente = document.getElementById('modalEditarUsuario');
        if (modalExistente) {
            modalExistente.remove();
        }
        
        // Agregar nuevo modal al body
        document.body.insertAdjacentHTML('beforeend', modalHTML);
        
        // Mostrar modal
        const modal = new bootstrap.Modal(document.getElementById('modalEditarUsuario'));
        modal.show();
    }

    async guardarCambiosUsuario() {
        const id = document.getElementById('editar-id').value;
        const nombre = document.getElementById('editar-nombre').value.trim();
        const activo = document.getElementById('editar-activo').value;
        
        if (!nombre) {
            alert('Por favor ingrese un nombre');
            return;
        }
        
        try {
            const response = await fetch(this.apiBase + 'usuarios.php', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    action: 'update',
                    id: parseInt(id),
                    nombre: nombre,
                    activo: parseInt(activo)
                })
            });
            
            const data = await response.json();
            
            if (data.success) {
                alert('Usuario actualizado exitosamente');
                
                // Cerrar modal
                const modal = bootstrap.Modal.getInstance(document.getElementById('modalEditarUsuario'));
                modal.hide();
                
                // Actualizar lista de usuarios
                this.cargarUsuarios();
                
                // Actualizar dashboard (por si cambió estado activo)
                this.cargarDashboard();
            } else {
                alert('Error: ' + data.message);
            }
        } catch (error) {
            console.error('Error actualizando usuario:', error);
            alert('Error al actualizar usuario');
        }
    }

    async eliminarUsuario(id, nombre) {
        if (!confirm(`¿Está seguro de desactivar al usuario "${nombre}"?\n\nEl usuario podrá ser reactivado después.`)) {
            return;
        }
        
        try {
            const response = await fetch(this.apiBase + 'usuarios.php', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    action: 'delete',
                    id: id
                })
            });
            
            const data = await response.json();
            
            if (data.success) {
                alert('Usuario desactivado exitosamente');
                
                // Actualizar lista de usuarios
                this.cargarUsuarios();
                
                // Actualizar dashboard
                this.cargarDashboard();
            } else {
                alert('Error: ' + data.message);
            }
        } catch (error) {
            console.error('Error eliminando usuario:', error);
            alert('Error al desactivar usuario');
        }
    }

    // Función auxiliar para escapar HTML
    escapeHtml(text) {
        const map = {
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#039;'
        };
        return text.replace(/[&<>"']/g, function(m) { return map[m]; });
    }
}

// Funciones globales actualizadas
function guardarUsuario() {
    const uid = document.getElementById('uid').value.trim();
    const nombre = document.getElementById('nombre').value.trim();
    const activo = document.getElementById('estado') ? document.getElementById('estado').value : '1';
    
    if (uid && nombre) {
        app.guardarUsuario(uid, nombre, activo);
    } else {
        alert('Por favor complete todos los campos');
    }
}

function cargarRegistros() {
    const fechaInicio = document.getElementById('fecha-inicio').value;
    const fechaFin = document.getElementById('fecha-fin').value;
    app.cargarRegistros(fechaInicio, fechaFin);
}

// Inicializar aplicación
document.addEventListener('DOMContentLoaded', function() {
    window.app = new ControlAsistencia();
    console.log('✅ Sistema de control de asistencia con notificaciones iniciado');
});