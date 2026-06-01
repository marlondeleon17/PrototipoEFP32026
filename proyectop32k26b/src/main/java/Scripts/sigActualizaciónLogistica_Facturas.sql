-- ============================================================
-- SCRIPT DE ACTUALIZACIÓN - SIG
-- Compatible con MySQL y MariaDB
-- Autor: Kevin Lopez - Módulo Cuentas Corrientes
-- ============================================================

USE sig;
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
-- PROCEDIMIENTO AUXILIAR: agrega columna solo si no existe
-- ============================================================

DROP PROCEDURE IF EXISTS agregarColumna;
DELIMITER $$
CREATE PROCEDURE agregarColumna(
    IN tabla VARCHAR(64),
    IN columna VARCHAR(64),
    IN definicion TEXT
)
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS
        WHERE TABLE_SCHEMA = 'sig'
          AND TABLE_NAME   = tabla
          AND COLUMN_NAME  = columna
    ) THEN
        SET @sql = CONCAT('ALTER TABLE `', tabla, '` ADD COLUMN `', columna, '` ', definicion);
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END IF;
END$$
DELIMITER ;

-- ============================================================
-- PROCEDIMIENTO AUXILIAR: agrega FK solo si no existe
-- ============================================================

DROP PROCEDURE IF EXISTS agregarFK;
DELIMITER $$
CREATE PROCEDURE agregarFK(
    IN tabla VARCHAR(64),
    IN nombreFK VARCHAR(64),
    IN definicion TEXT
)
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
        WHERE TABLE_SCHEMA    = 'sig'
          AND TABLE_NAME      = tabla
          AND CONSTRAINT_NAME = nombreFK
    ) THEN
        SET @sql = CONCAT('ALTER TABLE `', tabla, '` ADD CONSTRAINT `', nombreFK, '` ', definicion);
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END IF;
END$$
DELIMITER ;


CALL agregarColumna('facturasventa', 'Facvenumero',   'varchar(50) NULL');
CALL agregarColumna('facturasventa', 'Facvesubtotal', 'decimal(18,2) NULL');
CALL agregarColumna('facturasventa', 'Facveiva',      'decimal(18,2) NULL');
CALL agregarColumna('facturasventa', 'Facveestado',   'varchar(20) DEFAULT ''Vigente''');
CALL agregarColumna('facturasventa', 'Impid',         'int(11) NULL');

CALL agregarFK('facturasventa', 'fk_facventa_cli',
  'FOREIGN KEY (Cliid) REFERENCES clientes(Cliid)');
CALL agregarFK('facturasventa', 'fk_facventa_ven',
  'FOREIGN KEY (Venid) REFERENCES vendedores(Venid)');
CALL agregarFK('facturasventa', 'fk_facventa_imp',
  'FOREIGN KEY (Impid) REFERENCES impuestos(Impid)');


CALL agregarColumna('facturaventadetalle', 'Facvesubtotal', 'decimal(18,2) NULL');
CALL agregarColumna('facturaventadetalle', 'Pronombre',     'varchar(100) NULL');

CALL agregarFK('facturaventadetalle', 'fk_detven_fac',
  'FOREIGN KEY (Facid) REFERENCES facturasventa(Facid)');
CALL agregarFK('facturaventadetalle', 'fk_detven_prod',
  'FOREIGN KEY (Prodid) REFERENCES productos(Prodid)');


CREATE TABLE IF NOT EXISTS `impuestos` (
  `Impid`     int(11)      NOT NULL AUTO_INCREMENT,
  `Impnombre` varchar(50)  NOT NULL,
  `Impvalor`  decimal(5,2) NOT NULL COMMENT 'Ej: 12.00 para IVA 12%',
  `Impestado` varchar(10)  DEFAULT 'Activo',
  PRIMARY KEY (`Impid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO `impuestos` (`Impnombre`, `Impvalor`, `Impestado`) VALUES
  ('IVA', 12.00, 'Activo');


CREATE TABLE IF NOT EXISTS `lineas` (
  `lineaid`     int(11)       NOT NULL AUTO_INCREMENT,
  `linnombre`   varchar(100)  NOT NULL,
  `linestado`   tinyint(1)    DEFAULT 1,
  `lincomision` decimal(10,2) DEFAULT 0.00,
  PRIMARY KEY (`lineaid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `marcas` (
  `marcaid`   int(11)      NOT NULL AUTO_INCREMENT,
  `marnombre` varchar(100) NOT NULL,
  `marestado` tinyint(1)   DEFAULT 1,
  PRIMARY KEY (`marcaid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CALL agregarColumna('productos', 'Prodcomision', 'decimal(5,2) NULL');
CALL agregarColumna('productos', 'lineaid',      'int(11) NULL');
CALL agregarColumna('productos', 'marcaid',      'int(11) NULL');

CALL agregarFK('productos', 'fk_prod_linea',
  'FOREIGN KEY (lineaid) REFERENCES lineas(lineaid)');
CALL agregarFK('productos', 'fk_prod_marca',
  'FOREIGN KEY (marcaid) REFERENCES marcas(marcaid)');


CREATE TABLE IF NOT EXISTS `facturascompras` (
  `Faccomid`       int(11)       NOT NULL AUTO_INCREMENT,
  `Faccomnumero`   varchar(50)   NOT NULL UNIQUE,
  `Faccomfecha`    datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Procodigo`      int(11)       NOT NULL,
  `Impid`          int(11)       NULL,
  `Faccomsubtotal` decimal(18,2) NOT NULL,
  `Faccomiva`      decimal(18,2) NOT NULL,
  `Faccomtotal`    decimal(18,2) NOT NULL,
  `Faccomestado`   varchar(20)   DEFAULT 'Vigente',
  PRIMARY KEY (`Faccomid`),
  FOREIGN KEY (`Procodigo`) REFERENCES `proveedores`(`Procodigo`),
  FOREIGN KEY (`Impid`)     REFERENCES `impuestos`(`Impid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `facturadetallecompras` (
  `Faccomdetid`    int(11)       NOT NULL AUTO_INCREMENT,
  `Faccomid`       int(11)       NOT NULL,
  `Prodid`         int(11)       NOT NULL,
  `Pronombre`      varchar(100)  NULL,
  `Faccomcantidad` decimal(12,2) NOT NULL,
  `Faccomprecio`   decimal(18,2) NOT NULL,
  `Faccomsubtotal` decimal(18,2) NOT NULL,
  PRIMARY KEY (`Faccomdetid`),
  FOREIGN KEY (`Faccomid`) REFERENCES `facturascompras`(`Faccomid`),
  FOREIGN KEY (`Prodid`)   REFERENCES `productos`(`Prodid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `movimientoscc` (
  `Mccid`       int(11)       NOT NULL AUTO_INCREMENT,
  `Mccfecha`    datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Mccmonto`    decimal(12,2) NOT NULL,
  `Mcctipo`     ENUM('CARGO','ABONO') NOT NULL,
  `Mccconcepto` varchar(255)  NOT NULL,
  `Mccestado`   char(1)       NOT NULL DEFAULT 'A',
  `Mccsaldo`    decimal(12,2) NOT NULL DEFAULT 0.00,
  `Cliid`       int(11)       NULL,
  `Procodigo`   int(11)       NULL,
  `Acrecodigo`  int(11)       NULL,
  `Venid`       int(11)       NULL,
  `TTid`        int(11)       NOT NULL,
  `Mccmodulo`   ENUM('VENTAS','COMPRAS','PLANILLA','BANCOS','CC','LOGISTICA') NOT NULL,
  `Mccorigenid` int(11)       NULL,
  PRIMARY KEY (`Mccid`),
  FOREIGN KEY (`Cliid`)      REFERENCES `clientes`(`Cliid`),
  FOREIGN KEY (`Procodigo`)  REFERENCES `proveedores`(`Procodigo`),
  FOREIGN KEY (`Acrecodigo`) REFERENCES `acreedores`(`Acrecodigo`),
  FOREIGN KEY (`Venid`)      REFERENCES `vendedores`(`Venid`),
  FOREIGN KEY (`TTid`)       REFERENCES `Cattipotransaccion`(`TTid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT IGNORE INTO `Cattipotransaccion` (`TTnombretipo`, `TTdescripcion`) VALUES
  ('NOTA_CREDITO',   'Reduccion de saldo por devolucion o ajuste'),
  ('FACTURA_VENTA',  'Cargo por factura de venta a cliente'),
  ('FACTURA_COMPRA', 'Cargo por factura de compra a proveedor'),
  ('IMPUESTO',       'Cargo por impuesto aplicado en factura');


DROP PROCEDURE IF EXISTS agregarColumna;
DROP PROCEDURE IF EXISTS agregarFK;

SET FOREIGN_KEY_CHECKS = 1;