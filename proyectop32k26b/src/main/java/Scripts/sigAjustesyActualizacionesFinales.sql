USE sig;

-- Desactivar revisión de llaves foráneas para realizar cambios estructurales
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @dbname = DATABASE();

-- =============================================
-- 1. CAMBIO DE CHAR A VARCHAR (Cuentas)
-- =============================================
-- Esto cambia el tipo de dato sin borrar los registros actuales
ALTER TABLE `cuentasporpagar` MODIFY COLUMN `Cppestado` VARCHAR(50) NOT NULL;
ALTER TABLE `cuentasporcobrar` MODIFY COLUMN `Cpcestado` VARCHAR(50) NOT NULL;


-- =============================================
-- 2. VENDEDORES (Agregar Vencomisiones)
-- =============================================
SET @preparedStatement = (SELECT IF(
  (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS 
   WHERE TABLE_SCHEMA = @dbname AND TABLE_NAME = 'vendedores' AND COLUMN_NAME = 'Vencomisiones') > 0,
  'SELECT "Columna Vencomisiones ya existe.";',
  'ALTER TABLE vendedores ADD COLUMN Vencomisiones DECIMAL(18,2) DEFAULT 0.00;'
));
PREPARE stmt FROM @preparedStatement; EXECUTE stmt; DEALLOCATE PREPARE stmt;


-- =============================================
-- 3. RELACIÓN VENDEDOR EN CUENTAS POR PAGAR
-- =============================================
SET @preparedStatement = (SELECT IF(
  (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS 
   WHERE TABLE_SCHEMA = @dbname AND TABLE_NAME = 'cuentasporpagar' AND COLUMN_NAME = 'Venid') > 0,
  'SELECT "Relacion Venid en CPP ya existe.";',
  'ALTER TABLE cuentasporpagar ADD COLUMN Venid INT DEFAULT NULL, ADD CONSTRAINT fk_cpp_vendedor FOREIGN KEY (Venid) REFERENCES vendedores(Venid);'
));
PREPARE stmt FROM @preparedStatement; EXECUTE stmt; DEALLOCATE PREPARE stmt;


-- =============================================
-- 4. CORRECCIÓN PARA TABLA MARCAS
-- =============================================
-- Estandarizar nombre de columna marcnombre -> marnombre
SET @preparedStatement = (SELECT IF(
  (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = @dbname AND TABLE_NAME = 'marcas' AND COLUMN_NAME = 'marcnombre') > 0,
  'ALTER TABLE marcas CHANGE COLUMN marcnombre marnombre VARCHAR(100) NOT NULL;',
  'SELECT "Columna marnombre ya está correcta.";'
));
PREPARE stmt FROM @preparedStatement; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Agregar marestado
SET @preparedStatement = (SELECT IF((SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = @dbname AND TABLE_NAME = 'marcas' AND COLUMN_NAME = 'marestado') > 0, 'SELECT 1;', 'ALTER TABLE marcas ADD COLUMN marestado TINYINT(1) DEFAULT 1;'));
PREPARE stmt FROM @preparedStatement; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Agregar marcomision
SET @preparedStatement = (SELECT IF((SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = @dbname AND TABLE_NAME = 'marcas' AND COLUMN_NAME = 'marcomision') > 0, 'SELECT 1;', 'ALTER TABLE marcas ADD COLUMN marcomision DECIMAL(10,2) DEFAULT NULL;'));
PREPARE stmt FROM @preparedStatement; EXECUTE stmt; DEALLOCATE PREPARE stmt;


-- =============================================
-- 5. CORRECCIÓN PARA TABLA LINEAS
-- =============================================
SET @preparedStatement = (SELECT IF(
  (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = @dbname AND TABLE_NAME = 'lineas' AND COLUMN_NAME = 'linestado') > 0,
  'SELECT "linestado ya existe.";',
  'ALTER TABLE lineas ADD COLUMN linestado TINYINT(1) DEFAULT 1, ADD COLUMN lincomision DECIMAL(10,2) DEFAULT 0.00;'
));
PREPARE stmt FROM @preparedStatement; EXECUTE stmt; DEALLOCATE PREPARE stmt;


-- =============================================
-- 6. PRODUCTOS (Ajustar nombre de stock)
-- =============================================
SET @preparedStatement = (SELECT IF(
  (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = @dbname AND TABLE_NAME = 'productos' AND COLUMN_NAME = 'Prodstock') > 0,
  'ALTER TABLE productos CHANGE COLUMN Prodstock Prodstockactual INT DEFAULT 0;',
  'SELECT "Columna Prodstockactual ya está correcta.";'
));
PREPARE stmt FROM @preparedStatement; EXECUTE stmt; DEALLOCATE PREPARE stmt;


-- =============================================
-- 7. INSERCIÓN DE DATOS BASE
-- =============================================
INSERT IGNORE INTO `lineas` (`lineaid`, `linnombre`, `linestado`) VALUES (1, 'Ferretería', 1);
INSERT IGNORE INTO `marcas` (`marcaid`, `marnombre`, `marestado`) VALUES (1, 'Truper', 1);
INSERT IGNORE INTO `productos` (`Prodid`, `Prodnombre`, `Prodstockactual`, `lineaid`, `marcaid`) VALUES (1, 'Martillo', 100, 1, 1), (2, 'Pinza', 100, 1, 1);

SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;