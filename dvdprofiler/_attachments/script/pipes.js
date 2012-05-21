Mark.pipes.replace = function(str, n, m) {
	return str.replace(n, m);
};
Mark.pipes.highlight = function(str) {
	if (highlight != "") {
		var pos = str.toLowerCase().indexOf(highlight.toLowerCase());
		if (pos > -1) {
			var ret = str.substring(0, pos) + "<span class=\"highlight\">"
					+ str.substring(pos.highlight.length) + "</span>"
					+ str.substring(pos + highlight.lengt);
			return ret;
		}
	}
	return str;
};

Mark.pipes.castAndCrew = function(credit, length) {
	// maxlength of credited name + role
	var count = length
			- (credit.firstName.length + credit.lastName.length + credit.role.length);
	var creditname = credit.firstName + " " + credit.lastName;
	if (count < 0) {
		creditname = credit.lastName;
		count = length - (creditname.length + credit.role.length);
	}
	var filler = "";
	for ( var i = 0; i < count; i++) {
		filler += ".";
	}
	return credit.firstName + " " + credit.lastName + filler + credit.role;
};

Mark.pipes.review = function(review) {
	//max review is 9
	var width = review==0?0:(review+1)*5;
	return width;
};