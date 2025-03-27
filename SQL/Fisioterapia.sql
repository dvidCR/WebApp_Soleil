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

CREATE TABLE IF NOT EXISTS Sesion(
    id INT AUTO_INCREMENT,
    
);

CREATE TABLE IF NOT EXISTS Tratamiento(
    id INT AUTO_INCREMENT,
    
);

CREATE TABLE IF NOT EXISTS Paciente(
    dni VARCHAR(9),
    nombre VARCHAR(20),
    apellidos VARCHAR(30),
    PRIMARY KEY (dni)
);

-- Nombre anterior Ingreso
CREATE TABLE IF NOT EXISTS Servicio(
    id INT AUTO_INCREMENT,
    servicio VARCHAR(30),
    dia DATE,
    dni_empleado INT,
    dni_paciente INT,
    modo_pago VARCHAR(5),
    tarifa DOUBLE,
    PRIMARY KEY(id),
    FOREIGN KEY (dni_empleado) REFERENCES Empleado(dni),
    FOREIGN KEY (dni_paciente) REFERENCES Paciente(dni)
);

CREATE TABLE IF NOT EXISTS Gasto(
	id INT AUTO_INCREMENT,
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
    PRIMARY KEY(id),
    FOREIGN KEY(dni_empleado) REFERENCES Empleado(dni)
);
