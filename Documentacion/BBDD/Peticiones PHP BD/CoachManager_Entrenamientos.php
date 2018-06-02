<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);


$id_entrenador = $_GET["id_entrenador"];

$consulta = "SELECT id_entrenamiento, nombre, id_deporte FROM entrenamientos WHERE id_entrenador = {$id_entrenador} ORDER BY id_deporte";   
$resultado = mysqli_query($conexion, $consulta);
$info = mysqli_fetch_row($resultado);

if($info[0] == NULL){
    $resulta["resultado"] = "Null";
    $json['entrenamientos'][] = $resulta;
}

else{
    $resultado = mysqli_query($conexion, $consulta);
    while($grupos = mysqli_fetch_array($resultado)){
        $json['entrenamientos'][] = $grupos;
    }
}

echo json_encode($json);
mysqli_close($conexion);


?>