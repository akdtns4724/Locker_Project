<%@ page contentType="text/html;charset=euc-kr" %>
<jsp:useBean id="login" class="pack.Loginservice" scope="session" />
<jsp:setProperty name="login" property="*" />
<%
	String user_id = request.getParameter("id");
	String user_pw = request.getParameter("pw");
	
    login.initDB();
   	if(login.login(user_id, user_pw)){
   		out.println("true");
   	} else {
   		out.println("false");
   	}
    login.disDB();
%>
<html>
    <head>
        <title>로그인 상태</title>
    </head>
    <body>
        
    <body>
</html>