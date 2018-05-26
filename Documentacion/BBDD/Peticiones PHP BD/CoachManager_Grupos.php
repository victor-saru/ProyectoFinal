<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);


$id_entrenador = $_GET["id_entrenador"];

$consulta = "SELECT id_grupo, nombre, categoria FROM grupos WHERE id_entrenador = {$id_entrenador}";   
$resultado = mysqli_query($conexion, $consulta);
$info = mysqli_fetch_row($resultado);

if($info[0] == NULL){
    $resulta["resultado"] = "Null";
    $json['grupos'][] = $resulta;
}

else{
    $resultado = mysqli_query($conexion, $consulta);
    while($grupos = mysqli_fetch_array($resultado)){
        $json['grupos'][] = $grupos;
    }
}

echo json_encode($json);
mysqli_close($conexion);


?>