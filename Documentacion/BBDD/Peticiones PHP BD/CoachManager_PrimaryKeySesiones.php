<?php

    $hostname_localhost = "localhost";
    $database_localhost = "coachmanager";
    $username_localhost = "root";
    $password_localhost = "";

    $json = array();



    $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

    
    
    $consulta = "SELECT MAX(ID_SESION)+1 FROM sesiones";
    $resultado = mysqli_query($conexion, $consulta);
    $maxKey = mysqli_fetch_row($resultado);

    if($maxKey[0] != null){
        $resulta["resultado"] = $maxKey[0];
        $json['primarykey'][] = $resulta;
    }

    else{
        $resulta["resultado"] = "Null";
        $json['primarykey'][] = $resulta;
    }
    

    echo json_encode($json);  
    mysqli_close($conexion);
    



?>