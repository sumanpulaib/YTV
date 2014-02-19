<?php
$x='2010-07-29 12:43:54';
$y='2010-07-22 01:23:42';
   $time1 = new DateTime($x);
$time2 = new DateTime($y);
$interval = $time1->diff($time2);
$seconds=$interval->format('%s');
$minuts=$interval->format('%i');
$hours=$interval->format('%h');
$years=$interval->format('%Y');
$months=$interval->format('%m');
$days=$interval->format('%d');
echo "years\t:".$years."<br>";
echo "months\t:".$months."<br>";
echo "days\t:".$days."<br>";
echo "hours\t:".$hours."<br>";
echo "minutes\t:".$minuts."<br>"	;
echo "second\t:".$seconds;
    ?>
