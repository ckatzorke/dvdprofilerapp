function updateStatistic() {
	eval( $('select#statistic option:selected').val());
}

$(document).ready(function() {
	updateStatistic();
});
