// Apache 2.0 J Chris Anderson 2011
var path;
var design;
var db;
$(function() {   
    // friendly helper http://tinyurl.com/6aow6yn
    $.fn.serializeObject = function() {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    path = unescape(document.location.pathname).split('/'),
        design = path[3],
        db = $.couch.db(path[1]);
    
    
    //?
    var changesRunning = false;
    function setupChanges(since) {
        if (!changesRunning) {
            var changeHandler = db.changes(since);
            changesRunning = true;
            changeHandler.onChange(drawItems);
        }
    }
 });

function doHelpMessage(helpmessageNode){
	$.blockUI(
    		{ 
    			css: { 
		            border: 'none', 
		            padding: '15px', 
		            backgroundColor: '#000', 
		            '-webkit-border-radius': '10px', 
		            '-moz-border-radius': '10px', 
		            opacity: .9, 
		            color: '#fff',
		            'text-align': 'left'
    			},
    			message : helpmessageNode
    		}); 
}

function showLoading(){
	$.blockUI({
		message	:	"Loading items...",
		css 	: {
			border : 'none',
			padding : '15px',
			backgroundColor : '#000',
			'-webkit-border-radius' : '10px',
			'-moz-border-radius' : '10px',
			opacity : .5,
			color : '#fff'
		}
	});
}

function hideLoading(){
	$.unblockUI();
}
