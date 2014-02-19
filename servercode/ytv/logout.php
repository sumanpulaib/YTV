<?php

session_start();
if((isset($_SESSION['success'])) && ($_SESSION['success'] == "login ok")){
//echo "i am in view";
//include "footer_login.php";
//echo "Logout Success";
$_SESSION['success']="";

}else{
?>
<script>
location.replace("http://183.82.9.60/ytv/loginscreen.php");
</script>
<?php
}
?>
<!DOCTYPE html>
<html>
</head>
<body style='background:#d1be94;'> 
<div style="float: center; background-color: #DBDBDB;">
	<table style="width:100%;height:10%;">
		<tr bgcolor="#2E8B57">
     			 <td style="padding-left: 5px; 
                  		 padding-bottom:3px; 
                   		color:#FF6347;
				font-size:3em; 
                   		font-weight:bold"><center>Thank You</center>
			</td>
		</tr>
	</table>
</div>
<br><br><br><br>

</body>
</head>
</html>
