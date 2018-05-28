<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

$id_grupo = $_GET["id_grupo"];

$consulta = "SELECT alumnos.id_alumno, nombre, primer_apellido, segundo_apellido, dni, movil, fecha_nacimiento, genero, peso, altura, mano_dom, pie_dom, observaciones, alumnos.id_persona FROM personas JOIN alumnos ON personas.ID_PERSONA = alumnos.id_persona JOIN grupos_alumnos ON alumnos.id_alumno =  grupos_alumnos.id_alumno WHERE id_grupo={$id_grupo}";
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







        


 