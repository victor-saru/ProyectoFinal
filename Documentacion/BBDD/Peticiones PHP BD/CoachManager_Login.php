<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

if($_GET["correo"] != "" && $_GET["contrasenya"] != ""){


    $correo = $_GET["correo"];
    $consultaCorreo = "SELECT id_persona FROM entrenadores WHERE UPPER(correo) = UPPER('{$correo}')";
    $resultconsultaCorreo = mysqli_query($conexion, $consultaCorreo);
    $id_persona = mysqli_fetch_row($resultconsultaCorreo);
    
    $consultaIdEntrenador = "SELECT id_entrenador FROM entrenadores WHERE UPPER(correo) = UPPER('{$correo}')";
    $resultconsultaIdEntrenador = mysqli_query($conexion, $consultaIdEntrenador);
    $id_entrenador = mysqli_fetch_row($resultconsultaIdEntrenador);


    if($id_persona[0] != NULL){
    
        $contrasenya = $_GET["contrasenya"];
        $consultaContrasenya = "SELECT id_persona FROM entrenadores WHERE id_persona = '{$id_persona[0]}' && contrasenya = SHA1('{$contrasenya}')";
        $resultconsultaContrasenya = mysqli_query($conexion, $consultaContrasenya);
        $contrasenyaResultado = mysqli_fetch_row($resultconsultaContrasenya);

      

        if($contrasenyaResultado[0] != NULL){
            
            $resulta["resultado"] = $id_persona[0];
            $resulta["id_entrenador"] = $id_entrenador[0];
            $json['login'][] = $resulta;
            echo json_encode($json);
            mysqli_close($conexion);

        }

        else{
            $resulta["resultado"] = "Contrasenya";
            $json['login'][] = $resulta;
            echo json_encode($json);
            mysqli_close($conexion);
        }

    }

    else{
        $resulta["resultado"] = "Correo";
        $json['login'][] = $resulta;
        echo json_encode($json);
        mysqli_close($conexion);
    }



}

else{
    $resulta["resultado"] = "Null";
    $json['login'][] = $resulta;
    echo json_encode($json);
    mysqli_close($conexion);
}

?>