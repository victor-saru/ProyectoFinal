/*CREACIÓN DE TABLAS*/
CREATE TABLE PERSONAS(
    ID_PERSONA          INTEGER AUTO_INCREMENT,   
    NOMBRE              VARCHAR(50) NOT NULL,
    PRIMER_APELLIDO     VARCHAR(50) NOT NULL,
    SEGUNDO_APELLIDO    VARCHAR(50),
    DNI                 VARCHAR(9) NOT NULL,
    MOVIL               VARCHAR(9) NOT NULL,
    FECHA_NACIMIENTO    DATE NOT NULL,        
    CONSTRAINT PK_PERSONAS PRIMARY KEY (ID_PERSONA)
);

CREATE TABLE ENTRENADORES(
    ID_ENTRENADOR   INTEGER AUTO_INCREMENT,
    CORREO          VARCHAR(50) NOT NULL,
    CONSTRASEÑA     VARCHAR(50) NOT NULL, /*ENCRIPTAR AL INSERTAR SHA1*/
    ID_PERSONA      INTEGER,
    CONSTRAINT PK_ENTRENADORES PRIMARY KEY (ID_ENTRENADOR),
    CONSTRAINT FK_PERSONAS_ENTRENADORES FOREIGN KEY (ID_PERSONA) REFERENCES PERSONAS (ID_PERSONA)
);


CREATE TABLE ALUMNOS(
    ID_ALUMNO   INTEGER AUTO_INCREMENT,
    PESO        INTEGER,
    ALTURA      INTEGER,
    MANO_DOM    VARCHAR(20),
	PIE_DOM		VARCHAR(20),
	OBSERVACIONES VARCHAR(100),
	ID_PERSONA      INTEGER,
    CONSTRAINT PK_ALUMNOS PRIMARY KEY (ID_ALUMNO),
    CONSTRAINT FK_PERSONAS_ALUMNOS FOREIGN KEY (ID_PERSONA) REFERENCES PERSONAS (ID_PERSONA)
);


CREATE TABLE GRUPOS(
    ID_GRUPO        INTEGER AUTO_INCREMENT,
    NOMBRE          VARCHAR(20) NOT NULL,
    ID_RESPONSABLE  INTEGER ,
    CATEGORIA
    CONSTRAINT PK_GRUPO PRIMARY KEY (ID_GRUPO),
    CONSTRAINT FK_ENTRENADOR_GRUPOS FOREIGN KEY (ID_RESPONSABLE) REFERENCES ENTRENADORES (ID_ENTRENADOR)
);


CREATE TABLE GRUPOS_ALUMNOS (
    ID_GRUPO    INTEGER ,    
    ID_ALUMNO  	INTEGER ,
    CONSTRAINT PK_GRUP_ALUMN PRIMARY KEY (ID_GRUPO, ID_ALUMNO),
    CONSTRAINT FK_GRUPALUM_GRUPOS FOREIGN KEY (ID_GRUPO) REFERENCES GRUPOS (ID_GRUPO),
	CONSTRAINT FK_GRUPALUM_ALUMN FOREIGN KEY (ID_ALUMNO) REFERENCES ALUMNOS (ID_ALUMNO)
);


CREATE TABLE DEPORTES(
    ID_DEPORTE      INTEGER AUTO_INCREMENT,
    NOMBRE          VARCHAR(20) NOT NULL,
    CONSTRAINT PK_DEPORTES PRIMARY KEY (ID_DEPORTE)
);


CREATE TABLE EJERCICIOS(
    ID_EJERCICIO       INTEGER AUTO_INCREMENT,
    NOMBRE             VARCHAR(20) NOT NULL,
    DESCRIPCION        VARCHAR(50),
    ID_DEPORTE         INTEGER ,
    ID_ENTRENADOR      INTEGER ,
    CONSTRAINT PK_EJERCICIOS PRIMARY KEY (ID_EJERCICIO),
    CONSTRAINT FK_ENTRENADOR_EJERCICIOS FOREIGN KEY (ID_ENTRENADOR) REFERENCES ENTRENADORES (ID_ENTRENADOR),
    CONSTRAINT FK_DEPORTES FOREIGN KEY (ID_DEPORTE) REFERENCES DEPORTES (ID_DEPORTE)
);


CREATE TABLE ENTRENAMIENTOS(
    ID_ENTRENAMIENTO        INTEGER AUTO_INCREMENT,
    NOMBRE                  VARCHAR(20) NOT NULL,
    CONSTRAINT PK_ENTRENAMIENTOs PRIMARY KEY (ID_ENTRENAMIENTO) 
);


CREATE TABLE EJERCICIOS_ENTRENAMIENTOS(
    ID_ENTRENAMIENTO        INTEGER ,
    ID_EJERCICIO            INTEGER ,
    ORDEN                   INTEGER,
    CONSTRAINT PK_EJERCICIO_ENTRENAMIENTO PRIMARY KEY (ID_ENTRENAMIENTO, ORDEN),
    CONSTRAINT FK_EJERENTRE_ENTRENAMIENTO FOREIGN KEY (ID_ENTRENAMIENTO) REFERENCES ENTRENAMIENTOS (ID_ENTRENAMIENTO),
    CONSTRAINT FK_EJERENTRE_EJERCICIO FOREIGN KEY (ID_EJERCICIO) REFERENCES EJERCICIOS (ID_EJERCICIO)
);

CREATE TABLE SESIONES(
    FECHA_HORA              DATE,
    ID_GRUPO                INTEGER,
    ID_ENTRENAMIENTO        INTEGER,
    REALIZADA               BOOLEAN,
    MOTIVO_CANCELACION      VARCHAR(100),
    VALORACION              VARCHAR(100),
    CONSTRAINT PK_SESIONES PRIMARY KEY (FECHA_HORA, ID_GRUPO),
    CONSTRAINT FK_SESIONES_ENTRENAMIENTOS FOREIGN KEY (ID_ENTRENAMIENTO) REFERENCES ENTRENAMIENTOS (ID_ENTRENAMIENTO),
    CONSTRAINT FK_SESIONES_GRUPOS FOREIGN KEY (ID_GRUPO) REFERENCES GRUPOS (ID_GRUPO)
);

CREATE TABLE ASISTENCIAS(
    ID_GRUPO        INTEGER,
    FECHA_HORA      DATE,
    ID_ALUMNO       INTEGER,
    ASISTENCIA      BOOLEAN,
    CONSTRAINT PK_ASISTENCIAS PRIMARY KEY (FECHA_HORA, ID_GRUPO, ID_ALUMNO),
    CONSTRAINT FK_ASISTENCIAS_GRUPOS FOREIGN KEY (ID_GRUPO) REFERENCES GRUPOS (ID_GRUPO),
    CONSTRAINT FK_ASISTENCIAS_ALUMNOS FOREIGN KEY (ID_ALUMNO) REFERENCES ALUMNOS (ID_ALUMNO)
);


/*INSERCCIÓN DE DATOS*/
INSERT INTO PERSONAS 
    (NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DNI, MOVIL, FECHA_NACIMIENTO)
VALUES
    ('VÍCTOR', 'SÁNCHEZ', 'RUBIO', '48197654G', '661837375', '1996/05/03'),
    ('ÓSCAR', 'GÓMEZ', 'FERNÁNDEZ', '49222336J', '667348629', '1997/02/22'),
    ('ADMINISTRADOR', 'SABADEVS', 'COACHMANAGER', '00000000Z', '666666666', '2018/05/08');

INSERT INTO ALUMNOS 
    (PESO, ALTURA, MANO_DOM, PIE_DOM, OBSERVACIONES, ID_PERSONA)
VALUES
    (80, 173, 'DERECHA', 'DERECHA', 'ES RUBIO', 1),
    (72, 175, 'DERECHA', 'DERECHA', 'NO TIENE CLARO SU MANO Y PIE DOMINANTE', 2);


INSERT INTO ENTRENADORES
    (CORREO, CONSTRASEÑA, ID_PERSONA)
VALUES
    ('ADMIN@ADMIN.COM', SHA1('ADMIN'), 3);


INSERT INTO GRUPOS
    (NOMBRE, ID_RESPONSABLE)
VALUES
    ('ADMINISTRADORES', 1)

INSERT INTO GRUPOS_ALUMNOS
    (ID_GRUPO, ID_ALUMNO)
VALUES
    (1, 1),
    (1, 2);

INSERT INTO DEPORTES
    (NOMBRE)
VALUE
    ('ALPINISMO'),
    ('ANDAR'),
    ('AQUAGYM'),
    ('ATLETISMO'),
    ('BADMINTON'),
    ('STREET DANCE'),
    ('BALLET'),
    ('BALONCESTO'),
    ('BALONMANO'),
    ('BARCO'),
    ('BARRANQUISMO'),
    ('BEISBOL'),
    ('SOFTBALL'),
    ('BODYBOARD'),
    ('BOXEO'),
    ('BUCEO CON BOTELLA'),
    ('SUBMARINISMO'),
    ('CAMPING'),
    ('CARDIO TRAINING'),
    ('CAZA'),
    ('CICLISMO'),
    ('COMETAS'),
    ('CROSS TRAINNING'),
    ('DANZA'),
    ('DARDOS'),
    ('EQUITACIÓN'),
    ('ESCALADA'),
    ('ESQUÍ'),
    ('ESQUÍ DE FONDO'),
    ('ESQUÍ DE TRAVESÍA'),
    ('FITNESS'),
    ('FLAMENCO'),
    ('FRONTENIS'),
    ('FÚTBOL'),
    ('FÚTBOL AMERICANO'),
    ('GIMNASIA RITMICA'),
    ('GIMNASIA ARTISTICA'),
    ('GOLF'),
    ('HÍPICA'),
    ('HOCKEY HIERBA'),
    ('HOCKEY RUEDAS'),
    ('HOCKEY HIELO'),
    ('JIU-JITSU'),
    ('JUDO'),
    ('KARATE'),
    ('KAYAK'),
    ('KITESURF Y LANDKITE'),
    ('MARCHA NÓRDICA'),
    ('MONTAÑA'),
    ('MUSCULACIÓN'),
    ('NATACIÓN'),
    ('PÁDEL'),
    ('PATINAJE HIELO'),
    ('PATINES'),
    ('PELOTA VASCA'),
    ('PESCA'),
    ('PESCA SUBMARINA'),
    ('PETANCA'),
    ('PILATES'),
    ('PING-PONG'),
    ('RAID - TRAIL'),
    ('RAQUETAS DE NIEVE'),
    ('RUGBY'),
    ('RUNNING'),
    ('SENDERISMO'),
    ('SKATE Y PATINETES'),
    ('SKIMBOARD'),
    ('SNORKEL'),
    ('SNOWBOARD'),
    ('SQUASH'),
    ('STAND UP PADDLE'),
    ('SURF'),
    ('TAE KWON DO'),
    ('TENIS'),
    ('TENIS DE MESA'),
    ('TIRO AL PLATO'),
    ('TIRO CON ARCO'),
    ('TRAIL-RUNNING'),
    ('TREKKING'),
    ('TRIATLÓN'),
    ('VELA, BARCO'),
    ('VOLEIBOL'),
    ('VOLEY PLAYA'),
    ('ESQUÍ NAUTICO'),
    ('WINDSURF'),
    ('YOGA');


INSERT INTO EJERCICIOS
    (NOMBRE, DESCRIPCION, ID_DEPORTE, ID_ENTRENADOR)
VALUES
    ('VOLEA DE DERECHA', 'ALUMNO EN LA RED VOLEANDO DE DERECHA', 162, 1),
    ('VOLEA DE REVES', 'ALUMNO EN LA RED VOLEANDO DE REVES', 162, 1),
    ('DEFENSA FONDO PISTA', 'ALUMNO EN EL FONDO DE PISTA DEFENDIENDO (GOLPE PLANO, CORTADO Y GLOBO)', 162, 1),
    ('BANDEJA', 'BANDEJA AL CRUZADO Y AL PARALELO', 162, 1);


INSERT INTO ENTRENAMIENTOS
    (NOMBRE)
VALUES
    ('ENTRENO VOLEAS, DEFENSA Y BANDEJA')

INSERT INTO EJERCICIOS_ENTRENAMIENTOS
    (ID_ENTRENAMIENTO, ID_EJERCICIO, ORDEN)
VALUES
    (1, 3, 1),
    (1, 4, 2),
    (1, 5, 4),
    (1, 6, 3);

INSERT INTO SESIONES
    (FECHA_HORA, ID_GRUPO, ID_ENTRENAMIENTO, REALIZADA, MOTIVO_CANCELACION, VALORACION)
VALUES


