<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="register_url" value="/app/register"/>
<c:url var="login_url" value="/app/login"/>

<style>
    <jsp:directive.include file="/WEB-INF/style.css"/>
</style>

<BODY background="/WEB-INF/setwalls.jpg">
<jsp:directive.include file="/WEB-INF/jsp/navbar.jsp"/>

<div id="content">
    <form action="${login_url}" method="POST">
        <fieldset>
            <legend align="center">Аутентификация</legend>
            <div align="center">
                <label>Логин</label>
                <input type="text" name="login" id="login">
                <label>Пароль</label>
                <input type="password" name="password" id="password">
            </div>
            <div align="center">
                <input type="submit" value="Войти"/>
                <button href="${register_url}">Регистрация</button>
            </div>
        </fieldset>
    </form>
</div>
</BODY>
