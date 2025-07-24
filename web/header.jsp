<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

<style>
:root {
    --primary: #4F46E5;
    --primary-hover: #4338CA;
    --bg: #fff;
    --text: #1F2937;
    --muted: #6B7280;
    --radius: 8px;
}

* {
    box-sizing: border-box;
}

body {
    font-family: 'Poppins', sans-serif;
    margin: 0;
}

.header {
    background: #fff;
    border-bottom: 1px solid #e5e7eb;
    position: sticky;
    top: 0;
    z-index: 1000;
    box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}

.header .container {
    max-width: 1200px;
    margin: auto;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0.75rem 1.5rem;
}

.header .logo img {
    height: 40px;
}

.nav {
    display: flex;
    gap: 1.5rem;
    align-items: center;
}

.nav a {
    text-decoration: none;
    color: var(--text);
    font-weight: 500;
    transition: 0.3s;
}

.nav a:hover {
    color: var(--primary);
}

.search-form {
    position: relative;
}

.search-input {
    padding: 0.4rem 1rem 0.4rem 2.5rem;
    border-radius: var(--radius);
    border: 1px solid #ccc;
    background: #f9f9f9;
    font-size: 0.9rem;
}

.search-form i {
    position: absolute;
    left: 0.9rem;
    top: 50%;
    transform: translateY(-50%);
    color: #999;
}

.auth-buttons {
    display: flex;
    gap: 1rem;
    align-items: center;
}

.auth-buttons a {
    text-decoration: none;
    padding: 0.4rem 1rem;
    border-radius: var(--radius);
    font-weight: 500;
    font-size: 0.9rem;
}

.signup-btn {
    background-color: var(--primary);
    color: white;
    border: none;
    box-shadow: 0 4px 10px rgba(79, 70, 229, 0.2);
}

.signup-btn:hover {
    background-color: var(--primary-hover);
}

.login-btn {
    color: var(--text);
}

.user-dropdown {
    position: relative;
}

.user-avatar {
    background: #e0e7ff;
    border: none;
    border-radius: 50%;
    width: 40px;
    height: 40px;
    font-size: 1.2rem;
    color: var(--primary);
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
}

.dropdown-content {
    position: absolute;
    right: 0;
    top: 110%;
    background: white;
    min-width: 200px;
    border-radius: var(--radius);
    box-shadow: 0 4px 10px rgba(0,0,0,0.1);
    padding: 0.5rem 0;
    display: none;
    z-index: 999;
}

.dropdown-content a {
    display: block;
    padding: 0.6rem 1rem;
    color: var(--text);
    text-decoration: none;
    font-size: 0.9rem;
}

.dropdown-content a:hover {
    background: #f5f5f5;
}

.user-dropdown:hover .dropdown-content {
    display: block;
}
</style>

<header class="header">
    <div class="container">
        <!-- Logo -->
        <div class="logo">
            <a href="${pageContext.request.contextPath}/home">
                <img src="img/logo.png" alt="Logo">
            </a>
        </div>

        <!-- Navigation -->
        <nav class="nav">
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/courses">Courses</a>
            <a href="bloglist">Blog</a>
            <a href="userprofile">About</a>
            <c:if test="${not empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/my-registrations">My Registrations</a>
            </c:if>
        </nav>

        <!-- Search & User -->
        <div class="auth-buttons">
            <!-- Search -->
            <form action="search" method="GET" class="search-form">
                <i class="fas fa-search"></i>
                <input type="text" name="search" class="search-input" placeholder="Search">
            </form>

            <!-- If user is logged in -->
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <div class="user-dropdown">
                        <button class="user-avatar">
                            <i class="fas fa-user"></i>
                        </button>
                        <div class="dropdown-content">
                            <a href="userprofile"><i class="fas fa-user"></i> Profile</a>
                            <a href="myCourses"><i class="fas fa-book"></i> My Courses</a>
                            <a href="myRegistrations"><i class="fas fa-check-circle"></i> My Registrations</a>
                            <hr style="margin: 0.5rem 0;">
                            <a href="logout"><i class="fas fa-sign-out-alt"></i> Logout</a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <a href="login" class="login-btn">Log In</a>
                    <a href="register" class="signup-btn">Sign Up</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
