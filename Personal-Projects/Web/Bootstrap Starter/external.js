$(document).ready(function() {
	$('#highlight').on('click', function() {
		var term = $('#search').val();
		$('body').removeHighlight().highlight(term);
	});
});
