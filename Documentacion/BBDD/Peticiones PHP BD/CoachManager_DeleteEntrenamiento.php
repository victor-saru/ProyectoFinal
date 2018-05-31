<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();


$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);


$id_entrenamiento = $_GET["id_entrenamiento"];

$deleteEntrenamientoEjercicio = "DELETE FROM ejercicios_entrenamientos WHERE id_entrenamiento = {$id_entrenamiento}";
$resultado_deleteEntrenamientoEjercicio = mysqli_query($conexion, $deleteEntrenamientoEjercicio);

$deleteEntrenamiento = "DELETE FROM entrenamientos WHERE id_entrenamiento = {$id_entrenamiento}";                  
$resultado_deleteEntrenamiento = mysqli_query($conexion, $deleteEntrenamiento);



$resulta["resultado"] = "Eliminado";
$json['entrenamientos'][] = $resulta;

echo json_encode($json);  
mysqli_close($conexion);
    



?>