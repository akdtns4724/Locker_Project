$(document).ready(function() {
	$('#modifyForm').submit(function(e) {
		e.preventDefault();
		$.ajax({
			url : '/LockerProject/member/modify',
			type : 'get',
			data : $('#modifyForm').serialize(),
			success : function() {
				alert("회원 정보가 수정되었습니다.");
				$('#userInfo').modal('toggle');
			}
		});
	});
});

function makeUserInfoTable(idx, id, pw, name, phone, email) {
	console.log("makeUserInfoTable");

	$('#userInfo_table')
			.append(
					'<tr><td style="width:55px;"> ID : </td><td><input readonly name="user_id" type="text" value='
							+ id + ' style="width:200px"></td></tr>')
	$('#userInfo_table')
			.append(
					'<tr><td style="width:55px;"> PW : </td><td><input name="user_pw" type="password" value='
							+ pw + ' style="width:200px"></td></tr>')
	$('#userInfo_table')
			.append(
					'<tr><td style="width:55px;"> name : </td><td><input name="user_name" type="text" value='
							+ name + ' style="width:200px"></td></tr>')
	$('#userInfo_table')
			.append(
					'<tr><td style="width:55px;"> phone : </td><td><input name="user_phone" type="text" value='
							+ phone + ' style="width:200px"></td></tr>')
	$('#userInfo_table')
			.append(
					'<tr><td style="width:55px;"> email : </td><td><input name="user_email" type="text" value='
							+ email + ' style="width:200px"></td></tr>')
}

function logout() {
	console.log("logout");
	$.ajax({
		type : "GET",
		url : "/LockerProject/member/logout",
		success : function() {
			alert("로그아웃 되었습니다.");
			window.location = "/LockerProject/login.jsp";
		},
		error : function(e) {
			console.log(e);
		}
	});
}

function infoLocker(user_idx) {
	console.log("infoLocker");
	$.ajax({
		type : "POST",
		url : "/LockerProject/locker/getInfo",
		data : {
			"user_idx" : user_idx
		},
		datatype : "json",
		success : function(data) {
			if(data == "noLocker"){
				alert("신청한 사물함이 없습니다.");
			} else {
				alert(data + "번 사물함을 보유중입니다.")
			}
		},
		error : function(e) {
			console.log(e);
		}
	});
}

function withdrawUser(user_idx) {
	console.log("withdrawUser");
	if (confirm("정말 회원 탈퇴 하시겠습니까??") == true) {
		$.ajax({
			type : "POST",
			url : "/LockerProject/member/withdraw",
			data : {
				"user_idx" : user_idx
			},
			datatype : "json",
			success : function(data) {
				alert("성공적으로 탈퇴 되었습니다.");
				window.location = "/LockerProject/login.jsp";
			},
			error : function(e) {
				console.log(e);
			}
		});
	}
}

function withdrawLocker(user_idx) {
	console.log("withdrawLocker");
	if (confirm("정말 사물함을 철회 하시겠습니까??") == true) {
		$.ajax({
			type : "POST",
			url : "/LockerProject/locker/withdraw",
			data : {
				"user_idx" : user_idx
			},
			datatype : "json",
			success : function(data) {
				if(data == "Success"){
					alert("사물함 철회가 완료되었습니다.");
				} else if(data == "Fail"){
					alert("보유중인 사물함이 없습니다.");
				}
			},
			error : function(e) {
				console.log(e);
			}
		});
	}
}