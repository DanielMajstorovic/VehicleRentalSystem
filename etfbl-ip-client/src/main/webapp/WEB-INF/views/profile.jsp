<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/includes/head.jsp" %>
<style>
  .avatar{width:96px;height:96px;border-radius:50%;object-fit:cover}
  .rent-table th, .rent-table td{white-space:nowrap;font-size:.9rem}
</style>
<body class="client-body">
  <%@ include file="/includes/header.jsp" %>

  <div class="container py-3">

    <div class="card p-3 mb-4 shadow-sm">
      <div class="d-flex align-items-center">
        <img src="http://localhost:8080/images/avatars/${sessionScope.client.id}.png"
             class="avatar me-3"
             onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/assets/images/client.png';">
        <div class="flex-grow-1">
          <h6 class="mb-1">${clientInfo.email}</h6>
          <small class="text-muted">${sessionScope.client.username}</small>
        </div>
      </div>
      <hr>
      <p class="mb-1"><strong>Name:</strong> ${sessionScope.client.firstName} ${sessionScope.client.lastName}</p>
      <p class="mb-1"><strong>ID No:</strong> ${clientInfo.idNumber}</p>
      <p class="mb-1"><strong>Phone:</strong> ${clientInfo.phone}</p>
    </div>

    <h5 class="mb-2">Finished rentals</h5>
    <div class="table-responsive">
      <table class="table table-sm table-striped rent-table">
        <thead class="table-light">
          <tr><th>Vehicle</th><th>Start</th><th>End</th><th>Total ($)</th></tr>
        </thead>
        <tbody>
          <c:forEach items="${rentals}" var="r">
            <tr>
              <td>${r.vehicleModel}</td>
              <td><fmt:formatDate value="${r.startTime}" pattern="dd/MM/yyyy HH:mm"/></td>
              <td><fmt:formatDate value="${r.endTime}"   pattern="dd/MM/yyyy HH:mm"/></td>
              <td><fmt:formatNumber value="${r.totalAmount}" type="currency" maxFractionDigits="2"/></td>
            </tr>
          </c:forEach>
          <c:if test="${empty rentals}">
            <tr><td colspan="4" class="text-center text-muted">No finished rentals.</td></tr>
          </c:if>
        </tbody>
      </table>
    </div>

    <h5 class="mt-4">Change password</h5>
    <c:if test="${not empty pwdMsg}">
      <div class="alert alert-info py-2">${pwdMsg}</div>
    </c:if>
    <form class="row g-2" action="profile" method="post">
      <input type="hidden" name="action" value="changePwd">
      <div class="col-12"><input class="form-control" type="password" name="current" placeholder="Current password" required></div>
      <div class="col-12"><input class="form-control" type="password" name="next" placeholder="New password" required></div>
      <div class="col-12 d-grid"><button class="btn btn-primary">Update</button></div>
    </form>

    <h5 class="mt-4 text-danger">Delete account</h5>
    <p class="small text-muted">
      Deleting the account is <strong>permanent</strong>. To restore it later you will have to
      contact system managers. Are you sure?
    </p>
    <form action="profile" method="post" onsubmit="return confirm('Permanently delete account?');">
      <input type="hidden" name="action" value="delete">
      <button class="btn btn-outline-danger w-100">Delete my account</button>
    </form>

  </div>

<%@ include file="/includes/footer.jsp" %>
</body>
