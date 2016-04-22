'use strict';
var express = require('express');
var bodyParser = require('body-parser');
var fs = require('fs');
var _ = require('lodash');
var cors = require('cors');

var app = express();
var server = require('http').Server(app);
var io = require('socket.io')(server);

var port = process.env.PORT || 8080;

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
app.use(cors());

app.get('/', function(req, res){
	res.send("ayy");
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