<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form method="post">

	Name <input type="text" name="name"/>
	Age <input type="text" name="age"/>

	<input type="submit"/>

</form>

<%
		if(request.getMethod().equalsIgnoreCase("post")){

%>

			<jsp:useBean id="pd" class="com.cg.Person" scope="request"/>
				
				<jsp:setProperty name="pd" property="name" param="name"/>
			
				<jsp:setProperty name="pd" property="age" param="age"/>

			<jsp:include page="displayPersonDetails.jsp"/>




<%
		}
%>





</body>
</html>