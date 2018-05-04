<?php
error_reporting(E_ALL);
ini_set('display_errors',1);

$link=mysqli_connect("localhost", "root", "", "capstone");
if (!$link)
{
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();
}

mysqli_set_charset($link,"utf8");


//POST 값을 읽어온다.
$country=isset($_POST['Bname']) ? $_POST['Bname'] : '';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


if ($country !="" ){

    $sql="select * from building where Bname like '$country'";

    $result=mysqli_query($link,$sql);
    $data = array();
    if($result){

        $row_count = mysqli_num_rows($result);

        if ( 0 == $row_count ){

            array_push($data,
                array( 'id'=>'-1',
                'name'=>'N',
                'Bname'=>$country)
            );

            if (!$android) {

                echo "'";
                echo $country;
                echo "'은 찾을 수 없습니다.";

                echo "<pre>";
                print_r($data);
                echo '</pre>';
            }else
            {
                header('Content-Type: application/json; charset=utf8');
                $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
                echo $json;
            }

        }
        else{

            while($row=mysqli_fetch_array($result)){
                array_push($data,
                    array('Bname'=>$row["Bname"],
                    'Btype'=>$row["Btype"],
                    'Bconsist'=>$row["Bconsist"],
                    'Bdate'=>$row["Bdate"],
                    'Baddr'=>$row["Bcheck"],
                    'Bgrade'=>$row["Bgrade"],
                    'BLcheck'=>$row["BLcheck"]
                ));
            }



            if (!$android) {
                echo "<pre>";
                print_r($data);
                echo '</pre>';
            }else
            {
                header('Content-Type: application/json; charset=utf8');
                $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
                echo $json;
            }
        }


        mysqli_free_result($result);

    }
    else{
        echo "SQL문 처리중 에러 발생 : ";
        echo mysqli_error($link);
    }
}
else {
    echo "촬영할 건물을 입력하세요 ";
}


mysqli_close($link);

?>



<?php

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android){
?>

<html>
   <body>

      <form action="<?php $_PHP_SELF ?>" method="POST">
         건물명 : <input type = "text" name = "Bname" />
         <input type = "submit" />
      </form>

   </body>
</html>
<?php
}


?>
