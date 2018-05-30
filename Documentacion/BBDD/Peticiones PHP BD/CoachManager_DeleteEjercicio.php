<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();


$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);


$id_ejercicio = $_GET["id_ejercicio"];

$deleteEjercicio = "DELETE FROM ejercicios WHERE id_ejercicio = {$id_ejercicio}";
$resultado_deleteEjercicio = mysqli_query($conexion, $deleteEjercicio);

$resulta["resultado"] = "Eliminado";
$json['ejercicios'][] = $resulta;

echo json_encode($json);  
mysqli_close($conexion);
    



?>