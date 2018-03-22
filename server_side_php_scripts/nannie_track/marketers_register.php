<?php  
 require "init.php"; 
 
 //registering clent on the server part
 $fname = $_POST["fname"];
 $sname = $_POST["sname"];
 $phone=$_POST["phone"];
 $email=$_POST["email"];
 $owner_email=$_POST["owner_email"];
 $activate="Demo: Alani Tech--Call +2 54717 264871";
 

  //Lets check if data exists or not
 $sql_check="select fname,sname,phone,email,owner_email,activate from marketers;";
 $result = mysqli_query($con,$sql_check);
 
	if(mysqli_num_rows($result) ==0 )  
	 { 
	$sql_query = "Insert into marketers(fname,sname,phone,email,owner_email,activate) values('$fname','$sname','$phone','$email','$owner_email','$activate');";
	mysqli_query($con, $sql_query) or die (mysqli_error($con));
	mysqli_close($con); 
	$json['reply']="Record Saved Succesfully";
		echo json_encode($json);
	 }
	 else if(mysqli_num_rows($result) >0 )
	 {
		$sql_check_again="select fname,sname,phone,email,owner_email,activate from marketers where email='$email' or owner_email='$owner_email';";
		$second_result = mysqli_query($con,$sql_check_again);
			 
			if(mysqli_num_rows($second_result) >0 )
			{
				$row = mysqli_fetch_assoc($second_result); 
				$fetch_mail =$row["email"];
				$fetch_phone=$row["phone"];
				$fetch_fname=$row["fname"];
				$fetch_sname=$row["sname"];
				$fetch_owner_email=$row["owner_email"];
			
				if ($fetch_mail==$email)
				{
					$sql_query1 ="Update marketers SET fname='$fname', sname='$sname', email='$email',phone='$phone',owner_email='$owner_email' WHERE email='$email';";
					mysqli_query($con, $sql_query1) or die 
					(mysqli_error($con));
					mysqli_close($con); 
					$json['reply']="You've already Registered".$fetch_fname."  " .$fetch_sname;
					echo json_encode($json);
				}
				else if ($fetch_mail!=$email)
				{
					$json['reply']="You've already Registered".$fetch_fname."  " .$fetch_sname." "."Current Version Supports 1 user";
					echo json_encode($json);
				}
							
			}
			else if (mysqli_num_rows($second_result) ==0)
				{
				$sql_query2 = "Insert into marketers(fname,sname,phone,email,owner_email,activate) 
				values('$fname','$sname','$phone','$email','$owner_email','$activate');";
					mysqli_query($con, $sql_query2) or die (mysqli_error($con));
				mysqli_close($con); 
				$json['reply']="Record Saved Succesfully";
				echo json_encode($json);
				}
			
	
	 }
	    
 ?>  