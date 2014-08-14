-- ---------------------------------------------------- --
-- ------------Base De Datos CarritoCompras------------ --
-- ---------------------------------------------------- --
create database CarritoCompras;

use CarritoCompras;

grant all on CarritoCompras.* to 'db_admin'@'localhost' identified by 'db_admin';

create table CarritoCompras.usuarios (
	id_usuario int(6) primary key auto_increment,
	email varchar(60) not null unique,
	nombre varchar(20) not null,
	apellido varchar(20) not null,
	clave varchar(35) not null,
	estado VARCHAR(10) default 'ACTIVO',
	fecha_creacion datetime default current_timestamp,
	role varchar(20) not null,
	check (estado in ('ACTIVO','INACTIVO')),
	check (role in ('ADMIN','PARTICIPANTE'))
);

create table carritocompras.pais(
	id_pais int(6) primary key auto_increment,
	nombre varchar(60) not null, 
	estado VARCHAR(10) default 'ACTIVO',
	check (estado in ('ACTIVO','INACTIVO'))
);

create table carritocompras.direccion_envio (
	id_direccion int(6) primary key auto_increment,
	id_usuario int(6) not null, 
	id_pais int(6) not null, 
	nombre_contacto varchar(30) not null,
	Calle1 varchar(60) not null,
	Calle2 varchar(60),
	ciudad varchar(30) not null,
	zip_code int (10) not null,
	telefono varchar(15),
	principal boolean default false comment 'campo utilizado para saber si la direccion esta marcada como principal',
	foreign key (id_usuario) references carritocompras.usuarios (id_usuario),
	foreign key (id_pais) references carritocompras.pais (id_pais)
);

create table CarritoCompras.productos (
	id_producto int(6) primary key auto_increment,
	titulo varchar(100) not null, 
	Descripcion varchar(2000) not null,
	precio decimal(19,4) not null, 
	cantidad int(20) not null
);

create table carritocompras.fotos_productos (
	id_foto int(6) primary key auto_increment,
	id_producto int(6) not null,
	foto varchar(200) not null,
	ruta varchar(200) not null,
	foreign key (id_producto) references carritoCompras.productos(id_producto)
);

create table carritocompras.carrito (
	id_carrito int(6) primary key auto_increment,
	id_usuario int(6) not null,
	id_producto int(6) not null,
	fecha datetime not null default current_timestamp,
	foreign key (id_usuario) references carritocompras.usuarios(id_usuario),
	foreign key (id_producto) references carritocompras.productos(id_producto)
);

create table CarritoCompras.factura (
	id_factura int(6) primary key auto_increment,
	id_usuario int(6) not null,
	fecha datetime default current_timestamp,
	direccion_envio varchar(2000),
	total decimal(19,4),
	foreign key (id_usuario) references usuarios(id_usuario)
);

create table CarritoCompras.detalle_factura (
	id_detalle_factura int(6) auto_increment,
	id_factura int(6) not null, 
	id_producto int(6) not null,
	cantidad int(6) not null, 
	precio decimal(19,4) not null, 
	total  decimal(19,4) not null,
	primary key (id_detalle_factura, id_factura),
	foreign key (id_factura) references CarritoCompras.factura(id_factura),
	foreign key (id_producto) references CarritoCompras.productos(id_producto)
);

-- --------------------------------------------------------------- --
-- -----------------Usuarios de prueba --------------------------- --
-- --------------------------------------------------------------- --
insert into usuarios (email,nombre, apellido, clave,role) values ("ing.armandotorres@gmail.com","Armando","Torres","atorres","ADMIN");

-- --------------------------------------------------------------- --
-- ----------------Paises de prueba------------------------------- --
-- --------------------------------------------------------------- --
insert into pais (nombre) values ("Republica Dominicana");
insert into pais (nombre) values ("Estados Unidos");
insert into pais (nombre) values ("Cuba");
insert into pais (nombre) values ("Puerto Rico");
insert into pais (nombre) values ("Jamaica");
insert into pais (nombre) values ("Haiti");
insert into pais (nombre) values ("Venezuela");
insert into pais (nombre) values ("Colombia");
insert into pais (nombre) values ("Panama");
insert into pais (nombre) values ("Nicaragua");
insert into pais (nombre) values ("Costa Rica");
insert into pais (nombre) values ("Honduras");
insert into pais (nombre) values ("El Salvador");
insert into pais (nombre) values ("Guatemala");
insert into pais (nombre) values ("Mexico");
insert into pais (nombre) values ("Brazil");

-- --------------------------------------------------------------- --
-- ----------------Direcciones de prueba-------------------------- --
-- --------------------------------------------------------------- --
insert into direccion_envio (id_usuario, id_pais, nombre_contacto, calle1, calle2, ciudad, zip_code, telefono,principal)
	values (1,1,"Armando Torres","Calle primera, esquina tercera","Sector los colonos","La Romana",22000,"8099734102",true);
insert into direccion_envio (id_usuario, id_pais, nombre_contacto, calle1, ciudad, zip_code, telefono)
	values (1,1,"Armando Torres","Villa Espana","La Romana",22000,"8099734102");


-- --------------------------------------------------------------- --
-- ----------------Productos de prueba---------------------------- --
-- --------------------------------------------------------------- --
insert into productos (titulo,descripcion, precio, cantidad) values ("Intel core i5 4670K", "Procesador con 3.4ghz de velocidad de reloj, 4 nucleos, 3.8ghz de frecuencia turbo, arquitectura de 64bit, muy recomendado para juegos exigentes",10440.00, 5);
insert into productos (titulo,descripcion, precio, cantidad) values ("Asus Z87-A MotherBoard", "Designed for those requiring frequent BIOS access, users can easily enter the BIOS when the PC is on standby power with a simple press of a button. It saves you time during boot-up as you don’t have to repeatedly press the DEL key. Convenience and ease at their best!",5986.73, 5);
insert into productos (titulo,descripcion, precio, cantidad) values ("Corsair 750M PowerSupply", "CX Series Modular power supply units are an excellent choice for basic system builds and desktop PC computer upgrades, offering high reliability, low noise, and the flexibility of modular cabling.",3500, 5);
insert into productos (titulo,descripcion, precio, cantidad) values ("Corsair xms 8GB Ram Memomy", "4GB XMS Memory kit for dual channel systems, 1600MHz, 9-9-9-24, 1.65V",3700, 2);
insert into productos (titulo,descripcion, precio, cantidad) values ("Western Digital 1Tb Hard Disk", "El disco duro portátil WD Elements incluye una versión de prueba del software de copia de seguridad WD SmartWare Pro, que permite realizar copias de seguridad de los archivos en el disco WD Elements o en la nube usando su cuenta de DropBox",3000, 0);
insert into productos (titulo,descripcion, precio, cantidad) values ("EVGA gtx 760 sc", "EVGA GeForce GTX760 SuperClocked w/EVGA ACX Cooler 2GB GDDR5 256bit, Dual-Link DVI-I, DVI-D, HDMI,DP, SLI Ready Graphics Card (02G-P4-2765-KR) Graphics Cards 02G-P4-2765-KR",12000, 2);

-- --------------------------------------------------------------- --
-- ----------------Fotos Productos de prueba---------------------- --
-- --------------------------------------------------------------- --
insert into fotos_productos (id_producto, foto, ruta) values (1,"07-08-2014-153217.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (2,"07-08-2014-153611.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (2,"08-08-2014-144212.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (2,"08-08-2014-144215.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (2,"08-08-2014-144218.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (3,"08-08-2014-075546.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (3,"08-08-2014-144659.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (3,"08-08-2014-144702.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (3,"08-08-2014-144705.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (4,"08-08-2014-075727.png","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (4,"08-08-2014-144908.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (4,"08-08-2014-144912.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (5,"08-08-2014-075828.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (5,"08-08-2014-144927.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (5,"08-08-2014-144931.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (5,"08-08-2014-144934.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (6,"08-08-2014-075911.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (6,"08-08-2014-144947.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (6,"08-08-2014-144951.jpg","2014/08");
insert into fotos_productos (id_producto, foto, ruta) values (6,"08-08-2014-144955.jpg","2014/08");

-- --------------------------------------------------------------- --
-- ----------------Facturas de prueba---------------------------- --
-- --------------------------------------------------------------- --
insert into factura (id_usuario, fecha, total, direccion_envio) values (1,current_timestamp,(select sum(precio) from productos),"Calle primera, esquina tercera, Sector los colonos, La Romana, 22000");
insert into detalle_factura(id_factura, id_producto, cantidad, precio, total) 
select 1, id_producto, 1, precio, precio from productos;

-- --------------------------------------------------------------- --
-- ----------------Carrito de prueba---------------------------- --
-- --------------------------------------------------------------- --
insert into carrito (id_usuario, id_producto) values (1,1);
insert into carrito (id_usuario, id_producto) values (1,2);
insert into carrito (id_usuario, id_producto) values (1,3);
insert into carrito (id_usuario, id_producto) values (1,4);
insert into carrito (id_usuario, id_producto) values (1,6);