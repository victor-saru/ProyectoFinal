<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();


$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);


$id_grupo = $_GET["id_grupo"];

$deleteGrupoAlumno = "DELETE FROM grupos_alumnos WHERE id_grupo = {$id_grupo}";
$resultado_deleteGrupoAlumno = mysqli_query($conexion, $deleteGrupoAlumno);

$deleteGrupo = "DELETE FROM grupos WHERE id_grupo = {$id_grupo}";                  
$resultado_deleteGrupo = mysqli_query($conexion, $deleteGrupo);







$resulta["resultado"] = "Eliminado";
$json['grupos'][] = $resulta;

echo json_encode($json);  
mysqli_close($conexion);
    



?>