/*CREACIÓN DE TABLAS*/
CREATE TABLE PERSONAS(
    ID_PERSONA          INTEGER AUTO_INCREMENT,   
    NOMBRE              VARCHAR(50) NOT NULL,
    PRIMER_APELLIDO     VARCHAR(50) NOT NULL,
    SEGUNDO_APELLIDO    VARCHAR(50),
    DNI                 VARCHAR(9) NOT NULL,
    MOVIL               VARCHAR(9) NOT NULL,
    FECHA_NACIMIENTO    DATE NOT NULL,
    GENERO              VARCHAR(20),
    CONSTRAINT PK_PERSONAS PRIMARY KEY (ID_PERSONA)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE ENTRENADORES(
    ID_ENTRENADOR   INTEGER AUTO_INCREMENT,
    CORREO          VARCHAR(50) NOT NULL UNIQUE,
    CONTRASENYA     VARCHAR(50) NOT NULL, /*ENCRIPTAR AL INSERTAR SHA1*/
    ID_PERSONA      INTEGER,
    CONSTRAINT PK_ENTRENADORES PRIMARY KEY (ID_ENTRENADOR),
    CONSTRAINT FK_PERSONAS_ENTRENADORES FOREIGN KEY (ID_PERSONA) REFERENCES PERSONAS (ID_PERSONA)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;


CREATE TABLE ALUMNOS(
    ID_ALUMNO   INTEGER AUTO_INCREMENT,
    PESO        INTEGER ,
    ALTURA      INTEGER ,
    MANO_DOM    VARCHAR(20) ,
	PIE_DOM		VARCHAR(20) ,
	OBSERVACIONES  VARCHAR(43) ,
	ID_PERSONA     INTEGER NOT NULL,
    ID_ENTRENADOR  INTEGER NOT NULL, 
    CONSTRAINT PK_ALUMNOS PRIMARY KEY (ID_ALUMNO),
    CONSTRAINT FK_PERSONAS_ALUMNOS FOREIGN KEY (ID_PERSONA) REFERENCES PERSONAS (ID_PERSONA),
    CONSTRAINT FK_ALUMNOS_ENTRENADORES FOREIGN KEY (ID_ENTRENADOR) REFERENCES ENTRENADORES (ID_ENTRENADOR)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;


CREATE TABLE GRUPOS(
    ID_GRUPO            INTEGER AUTO_INCREMENT,
    NOMBRE              VARCHAR(20) NOT NULL,
    CATEGORIA           VARCHAR(50),
	ID_ENTRENADOR		INTEGER NOT NULL,
    CONSTRAINT PK_GRUPO PRIMARY KEY (ID_GRUPO),
	CONSTRAINT FK_GRUPO_ENTRENADOR FOREIGN KEY (ID_ENTRENADOR) REFERENCES ENTRENADORES (ID_ENTRENADOR)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;


CREATE TABLE GRUPOS_ALUMNOS (
    ID_GRUPO    INTEGER ,    
    ID_ALUMNO  	INTEGER ,
    CONSTRAINT PK_GRUP_ALUMN PRIMARY KEY (ID_GRUPO, ID_ALUMNO),
    CONSTRAINT FK_GRUPALUM_GRUPOS FOREIGN KEY (ID_GRUPO) REFERENCES GRUPOS (ID_GRUPO),
	CONSTRAINT FK_GRUPALUM_ALUMN FOREIGN KEY (ID_ALUMNO) REFERENCES ALUMNOS (ID_ALUMNO)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;


CREATE TABLE DEPORTES(
    ID_DEPORTE      INTEGER AUTO_INCREMENT,
    NOMBRE          VARCHAR(20) NOT NULL,
    CONSTRAINT PK_DEPORTES PRIMARY KEY (ID_DEPORTE)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;


CREATE TABLE EJERCICIOS(
    ID_EJERCICIO       INTEGER AUTO_INCREMENT,
    NOMBRE             VARCHAR(20) NOT NULL,
    DESCRIPCION        VARCHAR(50),
    ID_DEPORTE         INTEGER NOT NULL,
    ID_ENTRENADOR      INTEGER NOT NULL,
    CONSTRAINT PK_EJERCICIOS PRIMARY KEY (ID_EJERCICIO),
    CONSTRAINT FK_ENTRENADOR_EJERCICIOS FOREIGN KEY (ID_ENTRENADOR) REFERENCES ENTRENADORES (ID_ENTRENADOR),
    CONSTRAINT FK_DEPORTES FOREIGN KEY (ID_DEPORTE) REFERENCES DEPORTES (ID_DEPORTE)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;


CREATE TABLE ENTRENAMIENTOS(
    ID_ENTRENAMIENTO        INTEGER AUTO_INCREMENT,
    NOMBRE                  VARCHAR(20) NOT NULL,
    ID_DEPORTE              INTEGER NOT NULL,
    ID_ENTRENADOR           INTEGER NOT NULL,
    CONSTRAINT PK_ENTRENAMIENTOS PRIMARY KEY (ID_ENTRENAMIENTO),
    CONSTRAINT FK_DEPORTES_ENTRENEMIENTOS FOREIGN KEY (ID_DEPORTE) REFERENCES DEPORTES (ID_DEPORTE),
    CONSTRAINT FK_ENTRENADORES_ENTRENAMIENTOS FOREIGN KEY (ID_ENTRENADOR) REFERENCES ENTRENADORES (ID_ENTRENADOR)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;


CREATE TABLE EJERCICIOS_ENTRENAMIENTOS(
    ID_ENTRENAMIENTO        INTEGER ,
    ID_EJERCICIO            INTEGER ,
    ORDEN                   INTEGER,
    CONSTRAINT PK_EJERCICIO_ENTRENAMIENTO PRIMARY KEY (ID_ENTRENAMIENTO, ORDEN),
    CONSTRAINT FK_EJERENTRE_ENTRENAMIENTO FOREIGN KEY (ID_ENTRENAMIENTO) REFERENCES ENTRENAMIENTOS (ID_ENTRENAMIENTO),
    CONSTRAINT FK_EJERENTRE_EJERCICIO FOREIGN KEY (ID_EJERCICIO) REFERENCES EJERCICIOS (ID_EJERCICIO)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE SESIONES(
    ID_SESION               INTEGER AUTO_INCREMENT,
    ID_GRUPO                INTEGER,
    ID_ENTRENAMIENTO        INTEGER,
    FECHA_HORA_INICIO       DATETIME,
    FECHA_HORA_FIN          DATETIME,
    REALIZADA               BOOLEAN,
    MOTIVO_CANCELACION      VARCHAR(100),
    VALORACION              VARCHAR(100),
    CONSTRAINT PK_SESIONES PRIMARY KEY (ID_SESION, ID_GRUPO, ID_ENTRENAMIENTO),
    CONSTRAINT FK_SESIONES_ENTRENAMIENTOS FOREIGN KEY (ID_ENTRENAMIENTO) REFERENCES ENTRENAMIENTOS (ID_ENTRENAMIENTO),
    CONSTRAINT FK_SESIONES_GRUPOS FOREIGN KEY (ID_GRUPO) REFERENCES GRUPOS (ID_GRUPO)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE ASISTENCIAS(
    ID_GRUPO            INTEGER,
    ID_ENTRENAMIENTO    INTEGER,
    ID_ALUMNO           INTEGER,
    ID_SESION           INTEGER,
    ASISTENCIA          BOOLEAN,
    VALORACION          VARCHAR(100),
    CONSTRAINT PK_ASISTENCIAS PRIMARY KEY (ID_ENTRENAMIENTO, ID_GRUPO, ID_ALUMNO, ID_SESION),
    CONSTRAINT FK_ASISTENCIAS_GRUPOS FOREIGN KEY (ID_GRUPO) REFERENCES GRUPOS (ID_GRUPO),
    CONSTRAINT FK_ASISTENCIAS_ALUMNOS FOREIGN KEY (ID_ALUMNO) REFERENCES ALUMNOS (ID_ALUMNO),
    CONSTRAINT FK_ASISTENCIAS_ENTRENAMIENTOS FOREIGN KEY (ID_ENTRENAMIENTO) REFERENCES ENTRENAMIENTOS (ID_ENTRENAMIENTO),
    CONSTRAINT FK_ASISTENCIAS_SESIONES FOREIGN KEY (ID_SESION) REFERENCES SESIONES (ID_SESION)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;


/*INSERCCIÓN DE DATOS*/
INSERT INTO PERSONAS 
    (NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DNI, MOVIL, FECHA_NACIMIENTO, GENERO)
VALUES
    ('VÍCTOR', 'SÁNCHEZ', 'RUBIO', '48197654G', '661837375', '1996/05/03', 'MASCULINO'),
    ('ÓSCAR', 'GÓMEZ', 'FERNÁNDEZ', '49222336J', '667348629', '1997/02/22', 'MASCULINO'),
    ('ADMINISTRADOR', 'SABADEVS', 'COACHMANAGER', '00000000Z', '666666666', '2018/05/08', 'MASCULINO');

INSERT INTO ENTRENADORES
    (CORREO, CONTRASENYA, ID_PERSONA)
VALUES
    ('ADMIN@ADMIN.COM', SHA1('ADMIN'), 3);
INSERT INTO ALUMNOS 
    (PESO, ALTURA, MANO_DOM, PIE_DOM, OBSERVACIONES, ID_PERSONA, ID_ENTRENADOR)
VALUES
    (80, 173, 'DERECHA', 'DERECHA', 'ES RUBIO', 1, 1),
    (72, 175, 'DERECHA', 'DERECHA', 'NO TIENE CLARO SU MANO Y PIE DOMINANTE', 2, 1);





INSERT INTO GRUPOS
    (NOMBRE, CATEGORIA, ID_ENTRENADOR)
VALUES
    ('ADMINISTRADORES', 'PROGRAMACIÓN', 1);

INSERT INTO GRUPOS_ALUMNOS
    (ID_GRUPO, ID_ALUMNO)
VALUES
    (1, 1),
    (1, 2);

INSERT INTO DEPORTES
    (NOMBRE)
VALUE
    ('Fútbol'),
    ('Baloncesto'),
	('Golf'),
	('Tenis'),
	('Pádel'),
	('Judo');


INSERT INTO EJERCICIOS
    (NOMBRE, DESCRIPCION, ID_DEPORTE, ID_ENTRENADOR)
VALUES
    ('Cambios de orientación', 'Cambios de orientación continuos.', 1, 1),
    ('Maniobra y pase', 'Los centrocampistas combinan con el jugador del lado contrario el balón.', 1, 1),
    ('Posesión', 'Se pone a prueba cuánto pueden poseer el balón.', 1, 1),
	('Manejo de balón', 'Ceros, botando con la mano derecha, alrededor de la pierna derecha.', 2, 1),
	('Bote con dos balones', 'Bote con dos balones a la vez.', 2, 1),
	('Defensa', '1C1 con recuperación defensiva y cambio de lado del balón.', 2, 1),
	('Ejercicio de la silla', 'Ayuda a eliminar los movimientos excesivos de la parte baja del cuerpo.', 3, 1),
	('Ejercicio de la pared', 'Ayuda a corregir el swing.', 3, 1),
	('Ejercicio de estado físico', 'Ayuda a conseguir poder en el swing y crear una rotación del cuerpo adecuada.', 3, 1);

/*INSERT INTO ENTRENAMIENTOS
    (NOMBRE, ID_DEPORTE, ID_ENTRENADOR)
VALUES
    ('ENTRENO VOLEAS, DEFENSA Y BANDEJA', 52, 1);
	('ENTRENO VOLEAS, DEFENSA Y BANDEJA', 52, 1);
	('ENTRENO VOLEAS, DEFENSA Y BANDEJA', 52, 1);*/

/*INSERT INTO EJERCICIOS_ENTRENAMIENTOS
    (ID_ENTRENAMIENTO, ID_EJERCICIO, ORDEN)
VALUES
    (1, 1, 1),
    (1, 2, 2),
    (1, 3, 4),
    (1, 4, 3);*/

INSERT INTO SESIONES
    (ID_GRUPO, ID_ENTRENAMIENTO, FECHA_HORA_INICIO, FECHA_HORA_FIN, REALIZADA, MOTIVO_CANCELACION, VALORACION)
VALUES
    (1, 1, '2018-05-09 21:00:00', '2018-05-09 22:00:00', null, null, null);


