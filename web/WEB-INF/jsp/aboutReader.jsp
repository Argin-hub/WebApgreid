<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    <jsp:directive.include file="/WEB-INF/style.css"/>
</style>

<BODY background="/WEB-INF/setwalls.jpg">
<jsp:directive.include file="/WEB-INF/jsp/navbar.jsp"/>
<div id="content" align="center">
    <div>
        <p>Имя: ${user_info.person.firstName}</p>
        <p>Фамилия: ${user_info.person.lastName} </p>
        <p>Отчество: ${user_info.person.middleName}</p>
        <p>Телефон: ${user_info.person.phone}</p>
        <p>Дата рождения: ${user_info.person.birthday}</p>
        <p>e-mail: ${user_info.email}</p>
        <p>Дата регистрации: ${user_info.registerDate} </p>
        <p>Роль: ${user_info.userRole.name}</p>
    </div>
    <div>
        <form action="deleteProfile" method="POST">
            <input type="hidden" name="delete_id" value="${user_info.id}">
            <button id="submit" name="submit">Удалить пользователя</button>
        </form>
    </div>
</div>
</BODY>

