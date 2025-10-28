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

interface EScooterDto {
  id: number;
  vehicleId: number;
  vehicleUid: string;
  vehiclePurchasePrice: number;
  vehicleModel: string;
  vehicleManufacturerName: string;
  vehicleManufacturerCountry: string;
  vehicleStatus: string;
  vehiclePricePerSecond: number;
  vehicleX: number;
  vehicleY: number;
  maxSpeed: number;
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
  endTime?: string;
  driversLicense?: string;
  paymentCard?: string;
  totalAmount?: number;
  clientUserFirstName?: string;
  clientUserLastName?: string;
}

@Component({
  selector: 'app-escooter-details-manager',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './escooter-details-manager.component.html',
  styleUrl: './escooter-details-manager.component.scss'
})
export class EscooterDetailsManagerComponent implements OnInit, AfterViewInit {
  scooterId!: number;
  scooter: EScooterDto | null = null;
  imageUrl = '';

  rentals: RentalDto[] = [];
  rentalsSearch = '';
  rentalsCur = 0;
  rentalsSize = 5;
  rentalsTotal = 0;
  rentalsPages = 0;
  breakdowns: BreakdownDto[] = [];
  brSearch = '';
  brCur = 0;
  brSize = 5;
  brTotal = 0;
  brPages = 0;

  showAddBreakModal = false;
  newBreakDesc = '';

  @ViewChild('map', { static: false }) mapEl!: ElementRef;
  map!: L.Map;
  marker!: L.Marker;

  private baseUrl = 'http://localhost:8080/vehicles';
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private toast: ToastrService
  ) {}

  ngOnInit() {
    this.scooterId = +this.route.snapshot.paramMap.get('id')!;
    this.loadScooter();
  }
  ngAfterViewInit() {}

  loadScooter() {
    this.http
      .get<EScooterDto>(`${this.baseUrl}/eScooters/${this.scooterId}`)
      .subscribe({
        next: (s) => {
          this.scooter = s;
          this.imageUrl = `http://localhost:8080/images/vehicles/${s.vehicleUid}.jpg`;
          this.loadRentals();
          this.loadBreakdowns();
          setTimeout(() => this.initMap());
        },
        error: (_) => this.toast.error('Error loading e‑scooter.'),
      });
  }

  initMap() {
    if (!this.scooter) return;
    if (this.map) this.map.remove();
    this.map = L.map(this.mapEl.nativeElement).setView(
      [this.scooter.vehicleY || 44.7665, this.scooter.vehicleX || 17.187],
      15
    );
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap',
    }).addTo(this.map);
    const icon = L.icon({
      iconUrl: 'assets/images/pin.png',
      iconSize: [70, 70],
      iconAnchor: [35, 70],
    });
    this.marker = L.marker([this.scooter.vehicleY, this.scooter.vehicleX], {
      icon,
    }).addTo(this.map);
  }

  loadRentals() {
    if (!this.scooter) return;
    let params = new HttpParams()
      .set('page', this.rentalsCur)
      .set('size', this.rentalsSize);
    if (this.rentalsSearch.trim())
      params = params.set('searchTerm', this.rentalsSearch.trim());
    this.http
      .get<any>(`${this.baseUrl}/${this.scooter.id}/rentals`, { params })
      .subscribe({
        next: (r) => {
          this.rentals = r.content;
          this.rentalsTotal = r.totalElements;
          this.rentalsPages = r.totalPages;
        },
        error: (_) => this.toast.error('Error loading rentals'),
      });
  }
  onSearchRentals() {
    this.rentalsCur = 0;
    this.loadRentals();
  }
  goToRentalsPage(p: number) {
    if (p < 0 || p >= this.rentalsPages) return;
    this.rentalsCur = p;
    this.loadRentals();
  }

  loadBreakdowns() {
    if (!this.scooter) return;
    let params = new HttpParams()
      .set('page', this.brCur)
      .set('size', this.brSize);
    if (this.brSearch.trim())
      params = params.set('searchTerm', this.brSearch.trim());
    this.http
      .get<any>(`${this.baseUrl}/${this.scooter.id}/breakdowns`, { params })
      .subscribe({
        next: (r) => {
          this.breakdowns = r.content;
          this.brTotal = r.totalElements;
          this.brPages = r.totalPages;
        },
        error: (_) => this.toast.error('Error loading breakdowns'),
      });
  }
  onSearchBreakdowns() {
    this.brCur = 0;
    this.loadBreakdowns();
  }
  goToBreakdownsPage(p: number) {
    if (p < 0 || p >= this.brPages) return;
    this.brCur = p;
    this.loadBreakdowns();
  }

  openAddBreakModal() {
    this.newBreakDesc = '';
    this.showAddBreakModal = true;
  }
  closeAddBreakModal() {
    this.showAddBreakModal = false;
  }

  onSaveBreakdown() {
    if (!this.newBreakDesc.trim()) {
      this.toast.error('Description required');
      return;
    }
    if (!this.scooter) return;
    const payload = {
      description: this.newBreakDesc,
      vehicleId: this.scooter.id,
    };
    this.http
      .post(`${this.baseUrl}/breakdowns`, payload, { responseType: 'text' })
      .subscribe({
        next: (m) => {
          this.toast.success(m || 'Breakdown added');
          this.showAddBreakModal = false;
          this.loadBreakdowns();
          this.loadScooter();
        },
        error: (e) => this.toast.error(e.error || 'Error adding breakdown'),
      });
  }

  onDeleteBreakdown(b: BreakdownDto) {
    Swal.fire({
      title: 'Delete breakdown?',
      text: b.description,
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
    }).then((res) => {
      if (!res.isConfirmed) return;
      this.http
        .delete(`${this.baseUrl}/breakdowns/${b.id}`, { responseType: 'text' })
        .subscribe({
          next: (m) => {
            this.toast.success(m || 'Deleted');
            this.loadBreakdowns();
            this.loadScooter();
          },
          error: (e) => this.toast.error(e.error || 'Delete failed'),
        });
    });
  }

  onRepairBreakdown(b: BreakdownDto) {
    Swal.fire({
      title: 'Mark as repaired?',
      text: b.description,
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
    }).then((res) => {
      if (!res.isConfirmed) return;
      this.http
        .post(
          `${this.baseUrl}/breakdowns/${b.id}/repair`,
          {},
          { responseType: 'text' }
        )
        .subscribe({
          next: (m) => {
            this.toast.success(m || 'Repaired');
            this.loadBreakdowns();
            this.loadScooter();
          },
          error: (e) => this.toast.error(e.error || 'Repair failed'),
        });
    });
  }

  onImageError(event: Event): void {
    const target = event.target as HTMLImageElement;
    target.src = 'assets/images/scooter.png';
  }

  onBack() {
    this.router.navigate(['/manager/escooters-manager']);
  }
}
