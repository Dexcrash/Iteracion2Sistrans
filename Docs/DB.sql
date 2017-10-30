DROP TABLE CATEGORIA; 
DROP TABLE PRODUCTO_INGREDIENTE;
DROP TABLE EQUIVALENCIAINGREDIENTES;
DROP TABLE EQUIVALENCIAPRODUCTOS;
DROP TABLE PEDIDO_MENUS;
DROP TABLE PEDIDO_PRODUCTOS;
DROP TABLE RESERVA;
DROP TABLE CONDICION;
DROP TABLE ESPACIO;
DROP TABLE PEDIDO;
DROP TABLE INGREDIENTE;
DROP TABLE PREFERENCIA;
DROP TABLE MENU;
DROP TABLE PRODUCTO;
DROP TABLE RESTAURANTE;
DROP TABLE USUARIO;
DROP TABLE ZONA;


 CREATE TABLE ZONA
 (
   NOMBRE VARCHAR2(255 BYTE) NOT NULL, 
   CONSTRAINT ZONA_PK PRIMARY KEY (NOMBRE)
   
 );
 
 CREATE TABLE USUARIO 
   (
     IDENTIFICACION VARCHAR2(20 BYTE) NOT NULL, 
	 NOMBRE VARCHAR2(20 BYTE), 
	 CORREOELECTRONICO VARCHAR2(50 BYTE), 
	 ROL VARCHAR2(20 BYTE) NOT NULL, 
	 PASS VARCHAR2(20 BYTE), 
	 CONSTRAINT USUARIO_PK PRIMARY KEY (IDENTIFICACION)
	);
	
	CREATE TABLE RESTAURANTE
   (	ID NUMBER NOT NULL, 
	NOMBRE VARCHAR2(20 BYTE) NOT NULL, 
	REPRESENTANTE VARCHAR2(20 BYTE) NOT NULL, 
	PAGINAWEB VARCHAR2(20 BYTE), 
	BALANCEPRECIO NUMBER NOT NULL, 
	BALANCECOSTO NUMBER NOT NULL, 
	TIPO VARCHAR2(20 BYTE) NOT NULL, 
	ID_USUARIO VARCHAR2(20 BYTE) NOT NULL, 
	NOMBRE_ZONA VARCHAR2(20 BYTE) NOT NULL, 
	 CONSTRAINT RESTAURANTE_PK PRIMARY KEY (ID),
	 CONSTRAINT RESTAURANTE_FK1 FOREIGN KEY (NOMBRE_ZONA)
	 REFERENCES ZONA (NOMBRE), 
	 CONSTRAINT RESTAURANTE_FK2 FOREIGN KEY (ID_USUARIO)
	  REFERENCES USUARIO (IDENTIFICACION) 
   );
   
    CREATE TABLE PRODUCTO
   (	ID NUMBER NOT NULL, 
	NOMBRE VARCHAR2(20 BYTE) NOT NULL, 
	DESCRIPCION VARCHAR2(20 BYTE), 
	TRADUCCION VARCHAR2(20 BYTE), 
	TIEMPOPREPARACION NUMBER NOT NULL, 
	COSTO NUMBER NOT NULL, 
	PRECIO NUMBER NOT NULL, 
	DISPONIBLES NUMBER NOT NULL, 
	TIPO VARCHAR2(20 BYTE) NOT NULL, 
	ID_RESTAURANTE NUMBER NOT NULL, 
	MAXIMOPRODUCTOS NUMBER NOT NULL, 
	 CONSTRAINT PRODUCTOS_PK PRIMARY KEY (ID),
	 CONSTRAINT PRODUCTO_CHK1 CHECK (tipo IN ('Bebida','Plato fuerte','Acompañamiento','Postre','Entrada')), 
	 CONSTRAINT PRODUCTO_FK2 FOREIGN KEY (ID_RESTAURANTE)
	  REFERENCES RESTAURANTE (ID)
   );
   
    CREATE TABLE MENU
   (	ID VARCHAR2(20 BYTE) NOT NULL, 
	NOMBRE VARCHAR2(20 BYTE) NOT NULL, 
	COSTO NUMBER NOT NULL, 
	PRECIO NUMBER NOT NULL, 
	ID_RESTAURANTE NUMBER NOT NULL, 
	ID_PRODUCTOENTRADA NUMBER, 
	ID_PRODUCTOACOMPAÑAMIENTO NUMBER, 
	ID_PRODUCTOPLATOFUERTE NUMBER, 
	ID_PRODUCTOPOSTRE NUMBER, 
	ID_PRODUCTOBEBIDA NUMBER, 
	 CONSTRAINT MENU_PK PRIMARY KEY (ID), 
	 CONSTRAINT MENU_FK1 FOREIGN KEY (ID_RESTAURANTE)
	  REFERENCES RESTAURANTE (ID), 
	 CONSTRAINT MENU_FK2 FOREIGN KEY (ID_PRODUCTOENTRADA)
	  REFERENCES PRODUCTO (ID) , 
	 CONSTRAINT MENU_FK3 FOREIGN KEY (ID_PRODUCTOACOMPAÑAMIENTO)
	 REFERENCES PRODUCTO (ID) ,  
	 CONSTRAINT MENU_FK4 FOREIGN KEY (ID_PRODUCTOPLATOFUERTE)
	 REFERENCES PRODUCTO (ID) , 
	 CONSTRAINT MENU_FK5 FOREIGN KEY (ID_PRODUCTOPOSTRE)
	 REFERENCES PRODUCTO (ID) , 
	 CONSTRAINT MENU_FK6 FOREIGN KEY (ID_PRODUCTOBEBIDA)
	  REFERENCES PRODUCTO (ID) 
   );
   
   CREATE TABLE PREFERENCIA 
   (	TIPO VARCHAR2(20 BYTE) NOT NULL , 
	VALOR VARCHAR2(20 BYTE) NOT NULL , 
	ID_CLIENTE VARCHAR2(20 BYTE) NOT NULL , 
	 CONSTRAINT PREFERENCIA_PK PRIMARY KEY (TIPO, VALOR, ID_CLIENTE), 
	 CONSTRAINT PREFERENCIA_CHK1 CHECK (TIPO IN ('Condicion','Espacio','Ingrediente','Precio','Restaurante','Zona')) , 
	 CONSTRAINT PREFERENCIA_FK1 FOREIGN KEY (ID_CLIENTE)
	  REFERENCES USUARIO (IDENTIFICACION) 
   );
   
   
  CREATE TABLE INGREDIENTE
   (	ID NUMBER NOT NULL , 
      NOMBRE VARCHAR2(20 BYTE) NOT NULL , 
	DESCRIPCION VARCHAR2(20 BYTE), 
	TRADUCCION VARCHAR2(20 BYTE), 
	 CONSTRAINT INGREDIENTES_PK PRIMARY KEY (ID)
   );
   
   
  CREATE TABLE PEDIDO
   (	FECHAHORA DATE NOT NULL , 
	ID_CLIENTE VARCHAR2(20 BYTE) NOT NULL , 
	ID NUMBER NOT NULL , 
	SERVIDO NUMBER NOT NULL , 
	ID_MESA NUMBER NOT NULL,
	 CONSTRAINT PEDIDO_PK PRIMARY KEY (ID), 
	 CONSTRAINT PEDIDO_CHK1 CHECK (servido IN (1, 0) ) , 
	 CONSTRAINT PEDIDO_FK2 FOREIGN KEY (ID_CLIENTE)
	  REFERENCES USUARIO (IDENTIFICACION) 
   ) ;


  
  CREATE TABLE ESPACIO 
   (	ABIERTO NUMBER NOT NULL , 
	CAPACIDAD NUMBER NOT NULL , 
	ACCESO_ESPECIAL NUMBER NOT NULL , 
	NOMBRE_ZONA VARCHAR2(20 BYTE) NOT NULL , 
	ID NUMBER NOT NULL, 
	 CONSTRAINT ESPACIO_PK PRIMARY KEY (ID), 
	 CONSTRAINT ESPACIO_CHK1 CHECK (ABIERTO IN (1, 0)) , 
	 CONSTRAINT ESPACIO_CHK2 CHECK (ACCESO_ESPECIAL IN (1, 0)) , 
	 CONSTRAINT ESPACIO_FK1 FOREIGN KEY (NOMBRE_ZONA)
	  REFERENCES ZONA (NOMBRE) 
   );

   
  CREATE TABLE CONDICION 
   (	NOMBRE_CONDICION VARCHAR2(20 BYTE) NOT NULL , 
	ID_ESPACIO NUMBER NOT NULL , 
	 CONSTRAINT CONDICION_PK PRIMARY KEY (NOMBRE_CONDICION, ID_ESPACIO), 
	  CONSTRAINT CONDICION_CHK1 CHECK (NOMBRE_CONDICION IN ('Barra ensalada','Pago en efectivo','Guardería','Iluminación','Meseros','Música','Parrilla','Pago con tarjeta')) , 
	 CONSTRAINT CONDICION_FK1 FOREIGN KEY (ID_ESPACIO)
	  REFERENCES ESPACIO (ID) 
   );
   
   
  CREATE TABLE RESERVA
   (	FECHAHORA DATE NOT NULL , 
	NUMCOMENSALES NUMBER(*,0) NOT NULL , 
	ID_CLIENTE VARCHAR2(20 BYTE) NOT NULL , 
	NOMBRE_ZONA VARCHAR2(20 BYTE), 
	ID_MENU VARCHAR2(20 BYTE) NOT NULL , 
	DURACION NUMBER NOT NULL , 
	 CONSTRAINT RESERVA_PK PRIMARY KEY (FECHAHORA, ID_CLIENTE), 
	 CONSTRAINT RESERVA_FK1 FOREIGN KEY (ID_CLIENTE)
	  REFERENCES USUARIO (IDENTIFICACION), 
	 CONSTRAINT RESERVA_FK2 FOREIGN KEY (NOMBRE_ZONA)
	  REFERENCES ZONA (NOMBRE) , 
	 CONSTRAINT RESERVA_FK3 FOREIGN KEY (ID_MENU)
	  REFERENCES MENU (ID) 
   );
   
   
  CREATE TABLE PEDIDO_PRODUCTOS
   (	ID_PEDIDO NUMBER NOT NULL , 
	ID_PRODUCTO NUMBER NOT NULL , 
	CANTIDAD NUMBER NOT NULL , 
	 CONSTRAINT PEDIDO_PRODUCTOS_PK PRIMARY KEY (ID_PEDIDO, ID_PRODUCTO), 
	 CONSTRAINT PEDIDO_PRODUCTOS_FK1 FOREIGN KEY (ID_PEDIDO)
	  REFERENCES PEDIDO (ID), 
	 CONSTRAINT PEDIDO_PRODUCTOS_FK2 FOREIGN KEY (ID_PRODUCTO)
	  REFERENCES PRODUCTO (ID) 
   );
   
   
  CREATE TABLE PEDIDO_MENUS 
   (	ID_PEDIDO NUMBER NOT NULL , 
	ID_MENU VARCHAR2(20 BYTE) NOT NULL , 
	CANTIDAD NUMBER NOT NULL , 
	ID_ENTRADA NUMBER, 
	ID_ACOMPAÑAMIENTO NUMBER, 
	ID_PLATOFUERTE NUMBER, 
	ID_BEBIDA NUMBER, 
	ID_POSTRE NUMBER, 
	 CONSTRAINT PEDIDO_MENUS_PK PRIMARY KEY (ID_PEDIDO, ID_MENU), 
	 CONSTRAINT PEDIDO_MENUS_FK1 FOREIGN KEY (ID_MENU)
	  REFERENCES MENU (ID) , 
	 CONSTRAINT PEDIDO_MENUS_FK2 FOREIGN KEY (ID_PEDIDO)
	  REFERENCES PEDIDO (ID),
	 CONSTRAINT PEDIDO_MENUS_FK3 FOREIGN KEY (ID_ENTRADA)
	  REFERENCES PRODUCTO (ID), 
	 CONSTRAINT PEDIDO_MENUS_FK4 FOREIGN KEY (ID_ACOMPAÑAMIENTO)
	  REFERENCES PRODUCTO (ID), 
	 CONSTRAINT PEDIDO_MENUS_FK5 FOREIGN KEY (ID_PLATOFUERTE)
		  REFERENCES PRODUCTO (ID),  
	 CONSTRAINT PEDIDO_MENUS_FK6 FOREIGN KEY (ID_BEBIDA)
	 	  REFERENCES PRODUCTO (ID), 
	 CONSTRAINT PEDIDO_MENUS_FK7 FOREIGN KEY (ID_POSTRE)
		  REFERENCES PRODUCTO (ID)
   );
   
   
  CREATE TABLE EQUIVALENCIAPRODUCTOS
   (	ID_ORIGINAL NUMBER NOT NULL , 
	ID_EQUIVALENCIA NUMBER NOT NULL , 
	 CONSTRAINT EQUIVALENCIAPRODUCTOS_PK PRIMARY KEY (ID_ORIGINAL, ID_EQUIVALENCIA), 
	 CONSTRAINT EQUIVALENCIAPRODUCTOS_FK1 FOREIGN KEY (ID_EQUIVALENCIA)
	  REFERENCES PRODUCTO (ID) , 
	 CONSTRAINT EQUIVALENCIAPRODUCTOS_FK2 FOREIGN KEY (ID_ORIGINAL)
	  REFERENCES PRODUCTO(ID) 
   );
   
   
  CREATE TABLE EQUIVALENCIAINGREDIENTES 
   (	ID_ORIGINAL NUMBER NOT NULL , 
	ID_EQUIVALENCIA NUMBER NOT NULL , 
	 CONSTRAINT EQUIVALENCIAINGREDIENTES_PK PRIMARY KEY (ID_ORIGINAL, ID_EQUIVALENCIA), 
	 CONSTRAINT EQUIVALENCIAINGREDIENTES_FK1 FOREIGN KEY (ID_EQUIVALENCIA)
	  REFERENCES INGREDIENTE (ID) , 
	 CONSTRAINT EQUIVALENCIAINGREDIENTES_FK2 FOREIGN KEY (ID_ORIGINAL)
	  REFERENCES INGREDIENTE (ID) 
   );
   
   
  CREATE TABLE PRODUCTO_INGREDIENTE
   (	ID_PRODUCTO NUMBER NOT NULL , 
	ID_INGREDIENTE NUMBER NOT NULL , 
	 CONSTRAINT PRODUCTO_INGREDIENTE_PK PRIMARY KEY (ID_PRODUCTO, ID_INGREDIENTE), 
	 CONSTRAINT PRODUCTO_INGREDIENTE_FK1 FOREIGN KEY (ID_PRODUCTO)
	  REFERENCES PRODUCTO (ID) , 
	 CONSTRAINT PRODUCTO_INGREDIENTE_FK2 FOREIGN KEY (ID_INGREDIENTE)
	  REFERENCES INGREDIENTE (ID) 
   );
   
   
  CREATE TABLE CATEGORIA 
   (	NOMBRE VARCHAR2(20 BYTE) NOT NULL , 
	ID_PRODUCTO NUMBER NOT NULL , 
	 CONSTRAINT CATEGORIA_PK PRIMARY KEY (NOMBRE, ID_PRODUCTO), 
	 CONSTRAINT CATEGORIA_FK1 FOREIGN KEY (ID_PRODUCTO)
	  REFERENCES PRODUCTO (ID) 
   );
   
   INSERT INTO ZONA (NOMBRE) values ('Inferior');
   INSERT INTO ZONA (NOMBRE) values ('Norte');
   INSERT INTO ZONA (NOMBRE) values ('Occidental');
   INSERT INTO ZONA (NOMBRE) values ('Oriental');
   INSERT INTO ZONA (NOMBRE) values ('Superior');
   INSERT INTO ZONA (NOMBRE) values ('Sur');
   
   Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('6','Fernando','fernando@sistrans.com','Cliente','15489');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('39','Andres Camilo','andresca@hotmail.com','No registrado','null');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('7','Andy','andy_sobrio@gmail.com','Cliente','alzatucerveza');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('17','Chick And Chips','chickandchips@restaurante.com','Restaurante','chick456');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('0','Claudia','claudia@sistrans.com','Cliente','1123');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('1','Germán','german@sistrans.com','Cliente','345');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('2','Camilo','ca.sanchez38@uniandes.edu.co','Cliente','556');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('3','María','mp.franco10@uniandes.edu.co','Cliente','234');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('4','Paula','paula@hotmail.com','Cliente','122');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('5','Matty','matty1969@hotmail.com','Cliente','1234');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('11','El corral','elcorral@restaurante.com','Restaurante','a125f');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('12','Sopas abuela','abuela@restaurante.com','Restaurante','sf45');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('13','Subway','subway@restaurante.com','Restaurante','sf3455');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('14','Konnichiwa','sushi@restaurante.com','Restaurante','ssg677');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('15','One Burrito','mexico@restaurante.com','Restaurante','sff555');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('16','Doña blanca','panaderia@restaurante.com','Restaurante','hfds55');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('21','Admonsis','admon@admin.com','Administrador','1');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('22','Departamento','depto@admin.com','Administrador','2');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('23','Jefe','jefe@admin.com','Administrador','3');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('24','Presidente','pres@admin.com','Administrador','4');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('25','Director','dire@admin.com','Administrador','5');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('26','Rey','rey@admin.com','Administrador','6');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('27','Juan Manuel','juanm@admin.com','Administrador','7');
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('31',null,null,'No registrado',null);
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('32',null,null,'No registrado',null);
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('33',null,null,'No registrado',null);
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('34',null,null,'No registrado',null);
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('35',null,null,'No registrado',null);
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('36',null,null,'No registrado',null);
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('37',null,null,'No registrado',null);
Insert into USUARIO (IDENTIFICACION,NOMBRE,CORREOELECTRONICO,ROL,PASS) values ('38',null,null,'No registrado',null);
  

Insert into RESTAURANTE (ID,NOMBRE,REPRESENTANTE,PAGINAWEB,BALANCEPRECIO,BALANCECOSTO,TIPO,ID_USUARIO,NOMBRE_ZONA) values ('8','Chick And Chips','Camilo Sánchez','chickandchips.com','400000','200000','null','17','Oriental');
Insert into RESTAURANTE (ID,NOMBRE,REPRESENTANTE,PAGINAWEB,BALANCEPRECIO,BALANCECOSTO,TIPO,ID_USUARIO,NOMBRE_ZONA) values ('1','Hamburguesas EC','Juan','hamburguesas.com','100000','50000','Rapida','11','Oriental');
Insert into RESTAURANTE (ID,NOMBRE,REPRESENTANTE,PAGINAWEB,BALANCEPRECIO,BALANCECOSTO,TIPO,ID_USUARIO,NOMBRE_ZONA) values ('2','Sopas de la abuela','Miguel','sopas.com','2000000','500000','Casera','12','Occidental');
Insert into RESTAURANTE (ID,NOMBRE,REPRESENTANTE,PAGINAWEB,BALANCEPRECIO,BALANCECOSTO,TIPO,ID_USUARIO,NOMBRE_ZONA) values ('3','Subway R','Matías','subway.com','1290000','100000','Sandwich','13','Norte');
Insert into RESTAURANTE (ID,NOMBRE,REPRESENTANTE,PAGINAWEB,BALANCEPRECIO,BALANCECOSTO,TIPO,ID_USUARIO,NOMBRE_ZONA) values ('4','Sushi Kon','Manuel',null,'1220000','5000000','Japonesa','14','Oriental');
Insert into RESTAURANTE (ID,NOMBRE,REPRESENTANTE,PAGINAWEB,BALANCEPRECIO,BALANCECOSTO,TIPO,ID_USUARIO,NOMBRE_ZONA) values ('5','One Burrito','Carlos','oneburrito.edu.co','12990000','600000','Mexicana','15','Oriental');
Insert into RESTAURANTE (ID,NOMBRE,REPRESENTANTE,PAGINAWEB,BALANCEPRECIO,BALANCECOSTO,TIPO,ID_USUARIO,NOMBRE_ZONA) values ('6','Panadería DB','Samuel',null,'110000','40000','Casera','16','Inferior');
Insert into RESTAURANTE (ID,NOMBRE,REPRESENTANTE,PAGINAWEB,BALANCEPRECIO,BALANCECOSTO,TIPO,ID_USUARIO,NOMBRE_ZONA) values ('7','Ajiaquitos','Camilo','ajiaco.com','400000','20000','Casera','11','Occidental');
   

Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('1','Sopa','De verduras','Veggie','12','5000','10000','7','Plato fuerte','7','20');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('2','Salmón','Al ajillo','Garlic','34','5000','10000','10','Plato fuerte','2','20');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('3','Hamburguesa','Con carne','With meat','12','6000','12000','10','Entrada','1','20');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('4','Baratisimo','Muy barato','Very cheap','5','3000','7000','10','Plato fuerte','3','20');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('5','Italianisimo','Con salami','Salami','12','3000','10000','10','Plato fuerte','7','25');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('6','Burro grande','Barato','Cheap','4','5000','13000','10','Plato fuerte','5','20');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('7','Tamal','Tolimense','From Tolima','5','2000','5000','10','Acompañamiento','6','20');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('8','Ajiaco','Santafereño','From Bogota','30','5000','15000','10','Plato fuerte','7','20');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('9','Carne asada','Bien cocida','Well cooked','15','6000','15000','10','Plato fuerte','2','20');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('11','Cerveza','Espumosa','Nice','12','3000','6000','4','Bebida','4','10');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('12','Cheesecake','Recién horneado','Just baked','30','5000','15000','7','Postre','6','20');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('10','Aros de cebolla','Apanados','Fried','15','2000','6500','7','Acompañamiento','4','20');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('14','Hamburguesa Gigante','Con carne','With meat','18','14000','25000','10','Plato fuerte','1','20');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('16','Papas a la francesa','Fritas ','Fried','10','3000','5000','20','Acompañamiento','1','30');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('17','Aros de cebolla','Fritos','Fried','10','3000','5000','20','Acompañamiento','1','30');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('18','Platanitos','Fritos','Fried','10','3000','5000','10','Acompañamiento','1','20');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('19','Cerveza artesanal','Fría','Cold','2','2000','6000','10','Bebida','2','20');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('20','Gaseosa','Con gas','With gas','2','2000','6000','10','Bebida','2','20');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('21','Agua','Fresca','Fresh','2','2500','4000','20','Bebida','7','40');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('22','Jugo','De frutas','Natural','2','2500','4000','20','Bebida','7','40');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('15','Gaseosa Gigante','Con hielo','With ice','1','3000','5000','10','Bebida','1','20');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('23','Vino','Chileno','From Chile','2','2500','4000','20','Bebida','7','40');
Insert into PRODUCTO (ID,NOMBRE,DESCRIPCION,TRADUCCION,TIEMPOPREPARACION,COSTO,PRECIO,DISPONIBLES,TIPO,ID_RESTAURANTE,MAXIMOPRODUCTOS) values ('13','Burro mediano','Barato','Cheap','4','4000','10000','10','Plato fuerte','1','20');

Insert into MENU (ID,NOMBRE,COSTO,PRECIO,ID_RESTAURANTE,ID_PRODUCTOENTRADA,ID_PRODUCTOACOMPAÑAMIENTO,ID_PRODUCTOPLATOFUERTE,ID_PRODUCTOPOSTRE,ID_PRODUCTOBEBIDA) values ('8','El menú imp','90000','130000','2','2',null,'9',null,null);
Insert into MENU (ID,NOMBRE,COSTO,PRECIO,ID_RESTAURANTE,ID_PRODUCTOENTRADA,ID_PRODUCTOACOMPAÑAMIENTO,ID_PRODUCTOPLATOFUERTE,ID_PRODUCTOPOSTRE,ID_PRODUCTOBEBIDA) values ('1','Ajiaquito','10000','25000','2',null,null,'1',null,null);
Insert into MENU (ID,NOMBRE,COSTO,PRECIO,ID_RESTAURANTE,ID_PRODUCTOENTRADA,ID_PRODUCTOACOMPAÑAMIENTO,ID_PRODUCTOPLATOFUERTE,ID_PRODUCTOPOSTRE,ID_PRODUCTOBEBIDA) values ('2','Pequeño','12000','15000','4',null,'10',null,null,'11');
Insert into MENU (ID,NOMBRE,COSTO,PRECIO,ID_RESTAURANTE,ID_PRODUCTOENTRADA,ID_PRODUCTOACOMPAÑAMIENTO,ID_PRODUCTOPLATOFUERTE,ID_PRODUCTOPOSTRE,ID_PRODUCTOBEBIDA) values ('3','El menú feo','1000','10000','6',null,'7',null,'6',null);
Insert into MENU (ID,NOMBRE,COSTO,PRECIO,ID_RESTAURANTE,ID_PRODUCTOENTRADA,ID_PRODUCTOACOMPAÑAMIENTO,ID_PRODUCTOPLATOFUERTE,ID_PRODUCTOPOSTRE,ID_PRODUCTOBEBIDA) values ('4','Burrito','10000','13000','5',null,null,'6',null,null);
Insert into MENU (ID,NOMBRE,COSTO,PRECIO,ID_RESTAURANTE,ID_PRODUCTOENTRADA,ID_PRODUCTOACOMPAÑAMIENTO,ID_PRODUCTOPLATOFUERTE,ID_PRODUCTOPOSTRE,ID_PRODUCTOBEBIDA) values ('5','Italianisimo','7000','15000','3',null,null,'5',null,null);
Insert into MENU (ID,NOMBRE,COSTO,PRECIO,ID_RESTAURANTE,ID_PRODUCTOENTRADA,ID_PRODUCTOACOMPAÑAMIENTO,ID_PRODUCTOPLATOFUERTE,ID_PRODUCTOPOSTRE,ID_PRODUCTOBEBIDA) values ('12','Un nuevo sandwich','5999','12100','3',null,null,'5',null,null);
Insert into MENU (ID,NOMBRE,COSTO,PRECIO,ID_RESTAURANTE,ID_PRODUCTOENTRADA,ID_PRODUCTOACOMPAÑAMIENTO,ID_PRODUCTOPLATOFUERTE,ID_PRODUCTOPOSTRE,ID_PRODUCTOBEBIDA) values ('6','Para llevar','12000','32000','6',null,'7',null,null,null);

Insert into PREFERENCIA (TIPO,VALOR,ID_CLIENTE) values ('Condicion','Pago en efectivo','0');
Insert into PREFERENCIA (TIPO,VALOR,ID_CLIENTE) values ('Condicion','Parrilla','1');
Insert into PREFERENCIA (TIPO,VALOR,ID_CLIENTE) values ('Condicion','Pago con tarjeta','2');
Insert into PREFERENCIA (TIPO,VALOR,ID_CLIENTE) values ('Espacio','Abierto','1');
Insert into PREFERENCIA (TIPO,VALOR,ID_CLIENTE) values ('Ingrediente','Queso','3');
Insert into PREFERENCIA (TIPO,VALOR,ID_CLIENTE) values ('Ingrediente','Tomate','0');
Insert into PREFERENCIA (TIPO,VALOR,ID_CLIENTE) values ('Precio','13000','6');
Insert into PREFERENCIA (TIPO,VALOR,ID_CLIENTE) values ('Precio','20000','0');
Insert into PREFERENCIA (TIPO,VALOR,ID_CLIENTE) values ('Restaurante','One Burrito','2');
Insert into PREFERENCIA (TIPO,VALOR,ID_CLIENTE) values ('Zona','Oriental','0');


Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('11','Frijoles','Rojos','Red');
Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('12','Pimeton','Rojo','Red');
Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('13','Pimeton 2','Verde','Green');
Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('14','Queso americano','Americano','American');
Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('15','Leche entera','Entera','Entire');
Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('16','Leche de soya','Soya','Soy');
Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('1','Tomate','Chonto','Chonto trad');
Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('2','Lechuga','Recién sembrada','Collected');
Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('3','Pan','Ajonjolí','Ajonjoli');
Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('4','Leche','Deslactosada','Deslactosada trad');
Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('5','Papa','Sabanera','From La Sabana');
Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('6','Platano','Maduro','Maduro trad');
Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('7','Harina','Haz de oros','Haz de oros trad');
Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('8','Agua','Manantial','Manantial trad');
Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('9','Mermelada','De mora','Red berry');
Insert into INGREDIENTE (ID,NOMBRE,DESCRIPCION,TRADUCCION) values ('10','Queso','Campesino','Farmer');


Insert into PEDIDO (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-04 03:12:46','YYYY-MM-DD HH24:MI:SS'),'0','8','1','7');
Insert into PEDIDO (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-04 03:12:49','YYYY-MM-DD HH24:MI:SS'),'34','9','1','8');
Insert into PEDIDO (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-04 03:12:52','YYYY-MM-DD HH24:MI:SS'),'35','10','1','8');
Insert into PEDIDO (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-04 03:12:55','YYYY-MM-DD HH24:MI:SS'),'38','11','1','8');
Insert into PEDIDO (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-04 03:12:57','YYYY-MM-DD HH24:MI:SS'),'37','12','1','8');
Insert into PEDIDO (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-03 20:01:37','YYYY-MM-DD HH24:MI:SS'),'3','3','0','3');
Insert into PEDIDO (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-03 21:59:42','YYYY-MM-DD HH24:MI:SS'),'1','1','1','1');
Insert into PEDIDO (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-03 22:02:32','YYYY-MM-DD HH24:MI:SS'),'2','2','0','2');
Insert into PEDIDO  (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-04 22:02:47','YYYY-MM-DD HH24:MI:SS'),'4','4','0','4');
Insert into PEDIDO  (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-02 04:02:57','YYYY-MM-DD HH24:MI:SS'),'31','5','1','5');
Insert into PEDIDO  (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-02 05:03:10','YYYY-MM-DD HH24:MI:SS'),'32','6','1','5');
Insert into PEDIDO  (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-01 15:03:21','YYYY-MM-DD HH24:MI:SS'),'33','7','0','6');
Insert into PEDIDO  (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-04 12:52:00','YYYY-MM-DD HH24:MI:SS'),'0','18','1','10');
Insert into PEDIDO  (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-04 09:26:00','YYYY-MM-DD HH24:MI:SS'),'2','24','1','11');
Insert into PEDIDO  (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-04 09:26:00','YYYY-MM-DD HH24:MI:SS'),'2','25','0','11');
Insert into PEDIDO  (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-09-24 05:10:12','YYYY-MM-DD HH24:MI:SS'),'1','13','1','9');
Insert into PEDIDO  (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-01 05:10:21','YYYY-MM-DD HH24:MI:SS'),'1','14','1','9');
Insert into PEDIDO  (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-02 07:10:28','YYYY-MM-DD HH24:MI:SS'),'1','15','1','9');
Insert into PEDIDO  (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-04 05:10:35','YYYY-MM-DD HH24:MI:SS'),'1','16','0','9');
Insert into PEDIDO  (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-03 05:10:40','YYYY-MM-DD HH24:MI:SS'),'1','17','0','9');
Insert into PEDIDO  (FECHAHORA,ID_CLIENTE,ID,SERVIDO,ID_MESA) values (to_date('2017-10-29 05:20:55','YYYY-MM-DD HH24:MI:SS'),'34','26','1','12');

Insert into ESPACIO (ABIERTO,CAPACIDAD,ACCESO_ESPECIAL,NOMBRE_ZONA,ID) values ('1','45','1','Oriental','10');
Insert into ESPACIO (ABIERTO,CAPACIDAD,ACCESO_ESPECIAL,NOMBRE_ZONA,ID) values ('0','22','1','Oriental','11');
Insert into ESPACIO (ABIERTO,CAPACIDAD,ACCESO_ESPECIAL,NOMBRE_ZONA,ID) values ('0','33','0','Oriental','12');
Insert into ESPACIO (ABIERTO,CAPACIDAD,ACCESO_ESPECIAL,NOMBRE_ZONA,ID) values ('1','13','1','Oriental','9');
Insert into ESPACIO (ABIERTO,CAPACIDAD,ACCESO_ESPECIAL,NOMBRE_ZONA,ID) values ('0','10','1','Oriental','1');
Insert into ESPACIO (ABIERTO,CAPACIDAD,ACCESO_ESPECIAL,NOMBRE_ZONA,ID) values ('1','15','1','Occidental','2');
Insert into ESPACIO (ABIERTO,CAPACIDAD,ACCESO_ESPECIAL,NOMBRE_ZONA,ID) values ('1','20','1','Norte','3');
Insert into ESPACIO (ABIERTO,CAPACIDAD,ACCESO_ESPECIAL,NOMBRE_ZONA,ID) values ('0','25','0','Sur','4');
Insert into ESPACIO (ABIERTO,CAPACIDAD,ACCESO_ESPECIAL,NOMBRE_ZONA,ID) values ('1','30','0','Superior','5');
Insert into ESPACIO (ABIERTO,CAPACIDAD,ACCESO_ESPECIAL,NOMBRE_ZONA,ID) values ('1','25','1','Inferior','6');
Insert into ESPACIO (ABIERTO,CAPACIDAD,ACCESO_ESPECIAL,NOMBRE_ZONA,ID) values ('0','12','1','Inferior','7');
Insert into ESPACIO (ABIERTO,CAPACIDAD,ACCESO_ESPECIAL,NOMBRE_ZONA,ID) values ('0','50','1','Oriental','8');

Insert into CONDICION (NOMBRE_CONDICION,ID_ESPACIO) values ('Barra ensalada','1');
Insert into CONDICION (NOMBRE_CONDICION,ID_ESPACIO) values ('Pago en efectivo','1');
Insert into CONDICION (NOMBRE_CONDICION,ID_ESPACIO) values ('Guardería','1');
Insert into CONDICION (NOMBRE_CONDICION,ID_ESPACIO) values ('Iluminación','2');
Insert into CONDICION (NOMBRE_CONDICION,ID_ESPACIO) values ('Meseros','4');
Insert into CONDICION (NOMBRE_CONDICION,ID_ESPACIO) values ('Música','3');
Insert into CONDICION (NOMBRE_CONDICION,ID_ESPACIO) values ('Parrilla','1');
Insert into CONDICION (NOMBRE_CONDICION,ID_ESPACIO) values ('Parrilla','5');
Insert into CONDICION (NOMBRE_CONDICION,ID_ESPACIO) values ('Parrilla','6');
Insert into CONDICION (NOMBRE_CONDICION,ID_ESPACIO) values ('Pago con tarjeta','5');

Insert into RESERVA (FECHAHORA,NUMCOMENSALES,ID_CLIENTE,NOMBRE_ZONA,ID_MENU,DURACION) values (to_date('2017-10-03 20:06:56','YYYY-MM-DD HH24:MI:SS'),'2','1','Oriental','1','60');
Insert into RESERVA (FECHAHORA,NUMCOMENSALES,ID_CLIENTE,NOMBRE_ZONA,ID_MENU,DURACION) values (to_date('2017-10-03 22:07:09','YYYY-MM-DD HH24:MI:SS'),'4','2','Occidental','1','120');
Insert into RESERVA (FECHAHORA,NUMCOMENSALES,ID_CLIENTE,NOMBRE_ZONA,ID_MENU,DURACION) values (to_date('2017-10-01 15:07:13','YYYY-MM-DD HH24:MI:SS'),'10','3','Norte','4','180');
Insert into RESERVA (FECHAHORA,NUMCOMENSALES,ID_CLIENTE,NOMBRE_ZONA,ID_MENU,DURACION) values (to_date('2017-10-02 08:00:00','YYYY-MM-DD HH24:MI:SS'),'1','4','Superior','1','50');

Insert into PEDIDO_MENUS (ID_PEDIDO,ID_MENU,CANTIDAD,ID_ENTRADA,ID_ACOMPAÑAMIENTO,ID_PLATOFUERTE,ID_BEBIDA,ID_POSTRE) values ('1','1','1',null,null,null,null,null);
Insert into PEDIDO_MENUS (ID_PEDIDO,ID_MENU,CANTIDAD,ID_ENTRADA,ID_ACOMPAÑAMIENTO,ID_PLATOFUERTE,ID_BEBIDA,ID_POSTRE) values ('2','2','3',null,null,null,null,null);
Insert into PEDIDO_MENUS (ID_PEDIDO,ID_MENU,CANTIDAD,ID_ENTRADA,ID_ACOMPAÑAMIENTO,ID_PLATOFUERTE,ID_BEBIDA,ID_POSTRE) values ('3','1','1',null,null,null,null,null);
Insert into PEDIDO_MENUS (ID_PEDIDO,ID_MENU,CANTIDAD,ID_ENTRADA,ID_ACOMPAÑAMIENTO,ID_PLATOFUERTE,ID_BEBIDA,ID_POSTRE) values ('4','3','5',null,null,null,null,null);
Insert into PEDIDO_MENUS (ID_PEDIDO,ID_MENU,CANTIDAD,ID_ENTRADA,ID_ACOMPAÑAMIENTO,ID_PLATOFUERTE,ID_BEBIDA,ID_POSTRE) values ('5','4','1',null,null,null,null,null);
Insert into PEDIDO_MENUS (ID_PEDIDO,ID_MENU,CANTIDAD,ID_ENTRADA,ID_ACOMPAÑAMIENTO,ID_PLATOFUERTE,ID_BEBIDA,ID_POSTRE) values ('24','1','1',null,null,null,null,null);
Insert into PEDIDO_MENUS (ID_PEDIDO,ID_MENU,CANTIDAD,ID_ENTRADA,ID_ACOMPAÑAMIENTO,ID_PLATOFUERTE,ID_BEBIDA,ID_POSTRE) values ('24','2','1',null,null,null,null,null);
Insert into PEDIDO_MENUS (ID_PEDIDO,ID_MENU,CANTIDAD,ID_ENTRADA,ID_ACOMPAÑAMIENTO,ID_PLATOFUERTE,ID_BEBIDA,ID_POSTRE) values ('13','1','12',null,null,null,null,null);
Insert into PEDIDO_MENUS (ID_PEDIDO,ID_MENU,CANTIDAD,ID_ENTRADA,ID_ACOMPAÑAMIENTO,ID_PLATOFUERTE,ID_BEBIDA,ID_POSTRE) values ('14','2','2',null,null,null,null,null);
Insert into PEDIDO_MENUS (ID_PEDIDO,ID_MENU,CANTIDAD,ID_ENTRADA,ID_ACOMPAÑAMIENTO,ID_PLATOFUERTE,ID_BEBIDA,ID_POSTRE) values ('15','3','3',null,null,null,null,null);
Insert into PEDIDO_MENUS (ID_PEDIDO,ID_MENU,CANTIDAD,ID_ENTRADA,ID_ACOMPAÑAMIENTO,ID_PLATOFUERTE,ID_BEBIDA,ID_POSTRE) values ('15','2','4',null,null,null,null,null);



Insert into PEDIDO_PRODUCTOS (ID_PEDIDO,ID_PRODUCTO,CANTIDAD) values ('1','1','3');
Insert into PEDIDO_PRODUCTOS (ID_PEDIDO,ID_PRODUCTO,CANTIDAD) values ('2','2','6');
Insert into PEDIDO_PRODUCTOS (ID_PEDIDO,ID_PRODUCTO,CANTIDAD) values ('3','1','5');
Insert into PEDIDO_PRODUCTOS (ID_PEDIDO,ID_PRODUCTO,CANTIDAD) values ('4','2','5');
Insert into PEDIDO_PRODUCTOS (ID_PEDIDO,ID_PRODUCTO,CANTIDAD) values ('5','6','3');
Insert into PEDIDO_PRODUCTOS (ID_PEDIDO,ID_PRODUCTO,CANTIDAD) values ('1','5','3');
Insert into PEDIDO_PRODUCTOS (ID_PEDIDO,ID_PRODUCTO,CANTIDAD) values ('1','11','3');
Insert into PEDIDO_PRODUCTOS (ID_PEDIDO,ID_PRODUCTO,CANTIDAD) values ('24','11','2');
Insert into PEDIDO_PRODUCTOS (ID_PEDIDO,ID_PRODUCTO,CANTIDAD) values ('24','12','1');

Insert into EQUIVALENCIAINGREDIENTES (ID_ORIGINAL,ID_EQUIVALENCIA) values ('1','12');
Insert into EQUIVALENCIAINGREDIENTES (ID_ORIGINAL,ID_EQUIVALENCIA) values ('1','13');
Insert into EQUIVALENCIAINGREDIENTES (ID_ORIGINAL,ID_EQUIVALENCIA) values ('4','15');
Insert into EQUIVALENCIAINGREDIENTES (ID_ORIGINAL,ID_EQUIVALENCIA) values ('4','16');
Insert into EQUIVALENCIAINGREDIENTES (ID_ORIGINAL,ID_EQUIVALENCIA) values ('10','14');

Insert into EQUIVALENCIAPRODUCTOS (ID_ORIGINAL,ID_EQUIVALENCIA) values ('16','17');
Insert into EQUIVALENCIAPRODUCTOS (ID_ORIGINAL,ID_EQUIVALENCIA) values ('16','18');
Insert into EQUIVALENCIAPRODUCTOS (ID_ORIGINAL,ID_EQUIVALENCIA) values ('19','20');
Insert into EQUIVALENCIAPRODUCTOS (ID_ORIGINAL,ID_EQUIVALENCIA) values ('21','22');
Insert into EQUIVALENCIAPRODUCTOS (ID_ORIGINAL,ID_EQUIVALENCIA) values ('21','23');
Insert into EQUIVALENCIAPRODUCTOS (ID_ORIGINAL,ID_EQUIVALENCIA) values ('1','8');

Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('1','4');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('1','6');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('1','8');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('2','1');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('2','2');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('3','1');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('4','3');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('5','5');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('6','7');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('7','5');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('8','8');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('9','1');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('10','7');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('11','8');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('12','10');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('13','7');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('14','5');
Insert into PRODUCTO_INGREDIENTE (ID_PRODUCTO,ID_INGREDIENTE) values ('14','11');


Insert into CATEGORIA (NOMBRE,ID_PRODUCTO) values ('Bebidas alcoholicas','11');
Insert into CATEGORIA (NOMBRE,ID_PRODUCTO) values ('Comida a la parrilla','9');
Insert into CATEGORIA (NOMBRE,ID_PRODUCTO) values ('Comida casera','1');
Insert into CATEGORIA (NOMBRE,ID_PRODUCTO) values ('Comida colombiana','7');
Insert into CATEGORIA (NOMBRE,ID_PRODUCTO) values ('Comida colombiana','8');
Insert into CATEGORIA (NOMBRE,ID_PRODUCTO) values ('Comida mexicana','6');
Insert into CATEGORIA (NOMBRE,ID_PRODUCTO) values ('Comida rapida','3');
Insert into CATEGORIA (NOMBRE,ID_PRODUCTO) values ('Comida rapida','10');
Insert into CATEGORIA (NOMBRE,ID_PRODUCTO) values ('Pescados','2');
Insert into CATEGORIA (NOMBRE,ID_PRODUCTO) values ('Postres','12');
Insert into CATEGORIA (NOMBRE,ID_PRODUCTO) values ('Sandwiches','4');
Insert into CATEGORIA (NOMBRE,ID_PRODUCTO) values ('Sandwiches','5');

COMMIT;