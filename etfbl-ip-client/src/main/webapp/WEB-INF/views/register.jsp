<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/includes/head.jsp" %>
<body>
  <div class="login-container">
    <div class="background-overlay"></div>
    <div class="login-card">
      <div class="card-header">
        <div class="logo-container"><i class="bi bi-person-plus-fill"></i></div>
        <h2>New Client</h2>
      </div>
      <div class="card-body">
        <c:if test="${not empty error}">
          <div class="alert alert-danger">${error}</div>
        </c:if>
        <form action="register" method="post" enctype="multipart/form-data">
          <div class="row g-2">
            <div class="col-6"><input class="form-control" name="firstName"  placeholder="First name"  required></div>
            <div class="col-6"><input class="form-control" name="lastName"   placeholder="Last name"   required></div>
            <div class="col-6"><input class="form-control" name="username"   placeholder="Username"    required></div>
            <div class="col-6"><input type="password" class="form-control" name="password" placeholder="Password" required></div>
            <div class="col-6"><input class="form-control" name="idNumber"        placeholder="ID number"       required></div>
            <div class="col-6"><input class="form-control" name="passportNumber"  placeholder="Passport number" required></div>
            <div class="col-6"><input type="email" class="form-control" name="email" placeholder="E‑mail" required></div>
            <div class="col-6"><input class="form-control" name="phone" placeholder="Phone" required></div>
            <div class="col-12 mt-2"><input class="form-control" type="file" name="avatar" accept="image/*"></div>
          </div>
          <button class="btn btn-primary mt-4 w-100">Register</button>
          <div class="text-center mt-2 small">
            Already registered? <a href="login">Back to login</a>
          </div>
        </form>
      </div>
    </div>
  </div>
<%@ include file="/includes/footer.jsp" %>
