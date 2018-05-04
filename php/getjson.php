<?php

$link=mysqli_connect("localhost", "root", "", "capstone" );
if (!$link)
{
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();
}

mysqli_set_charset($link,"utf8");


$sql="select * from building";

$result=mysqli_query($link,$sql);
$data = array();
if($result){

    while($row=mysqli_fetch_array($result)){
        array_push($data,
            array('Bname'=>$row[0],
             'Btype'=>$row[1],
              'Bconsist'=>$row[2],
               'Bdate'=>$row[3],
                'Baddr'=>$row[4],
                  'Bcheck'=>$row[5],
                   'Bgrade'=>$row[6],
                    'BLcheck'=>$row[7]
        ));
    }

    header('Content-Type: application/json; charset=utf8');
     $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
     echo $json;


}
else{
    echo "SQL문 처리중 에러 발생 : ";
    echo mysqli_error($link);
}



mysqli_close($link);

?>
