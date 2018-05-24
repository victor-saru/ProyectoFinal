<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();

if($_GET["dni"] != "" && $_GET["nombre"] != "" && $_GET["primer_apellido"] != "" && $_GET["movil"] != "" && $_GET["fecha_nacimiento"] != "" ){

    $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);


    $dniGET = $_GET["dni"];
    $consulta = "SELECT ip_persona FROM personas WHERE UPPER(dni) = UPPER('{$dniGET}')";
    $resultadoIdPersona = mysqli_query($conexion, $consulta);
    $id_persona = mysqli_fetch_row($resultadoIdPersona);

    $consulta = "SELECT id_alumno FROM alumno WHERE id_persona = {$id_persona}";
    $resultadoIdAlumno = mysqli_query($conexion, $consulta);
    $id_alumno = mysqli_fetch_row($resultadoIdAlumno);


    

    if($dniResultado[0] == NULL){

        
        $id_alumno = $_GET["id_alumno"];
        $nombre = $_GET["nombre"];
        $primer_apellido = $_GET["primer_apellido"];
        $segundo_apellido = $_GET["segundo_apellido"];
        $movil = $_GET["movil"];
        $fecha_nacimiento = $_GET["fecha_nacimiento"];
        $genero = $_GET["genero"];

        
        $peso = $_GET["peso"];
        $altura = $_GET["altura"];
        $mano_dom = $_GET["mano_dom"];
        $pie_dom = $_GET["pie_dom"];
        $observaciones = $_GET["observaciones"];
        $id_persona = $_GET["id_persona"];
        $id_persona_entrenador = $_GET["id_persona_entrenador"];

        
     
        $updatePersona = "UPDATE personas SET (NOMBRE = '{$nombre}', PRIMER_APELLIDO = '{$primer_apellido}', SEGUNDO_APELLIDO = '{$segundo_apellido}', DNI = '{$dniGET}', MOVIL = '{$movil}', FECHA_NACIMIENTO = '{$fecha_nacimiento}', GENERO = '{$genero}') WHERE id_persona = {$id_persona}";

        $resultado_updatePersona = mysqli_query($conexion, $updatePersona);

        $updateAlumno = "UPDATE alumnos SET (PESO = '{$peso}', ALTURA = '{$altura}', MANO_DOM = '{$mano_dom}', PIE_DOM = '{$pie_dom}', OBSERVACIONES = '{$observaciones}') WHERE id_persona = {$id_persona}";
                            
        $resultado_updateAlumno = mysqli_query($conexion, $updateAlumno);

        
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
    
    $resulta["resultado"] = "Null";
    $json['persona'][] = $resulta;
    echo json_encode($json);
    mysqli_close($conexion);
}

?>