//express 모듈 
var express = require('express');
var api = express.Router();

//몽고 DB 모듈  
const MongoClient = require('mongodb').MongoClient;
const assert = require('assert');

// Connection URL
const url = 'mongodb://localhost:27017';

// Database Name
const dbName = 'test';

api.get("/timezone",function(req,res){

  res.send("find");


  MongoClient.connect(url, function(err, client) {
    assert.equal(null, err);
    console.log("Connected successfully to server");
  
    const db = client.db(dbName);
  
    
      findDocuments(db, function() {
        client.close();
        
      });
  });

}); 
// Use connect method to connect to the server

module.exports = api; 


const findDocuments = function(db, callback) {
  // Get the documents collection
  const collection = db.collection('user');
  // Find some documents
  collection.find({}).toArray(function(err, docs) {
    assert.equal(err, null);
    console.log("Found the following records");
    console.log(docs)
    
    callback(docs);
  });
}