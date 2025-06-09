<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/header.css" />
<div>
    <header>
        <div class="logo">
            <img src="img/logo.png" alt="alt" height="40" width="120"/>
        </div>
        <nav>
            <a href="home">Home</a>
            <a href="#">Course</a>
            <a href="#">Blog</a>
            <a href="profile.jsp">About</a>
        </nav>
        <div class="header-right">
            <!-- Form tìm kiếm -->
            <form action="search" method="GET" class="search-form">
                <input type="text" name="search" class="search-input" placeholder="Search">
            </form>
            <!-- Nút search-button (giữ nguyên) -->
            <button class="search-button">
                <svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                </svg>
            </button>
            <c:if test="${not empty sessionScope.user}">
                <a href="logout" class="signup-button">Log out</a>
            </c:if>
                 <c:if test="${empty sessionScope.user}">
                <a href="login" class="signup-button">Log in</a>
            </c:if>
                
            
        </div>
    </header>
</div>