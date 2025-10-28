import { Component, OnInit, AfterViewChecked } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import * as L from 'leaflet';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface RentalSummary {
  id: number;
  clientUserUsername: string;
  clientUserFirstName: string;
  clientUserLastName: string;
  vehicleModel: string;
  vehicleUid: string;
  startTime: string;
  endTime?: string;
}

interface RentalDetails {
  id: number;
  startTime: string;
  startX: number;
  startY: number;
  endTime?: string;
  endX?: number;
  endY?: number;
  vehicleUid: string;
  vehicleModel: string;
  vehicleManufacturerName: string;
  clientUserUsername: string;
  clientUserFirstName: string;
  clientUserLastName: string;
  clientEmail: string;
  clientPhone: string;
  clientId: number;
  driversLicense: string;
  paymentCard: string;
  totalAmount: number;
}

@Component({
  selector: 'app-rentals',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './rentals.component.html',
  styleUrls: ['./rentals.component.scss'],
})
export class RentalsComponent implements OnInit, AfterViewChecked {
  data: RentalSummary[] = [];
  page = 0;
  size = 5;
  totalPages = 0;
  totalElements = 0;
  searchTerm = '';

  showDetails = false;
  selected!: RentalDetails;
  private mapInitialized = false;

  private base = 'http://localhost:8080/rentals';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.load();
  }

  load() {
    let params = new HttpParams().set('page', this.page).set('size', this.size);
    if (this.searchTerm.trim()) {
      params = params.set('searchTerm', this.searchTerm.trim());
    }
    this.http.get<any>(this.base, { params }).subscribe((r) => {
      this.data = r.content;
      this.totalPages = r.totalPages;
      this.totalElements = r.totalElements;
    });
  }

  onSearch() {
    this.page = 0;
    this.load();
  }
  go(p: number) {
    if (p < 0 || p >= this.totalPages) return;
    this.page = p;
    this.load();
  }

  openDetails(id: number) {
    this.http.get<RentalDetails>(`${this.base}/${id}`).subscribe((dto) => {
      this.selected = dto;
      this.showDetails = true;
      this.mapInitialized = false;
    });
  }
  closeDetails() {
    this.showDetails = false;
  }

  ngAfterViewChecked() {
    if (this.showDetails && !this.mapInitialized) {
      this.initMap();
      this.mapInitialized = true;
    }
  }

  private initMap() {
    const { startY, startX, endY, endX, endTime } = this.selected;
  
    const map = L.map('rentalMap');
  
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors',
    }).addTo(map);
  
    const startLatLng = L.latLng(startY, startX);
    const startIcon = L.divIcon({
      html: '<i class="bi bi-geo-alt-fill fs-3 text-success"></i>',
      className: '',
      iconAnchor: [12, 36],
      iconSize: [24, 36],
    });
    L.marker(startLatLng, { icon: startIcon }).addTo(map);
  
    const bounds = L.latLngBounds([startLatLng]);
  
    if (endTime && endX !== undefined && endY !== undefined) {
      const endLatLng = L.latLng(endY, endX);
      const endIcon = L.divIcon({
        html: '<i class="bi bi-geo-alt-fill fs-3 text-danger"></i>',
        className: '',
        iconAnchor: [12, 36],
        iconSize: [24, 36],
      });
      L.marker(endLatLng, { icon: endIcon }).addTo(map);
  
      bounds.extend(endLatLng);
    }
  
    map.fitBounds(bounds, { padding: [20, 20] });
  
    const legend = new L.Control({ position: 'bottomleft' });
    legend.onAdd = function () {
      const div = L.DomUtil.create('div', 'legend bg-white p-2 rounded shadow-sm');
      div.innerHTML = `
        <div><i class="bi bi-geo-alt-fill text-success me-1"></i> Start</div>
        ${
          endTime
            ? '<div><i class="bi bi-geo-alt-fill text-danger me-1"></i> End</div>'
            : ''
        }
      `;
      return div;
    };
    legend.addTo(map);
  }
  
}
