<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>Ideabtyes OctoShape</title>
	<link rel="stylesheet" href="Democss/style_temp.css" type="text/css" media="all" />
</head>
<style>
.imgClass { 
    background-image: url(http://www.bookingslot.com/SendSmsimage.jpg);
    width: 90px;
    height: 32px;
}
</style>
<script>
function validateForm()
{
	var cname=document.forms["sendSMS"]["CityName"].value;
	var dname = document.forms["sendSMS"]["device"].value;
	if(dname=="selectoption")
	{
	  alert("Please select the DeviceName");
	  return false;
	}
	
}
</script>
<body>
<!-- Header -->
<div id="header">
	<div class="shell">
		<!-- Logo + Top Nav -->
		<div id="top">
			<h1><a href="#">Ideabytes OctoShape</a></h1>
		</div>
	</div>
</div>
<!-- End Header -->
<br/>
<br/>

	<h2 align="center"><b>SMS To-Reset Device</b></h2>
	<br/>
<br/>
<form id="sendSMS" name="sendSMS" action="http://bookingslot.com/SendSMS.php" method="POST" onSubmit="return validateForm()">
<center>
	<table>
	<tr>
		<td>
			<input type="text" id="CityName" name="CityName" style="width:200px;" value="City Name" onfocus="if (this.value == 'City Name') {this.value = '';}" onblur="if (this.value == '') {this.value = 'City Name';}"/>
		</td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td>
			<input type="text" id="AreaName" name="AreaName" style="width:200px;" value="AreaName" onfocus="if (this.value == 'AreaName') {this.value = '';}" onblur="if (this.value == '') {this.value = 'AreaName';}"/>
		</td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td>
			<input type="text" id="Placen" name="Placen" style="width:200px;" value="Place" onfocus="if (this.value == 'Place') {this.value = '';}" onblur="if (this.value == '') {this.value = 'Place';}"/>
		</td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td>
			<select id="device" name="device" style="width:200px;height:25px">
			<option value="selectoption">select DeviceName *</option>
			<option value="Device-1">Device-1</option>
			<option value="Device-2">Device-2</option>
			<option value="Device-3">Device-3</option>
			</select>
		</td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="" class="imgClass"/>
		</td>
	</tr>
	</table>
	</center>
</form>
</body>
</html>