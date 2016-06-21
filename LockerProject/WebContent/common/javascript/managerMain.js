$(document).ready(function() {
	$('#user_btn').click(function() {
		console.log('user list');
		$('#navbar').find('li').removeClass();
		$('#user_btn').parent().addClass('active');
		$('#mainbody').find('div').addClass('nonselectedBody');
		$('#mainbody').find('.user').addClass('selectedBody');
		$('#mainbody').find('.user').removeClass('nonselectedBody');
	});
	$('#locker_btn').click(function() {
		console.log('locker list');
		$('#navbar').find('li').removeClass();
		$('#locker_btn').parent().addClass('active');
		$('#mainbody').find('div').addClass('nonselectedBody');
		$('#mainbody').find('.locker').addClass('selectedBody');
		$('#mainbody').find('.locker').removeClass('nonselectedBody');
	});
	$('#log_btn').click(function() {
		console.log('log list');
		$('#navbar').find('li').removeClass();
		$('#log_btn').parent().addClass('active');
		$('#mainbody').find('div').addClass('nonselectedBody');
		$('#mainbody').find('.log').addClass('selectedBody');
		$('#mainbody').find('.log').removeClass('nonselectedBody');
	});
});

function makeUserListTable(userInfo) {
	var idx, id, pw, name, phone, email;
	idx = userInfo.split(' ')[0];
	id = userInfo.split(' ')[1];
	pw = userInfo.split(' ')[2];
	name = userInfo.split(' ')[3];
	phone = userInfo.split(' ')[4];
	email = userInfo.split(' ')[5];

	var infoString, idxString, idString, pwString, nameString, phoneString, emailString;
	idxString = '<td>' + idx + '</td>';
	idString = '<td>' + id + '</td>';
	pwString = '<td>' + pw + '</td>';
	nameString = '<td>' + name + '</td>';
	phoneString = '<td>' + phone + '</td>';
	emailString = '<td>' + email + '</td>';
	infoString = idxString + idString + pwString + nameString + phoneString
			+ emailString;
	var modifyBtnString = '<td><button type="button" class="btn-modify btn btn-info btn-lg" onclick="makeUserMofifyModal(this)" data-toggle="modal" data-target="#myModal">Modify</button></td>';
	var deleteBtnString = '<td><a class="deletebtn btn btn-lg btn-danger" role="button" onclick="deleteUser(this)">Delete</a></td>';
	$('#userlist_table').append(
			'<tr>' + infoString + modifyBtnString + deleteBtnString + '</tr>')
}

function makeUserMofifyModal(a) {
	$('#modifyModal_table').children().remove();
	var id = $($($(a).parents('tr')).children()[1]).text();
	var pw = $($($(a).parents('tr')).children()[2]).text();
	var name = $($($(a).parents('tr')).children()[3]).text();
	var phone = $($($(a).parents('tr')).children()[4]).text();
	var email = $($($(a).parents('tr')).children()[5]).text();
	$('#modifyModal_table')
			.append(
					'<tr><td style="width:55px;"> ID : </td><td><input readonly name="user_id" type="text" value='
							+ id + ' style="width:200px"></td></tr>')
	$('#modifyModal_table')
			.append(
					'<tr><td style="width:55px;"> PW : </td><td><input name="user_pw" type="password" value='
							+ pw + ' style="width:200px"></td></tr>')
	$('#modifyModal_table')
			.append(
					'<tr><td style="width:55px;"> name : </td><td><input name="user_name" type="text" value='
							+ name + ' style="width:200px"></td></tr>')
	$('#modifyModal_table')
			.append(
					'<tr><td style="width:55px;"> phone : </td><td><input name="user_phone" type="text" value='
							+ phone + ' style="width:200px"></td></tr>')
	$('#modifyModal_table')
			.append(
					'<tr><td style="width:55px;"> email : </td><td><input name="user_email" type="text" value='
							+ email + ' style="width:200px"></td></tr>')
}

function deleteLocker(lockerInfo) {
	console.log('deleteLocker');
	if (confirm("정말 삭제하시겠습니까??") == true) {
		var user_idx = $($(lockerInfo).parents('tr').find('td')[1]).text();
		console.log(user_idx);
		$.ajax({
			type:"POST",
			url : "/LockerProject/locker/delete",
			data : {
				"user_idx" : user_idx
			},
			datatype : "json",
			success : function(data) {
				if(data == "Abnormally"){
					alert("알 수 없는 에러가 발생하였습니다.\n관리자에게 문의하세요.");
				} else if(data == "Success"){
					alert("해당 사물함이 정상적으로 삭제되었습니다.");
					location.reload(true);
				}
			},
			error : function(e) {
				alert("에러 발생");
			}
		});
	}
}
function deleteUser(userInfo) {
	console.log('deleteUser');
	if (confirm("정말 삭제하시겠습니까??") == true) {
		var user_idx = $($(userInfo).parents('tr').find('td')[0]).text();
		$.ajax({
			type : "POST",
			url : "/LockerProject/member/delete",
			data : {
				"user_idx" : user_idx
			},
			datatype : "json",
			success : function() {
				alert("삭제 되었습니다.");
				location.reload(true);
			},
			error : function(e) {
				alert("에러 발생");
			}
		});
	} else {
		return;
	}
}
function makeLogListTable(LogInfo){
	console.log(LogInfo);
	var idx, user_idx, act, locker_idx, log_date;
	idx = LogInfo.split('	')[0];
	user_idx = LogInfo.split('	')[1];
	act = LogInfo.split('	')[2];
	locker_idx = LogInfo.split('	')[3];
	log_date = LogInfo.split('	')[4];
	
	var idxString = '<td>' + idx + '</td>';
	var user_idx_String = '<td>' + user_idx + '</td>';
	var act_String = '<td>' + act + '</td>';
	var locker_idx_String = '<td>' + locker_idx + '</td>';
	var log_date_String = '<td>' + log_date + '</td>';
	var infoString = idxString + user_idx_String + act_String + locker_idx_String + log_date_String;
	$('#loglist_table').append('<tr>' + infoString  + '</tr>')
}
function makeLockerListTable(LockerInfo) {
	console.log(LockerInfo);
	var idx, id, pw, name, phone, email;
	idx = LockerInfo.split(' ')[0];
	user_idx = LockerInfo.split(' ')[1];
	locker_idx = LockerInfo.split(' ')[2];
	user_id = LockerInfo.split(' ')[3];
	user_name = LockerInfo.split(' ')[4];

	var infoString, idxString, idString, pwString;
	idxString = '<td>' + idx + '</td>';
	user_idx_String = '<td>' + user_idx + '</td>';
	locker_idx_String = '<td>' + locker_idx + '</td>';
	user_id_String = '<td>' + user_id + '</td>';
	user_name_String = '<td>' + user_name + '</td>';
	infoString = idxString + user_idx_String + user_id_String
			+ user_name_String + locker_idx_String;
	var deleteBtnString = '<td><a class="deletebtn btn btn-lg btn-danger" role="button" onclick="deleteLocker(this)">Delete</a></td>';
	$('#lockerlist_table').append(
			'<tr>' + infoString + deleteBtnString + '</tr>')
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
			alert("에러 발생");
		}
	});
}