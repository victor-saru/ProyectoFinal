<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();

//&& $_GET["contrasenya"] != ""
if($_GET["dni"] != "" && $_GET["nombre"] != "" && $_GET["primer_apellido"] != "" && $_GET["movil"] != "" && $_GET["fecha_nacimiento"] != "" ){

    $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

    //Consulta DNI
    $dniGET = $_GET["dni"];
    $consulta = "SELECT dni FROM personas WHERE dni = '{$dniGET}'";
    $resultado = mysqli_query($conexion, $consulta);
    $dniResultado = mysqli_fetch_row($resultado);


    

    if($dniResultado[0] == NULL){

        

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
        

        $insertPersona = "INSERT INTO PERSONAS (NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DNI, MOVIL, FECHA_NACIMIENTO, GENERO) VALUES ('{$nombre}', '{$primer_apellido}', '{$segundo_apellido}', '{$dniGET}', '{$movil}', '{$fecha_nacimiento}', '{$genero}')";

        $resultado_insertPersona = mysqli_query($conexion, $insertPersona);

        

        $consultaPersona = "SELECT id_persona FROM personas WHERE UPPER(dni) = UPPER('{$dniGET}')";
        $resultado_consultaPersona = mysqli_query($conexion, $consultaPersona);
        $id_persona = mysqli_fetch_row($resultado_consultaPersona);


        $insertAlumno = "INSERT INTO alumnos (PESO, ALTURA, MANO_DOM, PIE_DOM, OBSERVACIONES, ID_PERSONA) VALUES ({$peso}, {$altura}, '{$mano_dom}', '{$pie_dom}', '{$observaciones}', '{$id_persona[0]}')";
                            
        $resultado_insertAlumno = mysqli_query($conexion, $insertAlumno);
        
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
    //echo "campo sin rellenar";
    $resulta["resultado"] = "Null";
    $json['persona'][] = $resulta;
    echo json_encode($json);
    mysqli_close($conexion);
}

?>