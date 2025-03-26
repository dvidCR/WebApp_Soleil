CREATE DATABASE IF NOT EXISTS Fisioterapia;
USE Fisioterapia;

CREATE TABLE IF NOT EXISTS Gasto(
	id INT AUTO_INCREMENT,
    cantidad INT,
    motivo VARCHAR(30),
    proveedor VARCHAR(30),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS Usuario(
    usuario VARCHAR(20),
    contrasena VARCHAR(20),
    rol VARCHAR(10),
    PRIMARY KEY(usuario)
);

CREATE TABLE IF NOT EXISTS Empleado(
	id INT AUTO_INCREMENT,
    nombre VARCHAR(20),
    usuario VARCHAR(20),
    apellidos VARCHAR(30),
    dni VARCHAR(9),
    PRIMARY KEY(id),
    FOREIGN KEY(usuario) REFERENCES Usuario(usuario)
);

CREATE TABLE IF NOT EXISTS Ingreso(
	id INT AUTO_INCREMENT,
    servicio VARCHAR(30),
    dia DATE,
    fisioterapeuta INT,
    paciente INT,
    pago VARCHAR(5),
    tarifa DOUBLE,
    cantidad INT,
    PRIMARY KEY(id),
    FOREIGN KEY (fisioterapeuta) REFERENCES Empleado(id),
    FOREIGN KEY (paciente) REFERENCES Paciente(id)
);

CREATE TABLE IF NOT EXISTS Fichaje(
	id INT AUTO_INCREMENT,
    hora_entrada DATE,
    hora_salida DATE,
    empleado INT,
    PRIMARY KEY(id),
    FOREIGN KEY(empleado) REFERENCES Empleado(id)
);

CREATE TABLE IF NOT EXISTS Paciente(
	id INT AUTO_INCREMENT,
    dni VARCHAR(9),
    nombre VARCHAR(20),
    apellidos VARCHAR(30),
    PRIMARY KEY (id)
);
