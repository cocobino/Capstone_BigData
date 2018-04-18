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

  res.send("insert");
  
  MongoClient.connect(url, function(err, client) {
    assert.equal(null, err);
    console.log("Connected successfully to server");
  
    const db = client.db(dbName);
  
    
      insertDocuments(db, function() {
        client.close();
     
      });
  });

}); 
// Use connect method to connect to the server

module.exports = api; 



//레코드 삽입  
const insertDocuments = function(db, callback) {
    // Get the documents collection
    const collection = db.collection('user');
    // Insert some documents
    collection.insertMany([
      {a : 1}, {a : 2}, {a : 3}
    ], function(err, result) {
      assert.equal(err, null);
      assert.equal(3, result.result.n);
      assert.equal(3, result.ops.length);
      console.log("Inserted 3 documents into the collection");
      callback(result);
    });
  }
  