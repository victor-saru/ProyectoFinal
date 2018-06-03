<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

$id_grupo = $_GET["id_grupo"];
$id_sesion = $_GET["id_sesion"];

$consulta = "SELECT sesiones.id_entrenamiento, nombre, id_deporte, sesiones.id_entrenador  FROM sesiones JOIN entrenamientos ON sesiones.ID_ENTRENAMIENTO = entrenamientos.ID_ENTRENAMIENTO WHERE ID_SESION = {$id_sesion} and ID_GRUPO = {$id_grupo}" ;   
$resultado = mysqli_query($conexion, $consulta);
$info = mysqli_fetch_row($resultado);


if($info[0] == NULL){
    $resulta["resultado"] = "Null";
    $json['entrenosesion'][] = $resulta;
}

else{

    $resultado = mysqli_query($conexion, $consulta);

    while($entrenamientos = mysqli_fetch_array($resultado)){ 
        $json['entrenosesion'][] = $entrenamientos;
    }
}

echo json_encode($json);
mysqli_close($conexion); 

?>


 