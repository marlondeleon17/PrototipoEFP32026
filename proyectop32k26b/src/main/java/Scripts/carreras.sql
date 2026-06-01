CREATE TABLE facultades (
    codigo_facultad VARCHAR(5) NOT NULL,
    nombre_facultad VARCHAR(45) NOT NULL,
    estatus_facultad VARCHAR(1) NOT NULL,
    PRIMARY KEY (codigo_facultad)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4;




CREATE TABLE carreras (
    codigo_carrera VARCHAR(5) NOT NULL,
    nombre_carrera VARCHAR(45) NOT NULL,
    codigo_facultad VARCHAR(5) NOT NULL,
    estatus_carrera VARCHAR(1) NOT NULL DEFAULT 'A',

    PRIMARY KEY (codigo_carrera),

    CONSTRAINT fk_carreras_facultad
        FOREIGN KEY (codigo_facultad)
        REFERENCES facultades(codigo_facultad)
        ON UPDATE CASCADE
        ON DELETE RESTRICT

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4;




