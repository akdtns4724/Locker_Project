$(document).ready(function() {
});

function userlogin() {
	console.log("login");
	console.log($("input[name*='id']").val());
	console.log($("input[name*='pw']").val());
	$.ajax({
		type : "POST",
		url : "/LockerProject/login",
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		data : {
			"id" : $("input[name*='id']").val(),
			"pw" : $("input[name*='pw']").val()
		},
		success : function(e) {
			console.log(e);
		},
		error : function(e) {
			console.log(e)
		}
	});
}