<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

if($_GET["nombre"] != ""){


    $nombre = $_GET["nombre"];
    $id_entrenamiento = $_GET["id_entrenamiento"];
    $id_entrenador = $_GET["id_entrenador"];
    $id_deporte = $_GET["id_deporte"];
    
    $updateEntrenamiento = "UPDATE entrenamientos SET NOMBRE = '{$nombre}', id_deporte = '{$id_deporte}', ID_ENTRENADOR = '{$id_entrenador}' WHERE id_entrenamiento = {$id_entrenamiento}";

    $resultado_updateEntrenamiento = mysqli_query($conexion, $updateEntrenamiento);

    $resulta["resultado"] = "Correcto";
    $json['entrenamiento'][] = $resulta;

    echo json_encode($json);  
    mysqli_close($conexion);
}


else{
    
    $resulta["resultado"] = "Null";
    $json['entrenamiento'][] = $resulta;
    echo json_encode($json);
    mysqli_close($conexion);
}

?>



