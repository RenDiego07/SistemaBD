drop database if exists RentaUnCuento;
create database RentaUnCuento;
use  RentaUnCuento;

create table Repartidor(
	IDrepartidor char(30) NOT NULL PRIMARY KEY,
    nombre Varchar(50) NOT NULL,
    apellido Varchar(50) NOT NULL
);

create table TipoCompra(
	tipoCompra varchar(20) PRIMARY KEY NOT NULL,
    costo float CHECK (costo >0) NOT NULL,
    numeroCuentos int CHECK (numeroCuentos >0) NOT NULL
    
);
create table usuario(
	IDUsuario varchar(10) NOT NULL PRIMARY KEY,
    nombre char(30) NOT NULL,
    apellido char(30) NOT NULL,
    contrasena char(30) NOT NULL,
    telefono char(15) NOT NULL ,
    mail char(50) NOT NULL,
	ultimaCompra varchar (10),
    deudas double not null check(deudas >=0 ),
    FOREIGN KEY(ultimaCompra) REFERENCES TipoCompra(tipoCompra)
);


create table compra(
	IDCompra int(10) not null Primary Key auto_increment,
    fechaCompra  DATE NOT NULL,
    regalo boolean,
    UsuarioId char(10) NOT NULL,
    TipoCompraId varchar(20) NOT NULL,
    cupon boolean NOT NULL, 
	numCuentos int(10) NOT NULL,
    FOREIGN KEY (UsuarioId) references Usuario(IDUsuario),
    FOREIGN KEY (TipoCompraId) references TipoCompra(tipoCompra)

);

create table Pago(
	IDPago int(10) not null PRIMARY KEY auto_increment,
	compraId int(10) NOT NULL,
    total float CHECK (TOTAL>0) NOT NULL,
    fecha DATE NOT NULL,
    tipoPago varchar(15) NOT NULL,
    NumeroCuenta char(30),
    FOREIGN KEY(compraId) REFERENCES compra(IDCompra)
);


create table Envio(
	IDEnvio int(10) not null PRIMARY KEY auto_increment,
    CompraId int(10) NOT NULL,
    usuarioId char(10) NOT NULL,
    repartidorId char(30) NOT NULL,
    direccion Varchar(30) NOT NULL,
    destinatario Varchar(30) NOT NULL,
    fechaEntrega DATE NOT NULL,
    fechaDevolucionFija DATE,
    FOREIGN KEY (usuarioId) REFERENCES Usuario(IDUsuario),
    FOREIGN KEY (repartidorId) REFERENCES repartidor(IDRepartidor),
    FOREIGN KEY (CompraId) REFERENCES Compra(IDCompra)
);

create Table Devolucion(
	IDDevolucion int(10) not null PRIMARY KEY auto_increment,
    envioId int(10) NOT NULL,
    fechaDeDevolucion Date NOT NULL,
    FOREIGN KEY(envioId) REFERENCES envio(IDEnvio)
);

create table Libro(
	IDLibro varchar(15) not null PRIMARY KEY,
	titulo char(30) NOT NULL,
    descripcion Varchar(155) NOT NULL,
    stock int check(stock >= 0) NOT NULL,
    categoria char(30) NOT NULL,
    autor char(30) NOT NULL,
    editorial varchar(30) NOT NULL,
    idioma Varchar(15) NOT NULL
);


create Table EntregaLibros(
	EnvioId int(10),
    LibroId varchar(15),
    PRIMARY KEY( EnvioId, LibroId),
    FOREIGN KEY (EnvioId) REFERENCES Envio(IDEnvio),
    FOREIGN KEY (LibroId) REFERENCES Libro(IDLibro)
);


create Table LibrosAgregados(
	IDLibro varchar(15) NOT NULL,
    IDUsuario varchar (10) NOT NULL,
    estado varchar(10) NOT NULL,
    PRIMARY KEY(IDLibro, IDUsuario),
    FOREIGN KEY (IDLibro) REFERENCES Libro(IDLibro),
    FOREIGN KEY (IDUsuario) REFERENCES usuario(IDUsuario)
);

insert into TipoCompra values ("memb1",2500,1);
insert into TipoCompra values ("memb2",5000,2);
insert into TipoCompra values ("memb3",2500,1);
insert into TipoCompra values ("paq1",350,2);
insert into TipoCompra values ("paq2",500,4);
insert into TipoCompra values ("paq3",1000,8);
insert into TipoCompra values ("paq4",2000,16);

insert into usuario values("1122334455","Harry","Potter","hp123","0995575908","hp321@gmail.com","memb1",0);
insert into usuario values("1221344256","Marco","Solis","mas9","0999377452","mas@gmail.com",null,0);
insert into usuario values("1234543210","Marie","Curie","radio","0989865560","curiepol@gmail.com","memb2",0);
insert into usuario values("0120130145","Elvis","Presley","rock","0967466456","rockroll@gmail.com","paq4",0);
insert into usuario values("0224035901","Harry","Styles","asitwas","0349384556","watermelon@gmail.com","paq1",0);
insert into usuario values("1010235796","Diego","Flores","user1","0994573849","dflores@live.com","paq1",0);
insert into usuario values("9871239871","Alex","Vizuete","user2","0998899765","avizuete@live.com",null,0);
insert into usuario values("1928736455","Ariana","Grande","problem","0989876543","bunny@gmail.com","memb3",0);
insert into usuario values("0605996685","Cecilia","Ortiz","user3","09961032001","cortiz@live.com","memb1",0);
insert into usuario values("0603124512","Milton","Ortiz","user4","0998311282","wladisito@gmail.com","paq2",0);

insert into repartidor values("0005593810","Ron","Weasley");
insert into repartidor values("1719981710","Luna","Love");
insert into repartidor values("1177665455","Bruno","Mars");
insert into repartidor values("0103050709","Conan","Gray");

insert into compra values(0,"2023-01-01",false,"0603124512","paq3",false,8);
insert into compra values(0,"2023-01-10",false,"1122334455","memb1",false,1);
insert into compra values(0,"2023-01-17",false,"0605996685","memb1",false,1);
insert into compra values(0,"2023-01-29",false,"1234543210","memb2",false,2);
insert into compra values(0,"2023-02-18",false,"0603124512","paq2",false,4);
insert into compra values(0,"2022-12-25",false,"0120130145","paq4",false,16);
insert into compra values(0,"2022-12-25",true,"1010235796","paq1",false,2);
insert into compra values(0,"2023-03-13",false,"1928736455","memb3",true,1);
insert into compra values(0,"2023-05-01",false,"0224035901","paq1",false,2);
insert into compra values(0,"2023-06-01",false,"1010235796","paq1",true,2);

insert into pago values(0,1,1000,"2023-01-01","Efectivo",null);
insert into pago values(0,2,2500,"2023-01-10","Tarjeta","121859604932");
insert into pago values(0,3,2500,"2023-01-15","Efectivo",null);
insert into pago values(0,4,5000,"2023-01-29","Tarjeta","18118293845");
insert into pago values(0,5,600,"2023-02-18","Efectivo",null);
insert into pago values(0,6,2000,"2022-12-25","Tarjeta","11928340596");
insert into pago values(0,7,350,"2022-12-25","Efectivo",null);
insert into pago values(0,8,4000,"2023-03-13","Efectivo",null);
insert into pago values(0,9,350,"2023-05-01","Tarjeta","23458122394");
insert into pago values(0,10,300,"2023-06-01","Efectivo",null);

insert into envio values(0,1,"0603124512","0103050709","Viena Y Roma, RBBA","Milton Ortiz","2023-01-06","2023-01-14");
insert into envio values(0,2,"1122334455","1719981710","Calle Diagon, HGWT","Harry Potter","2023-01-17","2023-01-25");
insert into envio values(0,3,"0605996685","0103050709","kingman y Gilbert, RBBA","Cecilia Ortiz","2023-01-21","2023-01-29");
insert into envio values(0,4,"1234543210","0005593810","Aux de Pax, POL","Marie Curie","2023-02-06","2023-02-14");
insert into envio values(0,5,"0603124512","0103050709","Viena Y Roma, RBBA","Milton Ortiz","2023-02-24","2023-03-01");
insert into envio values(0,6,"0120130145","0005593810","Bay City Jail, CAL","Elvis Presley","2022-12-30","2023-01-08");
insert into envio values(0,7,"1221344256","1177665455","Main View Av, PNM","Marco Solis","2022-12-30",null);
insert into envio values(0,8,"1928736455","0005593810","Times Square, NY","Milton Ortiz","2023-03-18","2023-03-26");
insert into envio values(0,9,"0224035901","1177665455","Another World Blvd, LA","Harry Styles","2023-05-05","2023-05-14");
insert into envio values(0,10,"1010235796","1177665455","La Garzota, GYE","Diego Flores","2023-06-07",null);

insert into devolucion values(0,1,"2023-01-14");
insert into devolucion values(0,2,"2023-01-25");
insert into devolucion values(0,3,"2023-01-29");
insert into devolucion values(0,4,"2023-02-14");
insert into devolucion values(0,5,"2023-03-01");
insert into devolucion values(0,6,"2023-01-08");
insert into devolucion values(0,8,"2023-03-26");
insert into devolucion values(0,9,"2023-05-13");

insert into libro values("9788416750375","El Principito",
"El libro de sabiduría más influyente de la era moderna" ,
10,"Novela","Antoine de Saint-Exupéry","Berenice","Español");
insert into libro values("9788448865108","Bluey: Abuelitas",
"Disfruta de las sorprendentes y divertidas aventuras de BLUEY" ,
15,"Cuento","Joe Brumm","BEASCOA","Castellano");
insert into libro values("9789584201386","El Principito",
"El libro de sabiduría más influyente de la era moderna, Edición Especial" ,
5,"Novela","Antoine de Saint-Exupéry","EMECE","Español");
select * from libro;






