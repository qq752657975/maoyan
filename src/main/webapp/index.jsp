<html>
<body>
<h2>Hello World!</h2>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
            path + "/";
%>
%>
<form action="<%=basePath%>agent/text" method="post" enctype="multipart/form-data">
    <input type="file" name="file" /><br />
    <input type="text" name="name" value="123"><br />
    <input type="submit" value="上传"/>

</form>
</body>
</html>
