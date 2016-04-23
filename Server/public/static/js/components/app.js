'use strict';
import React from 'react';
import io from 'socket.io-client';

import Header from './Header.js';
import Index from './webpage/Index.js';
import Navbars from './navbar/Navbars.js';


var App = React.createClass({

	getInitialState(){
		return{
			status: 'disconnected'
		}
	},

	componentWillMount(){
		var sock = this.socket;
		sock = io('http://localhost:3000');
		sock.on('connect', this.connect);

		sock.on('disconnect', this.disconnect);
		sock.on('heart_rate_test', function(data){
			console.log(data);
			sock.emit('heart_rate', {
				helloooooo: "ooooo"
			})
		});

		sock.on('global test', function(data){
			console.log("Global test successful " + data);
		});
	},

	connect(){
		this.setState({status: 'connected'});
		console.log("connected");
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
			</div>
		)
	}
});

module.exports = App;