<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);


$consulta = "SELECT nombre, categoria FROM grupos" ;   
$resultado = mysqli_query($conexion, $consulta);


while($grupos = mysqli_fetch_array($resultado)){
    $json['grupos'][] = $grupos;
}

echo json_encode($json);
mysqli_close($conexion);


?>