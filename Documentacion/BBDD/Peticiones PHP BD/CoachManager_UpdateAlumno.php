<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();

if($_GET["dni"] != "" && $_GET["nombre"] != "" && $_GET["primer_apellido"] != "" && $_GET["movil"] != "" && $_GET["fecha_nacimiento"] != "" ){

    $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);




        
        $id_alumno = $_GET["id_alumno"];
        $nombre = $_GET["nombre"];
        $primer_apellido = $_GET["primer_apellido"];
        $segundo_apellido = $_GET["segundo_apellido"];
        $dni = $_GET["dni"];
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

        
     
        $updatePersona = "UPDATE personas SET NOMBRE = '{$nombre}', PRIMER_APELLIDO = '{$primer_apellido}', SEGUNDO_APELLIDO = '{$segundo_apellido}', DNI = '{$dni}', MOVIL = '{$movil}', FECHA_NACIMIENTO = '{$fecha_nacimiento}', GENERO = '{$genero}' WHERE id_persona = {$id_persona}";

        $resultado_updatePersona = mysqli_query($conexion, $updatePersona);

        $updateAlumno = "UPDATE alumnos SET PESO = '{$peso}', ALTURA = '{$altura}', MANO_DOM = '{$mano_dom}', PIE_DOM = '{$pie_dom}', OBSERVACIONES = '{$observaciones}' WHERE id_persona = {$id_persona}";
                            
        $resultado_updateAlumno = mysqli_query($conexion, $updateAlumno);

        
        $resulta["resultado"] = "Correcto";
        $json['persona'][] = $resulta;

        echo json_encode($json);  
        mysqli_close($conexion);
   

  
  
  
 
}

else{
    
    $resulta["resultado"] = "Null";
    $json['persona'][] = $resulta;
    echo json_encode($json);
    mysqli_close($conexion);
}
//http://10.1.6.74/CoachManagerPHP/CoachManager_UpdateAlumno.php?id_alumno=128&nombre=pepe&primer_apellido=sancho&segundo_apellido=moreno&dni=48197654P&movil=1234&fecha_nacimiento=2018/02/01&genero=Femenino&peso=100&altura=100&mano_dom=derecha&pie_dom=izquierda&observaciones=ahora es moreno&id_persona=146&id_persona_entrenador=122
?>



