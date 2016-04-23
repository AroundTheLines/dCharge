import React from 'react';

import Topbar from './Topbar.js';
import Sidebar from './Sidebar.js';

var Navbars = React.createClass({
	render(){
		return(
			<nav class="navbar navbar-default navbar-static-top" role="navigation" id="navbars">
				<Topbar />
				<Sidebar />
			</nav>
		)
	}
});

module.exports = Navbars;