<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

$id_entrenamiento = $_GET["id_entrenamiento"];

$consulta = "SELECT ejercicios.id_ejercicio, nombre, descripcion, id_deporte, id_entrenador FROM ejercicios JOIN ejercicios_entrenamientos ON ejercicios.id_ejercicio = ejercicios_entrenamientos.id_ejercicio WHERE id_entrenamiento={$id_entrenamiento} ORDER BY orden ";
$resultado = mysqli_query($conexion, $consulta);
$info = mysqli_fetch_row($resultado);


if($info[0] == NULL){
    $resulta["resultado"] = "Null";
    $json['ejerentre'][] = $resulta;
}

else{

    $resultado = mysqli_query($conexion, $consulta);

    while($ejercicios = mysqli_fetch_array($resultado)){ 
        $json['ejerentre'][] = $ejercicios;
    }
}

echo json_encode($json);
mysqli_close($conexion); 

?>












        


 