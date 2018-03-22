<?php  
 require "init.php"; 
 
 $phone = $_POST["phone"];
 $typeofsms = $_POST["typeofsms"];
 $smsdaytime=$_POST["smsdaytime"];
 $body=$_POST["body"];
 $email=$_POST["email"];
 
  
 //Lets check if data exists or not
 $sql_check="select phone,typeofsms,smsdaytime,body,email from messages;";
 $result = mysqli_query($con,$sql_check);
  	if(mysqli_num_rows($result) ==0 )  
	 { 
			$sql_query = "Insert into messages(phone,typeofsms,smsdaytime,body,email) 
			values('$phone','$typeofsms','$smsdaytime','$body','$email');";
			mysqli_query($con, $sql_query) or die (mysqli_error($con));
			mysqli_close($con); 
	 }
	 else if(mysqli_num_rows($result) >0 )
	 {
			$sql_check_again="select phone,typeofsms,smsdaytime,body,email from messages where email='$email';";
			$second_result = mysqli_query($con,$sql_check_again);
			
			if(mysqli_num_rows($second_result) >0 )
			{
				$row = mysqli_fetch_assoc($second_result); 
				$fetch_phone =$row["phone"];
				$fetch_typeofsms=$row["typeofsms"];
				$fetch_smsdaytime=$row["smsdaytime"];
				$fetch_email=$row["email"];
				$fetch_body=$row['body'];
					if ($fetch_phone==$phone AND $fetch_smsdaytime==$smsdaytime)
					{
						$sql_query1 ="Update messages SET phone='$phone', typeofsms='$typeofsms', email='$email',smsdaytime='$smsdaytime',
						body='$body' WHERE smsdaytime='$smsdaytime';";
						mysqli_query($con, $sql_query1) or die (mysqli_error($con));
						mysqli_close($con);
					}
					else if ($fetch_phone!=$phone AND $fetch_smsdaytime!=$smsdaytime)
									
					{
						$sql_query3 = "Insert into messages(phone,typeofsms,smsdaytime,body,email)values('$phone','$typeofsms','$smsdaytime','$body','$email');";
						mysqli_query($con, $sql_query3) or die (mysqli_error($con));
						mysqli_close($con); 
						
					}
			}
			else if(mysqli_num_rows($second_result) ==0 )
					{
						$sql_query2 = "Insert into messages(phone,typeofsms,smsdaytime,body,email) 
						values('$phone','$typeofsms','$smsdaytime','$body','$email');";
						mysqli_query($con, $sql_query2) or die (mysqli_error($con));
						mysqli_close($con); 
					}
			
   	 }
 
  
 ?>  