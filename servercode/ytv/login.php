
<?php
$imeiNo = $_GET['imeino'];
//echo "asdfdsf".$imeiNo;
?>
<!DOCTYPE html>
<html>
    <head>
<script type="text/javascript">
 
function validateMyForm()

{
// Create validation tracking variable
var valid = true;
var validationMessage; 
var name1 = document.getElementById('name').value;
var pwd1=document.getElementById('pwd').value;
var imei1=document.getElementById('imei').value;

	if((name1.length==0)&&(pwd1.length==0)){
        // Validate all fields
	valid = false;
	alert("Please Enter all Details");
		return false;
 
    	}
	else if (document.getElementById('name').value.length == 0)

        {
            
            validationMessage = validationMessage + ' Name is missing\r\n';

            valid = false;
           alert("Username is missing");
		return false;
        }

	// Validate password
       else if (document.getElementById('pwd').value.length == 0)

        {

            validationMessage = validationMessage + 'Password is missing\r\n';

            valid = false;
alert("Password is missing");
		return false;

        }
else
{
 window.location.href =  'http://183.82.9.60/ytv/validate.php?name='+name1+'&pwd='+pwd1+'&imei='+imei1;

}

}//validateMyForm()

</script>
</head>
 <body style='background:#d1be94;'> 
<div style="float: center; background-color: #DBDBDB;">
	<table style="width:100%;height:10%;">
		<tr bgcolor="#2E8B57">
     			 <td style="padding-left: 5px; 
                  		 padding-bottom:3px; 
                   		color:#FF6347;
				font-size:3em; 
                   		font-weight:bold"><center>Welcome To Login</center>
			</td>
		</tr>
	</table>
</div>  
<br />        
<form  action="http://183.82.9.60/ytv/validate.php" method="post">
<table align="center" style="width:10%;height:10%;">
<tr>
<td>Name</td><td> <input type="text" name="user" id="name" value="" placeholder="please enter Username" required></td>
</tr>
<tr>
<td>Password</td><td> <input type="password" name="pwd" id="pwd" value="" placeholder="please enter Password" required></td>
</tr>
</table> 
<br> 
<table align="center">
<tr >
<input type="hidden" id="imei" name= "imei" value="<?php echo $imeiNo; ?>">
<td ><input type="submit" value="Login"></td>
</tr>
</table>
<br><br>
<table align="center">
<tr>
<td><?php
if($_GET['login_fail'] == "faild")
{
 echo "Login Failed Please try again!	";
}
?></td>
</tr>
</table>
</form>
</div>
</body>
</html> 

