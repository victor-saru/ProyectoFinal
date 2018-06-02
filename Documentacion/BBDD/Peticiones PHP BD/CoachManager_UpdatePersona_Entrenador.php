<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();

if($_GET["dni"] != "" && $_GET["nombre"] != "" && $_GET["primer_apellido"] != "" && $_GET["movil"] != "" && $_GET["fecha_nacimiento"] != "" && $_GET["correo"] != "" ){

    $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

        $id_persona = $_GET["id_persona"];
        $nombre = $_GET["nombre"];
        $primer_apellido = $_GET["primer_apellido"];
        $segundo_apellido = $_GET["segundo_apellido"];
        $dni = $_GET["dni"];
        $movil = $_GET["movil"];
        $fecha_nacimiento = $_GET["fecha_nacimiento"];
        $genero = $_GET["genero"];

        
        $correo = $_GET["correo"];
        $contrasenya = $_GET["contrasenya"];
        $id_entrenador = $_GET["id_entrenador"];
        

        
     
        $updatePersona = "UPDATE personas SET NOMBRE = '{$nombre}', PRIMER_APELLIDO = '{$primer_apellido}', SEGUNDO_APELLIDO = '{$segundo_apellido}', DNI = '{$dni}', MOVIL = '{$movil}', FECHA_NACIMIENTO = '{$fecha_nacimiento}', GENERO = '{$genero}' WHERE id_persona = {$id_persona}";

        $resultado_updatePersona = mysqli_query($conexion, $updatePersona);

        $updateEntrenador = "UPDATE entrenadores SET correo = '{$correo}', contrasenya = '{$contrasenya}' WHERE id_entrenador = {$id_entrenador}";
                            
        $resultado_updateEntrenador = mysqli_query($conexion, $updateEntrenador);

        
        $resulta["resultado"] = "Correcto";
        $json['entrenador'][] = $resulta;

        echo json_encode($json);  
        mysqli_close($conexion);

}

else{
    
    $resulta["resultado"] = "Null";
    $json['entrenador'][] = $resulta;
    echo json_encode($json);
    mysqli_close($conexion);
}

?>



