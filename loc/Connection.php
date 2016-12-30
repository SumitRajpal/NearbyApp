<?php
$server_name = "ramstertechcom4.ipagemysql.com";
$user_name = "ramstertechcom4";
$password = "Alienware@17";
$db= 'demodata';
$conn = mysqli_connect($server_name, $user_name, $password,$db);

if(!$conn){
	die("Error Connection");
}

?>