<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();

//&& $_GET["contrasenya"] != ""
if($_GET["dni"] != "" && $_GET["nombre"] != "" && $_GET["primer_apellido"] != "" && $_GET["movil"] != "" && $_GET["fecha_nacimiento"] != "" && $_GET["correo"] != "" && $_GET["contrasenya"] != ""){

    $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

    //Consulta DNI
    $dniGET = $_GET["dni"];
    $consulta = "SELECT dni FROM personas WHERE dni = '{$dniGET}'";
    $resultado = mysqli_query($conexion, $consulta);
    $dniResultado = mysqli_fetch_row($resultado);

    
    //Consulta CORREO
    $correoGET = $_GET["correo"];
    $consultaCorreo = "SELECT correo FROM entrenadores WHERE correo = '{$correoGET}'";
    $resultadoCorreo = mysqli_query($conexion, $consultaCorreo);
    $correoResultado = mysqli_fetch_row($resultadoCorreo);

    if($correoResultado[0] == NULL){

        if($dniResultado[0] == NULL){

            //echo "correcto";
    
            $nombre = $_GET["nombre"];
            $primer_apellido = $_GET["primer_apellido"];
            $segundo_apellido = $_GET["segundo_apellido"];
            //$dni = $_GET["dni"];
            $movil = $_GET["movil"];
            $fecha_nacimiento = $_GET["fecha_nacimiento"];
            $genero = $_GET["genero"];
    
            //$correo = $_GET["correo"];
            $contrasenya = $_GET["contrasenya"];
    
            $insertPersona = "INSERT INTO PERSONAS (NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DNI, MOVIL, FECHA_NACIMIENTO, GENERO) VALUES ('{$nombre}', '{$primer_apellido}', '{$segundo_apellido}', '{$dniGET}', '{$movil}', '{$fecha_nacimiento}', '{$genero}')";
    
            $resultado_insertPersona = mysqli_query($conexion, $insertPersona);
    
            
    
            $consultaPersona = "SELECT id_persona FROM personas WHERE UPPER(dni) = UPPER('{$dniGET}')";
            $resultado_consultaPersona = mysqli_query($conexion, $consultaPersona);
            $id_persona = mysqli_fetch_row($resultado_consultaPersona);
    
            //echo "<br>ID_PERSONA".$id_persona[0];
    
            $insertEntrenador = "INSERT INTO ENTRENADORES (CORREO, CONTRASENYA, ID_PERSONA) VALUES ('{$correoGET}', SHA1('{$contrasenya}'), '{$id_persona[0]}')";
                                
            $resultado_insertEntrenador = mysqli_query($conexion, $insertEntrenador);
            
            $resulta["resultado"] = "Correcto";
            $json['persona'][] = $resulta;
    
            echo json_encode($json);  
            mysqli_close($conexion);
        }
    
        else{
            $resulta["resultado"] = "DniRepetido";
            $json['persona'][] = $resulta;
            echo json_encode($json);
            mysqli_close($conexion);
        }

    }

    else{
        $resulta["resultado"] = "CorreoRepetido";
        $json['persona'][] = $resulta;
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