<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/includes/head.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet"
	href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>

<style>
.car-card {
	transition: box-shadow .25s;
	cursor: pointer;
}

.car-card:hover {
	box-shadow: 0 6px 16px rgba(0, 0, 0, .15);
}

.car-img {
	width: 100%;
	height: 120px;
	object-fit: contain;
	border-radius: .4rem .4rem 0 0;
}

#map {
	height: 250px;
	border-radius: 8px;
}
</style>

<body class="client-body">
	<%@ include file="/includes/header.jsp"%>

	<div class="container py-3">
		<h4 class="mb-3">Select a car</h4>

		<div class="d-flex flex-column gap-3">
			<c:forEach items="${cars}" var="c">
				<div class="card car-card" data-vehicle-id="${c.id}"
					onclick="chooseCar(this, '${c.uid}', ${c.id}, ${c.pricePerSecond})">
					<img class="car-img"
						src="http://localhost:8080/images/vehicles/${c.uid}.jpg"
						onerror="
              this.onerror=null;
              this.src='${pageContext.request.contextPath}/assets/images/car.png';
            " />
					<div class="card-body py-2">
						<div class="d-flex justify-content-between">
							<span> <strong>${c.manufacturerName}</strong> ${c.model}
							</span> <span class="text-primary small"> <c:set var="dailyPrice"
									value="${c.pricePerSecond * 86400}" /> <c:choose>
									<c:when test="${dailyPrice lt 10}">
                    $<fmt:formatNumber value="${dailyPrice}"
											type="number" minFractionDigits="2" maxFractionDigits="2" />
                    /day
                  </c:when>
									<c:otherwise>
                    $<fmt:formatNumber value="${dailyPrice}"
											type="number" maxFractionDigits="0" />
                    /day
                  </c:otherwise>
								</c:choose>
							</span>
						</div>
					</div>
				</div>
			</c:forEach>
			<c:if test="${empty cars}">
				<div class="alert alert-info text-center">No cars available.</div>
			</c:if>
		</div>

		<form id="rentForm" class="mt-4 d-none" action="car" method="post">
			<input type="hidden" name="vehicleId" id="vehicleId" /> <input
				type="hidden" name="vehicleUid" id="vehicleUid" /> <input
				type="hidden" name="pricePerSec" id="pps" /> <input type="hidden"
				name="startX" id="startX" /> <input type="hidden" name="startY"
				id="startY" />

			<h5 class="mb-2">Pick‑up location</h5>
			<div id="map" class="mb-3"></div>

			<h5 class="mb-2">Driver’s info</h5>
			<input class="form-control mb-2" name="driversLicense"
				placeholder="Driver’s license" required /> <input
				class="form-control mb-2" name="paymentCard" id="card"
				placeholder="Payment card (xxxx xxxx xxxx xxxx)" maxlength="19"
				required />

			<div class="form-check mb-3">
				<input class="form-check-input" type="checkbox" id="agree" required />
				<label class="form-check-label small" for="agree"> I agree
					that the rental cost will be charged to this card. </label>
			</div>

			<button class="btn btn-success w-100">Start ride</button>
		</form>

	</div>

	<script>
    function chooseCar(el, uid, id, pps) {
      document
        .querySelectorAll('.car-card')
        .forEach(card => {
          if (card !== el) card.classList.add('d-none');
        });
      el.classList.add('border', 'border-primary');

      document.getElementById('vehicleUid').value = uid;
      document.getElementById('vehicleId').value = id;
      document.getElementById('pps').value = pps;
      document.getElementById('rentForm').classList.remove('d-none');

      if (!window.mapInit) initMap();
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    document
      .getElementById('card')
      .addEventListener('input', e => {
        e.target.value = e.target.value
          .replace(/\D/g, '')
          .replace(/(.{4})/g, '$1 ')
          .trim();
      });

    function initMap() {
      const defaultPos = [44.7725, 17.1910];
      const map = L.map('map', { center: defaultPos, zoom: 14 });
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '©OSM'
      }).addTo(map);

      const m = L.marker(defaultPos, { draggable: true }).addTo(map);
      const sx = document.getElementById('startX'),
        sy = document.getElementById('startY');
      const setLatLng = latlng => {
        sx.value = latlng.lng.toFixed(6);
        sy.value = latlng.lat.toFixed(6);
      };
      setLatLng(m.getLatLng());
      m.on('dragend', e => setLatLng(e.target.getLatLng()));

      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(pos => {
          const here = [pos.coords.latitude, pos.coords.longitude];
          map.setView(here, 15);
          m.setLatLng(here);
          setLatLng(m.getLatLng());
        });
      }
      setTimeout(() => map.invalidateSize(), 300);
      window.mapInit = true;
    }
  </script>

	<%@ include file="/includes/footer.jsp"%>
</body>
