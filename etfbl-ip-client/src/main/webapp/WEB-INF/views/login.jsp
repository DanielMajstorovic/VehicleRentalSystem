<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/includes/head.jsp" %>
<body>
  <div class="login-container">
    <div class="background-overlay"></div>
    <div class="login-card">
      <div class="card-header">
        <div class="logo-container"><i class="bi bi-car-front-fill"></i></div>
        <h2>ETFBL_IP</h2><p class="text-muted">Vehicle Rental System</p>
      </div>
      <div class="card-body">
        <h3 class="mb-4">Client Login</h3>

        <c:if test="${not empty error}">
          <div class="alert alert-danger text-center py-2" role="alert">
            ${error}
          </div>
        </c:if>

        <form action="login" method="post">
          <div class="mb-4">
            <label class="form-label">Username</label>
            <div class="input-group">
              <span class="input-group-text"><i class="bi bi-person"></i></span>
              <input name="username" type="text" placeholder="Username" class="form-control" required />
            </div>
          </div>
          <div class="mb-4">
            <label class="form-label">Password</label>
            <div class="input-group">
              <span class="input-group-text"><i class="bi bi-lock"></i></span>
              <input id="pwd" name="password" type="password" placeholder="Password" class="form-control" required />
              <button class="input-group-text password-toggle" type="button" onclick="togglePwd()" tabindex="-1">
                <i class="bi bi-eye"></i>
              </button>
            </div>
          </div>
          <div class="d-grid gap-2">
            <button type="submit" class="btn btn-primary btn-lg">
              <i class="bi bi-box-arrow-in-right me-2"></i>Login
            </button>
          </div>
          <div class="text-center mt-3 small">
            New here? <a href="register">Create account</a>
          </div>
        </form>
      </div>
    </div>
  </div>

<script>
function togglePwd(){
  const pwd = document.getElementById('pwd');
  const icon = document.querySelector('.password-toggle i');
  if (pwd.type === 'password') {
    pwd.type = 'text';
    icon.classList.replace('bi-eye', 'bi-eye-slash');
  } else {
    pwd.type = 'password';
    icon.classList.replace('bi-eye-slash', 'bi-eye');
  }
}
</script>
<%@ include file="/includes/footer.jsp" %>
