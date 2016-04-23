var express = require('express');
var bodyParser = require('body-parser');
var Firebase = require('firebase');
var cors = require('cors');
var app = express();

var patientsRef = new Firebase("https://dcharge.firebaseio.com/").child('patients');

var connections = [];

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
app.use(cors());
app.use(express.static('./public/static'));
app.use(express.static('./bower_components'));



var server = app.listen(3000, function(){
	console.log("app is now listening on port " + 3000);
});

var io = require('socket.io').listen(server);

io.sockets.on('connection', function(socket){
	var currentPatient = patientsRef.push({
			name: "John Smith",
			patient_type: "chemotherapy",
			heart_rate: []
		});
	socket.once('disconnect', function(){
		connections.splice(connections.indexOf(socket), 1);
		socket.disconnect();
		console.log("disconnected: %s sockets remaining", connections.length);
	});

	socket.broadcast.emit('global test', {
		mmm: "asd"
	});

	socket.on('heart_rate', function(data){
		console.log("heart rate data: " + data);
		console.log(JSON.stringify(data));
		currentPatient.child('heart_rate').push({
			date: new Date().getTime(),
			rate: data
		});
	});

	connections.push(socket);
	console.log("socket id: %s", socket.id);
	console.log("connected: %s sockets remaining", connections.length);

	socket.emit('heart_rate_test', {
		qqq: "aaa"
	});
});