
drop database if exists RentaUnCuento;
create database RentaUnCuento;
use  RentaUnCuento;

create table membresia (
	IDMembresia varchar(20) NOT NULL PRIMARY KEY,
    costo float NOT NULL CHECK (costo >= 2000),
    tipo varchar(20) NOT NULL,
	duracionMeses int  check (duracionMeses >0) NOT NULL,
	numeroDeCuentos  int check(numeroDeCuentos >0)
	);

create table Repartidor(
	IDrepartidor char(30) NOT NULL PRIMARY KEY,
    nombre Varchar(50) NOT NULL,
    apellido Varchar(50) NOT NULL
);

create table Libro(
	IDLibro varchar(15) not null PRIMARY KEY,
	titulo char(30) NOT NULL,
    descripcion Varchar(400) NOT NULL,
    stock int check(stock >= 0) NOT NULL,
    categoria char(30) NOT NULL,
    autor char(30) NOT NULL,
    editorial varchar(30) NOT NULL,
    idioma Varchar(15) NOT NULL,
precio Float check (precio > 0) NOT NULL
);


create table usuario(
	IDUsuario char(10) NOT NULL PRIMARY KEY,
    nombre char(30) NOT NULL,
    apellido char(30) NOT NULL,
    contrasena char(30) NOT NULL,
    telefono char(15) NOT NULL ,
    mail char(50) NOT NULL,
	Tipomembresia varchar (20),
    deudas double not null check(deudas >=0 ),
FOREIGN KEY (TipoMembresia) REFERENCES membresia(IDMembresia)
);

create table compra_libro (
	IDCompra_libro varchar(15) primary key,
	IDLibro varchar(15),
    IDUsuario char(10),
    fechaCompra date,
    cantidad int,
    foreign key(IDLibro) references libro(IDLibro),
    foreign key(IDUsuario) references usuario(IDUsuario)
    
);


CREATE TABLE Paquete(
	ID_Paquete varchar(20) NOT NULL PRIMARY KEY,
    costo float check (costo >= 350) NOT NULL,
tipo varchar(15) NOT NULL,
    numeroDeCuentos int check (numeroDeCuentos>0) NOT NULL
	 

);



create table compra(
	IDCompra int NOT NULL Primary Key auto_increment,
    fechaCompra  DATE NOT NULL,
    regalo boolean,
    UsuarioID char(10) NOT NULL,
    IDPaquete varchar(20),
    IDMembresia Varchar(20),
    cupon boolean NOT NULL, 
    NumCuentos int check (NumCuentos >0) NOT NULL,
    FOREIGN KEY (UsuarioID) references Usuario(IDUsuario),
    FOREIGN KEY (IDPaquete) references Paquete(ID_Paquete),
    FOREIGN KEY (IDMembresia) references Membresia(IDMembresia)
);

create table Pago(
	IDPago int not null PRIMARY KEY auto_increment,
	compraID int(10) NOT NULL,
    total float CHECK (TOTAL>0) NOT NULL,
    fecha DATE NOT NULL,
    tipoPago varchar(15) NOT NULL,
    NumeroCuenta char(30),
 IDCompra_libro varchar(15),
    FOREIGN KEY(CompraId) REFERENCES compra(IDCompra),
  FOREIGN KEY(IDCompra_libro) REFERENCES compra_libro(IDCompra_libro)
);


create table Envio(
IDEnvio int not null PRIMARY KEY auto_increment,
    CompraId int(10),
    usuarioId char(10) NOT NULL,
    repartidorId char(30) NOT NULL,
    direccion Varchar(30) NOT NULL,
    destinatario Varchar(30) NOT NULL,
    fechaEntrega DATE NOT NULL,
    fechaDevolucionFija DATE,
    IDCompra_libro varchar(15),

    FOREIGN KEY (usuarioId) REFERENCES Usuario(IDUsuario),
    FOREIGN KEY (repartidorId) REFERENCES repartidor(IDRepartidor),
    FOREIGN KEY (CompraId) REFERENCES Compra(IDCompra),
    FOREIGN KEY (IDCompra_libro) REFERENCES compra_libro(IDCompra_libro)
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




insert into paquete values ('TP1',350,'Paquete-1',2);
insert into paquete values ('TP2',600,'Paquete-2',4);
insert into paquete values ('TP3',1000,'Paquete-3',8);
insert into paquete values ('TP4',2000,'Paquete-1',16);

insert into membresia values ('MB3', 5000.00, 'Membresia-3',10, 40);

insert into membresia values ('MB2', 5000.00, 'Membresia-2',5, 40);
insert into membresia values ('MB1', 2500.00, 'Membresia-1',5, 20);
insert into usuario values("1122334455","Harry","Potter","hp123","0995575908","hp321@gmail.com","MB1",0);
insert into usuario values("1221344256","Marco","Solis","mas9","0999377452","mas@gmail.com",null,0);
insert into usuario values("1234543210","Marie","Curie","radio","0989865560","curiepol@gmail.com","MB2",0);
insert into usuario values("0120130145","Elvis","Presley","rock","0967466456","rockroll@gmail.com",NULL,0);
insert into usuario values("0224035901","Harry","Styles","asitwas","0349384556","watermelon@gmail.com",NULL,0);
insert into usuario values("1010235796","Diego","Flores","user1","0994573849","dflores@live.com",NULL,0);
insert into usuario values("9871239871","Alex","Vizuete","user2","0998899765","avizuete@live.com",null,0);
insert into usuario values("1928736455","Ariana","Grande","problem","0989876543","bunny@gmail.com","MB3",0);
insert into usuario values("0605996685","Cecilia","Ortiz","user3","09961032001","cortiz@live.com","MB1",0);
insert into usuario values("0603124512","Milton","Ortiz","user4","0998311282","wladisito@gmail.com",NULL,0);


insert into repartidor values("0005593810","Ron","Weasley");
insert into repartidor values("1719981710","Luna","Love");
insert into repartidor values("1177665455","Bruno","Mars");
insert into repartidor values("0103050709","Conan","Gray");
insert into repartidor values('0936654778','Alex', 'Vizuete');
insert into repartidor values('0966889991','Jaime', 'Torres');
insert into repartidor values('0924457719','Ariana', 'Borbor');
insert into repartidor values('0955778881','Edu', 'Navarro');
insert into repartidor values('0925053819','Scarlet', 'Cevallos');
insert into repartidor values('0523574819','Domenika', 'Ordoñez');

insert into compra values(0,"2023-01-01",false,"0603124512","TP3",null,false,8);

insert into compra values(0,"2023-01-10",false,"1122334455",NULL,"MB1",false,1);

insert into compra values(0,"2023-01-17",false,"0605996685",NULL,"MB1",false,1);
insert into compra values(0,"2023-01-29",false,"1234543210",NULL,"MB2",false,2);
insert into compra values(0,"2023-02-18",false,"0603124512","TP2",NULL,false,4);
insert into compra values(0,"2022-12-25",false,"0120130145","TP4",NULL,false,16);
insert into compra values(0,"2022-12-25",true,"1010235796","TP1",NULL,false,2);
insert into compra values(0,"2023-03-13",false,"1928736455",NULL,"MB3",true,1);
insert into compra values(0,"2023-05-01",false,"0224035901","TP1",NULL,false,2);
insert into compra values(0,"2023-06-01",false,"1010235796","TP1",NULL,true,2);

insert into pago values(0,1,1000,"2023-01-01","Efectivo",null,null);
insert into pago values(0,2,2500,"2023-01-10","Tarjeta","121859604932",null);
insert into pago values(0,3,2500,"2023-01-15","Efectivo",null,null);
insert into pago values(0,4,5000,"2023-01-29","Tarjeta","18118293845",null);
insert into pago values(0,5,600,"2023-02-18","Efectivo",null,null);
insert into pago values(0,6,2000,"2022-12-25","Tarjeta","11928340596",null);
insert into pago values(0,7,350,"2022-12-25","Efectivo",null,null);
insert into pago values(0,8,4000,"2023-03-13","Efectivo",null,null);
insert into pago values(0,9,350,"2023-05-01","Tarjeta","23458122394",null);
insert into pago values(0,10,300,"2023-06-01","Efectivo",null,null);
insert into envio values(0,1,"0603124512","0103050709","Viena Y Roma, RBBA","Milton Ortiz","2023-01-06","2023-01-14",null);
insert into envio values(0,2,"1122334455","1719981710","Calle Diagon, HGWT","Harry Potter","2023-01-17","2023-01-25",null);


insert into envio values(0,3,"0605996685","0103050709","kingman y Gilbert, RBBA","Cecilia Ortiz","2023-01-21","2023-01-29",null);
insert into envio values(0,4,"1234543210","0005593810","Aux de Pax, POL","Marie Curie","2023-02-06","2023-02-14",null);
insert into envio values(0,5,"0603124512","0103050709","Viena Y Roma, RBBA","Milton Ortiz","2023-02-24","2023-03-01",null);
insert into envio values(0,6,"0120130145","0005593810","Bay City Jail, CAL","Elvis Presley","2022-12-30","2023-01-08",null);
insert into envio values(0,7,"1221344256","1177665455","Main View Av, PNM","Marco Solis","2022-12-30",null,null);
insert into envio values(0,8,"1928736455","0005593810","Times Square, NY","Milton Ortiz","2023-03-18","2023-03-26",null);
insert into envio values(0,9,"0224035901","1177665455","Another World Blvd, LA","Harry Styles","2023-05-05","2023-05-14",null);
insert into envio values(0,10,"1010235796","1177665455","La Garzota, GYE","Diego Flores","2023-06-07",null,null);



insert into libro values("9788416750375","El Principito",
"El libro de sabiduría más influyente de la era moderna" ,
10,"Novela","Antoine de Saint-Exupéry","Berenice","Español",750);

insert into libro values('9911882277334','TUNTÚN',"cuento divertido sobre un elefante gigante que busca amigos en la selva", 20 ,"Fantasía y Aventura","Anya Damirón y Pablo Pino",'Kalandraka','Español',800);

insert into libro values('9993224561224', '¿Qué es eso?', 'Ardilla, Oso, Pato, Cocodrilo y sus amigos, están tratando de descifrar un misterio', 5, 'Fantasia/Aventura', 'Anya Domirón','Editorial Norma','Español',400);

insert into libro values ('9876543218778','Cosquillas de Lluvia','Un libro sobre el miedo que nos invita a ser valientes para descubrir que lo que nos asusta, casi siempre, está solo en nuestras cabezas.',
5,"Fantasia/Aventura","Anya Damirón","Editorial Norma","Español",1200);
insert into libro values ('9678567398765','¿Quién se Movió?','es una experiencia de lectura, con la música como protagonista. Recrea el clásico juego de las estatuas, invitando a los niños y niñas a bailar con los dinosaurios y a descubrir, quién cambió de posición.',
2,"Fantasia",'Anya Damirón', "Editorial norma", "Castellano",1300);
insert into libro values("9788448865108","Bluey: Abuelitas",
"Disfruta de las sorprendentes y divertidas aventuras de BLUEY",
15,"Cuento","Joe Brumm","BEASCOA","Castellano",860);

insert into libro values ('9250538291224', "Una carrera muy marrón","Antón Piñón es un lemming intrépido y un poco torpón. Entre resbalones y tropezones, vivirá muchas aventuras con las que te divertirás un montón.",
5,"Aventura", "Jose Andrés", "Alba","Español", 500);

insert into libro values ('9988776687609', "Una gallina en la azotea", 'Disfruta en familia de uno de los cuentos más escuchados de nuestro podcast. Es un cuento español que con una divertida historia nos recuerda que aveces las cosas no son como aparentan.',
6,'Fantasia', 'MARQUÉS DE SADE', 'Editorial Kokinos', "Español", 1250);

insert into libro values ("9988976810223", "Un extraño vecino", 'Agitando despacio sus alas, sin levantar apenas viento, llega tranquilo y en silencio, el señor abeja a su hogar. al final de la jornada, tan solo quiere descansar. Cri, cricri, cricricri no puede ser. De lunes a domingo, en las páginas de este libro, el señor abeja no logra dormir por culpa de un imperceptible CRI. ¿Quién será el extraño vecino que emite ese ruido?',
8,"Misterio/Fantasia","Ana Rodriguez", "Amigos de papel","Español", 700);


insert into EntregaLibros values (1,'9993224561224');
insert into EntregaLibros values (2,'9876543218778');
insert into EntregaLibros values (3,'9678567398765');
insert into EntregaLibros values (4,'9788448865108');
insert into EntregaLibros values (5,'9911882277334');
insert into EntregaLibros values (6, "9788416750375");
insert into EntregaLibros values (7, "9911882277334");
insert into EntregaLibros values (8,"9993224561224");
insert into EntregaLibros values (9,"9876543218778");
insert into EntregaLibros values(10, "9678567398765");






insert into librosagregados values("9788416750375", "0605996685", "rentado");
insert into librosagregados values("9788448865108", "1221344256", "no rentado");
insert into librosagregados values("9788448865108", "9871239871", "rentado");
insert into librosagregados values("9876543218778", "0603124512", "rentado");
insert into librosagregados values("9876543218778", "0605996685", "no rentado");
insert into librosagregados values("9911882277334", "0603124512", "rentado");
insert into librosagregados values("9911882277334", "1234543210", "rentado");
insert into librosagregados values("9988776687609", "0120130145", "no rentado");
insert into librosagregados values("9988776687609", "1221344256", "rentado");
insert into librosagregados values("9988976810223", "1122334455", "rentado");




insert into compra_libro values("C3","9993224561224","0224035901", "2023-02-3",1);
insert into compra_libro values("C4","9876543218778","1928736455","2023-05-07",1);
insert into compra_libro values("C5","9678567398765","0224035901","2022-03-03",1);
insert into compra_libro values("C7","9876543218778","9871239871","2023-02-21",1);
insert into compra_libro values("C8","9788416750375","0224035901","2021-08-20",1);
insert into compra_libro values("C9","9988776687609","1122334455","2020-12-25",1);
insert into compra_libro values("C10","9988976810223","1234543210","2023-11-13",1);
insert into compra_libro values("C11","9911882277334","1928736455","2022-07-12",1);
insert into compra_libro values("C12","9788416750375","0605996685","2022-03-22",1);
insert into compra_libro values("C16","9993224561224","1234543210","2022-03-22",1);
