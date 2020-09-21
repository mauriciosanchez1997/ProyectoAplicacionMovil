<?php
	$con = mysqli_connect("localhost", "root","12345678","bd_usuario");

	$nombre =$_POST["nombre"];
	$edad =$_POST["edad"];
	$usuario =$_POST["usuario"];
	$clave =$_POST["clave"];
	$statement = mysqli_prepare($con, "INSERT INTO user (nombre, usuario, edad,clave) VALUES 
		(?, ?, ?, ?)");
	mysqli_stmt_bind_param($statement, "ssis", $nombre, $usuario, $edad, $clave);
	mysqli_stmt_execute($statement);
	$response = array();
	$response["success"] = true;
	echo json_encode($response);
?>