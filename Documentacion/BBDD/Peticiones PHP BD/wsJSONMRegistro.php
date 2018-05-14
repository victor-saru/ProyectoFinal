<?php

$hostname_localhost = "localhost";
$database_localhost = "bdpruebas";
$username_localhost = "root";
$password_localhost = "";

$json = array();

if(isset($_GET["nombre"]) && isset($_GET["apellido"])){
    $nombre = $_GET["nombre"];
    $apellido = $_GET["apellido"];
    
    $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

    $insert = "INSERT INTO usuarios(nombre, apellido) VALUES('{$nombre}', '{$apellido}')";
    $resultado_insert =  mysqli_query($conexion, $insert);

   if($resultado_insert){
        $consulta = "SELECT * FROM usuarios WHERE nombre = '{$nombre}'";
        $resultado = mysqli_query($conexion, $consulta);

        if($registro = mysqli_fetch_array($resultado)){
            $json['usuarios'][] = $registro;
        }

        mysqli_close($conexion);
        echo json_encode($json);
   }

   else{
       $resulta["nombre"] = 'No Registra';
       $resulta["apellido"] = 'No Registra';
       $json['usuarios'][] = $resulta;
       echo json_encode($json);
   }

}

else{
    $resulta["nombre"] = 'WS No Registra';
    $resulta["apellido"] = 'WS No Registra';
    $json['usuarios'][] = $resulta;
    echo json_encode($json);
}


?>