<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

$id_entrenador = $_GET["id_entrenador"];

$consulta = "SELECT id_alumno, nombre, primer_apellido, segundo_apellido, dni, movil, fecha_nacimiento, genero, peso, altura, mano_dom, pie_dom, observaciones, alumnos.id_persona FROM personas JOIN alumnos ON personas.ID_PERSONA = alumnos.id_persona WHERE id_entrenador={$id_entrenador}" ;   
$resultado = mysqli_query($conexion, $consulta);
$info = mysqli_fetch_row($resultado);


if($info[0] == NULL){
    $resulta["resultado"] = "Null";
    $json['alumnos'][] = $resulta;
}

else{

    $resultado = mysqli_query($conexion, $consulta);

    while($alumnos = mysqli_fetch_array($resultado)){ 
        $json['alumnos'][] = $alumnos;
    }
}

echo json_encode($json);
mysqli_close($conexion); 

?>


 