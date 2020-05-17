<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="register_url" value="/app/register"/>
<c:url var="home_url" value="/app/welcome"/>
<style>
    <jsp:directive.include file="/WEB-INF/style.css"/>
</style>

<BODY background="/WEB-INF/setwalls.jpg">
<jsp:directive.include file="/WEB-INF/jsp/navbar.jsp"/>
<div id="content">

    <form action="${register_url}" method="POST">
        <fieldset>
            <legend id="header">Регистрация</legend>
            <div>
                <label>Имя</label>
                <input id="fn" name="first_name" type="text" placeholder="Имя">
            </div>
            <div>
                <label>Фамилия</label>
                <input id="ln" name="last_name" type="text" placeholder="Фамилия">
            </div>
            <div>
                <label>Отчество</label>
                <input id="mn" name="middle_name" type="text" placeholder="Отчество">
            </div>
            <div>
                <label>Дата рождения</label>
                <input id="birthDate" name="birthday" type="text" placeholder="YYYY-MM-DD">
            </div>
            <div>
                <label>Контактный телефон</label>
                <input id="phone" name="phone" type="text" placeholder="89950239421" >
            </div>
            <div>
                <label>e-mail</label>
                <input type="text" id="email" name="email" placeholder="e-mail" >
            </div>
            <div>
                <label>Пароль</label>
                <input type="password" id="password" name="password" >
                Минимальная длина пароля: 6 символов
            </div>
            <div>
                <label>Подтверждение пароля</label>
                <input type="password" id="password_confirm" name="password_confirm" placeholder="">
            </div>
            <div>
                <button id="submit" name="submit">Зарегистрироваться</button>
            </div>
            <div>
                <a href="${home_url}" role="button">Назад</a>
            </div>
        </fieldset>
    </form>
</div>
</BODY>
