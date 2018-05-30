<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

$consulta = "SELECT id_deporte, nombre FROM deportes ORDER BY nombre";   
$resultado = mysqli_query($conexion, $consulta);
$info = mysqli_fetch_row($resultado);


if($info[0] == NULL){
    $resulta["resultado"] = "Null";
    $json['deportes'][] = $resulta;
}

else{

    $resultado = mysqli_query($conexion, $consulta);


    while($deportes = mysqli_fetch_array($resultado)){
        $json['deportes'][] = $deportes;
        
    }
}

echo json_encode($json);
mysqli_close($conexion); 

?>


 