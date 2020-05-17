<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    <jsp:directive.include file="/WEB-INF/style.css"/>
</style>

<BODY background="/WEB-INF/setwalls.jpg">
<jsp:directive.include file="/WEB-INF/jsp/navbar.jsp"/>
<div id="content">
    <c:if test="${role.equals('admin')}">
        <table border=1 class="task" align=center>
            <caption>Пользователи</caption>
            <thead>
                <tr>
                    <th>Имя</th>
                    <th>Фамилия</th>
                    <th>e-mail</th>
                    <th>Дата регистрации</th>
                    <th>Действие</th>
                </tr>
            </thead>
            <c:forEach items="${readers}" var="reader">
                <tr>
                    <td>${reader.person.firstName}</td>
                    <td>${reader.person.lastName}</td>
                    <td>${reader.email}</td>
                    <td>${reader.registerDate}</td>
                    <td class="text-center"><a class='btn btn-info btn-xs' href="aboutReader?user_id=${reader.id}">Подробнее</a></td>
                </tr>
            </c:forEach>
        </table>
        <div align="center">Страница ${currentPage} из ${noOfPages}</div>
    </c:if>
</div>
</BODY>