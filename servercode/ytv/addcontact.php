<?php

$name=$_GET['name'];
$mobilenumber = $_GET['mnum'];
$town = $_GET['cityname'];
$country = $_GET['cname'];
$imei = $_GET['imei'];
echo "Details Saved";
//echo $name;
//echo $imei."<br>I am in SMS";
$con1=mysqli_connect("localhost","root","root","bjp");
// Check connection
if (mysqli_connect_errno())
{
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}	
$query="SELECT * FROM userdetails WHERE imei like '%$imei%'";
$result=mysqli_query($con1,$query);
if($result->num_rows==0)
{
//echo "in if";
	$res=mysqli_query($con1,"INSERT INTO userdetails (name,mobilenumber,town, country,imei) VALUES ('$name','$mobilenumber','$town','$country','$imei')");

  	mysqli_close($con);

}
else
{
//echo "in else".$mobilenumber;
$some="UPDATE userdetails SET name='".$name."',mobilenumber='".$mobilenumber."',town='".$town."',country='".$country."' WHERE imei like '%$imei%' ";
//echo $some;
	$res=mysqli_query($con1,$some);

 	mysqli_close($con);
}

?> 
