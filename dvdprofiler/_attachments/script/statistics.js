(function($) {
	var $stats = new Array();
	var $select;
	var methods = {
		init : function() {
			console.log('init');
			this
					.each(function() {
						var $this = $(this);
						$this
								.append('<select id="statistic"><option value="init">Please choose...</option></select>');
						$select = $('select#statistic');
						for ( var idx in $stats) {
							$select.append('<option value="' + idx + '">' + idx
									+ '</option>');
						}
						$select.change(function() {
							$("select#statistic option:selected")
									.each(
											function() {
												$.fn.statistics('show', $(this)
														.text());
											});
						});
					});
		},
		show : function(statistic) {
			console.log('show(' + statistic + ')');
			$('#statistics').hide();
			showLoading();
			$stats[statistic].call();
			hideLoading();
			$('#statistics').fadeIn();
		},
		register : function(statistic, callback) {
			console.log('register(' + statistic + ')');
			$stats[statistic] = callback;
		}
	};

	$.fn.statistics = function(method) {

		// Method calling logic
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(
					arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$
					.error('Method ' + method
							+ ' does not exist on jQuery.statistics');
		}

	};

})(jQuery);

$(document).ready(function() {
	$('#statisticchooser').statistics();
});
