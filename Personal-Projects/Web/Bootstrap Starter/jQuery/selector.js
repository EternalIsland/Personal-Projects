/**
 * @author Matthew Hynes
 */

$(document).ready(function() {
	$("#load").on("click", function() {
		$.ajax({
			url : "page.php",
			statusCode : {
				404 : function() {
					$("#content").html("Page not found.");
				},
				200 : function() {
					$("#content").html("She works buddy!");
				}
			},
			success : function(data) {
				$("#content").html(data);
			}
		});
	});
});
