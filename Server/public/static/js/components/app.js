'use strict';
import React from 'react';
import io from 'socket.io-client';

import Router from 'react-router';
// var RouteHandler = require('react-router').RouteHandler;
import Navbars from './navbar/Navbars.js';
import Index from './webpage/Index.js';
import Graph from './webpage/graphs/graph.js';


var App = React.createClass({

	getInitialState(){
		return{
			status: 'disconnected'
		}
	},

	componentWillMount(){
		console.log("connecting");
		var sock = this.socket;
		sock = io('http://localhost:3000');
		sock.on('connect', this.connect);
		sock.on('disconnect', this.disconnect);

		sock.on('global test', function(data){
			console.log("Global test successful " + data);
		});
	},

	connect(){
		console.log("connected");
		this.setState({status: 'connected'});
		console.log("status is: " + this.state.status);
		
	},

	disconnect(){
		this.setState({status: 'disconnected'});
		console.log("disconnected");
	},

	render(){
		return(
			<div>
				<Navbars />
				<Index />
				<Graph />
			</div>
		)
	}
});

module.exports = App;