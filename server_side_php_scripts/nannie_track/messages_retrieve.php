<?php  
 require "init.php";  
 
	//$owner_email=$_POST["owner_email"];
	$owner_email=$_POST["owner_email"];
	$sql_check="select phone,typeofsms,smsdaytime,body,email,owner_email from get_sms where owner_email='$owner_email';";
	$result = mysqli_query($con,$sql_check);

	 if(mysqli_num_rows($result) >0 )  
	 {  		
			$sql_check_again="select phone,typeofsms,smsdaytime,body,email,owner_email from get_sms where owner_email='$owner_email';";
			$second_result = mysqli_query($con,$sql_check_again);
			  if(mysqli_num_rows($second_result) >0 )  
					{  
						$json=array();		
						while($row = mysqli_fetch_assoc($second_result))
						{					 
							$json[]=$row;
						}
													
						 echo json_encode($json);
					}
	}  
	 else  
	 {   
	 $json['reply'] = "Error";  
	 echo json_encode($json);
		mysqli_close($con);
	 }

 ?>  