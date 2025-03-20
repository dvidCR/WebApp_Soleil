CREATE DATABASE IF NOT EXISTS Fisioterapia;
USE Fisioterapia;

CREATE TABLE IF NOT EXISTS Ingresos(
	id INT AUTO_INCREMENT,
    servicio VARCHAR(30),
    dia DATE,
    fisioterapeuta VARCHAR(30),
    paciente VARCHAR(30),
    pago VARCHAR(5),
    tarifa DOUBLE,
    cantidad INT,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS Gastos(
	id INT AUTO_INCREMENT,
    cantidad INT,
    motivo VARCHAR(30),
    proveedor VARCHAR(30),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS Empleados(
	id INT AUTO_INCREMENT,
    nombre VARCHAR(20),
    apellido VARCHAR(30),
    dni VARCHAR(9),
    rol VARCHAR(10),
    PRIMARY KEY(id),
    FOREIGN KEY(rol) REFERENCES Usuarios (rol)
);

CREATE TABLE IF NOT EXISTS Usuarios(
    usuario VARCHAR(20),
    contrasena VARCHAR(20),
    rol VARCHAR(10),
    PRIMARY KEY(rol)
);