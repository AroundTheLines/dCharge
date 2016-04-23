'use strict';
var express = require('express');
var bodyParser = require('body-parser');
var fs = require('fs');
var Firebase = require('firebase');
var _ = require('lodash');
var cors = require('cors');

var app = express();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var patientsRef = new Firebase("https://dcharge.firebaseio.com/").child('patients');

var port = process.env.PORT || 8080;

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
app.use(cors());

app.get('/', function(req, res){
	patientsRef.push({
		name: "John Smith",
		birthday: new Date().toJSON(),
		hospital: "some hospital",
		heart_rate: [{
			date: new Date().getTime(),
			rate: Math.random()*100
		}]
	});
	res.send("ayy");

});

app.post('/heart_rate', function(req, res){
	console.log('TIMESTAMP: ' + (new Date().getTime()));
	console.log(req.body);
	res.send('okay');
});

app.delete('/patient', function(req, res){
	patientsRef.child(req.body.patient_id).remove(function(error){
		if(error){
			console.log("error");
			res.send("error");
		}else{
			console.log("successfully removed");
			res.send("successfully removed");
		}
	});
});

io.on('connection', function (socket) {
  socket.emit('news', { hello: 'world' });
  socket.on('my other event', function (data) {
    console.log(data);
  });
});

app.listen(port, function(){
	console.log("magic happens on port " + port);
});