<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();

if($_GET["nombre"] != "" ){

    $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);


    $nombreGET = $_GET["nombre"];
$consulta = "SELECT nombre FROM ejercicios WHERE UPPER(nombre) = UPPER('{$nombreGET}') and id_deporte = '{$id_deporte}'";
    $resultado = mysqli_query($conexion, $consulta);
    $nombreResultado = mysqli_fetch_row($resultado);


    

    if($nombreResultado[0] == NULL){

        $descripcion = $_GET["descripcion"];
        $id_deporte  = $_GET["id_deporte"];
        $id_entrenador  = $_GET["id_entrenador"];
    
        $insertEjercicio = "INSERT INTO EJERCICIOS (NOMBRE, DESCRIPCION, ID_DEPORTE, ID_ENTRENADOR) VALUES ('{$nombreGET}', '{$descripcion}', '{$id_deporte}', '{$id_entrenador}')";

        $resultado_insertEjercicio = mysqli_query($conexion, $insertEjercicio);

        echo json_encode($json);  
        mysqli_close($conexion);
    }

    else{
        $resulta["resultado"] = "NombreReperido";
        $json['ejercicio'][] = $resulta;
        echo json_encode($json);
        mysqli_close($conexion);
    }
 
}

else{
    
    $resulta["resultado"] = "Null";
    $json['ejercicio'][] = $resulta;
    echo json_encode($json);
    mysqli_close($conexion);
}

?>