<?php
$dbhost = 'localhost';
$dbuser = 'i3300981_wp1';
$dbpass = 'G.tqJU([zQRRyl0(6E*81]~8';
$conn = mysqli_connect($dbhost, $dbuser, $dbpass) or die ("Unable to connect") ;


$sql = "SELECT ID,post_title FROM `wp_posts` WHERE post_type ='job_listing'";
$query = mysqli_query($conn, $sql );
$result = array();

 while($row = mysqli_fetch_array($query, MYSQLI_ASSOC)) {
 array_push($result,
 array('ID'=>$row['ID'],
        'post_title'=>$row['post_title']}
        
 echo json_encode(array("result"=>$result));
}else{
   echo "No Matches Found";
}
?>
?>