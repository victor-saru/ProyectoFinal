<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();




$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);


$id_persona = $_GET["id_persona"];
$consulta = "SELECT personas.id_persona, nombre, primer_apellido, segundo_apellido, dni, movil, fecha_nacimiento, genero, id_entrenador, correo, contrasenya FROM personas JOIN entrenadores ON personas.ID_PERSONA = entrenadores.id_persona WHERE personas.ID_PERSONA = {$id_persona}";
$resultado = mysqli_query($conexion, $consulta);
$entrenadorResultado = mysqli_fetch_array($resultado);



$resulta["resultado"] = "Correcto";
$json['entrenador'][] = $entrenadorResultado;

echo json_encode($json);  
mysqli_close($conexion);
    

  



?>