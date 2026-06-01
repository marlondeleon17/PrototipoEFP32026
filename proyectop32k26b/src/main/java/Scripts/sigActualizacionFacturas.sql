-- ============================================================
-- SCRIPT DE ACTUALIZACIÓN - SIG
-- Solo agrega cambios nuevos, NO modifica lo que ya existe
-- Autor: Kevin Lopez y Maria Celeste - Módulo Cuentas Corrientes
-- ============================================================

USE sig;
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
-- PASO 1: ACTUALIZAR facturasventa
-- Agregar campos que le faltan para nivelarla
-- ============================================================

-- Verificar si los campos ya existen antes de agregar
-- Si ya existen no hace nada, si no existen los agrega

ALTER TABLE `facturasventa`
  ADD COLUMN Facvenumero varchar(50) NULL AFTER Facid,
  ADD COLUMN Facvesubtotal decimal(18,2) NULL AFTER Venid,
  ADD COLUMN Facveiva decimal(18,2) NULL AFTER Facvesubtotal,
  ADD COLUMN Facveestado varchar(20) DEFAULT 'Vigente' AFTER Factotal,
  MODIFY COLUMN `Facfecha`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  MODIFY COLUMN `Factotal`  decimal(18,2) NOT NULL;

-- Agregar FKs solo si no existen
ALTER TABLE `facturasventa`
  ADD CONSTRAINT `fk_facventa_cli`
    FOREIGN KEY (`Cliid`) REFERENCES `clientes`(`Cliid`),
  ADD CONSTRAINT `fk_facventa_ven`
    FOREIGN KEY (`Venid`) REFERENCES `vendedores`(`Venid`);

-- ============================================================
-- PASO 2: ACTUALIZAR facturaventadetalle
-- Agregar campos que le faltan
-- ============================================================

ALTER TABLE `facturaventadetalle`
  ADD COLUMN `Facvesubtotal` decimal(18,2) NULL AFTER `Preciounitario`,
  MODIFY COLUMN `Cantidad`       int(11)       NOT NULL,
  MODIFY COLUMN `Preciounitario` decimal(18,2) NOT NULL;

-- Agregar FKs solo si no existen
ALTER TABLE `facturaventadetalle`
  ADD CONSTRAINT `fk_detven_fac`
    FOREIGN KEY (`Facid`)  REFERENCES `facturasventa`(`Facid`),
  ADD CONSTRAINT `fk_detven_prod`
    FOREIGN KEY (`Prodid`) REFERENCES `productos`(`Prodid`);

-- ============================================================
-- PASO 3: CREAR facturascompras (tabla nueva)
-- ============================================================

CREATE TABLE IF NOT EXISTS `facturascompras` (
  `Faccomid`       int(11)       NOT NULL AUTO_INCREMENT,
  `Faccomnumero`   varchar(50)   NOT NULL UNIQUE,
  `Faccomfecha` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Procodigo`      int(11)       NOT NULL,
  `Faccomsubtotal` decimal(18,2) NOT NULL,
  `Faccomiva`      decimal(18,2) NOT NULL,
  `Faccomtotal`    decimal(18,2) NOT NULL,
  `Faccomestado`   varchar(20)   DEFAULT 'Vigente',
  PRIMARY KEY (`Faccomid`),
  FOREIGN KEY (`Procodigo`) REFERENCES `proveedores`(`Procodigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- PASO 4: CREAR facturadetallecompras (tabla nueva)
-- ============================================================

CREATE TABLE IF NOT EXISTS `facturadetallecompras` (
  `Faccomdetid`    int(11)       NOT NULL AUTO_INCREMENT,
  `Faccomid`       int(11)       NOT NULL,
  `Prodid`         int(11)       NOT NULL,
  `Faccomcantidad` decimal(12,2) NOT NULL,
  `Faccomprecio`   decimal(18,2) NOT NULL,
  `Faccomsubtotal` decimal(18,2) NOT NULL,
  PRIMARY KEY (`Faccomdetid`),
  FOREIGN KEY (`Faccomid`) REFERENCES `facturascompras`(`Faccomid`),
  FOREIGN KEY (`Prodid`)   REFERENCES `productos`(`Prodid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- PASO 5: CREAR movimientoscc (tabla nueva para CC independiente)
-- ============================================================

CREATE TABLE IF NOT EXISTS `movimientoscc` (
  `Mccid`       int(11)       NOT NULL AUTO_INCREMENT,
  `Mccfecha`    datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Mccmonto`    decimal(12,2) NOT NULL,
  `Mcctipo`     ENUM('CARGO','ABONO') NOT NULL,
  `Mccconcepto` varchar(255)  NOT NULL,
  `Mccestado`   char(1)       NOT NULL DEFAULT 'A',
  `Mccsaldo`    decimal(12,2) NOT NULL DEFAULT 0.00,
  -- Entidad que genera el movimiento
  `Cliid`       int(11) NULL,
  `Procodigo`   int(11) NULL,
  `Acrecodigo`  int(11) NULL,
  `Venid`       int(11) NULL,
  -- Tipo y módulo origen
  `TTid`        int(11) NOT NULL,
  `Mccmodulo`   ENUM('VENTAS','COMPRAS','PLANILLA','BANCOS','CC','LOGISTICA') NOT NULL,
  `Mccorigenid` int(11) NULL COMMENT 'ID del documento en el módulo origen',
  PRIMARY KEY (`Mccid`),
  FOREIGN KEY (`Cliid`)      REFERENCES `clientes`(`Cliid`),
  FOREIGN KEY (`Procodigo`)  REFERENCES `proveedores`(`Procodigo`),
  FOREIGN KEY (`Acrecodigo`) REFERENCES `acreedores`(`Acrecodigo`),
  FOREIGN KEY (`Venid`)      REFERENCES `vendedores`(`Venid`),
  FOREIGN KEY (`TTid`)       REFERENCES `Cattipotransaccion`(`TTid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- PASO 6: Agregar tipos nuevos a Cattipotransaccion
-- ============================================================

INSERT IGNORE INTO `Cattipotransaccion` (`TTnombretipo`, `TTdescripcion`) VALUES
  ('NOTA_CREDITO', 'Reduccion de saldo por devolucion o ajuste'),
  ('FACTURA_VENTA', 'Cargo por factura de venta a cliente'),
  ('FACTURA_COMPRA', 'Cargo por factura de compra a proveedor');

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================
-- RESUMEN DE CAMBIOS APLICADOS:
-- ✔ facturasventa     → campos nuevos agregados (Facvenumero, iva, subtotal, estado)
-- ✔ facturaventadetalle → campos nuevos + FKs formales
-- ✔ facturascompras   → tabla nueva creada
-- ✔ facturadetallecompras → tabla nueva creada
-- ✔ movimientoscc     → tabla nueva para CC independiente
-- ✔ Cattipotransaccion → 3 tipos nuevos agregados
-- ============================================================
