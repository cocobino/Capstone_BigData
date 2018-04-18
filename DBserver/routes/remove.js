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

  res.send("remove");
  
  MongoClient.connect(url, function(err, client) {
    assert.equal(null, err);
    console.log("Connected successfully to server");
  
    const db = client.db(dbName);
  
    
      removeDocument(db, function() {
        client.close();
     
      });
  });

}); 
// Use connect method to connect to the server

module.exports = api; 


//remove document 
const removeDocument = function(db, callback) {
    // Get the documents collection
    const collection = db.collection('user');
    // Delete document where a is 3
    collection.deleteOne({ a : 3 }, function(err, result) {
      assert.equal(err, null);
      assert.equal(1, result.result.n);
      console.log("Removed the document with the field a equal to 3");
      callback(result);
    });    
  }