
<?php
$imeiNo = $_GET['imeiNo'];
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
var conpwd1=document.getElementById('conpwd').value;
var city1=document.getElementById('city').value;
var email1=document.getElementById('email').value;
var imei1=document.getElementById('imei').value;

	if((name1.length==0)&&(pwd1.length==0)&&(conpwd1.length==0)&&(email1.length==0)&&(city1.length==0)){
        // Validate all fields
	valid = false;
	alert("Please Enter all Details");
		return false;
 
    	}
	else if (document.getElementById('name').value.length == 0)

        {
            
            validationMessage = validationMessage + ' Username is missing\r\n';

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
// Validate password
       else if (document.getElementById('conpwd').value.length == 0)

        {

            validationMessage = validationMessage + 'Password is missing\r\n';

            valid = false;
alert("Confirm Password is missing");
		return false;

        }
// Validate password
       else if (document.getElementById('email').value.length == 0)

        {

            validationMessage = validationMessage + 'Password is missing\r\n';

            valid = false;
alert("Email id is missing");
		return false;

        }
// Validate city name
       else if (document.getElementById('city').value.length == 0)

        {

            validationMessage = validationMessage + 'Password is missing\r\n';

            valid = false;
alert("City name is missing");
		return false;

        }
else if(pwd1!=conpwd1){
alert("Passwords DoNot Match");
		return false;
}
else
{
 window.location.href =  'http://183.82.9.60/ytv/save.php?name='+name1+'&pwd='+pwd1+'&cityname='+city1+'&email='+email1;

}

}//validateMyForm()

</script>
</head> 
<br />
<div style="float: center; background-color: #DBDBDB;">
	<table style="width:100%;height:10%;">
		<tr bgcolor="#2E8B57">
     			 <td style="padding-left: 5px; 
                  		 padding-bottom:3px; 
                   		color:#FF6347;
				font-size:3em; 
                   		font-weight:bold"><center>WelCome To Registration</center>
			</td>
		</tr>
	</table>
</div> 
<br><br>
 <body style='background:#d1be94;'>         
<form  onsubmit="return false;">
<table align="center" style="width:10%;height:10%;">
<tr>
<td>Username</td><td> <input type="text" name="user" id="name" value="" placeholder="please enter Username" ></td>
</tr>
<tr>
<td>City</td><td> <input type="text" name="city" id="city" value="" placeholder="please enter City" ></td>
</tr>
<tr>
<td>Email</td><td> <input type="text" name="email" id="email" value="" placeholder="please enter Email" ></td>
</tr>
<tr>
<td>Password</td><td> <input type="password" name="pwd" id="pwd" value="" placeholder="please enter Password" ></td>
</tr>
<tr>
<td>Confirm Password</td><td> <input type="password" name="conpwd" id="conpwd" value="" placeholder="Re enter Password" ></td>
</tr>
</table> 
<br> 
<table align="center">
<tr >
<input type="hidden" id="imei" name= "imei" value="<?php echo $imeiNo; ?>">
<td ><input type="submit" value="Save" onClick="validateMyForm();"></td>
</tr>
</table>
</form>
</div>
</body>
</html> 

