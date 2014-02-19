<?php
$imeiNo = $_GET['imeiNo'];
//echo $imeiNo;
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
                   		font-weight:bold"><center>WelCome To YTV</center>
			</td>
		</tr>
	</table>
</div>
<br><br><br><br>
<table align="center">
<tr >
<input type="hidden" id="imei" name= "imei" value="<?php echo $imeiNo; ?>">
<td><a href="login.php?imeino=<?php echo $imeiNo ?>">
    <button>Login</button>
</tr>
</table>
<br>
<table align="center">
<tr>
<td><a href="createuser.php?imeiNo=<?php echo $row['imei']; ?>">
    <button>Create User</button>
</tr>
</table>
</body>
</head>
</html>

