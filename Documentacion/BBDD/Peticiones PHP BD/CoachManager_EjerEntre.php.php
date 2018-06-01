<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

$id_entrenamiento = $_GET["id_entrenamiento"];

$consulta = "SELECT id_ejercicio, nombre, descripcion,  FROM personas JOIN alumnos ON personas.ID_PERSONA = alumnos.id_persona JOIN grupos_alumnos ON alumnos.id_alumno =  grupos_alumnos.id_alumno WHERE id_entrenamiento={$id_entrenamiento}";
$resultado = mysqli_query($conexion, $consulta);
$info = mysqli_fetch_row($resultado);


if($info[0] == NULL){
    $resulta["resultado"] = "Null";
    $json['alumnosgrupo'][] = $resulta;
}

else{

    $resultado = mysqli_query($conexion, $consulta);

    while($alumnos = mysqli_fetch_array($resultado)){ 
        $json['alumnosgrupo'][] = $alumnos;
    }
}

echo json_encode($json);
mysqli_close($conexion); 

?>







        


 