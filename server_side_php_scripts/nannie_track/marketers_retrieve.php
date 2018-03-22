<?php  
 require "init.php";  
 
	//$owner_email=$_POST["owner_email"];
	 $sql_query = "select longitude,latitude,token,email,fname,sname,phone,owner_email,tarehe,activate from marketers;";  
	 $result = mysqli_query($con,$sql_query);  
	 
	 if(mysqli_num_rows($result) >0 )  
	 {  
		$sql_check_again="select longitude,latitude,token,email,fname,sname,phone,owner_email,tarehe,activate from marketers where owner_email='$owner_email';";
		$second_result = mysqli_query($con,$sql_check_again);
			  if(mysqli_num_rows($second_result) >0 )  
					{  							
						$row = mysqli_fetch_assoc($second_result);
						 						 
						$json['longitude']=$row["longitude"]+0; 
						$json['latitude']=$row["latitude"]+0; 
						$json['token']=$row["token"]; 
						$json['email']=$row["email"]; 
						$json['fname']=$row["fname"]; 
						$json['sname']=$row["sname"];
						$json['phone']=$row["phone"]; 	
						$json['owner_email']=$row["owner_email"]; 
						$json['tarehe']=$row["tarehe"]; 
						$json['activate']=$row["activate"];
						
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