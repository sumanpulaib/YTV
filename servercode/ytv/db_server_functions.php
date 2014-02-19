<?php 
class DB_Functions {
    private $passwordSeed = "MobileReporter24Feb2013";
 
    private $db;
 
    //put your code here
    // constructor
    function __construct() {
        include_once './db_connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }
 
    // destructor
    function __destruct() {
 
    }
 
    public function isAuthorized($email) {
       $result = mysql_query("SELECT Authorized FROM Users where email='$email'");
       $returned = mysql_fetch_array($result);
       if ($returned[0] == "1") {
          return true;
       }
       else {
          return false;
       }
    }


    private function decryptPassword($email,$password) {
	$result = mysql_query("SELECT AES_DECRYPT(encrypted_password,CONCAT('$email','$this->passwordSeed')) FROM Users WHERE email='$email'");
        $returned = mysql_fetch_array($result);
        return $returned[0]; 
    }


    public function validateUser($username,$password) {
        $result = mysql_query("SELECT Username, Email FROM Users where username='$username'");
        $returned = mysql_fetch_array($result);
        if ($username == $returned[0]) {
            $email = $returned[1];
            $decryptedPassword  = $this->decryptPassword($email,$password);
            if ($decryptedPassword == $password) {
                return  json_encode(array('Result' => 'AOK', 'Reason' => ''));
            }
            else {
                return json_encode(array('Result' => 'NOK', 'Reason' => 'Bad user credentials'));
            }
        }
        else {
            return json_encode(array('Result' => 'NOK', 'Reason' => 'User does not exists'));
        } 
    }


    public function BooleanResultValidateUser($username,$password) {
        $result = mysql_query("SELECT Email FROM Users where username='$username'");
        if ($result) {
            $returned = mysql_fetch_array($result);
            $email = $returned[0];
            $decryptedPassword = $this->decryptPassword($email,$password);
            if ($decryptedPassword == $password) {
                return true;
            }
            else {
                return false;
           }
        }
    }




    public function updatePosition($username, $password, $dateTime, $latitude, $longitude, $heading, $speed) {
        if ($this->validateUser($username,$password)) {
            if ($speed == 0.0) {
              $heading = -1.0;
            }
            $result = mysql_query("UPDATE Users SET lastUpdatedAt='$dateTime', latitude=$latitude, longitude=$longitude, speed=$speed, heading=$heading WHERE username='$username'");
            if ($result) {
                return json_encode(array('Result' => 'OK', 'Reason' => ''));
            }
            else {
                return json_encode(array('Result' => 'NOK', 'Reason' => 'Unknown Reason'));
            }
        }
        else {
            return json_encode(array('Result' => 'NOK', 'Reason' => 'Bad credentials'));
        }
    }

    public function getRegId($email, $password) {
        $validUser = $this->validateUser($email,$password);
        if ($validUser == "NOK") {
           return "NOK";
        } 
        $result = mysql_query("SELECT gcm_regid FROM Users where email='$email'");
        $returned = mysql_fetch_array($result);
        return $returned[0];
    }



 
 
  
    public function AddRequest($eventName ,$eventDescription, $eventExpiry, $eventAddress,$latitude, $longitude, $notificationDistance, $recordDistance) {
        $sql = "INSERT INTO Request(RequestDateTime,EventName,EventDescription,EventExpiry,Address,Latitude,Longitude,NotificationDistance,RecordDistance) VALUES (NOW(),'$eventName','$eventDescription','$eventExpiry','$eventAddress',$latitude,$longitude,$notificationDistance,$recordDistance)";        
        //error_log($sql,0);
        $result = mysql_query($sql);
        error_log($result,0);
        return $result;
    }
    
    
    public function getAllContactsForUser($username) {
        
        $response_array = Array();
        $result = mysql_query("SELECT FriendUsername FROM FriendList WHERE Username='$username' ORDER BY FriendUsername");
        while($row = mysql_fetch_array($result)){
            $result2 = mysql_query("SELECT Fullname, Email FROM Users WHERE Username='$row[0]'");
            $row2 = mysql_fetch_array($result2);
            $row_array['Username'] = $row[0];
            $row_array['Fullname'] = $row2[0];
            $row_array['Email'] = $row2[1];
            array_push($response_array,$row_array);            
        }
        echo json_encode($response_array);
     }


     public function getAllGroupsForUser($username) {
        $response_array = Array();
        $result = mysql_query("SELECT Groupname, GroupDescription FROM GroupNames WHERE Username='$username' ORDER BY Groupname");
        while($row = mysql_fetch_array($result)){
            $groupname = $row[0];
            $groupdesc = $row[1];
            $result = mysql_query("SELECT FriendList.FriendUsername FROM FriendList,GroupNames WHERE GroupNames.Groupname='$groupname' ORDER BY FriendList.FriendUsername");
            while($row = mysql_fetch_array($result)){
                $username = $row[0];
                $result2 = mysql_query("SELECT Fullname, Email FROM Users WHERE Username='$row[0]'");
                $row2 = mysql_fetch_array($result2);
                $row_array['Groupname'] = $groupname;
                $row_array['Groupdesc'] = $groupdesc;
                $row_array['FriendUsername'] = $username;
                $row_array['FriendFullname'] = $row2[0];  
               array_push($response_array,$row_array);            
            }
        }
        echo json_encode($response_array);
     }
public function getAllAppActiveInfo() { 
        //$result = mysql_query("select name, datetime, imei,activityStatus FROM userinfo Where activityStatus='1'"); old
$result = mysql_query("select * FROM recentuserinfo WHERE activestatus='true'"); 
        return $result; 	
    }
public function getAllEntriesFromNetTable($x)
{
$result = mysql_query("select town from userdetails where imei='$x' "); 
        return $result; 
}
public function getAllEntriesFromCreateTable($x)
{
$result = mysql_query("select password from createuser where username like'%$x%' "); 
        return $result; 
}
    public function getAllLoggedInUserInfo() { 
        //$result = mysql_query("select name, datetime, imei,activityStatus FROM userinfo Where activityStatus='1'"); old
$result = mysql_query("select * FROM recentuserinfo"); 
        return $result; 
    } 
    
    public function getNumberOfUsersWithAccounts() {
        $result = mysql_query("select count(*) from recentuserinfo");
        $row = mysql_fetch_array($result);
        return $row[0];
    }
}
?>
