USE DB_Hospital;

-- ESPECIALIDADES
INSERT INTO Especialidad (Nombre)
VALUES
('Medicina General'),
('Pediatría'),
('Ginecología'),
('Cardiología'),
('Odontología');

-- MÉDICOS
INSERT INTO Medico (Nombres, Apellidos, DNI, Nro_Colegiatura, Telefono, Especialidad_ID)
VALUES
('Luis', 'Ramírez Torres', '72134567', 'COL-1001', '987654321', 1),
('María', 'Gonzales Huamán', '71234568', 'COL-1002', '956123478', 2),
('Carla', 'Pérez Salazar', '70123459', 'COL-1003', '945871236', 3),
('José', 'Quispe Flores', '74589632', 'COL-1004', '912345789', 4),
('Ana', 'Rodríguez Loayza', '73215698', 'COL-1005', '923654178', 5);

-- PACIENTES
INSERT INTO Paciente (Nombres, Apellidos, DNI, Fecha_nacimiento ,Telefono )
VALUES
('Javier', 'Mendoza Castillo', '74851236', '1990-05-12', '987321654'),
('Lucía', 'Ramírez Paredes', '71589423', '1985-09-27', '956478213'),
('Carlos', 'Torres Huamán', '73214589', '2000-01-08','945612378');


-- USUARIOS
INSERT INTO Usuario (Username, Contrasenia, Nombres, Apellidos, DNI, Rol) VALUES
('admin1', '$2a$10$pehyH/xsPAjfSYDgzqvX5uZMKEHtT/z6Ikslr7x9Ej3UUZnq5gr3G', 'Luis Alberto', 'Ramírez Torres', '72134567', 'ADMINISTRADOR'),
('recep1', '$2a$10$pehyH/xsPAjfSYDgzqvX5uZMKEHtT/z6Ikslr7x9Ej3UUZnq5gr3G', 'María Fernanda', 'Gonzales Huamán', '71234568', 'RECEPCIONISTA'),
('caje1', '$2a$10$pehyH/xsPAjfSYDgzqvX5uZMKEHtT/z6Ikslr7x9Ej3UUZnq5gr3G', 'Carlos Enrique', 'Pérez Salazar', '73214569', 'CAJERO');

-- HORARIOS DE ATENCIÓN
INSERT INTO Horarios_Atencion (ID_MEDICO, Dia_Semana, Horario_Entrada, Horario_Salida)
VALUES
(1, 'LUNES', '09:00:00', '13:00:00'),
(1, 'MIERCOLES', '09:00:00', '13:00:00'),
(1, 'VIERNES', '09:00:00', '13:00:00'),
(2, 'MARTES', '08:00:00', '12:00:00'),
(2, 'JUEVES', '08:00:00', '12:00:00'),
(3, 'LUNES', '14:00:00', '18:00:00'),
(3, 'MIERCOLES', '14:00:00', '18:00:00'),
(4, 'MARTES', '09:00:00', '13:00:00'),
(4, 'JUEVES', '09:00:00', '13:00:00'),
(5, 'LUNES', '10:00:00', '16:00:00'),
(5, 'VIERNES', '10:00:00', '16:00:00');

-- CITAS
INSERT INTO Cita (ID_MEDICO, ID_Paciente, ID_Usuario, Fecha_Cita, Motivo)
VALUES
(1, 1, 3, '2025-10-10 09:00:00', 'Dolor de cabeza y fiebre'),
(2, 2, 3, '2025-10-09 08:00:00', 'Consulta pediátrica general'),
(4, 3, 3, '2025-10-08 09:00:00', 'Chequeo cardiológico');

-- COMPROBANTES DE PAGO
INSERT INTO Comprobante_Pago (ID_Cita, Nombre_Pagador, Apellidos_Pagador, DNI_Pagador, Contacto_Pagador, Monto, Metodo_Pago)
VALUES
(1, 'Javier', 'Mendoza Castillo', '74851236', '987321654', 80.00, 'EFECTIVO'),
(2, 'Lucía', 'Ramírez Paredes', '71589423', '956478213', 100.00, 'TARJETA'),
(3, 'Carlos', 'Torres Huamán', '73214589', '945612378', 120.00, 'TRANSFERENCIA');
