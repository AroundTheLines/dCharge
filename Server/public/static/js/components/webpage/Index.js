import React from 'react';

import PatientPanel from './index/PatientPanel.js';

var Index = React.createClass({
	render(){
		return (
			<div id="page-wrapper">
				<div className="row">
					<div className="col-lg-12">
						<h1 className="page-header">Current Patients</h1>
					</div>
				</div>
				<div className="row">
					<PatientPanel />
					<PatientPanel />
					<PatientPanel />
					<PatientPanel />
				</div>
			</div>
		)
	}
});

module.exports = Index;