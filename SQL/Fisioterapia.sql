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
    PRIMARY KEY (dni)
);

CREATE TABLE IF NOT EXISTS Tratamiento(
    id INT AUTO_INCREMENT,
    tipo_tratamiento VARCHAR(30),
    dni_paciente VARCHAR(9),
    PRIMARY KEY (id),
    FOREIGN KEY (dni_paciente) REFERENCES Paciente(dni)    
);

-- Nombre anterior Ingreso
CREATE TABLE IF NOT EXISTS Servicio(
    id INT AUTO_INCREMENT,
    fecha_cita DATE,
    dni_empleado VARCHAR(9),
    dni_paciente VARCHAR(9),
    id_tratamiento INT,
    modo_pago VARCHAR(5),
    tarifa DOUBLE,
    concepto VARCHAR(30),
    num_sesiones INT,
    PRIMARY KEY(id),
    FOREIGN KEY (dni_empleado) REFERENCES Empleado(dni),
    FOREIGN KEY (dni_paciente) REFERENCES Paciente(dni),
    FOREIGN KEY (id_tratamiento) REFERENCES Tratamiento(id)
);

CREATE TABLE IF NOT EXISTS Gasto(
    id_gasto INT AUTO_INCREMENT,
    cantidad INT,
    motivo VARCHAR(30),
    proveedor VARCHAR(30),
    PRIMARY KEY(id)
);

-- Calcular las horas de manera externa sin guardarlas en la bd (opcional).
CREATE TABLE IF NOT EXISTS Fichaje(
    id_fichaje INT AUTO_INCREMENT,
    fecha DATE,
    hora_entrada DATE,
    hora_salida DATE,
    dni_empleado VARCHAR(9),
    PRIMARY KEY(id_fichaje),
    FOREIGN KEY(dni_empleado) REFERENCES Empleado(dni)
);
