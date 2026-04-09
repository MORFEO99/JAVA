import os
import tkinter as tk
from tkinter import messagebox, simpledialog, ttk

# -------------------------------
# Excepción personalizada
# -------------------------------
class TareaNoEncontradaError(Exception):
    def __init__(self, mensaje="Tarea no encontrada"):
        self.mensaje = mensaje
        super().__init__(self.mensaje)

# -------------------------------
# Clase Tarea (entidad)
# -------------------------------
class Tarea:
    def __init__(self, id: int, nombre: str, completada: bool = False):
        self.id = id
        self.nombre = nombre
        self.completada = completada

    def __str__(self):
        estado = "✓" if self.completada else "✗"
        return f"{estado}  [{self.id}] {self.nombre}"

# -------------------------------
# Gestor de tareas (persistencia y lógica)
# -------------------------------
class GestorTareas:
    def __init__(self, archivo="tareas.txt"):
        self.archivo = archivo
        self.tareas = self._cargar_tareas()

    def _cargar_tareas(self):
        tareas = []
        try:
            with open(self.archivo, "r", encoding="utf-8") as f:
                for linea in f:
                    linea = linea.strip()
                    if not linea:
                        continue
                    partes = linea.split("|")
                    if len(partes) != 3:
                        continue
                    id_str, nombre, completada_str = partes
                    try:
                        id_tarea = int(id_str)
                        completada = completada_str.lower() == "true"
                        tareas.append(Tarea(id_tarea, nombre, completada))
                    except ValueError:
                        continue
        except (FileNotFoundError, PermissionError):
            pass
        return tareas

    def _guardar_tareas(self):
        try:
            with open(self.archivo, "w", encoding="utf-8") as f:
                for tarea in self.tareas:
                    f.write(f"{tarea.id}|{tarea.nombre}|{tarea.completada}\n")
        except PermissionError:
            messagebox.showerror("Error", "No se pudo guardar el archivo. Permisos insuficientes.")

    def _obtener_nuevo_id(self):
        if not self.tareas:
            return 1
        return max(t.id for t in self.tareas) + 1

    def agregar_tarea(self, nombre: str) -> Tarea:
        if not nombre or not nombre.strip():
            raise ValueError("El nombre no puede estar vacío")
        nuevo_id = self._obtener_nuevo_id()
        nueva_tarea = Tarea(nuevo_id, nombre.strip(), False)
        self.tareas.append(nueva_tarea)
        self._guardar_tareas()
        return nueva_tarea

    def actualizar_tarea(self, id_tarea: int, completada: bool = None, nombre: str = None) -> Tarea:
        for tarea in self.tareas:
            if tarea.id == id_tarea:
                if completada is not None:
                    tarea.completada = completada
                if nombre is not None:
                    tarea.nombre = nombre.strip()
                self._guardar_tareas()
                return tarea
        raise TareaNoEncontradaError(f"No existe tarea con ID {id_tarea}")

    def eliminar_tarea(self, id_tarea: int) -> bool:
        for i, tarea in enumerate(self.tareas):
            if tarea.id == id_tarea:
                del self.tareas[i]
                self._guardar_tareas()
                return True
        return False

    def listar_tareas(self, filtro=None):
        if filtro is None:
            return self.tareas[:]
        return [t for t in self.tareas if t.completada == filtro]

    def buscar_por_nombre(self, texto: str):
        if not texto:
            return []
        texto_lower = texto.lower()
        return [t for t in self.tareas if texto_lower in t.nombre.lower()]

# -------------------------------
# Interfaz gráfica mejorada
# -------------------------------
class AppTareas:
    COLOR_FONDO = "#f0f0f0"
    COLOR_BOTON_PRIMARIO = "#4CAF50"   # Verde
    COLOR_BOTON_SECUNDARIO = "#2196F3"  # Azul
    COLOR_BOTON_PELIGRO = "#f44336"     # Rojo
    COLOR_TEXTO = "#333333"

    def __init__(self, root):
        self.root = root
        self.root.title("Gestor de Tareas - Versión Mejorada")
        self.root.geometry("700x550")
        self.root.resizable(True, True)
        self.root.configure(bg=self.COLOR_FONDO)

        self.gestor = GestorTareas()

        # Variables
        self.filtro_actual = tk.StringVar(value="todas")
        self.busqueda_texto = tk.StringVar()

        # Estilo de la lista usando ttk para colores alternados
        self._configurar_estilos()
        self._crear_widgets()
        self._actualizar_lista()

    def _configurar_estilos(self):
        style = ttk.Style()
        style.theme_use("clam")
        style.configure("Treeview", 
                        background="#ffffff",
                        foreground=self.COLOR_TEXTO,
                        rowheight=25,
                        font=("Segoe UI", 10))
        style.configure("Treeview.Heading", 
                        font=("Segoe UI", 10, "bold"),
                        background="#d9d9d9")
        style.map("Treeview",
                  background=[('selected', '#347083')],
                  foreground=[('selected', 'white')])

    def _crear_widgets(self):
        # Marco superior (agregar tarea)
        frame_superior = tk.Frame(self.root, bg=self.COLOR_FONDO)
        frame_superior.pack(pady=10, padx=10, fill=tk.X)

        tk.Label(frame_superior, text="➕ Nueva tarea:", bg=self.COLOR_FONDO, font=("Segoe UI", 10)).pack(side=tk.LEFT, padx=5)
        self.entry_nueva = tk.Entry(frame_superior, width=45, font=("Segoe UI", 10))
        self.entry_nueva.pack(side=tk.LEFT, padx=5, fill=tk.X, expand=True)
        self.entry_nueva.bind("<Return>", lambda e: self.agregar_tarea())

        btn_agregar = tk.Button(frame_superior, text="Agregar", command=self.agregar_tarea,
                                bg=self.COLOR_BOTON_PRIMARIO, fg="white", font=("Segoe UI", 9, "bold"),
                                padx=10, pady=2)
        btn_agregar.pack(side=tk.LEFT, padx=5)

        # Marco de búsqueda
        frame_busqueda = tk.Frame(self.root, bg=self.COLOR_FONDO)
        frame_busqueda.pack(pady=5, padx=10, fill=tk.X)

        tk.Label(frame_busqueda, text="🔍 Buscar:", bg=self.COLOR_FONDO, font=("Segoe UI", 10)).pack(side=tk.LEFT, padx=5)
        self.entry_buscar = tk.Entry(frame_busqueda, textvariable=self.busqueda_texto, width=35, font=("Segoe UI", 10))
        self.entry_buscar.pack(side=tk.LEFT, padx=5, fill=tk.X, expand=True)
        self.entry_buscar.bind("<KeyRelease>", lambda e: self._actualizar_lista())

        btn_limpiar = tk.Button(frame_busqueda, text="✖ Limpiar", command=self._limpiar_busqueda,
                                bg=self.COLOR_BOTON_SECUNDARIO, fg="white", font=("Segoe UI", 9))
        btn_limpiar.pack(side=tk.LEFT, padx=5)

        # Marco de filtros
        frame_filtros = tk.Frame(self.root, bg=self.COLOR_FONDO)
        frame_filtros.pack(pady=5, padx=10, fill=tk.X)

        tk.Label(frame_filtros, text="📋 Filtrar:", bg=self.COLOR_FONDO, font=("Segoe UI", 10)).pack(side=tk.LEFT, padx=5)
        rb_todas = tk.Radiobutton(frame_filtros, text="Todas", variable=self.filtro_actual, value="todas",
                                  command=self._actualizar_lista, bg=self.COLOR_FONDO, font=("Segoe UI", 9))
        rb_todas.pack(side=tk.LEFT, padx=5)
        rb_pendientes = tk.Radiobutton(frame_filtros, text="Pendientes", variable=self.filtro_actual, value="pendientes",
                                       command=self._actualizar_lista, bg=self.COLOR_FONDO, font=("Segoe UI", 9))
        rb_pendientes.pack(side=tk.LEFT, padx=5)
        rb_completadas = tk.Radiobutton(frame_filtros, text="Completadas", variable=self.filtro_actual, value="completadas",
                                        command=self._actualizar_lista, bg=self.COLOR_FONDO, font=("Segoe UI", 9))
        rb_completadas.pack(side=tk.LEFT, padx=5)

        # Lista de tareas con Treeview (mejor visualización)
        frame_lista = tk.Frame(self.root, bg=self.COLOR_FONDO)
        frame_lista.pack(pady=10, padx=10, fill=tk.BOTH, expand=True)

        scrollbar = tk.Scrollbar(frame_lista)
        scrollbar.pack(side=tk.RIGHT, fill=tk.Y)

        self.tree = ttk.Treeview(frame_lista, columns=("estado", "id", "nombre"), show="headings", yscrollcommand=scrollbar.set)
        self.tree.heading("estado", text="Estado")
        self.tree.heading("id", text="ID")
        self.tree.heading("nombre", text="Nombre de la tarea")
        self.tree.column("estado", width=60, anchor="center")
        self.tree.column("id", width=50, anchor="center")
        self.tree.column("nombre", width=450, anchor="w")
        self.tree.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)
        scrollbar.config(command=self.tree.yview)

        # Eventos: doble clic para editar, clic derecho para menú contextual
        self.tree.bind("<Double-1>", lambda e: self.editar_tarea())
        self.tree.bind("<Button-3>", self._mostrar_menu_contextual)

        # Marco de botones de acción
        frame_botones = tk.Frame(self.root, bg=self.COLOR_FONDO)
        frame_botones.pack(pady=10, padx=10, fill=tk.X)

        btn_completar = tk.Button(frame_botones, text="✔ Marcar como completada", command=self.marcar_completada,
                                  bg=self.COLOR_BOTON_PRIMARIO, fg="white", font=("Segoe UI", 9, "bold"), padx=8)
        btn_completar.pack(side=tk.LEFT, padx=5)

        btn_editar = tk.Button(frame_botones, text="✏ Editar nombre", command=self.editar_tarea,
                               bg=self.COLOR_BOTON_SECUNDARIO, fg="white", font=("Segoe UI", 9, "bold"), padx=8)
        btn_editar.pack(side=tk.LEFT, padx=5)

        btn_eliminar = tk.Button(frame_botones, text="🗑 Eliminar tarea", command=self.eliminar_tarea,
                                 bg=self.COLOR_BOTON_PELIGRO, fg="white", font=("Segoe UI", 9, "bold"), padx=8)
        btn_eliminar.pack(side=tk.LEFT, padx=5)

        btn_refrescar = tk.Button(frame_botones, text="🔄 Refrescar", command=self._actualizar_lista,
                                  bg="#999999", fg="white", font=("Segoe UI", 9))
        btn_refrescar.pack(side=tk.RIGHT, padx=5)

    def _mostrar_menu_contextual(self, event):
        # Seleccionar el elemento bajo el cursor
        item = self.tree.identify_row(event.y)
        if item:
            self.tree.selection_set(item)
            menu = tk.Menu(self.root, tearoff=0)
            menu.add_command(label="Editar nombre", command=self.editar_tarea)
            menu.add_command(label="Marcar completada", command=self.marcar_completada)
            menu.add_command(label="Eliminar", command=self.eliminar_tarea)
            menu.post(event.x_root, event.y_root)

    def _limpiar_busqueda(self):
        self.busqueda_texto.set("")
        self._actualizar_lista()

    def _obtener_lista_filtrada(self):
        texto_buscar = self.busqueda_texto.get().strip()
        if texto_buscar:
            tareas_base = self.gestor.buscar_por_nombre(texto_buscar)
        else:
            tareas_base = self.gestor.listar_tareas()

        filtro = self.filtro_actual.get()
        if filtro == "pendientes":
            return [t for t in tareas_base if not t.completada]
        elif filtro == "completadas":
            return [t for t in tareas_base if t.completada]
        else:
            return tareas_base

    def _actualizar_lista(self):
        for item in self.tree.get_children():
            self.tree.delete(item)
        tareas = self._obtener_lista_filtrada()
        for tarea in tareas:
            estado = "✓" if tarea.completada else "✗"
            # Alternar colores de fila (Treeview no lo hace automático, pero se puede configurar tag)
            self.tree.insert("", tk.END, values=(estado, tarea.id, tarea.nombre), tags=('even' if len(self.tree.get_children()) % 2 == 0 else 'odd'))
        self.tree.tag_configure('odd', background='#f9f9f9')
        self.tree.tag_configure('even', background='#ffffff')
        self.tareas_actuales = tareas

    def _obtener_tarea_seleccionada(self):
        seleccion = self.tree.selection()
        if not seleccion:
            messagebox.showwarning("Sin selección", "Por favor seleccione una tarea.")
            return None
        item = seleccion[0]
        valores = self.tree.item(item, "values")
        if not valores:
            return None
        # valores[1] es el ID (string)
        try:
            id_tarea = int(valores[1])
            for t in self.tareas_actuales:
                if t.id == id_tarea:
                    return t
        except ValueError:
            return None
        return None

    def agregar_tarea(self):
        nombre = self.entry_nueva.get().strip()
        if not nombre:
            messagebox.showwarning("Nombre vacío", "Debe ingresar un nombre para la tarea.")
            return
        try:
            self.gestor.agregar_tarea(nombre)
            self.entry_nueva.delete(0, tk.END)
            self._actualizar_lista()
        except Exception as e:
            messagebox.showerror("Error", f"No se pudo agregar la tarea:\n{e}")

    def marcar_completada(self):
        tarea = self._obtener_tarea_seleccionada()
        if not tarea:
            return
        if tarea.completada:
            messagebox.showinfo("Info", "La tarea ya está marcada como completada.")
            return
        try:
            self.gestor.actualizar_tarea(tarea.id, completada=True)
            self._actualizar_lista()
        except TareaNoEncontradaError:
            messagebox.showerror("Error", "La tarea ya no existe.")
        except Exception as e:
            messagebox.showerror("Error", f"No se pudo actualizar:\n{e}")

    def eliminar_tarea(self):
        tarea = self._obtener_tarea_seleccionada()
        if not tarea:
            return
        if messagebox.askyesno("Confirmar", f"¿Eliminar la tarea '{tarea.nombre}'?"):
            try:
                self.gestor.eliminar_tarea(tarea.id)
                self._actualizar_lista()
            except Exception as e:
                messagebox.showerror("Error", f"No se pudo eliminar:\n{e}")

    def editar_tarea(self):
        tarea = self._obtener_tarea_seleccionada()
        if not tarea:
            return
        nuevo_nombre = simpledialog.askstring("Editar tarea", "Nuevo nombre:", initialvalue=tarea.nombre)
        if nuevo_nombre and nuevo_nombre.strip():
            try:
                self.gestor.actualizar_tarea(tarea.id, nombre=nuevo_nombre.strip())
                self._actualizar_lista()
            except TareaNoEncontradaError:
                messagebox.showerror("Error", "La tarea ya no existe.")
            except Exception as e:
                messagebox.showerror("Error", f"No se pudo editar:\n{e}")

# -------------------------------
# Punto de entrada
# -------------------------------
if __name__ == "__main__":
    root = tk.Tk()
    app = AppTareas(root)
    root.mainloop()