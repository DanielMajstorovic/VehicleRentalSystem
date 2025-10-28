<%@ page contentType="text/html;charset=UTF-8" %>
<nav class="navbar navbar-expand-lg navbar-dark custom-navbar shadow-sm">
  <div class="container-fluid">
    <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/home">
      <img src="${pageContext.request.contextPath}/assets/images/etf_icon.png"
           alt="ETF Logo"
           class="navbar-logo me-2"/>
      <span class="fs-5 fw-semibold">ETFBL_IP</span>
    </a>
    <a href="logout"
       class="btn btn-outline-light d-flex align-items-center">
      <i class="bi bi-box-arrow-right me-2"></i>Logout
    </a>
  </div>
</nav>
