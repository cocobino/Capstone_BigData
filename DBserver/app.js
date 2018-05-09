var express = require("express"); 
var app = express();


var apiVersion1 = require("./routes/find.js"); 
//var apiVersion2 = require("./routes/insert.js"); 
//var apiVersion3 = require("./routes/remove.js"); 
//var apiVersion4 = require("./routes/update.js"); 


app.use("/v1",apiVersion1);
//app.use("/v2",apiVersion2);
//app.use("/v3",apiVersion3);
//app.use("/v4",apiVersion4);

app.listen(7088,function(){

  console.log("App started DBserver API 7088 port"); 
});