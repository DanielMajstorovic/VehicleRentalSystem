import {
  Component,
  OnInit,
  AfterViewInit,
} from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import * as L from 'leaflet';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Toast, ToastrService } from 'ngx-toastr';

interface VehicleMap {
  id: number;
  uid: string;
  model: string;
  manufacturerName: string;
  type: 'CAR' | 'EBIKE' | 'ESCOOTER';
  status: string;
  pricePerSecond: number;
  x: number;
  y: number;
}

@Component({
  selector: 'app-vehicles-map',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './vehicles-map.component.html',
  styleUrls: ['./vehicles-map.component.scss'],
})
export class VehiclesMapComponent implements OnInit, AfterViewInit {
  searchTerm = '';
  statuses = ['AVAILABLE', 'RENTED', 'BROKEN'];
  statusFilter = '';

  private map!: L.Map;
  private markers = L.layerGroup();
  private base = 'http://localhost:8080/vehicles/map';

  constructor(private http: HttpClient, private toastr: ToastrService) {}

  ngOnInit() {}

  ngAfterViewInit() {
    this.initMap();
    this.load();
  }

  initMap() {
    this.map = L.map('vehiclesMap').setView([44.7758, 17.1858], 13);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors'
    }).addTo(this.map);

    this.markers.addTo(this.map);

    const legendControl = L.Control.extend({
      onAdd: () => {
        const div = L.DomUtil.create('div', 'leaflet-control legend bg-white p-2 rounded shadow-sm');
        div.innerHTML = `
          <div><i class="bi bi-geo-alt-fill text-primary"></i> Car</div>
          <div><i class="bi bi-geo-alt-fill text-success"></i> E-Bike</div>
          <div><i class="bi bi-geo-alt-fill text-warning"></i> E-Scooter</div>
        `;
        return div;
      }
    });
    this.map.addControl(new legendControl({ position: 'bottomleft' }));
  }

  load() {
    let params = new HttpParams();
    if (this.searchTerm.trim()) {
      params = params.set('searchTerm', this.searchTerm.trim());
    }
    if (this.statusFilter) {
      params = params.set('status', this.statusFilter);
    }

    this.http.get<VehicleMap[]>(this.base, { params }).subscribe(list => {

      if(list.length === 0) {
        this.map.setView([44.7758, 17.1858], 13);
        this.markers.clearLayers();
        this.toastr.info('No vehicles found', 'Info', {
          positionClass: 'toast-bottom-right',
          timeOut: 3000,
          progressBar: true,
          closeButton: true
        });
      }

      this.markers.clearLayers();

      const bounds = L.latLngBounds([]);

      for (const v of list) {
        const colorClass = {
          CAR: 'text-primary',
          EBIKE: 'text-success',
          ESCOOTER: 'text-warning'
        }[v.type];

        const icon = L.divIcon({
          html: `<i class="bi bi-geo-alt-fill fs-3 ${colorClass}"></i>`,
          className: '',
          iconAnchor: [12, 36],
          iconSize: [24, 36],
          popupAnchor: [0, -36]
        });

        const position = L.latLng(v.y, v.x);
        const marker = L.marker(position, { icon })
          .bindPopup(this.makePopupHtml(v), { minWidth: 200 });

        marker.addTo(this.markers);
        bounds.extend(position);
      }

      if (list.length > 0) {
        this.map.fitBounds(bounds, { padding: [20, 20] });
      }
    });
  }

  private makePopupHtml(v: VehicleMap) {
    let priceLabel: string;
    if (v.type === 'CAR') {
      priceLabel = (v.pricePerSecond * 86400).toFixed(2) + ' $/day';
    } else if (v.type === 'EBIKE') {
      priceLabel = (v.pricePerSecond * 3600).toFixed(2) + ' $/h';
    } else {
      priceLabel = (v.pricePerSecond * 60).toFixed(2) + ' $/min';
    }

    const imgUrl = `http://localhost:8080/images/vehicles/${v.uid}.jpg`;
    let fallback = `assets/images/${v.type.toLowerCase()}.png`;
    if(v.type === 'CAR') {
      fallback = `assets/images/car.png`;
    } else if(v.type === 'EBIKE') {
      fallback = `assets/images/bike.png`;
    }
    else if(v.type === 'ESCOOTER') {
      fallback = `assets/images/scooter.png`;
    }

    return `
      <div style="text-align:center">
        <img src="${imgUrl}" onerror="this.src='${fallback}'"
             style="width:100px;height:auto;margin-bottom:0.5rem;border-radius:4px"/>
      </div>
      <div><strong>UID:</strong> ${v.uid}</div>
      <div><strong>Manufacturer:</strong> ${v.manufacturerName}</div>
      <div><strong>Model:</strong> ${v.model}</div>
      <div><strong>Price:</strong> ${priceLabel}</div>
      <div><strong>Status:</strong> ${v.status}</div>
      <div><strong>Vehicle type:</strong> ${v.type}</div>
    `;
  }
}
