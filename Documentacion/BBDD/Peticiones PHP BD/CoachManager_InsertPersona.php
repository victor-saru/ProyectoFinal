<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();

if (isset($_GET["nombre"]) && isset($_GET["primer_apellido"]) && isset($_GET["dni"]) && isset($_GET["movil"]) && isset($_GET["fecha_nacimiento"])){

    $nombre = $_GET["nombre"];
    $primer_apellido = $_GET["nomprimer_apellidobre"];
    $segundo_apellido = $_GET["segundo_apellido"];
    $dni = $_GET["dni"];
    $movil = $_GET["movil"];
    $fecha_nacimiento = $_GET["fecha_nacimiento"];
    $genero = $_GET["genero"];

    $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

    $insert = "INSERT INTO PERSONAS (NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DNI, MOVIL, FECHA_NACIMIENTO, GENERO)" 
        + " VALUES ('{$nombre}', '{$primer_apellido}', '{$segundo_apellido}', '{$dni}', '{$movil}', '{$fecha_nacimiento}', '{$genero}')";

    $resultado_insert = mysqli_query($conexion, $insert);

    
}

else{
    $resulta["nombre"] = 'WS No registra';
    $resulta["nomprimer_apellidobre"] = 'WS No registra';
    $resulta["segundo_apellido"] = 'WS No registra';
    $resulta["dni"] = 'WS No registra';
    $resulta["movil"] = 'WS No registra';
    $resulta["fecha_nacimiento"] = 'WS No registra';
    $resulta["genero"] = 'WS No registra';

    $json['persona'][] = $resulta;
    echo json_encode($json);
}