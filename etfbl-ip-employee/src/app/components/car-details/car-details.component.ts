import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  AfterViewInit,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpParams } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';
import * as L from 'leaflet';

interface CarDto {
  id: number;
  vehicleId: number;
  vehicleUid: string;
  vehiclePurchasePrice: number;
  vehicleModel: string;
  vehicleManufacturerId: number;
  vehicleManufacturerName: string;
  vehicleManufacturerCountry: string;
  vehicleStatus: string;
  vehiclePricePerSecond: number;
  vehicleX: number;
  vehicleY: number;
  purchaseDate: string;
  description: string;
}

interface BreakdownDto {
  id: number;
  description: string;
  breakdownTime: string;
  repairTime?: string;
}

interface RentalDto {
  id: number;
  startTime: string;
  startX: number;
  startY: number;
  endTime?: string;
  endX?: number;
  endY?: number;
  duration?: number;
  driversLicense?: string;
  paymentCard?: string;
  totalAmount?: number;
  clientUserFirstName?: string;
  clientUserLastName?: string;
}

@Component({
  selector: 'app-car-details',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './car-details.component.html',
  styleUrls: ['./car-details.component.scss'],
})
export class CarDetailsComponent implements OnInit, AfterViewInit {
  carId!: number;

  car: CarDto | null = null;

  vehicleImageUrl: string = '';

  rentals: RentalDto[] = [];
  rentalsSearchTerm: string = '';
  rentalsCurrentPage = 0;
  rentalsPageSize = 5;
  rentalsTotalElements = 0;
  rentalsTotalPages = 0;

  breakdowns: BreakdownDto[] = [];
  breakdownsSearchTerm: string = '';
  breakdownsCurrentPage = 0;
  breakdownsPageSize = 5;
  breakdownsTotalElements = 0;
  breakdownsTotalPages = 0;

  showAddBreakdownModal = false;
  newBreakdownDescription = '';

  @ViewChild('map', { static: false }) mapElement!: ElementRef;
  leafletMap!: L.Map;
  marker!: L.Marker;

  private baseUrl = 'http://localhost:8080/vehicles';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.carId = +this.route.snapshot.paramMap.get('id')!;
    this.loadCarDetails();
  }

  ngAfterViewInit(): void {}

  loadCarDetails() {
    this.http.get<CarDto>(`${this.baseUrl}/cars/${this.carId}`).subscribe({
      next: (carData) => {
        this.car = carData;
        this.setupCarImageUrl();

        this.loadRentals();
        this.loadBreakdowns();

        setTimeout(() => {
          this.initLeafletMap();
        }, 0);
      },
      error: (err) => {
        this.toastr.error('Error loading car details.');
        console.error(err);
      },
    });
  }

  setupCarImageUrl() {
    if (!this.car) return;
    const uid = this.car.vehicleUid;

    this.vehicleImageUrl = `http://localhost:8080/images/vehicles/${uid}.jpg`;
  }

  onImageError(event: any) {
    event.target.src = 'assets/images/car.png';
  }

  initLeafletMap() {
    if (!this.car) return;

    if (this.leafletMap) {
      this.leafletMap.remove();
    }

    const lat = this.car.vehicleY || 44.7665;
    const lng = this.car.vehicleX || 17.187;
    const zoom = 15;

    this.leafletMap = L.map(this.mapElement.nativeElement).setView(
      [lat, lng],
      zoom
    );

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution:
        'Map data © <a href="https://openstreetmap.org">OpenStreetMap</a> contributors',
    }).addTo(this.leafletMap);

    const customIcon = L.icon({
      iconUrl: 'assets/images/pin.png',
      iconSize: [70, 70],
      iconAnchor: [35, 70],
    });

    this.marker = L.marker([lat, lng], {
      draggable: false,
      icon: customIcon,
    }).addTo(this.leafletMap);
  }

  loadRentals() {
    if (!this.car) return;
    let params = new HttpParams()
      .set('page', this.rentalsCurrentPage)
      .set('size', this.rentalsPageSize);
    if (this.rentalsSearchTerm && this.rentalsSearchTerm.trim() !== '') {
      params = params.set('searchTerm', this.rentalsSearchTerm.trim());
    }

    this.http
      .get<any>(`${this.baseUrl}/${this.car.id}/rentals`, { params })
      .subscribe({
        next: (response) => {
          this.rentals = response.content;
          this.rentalsTotalElements = response.totalElements;
          this.rentalsTotalPages = response.totalPages;
        },
        error: (err) => {
          this.toastr.error('Error loading rentals');
          console.error(err);
        },
      });
  }

  onSearchRentals() {
    this.rentalsCurrentPage = 0;
    this.loadRentals();
  }

  goToRentalsPage(page: number) {
    if (page < 0 || page >= this.rentalsTotalPages) return;
    this.rentalsCurrentPage = page;
    this.loadRentals();
  }

  loadBreakdowns() {
    if (!this.car) return;
    let params = new HttpParams()
      .set('page', this.breakdownsCurrentPage)
      .set('size', this.breakdownsPageSize);
    if (this.breakdownsSearchTerm && this.breakdownsSearchTerm.trim() !== '') {
      params = params.set('searchTerm', this.breakdownsSearchTerm.trim());
    }

    this.http
      .get<any>(`${this.baseUrl}/${this.car.id}/breakdowns`, { params })
      .subscribe({
        next: (response) => {
          this.breakdowns = response.content;
          this.breakdownsTotalElements = response.totalElements;
          this.breakdownsTotalPages = response.totalPages;
        },
        error: (err) => {
          this.toastr.error('Error loading breakdowns');
          console.error(err);
        },
      });
  }

  onSearchBreakdowns() {
    this.breakdownsCurrentPage = 0;
    this.loadBreakdowns();
  }

  goToBreakdownsPage(page: number) {
    if (page < 0 || page >= this.breakdownsTotalPages) return;
    this.breakdownsCurrentPage = page;
    this.loadBreakdowns();
  }

  openAddBreakdownModal() {
    this.newBreakdownDescription = '';
    this.showAddBreakdownModal = true;
  }

  closeAddBreakdownModal() {
    this.showAddBreakdownModal = false;
  }

  onSaveBreakdown() {
    if (!this.newBreakdownDescription.trim()) {
      this.toastr.error('Description is required!');
      return;
    }
    if (!this.car) return;

    const payload = {
      description: this.newBreakdownDescription,
      vehicleId: this.car.id,
    };

    this.http
      .post(`${this.baseUrl}/breakdowns`, payload, { responseType: 'text' })
      .subscribe({
        next: (res: any) => {
          this.toastr.success(res || 'Breakdown added successfully!');
          this.showAddBreakdownModal = false;

          this.loadBreakdowns();
          this.loadCarDetails();
        },
        error: (err) => {
          this.toastr.error(err.error || 'Error creating breakdown.');
        },
      });
  }

  onDeleteBreakdown(breakdown: BreakdownDto) {
    Swal.fire({
      title: 'Delete breakdown?',
      text: `Description: ${breakdown.description}`,
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
      buttonsStyling: false,
      customClass: {
        popup: 'rounded border',
        confirmButton: 'btn btn-outline-danger me-2',
        cancelButton: 'btn btn-outline-secondary',
      },
      backdrop: false,
      allowOutsideClick: false,
    }).then((result) => {
      if (!result.isConfirmed) return;

      this.http
        .delete(`${this.baseUrl}/breakdowns/${breakdown.id}`, {
          responseType: 'text',
        })
        .subscribe({
          next: (res: any) => {
            this.toastr.success(res || 'Breakdown deleted!');

            this.loadBreakdowns();
            this.loadCarDetails();
          },
          error: (err) => {
            this.toastr.error(err.error || 'Error deleting breakdown.');
          },
        });
    });
  }

  onRepairBreakdown(breakdown: BreakdownDto) {
    Swal.fire({
      title: 'Mark as repaired?',
      text: `Description: ${breakdown.description}`,
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Repair',
      cancelButtonText: 'Cancel',
      buttonsStyling: false,
      customClass: {
        popup: 'rounded border',
        confirmButton: 'btn btn-outline-primary me-2',
        cancelButton: 'btn btn-outline-secondary',
      },
      backdrop: false,
      allowOutsideClick: false,
    }).then((result) => {
      if (!result.isConfirmed) return;

      this.http
        .post(
          `${this.baseUrl}/breakdowns/${breakdown.id}/repair`,
          {},
          { responseType: 'text' }
        )
        .subscribe({
          next: (res: any) => {
            this.toastr.success(res || 'Breakdown repaired!');
            this.loadBreakdowns();
            this.loadCarDetails();
          },
          error: (err) => {
            this.toastr.error(err.error || 'Error repairing breakdown.');
          },
        });
    });
  }

  onBackToCars() {
    this.router.navigate(['/administrator/cars']);
  }
}
