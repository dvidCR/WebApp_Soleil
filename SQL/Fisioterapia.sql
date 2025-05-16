CREATE DATABASE IF NOT EXISTS Fisioterapia;
USE Fisioterapia;

CREATE TABLE IF NOT EXISTS Empleado(
    dni VARCHAR(9),
    nombre VARCHAR(20),
    apellidos VARCHAR(30),
    correo VARCHAR(20),
    usuario VARCHAR(20),
    contrasena VARCHAR(20),
    rol VARCHAR(10),
    PRIMARY KEY(dni)
);

CREATE TABLE IF NOT EXISTS Paciente(
    dni VARCHAR(9),
    nombre VARCHAR(20),
    apellidos VARCHAR(30),
    dni_empleado VARCHAR(9) NOT NULL,
    PRIMARY KEY (dni),
    FOREIGN KEY (dni_empleado) REFERENCES Empleado(dni)
);

CREATE TABLE IF NOT EXISTS Tratamiento(
    id_tratamiento INT AUTO_INCREMENT,
    tipo_tratamiento VARCHAR(30),
    descripcion VARCHAR(100),
    dni_paciente VARCHAR(9) NOT NULL,
    PRIMARY KEY (id_tratamiento),
    FOREIGN KEY (dni_paciente) REFERENCES Paciente(dni)    
);

-- Nombre anterior Ingreso
CREATE TABLE IF NOT EXISTS Servicio(
    id_servicio INT AUTO_INCREMENT,
    fecha_cita DATE,
    dni_empleado VARCHAR(9) NOT NULL,
    dni_paciente VARCHAR(9) NOT NULL,
    id_tratamiento INT NOT NULL,
    modo_pago VARCHAR(5),
    tarifa DOUBLE,
    concepto VARCHAR(30),
    num_sesiones INT,
    PRIMARY KEY(id_servicio),
    FOREIGN KEY (dni_empleado) REFERENCES Empleado(dni),
    FOREIGN KEY (dni_paciente) REFERENCES Paciente(dni),
    FOREIGN KEY (id_tratamiento) REFERENCES Tratamiento(id_tratamiento)
);

CREATE TABLE IF NOT EXISTS Gasto(
    id_gasto INT AUTO_INCREMENT,
    cantidad DOUBLE,
    motivo VARCHAR(30),
    proveedor VARCHAR(30),
    fecha DATE,
    PRIMARY KEY(id_gasto)
);

-- Calcular las horas de manera externa sin guardarlas en la bd (opcional).
CREATE TABLE IF NOT EXISTS Fichaje(
    id_fichaje INT AUTO_INCREMENT,
    fecha DATE,
    hora_entrada TIME,
    hora_salida TIME,
    dni_empleado VARCHAR(9) NOT NULL,
    PRIMARY KEY(id_fichaje),
    FOREIGN KEY(dni_empleado) REFERENCES Empleado(dni)
);

