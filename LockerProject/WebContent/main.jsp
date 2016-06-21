<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="user" class="servlet.UserMainServlet" scope="session" />
<jsp:setProperty name="user" property="*" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="/LockerProject/common/css/main.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="/LockerProject/common/javascript/main.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<title>사물함 신청 페이지</title>
<%
	String id = null
		, pw, name, phone, email, 
		idx = null;
	if ((String) session.getAttribute("LockerApply_Result") == "hasLocker"){
		%>
			<script>
				alert("이미 보유중인 사물함이 있습니다.");
			</script>
		<%
		session.setAttribute("LockerApply_Result", "");
	} else if ((String) session.getAttribute("LockerApply_Result") == "noLocker"){
		%>
			<script>
				alert("주인이 있는 사물함입니다.\n다른 사물함을 신청해 주세요.");
			</script>
		<%
		session.setAttribute("LockerApply_Result", "");
	} else if ((String) session.getAttribute("LockerApply_Result") == "Success"){
		%>
			<script>
				alert(<%= (String) session.getAttribute("LockerNumber")%> + "번 사물함이 신청되었습니다.");
			</script>
		<%
		session.setAttribute("LockerApply_Result", "");
	} else if (((String) session.getAttribute("UserWithdraw_Result") == "noHimself")
			|| ((String) session.getAttribute("LockerWithdraw_Result") == "noHimself")) {
		%>
			<script>
				alert('정상적인 접근이 아닙니다.');
				$.ajax({
					type : "GET",
					url : "/LockerProject/member/logout",
					success : function() {
						window.location = "/LockerProject/login.jsp";
					}
				});
			</script>
		<%
	} else if ((String) session.getAttribute("LockerWithdraw_Result") == "Fail") {
		%>
			<script>
				alert(<%= (String) session.getAttribute("LockerNumber")%> + "번 사물함이 신청되었습니다.");
				// alert('신청중인 사물함이 없습니다.');		
			</script>
		<%
	}
	idx = (String) session.getAttribute("idx");
	id = (String) session.getAttribute("id");
	pw = (String) session.getAttribute("pw");
	name = (String) session.getAttribute("name");
	phone = (String) session.getAttribute("phone");
	email = (String) session.getAttribute("email");
	%>
		<script>
			$(document).ready(function(){
				makeUserInfoTable('<%=idx%>', '<%=id%>', '<%=pw%>', '<%=name%>', '<%=phone%>', '<%=email%>');
			});
		</script>
	<%
	user.initDB();
	if (!user.checkSession(idx, id, pw, name, phone, email)
			|| ((String) session.getAttribute("LockerWithdraw_Result") == "noLogin")) {
		%>
			<script>
				alert("로그인이 필요합니다.");
				window.location = "/LockerProject/login.jsp";
			</script>
		<%
	}
	user.disDB();
%>
</head>
<body>
<div class="container" style="margin-top:20px;">
	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Locker Project</a>
		</div>

		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a data-toggle="modal" data-target="#userInfo">회원 정보</a></li>
				<li><a onclick="infoLocker('<%=idx%>')">사물함 정보</a></li>
				<li class="divider"></li>
				<li><a onclick="withdrawUser('<%=idx%>')">회원 탈퇴</a></li>
				<li class="divider"></li>
				<li><a onclick="withdrawLocker('<%=idx%>')">사물함 철회</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<button id="logout_btn" class="btn btn-lg btn-primary"
					onclick="logout()">Logout</button>
				</li>
			</ul>
		</div>
	</div>
	</nav>
	<table style="margin: auto;">
		<tr>
			<td>
				<div class="login-page">
					<div class="form">
						<form class="lockerApplr_form" name=login_form method="post"
							action="/LockerProject/locker/apply">
							<table border="1" bordercolor="#A9A9A9" cellspacing="0"
								cellpadding="3" align="center">
								<tr>
									<td colspan=2><img id="image_locker"
										src="/LockerProject/common/image/all_Locker.JPG"
										style="width: 500px;"></td>
								</tr>
								<tr>
									<td style="height: 50px; font-size: 25pt;"><font>Locker
											Apply</font></td>
								</tr>
								<tr>
									<td><input type=text placeholder="Locker Number"
										name=locker_idx size=20 maxlength=12 style="width: 80%;">
									</td>
								</tr>
								<tr>
									<td><input type=submit value="Apply"
										style="background: #76b852" /></td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</td>
			<td style="width: 500px; height: 600px;">
				<h1>
					주의사항 : 
				</h1>
				<ul>
					<li> 사물함은 우리 학과의 재산이므로 소중하게 다뤄주세요. </li>
					<li> 사물함 파손시 수리비는 보유 학생에게 청구됩니다. </li>
					<li> 도난 방지를 위해 자물쇠를 꼭 채워 주세요. </li>
					<li> 기타 신청 과정에 문제가 있을시</li>
					<li>  01068898320 관리자 번호로 연락주시길 바랍니다.</li>
				</ul>
			</td>
		</tr>
	</table>

		<div class="modal fade" id="userInfo" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">User Information</h4>
				</div>
				<form id=modifyForm accept-charset=utf-8 name=frm1 action=/LockerProject/member/modify method=get onsubmit="return validationCheck()">
					<div class="modal-body">
						<table id="userInfo_table">
						</table>
					</div>
					<div class="modal-footer">
						<input type=submit class="btn btn-default" value="Modify">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
	<script>
		function validationCheck() {
			if (document.frm1.user_id.value == "") {
				alert("이름을 입력하세요.");
				return false;
			} else if (document.frm1.user_pw.value == "") {
				alert("비밀번호를 입력하세요.");
				return false;
			} else if (document.frm1.user_name.value == "") {
				alert("이름을 입력하세요.");
				return false;
			} else if (document.frm1.user_phone.value == "") {
				alert("전화번호를 입력하세요.");
				return false;
			} else if (document.frm1.user_email.value == "") {
				alert("이메일을 입력하세요.");
				return false;
			} else if (!emailValidation(document.frm1.user_email.value)) {
				alert("올바른 이메일 형식으로 입력해주세요.");
				return false;
			} else {
				return true;
			}
		}

		function emailValidation(email) {
			var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;
			//이메일 형식에 맞지않으면
			if (!email.match(regExp)) {
				return false;
			}
			return true;
		}
	</script>
</body>
</html>