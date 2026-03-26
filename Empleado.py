class Empleado:
    """Clase base que representa a cualquier empleado"""

    def __init__(self, nombre, edad, salario_base, años_experiencia=0):
        # Atributos públicos
        self.nombre = nombre
        self.edad = edad
        self.años_experiencia = años_experiencia
        # Atributo protegido (convención: _ significa "no tocar directamente")
        self._salario_base = salario_base

    def calcular_salario(self):
        """Método base que será sobrescrito por las clases hijas"""
        return self._salario_base

    def mostrar_info(self):
        """Muestra la información básica del empleado"""
        return f"Empleado: {self.nombre}, Edad: {self.edad}, Experiencia: {self.años_experiencia} años, Salario: ${self.calcular_salario():.2f}"

    def aumentar_salario(self, porcentaje):
        """Aumenta el salario base en un porcentaje dado"""
        incremento = self._salario_base * (porcentaje / 100)
        self._salario_base += incremento
        return f"✓ Salario de {self.nombre} aumentado en {porcentaje}%. Nuevo salario base: ${self._salario_base:.2f}"


class Gerente(Empleado):
    """Gerente - Hereda de Empleado"""

    def __init__(self, nombre, edad, salario_base, bono, departamento, años_experiencia=0):
        # Llamar al constructor de la clase padre
        super().__init__(nombre, edad, salario_base, años_experiencia)
        # Atributos específicos de Gerente
        self.bono = bono
        self.departamento = departamento

    def calcular_salario(self):
        """SOBRESCRITURA: El salario del gerente incluye el bono"""
        return self._salario_base + self.bono

    def mostrar_info(self):
        """SOBRESCRITURA: Mostrar información específica del gerente"""
        return (f"Gerente: {self.nombre}, Edad: {self.edad}, Experiencia: {self.años_experiencia} años, "
                f"Departamento: {self.departamento}, "
                f"Salario: ${self.calcular_salario():.2f} "
                f"(Base: ${self._salario_base} + Bono: ${self.bono})")


class Desarrollador(Empleado):
    """Desarrollador - Hereda de Empleado"""

    def __init__(self, nombre, edad, salario_base, lenguaje, horas_extra=0, pago_hora=50, años_experiencia=0):
        super().__init__(nombre, edad, salario_base, años_experiencia)
        # Atributos específicos
        self.lenguaje = lenguaje
        self.horas_extra = horas_extra
        self.pago_hora = pago_hora

    def calcular_salario(self):
        """SOBRESCRITURA: El desarrollador gana extra por horas extra"""
        extra = self.horas_extra * self.pago_hora
        # Bono adicional por años de experiencia
        bono_experiencia = self._salario_base * (self.años_experiencia * 0.01)  # 1% por cada año
        return self._salario_base + extra + bono_experiencia

    def mostrar_info(self):
        """SOBRESCRITURA: Mostrar información específica"""
        extra = self.horas_extra * self.pago_hora
        bono_exp = self._salario_base * (self.años_experiencia * 0.01)
        return (f"Desarrollador: {self.nombre}, Edad: {self.edad}, Experiencia: {self.años_experiencia} años, "
                f"Lenguaje: {self.lenguaje}, "
                f"Salario: ${self.calcular_salario():.2f} "
                f"(Base: ${self._salario_base} + "
                f"HE: {self.horas_extra} x ${self.pago_hora} = ${extra} + "
                f"Bono Exp: ${bono_exp:.2f})")


class Practicante(Empleado):
    """Practicante - Hereda de Empleado (RETO 1 y 2)"""

    def __init__(self, nombre, edad, salario_base, universidad, años_experiencia=0):
        super().__init__(nombre, edad, salario_base, años_experiencia)
        # Atributos específicos
        self.universidad = universidad

    def calcular_salario(self):
        """SOBRESCRITURA: El practicante gana el 70% del salario base más bonificación por experiencia"""
        porcentaje = 0.70  # 70% del salario base
        salario_practica = self._salario_base * porcentaje
        # Bono adicional por años de experiencia (1% por cada año sobre el salario reducido)
        bono_experiencia = salario_practica * (self.años_experiencia * 0.01)
        return salario_practica + bono_experiencia

    def mostrar_info(self):
        """SOBRESCRITURA: Mostrar información específica del practicante"""
        porcentaje = 0.70
        salario_base_practica = self._salario_base * porcentaje
        bono_exp = salario_base_practica * (self.años_experiencia * 0.01)
        return (f"Practicante: {self.nombre}, Edad: {self.edad}, Experiencia: {self.años_experiencia} años, "
                f"Universidad: {self.universidad}, "
                f"Salario: ${self.calcular_salario():.2f} "
                f"(Base: ${self._salario_base} x 70% = ${salario_base_practica:.2f} + "
                f"Bono Exp: ${bono_exp:.2f})")


def mostrar_nomina(empleados):
    """FUNCIÓN QUE DEMUESTRA POLIMORFISMO"""
    print("\n" + "=" * 60)
    print("NÓMINA DE EMPLEADOS")
    print("=" * 60)

    total_nomina = 0
    for empleado in empleados:
        # POLIMORFISMO: Aunque todos son tipos diferentes, todos entienden los mismos métodos
        print(empleado.mostrar_info())
        total_nomina += empleado.calcular_salario()

    print("-" * 60)
    print(f"TOTAL NÓMINA: ${total_nomina:.2f}")
    print("=" * 60)


def mostrar_menu():
    """Muestra las opciones disponibles"""
    print("\n" + "=" * 50)
    print("SISTEMA DE GESTIÓN DE EMPLEADOS")
    print("=" * 50)
    print("1. Agregar empleado regular")
    print("2. Agregar gerente")
    print("3. Agregar desarrollador")
    print("4. Agregar practicante")
    print("5. Ver todos los empleados")
    print("6. Calcular salario de un empleado")
    print("7. Aumentar salario de un empleado")
    print("8. Ver total de nómina")
    print("9. Salir")
    print("-" * 50)


def agregar_empleado(empleados):
    """Agrega un empleado regular"""
    print("\n--- NUEVO EMPLEADO REGULAR ---")
    try:
        nombre = input("Nombre: ").strip()
        if not nombre:
            print("⚠ El nombre no puede estar vacío")
            return empleados
        
        edad = int(input("Edad: "))
        salario = float(input("Salario base: $"))
        experiencia = int(input("Años de experiencia: "))

        nuevo = Empleado(nombre, edad, salario, experiencia)
        empleados.append(nuevo)
        print(f"✓ Empleado {nombre} agregado exitosamente")
    except ValueError:
        print("⚠ Error: Por favor ingrese valores numéricos válidos")
    return empleados


def agregar_gerente(empleados):
    """Agrega un gerente"""
    print("\n--- NUEVO GERENTE ---")
    try:
        nombre = input("Nombre: ").strip()
        if not nombre:
            print("⚠ El nombre no puede estar vacío")
            return empleados
        
        edad = int(input("Edad: "))
        salario = float(input("Salario base: $"))
        bono = float(input("Bono: $"))
        depto = input("Departamento: ").strip()
        experiencia = int(input("Años de experiencia: "))

        nuevo = Gerente(nombre, edad, salario, bono, depto, experiencia)
        empleados.append(nuevo)
        print(f"✓ Gerente {nombre} agregado exitosamente")
    except ValueError:
        print("⚠ Error: Por favor ingrese valores numéricos válidos")
    return empleados


def agregar_desarrollador(empleados):
    """Agrega un desarrollador"""
    print("\n--- NUEVO DESARROLLADOR ---")
    try:
        nombre = input("Nombre: ").strip()
        if not nombre:
            print("⚠ El nombre no puede estar vacío")
            return empleados
        
        edad = int(input("Edad: "))
        salario = float(input("Salario base: $"))
        lenguaje = input("Lenguaje principal: ").strip()
        horas = int(input("Horas extra (0 si no tiene): "))
        experiencia = int(input("Años de experiencia: "))

        nuevo = Desarrollador(nombre, edad, salario, lenguaje, horas, 50, experiencia)
        empleados.append(nuevo)
        print(f"✓ Desarrollador {nombre} agregado exitosamente")
    except ValueError:
        print("⚠ Error: Por favor ingrese valores numéricos válidos")
    return empleados


def agregar_practicante(empleados):
    """Agrega un practicante (RETO)"""
    print("\n--- NUEVO PRACTICANTE ---")
    try:
        nombre = input("Nombre: ").strip()
        if not nombre:
            print("⚠ El nombre no puede estar vacío")
            return empleados
        
        edad = int(input("Edad: "))
        salario = float(input("Salario base: $"))
        universidad = input("Universidad: ").strip()
        experiencia = int(input("Años de experiencia: "))

        nuevo = Practicante(nombre, edad, salario, universidad, experiencia)
        empleados.append(nuevo)
        print(f"✓ Practicante {nombre} agregado exitosamente")
    except ValueError:
        print("⚠ Error: Por favor ingrese valores numéricos válidos")
    return empleados


def ver_empleados(empleados):
    """Muestra todos los empleados"""
    if not empleados:
        print("\n⚠ No hay empleados registrados")
        return

    print("\n" + "=" * 60)
    print("LISTA DE EMPLEADOS")
    print("=" * 60)

    for i, emp in enumerate(empleados, 1):
        print(f"{i}. {emp.mostrar_info()}")
    print("=" * 60)


def calcular_salario_individual(empleados):
    """Calcula el salario de un empleado específico"""
    if not empleados:
        print("\n⚠ No hay empleados registrados")
        return

    print("\n--- SELECCIONE EMPLEADO ---")
    for i, emp in enumerate(empleados, 1):
        print(f"{i}. {emp.nombre} ({type(emp).__name__})")

    try:
        opcion = input("\nNúmero del empleado: ").strip()
        if not opcion:
            print("⚠ No ingresó ninguna opción")
            return
        
        opcion = int(opcion) - 1
        if 0 <= opcion < len(empleados):
            emp = empleados[opcion]
            print("\n" + "-" * 40)
            print(emp.mostrar_info())
            print(f"Salario calculado: ${emp.calcular_salario():.2f}")
            print("-" * 40)
        else:
            print("⚠ Opción inválida")
    except ValueError:
        print("⚠ Por favor ingrese un número válido")


def aumentar_salario_empleado(empleados):
    """Aumenta el salario de un empleado (RETO 3)"""
    if not empleados:
        print("\n⚠ No hay empleados registrados")
        return

    print("\n--- SELECCIONE EMPLEADO PARA AUMENTAR SALARIO ---")
    for i, emp in enumerate(empleados, 1):
        print(f"{i}. {emp.nombre} ({type(emp).__name__}) - Salario base: ${emp._salario_base:.2f}")

    try:
        opcion = input("\nNúmero del empleado: ").strip()
        if not opcion:
            print("⚠ No ingresó ninguna opción")
            return
        
        opcion = int(opcion) - 1
        if 0 <= opcion < len(empleados):
            emp = empleados[opcion]
            porcentaje = float(input("Porcentaje de aumento (%): "))
            if porcentaje > 0:
                resultado = emp.aumentar_salario(porcentaje)
                print(f"\n{resultado}")
            else:
                print("⚠ El porcentaje debe ser mayor a 0")
        else:
            print("⚠ Opción inválida")
    except ValueError:
        print("⚠ Por favor ingrese valores numéricos válidos")


def ver_total_nomina(empleados):
    """Muestra el total de la nómina"""
    if not empleados:
        print("\n⚠ No hay empleados registrados")
        return

    total = sum(emp.calcular_salario() for emp in empleados)

    print("\n" + "=" * 50)
    print("RESUMEN DE NÓMINA")
    print("=" * 50)
    print(f"Total empleados: {len(empleados)}")
    print(f"Total nómina: ${total:.2f}")

    # Mostrar desglose por tipo
    tipos = {}
    for emp in empleados:
        tipo = type(emp).__name__
        if tipo not in tipos:
            tipos[tipo] = {"cantidad": 0, "total": 0}
        tipos[tipo]["cantidad"] += 1
        tipos[tipo]["total"] += emp.calcular_salario()

    print("\n--- Desglose por tipo ---")
    for tipo, datos in tipos.items():
        print(f"{tipo}: {datos['cantidad']} empleado(s) - Total: ${datos['total']:.2f}")
    print("=" * 50)


if __name__ == "__main__":
    # Lista para almacenar empleados
    empleados = []

    # Agregar algunos empleados de ejemplo
    empleados.append(Empleado("Juan Pérez", 30, 3000, 5))
    empleados.append(Gerente("Ana Gómez", 45, 5000, 2000, "Tecnología", 15))
    empleados.append(Desarrollador("Carlos López", 28, 3500, "Python", 10, 50, 3))
    empleados.append(Desarrollador("María Rodríguez", 32, 4000, "Java", 0, 50, 8))
    empleados.append(Gerente("Roberto Sánchez", 50, 8000, 3500, "Ventas", 20))
    empleados.append(Practicante("Luis Torres", 22, 1500, "Universidad Nacional", 1))

    print("\nSISTEMA DE EMPLEADOS CON HERENCIA Y POLIMORFISMO")
    print("Empleados de ejemplo cargados")
    print(f"Total de empleados precargados: {len(empleados)}")

    while True:
        mostrar_menu()
        opcion = input("Seleccione una opción: ").strip()

        if opcion == "1":
            empleados = agregar_empleado(empleados)
            input("\nPresione Enter para continuar...")
        elif opcion == "2":
            empleados = agregar_gerente(empleados)
            input("\nPresione Enter para continuar...")
        elif opcion == "3":
            empleados = agregar_desarrollador(empleados)
            input("\nPresione Enter para continuar...")
        elif opcion == "4":
            empleados = agregar_practicante(empleados)
            input("\nPresione Enter para continuar...")
        elif opcion == "5":
            ver_empleados(empleados)
            input("\nPresione Enter para continuar...")
        elif opcion == "6":
            calcular_salario_individual(empleados)
            input("\nPresione Enter para continuar...")
        elif opcion == "7":
            aumentar_salario_empleado(empleados)
            input("\nPresione Enter para continuar...")
        elif opcion == "8":
            ver_total_nomina(empleados)
            input("\nPresione Enter para continuar...")
        elif opcion == "9":
            print("\n👋 ¡Hasta luego!")
            break
        else:
            print("\n⚠ Opción inválida. Intente nuevamente.")
            input("\nPresione Enter para continuar...")