
<?php

$con=mysqli_connect("localhost", "root", "", "capstone");


if(mysqli_connect_errno($con)){
  echo "Failed to connect to MySQL : " . mysqli_connect_error();

}

mysqli_set_charset($con, "utf8");

$res = mysqli_query($con, "select * from building");

$result = array();

while($row = mysqli_fetch_array($res)){

  array_push($result,
      array('Bname'=>$row[0], 'Btype'=>$row[1], 'Bconsist'=>$row[2], 'Bdate'=>$row[3], 'Baddr'=>$row[4],
            'Bcheck'=>$row[5], 'Bgrade'=>$row[6], 'BLcheck'=>$row[7]
    ));
}

echo json_encode(array("result"=>$result), JSON_UNESCAPED_UNICODE);

mysqli_close($con);
?>
