DROP TABLE PERSONAS;
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

DROP TABLE ENTRENADORES;
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
    REALIZADO               BOOLEAN NOT NULL,
    MOTIVO_CANCEL           VARCHAR(100),
    FECHA                   DATE,
    ID_ENTRENADOR           INTEGER ,
    ID_GRUPO                INTEGER ,
    CONSTRAINT PK_ENTRENAMIENTO PRIMARY KEY (ID_ENTRENADOR),
    CONSTRAINT FK_PERSONAS_ENTRENAMIENTO FOREIGN KEY (ID_ENTRENADOR) REFERENCES PERSONAS (ID_ENTRENADOR),
    CONSTRAINT FK_GRUPO_ENTRENAMIENTO FOREIGN KEY (ID_GRUPO) REFERENCES GRUPOS (ID_GRUPO)
);


CREATE TABLE GRUPOS_DEPORTES(
    ID_DEPORTE      INTEGER ,
    ID_GRUPO        INTEGER ,
    CONSTRAINT PK_GRUPOS_DEPORTES PRIMARY KEY (ID_DEPORTE, ID_GRUPO),
    CONSTRAINT FK_GRUPDEPOR_DEPORTES FOREIGN KEY (ID_DEPORTE) REFERENCES DEPORTES (ID_DEPORTE),
    CONSTRAINT FK_GRUPDEPOR_GRUPOS FOREIGN KEY (ID_GRUPO) REFERENCES GRUPOS (ID_GRUPO)
);


CREATE TABLE EJERCICIOS_ENTRENEMIENTOS(
    ID_ENTRENAMIENTO        INTEGER ,
    ID_EJERCICIO       INTEGER ,
    CONSTRAINT PK_EJERCICIO_ENTRENAMIENTO PRIMARY KEY (ID_ENTRENAMIENTO, ID_EJERCICIO),
    CONSTRAINT FK_EJERENTRE_ENTRENAMIENTO FOREIGN KEY ENTRENAMIENTOS (ID_ENTRENAMIENTO),
    CONSTRAINT FK_EJERENTRE_EJERCICIO FOREIGN KEY EJERCICIOS (ID_EJERCICIO)
);







