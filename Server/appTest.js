var express = require('express');
var app = express();

var connections = [];

app.use(express.static('./public'));

var server = app.listen(3000, function(){
	console.log("app is now listening on port " + 3000);
});

var io = require('socket.io').listen(server);

io.sockets.on('connection', function(socket){
	socket.once('disconnect', function(){
		connections.splice(connections.indexOf(socket), 1);
		socket.disconnect();
		console.log("disconnected: %s sockets remaining", connections.length);
	});

	socket.emit('heart_rate_test', {
		qqq: "aaa"
	});

	socket.on('heart_rate', function(data){
		console.log("heart rate data: " + data);
	});

	connections.push(socket);
	console.log("socket id: %s", socket.id);
	console.log("connected: %s sockets remaining", connections.length);
});