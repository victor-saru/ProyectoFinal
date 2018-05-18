<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);


$consulta = "SELECT nombre, primer_apellido, segundo_apellido, dni, movil, fecha_nacimiento, genero, peso, altura, mano_dom, pie_dom, observaciones FROM personas JOIN alumnos ON personas.ID_PERSONA = alumnos.id_persona" ;   
$resultado = mysqli_query($conexion, $consulta);


while($alumnos = mysqli_fetch_array($resultado)){
    $json['alumnos'][] = $alumnos;
}

echo json_encode($json);
mysqli_close($conexion);


?>