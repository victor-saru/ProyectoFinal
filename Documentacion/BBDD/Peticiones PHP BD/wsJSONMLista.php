<?php

$hostname_localhost = "localhost";
$database_localhost = "bdpruebas";
$username_localhost = "root";
$password_localhost = "";

$json = array();
    
    $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

    $consulta = "SELECT * FROM usuarios";
    $resultado = mysqli_query($conexion, $consulta);

    while($registro = mysqli_fetch_array($resultado)){
        $json['usuarios'][] = $registro;
    }

    mysqli_close($conexion);
    echo json_encode($json);
   

?>