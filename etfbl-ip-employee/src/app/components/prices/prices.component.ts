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
  pricePerSecond:   number;
}

@Component({
  selector: 'app-prices',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './prices.component.html',
  styleUrl: './prices.component.scss'
})
export class PricesComponent implements OnInit {

  searchTerm = '';
  typeFilter = '';

  page       = 0;
  size       = 5;
  totalPages = 0;
  totalItems = 0;

  vehicles: VehicleListDto[] = [];

  showModal        = false;
  selectedVehicle?: VehicleListDto;
  priceInput       = 0;

  private vehiclesUrl  = 'http://localhost:8080/vehicles/for-breakdowns';
  private priceUrlBase = 'http://localhost:8080/vehicles';

  constructor(private http: HttpClient,
              private toast: ToastrService) {}

  ngOnInit() { this.refresh(); }

  refresh() {
    let params = new HttpParams()
      .set('page',  this.page)
      .set('size',  this.size)
      .set('search', this.searchTerm.trim());
    if (this.typeFilter) params = params.set('type', this.typeFilter);

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

  openModal(v: VehicleListDto) {
    this.selectedVehicle = v;

    this.priceInput = v.type === 'CAR'      ? v.pricePerSecond*86400 :
                      v.type === 'EBIKE'    ? v.pricePerSecond*3600  :
                      v.type === 'ESCOOTER' ? v.pricePerSecond*60    :
                                               v.pricePerSecond;
    this.showModal  = true;
  }
  closeModal() { this.showModal = false; }

  save() {
    if (!this.selectedVehicle) return;
    if (this.priceInput <= 0) {
      this.toast.error('Price must be positive'); return;
    }

    const perSecond = this.selectedVehicle.type === 'CAR'      ? this.priceInput/86400 :
                      this.selectedVehicle.type === 'EBIKE'    ? this.priceInput/3600  :
                      this.selectedVehicle.type === 'ESCOOTER' ? this.priceInput/60    :
                                                                 this.priceInput;

    this.http.put(`${this.priceUrlBase}/${this.selectedVehicle.id}/price`,
                    { pricePerSecond: perSecond }, { responseType:'text' })
      .subscribe({
        next: () => {
          this.toast.success('Price updated');
          this.closeModal();
          this.refresh();
        },
        error: err => this.toast.error(err.error || 'Error updating price')
      });
  }
}
