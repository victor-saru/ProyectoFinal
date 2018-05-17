<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();

if(isset($_GET["dni"])){

    $dni = $_GET["dni"];

    $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

    $consulta = "SELECT dni FROM personas WHERE dni = '{$dni}'";
    $id_persona = mysqli_query($conexion, $consulta);

    if($registro = mysqli_fetch_array($id_persona)){
        $json['persona'][] = $registro;
    }

    else{
        $resulta["dni"] = 'Null';
        $json['persona'][] = $resulta;
        echo json_encode($json);
    }

    mysqli_close($conexion);
    echo json_encode($json);
    
}


else{
    $resulta["dni"] = ' WS No Registra';
   
    $json['persona'][] = $resulta;
    echo json_encode($json);
}

?>