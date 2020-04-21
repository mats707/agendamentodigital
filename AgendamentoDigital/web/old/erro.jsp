<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <h1>Error!</h1>
        <%= ((Exception)request.getAttribute("erro")).getMessage() %>
        <a href="${site}/index.jsp">Página Inicial</a>
    </body>
</html>
