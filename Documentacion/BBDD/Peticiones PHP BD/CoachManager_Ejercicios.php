<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

$id_entrenador = $_GET["id_entrenador"];
$id_deporte = $_GET["id_deporte"];

$consulta = "SELECT id_ejercicio, nombre, descripcion, id_deporte, id_entrenador FROM ejercicios WHERE id_entrenador={$id_entrenador} and id_deporte = {$id_deporte}" ;   
$resultado = mysqli_query($conexion, $consulta);
$info = mysqli_fetch_row($resultado);


if($info[0] == NULL){
    $resulta["resultado"] = "Null";
    $json['ejercicios'][] = $resulta;
}

else{

    $resultado = mysqli_query($conexion, $consulta);

    while($ejercicios = mysqli_fetch_array($resultado)){ 
        $json['ejercicios'][] = $ejercicios;
    }
}

echo json_encode($json);
mysqli_close($conexion); 

?>


 