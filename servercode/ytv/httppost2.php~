<?php
$data = file_get_contents('php://input');
$obj = json_decode($data);
 $name   = $obj->{"name"};
$datetime=$obj->{"datetime"]);
$imei=$obj->{"imei"]);
$activityStatus=$obj->{"activityStatus"]);
//$InternetSpeed=urldecode($_POST['InternetSpeed']);
$StreamStatus=$obj->{"StreamStatus"]);

$con=mysqli_connect("localhost","root","root","ytv");
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

mysqli_query($con,"INSERT INTO userinfo (name,datetime,imei,activityStatus)
VALUES ('$name','$datetime',$imei,'$activityStatus')");
 print "$name";
//echo "hello"+$datetime;
$con1=mysqli_connect("localhost","root","root","ytv");
// Check connection
if (mysqli_connect_errno())
{
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}	
$query="SELECT * FROM recentuserinfo WHERE imei like '%$imei%'";
$result=mysqli_query($con1,$query);
//echo "result".$result->num_rows;
if($result->num_rows==0)
{
	//echo "If"; 
	$res=mysqli_query($con1,"INSERT INTO recentuserinfo VALUES ($imei,'$datetime','$activityStatus','$StreamStatus')");

  	mysqli_close($con1);
}
else
{
	//echo "else";
	$res=mysqli_query($con1,"UPDATE recentuserinfo SET datetime='".$datetime."',activestatus='".$activityStatus."',StreamStatus='".$StreamStatus."' WHERE imei=$imei");

 	mysqli_close($con1);
}
?> 
