'use strict';
var React = require('react');
var io = require('socket.io-client');

var Header = require('./Header.js');

var App = React.createClass({

	getInitialState(){
		return{
			status: 'disconnected'
		}
	},

	componentWillMount(){
		var sock = this.socket;
		sock = io('http://63168325.ngrok.io');
		sock.on('connect', this.connect);
		sock.on('disconnect', this.disconnect);
		sock.on('heart_rate_test', function(data){
			console.log(data);
			sock.emit('heart_rate', "helloooooo")
		});
	},

	connect(){
		this.setState({status: 'connected'});
	},

	disconnect(){
		this.setState({status: 'disconnected'});
	},

	render(){
		return(
			<div>
				<Header title="hello" status={this.state.status}/>
			</div>
		)
	}
});

module.exports = App;