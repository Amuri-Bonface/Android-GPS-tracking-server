<?php  
 require "init.php";  
 		$owner_email=$_POST["owner_email"];
	 	$sql_del="delete from marketers where owner_email='$owner_email';";
		$second_result = mysqli_query($con,$sql_del);
		$json['reply']="Succesfully Deleted"; 
		echo json_encode($json);
 ?>  