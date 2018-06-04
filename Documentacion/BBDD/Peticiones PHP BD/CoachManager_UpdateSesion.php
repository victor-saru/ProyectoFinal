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


$updateSesion = "UPDATE sesiones  SET ID_SESION = '{$id_sesion}' ,ID_GRUPO = '{$id_grupo}', ID_ENTRENAMIENTO = '{$id_entrenamiento}', FECHA_HORA_INICIO = '{$fecha_hora_inicio}', FECHA_HORA_FIN = '{$fecha_hora_fin}', REALIZADA = '{$realizada}', MOTIVO_CANCELACION = '{$motivo_cancelacion}', VALORACION = '{$valoracion}', ID_ENTRENADOR = '{$id_entrenador}'WHERE id_sesion ='{$id_sesion}'"; 
                    
$resultado_updateSesion = mysqli_query($conexion, $updateSesion);

$resulta["resultado"] = "Correcto";
$json['updateSesion'][] = $resulta;

echo json_encode($json);  
mysqli_close($conexion);
?>