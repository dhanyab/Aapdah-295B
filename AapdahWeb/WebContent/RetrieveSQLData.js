/**
 * 
 */

$(document).ready(function(){
	$('#displayTable').hide();

	var crimeType = $('#crimeType').val();
	$('#submitButton').click(function(){
		$.ajax({
			type: "GET",
			contentType: "application/json",
			url:"RetrieveSqlDataServlet",
			data: "crimeType=" +crimeType,
			dataType: "json",
			success: function(jsonResponse){
				$("#retrieveSQLData tbody").find("tr:gt(0)").remove();
				var table = $('#retrieveSQLData');

				$.each(jsonResponse, function(key,value){
					var rowNew = $('<tr align="center" class="tableRows">' + 
							'<td id="address" name="address"> </td>'+	
							'<td id="agency" name="agency"> </td>'+
							'<td id="date" name="date"> </td>'+
							'<td id="time" name="time"> </td>'+
					'</tr>');

					rowNew.children().eq(0).text(value['address']);
					rowNew.children().eq(1).text(value['agency']);
					rowNew.children().eq(2).text(value['date']);
					rowNew.children().eq(3).text(value['time']);

					rowNew.appendTo(table);
				});

				$('#displayTable').show();			
			},
			error:function(){
				alert("No data for the selected crime type");	
			}
		});		
	});
	$(function(){
		$("#crimeType").autocomplete({
			source: function(request, response){
				$.ajax({
					type: "GET",
					url: "AutoCompleteCrimeTypeServlet",
					dataType: "json",
					data: {
						"term": ""
					},
					success: function(data) {
						response(data);
					}
				});
			} 
		});
	});
});