<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();



$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

$nombre = $_GET["nombre"];
$id_ejercicio = $_GET["id_ejercicio"];
$orden = $_GET["orden"];


$insertEjercicio = "INSERT INTO ejercicios_entrenamientos (id_ejercicio, id_entrenamiento, orden) VALUES ('{$id_ejercicio}', (SELECT id_entrenamiento FROM entrenamientos WHERE UPPER(nombre) = UPPER('{$nombre}')), {$orden})";

                    
$resultado_insertEjercicio = mysqli_query($conexion, $insertEjercicio);

$resulta["resultado"] = "Correcto";
$json['ejerciciosentrenamientos'][] = $resulta;

echo json_encode($json);  
mysqli_close($conexion);
?>