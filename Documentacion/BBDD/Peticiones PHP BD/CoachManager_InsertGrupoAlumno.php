<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();



$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

$nombre = $_GET["nombre"];
$id_alumno = $_GET["id_alumno"];


$insertAlumno = "INSERT INTO grupos_alumnos (ID_ALUMNO, ID_GRUPO) VALUES ('{$id_alumno}', (SELECT id_grupo FROM grupos WHERE UPPER(nombre) = UPPER('{$nombre}')))";
                    
$resultado_insertAlumno = mysqli_query($conexion, $insertAlumno);

$resulta["resultado"] = "Correcto";
$json['gruposalumnos'][] = $resulta;

echo json_encode($json);  
mysqli_close($conexion);
?>