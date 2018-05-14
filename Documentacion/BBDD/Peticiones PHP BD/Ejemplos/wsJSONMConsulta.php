<?php

$hostname_localhost = "localhost";
$database_localhost = "bdpruebas";
$username_localhost = "root";
$password_localhost = "";

$json = array();

if(isset($_GET["nombre"])){
    $nombre = $_GET["nombre"];
    
    $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

    $consulta = "SELECT nombre, apellido FROM usuarios WHERE nombre = '{$nombre}'";
    $resultado =  mysqli_query($conexion, $consulta);

    if($registro = mysqli_fetch_array($resultado)){
        $json['usuarios'][] = $registro;
    }

   else{
       $resulta["nombre"] = 'No Registra';
       $resulta["apellido"] = 'No Registra';
       $json['usuarios'][] = $resulta;
       echo json_encode($json);
   }

    mysqli_close($conexion);
    echo json_encode($json);

}

else{
    $resulta["nombre"] = 'WS No Registra';
    $resulta["apellido"] = 'WS No Registra';
    $json['usuarios'][] = $resulta;
    echo json_encode($json);
}


?>