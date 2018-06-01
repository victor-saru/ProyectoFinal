<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

$id_deporte = $_GET["id_deporte"];

$consulta = "SELECT id_deporte, nombre FROM deportes WHERE id_deporte = {$id_deporte} ORDER BY nombre";   
$resultado = mysqli_query($conexion, $consulta);
$deporte = mysqli_fetch_array($resultado);


$json['deporte'][] = $deporte;


echo json_encode($json);
mysqli_close($conexion); 

?>


 