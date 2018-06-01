<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();

if($_GET["nombre"] != ""){

    $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

    //Consulta Nombre
    $nombre = $_GET["nombre"];
    $id_entrenador = $_GET["id_entrenador"];
    $consulta = "SELECT nombre FROM grupos WHERE UPPER(nombre) = UPPER('{$nombre}') and id_entrenador = {$id_entrenador}";
    $resultado = mysqli_query($conexion, $consulta);
    $nombreResultado = mysqli_fetch_row($resultado);


    

    if($nombreResultado[0] == NULL){

        
        $id_deporte = $_GET["id_deporte"];
        

        $insertEntrenamiento = "INSERT INTO entrenamientos (NOMBRE, ID_DEPORTE, ID_ENTRENADOR) VALUES ('{$nombre}', '{$id_deporte}', '{$id_entrenador}')";

        $resultado_insertEntrenamiento = mysqli_query($conexion, $insertEntrenamiento);

        
        $resulta["resultado"] = "Correcto";
        $json['entrenamiento'][] = $resulta;

        echo json_encode($json);  
        mysqli_close($conexion);
    }

    else{
        $resulta["resultado"] = "NombreRepetido";
        $json['grupo'][] = $resulta;
        echo json_encode($json);
        mysqli_close($conexion);
    }
 
}

else{
    //echo "campo sin rellenar";
    $resulta["resultado"] = "Null";
    $json['persona'][] = $resulta;
    echo json_encode($json);
    mysqli_close($conexion);
}

?>