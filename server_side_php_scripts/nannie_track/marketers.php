<?php  
 require "init.php"; 
 ///for registering the client part of the app
 $longitude = $_POST["longitude"];
 $latitude = $_POST["latitude"];
 $email=$_POST["email"];
 $token=$_POST["token"];
 $tarehe=$_POST["tarehe"];
 
 
 //Lets check if data exists or not
 $sql_check="select longitude,latitude,token,email,tarehe from marketers;";
 $result = mysqli_query($con,$sql_check);
 	if(mysqli_num_rows($result) ==0 )  
	 { 
			$sql_query = "Insert into marketers(longitude,latitude,token,email,tarehe) values('$longitude','$latitude','$token','$email','$tarehe');";
			mysqli_query($con, $sql_query) or die (mysqli_error($con));
			mysqli_close($con); 
	 }
	 else if(mysqli_num_rows($result) >0 )
	 {
			$sql_check_again="select longitude,latitude,token,email,tarehe from marketers where email='$email';";
			$second_result = mysqli_query($con,$sql_check_again);
			
			if(mysqli_num_rows($second_result) >0 )
			{
				$row = mysqli_fetch_assoc($second_result); 
				$fetch_longitude =$row["longitude"];
				$fetch_latitude=$row["latitude"];
				$fetch_token=$row["token"];
				$fetch_email=$row["email"];
					if ($fetch_email==$email)
					{
						$sql_query1 ="Update marketers SET longitude='$longitude', latitude='$latitude', email='$email',token='$token',tarehe='$tarehe' WHERE email='$email';";
						mysqli_query($con, $sql_query1) or die (mysqli_error($con));
						mysqli_close($con);
					}
			}
			else if(mysqli_num_rows($second_result) ==0 )
					{
						$sql_query2 = "Insert into marketers(longitude,latitude,token,email,tarehe) values('$longitude','$latitude','$token','$email','$tarehe');";
						mysqli_query($con, $sql_query2) or die (mysqli_error($con));
						mysqli_close($con); 
					}
			
   	 }
 
  
 ?>  