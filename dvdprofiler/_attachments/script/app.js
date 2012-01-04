// Apache 2.0 J Chris Anderson 2011
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

    var path = unescape(document.location.pathname).split('/'),
        design = path[3],
        db = $.couch.db(path[1]);
    function createOverview() {
        db.view(design + "/media", {
            group : "true",
            success : function(data) {
            	console.log(data);
                var overview = $.mustache($("#mustache-overview").html(), data);
                $("#overview").html(overview);
                $('#carousel-overview').jcarousel({
                    auto: 2,
                    wrap: 'last',
                    visible: 1,
                    scroll: 1,
                    animation: "slow",
                    buttonNextHTML: null,
                    buttonPrevHTML: null
                });
            }
        });
    };
    createOverview();
    
    //?
    var changesRunning = false;
    function setupChanges(since) {
        if (!changesRunning) {
            var changeHandler = db.changes(since);
            changesRunning = true;
            changeHandler.onChange(drawItems);
        }
    }
    $("#account").couchLogin({
    	//TODO
        loggedIn : function(r) {
            $("#profile").couchProfile(r, {
                profileReady : function(profile) {
                    $("#create-message").submit(function(e){
                        e.preventDefault();
                        var form = this, doc = $(form).serializeObject();
                        doc.created_at = new Date();
                        doc.profile = profile;
                        db.saveDoc(doc, {success : function() {form.reset();}});
                        return false;
                    }).find("input").focus();
                }
            });
        },
        loggedOut : function() {
            $("#profile").html('<p>Please log in to see your profile.</p>');
        }
    });
 });