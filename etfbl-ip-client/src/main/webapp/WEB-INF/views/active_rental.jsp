<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/includes/head.jsp"%>

<link
  rel="stylesheet"
  href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"
  integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY="
  crossorigin=""
/>
<script
  src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"
  integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo="
  crossorigin=""
></script>

<style>
  #map {
    height: 250px;
    width: 100%;
    border-radius: 8px;
    overflow: hidden;
  }
  .vehicle-img {
    max-height: 120px;
    width: 100%;
    object-fit: contain;
  }
</style>

<body class="client-body">
  <%@ include file="/includes/header.jsp"%>

  <div class="container py-3">
    <h4 class="mb-3 text-center">Active ride</h4>
    <div class="card p-3 shadow-sm">
      <p class="text-center">
        <img
          src="http://localhost:8080/images/vehicles/${activeRental.vehicleUid}.jpg"
          alt="vehicle"
          class="vehicle-img mb-2"
          onerror="this.onerror=null;
                   this.src='${pageContext.request.contextPath}/assets/images/etf_icon.png';"
        />
      </p>
      <p><strong>Vehicle:</strong> ${activeRental.vehicleUid} – ${activeRental.vehicleModel}</p>
      <p><strong>Started:</strong> ${activeRental.startTime}</p>
      <p><strong>Elapsed:</strong> <span id="elapsed">--</span></p>
      <p><strong>Total:</strong> <span id="total">$0.00</span></p>

      <label class="fw-semibold mt-3 small">Select your final location:</label>
      <div id="map" class="mb-3"></div>

      <form id="finishForm" action="active-rental" method="post">
        <input type="hidden" name="rentalId"   value="${activeRental.id}">
        <input type="hidden" name="vehicleUid" value="${activeRental.vehicleUid}">
        <input type="hidden" name="clientId"   value="${sessionScope.client.id}">
        <input type="hidden" name="pricePerSec" value="${activeRental.pricePerSecond}">
        <input type="hidden" name="startIso"    value="${activeRental.startTime.toInstant()}">
        <input type="hidden" name="endX" id="endX">
        <input type="hidden" name="endY" id="endY">
        <button type="submit" class="btn btn-success w-100">
          <i class="bi bi-flag-fill me-2"></i> Finish ride &amp; download receipt
        </button>
      </form>
    </div>
  </div>

  <script>
    (function(){
      const price = parseFloat("${activeRental.pricePerSecond}");
      const start = new Date("${activeRental.startTime}".replace(' ','T'));
      const elE   = document.getElementById('elapsed');
      const elT   = document.getElementById('total');
      function pad(n){return n.toString().padStart(2,'0');}
      function tick(){
        const diff = Math.floor((Date.now()-start)/1000);
        const h = Math.floor(diff/3600),
              m = Math.floor(diff/60)%60,
              s = diff%60;
        elE.textContent = pad(h)+'h '+pad(m)+'m '+pad(s)+'s';
        elT.textContent = '$'+(diff*price).toFixed(2);
      }
      tick(); setInterval(tick,1000);
    })();

    (function(){
      const defaultPos = [44.7725,17.1910];
      const map = L.map('map',{center:defaultPos,zoom:14});
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
                  {maxZoom:19, attribution:'©OSM'}).addTo(map);
      let marker = L.marker(defaultPos,{draggable:true}).addTo(map);

      const endX = document.getElementById('endX'),
            endY = document.getElementById('endY');
      function setHidden(ll){
        endX.value = ll.lng.toFixed(6);
        endY.value = ll.lat.toFixed(6);
      }
      setHidden(marker.getLatLng());
      marker.on('dragend', e => setHidden(e.target.getLatLng()));

      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(pos => {
          const here = [pos.coords.latitude, pos.coords.longitude];
          map.setView(here, 15);
          marker.setLatLng(here);
          setHidden(marker.getLatLng());
        });
      }

      setTimeout(()=> map.invalidateSize(), 1150);
    })();

    document.getElementById('finishForm')
      .addEventListener('submit', async function(e){
        e.preventDefault();
        const form = e.target,
              data = new FormData(form);

        const resp = await fetch(form.action, {
          method: 'POST',
          body: data
        });
        if (!resp.ok) {
          alert('Error while finishing drive!');
          return;
        }

        const blob = await resp.blob(),
              url  = URL.createObjectURL(blob),
              a    = document.createElement('a');
        a.href     = url;
        
        const now = new Date();
        const ts = now.toLocaleString('sv-SE').replace(/:/g, '-').replace(' ', '_');
        const titlep = 'invoice_for_rental_'+ts+'.pdf';
        a.download = titlep;

        
        document.body.appendChild(a);
        a.click();
        URL.revokeObjectURL(url);

        window.location.href = '${pageContext.request.contextPath}/home';
      });
  </script>

  <%@ include file="/includes/footer.jsp"%>
</body>
