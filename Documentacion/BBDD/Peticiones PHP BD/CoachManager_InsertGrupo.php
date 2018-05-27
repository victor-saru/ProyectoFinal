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
    $consulta = "SELECT nombre FROM grupos WHERE UPPER(nombre) = UPPER('{$nombre}')";
    $resultado = mysqli_query($conexion, $consulta);
    $nombreResultado = mysqli_fetch_row($resultado);


    

    if($nombreResultado[0] == NULL){

        $categoria = $_GET["categoria"];
        $id_entrenador = $_GET["id_entrenador"];
        

        $insertPersona = "INSERT INTO grupos (NOMBRE, CATEGORIA, ID_ENTRENADOR) VALUES ('{$nombre}', '{$categoria}', '{$id_entrenador}')";

        $resultado_insertPersona = mysqli_query($conexion, $insertPersona);

        
        $resulta["resultado"] = "Correcto";
        $json['grupo'][] = $resulta;

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