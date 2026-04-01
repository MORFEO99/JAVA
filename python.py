import tkinter as tk
from tkinter import messagebox
import math

class CalculadoraPro:
    def __init__(self):
        self.ventana = tk.Tk()
        self.ventana.title("Calculadora Blue")
        self.ventana.geometry("350x500")
        self.ventana.configure(bg="#0F172A")  # azul oscuro

        self.expresion = ""

        self.crear_display()
        self.crear_botones()
        self.bind_teclado()

    def crear_display(self):
        self.display = tk.Entry(
            self.ventana,
            font=("Consolas", 26),
            bg="#020617",
            fg="#E2E8F0",
            justify="right",
            relief=tk.FLAT
        )
        self.display.pack(fill="both", padx=10, pady=15, ipady=15)

    def actualizar(self, valor):
        self.expresion += str(valor)
        self.display.delete(0, tk.END)
        self.display.insert(0, self.expresion)

    def limpiar(self):
        self.expresion = ""
        self.display.delete(0, tk.END)

    def borrar(self):
        self.expresion = self.expresion[:-1]
        self.display.delete(0, tk.END)
        self.display.insert(0, self.expresion)

    def calcular(self):
        try:
            resultado = eval(self.expresion)
            self.expresion = str(round(resultado, 8))
            self.display.delete(0, tk.END)
            self.display.insert(0, self.expresion)
        except ZeroDivisionError:
            messagebox.showerror("Error", "No se puede dividir entre cero")
            self.limpiar()
        except:
            messagebox.showerror("Error", "Expresión inválida")
            self.limpiar()

    def raiz(self):
        try:
            valor = float(self.display.get())
            if valor < 0:
                raise ValueError
            self.expresion = str(math.sqrt(valor))
            self.actualizar_display()
        except:
            messagebox.showerror("Error", "Raíz inválida")

    def actualizar_display(self):
        self.display.delete(0, tk.END)
        self.display.insert(0, self.expresion)

    def crear_botones(self):
        frame = tk.Frame(self.ventana, bg="#0F172A")
        frame.pack(expand=True, fill="both")

        botones = [
            ("C", 1, 0, self.limpiar), ("←", 1, 1, self.borrar), ("√", 1, 2, self.raiz), ("/", 1, 3, lambda: self.actualizar("/")),
            ("7", 2, 0, lambda: self.actualizar("7")), ("8", 2, 1, lambda: self.actualizar("8")), ("9", 2, 2, lambda: self.actualizar("9")), ("*", 2, 3, lambda: self.actualizar("*")),
            ("4", 3, 0, lambda: self.actualizar("4")), ("5", 3, 1, lambda: self.actualizar("5")), ("6", 3, 2, lambda: self.actualizar("6")), ("-", 3, 3, lambda: self.actualizar("-")),
            ("1", 4, 0, lambda: self.actualizar("1")), ("2", 4, 1, lambda: self.actualizar("2")), ("3", 4, 2, lambda: self.actualizar("3")), ("+", 4, 3, lambda: self.actualizar("+")),
            ("0", 5, 0, lambda: self.actualizar("0")), (".", 5, 1, lambda: self.actualizar(".")), ("=", 5, 2, self.calcular),
        ]

        for (text, row, col, cmd) in botones:
            # 🎨 Colores personalizados
            if text in ["+", "-", "*", "/", "="]:
                bg = "#2563EB"  # azul brillante
            elif text in ["C", "←", "√"]:
                bg = "#475569"  # gris azulado
            else:
                bg = "#1E293B"  # botones normales

            btn = tk.Button(
                frame,
                text=text,
                font=("Arial", 16, "bold"),
                bg=bg,
                fg="white",
                activebackground="#3B82F6",
                relief=tk.FLAT,
                command=cmd
            )
            btn.grid(row=row, column=col, sticky="nsew", padx=5, pady=5)

        for i in range(6):
            frame.rowconfigure(i, weight=1)
        for j in range(4):
            frame.columnconfigure(j, weight=1)

    def bind_teclado(self):
        self.ventana.bind("<Key>", self.tecla)

    def tecla(self, event):
        tecla = event.char

        if tecla in "0123456789+-*/.":
            self.actualizar(tecla)
        elif event.keysym == "Return":
            self.calcular()
        elif event.keysym == "BackSpace":
            self.borrar()
        elif event.keysym == "Escape":
            self.limpiar()

    def ejecutar(self):
        self.ventana.mainloop()


if __name__ == "__main__":
    app = CalculadoraPro()
    app.ejecutar()