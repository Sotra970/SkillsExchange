<?php
$dbhost = 'localhost';
$dbuser = 'i3300981_wp1';
$dbpass = 'G.tqJU([zQRRyl0(6E*81]~8';
$conn = mysqli_connect($dbhost, $dbuser, $dbpass) or die ("Unable to connect") ;
if(! $conn )
{
 echo 'Could not connect: ' . mysqli_error();
}
error_reporting(-1);
ini_set('display_errors', 'On');
mysqli_set_charset($conn, 'utf8');

$search ="";
if(isset($_REQUEST['query'] )){
   $search = $_REQUEST['query'];
}
if($search != ""){

   $sql = "SELECT ID,post_title,post_content,post_date FROM `wp_posts` WHERE post_title LIKE '%".$search."%'";
 
    mysqli_select_db($conn,'i3300981_wp1');

$query = mysqli_query($conn, $sql ) or die ("Error: ".mysqli_error($conn));;
$result = array();


while($row = mysqli_fetch_array($query, MYSQLI_ASSOC)){

array_push($result,
    array('ID'=>$row['ID'],
        'post_title'=>$row['post_title'],
        'post_content'=>$row['post_content'],
        'post_date'=>$row['post_date']
    ));
}
    echo json_encode(array("result"=>$result));
}else{
   echo "No Matches Found";
}
?>