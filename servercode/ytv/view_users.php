<?php 
session_start();
if((isset($_SESSION['success'])) && ($_SESSION['success'] == "login ok")){
//echo "i am in view";
//include "footer_login.php";
//echo "entered";
?>
<meta http-equiv="refresh" content="5" > 
<?php
echo  "<body style='background:#211213;'>";
function DisplayLatitude($strlatitude) {
    $latitude = (float)$strlatitude;
    if ($latitude > 0) {
        return $latitude . " N";
    }
    else if ($latitude < -999.0) {
        return "Unknown location";
    }
    else if ($latitude < 0) {
        $latitude = -1*$latitude;
        return $latitude . " S";
    }
    else {
        return $latitude;
    }
}


function DisplayLongitude($strlongitude) {
    $longitude = (float)$strlongitude;
    if ($longitude > 0) {
        return $longitude . " E";
    }
    else if ($longitude < -999.0) {
        return "";
    }
    else if ($longitude < 0) {
        $longitude = -1*$longitude;
        return $longitude . " W";
    }
    else {
        return $longitude;
    }
}
?>

<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function(){
 
            });
             
            function sendPushNotification(id){
                var data = $('form#'+id).serialize();
                $('form#'+id).unbind('submit');
                $.ajax({
                    url: "send_message.php",
                    type: 'GET',
                    data: data,
                    beforeSend: function() {
 
                    },
                    success: function(data, textStatus, xhr) {
                          $('.txt_message').val("");
                    },
                    error: function(xhr, textStatus, errorThrown) {
 
                    }
                });
                return false;
            }
        </script>
        <style type="text/css">
            .container{
                width: 1100px;
                margin: 0 auto;
                padding: 0;
            }
            h1{
                font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
                font-size: 18px;
                color: #777;
            }
	    h2{
                font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
                font-size: 14px;
                color: #777;
            }
            
            table.users {
                float: left;
                font-size: 12px;
                font-style: normal;
                font-weigth: bold;
                list-style: none;
                border: 1px solid #dedede;
                padding: 10px;
                margin: 0 5px 5px 0;
                border-radius: 3px;
                -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.35);
                -moz-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.35);
                box-shadow: 0 1px 1px rgba(0, 0, 0, 0.35);
                font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
                color: #555;
            }

            table.users td {
                float: left;
                width: 200px;
                list-style: none;
                padding: 2px;
                margin: 0 2px 2px 0;
                border-radius: 2px;
                font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
                color: #555;
            }

            table.users labelHeading {
                font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
                font-size: 12px;
                font-style: normal;
                font-variant: normal;
                font-weight: bold;
                color: #393939;
                display: block;
                float: left;
            }

            table.users label {
                font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
                font-size: 12px;
                font-style: normal;
                font-variant: normal;
                font-weight: normal;
                color: #393939;
                display: block;
                float: left;
            }
        </style>
    </head>
    <body >

        <?php
        include_once 'db_server_functions.php';
$con=mysqli_connect("localhost","root","root","ytv");
        $db = new DB_Functions();
        $users = $db->getAllLoggedInUserInfo();
        $userCnt = $db->getNumberOfUsersWithAccounts();
	$active=$db->getAllAppActiveInfo()		;
	
            $no_of_ActiveUsers = mysql_num_rows($active);	

        if ($users != false)
            $no_of_users = mysql_num_rows($users);
        else
            $no_of_users = 0;
        ?>
        <div class="container">
	<div style="float: center; background-color: #DBDBDB;">
	<table style="width:100%;height:10%;">
		<tr bgcolor="#2E8B57">
     			 <td style="padding-left: 5px; 
                  		 padding-bottom:3px; 
                   		color:#FF6347;
				font-size:3em; 
                   		font-weight:bold"><center>Deccan TV Dashboard</center><td><label><a href="logout.php?imeiNo=<?php echo $row['imei']; ?>">
    <center><button>Logout</button></center>
</a></label></td>
			</td>
		</tr>
	</table>
</div>
<div style="float: center; background-color: #DBDBDB;">
	<table width="40%">
		<tr>
			<td> <h2>Number of Devices currently active </h2>
			</td>
			<td><h2><?php echo $no_of_ActiveUsers; ?></h2>
			</td>
		</tr>
		<tr>
		<td> <h2>Last Updated at </h2>
		</td>
		<td><h2><?php $date = date('Y-m-d H:i:s'); echo $date; ?></h2>
		</td>
		</tr>
		<tr>
		<td><h2>Currently Running</h2>
		</td>
		<td><h2><img src="green.png"  width="25" height="25"></h2>
		</td>
		</tr>
		<tr>
		<td><h2>Currently Not Running</h2>
		</td>
		<td><h2><img src="yellow.png"  width="25" height="25"></h2>
		</td>
		</tr>
		<tr>
		<td><h2>App Uninstalled/No Network</h2>
		</td>
		<td><h2><img src="red.png"  width="25" height="25"></h2>
		</td>
		</tr>
		</table>
</div>
     <!-- <h2>Number of Devices currently active : <?php echo $no_of_ActiveUsers; ?>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h2>            
            <h2>Last Updated at : <?php $date = date('Y-m-d H:i:s'); echo $date; ?> 
	<h2>Currently Running<img src="green.png"  width="25" height="25"></h2>
	<h2>Currently Not Running<img src="yellow.png"  width="25" height="25"></h2>
	<h2>App Uninstalled<img src="red.png"  width="25" height="25"></h2> -->
            	
                <?php
 		if ($no_of_users > 0) {
                    ?>
<br><br>
                    <div class="table">
	<TABLE border=10 bgcolor=#d1be94 width="100%">
<TR BGCOLOR="#a1be94"> <TH>Device ID</TH><TH>Active Status</TH><TH>Location</TH><TH>NetSpeed</TH> <TH>Date Time
</TH><TH>Reset Button</TH><TH>Add Details</TH><TH>StreamStatus</TH></TR>
                    <tr>
                   <?php
		    session_start();
                    $cnt = 0;
                    while ($row = mysql_fetch_array($users)) {
			$status=$row["activestatus"];
			$InternetSpeed=$row["InternetSpeed"];
			$datetimetable=$row["datetime"];
			$imei=$row["imei"];
			$StreamStatus=$row["StreamStatus"];
			$dt = new DateTime();
			$datetimesys= $dt->format('Y-m-d h:i:s a');
			//calculating time diff for web status change
			$time1 = new DateTime($datetimesys);
			$time2 = new DateTime($datetimetable);
			$interval = $time1->diff($time2);
			$seconds=$interval->format('%s');
			$minutes=$interval->format('%i');
			$hours=$interval->format('%h');
			$years=$interval->format('%Y');
			$months=$interval->format('%m');
			$days=$interval->format('%d');
			//echo "years\t:".$years."<br>";
			//echo "months\t:".$months."<br>";
			//echo "days\t:".$days."<br>";
			//echo "hours\t:".$hours."<br>";
			//echo "minutes\t:".$minutes."<br>"	;
			//echo "second\t:".$seconds;
			$pKey = $row["imei"];
			   $cnt++;
                        ?>
                        <tr align="center">	
                      <td><label><?php echo $row["imei"];  ?></label></td> 
                      <?php if($status==1||$status=="true"){?>
		         <?php if($minutes>=3||$hours>=1){ 
			$con1=mysqli_connect("localhost","root","root","ytv");
			$res=mysqli_query($con1,"UPDATE recentuserinfo SET InternetSpeed='0',activestatus='deleted' WHERE imei='$imei'");
			$s = "enered in if";
			}else{ $s = "in else"; }?>
                    	<td ><label><?php if($s == "enered in if"){echo "<img src='red.png' style='width:30px;hieght:30px;' >";}
			else{echo "<img src='green.png' style='width:30px;hieght:30px;' >";}?></label></td>
			<?php 
			}
			elseif($minutes>=3){
			$con1=mysqli_connect("localhost","root","root","ytv");
			$res=mysqli_query($con1,"UPDATE recentuserinfo SET InternetSpeed='0',StreamStatus='0',activestatus='deleted' WHERE imei='$imei'");
			?>
			<td ><label><?php echo "<img src='red.png' style='width:30px;hieght:30px;' >" ;?></label></td>
			<?php	
			}
				
			elseif($status=="yellow")
			{
			?>
 			<td ><label><?php echo "<img src='yellow.png' style='width:30px;hieght:30px;' >"  ;    ?></label></td>
			<?php
			}
?>

<?php 
$xx=$db->getAllEntriesFromNetTable($row["imei"]);
if($row1 = mysql_fetch_array($xx)){
$town=$row1["town"];
}
//echo $town;
if($town == "")
{
?>
<td ><label>add Location</label></td>
<?php
}
else{
?>
 <td ><label><?php echo  $town ;    $town = "";?></label></td>	
<?php
}
?>
<td ><label><?php echo  $InternetSpeed ;    ?></label></td>
<td><label><?php echo $row["datetime"]     ?></label></td> 
<td><label><a href="sendsms.php?mobileNo=<?php echo $mobilenumber; ?>&imei=<?php  echo $imei; ?> &town=<?php  echo $town; ?>">
    <button>Reset</button>
</a></label></td>
<td><label><a href="loginscreen.php?imeiNo=<?php echo $row['imei']; ?>">
    <button>addContact</button>
</a></label></td>
<td><label><?php echo $row["StreamStatus"]     ?></label></td> 
</tr>
<?php }//first while ending ?>
<tr>
</tr>
</table>
<?php } else { ?>
<li> IBPlayer - No users currently active. (Total number of user accounts : <?php echo $userCnt; ?>) </li>
 <?php } ?>
</div>
</body>
</html>
<?php
}else{
?>
<script>
location.replace("http://183.82.9.60/ytv/loginscreen.php");
</script>
<?php
}
?>
