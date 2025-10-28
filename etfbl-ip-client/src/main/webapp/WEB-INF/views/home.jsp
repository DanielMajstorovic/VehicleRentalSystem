<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/includes/head.jsp"%>
<title>ETFBL_IP - Home</title>
</head>
<body>
	<div class="page-wrapper">
		<%@ include file="/includes/header.jsp"%>

		<div class="page-content">
			<c:if test="${not empty activeRental}">
				<div class="container">
					<div
						class="alert alert-warning d-flex justify-content-between align-items-center small">
						<div>
							<strong>Active ride:</strong> ${activeRental.vehicleUid} –
							${activeRental.vehicleModel}, started at
							${activeRental.startTime}
						</div>
						<a href="active-rental" class="btn btn-sm btn-outline-dark">Details</a>
					</div>
				</div>
			</c:if>

			<div
				class="container flex-grow-1 d-flex align-items-center justify-content-center">
				<div class="home-grid-container">
					<div class="row g-3 home-grid">
						<div class="col-10 col-sm-6 col-md-4 col-lg-3">
							<a href="car"
								class="btn btn-outline-primary w-100 py-4 d-flex flex-column align-items-center">
								<i class="bi bi-car-front-fill mb-2"></i> Car rental
							</a>
						</div>
						<div class="col-10 col-sm-6 col-md-4 col-lg-3">
							<a href="ebike"
								class="btn btn-outline-primary w-100 py-4 d-flex flex-column align-items-center">
								<i class="bi bi-bicycle mb-2"></i> E‑Bike rental
							</a>
						</div>
						<div class="col-10 col-sm-6 col-md-4 col-lg-3">
							<a href="escooter"
								class="btn btn-outline-primary w-100 py-4 d-flex flex-column align-items-center">
								<i class="bi bi-scooter mb-2"></i> E‑Scooter rental
							</a>
						</div>
						<div class="col-10 col-sm-6 col-md-4 col-lg-3">
							<a href="profile"
								class="btn btn-outline-secondary w-100 py-4 d-flex flex-column align-items-center">
								<i class="bi bi-person-circle mb-2"></i> My profile
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>

		<%@ include file="/includes/footer.jsp"%>
	</div>
</body>
</html>
