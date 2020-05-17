<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    <jsp:directive.include file="/WEB-INF/style.css"/>
</style>

<BODY background="/WEB-INF/setwalls.jpg">
<jsp:directive.include file="/WEB-INF/jsp/navbar.jsp"/>
<div id="content">
    <table border=1 class="task" align=center>
        <caption>Книги</caption>
        <thead>
            <tr>
                <th>Наименование</th>
                <th>Жанр</th>
                <th>Автор</th>
                <th>Описание</th>
                <th>Действие</th>
            </tr>
        </thead>
        <c:forEach items="${books}" var="books">
            <tr>
                <td>${books.name}</td>
                <td>${books.genre.name}</td>
                <td>${books.author.lastName}</td>
                <td>${books.description}</td>
                <td class="text-center">Подробнее</td>
            </tr>
        </c:forEach>
    </table>
    <div align="center">
        Страница ${currentPage} из ${noOfPages}
    </div>
</div>
</BODY>