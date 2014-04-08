<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Report Crime</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.0/themes/base/jquery-ui.css" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />

<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script type="text/javascript" src="RetrieveSQLData.js"></script>
</head>
<br>
<br>
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
											style="font-family: Verdana, Helvetica, sans-serif; color: #A4A4A4; margin: 10px; padding: 10px 0 0 0; text-transform: uppercase; color: white; font-size: 15px; font-weight: bold; line-height: 0.1cm; letter-spacing: 0.3cm;"
											align="left">
											Aapdah-Report a Crime<br>
										</h2>
										
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
	<br><br>
	<div id="reportId" align="justify">
		<table>
			<tbody>
				<tr>
					<td><label for="name"><font size="3" color="White">Crime
								Type:</font></label></td>
					<td><input type="text" id="crimetypereport"
						name="crimetypereport"></td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td><label for="address"><font size="3" color="White">Address:</font></label></td>
					<td><textarea rows="3" cols="3"></textarea></td>

				</tr>
				<tr>
					<td><label for="name"><font size="3" color="White">Date:</font></label>
					</td>
					<td><input type="text" id="crimetypereport"
						name="crimetypereport"></td>
				</tr>
				<tr>
					<td><label for="name"><font size="3" color="White">
								Time:</font></label></td>
					<td><input type="text" id="crimetypereport"
						name="crimetypereport"></td>
				</tr>
				<tr>
					<td><input type="Submit" name="submitreport" align="middle" style ="height:75px; width:100px" /></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>