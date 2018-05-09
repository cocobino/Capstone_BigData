var express = require("express"); 

var app = express();
var fs = require('fs'); 


app.get('/',function(req,res,next){
//res.send("sorry can't supported!!");

//res.sendStatus(test2); 
    next(); 

    
},function(req,res){

    fs.readFile('./public/test.txt','utf-8',function(err,data){
        console.log(data); 
        var test1 = "문자열"; 

        test1 = data; 

        res.json(test1); 

    });

});



app.listen(7088,function(){

    console.log("started 7088 port"); 
    
});
/*
var testset = function(callback){
    fs.readFile('./public/test.txt','utf-8',function(err,data){
    //console.log(data); 
    callback();
    });
}

*/