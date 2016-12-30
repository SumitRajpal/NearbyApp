<?PHP

include_once("Connection.php");
$latitude=$_GET['latitude'];
$longitude=$_GET['longitude'];

$query = "select * from(SELECT name,latitude,longitude,round((((acos(sin((".$latitude."*pi()/180)) * sin((`latitude`*pi()/180))+cos((".$latitude."*pi()/180)) * cos((`latitude`*pi()/180)) * cos(((".$longitude."- `longitude`)*pi()/180))))*180/pi())*60*1.1515*1.609344),1) as distance
FROM location) as temp order by distance
";

$result = mysqli_query($conn, $query);
while(($row = mysqli_fetch_assoc($result)) == true){
	$data[]=$row;
}
echo json_encode($data);

?>