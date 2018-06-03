<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();



$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

$id_sesion = $_GET["id_sesion"];
$id_grupo = $_GET["id_grupo"];
$id_entrenamiento = $_GET["id_entrenamiento"];
$fecha_hora_inicio = $_GET["fecha_hora_inicio"];
$fecha_hora_fin = $_GET["fecha_hora_fin"];
$realizada = $_GET["realizada"];
$motivo_cancelacion = $_GET["motivo_cancelacion"];
$valoracion = $_GET["valoracion"];
$id_entrenador = $_GET["id_entrenador"];


$insertSesion = "INSERT INTO sesiones (ID_SESION ,ID_GRUPO, ID_ENTRENAMIENTO, FECHA_HORA_INICIO, FECHA_HORA_FIN, REALIZADA, MOTIVO_CANCELACION, VALORACION, ID_ENTRENADOR) VALUES ('{$id_sesion}', '{$id_grupo}', '{$id_entrenamiento}', '{$fecha_hora_inicio}', '{$fecha_hora_fin}', '{$realizada}', '{$motivo_cancelacion}', '{$valoracion}', '{$id_entrenador}')";
                    
$resultado_insertSesion = mysqli_query($conexion, $insertSesion);

$resulta["resultado"] = "Correcto";
$json['sesion'][] = $resulta;

echo json_encode($json);  
mysqli_close($conexion);
?>