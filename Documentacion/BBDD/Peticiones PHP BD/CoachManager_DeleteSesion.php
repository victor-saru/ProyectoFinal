<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();


$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);


$id_sesion = $_GET["id_sesion"];

$deleteSesion = "DELETE FROM sesiones WHERE id_sesion = {$id_sesion}";
$resultado_deleteSesion = mysqli_query($conexion, $deleteSesion);



$resulta["resultado"] = "Eliminado";
$json['sesiones'][] = $resulta;

echo json_encode($json);  
mysqli_close($conexion);
    



?>