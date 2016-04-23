import React from 'react';

var NavPatient = React.createClass({
	// propTypes: {
	// 	patientName: React.PropTypes.string.patientName
	// },

	getDefaultProps(){
		return {
			detailsLink: "#",
			patientName: "John Doe"
		}
	},

	render(){
		<li>
			<a href={this.props.detailsLink}>{this.props.patientName}</a>
		</li>
	}
});

module.exports = NavPatient;