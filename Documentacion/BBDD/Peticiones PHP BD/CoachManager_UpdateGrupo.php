<?php

$hostname_localhost = "localhost";
$database_localhost = "coachmanager";
$username_localhost = "root";
$password_localhost = "";

$json = array();
$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

if($_GET["nombre"] != ""  && $_GET["id_entrenador"] != "" && $_GET["id_grupo"] != ""){

    
    
    

        $nombre = $_GET["nombre"];

        
            $categoria = $_GET["categoria"];
            $id_entrenador = $_GET["id_entrenador"];
            $id_grupo = $_GET["id_grupo"];
            
            $updateGrupo = "UPDATE grupos SET NOMBRE = '{$nombre}', CATEGORIA = '{$categoria}', ID_ENTRENADOR = '{$id_entrenador}' WHERE id_grupo = {$id_grupo}";

            $resultado_updateGrupo = mysqli_query($conexion, $updateGrupo);

            $resulta["resultado"] = "Correcto";
            $json['grupo'][] = $resulta;

            echo json_encode($json);  
            mysqli_close($conexion);
        }


else{
    
    $resulta["resultado"] = "Null";
    $json['persona'][] = $resulta;
    echo json_encode($json);
    mysqli_close($conexion);
}

?>



