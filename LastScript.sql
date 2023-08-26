USE rentauncuento;
-- triggers 

DELIMITER //
create trigger saveRemovedBooks
before delete on libro
FOR EACH ROW
begin
	insert into historiallibrosborrados 
    values (old.IDLibro, old.titulo, old.descripcion, old.categoria, old.autor, old.editorial, old.idioma, old.precio);
end;
// 
DELIMITER ;


DELIMITER //
create trigger aumentarDeudaMembresia
before insert on compra
for each row
begin
	declare mem varchar(20);
    if(new.IDMembresia is not null) then
		update usuario set deudas = deudas + (select costo from membresia where IDMembresia = new.IDMembresia limit 1) where IDUsuario = new.UsuarioID;
	end if;
end
// DELIMITER ;


DELIMITER // 
create trigger aumentarDeudaPaquete
before insert on compra
for each row
begin
	if(new.IDPaquete is not null) then
		update usuario set deudas = deudas + (select costo from paquete where ID_Paquete = new.IDPaquete) where IDusuario = new.UsuarioID;
    end if;
end;
//
DELIMITER ;

-- insertar un cliente
drop procedure agregarCliente;
DELIMITER // 
create procedure agregarCliente(in InputIDUsuario varchar(10), in InputNombre char(30), in InputApellido char(30), in InputContrasena char(30), in InputTelefono char(15), in InputMail char(50))
BEGIN
	start transaction;
	if exists (select 1 from usuario where IDUsuario = InputIDUsuario) then
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Producto ya existente"; 
	else
		insert into usuario values(InputIDUsuario, InputNombre, InputApellido, InputContrasena, InputTelefono, InputMail, null, 0);
		commit;
        ROLLBACK;
		
 end if;
    
    
END;
// DELIMITER ;

-- actualizar un usuario

DELIMITER //
CREATE PROCEDURE actualizarUsuario(in InputIDUsuario varchar(10), in InputNombre char(30), in InputApellido char(30), in InputContrasena char(30), in InputTelefono char(15), in InputMail char(50) )
begin
	START TRANSACTION;
		if exists(select 1 from usuario where IDUsuario = InputIDUsuario) then
			update usuario set nombre = InputNombre, apellido =InputApellido, contrasena = InputContrasena, telefono = InputTelefono, mail = InputMail
            where IDUsuario = InputIDUsuario;
            COMMIT;
            ROLLBACK;
        else
			SIGNAL SQLSTATE "45000" SET MESSAGE_TEXT = "SE HA INTENTADO ACTUALIZAR UN USAURIO NO EXISTENTE";
        end if;    
end;
// 
DELIMITER ;



-- eliminar un usuario
DELIMITER //
CREATE PROCEDURE eliminarUsuario(IN InputIDUsuario char (10))
begin
	START TRANSACTION;
	if exists (select 1 from usuario where IDUsuario = InputIDUsuario) then
		delete from usuario where IDUsuario = InputIDUsuario;
        COMMIT;
        ROLLBACK;
    else
		SIGNAL SQLSTATE "45000" SET MESSAGE_TEXT = "NO SE PUEDE ELIMINAR UN USUARIO QUE NO EXISTE";
    end if;
	
end;
// DELIMITER ;


-- Views
CREATE VIEW librosEnStock AS
SELECT * FROM libro WHERE stock > 0;
CREATE VIEW comprasConCupon AS
SELECT * FROM compra WHERE cupon = 1;
CREATE VIEW usuariosConMembresia AS 
SELECT * FROM usuario WHERE Tipomembresia IS NOT NULL;
CREATE VIEW usuariosConDeuda AS
SELECT * FROM usuario WHERE deudas > 0;
CREATE VIEW ComprasPorUsuario AS
SELECT
    c.IDUsuario,
	u.NombreUsuario,
    c.IDCompra,
    c.FechaCompra    
FROM compra c JOIN usuario u ON c.IDUsuario = u.IDUsuario;




-- Stored Procedures
DELIMITER //
CREATE PROCEDURE CompraUsuario(IN idusuario VARCHAR(15))
BEGIN
START TRANSACTION;
select * from ComprasPorUsuario where c.IDUsuario=idusuario;
COMMIT;
ROLLBACK;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE insertBook(IN id VARCHAR(15), IN title CHAR(30), IN descp VARCHAR(400), IN stockIn INT, IN cat CHAR(30), IN author CHAR(30), IN edit VARCHAR(30), IN idi VARCHAR(15), IN price FLOAT)
BEGIN
START TRANSACTION;
INSERT INTO libro VALUES(id, title, descp, stockIn, cat, author, edit, idi, price);
COMMIT;
ROLLBACK;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE deleteBook(IN id VARCHAR(15))
BEGIN
START TRANSACTION;
DELETE FROM libro WHERE IDLibro = id;
COMMIT;
ROLLBACK;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE updateBook(IN id VARCHAR(15), IN descp VARCHAR(400), IN stockIn INT, IN cat CHAR(30), IN price FLOAT) 
BEGIN
START TRANSACTION;
UPDATE libro 
SET descripcion = descp, stock = stockIn, categoria = cat, precio = price
WHERE IDLibro = id;
COMMIT;
ROLLBACK;
END //
DELIMITER ;


-- Eliminar un registro de la tabla 'compra'
DELIMITER //
CREATE PROCEDURE DeleteCompra(IN purchaseID INT)
BEGIN
    START TRANSACTION;
    DELETE FROM compra WHERE IDCompra = purchaseID;
    COMMIT;
    ROLLBACK;
END;//
DELIMITER ;

-- Actualizar un registro en la tabla 'compra'
DELIMITER //
CREATE PROCEDURE UpdateCompra(IN purchaseID INT, IN newFechaCompra DATE,
    IN newRegalo BOOLEAN, IN newCupon BOOLEAN)
BEGIN
    START TRANSACTION;
    UPDATE compra
    SET fechaCompra = newFechaCompra,
        regalo = newRegalo,
        cupon = newCupon
    WHERE IDCompra = purchaseID;

    COMMIT;
    Rollback;
END;
//
DELIMITER ;

-- Insertar un nuevo registro en la tabla 'compra'
DELIMITER //
CREATE PROCEDURE InsertIntoCompra(
    IN newFechaCompra DATE,
    IN newRegalo BOOLEAN,
    IN newUsuarioID CHAR(10),
    IN newIDPaquete VARCHAR(20),
    IN newIDMembresia VARCHAR(20),
    IN newCupon BOOLEAN,
    IN newNumCuentos INT
)
BEGIN    
    START TRANSACTION;
    INSERT INTO compra (fechaCompra, regalo, UsuarioID, IDPaquete, IDMembresia, cupon, NumCuentos)
    VALUES (newFechaCompra, newRegalo, newUsuarioID, newIDPaquete, newIDMembresia, newCupon, newNumCuentos);

    COMMIT;
    ROLLBACK;
END;
//
DELIMITER ;

-- Indexes
CREATE INDEX categoryIndex ON libro(categoria);
CREATE INDEX namesIndex ON usuario(nombre, apellido);
CREATE INDEX FechaCompraIndex ON compra (FechaCompra);


-- Crear usuarios
CREATE USER 'admin1' IDENTIFIED BY 'adminrc';
CREATE USER 'contador' IDENTIFIED BY 'contador123';
CREATE USER 'repartidor' IDENTIFIED BY 'repenvios';
CREATE USER 'usuario1' IDENTIFIED BY 'user';
CREATE USER 'auditor' IDENTIFIED BY 'control';

-- Privilegios al usuario 'admin1'
GRANT ALL PRIVILEGES ON *.* TO 'admin' WITH GRANT OPTION;
 
-- Permisos al usuario 'contador'
GRANT EXECUTE ON PROCEDURE DeleteCompra TO 'contador';
GRANT EXECUTE ON PROCEDURE UpdateCompra TO 'contador';
GRANT EXECUTE ON PROCEDURE InsertIntoCompra TO 'contador';
GRANT SELECT ON Compra TO 'contador';
GRANT SELECT ON Pago TO 'contador';

-- Permisos al usuario 'repartidor'
GRANT EXECUTE ON PROCEDURE DeleteEnvio TO 'repartidor';
GRANT SELECT ON Envio TO 'repartidor';
GRANT SELECT ON Usuario TO 'repartidor';



-- Permisos al usuario 'auditor'
GRANT DELETE,SELECT ON Libro TO 'auditor';
GRANT DELETE,SELECT ON usuario TO 'auditor';
GRANT EXECUTE ON PROCEDURE agregarCliente TO 'repartidor';
GRANT EXECUTE ON PROCEDURE actualizarUsuario TO 'repartidor';
GRANT EXECUTE ON PROCEDURE eliminarUsuario TO 'repartidor';

GRANT EXECUTE ON PROCEDURE updateBook TO 'repartidor';
GRANT EXECUTE ON PROCEDURE deleteBook TO 'repartidor';
GRANT EXECUTE ON PROCEDURE insertBook TO 'repartidor';


GRANT SELECT ON librosEnStock TO 'auditor';
GRANT SELECT ON comprasConCupon TO 'auditor';
GRANT SELECT ON usuariosConMembresia TO 'auditor';
GRANT SELECT ON usuariosConDeuda TO 'auditor';


-- Permisos al usuario 'usuario1'
GRANT SELECT ON librosEnStock TO 'usuario1';
GRANT EXECUTE ON PROCEDURE CompraUsuario TO 'usuario1';


FLUSH PRIVILEGES;



