import React from 'react';
import Highcharts from 'highcharts';
const ReactHighcharts = require('react-highcharts'); // Expects that Highcharts was loaded in the code.

var Graph = React.createClass({
	render(){
		var config = {
			xAxis: {
					categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
				},
				series: [{
					data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 295.6, 454.4,321.4,531.5,961.5,741.2]
			}]
		};
		return(
				React.createElement(ReactHighcharts, { config: config })
		);
	}
});

module.exports = Graph;