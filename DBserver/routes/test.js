module.exports = function(app){//함수로 만들어 객체 app을 전달받음
	var express = require('express');
	var router = express.Router();
	router.get('/r1', function(req, res){
        res.send('Hello /p1/r1');	
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

          MongoClient.connect(url, function(err, client) {
            assert.equal(null, err);
            console.log("Connected successfully to server");
          
            const db = client.db(dbName);
          
           
              findDocuments(db, function() {
                client.close();
            
            });
          });

	});

	return router;	//라우터를 리턴
};