import React from 'react';

var PatientPanel = React.createClass({
	render(){
		return(
			<div className="col-lg-3 col-md-6">
				<div className="panel panel-primary">
					<div className="panel-heading">
						<div className="row">
							<div className="col-xs-3">
								<img src="imgs/camel1.jpg" alt="camelface" className="img-circle portrait"/>
							</div>
							<div className="col-xs-9 text-right">
								<div className="huge">John Smith</div>
								<div>1337 University Ave L8R 1O1</div>
								<div>Last Checkin : 30m</div>
							</div>
						</div>
					</div>
					<a href="heart-rate.html">
						<div className="panel-footer">
							<span className="pull-left">Patient Details</span>
							<span className="pull-right"><i className="fa fa-arrow-circle-right"></i></span>
							<div className="clearfix"></div>
						</div>
					</a>
					<a href="#myPopup">
						<div className="panel-footer">
							<span className="pull-left">Contact Now</span>
							<span className="pull-right"><i className="fa fa-arrow-circle-right"></i></span>
							<div className="clearfix"></div>
						</div>
					</a>
				</div>
			</div>
		)
	}
});

module.exports = PatientPanel;