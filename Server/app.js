'use strict';
var express = require('express');
var bodyParser = require('body-parser');
var fs = require('fs');
var Firebase = require('firebase');
var _ = require('lodash');
var cors = require('cors');

var app = express();
var server = require('http').createServer(app);
var io = require('socket.io')(server);
var patientsRef = new Firebase("https://dcharge.firebaseio.com/").child('patients');

var port = process.env.PORT || 8080;

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
app.use(cors());



// app.post('/heart_rate', function(req, res){
// 	console.log('TIMESTAMP: ' + (new Date().getTime()));
// 	console.log(req.body);
// 	res.send('okay');
// });



server.listen(port, function(){
	console.log("magic happens on port " + port);
});

io.on('connection', function (socket) {
	console.log("Hello socket " + socket.id);
	socket.broadcast.emit('new message', {
			username: "asdsdsadsad",
			message: "blargh"
	});

	socket.on('new message', function (data){
		console.log("the data received is: " + data);
		socket.broadcast.emit('new message', {
			username: "asdsdsadsad",
			message: "blah"
		});
	});

	socket.on('login', function(data){
		console.log(data.username);
		console.log(data.password);
		if(data.username === "tester" && data.password === "password"){
			socket.broadcast.emit('Logged In', {
				loggedIn: true
			})
		}
	})
});


app.use(express.static('./public/views'));
// app.get('/', function(req, res){
// 	patientsRef.push({
// 		name: "John Smith",
// 		birthday: new Date().toJSON(),
// 		hospital: "some hospital",
// 		heart_rate: [{
// 			date: new Date().getTime(),
// 			rate: Math.random()*100
// 		}],
//		treatment_type: "chemotherapy"
// 	});
// 	console.log('lol');
// 	res.send("ayy");
// });

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



// // Setup basic express server
// var express = require('express');
// var cors = require('cors');
// var app = express();

// app.use(cors());
// var server = require('http').createServer(app);
// var io = require('socket.io')(server);
// var port = process.env.PORT || 8080;

// server.listen(port, function () {
//   console.log('Server listening at port %d', port);
// });

// // Routing
// app.use(express.static(__dirname + '/public'));

// // Chatroom

// var numUsers = 0;

// io.on('connection', function (socket) {
// 	console.log("hi " + socket.id);
// 	//console.log(socket);
//   var addedUser = false;

//   // when the client emits 'new message', this listens and executes
//   socket.on('new message', function (data) {
//     // we tell the client to execute 'new message'
    
//     socket.broadcast.emit('new message', {
//       username: "asdsdsadsad",
//       message: data
//     });
//     console.log("data is: " + data);
//   });

//   // when the client emits 'add user', this listens and executes
//   socket.on('add user', function (username) {
//     if (addedUser) return;

//     // we store the username in the socket session for this client
//     socket.username = username;
//     ++numUsers;
//     addedUser = true;
//     socket.emit('login', {
//       numUsers: numUsers
//     });
//     // echo globally (all clients) that a person has connected
//     socket.broadcast.emit('user joined', {
//       username: socket.username,
//       numUsers: numUsers
//     });
//   });

//   // when the client emits 'typing', we broadcast it to others
//   socket.on('typing', function () {
//     socket.broadcast.emit('typing', {
//       username: socket.username
//     });
//   });

//   // when the client emits 'stop typing', we broadcast it to others
//   socket.on('stop typing', function () {
//     socket.broadcast.emit('stop typing', {
//       username: socket.username
//     });
//   });

//   // when the user disconnects.. perform this
//   socket.on('disconnect', function () {
//     if (addedUser) {
//       --numUsers;

//       // echo globally that this client has left
//       socket.broadcast.emit('user left', {
//         username: socket.username,
//         numUsers: numUsers
//       });
//     }
//   });
// });