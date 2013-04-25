<?php

$databasehost = "karlashop.dk.mysql";
$databasename = "karlashop_dk";
$databaseusername ="karlashop_dk";
$databasepassword = "KarlaMarla";

$con = mysql_connect($databasehost,$databaseusername,$databasepassword) or die(mysql_error());
mysql_select_db($databasename) or die(mysql_error());
$query = file_get_contents("php://input"); 
$sth = mysql_query($query);

if (mysql_errno()) { 
    header("HTTP/1.1 500 Internal Server Error");
    echo $query.'\n';
    echo mysql_error(); 
}
else
{
    $rows = array();
    while($r = mysql_fetch_assoc($sth)) {
        $rows[] = $r;
    }
    print json_encode($rows);
}
?>
