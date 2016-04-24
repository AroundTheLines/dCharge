import React from 'react'

import NavPatient from './NavPatient.js';

var Sidebar = React.createClass({
	render(){
		return(
			<div className="navbar-default sidebar" role="navigation">
				<div className="sidebar-nav navbar-collapse">
					<ul className="nav" id="side-menu">
						<li>
							<a href="index.html"><i className="fa fa-dashboard fa-fw"></i> Dashboard</a>
						</li>
						<li>
							<a href="#"><i className="fa fa-users fa-fw"></i> Patients<span className="fa fa arrow"></span></a>
							<ul className="nav nav-second-level">
								<li>
									<a href="#">Second Level Item</a>
								</li>
								<li>
									<a href="#">Second Level Item</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		)
	}
});

module.exports = Sidebar;