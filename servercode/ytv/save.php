
<?php

$username=$_GET['name'];
$town = $_GET['cityname'];
$email = $_GET['email'];
$password=$_GET['pwd'];
$imei = $_GET['imei'];

//echo $name;
//echo $imei."<br>I am in SMS";

   
$con1=mysqli_connect("localhost","root","root","ytv");
// Check connection
if (mysqli_connect_errno())
{
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
	
$query="SELECT * FROM createuser WHERE username like '%$username%'";
$result=mysqli_query($con1,$query);
if($result->num_rows==0)
{
//echo "in if";
	$res=mysqli_query($con1,"INSERT INTO createuser (username,email,city,password,imei) VALUES ('$username','$email','$town','$password','$imei')");
echo "Details Saved";
  	mysqli_close($con);

}
else
{
echo "User Already Exist!";
}

?> 

