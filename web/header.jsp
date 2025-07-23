<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">

<style>
/* Header Styles */
:root {
    --primary-color: #4F46E5;
    --primary-hover: #4338CA;
    --text-color: #1F2937;
    --text-light: #6B7280;
    --border-color: #E5E7EB;
    --bg-color: #FFFFFF;
    --shadow-sm: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
    --shadow-md: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
    --transition: all 0.3s ease;
}

.main-header {
    background-color: rgba(255, 255, 255, 0.98);
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    width: 100%;
    z-index: 1000;
    transition: all 0.3s ease;
    backdrop-filter: blur(10px);
    -webkit-backdrop-filter: blur(10px);
}

.header-container {
    max-width: 1400px;
    margin: 0 auto;
    padding: 0 2rem;
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 70px;
    position: relative;
    gap: 1.5rem;
}

/* Ensure proper alignment of header elements */
.header-container > * {
    flex-shrink: 0;
}

/* Logo */
.logo {
    display: flex;
    align-items: center;
    z-index: 1001;
}

.logo-img {
    height: 40px;
    width: auto;
    transition: var(--transition);
}

/* Navigation */
.main-nav {
    margin-left: 40px;
    flex: 1;
}

.nav-list {
    display: flex;
    list-style: none;
    gap: 1.5rem;
    margin: 0;
    padding: 0;
}

.nav-item {
    position: relative;
}

.nav-link {
    color: var(--text-color);
    text-decoration: none;
    font-weight: 500;
    font-size: 0.95rem;
    padding: 0.75rem 0;
    display: flex;
    align-items: center;
    gap: 0.5rem;
    transition: var(--transition);
    position: relative;
    font-family: 'Poppins', sans-serif;
}

.nav-link:hover {
    color: var(--primary-color);
}

/* Dropdown Menu */
.dropdown {
    position: relative;
}

.dropdown-menu {
    position: absolute;
    top: 100%;
    left: 0;
    background: white;
    min-width: 220px;
    border-radius: 8px;
    box-shadow: var(--shadow-md);
    opacity: 0;
    visibility: hidden;
    transform: translateY(10px);
    transition: all 0.3s ease;
    z-index: 1000;
    padding: 0.5rem 0;
    margin-top: 0.5rem;
}

.dropdown.active .dropdown-menu {
    opacity: 1;
    visibility: visible;
    transform: translateY(0);
}

.dropdown-item {
    display: flex;
    align-items: center;
    gap: 0.75rem;
    padding: 0.65rem 1.25rem;
    color: var(--text-color);
    text-decoration: none;
    font-size: 0.9rem;
    transition: var(--transition);
    font-family: 'Poppins', sans-serif;
}

.dropdown-item i {
    width: 18px;
    text-align: center;
    color: var(--text-light);
}

.dropdown-item:hover {
    background-color: #F9FAFB;
    color: var(--primary-color);
}

.dropdown-divider {
    height: 1px;
    background-color: var(--border-color);
    margin: 0.5rem 0;
}

/* Search Bar */
.search-container {
    position: relative;
    margin: 0 1rem 0 0.5rem;
    width: 260px;
    flex-shrink: 1;
    align-self: center;
}

.search-form {
    position: relative;
    display: flex;
    align-items: center;
    width: 100%;
}

.search-input {
    width: 100%;
    padding: 0.6rem 1rem 0.6rem 2.8rem;
    border: 1px solid #e0e4f5;
    border-radius: 25px;
    font-size: 0.92rem;
    transition: all 0.3s ease;
    background-color: #f5f7ff;
    font-family: 'Poppins', sans-serif;
    box-shadow: 0 2px 10px rgba(102, 126, 234, 0.1);
    height: 40px;
}

.search-input:focus {
    outline: none;
    border-color: #667eea;
    box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.2);
    background-color: #fff;
}

.search-icon {
    position: absolute;
    left: 16px;
    top: 50%;
    transform: translateY(-50%);
    color: #667eea;
    font-size: 1rem;
    z-index: 2;
    pointer-events: none;
}

.search-button {
    position: absolute;
    left: 0.75rem;
    top: 50%;
    transform: translateY(-50%);
    background: none;
    border: none;
    color: var(--text-light);
    cursor: pointer;
    padding: 0.25rem;
    transition: var(--transition);
}

/* User Actions */
.user-actions {
    display: flex;
    align-items: center;
    gap: 3rem;
    margin-left: auto;
    position: relative;
    z-index: 2;
}

.login-btn, .signup-btn {
    padding: 0.5rem 1.25rem;
    border-radius: 6px;
    font-weight: 500;
    font-size: 0.9rem;
    text-decoration: none;
    transition: var(--transition);
    font-family: 'Poppins', sans-serif;
}

.login-btn {
    color: var(--text-color);
}

.signup-btn {
    background: linear-gradient(45deg, #667eea, #764ba2);
    color: white;
    border: none;
    font-weight: 600;
    box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
    transition: all 0.3s ease;
}

.signup-btn:hover {
    background-color: var(--primary-hover);
    border-color: var(--primary-hover);
}

.user-actions .user-icon {
    width: 44px;
    height: 44px;
    border-radius: 12px;
    background: linear-gradient(135deg, #f0f4ff 0%, #e6ebff 100%);
    display: flex;
    align-items: center;
    justify-content: center;
    color: #5c6bc0;
    cursor: pointer;
    transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
    border: none;
    font-size: 1.2rem;
    box-shadow: 0 4px 6px -1px rgba(102, 126, 234, 0.1), 
                0 2px 4px -1px rgba(102, 126, 234, 0.06);
    position: relative;
    overflow: hidden;
    flex-shrink: 0;
    margin: 0;
}

.user-actions .user-icon::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: linear-gradient(135deg, rgba(255,255,255,0.3) 0%, rgba(255,255,255,0) 100%);
    opacity: 0;
    transition: opacity 0.3s ease;
}

.user-actions .user-icon:hover::before {
    opacity: 1;
}

.user-actions .user-icon:hover {
    transform: translateY(-2px);
    box-shadow: 0 10px 15px -3px rgba(102, 126, 234, 0.2), 
                0 4px 6px -2px rgba(102, 126, 234, 0.1);
    color: #4a56b7;
}

.user-actions .user-icon i {
    position: relative;
    z-index: 1;
}

/* User Dropdown */
.user-dropdown {
    position: relative;
}

.dropdown-content {
    position: absolute;
    right: 0;
    top: calc(100% + 0.5rem);
    background: white;
    min-width: 220px;
    border-radius: 8px;
    box-shadow: var(--shadow-md);
    opacity: 0;
    visibility: hidden;
    transform: translateY(10px);
    transition: all 0.3s ease;
    z-index: 1000;
    padding: 0.5rem 0;
}

.user-dropdown:hover .dropdown-content {
    opacity: 1;
    visibility: visible;
    transform: translateY(0);
}

/* Mobile Menu Toggle */
.mobile-menu-toggle {
    display: none;
    background: none;
    border: none;
    color: var(--text-color);
    font-size: 1.5rem;
    cursor: pointer;
    z-index: 1001;
    padding: 0.5rem;
    margin-right: -0.5rem;
}

/* Mobile Search */
.mobile-search {
    display: none;
    padding: 1rem;
    border-top: 1px solid var(--border-color);
    background: white;
}

/* Responsive Styles */
@media (max-width: 1024px) {
    .header-container {
        padding: 0 1.5rem;
    }
    
    .search-container {
        width: 250px;
    }
}

@media (max-width: 992px) {
    .mobile-menu-toggle {
        display: block;
    }
    
    .main-nav {
        position: fixed;
        top: 70px;
        left: 0;
        right: 0;
        background: white;
        height: calc(100vh - 70px);
        overflow-y: auto;
        transform: translateX(100%);
        transition: transform 0.3s ease-in-out;
        padding: 1.5rem;
        margin: 0;
        z-index: 1000;
    }
    
    .main-nav.active {
        transform: translateX(0);
        box-shadow: -5px 0 15px rgba(0, 0, 0, 0.1);
    }
    
    .nav-list {
        flex-direction: column;
        gap: 0.5rem;
    }
    
    .nav-link {
        padding: 0.75rem 1rem;
        border-radius: 6px;
    }
    
    .nav-link:hover {
        background-color: #F9FAFB;
    }
    
    .dropdown-menu {
        position: static;
        box-shadow: none;
        opacity: 1;
        visibility: visible;
        transform: none;
        padding: 0.5rem 0 0 1rem;
        margin: 0;
        max-height: 0;
        overflow: hidden;
        transition: max-height 0.3s ease;
    }
    
    .dropdown.active .dropdown-menu {
        max-height: 500px;
    }
    
    .dropdown-item {
        padding: 0.6rem 1rem;
        font-size: 0.9rem;
    }
    
    .search-container {
        display: none;
    }
    
    .mobile-search {
        display: block;
    }
    
    .mobile-search .search-form {
        width: 100%;
    }
    
    .mobile-search .search-input {
        width: 100%;
        padding: 0.75rem 1rem 0.75rem 3rem;
    }
    
    .mobile-search .search-button {
        left: 1rem;
    }
}

@media (max-width: 768px) {
    .header-container {
        padding: 0 1rem;
        height: 65px;
    }
    
    .logo-img {
        height: 35px;
    }
    
    .main-nav {
        top: 65px;
        height: calc(100vh - 65px);
    }
    
    .user-actions {
        margin-left: auto;
    }
    
    .signup-btn {
        display: none;
    }
}

/* Sticky Header */
.main-header.sticky {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    animation: slideDown 0.5s ease-out;
    box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1);
}

@keyframes slideDown {
    from {
        transform: translateY(-100%);
    }
    to {
        transform: translateY(0);
    }
}

/* Animation for dropdown icons */
.dropdown-toggle i {
    transition: transform 0.3s ease;
}

.dropdown.active .dropdown-toggle i {
    transform: rotate(180deg);
}
</style>

<header class="main-header">
    <div class="header-container">
        <!-- Logo -->
        <div class="logo">
            <a href="home">
                <img src="img/logo.png" alt="EduLearn" class="logo-img">
            </a>
        </div>
        
        <!-- Mobile Menu Toggle -->
        <div class="mobile-menu-toggle">
            <i class="fas fa-bars"></i>
        </div>
        
        <!-- Main Navigation -->
        <nav class="main-nav">
            <ul class="nav-list">
                <li class="nav-item"><a href="home" class="nav-link">Home</a></li>
                <li class="nav-item"><a href="courses" class="nav-link">Courses</a></li>
                <li class="nav-item"><a href="bloglist" class="nav-link">Blog</a></li>
                <c:if test="${not empty sessionScope.user}">
                    <li class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle">My Account <i class="fas fa-chevron-down"></i></a>
                        <ul class="dropdown-menu">
                            <li><a href="userprofile" class="dropdown-item"><i class="fas fa-user"></i> Profile</a></li>
                            <li><a href="myCourses" class="dropdown-item"><i class="fas fa-book"></i> My Courses</a></li>
                            <li><a href="myRegistrations" class="dropdown-item"><i class="fas fa-book"></i> My Registrations</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </nav>
        
        <!-- Header Actions -->
        <div class="header-actions">
            <!-- Search Form -->
           
            
            <!-- User Actions -->
            <div class="user-actions">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <div class="user-dropdown">
                            <button class="user-avatar">
                                <i class="fas fa-user-circle"></i>
                            </button>
                            <div class="dropdown-content">
                                <a href="userprofile" class="dropdown-item"><i class="fas fa-user"></i> Profile</a>
                                <a href="myCourses" class="dropdown-item"><i class="fas fa-book"></i> My Courses</a>
                                <a href="myRegistrations" class="dropdown-item"><i class="fas fa-book"></i> My Registrations</a>
                                <div class="dropdown-divider"></div>
                                <a href="logout" class="dropdown-item logout"><i class="fas fa-sign-out-alt"></i> Logout</a>
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
    </div>
    
    <!-- Mobile Search -->
    <div class="mobile-search">
        <form action="search" method="GET" class="search-form">
            <input type="text" name="search" placeholder="Search courses...">
            <button type="submit"><i class="fas fa-search"></i></button>
        </form>
    </div>
</header>

<script>
// Mobile menu toggle
const mobileMenuToggle = document.querySelector('.mobile-menu-toggle');
const mainNav = document.querySelector('.main-nav');
const body = document.body;

mobileMenuToggle.addEventListener('click', () => {
    mainNav.classList.toggle('active');
    mobileMenuToggle.classList.toggle('active');
    body.classList.toggle('menu-open');
});

// Close mobile menu when clicking outside
document.addEventListener('click', (e) => {
    if (!e.target.closest('.main-nav') && !e.target.closest('.mobile-menu-toggle')) {
        mainNav.classList.remove('active');
        mobileMenuToggle.classList.remove('active');
        body.classList.remove('menu-open');
    }
});

// Dropdown functionality
const dropdowns = document.querySelectorAll('.dropdown-toggle');
dropdowns.forEach(dropdown => {
    dropdown.addEventListener('click', (e) => {
        e.preventDefault();
        const parent = dropdown.parentElement;
        parent.classList.toggle('active');
    });
});

// Close dropdowns when clicking outside
document.addEventListener('click', (e) => {
    if (!e.target.matches('.dropdown-toggle')) {
        document.querySelectorAll('.dropdown').forEach(dropdown => {
            dropdown.classList.remove('active');
        });
    }
});
</script>