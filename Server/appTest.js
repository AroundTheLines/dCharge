var express = require('express');
var bodyParser = require('body-parser');
var Firebase = require('firebase');
var Promise = require('promise');
var renderToString = require('react-dom/server').renderToString;
var match = require('react-router').match;
var RouterContext = require('react-router').RouterContext;
var cors = require('cors');
var app = express();

var patientsRef = new Firebase("https://dcharge.firebaseio.com/").child('patients');

var connections = [];

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
app.use(cors());
app.use(express.static('./public/static'));
app.use(express.static('./bower_components'));

var currentPatient = patientsRef.push({
			name: "John Smith",
			patient_type: "chemotherapy",
			heart_rate: []
		});

var server = app.listen(3000, function(){
	console.log("app is now listening on port " + 3000);
});

var heartRates = [];
var hourlyAvg = 0;
var numpoints = 1;
var io = require('socket.io').listen(server);

io.sockets.on('connection', function(socket){
	
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
		
		hourlyAvg = ((numpoints - 1)*hourlyAvg + data)/numpoints;
		numpoints++;
		if(date % 3600000 == 0){
			socket.emit('hourly_avg_heart_rate', hourlyAvg);
			numpoints = 1;
			hourlyAvg = 0;
		}

		heartRates.push(data);
		var isAProblem = isProblem(heartRates);
		console.log("response from problem is: %s", isAProblem);
		if(isAProblem !== ""){
			socket.emit('heart_rate_problem', isAProblem);
		}
		// socket.emit('heart_rate_update', data);
	});

	connections.push(socket);
	console.log("socket id: %s", socket.id);
	console.log("connected: %s sockets remaining", connections.length);

	socket.broadcast.emit('heart_rate_update', {
		date: new Date().getTime(),
		rate: Math.floor((Math.random() * 100))
	});
});

// Params: Array of numbers (heart rates)
function isProblem(heartRate){
		var isFirst = true
		var mean = 0;
		var stdDev = 0;
		var numStored = 7;
		var heartRateValues = heartRate;
		// heartRateValues = [10,220,29,120,42,94,20,76];
		for(i = heartRateValues.length - 1; i >= heartRateValues.length - numStored; i--){
			mean += heartRateValues[i];
		}
		mean /= heartRateValues.length;
		for(i = heartRateValues.length - 1; i >= heartRateValues.length - numStored; i--){
			stdDev += Math.pow(heartRateValues[i] - mean,2);
		}
		stdDev = Math.sqrt(stdDev/numStored);
		if(heartRateValues[heartRateValues.length - 1] == 0 && isFirst == true){
			isFirst = false;
			return"";
		}
		else if(stdDev > 10){
			return "Heart beat has high varience";
		}
		else if(heartRateValues[heartRateValues.length - 1] < 50){
			return "is either a sleep or has bradycardia";
		}
		else if(heartRateValues[heartRateValues.length - 1] >= 100){
			return "tachycardia is occuring";
		}
		else{
			return "";
		}
	}
