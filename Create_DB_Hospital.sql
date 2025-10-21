-- =====================================================
-- CREACIÓN DE BASE DE DATOS
-- =====================================================
DROP DATABASE IF EXISTS DB_Hospital;
CREATE DATABASE DB_Hospital;
USE DB_Hospital;

-- =====================================================
-- TABLA ESPECIALIDAD
-- =====================================================
CREATE TABLE Especialidad (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL UNIQUE
);

-- =====================================================
-- TABLA MÉDICO
-- =====================================================
CREATE TABLE Medico (
    ID_Medico INT AUTO_INCREMENT PRIMARY KEY,
    Nombres VARCHAR(100) NOT NULL,
    Apellidos VARCHAR(100) NOT NULL,
    DNI VARCHAR(8) NOT NULL UNIQUE,
    Nro_Colegiatura VARCHAR(20) NOT NULL UNIQUE,
    Telefono VARCHAR(15),
    Especialidad_ID INT NOT NULL,
    FOREIGN KEY (Especialidad_ID) REFERENCES Especialidad(ID)
);

-- =====================================================
-- TABLA PACIENTE
-- =====================================================
CREATE TABLE Paciente (
    ID_Paciente INT AUTO_INCREMENT PRIMARY KEY,
    Nombres VARCHAR(100) NOT NULL,
    Apellidos VARCHAR(50) NOT NULL,
    DNI VARCHAR(8) NOT NULL UNIQUE,
	Fecha_nacimiento DATE,
    Telefono VARCHAR(15)
);

-- =====================================================
-- TABLA Usuario
-- =====================================================
CREATE TABLE Usuario (
    Id_Usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Contrasenia VARCHAR(200) NOT NULL,
    Nombres VARCHAR(100) NOT NULL,
    Apellidos VARCHAR(100) NOT NULL,
    DNI VARCHAR(8) NOT NULL UNIQUE,
    Telefono VARCHAR(9) NULL,
    Img_Perfil VARCHAR(200) NULL,
    Correo VARCHAR(100) NULL,
    Rol ENUM('ADMINISTRADOR', 'RECEPCIONISTA','CAJERO') NOT NULL
);

-- =====================================================
-- TABLA CITA (con referencia a trabajador)
-- =====================================================
CREATE TABLE Cita (
    ID_Cita INT AUTO_INCREMENT PRIMARY KEY,
    ID_Medico INT NOT NULL,
    ID_Paciente INT NOT NULL,
	Id_Usuario BIGINT NOT NULL,
    Fecha_Cita DATETIME NOT NULL,
    Motivo VARCHAR(255),
    Estado ENUM('PENDIENTE', 'PAGADO') DEFAULT 'PENDIENTE',
    FOREIGN KEY (ID_MEDICO) REFERENCES Medico(ID_MEDICO),
    FOREIGN KEY (ID_Paciente) REFERENCES Paciente(ID_PACIENTE),
    FOREIGN KEY (Id_Usuario) REFERENCES Usuario(Id_Usuario)
);

-- =====================================================
-- TABLA HORARIOS ATENCIÓN
-- =====================================================
CREATE TABLE Horarios_Atencion (
    ID_Horario INT AUTO_INCREMENT PRIMARY KEY,
    ID_MEDICO INT NOT NULL,
    Dia_Semana ENUM('LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO', 'DOMINGO') NOT NULL,
    Horario_Entrada TIME NOT NULL,
    Horario_Salida TIME NOT NULL,
    FOREIGN KEY (ID_MEDICO) REFERENCES Medico(Id_Medico),
    CONSTRAINT UC_Horario UNIQUE (ID_Medico, Dia_Semana, Horario_Entrada, Horario_Salida)
);

-- =====================================================
-- TABLA COMPROBANTE DE PAGO
-- =====================================================
CREATE TABLE Comprobante_Pago (
    ID_Comprobante INT AUTO_INCREMENT PRIMARY KEY,
    ID_Cita INT NOT NULL UNIQUE,
	Nombre_Pagador VARCHAR(100) NOT NULL,
	Apellidos_Pagador VARCHAR(100) NOT NULL,
    DNI_Pagador VARCHAR(8),
    Contacto_Pagador VARCHAR(15),
    Fecha_Emision DATETIME NOT NULL DEFAULT NOW(),
    Monto DECIMAL(10,2) NOT NULL,
    Metodo_Pago ENUM('EFECTIVO', 'TARJETA', 'TRANSFERENCIA') NOT NULL,
    Estado ENUM('EMITIDO', 'ANULADO') DEFAULT 'EMITIDO',
    FOREIGN KEY (ID_Cita) REFERENCES Cita(ID_Cita)
);

-- =========================================================
-- TRIGGER PARA CAMBIAR ESTADO DE CITA AL CREAR COMPROBANTE
-- =========================================================
DELIMITER $$

CREATE TRIGGER trg_After_Insert_Comprobante
AFTER INSERT ON Comprobante_Pago
FOR EACH ROW
BEGIN
    UPDATE Cita
    SET Estado = 'PAGADO'
    WHERE ID_Cita = NEW.ID_Cita;
END $$

DELIMITER ;
