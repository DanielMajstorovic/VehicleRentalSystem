<%@ page contentType="text/html;charset=UTF-8"%>

<nav class="navbar navbar-expand-lg navbar-dark custom-navbar shadow-sm">
  <div class="container-fluid">

    <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/feed">
      <img
        src="${pageContext.request.contextPath}/assets/images/etf_icon.png"
        alt="ETF Logo"
        class="navbar-logo me-2"
      />
      ETFBL_IP
    </a>


    <button class="btn btn-outline-light d-flex align-items-center" id="logoutBtn">
      <i class="bi bi-box-arrow-right me-2"></i> Logout
    </button>
  </div>
</nav>

<script>
  $(document).ready(function () {
    $('#logoutBtn').click(function () {
      sessionStorage.clear();
      window.location = '${pageContext.request.contextPath}/login';
    });
  });
</script>
