import { Component, OnInit } from '@angular/core';
import { CommonModule }  from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';

interface VehicleListDto {
  id:               number;
  uid:              string;
  model:            string;
  manufacturerName: string;
  type:             'CAR' | 'EBIKE' | 'ESCOOTER' | 'UNKNOWN';
  status:           'AVAILABLE' | 'RENTED' | 'BROKEN';
  pricePerSecond: number;
}

@Component({
  selector: 'app-breakdown-entry',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './breakdown-entry.component.html',
  styleUrls: ['./breakdown-entry.component.scss']
})
export class BreakdownEntryComponent implements OnInit {

  
  searchTerm   = '';
  typeFilter   = '';

  page         = 0;
  size         = 5;
  totalPages   = 0;
  totalItems   = 0;

  vehicles: VehicleListDto[] = [];

  showModal         = false;
  selectedVehicle?: VehicleListDto;
  breakdownDesc     = '';

  private vehiclesUrl   = 'http://localhost:8080/vehicles/for-breakdowns';
  private breakdownUrl  = 'http://localhost:8080/breakdowns';

  constructor(private http: HttpClient,
              private toast: ToastrService) {}

  ngOnInit() { this.refresh(); }

  refresh() {
    let params = new HttpParams()
      .set('page',  this.page)
      .set('size',  this.size)
      .set('search', this.searchTerm.trim());
    if (this.typeFilter) { params = params.set('type', this.typeFilter); }

    this.http.get<any>(this.vehiclesUrl, { params })
      .subscribe({
        next: res => {
          this.vehicles   = res.content;
          this.totalPages = res.totalPages;
          this.totalItems = res.totalElements;
        },
        error: () => this.toast.error('Error loading vehicles')
      });
  }

  changePage(p: number) {
    if (p<0 || p>=this.totalPages) return;
    this.page = p;
    this.refresh();
  }

  clearFilters() {
    this.searchTerm = '';
    this.typeFilter = '';
    this.page       = 0;
    this.refresh();
  }

  openModal(v: VehicleListDto) {
    this.selectedVehicle = v;
    this.breakdownDesc   = '';
    this.showModal       = true;
  }
  closeModal()  { this.showModal = false; }

  save() {
    if (!this.breakdownDesc.trim() || !this.selectedVehicle) {
      this.toast.error('Description is required'); return;
    }
    const payload = {
      description: this.breakdownDesc,
      vehicleId:   this.selectedVehicle.id
    };
    this.http.post(this.breakdownUrl, payload, { responseType:'text' })
      .subscribe({
        next: () => {
          this.toast.success('Breakdown saved');
          this.closeModal();
          this.refresh();
        },
        error: err => this.toast.error(err.error || 'Error saving breakdown')
      });
  }
}
