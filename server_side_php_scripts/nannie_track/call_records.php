<?php  
 require "init.php"; 
 
 $phone = $_POST["phnumber"];
 $calltype = $_POST["dir"];
 $calldaytime=$_POST["callDayTime"];
 $callduration=$_POST["callDuration"];
 $email=$_POST["email"];
 
  
 //Lets check if data exists or not
 $sql_check="select phone,calltype,calldaytime,callduration,email from callrecords;";
 $result = mysqli_query($con,$sql_check);
  	if(mysqli_num_rows($result) ==0 )  
	 { 
			$sql_query = "Insert into callrecords(phone,calltype,calldaytime,callduration,email) 
			values('$phone','$calltype','$calldaytime','$callduration','$email');";
			mysqli_query($con, $sql_query) or die (mysqli_error($con));
			mysqli_close($con); 
	 }
	 else if(mysqli_num_rows($result) >0 )
	 {
			$sql_check_again="select phone,calltype,calldaytime,callduration,email from callrecords where email='$email';";
			$second_result = mysqli_query($con,$sql_check_again);
			
			if(mysqli_num_rows($second_result) >0 )
			{
				$row = mysqli_fetch_assoc($second_result); 
				$fetch_phone =$row["phone"];
				$fetch_calltype=$row["calltype"];
				$fetch_callduration=$row["callduration"];
				$fetch_email=$row["email"];
				$fetch_calldaytime=$row['calldaytime'];
					if ($fetch_phone==$phone AND $fetch_calldaytime==$calldaytime)
					{
						$sql_query1 ="Update callrecords SET phone='$phone', calltype='$calltype', email='$email',calldaytime='$calldaytime',
						callduration='$callduration' WHERE email='$email';";
						mysqli_query($con, $sql_query1) or die (mysqli_error($con));
						mysqli_close($con);
					}
					else if ($fetch_phone!=$phone AND $fetch_calldaytime!=$calldaytime)
									
					{
						$sql_query3="Insert into callrecords(phone,calltype,calldaytime,callduration,email)values('$phone','$calltype','$calldaytime','$callduration','$email');";
						mysqli_query($con, $sql_query3) or die (mysqli_error($con));
						mysqli_close($con); 
						
					}
			}
			else if(mysqli_num_rows($second_result) ==0 )
					{
						$sql_query2="Insert into callrecords(phone,calltype,calldaytime,callduration,email)values('$phone','$calltype','$calldaytime','$callduration','$email');";
						mysqli_query($con, $sql_query2) or die (mysqli_error($con));
						mysqli_close($con); 
					}
			
   	 }
 
  
 ?>  