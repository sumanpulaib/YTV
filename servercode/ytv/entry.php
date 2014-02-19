
<?php
session_start();
$imeiNo = $_SESSION['imeiNo'];
//echo "asdfdsf".$imeiNo;
?>
<!DOCTYPE html>
<html>
    <head>
<script type="text/javascript">
function validateMyForm()
{
 // Create validation tracking variables
 var valid = true;
var validationMessage; 
var name1 = document.getElementById('name').value;
var city1=document.getElementById('city').value;
var mobilenum=document.getElementById('mnumber').value;
var countryname=document.getElementById('country').value;
var imei1=document.getElementById('imei').value;

if((name1.length==0)&&(mobilenum.length==0)&&(city1.length==0)){
        // Validate all fields
		valid = false;
		alert("Please Enter all Details");
		return false;
 
   		 }
		else if (document.getElementById('name').value.length == 0)

        	{
            
            		validationMessage = validationMessage + ' Name is missing\r\n';

           		 valid = false;
           		alert("Name is missing");
			return false;
       		}
		
		// Validate mobile number
		else if (document.getElementById('mnumber').value.length == 0)

        	{

            		validationMessage = validationMessage + 'Mobile Number is missing\r\n';

            		valid = false;
			alert("Mobile Number is missing");
			return false;
		 }

        // Validate city name

       		else if (document.getElementById('city').value.length == 0)

        	{

            		validationMessage = validationMessage + 'City name is missing\r\n';

            		valid = false;
			alert("City name is missing");
			return false;

       		 }

		else
		{
			 window.location.href =  'http://183.82.9.60/ytv/addcontact.php?name='+name1+'&mnum='+mobilenum	+'&cityname='+city1+'&cname='+countryname+'&imei='+imei1;

		}

		}//validateMyForm()

</script>
</head>
<body style='background:#d1be94;'>   
<br />
<div style="float: center; background-color: #DBDBDB;">
	<table style="width:100%;height:10%;">
		<tr bgcolor="#2E8B57">
     			 <td style="padding-left: 5px; 
                  		 padding-bottom:3px; 
                   		color:#FF6347;
				font-size:3em; 
                   		font-weight:bold"><center>Add Contact Details</center>
			</td>
		</tr>
	</table>
</div>        
<form  onsubmit="return false;">
<table align="center" style="width:10%;height:10%;">
<tr>
<td>Name</td><td> <input type="text" name="user" id="name" value="" placeholder="please enter Name" ></td>
</tr>
<tr>
<td>MobileNumber</td><td> <input type="text" name="mnumber" id="mnumber" value="" placeholder="please enter mobile number" ></td>
</tr>
<tr>
<td>City</td><td> <input type="text" name="city" id="city" value="" placeholder="please enter City" ></td>
</tr>
<tr><label for="name"><td>Country</td></label><td><select name="country" id="country" >
                                        <option value="India">India</option>
                                        <option value="France">France</option>
                                        <option value="Italy">Italy</option>
</tr>
 						</td></select> 
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

