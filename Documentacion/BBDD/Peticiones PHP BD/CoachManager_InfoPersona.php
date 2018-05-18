<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

if($_GET["id_persona"] != ""){

    $id_persona = $_GET["id_persona"];
    $consulta = "SELECT nombre, primer_apellido, segundo_apellido, CORREO FROM personas join entrenadores on personas.ID_PERSONA = entrenadores.ID_ENTRENADOR WHERE personas.ID_PERSONA = {$id_persona}";   
    $resultado = mysqli_query($conexion, $consulta);


    while($info = mysqli_fetch_array($resultado)){
        $json['persona'][] = $info;
    }

    echo json_encode($json);
    mysqli_close($conexion);


}

else{
    $resulta["resultado"] = "Null";
    $json['persona'][] = $resulta;
    echo json_encode($json);
    mysqli_close($conexion);
}


?>