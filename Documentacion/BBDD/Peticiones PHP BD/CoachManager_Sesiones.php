<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);


$id_entrenador = $_GET["id_entrenador"];

$consulta = "SELECT id_sesion, id_grupo, id_entrenamiento, DATE(fecha_hora_inicio), TIME(fecha_hora_inicio), TIME(fecha_hora_fin), realizada, motivo_cancelacion, valoracion, id_entrenador FROM sesiones GROUP BY ID_SESION HAVING id_entrenador = {$id_entrenador} ORDER BY fecha_hora_inicio";   
$resultado = mysqli_query($conexion, $consulta);
$info = mysqli_fetch_row($resultado);

if($info[0] == NULL){
    $resulta["resultado"] = "Null";
    $json['sesiones'][] = $resulta;
}

else{
    $resultado = mysqli_query($conexion, $consulta);
    while($sesiones = mysqli_fetch_array($resultado)){
        $json['sesiones'][] = $sesiones;
    }
}

echo json_encode($json);
mysqli_close($conexion);


?>