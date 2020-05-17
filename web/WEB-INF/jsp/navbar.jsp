<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:url var="books_url" value="/app/books"/>
<c:url var="readers_url" value="/app/readers"/>

<div id="header">Web Library</div>
<div id="navbar">
    <ul>
        <c:if test="${role.equals('user')}">
            <li><a href=${books_url}>Книги</a></li>
        </c:if>
        <c:if test="${role.equals('admin')}">
            <li><a href=${books_url}>Книги</a></li>
            <li><a href=${readers_url}>Пользователи</a></li>
        </c:if>
    </ul>
</div>




