<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Aapdah Home</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.0/themes/base/jquery-ui.css" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />

<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script type="text/javascript" src="RetrieveSQLData.js"></script>
<script type="text/javascript">
function popupUploadForm(){
    var newWindow = window.open('http://localhost:8080/AapdahWeb/ReportCrime.jsp', 'name', 'height=500,width=600');

}
</script>
</head>
<body style="background-color: #424242;">
	<div id="header_cc"
		style="margin: 0 auto; padding: 10px 0 40px 0; clear: both; font-family: Verdana, Helvetica, sans-serif; max-width: 100% 16px; height: 75px; background: #000000; width: 1250px">
		<table cellpadding="0" cellspacing="0">
			<tbody>
				<tr>
					<td>
						<table id="masterhead" cellpadding="0" border="0" align="center">
							<tbody>
								<tr>

									<td width="3100px" valign="middle" align="left">
										<h2
											style="font-family: Verdana, Helvetica, sans-serif; color: #A4A4A4; margin: 10px; padding: 10px 0 0 0; text-transform: uppercase; color: white; font-size: 35px; font-weight: bold; line-height: 0.3cm; letter-spacing: 0.3cm;"
											align="left">
											Aapdah <br>
										</h2>
										<p
											style="font-family: Verdana, Helvetica, sans-serif; padding: 10px 0 0 0; font-size: 20px; font-style: italic; line-height: 0.3cm; letter-spacing: 0.1cm; color: #A4A4A4"
											align="left">The Disaster Data Handling Application</p>
									</td>
									<td><img
										src="http://www.clker.com/cliparts/o/q/H/f/A/e/caution-logo.svg"
										align="right" width=100 height=100></td>
								</tr>
							</tbody>
						</table>
					</td>

				</tr>
			</tbody>
		</table>
	</div>
	<br>
	<br>
	<br>
	<div id="selection">
		<table cellpadding="0" cellspacing="25">
			<tbody>
				<tr>
					<td><img
						src="http://stopcrime365.com/uploaded_images/stop-crime-clipart-720270.jpg"
						width=100 height=100 align="left"></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>
						<form name="input">
							<p
								style="font-family: Verdana, Helvetica, sans-serif; font-size: 20px; color: white">
								Select Crime Type: <input type="text" name="crimeType"
									id="crimeType" align="middle" /> <input type="submit"
									value="Submit" id="submitButton" name="submitButton"
									align="middle" />
							</p>
						</form>
					</td>
				</tr>
				<tr>
					<td><img
						src="http://www.mikesowden.org/feveredmutterings/wp-content/uploads/2009/08/RobberyNotAllowed.jpg"
						width=100 height=100 align="left"></td>

					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><a onclick="popupUploadForm();"
						style="color: white; font-family: Verdana, Helvetica, sans-serif; font-size: 20px;">Report</a>
				</tr>
				<tr>
					<td><img
						src="http://www.openmarket.org/wp-content/uploads/2012/08/ban-fists-update-75.png"
						width=100 height=100 align="left"></td>
				</tr>
			</tbody>
		</table>
	</div>

	<br>
	<br>

	<div id="displayTable" align="center">
		<table id=retrieveSQLData align="center" cellpadding="1"
			cellspacing="1" border="1" width="1200">
			<thead>
				<tr align="center">
					<th>Address</th>
					<th>Agency</th>
					<th>Date</th>
					<th>Time</th>
				</tr>

			</thead>

			<tbody id="rows" align="center">
			</tbody>
		</table>
	</div>


</body>
</html>