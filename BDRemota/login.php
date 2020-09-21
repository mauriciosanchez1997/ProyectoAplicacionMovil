<?php
$con = mysqli_connect("localhost", "root","12345678","bd_usuario");
$username = $_POST["usuario"];
$password = $_POST["clave"];
$statement = mysqli_prepare($con, "SELECT * FROM user WHERE usuario = ? AND clave = ?");
mysqli_stmt_bind_param($statement, "ss",$username, $password);
mysqli_stmt_execute($statement);
mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $idusuario, $nombre, $usuario, $clave, $edad,$tipo);
$response = array();
$response["success"] = false;

while (mysqli_stmt_fetch($statement)) {
	$response["success"] = true;
	$response["nombre"] = $nombre;
	$response["edad"] = $edad;
	$response["usuario"] = $usuario;
	$response["clave"] = $clave;
	$response["tipo"] = $tipo;
}
echo json_encode($response);
?>