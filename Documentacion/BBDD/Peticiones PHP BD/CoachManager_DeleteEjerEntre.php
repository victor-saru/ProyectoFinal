<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();


$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);


$id_entrenamiento = $_GET["id_entrenamiento"];

$deleteEjerEntre = "DELETE FROM ejercicios_entrenamientos WHERE id_entrenamiento = {$id_entrenamiento}";
$resultado_deleteEjerEntre = mysqli_query($conexion, $deleteEjerEntre);

$resulta["resultado"] = "Eliminado";
$json['eliminar'][] = $resulta;

echo json_encode($json);  
mysqli_close($conexion);
    



?>