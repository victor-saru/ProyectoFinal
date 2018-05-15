<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();

if(isset($_GET["correo"]) && isset($_GET["contraseña"]) && isset($_GET["dni"])){

    $correo = $_GET["correo"];    
    $contraseña = $_GET["contraseña"];
    $dni = $_GET["dni"];

    $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

    $consulta = "SELECT id_persona FROM personas WHERE dni = '{$dni}'";
    $id_persona = mysqli_query($conexion);

    $insert = "INSERT INTO entrenadores(correo, contraseña, id_persona)" 
        + " VALUES ('{$correo}', SHA1('{$contraseña}'), '{$id_persona}'";

    $resultado_insert = mysqli_query($conexion, $insert);

    mysqli_close($conexion);
    
}


else{
    $resulta["correo"] = ' WS No Registra';
    $resulta["contraseña"] = 'WS No Registra';
    $resulta["id_persona"] = 'WS No Registra';
   
    $json['entrenador'][] = $resulta;
    echo json_encode($json);
}

?>