<?php
/* check logged session */
session_start();
if(!isset($_SESSION['success'])){
    if(basename($_SERVER['PHP_SELF']) != "login.php"){
        header("Location: login.php");
        exit;
    }
}
?>
