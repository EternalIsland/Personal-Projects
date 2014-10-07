(function($) {
	$.fn.targetBlank = function() {
		var targetArray = ['_blank', '_self', '_parent', '_top'];
		var target = $.trim($(this).attr('target'));

		if (target === undefined || target === '' || !($.inArray(target, targetArray))) {
			$(this).attr('target', '_blank');
		}
	};
})(jQuery);

