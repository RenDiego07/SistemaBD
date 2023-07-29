create table Repartidor(
	IDrepartidor char(30) NOT NULL PRIMARY KEY,
    nombre Varchar(50) NOT NULL,
    apellido Varchar(50) NOT NULL
);
create table membresia (
	IDMembresia varchar(20) NOT NULL PRIMARY KEY,
    costo float NOT NULL CHECK (costo > 350),
    tipo varchar(20) NOT NULL
);

Alter table membresia
ADD Constraint costoCheck check (costo > 350);


CREATE TABLE PAQUETE(
	IDPaquete varchar(20) NOT NULL PRIMARY KEY,
    costo float NOT NULL,
    numeroDeCuentos int
);


create table usuario(
	IDUsuario char(10) NOT NULL PRIMARY KEY,
    nombre char(30) NOT NULL,
    apellido char(30) NOT NULL,
    contrasena char(30) NOT NULL,
    telefono char(15) NOT NULL ,
    mail char(50) NOT NULL,
	membresia varchar (20) NOT NULL,
    deudas double not null check(deudas >=0 )
);








create table compra(
	IDCompra char(10) NOT NULL Primary Key,
    fechaCompra  DATE NOT NULL,
    regalo boolean,
    IDUsuario char(10) NOT NULL,
    IDPaquete varchar(20) NOT NULL,
    IDMembresia Varchar(20) NOT NULL,
    cupon boolean NOT NULL, 
    tipo Varchar(20) NOT NULL,
    FOREIGN KEY (IDUsuario) references Usuario(IDUsuario),
    FOREIGN KEY (IDPAQUETE) references Paquete(IDPaquete),
    FOREIGN KEY (IDMembresia) references Membresia(IDMembresia)
);
create table transferencia(
	IDPago char(10) NOT NULL PRIMARY KEY,
    total float CHECK (TOTAL>0) NOT NULL,
	compraID char(10) NOT NULL,
    fecha DATE NOT NULL,
    cuentaEmisor char(30) NOT NULL
);
create table Efectivo(
	IDPago char(20) Primary KEY NOT NULL,
    total float check(total >0),
    IDCompra char(10),
    FOREIGN KEY (IDCompra) REFERENCES Compra(IDCompra)
);

create table Envio(
	IDEnvio Varchar(30) NOT NULL PRIMARY KEY,
    direccion Varchar(30) NOT NULL,
    destinatario Varchar(30) NOT NULL,
    fechaEntrega DATE NOT NULL,
    fechaDevolucion DATE NOT NULL,
    usuario char(10) NOT NULL,
    repartidor char(30) NOT NULL,
    IDCompra char(10) NOT NULL,
    FOREIGN KEY (usuario) REFERENCES Usuario(IDUsuario),
    FOREIGN KEY (repartidor) REFERENCES repartidor(IDRepartidor),
    FOREIGN KEY (IDCompra) REFERENCES Compra(IDCompra)
);
create Table Devolucion(
	IDDevolucion char(10) PRIMARY KEY NOT NULL,
    envio Varchar(30) NOT NULL,
    fechaDeDevolucion Date NOT NULL,
    FOREIGN KEY(envio) REFERENCES envio(IDEnvio)
);
create table Libro(
	IDLibro Varchar(30) PRIMARY KEY NOT NULL,
    IDEnvio Varchar(30) NOT NULL,
    precio float check(precio >0) NOT NULL,
    descripcion Varchar(155) NOT NULL,
    stock int check(stock >= 0) NOT NULL,
    categoria char(30) NOT NULL,
    autor char(30) NOT NULL,
    titulo char(30) NOT NULL,
    editorial varchar(30) NOT NULL,
    idioma Varchar(15) NOT NULL
);

create Table LibroXEnvio(
	IDEnvio Varchar(30),
    IDLibro Varchar(30),
    PRIMARY KEY( IDEnvio, IDLibro),
    FOREIGN KEY (IDEnvio) REFERENCES Envio(IDEnvio),
    FOREIGN KEY (IDLibro) REFERENCES Libro(IDLibro)
);


create Table LibrosAgregados(
	IDLibro varchar(30) NOT NULL,
    IDUsuario varchar (30) NOT NULL,
    estado varchar(10) NOT NULL,
    PRIMARY KEY(IDLibro, IDUsuario),
    FOREIGN KEY (IDLibro) REFERENCES Libro(IDLibro),
    FOREIGN KEY (IDUsuario) REFERENCES usuario(IDUsuario)
);
