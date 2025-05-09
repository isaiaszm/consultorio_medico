DELETE FROM citas;
DELETE FROM doctores;
DELETE FROM consultorios;


INSERT INTO doctores (nombre, apellido_paterno, apellido_materno, especialidad) VALUES
    ('Juan', 'García', 'López', 'Cardiología');
INSERT INTO doctores (nombre, apellido_paterno, apellido_materno, especialidad) VALUES
    ('María', 'Rodríguez', 'Sánchez', 'Pediatría');
INSERT INTO doctores (nombre, apellido_paterno, apellido_materno, especialidad) VALUES
    ('Carlos', 'Martínez', 'Fernández', 'Ortopedia');
INSERT INTO doctores (nombre, apellido_paterno, apellido_materno, especialidad) VALUES
    ('Ana', 'Hernández', 'Gómez', 'Dermatología');
INSERT INTO doctores (nombre, apellido_paterno, apellido_materno, especialidad) VALUES
    ('Luis', 'Pérez', 'Ramírez', 'Neurología');

INSERT INTO consultorios (no_consultorio, piso) VALUES
    (101, 1);
INSERT INTO consultorios (no_consultorio, piso) VALUES
    (102, 1);
INSERT INTO consultorios (no_consultorio, piso) VALUES
    (201, 2);
INSERT INTO consultorios (no_consultorio, piso) VALUES
    (202, 2);
INSERT INTO consultorios (no_consultorio, piso) VALUES
    (301, 3);