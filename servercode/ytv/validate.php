
<?php
session_start();
$name=$_POST['user'];
$pwd = $_POST['pwd'];
$im=$_POST['imei'];
$_SESSION['imeiNo'] = $im;
//echo "Details Saved<br>";
//echo $_SESSION['imeiNo']."<br>";
//echo $pwd."<br>";
$con1=mysqli_connect("localhost","root","root","ytv");
 include_once 'db_server_functions.php';
$db = new DB_Functions();
// Check connection
if (mysqli_connect_errno())
{
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}	
$query="SELECT password FROM createuser WHERE username like '%$name%'";
$result=mysqli_query($con1,$query);
			$xx=$db->getAllEntriesFromCreateTable($name);
                          while ($row1 = mysql_fetch_array($xx)) {
			
				$password=$row1["password"];
//echo $password;

}
if($password==$pwd)
{
echo "Login Success";
$_SESSION['success'] = "login ok";
?>
<script>
location.replace("http://183.82.9.60/ytv/view_users.php");
</script>
<?php

}
else
{
//echo "Login Failed";
?>
<script>
location.replace("http://183.82.9.60/ytv/login.php?login_fail=faild");
</script>
<?php
}

?>

