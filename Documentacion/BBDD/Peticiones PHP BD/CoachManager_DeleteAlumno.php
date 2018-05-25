<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();


$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);


$id_persona = $_GET["id_persona"];
$id_alumno = $_GET["id_alumno"];

$deleteAlumno = "DELETE FROM alumnos WHERE id_alumno = {$id_alumno}";                  
$resultado_deleteAlumno = mysqli_query($conexion, $deleteAlumno);

$deletePersona = "DELETE FROM personas WHERE id_persona = {$id_persona}";
$resultado_deletePersona = mysqli_query($conexion, $deletePersona);





$resulta["resultado"] = "Eliminado";
$json['alumnos'][] = $resulta;

echo json_encode($json);  
mysqli_close($conexion);
    



?>