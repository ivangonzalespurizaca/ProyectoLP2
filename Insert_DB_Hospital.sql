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
-- CITAS FUTURAS (15 registros)
INSERT INTO Cita (ID_MEDICO, ID_Paciente, ID_Usuario, Fecha, Hora, Motivo)
VALUES
(1, 1, 3, '2025-10-27', '09:00:00', 'Consulta general'),
(1, 2, 3, '2025-10-29', '09:00:00', 'Dolor de garganta'),
(1, 3, 3, '2025-11-03', '09:00:00', 'Fiebre y malestar'),
(2, 1, 3, '2025-10-28', '08:00:00', 'Revisión pediátrica'),
(2, 2, 3, '2025-10-30', '08:30:00', 'Chequeo infantil'),
(2, 3, 3, '2025-11-04', '09:00:00', 'Vacunación'),
(3, 1, 3, '2025-10-27', '14:00:00', 'Control ginecológico'),
(3, 2, 3, '2025-10-29', '15:00:00', 'Consulta prenatal'),
(3, 3, 3, '2025-11-05', '16:00:00', 'Revisión anual'),
(4, 1, 3, '2025-10-28', '09:00:00', 'Chequeo cardíaco');

-- COMPROBANTES DE PAGO CORRESPONDIENTES
INSERT INTO Comprobante_Pago (ID_Cita, Nombre_Pagador, Apellidos_Pagador, DNI_Pagador, Contacto_Pagador, Monto, Metodo_Pago)
VALUES
(1, 'Javier', 'Mendoza Castillo', '74851236', '987321654', 80.00, 'EFECTIVO'),
(2, 'Lucía', 'Ramírez Paredes', '71589423', '956478213', 100.00, 'TARJETA'),
(3, 'Carlos', 'Torres Huamán', '73214589', '945612378', 120.00, 'TRANSFERENCIA'),
(4, 'Javier', 'Mendoza Castillo', '74851236', '987321654', 90.00, 'EFECTIVO'),
(5, 'Lucía', 'Ramírez Paredes', '71589423', '956478213', 95.00, 'TARJETA'),
(6, 'Carlos', 'Torres Huamán', '73214589', '945612378', 110.00, 'TRANSFERENCIA'),
(7, 'Javier', 'Mendoza Castillo', '74851236', '987321654', 85.00, 'EFECTIVO'),
(8, 'Lucía', 'Ramírez Paredes', '71589423', '956478213', 100.00, 'TARJETA'),
(9, 'Carlos', 'Torres Huamán', '73214589', '945612378', 115.00, 'TRANSFERENCIA'),
(10, 'Javier', 'Mendoza Castillo', '74851236', '987321654', 120.00, 'EFECTIVO');

select * from comprobante_pago;

select * from cita;

select * from medico;

select * from especialidad;

select * from usuario;