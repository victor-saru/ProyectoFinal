<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();

$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

if($_GET["nombre"] != ""){

    

        $id_ejercicio = $_GET["id_ejercicio"];
        $nombre = $_GET["nombre"];
        $descripcion = $_GET["descripcion"];
        $id_deporte = $_GET["id_deporte"];
        $id_entrenador = $_GET["id_entrenador"];

        
     
        $updateEjercicio = "UPDATE EJERCICIOS SET NOMBRE = '{$nombre}', DESCRIPCION = '{$descripcion}', id_deporte = '{$id_deporte}', id_entrenador = '{$id_entrenador}' WHERE id_ejercicio = {$id_ejercicio}";
        $resultado_updateEjercicio = mysqli_query($conexion, $updateEjercicio);

        
        $resulta["resultado"] = "Correcto";
        $json['ejercicio'][] = $resulta;

        echo json_encode($json);  
        mysqli_close($conexion);

 
}

else{
    
    $resulta["resultado"] = "Null";
    $json['ejercicio'][] = $resulta;
    echo json_encode($json);
    mysqli_close($conexion);
}

?>



