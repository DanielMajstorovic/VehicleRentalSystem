import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  AfterViewInit
} from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

import * as L from 'leaflet';
import { Router } from '@angular/router';

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

interface ManufacturerShort {
  id: number;
  name: string;
  country: string;
}

@Component({
  selector: 'app-cars',
  templateUrl: './cars.component.html',
  styleUrls: ['./cars.component.scss'],
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  standalone: true
})
export class CarsComponent implements OnInit, AfterViewInit {
  cars: CarDto[] = [];

  currentPage = 0;
  pageSize = 5;
  totalElements = 0;
  totalPages = 0;

  searchTerm: string = '';

  showCreateModal = false;
  createCarForm: FormGroup;
  selectedImageFile: File | null = null;
  imageError: string | null = null;

  manufacturers: ManufacturerShort[] = [];

  @ViewChild('map', { static: false }) mapElement!: ElementRef;
  leafletMap!: L.Map;
  marker!: L.Marker;

  defaultLat = 44.7665;
  defaultLng = 17.187;
  defaultZoom = 15;

  private baseUrl = 'http://localhost:8080/vehicles';
  private manufacturerUrl = 'http://localhost:8080/manufacturers/short';

  constructor(
    private http: HttpClient,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private router: Router
  ) {
    this.createCarForm = this.fb.group({
      vehicleUid: ['', Validators.required],
      vehicleModel: ['', Validators.required],
      vehicleManufacturerId: ['', Validators.required],
      vehiclePurchasePrice: ['', Validators.required],
      vehiclePricePerDay: ['', Validators.required],
      purchaseDate: ['', Validators.required],
      description: ['', Validators.required],

      vehicleY: [this.defaultLat, Validators.required],
      vehicleX: [this.defaultLng, Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadCars();
    this.loadManufacturers();
  }

  ngAfterViewInit(): void {
  }

  loadCars() {
    let params = new HttpParams()
      .set('page', this.currentPage)
      .set('size', this.pageSize);
    
    if (this.searchTerm && this.searchTerm.trim() !== '') {
      params = params.set('searchTerm', this.searchTerm.trim());
    }

    this.http.get<any>(`${this.baseUrl}/cars`, { params })
      .subscribe({
        next: (response) => {
          this.cars = response.content;
          this.totalElements = response.totalElements;
          this.totalPages = response.totalPages;
        },
        error: (err) => {
          console.error('Error while fetching cars:', err);
          this.toastr.error('Error while fetching cars list.');
        }
      });
  }

  loadManufacturers() {
    this.http.get<ManufacturerShort[]>(this.manufacturerUrl).subscribe({
      next: (list) => {
        this.manufacturers = list; 
      },
      error: (err) => {
        console.error('Error loading manufacturers:', err);
        this.toastr.error('Failed to load manufacturers.');
      }
    });
  }

  goToPage(page: number) {
    if (page < 0 || page >= this.totalPages) {
      return;
    }
    this.currentPage = page;
    this.loadCars();
  }

  onSearch() {
    this.currentPage = 0; 
    this.loadCars();
  }

  onCsvFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append('file', file);

    this.http.post(`${this.baseUrl}/uploadCSV`, formData, { responseType: 'text' })
      .subscribe({
        next: (response: any) => {
          this.toastr.info(
            (response || 'CSV uploaded successfully!').replace(/\n/g, '<br>'),
            'Success!',
            {
              timeOut: 10000,
              closeButton: true,
              extendedTimeOut: 0,
              tapToDismiss: false,
              enableHtml: true,
              positionClass: 'toast-bottom-right',
            }
          );
          this.loadCars();
        },
        error: (err) => {
          if (err.error && typeof err.error === 'string') {
            this.toastr.error(err.error);
          } else {
            this.toastr.error('Error uploading CSV file.');
          }
        }
      });
  }

  openCreateCarModal() {
    this.showCreateModal = true;
    this.createCarForm.reset({
      vehicleY: this.defaultLat,
      vehicleX: this.defaultLng
    });
    this.selectedImageFile = null;
    this.imageError = null;

    setTimeout(() => {
      this.initLeafletMap();
    }, 0);
  }
  
  closeCreateCarModal() {
    this.showCreateModal = false;
  }

  initLeafletMap() {
    if (this.leafletMap) {
      this.leafletMap.remove();
    }

    this.leafletMap = L.map(this.mapElement.nativeElement).setView(
      [this.defaultLat, this.defaultLng],
      this.defaultZoom
    );

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution:
        'Map data © <a href="https://openstreetmap.org">OpenStreetMap</a> contributors'
    }).addTo(this.leafletMap);

    const customIcon = L.icon({
      iconUrl: 'assets/images/pin.png',
      iconSize: [50, 50], 
      iconAnchor: [25, 50],     
    });
  

    this.marker = L.marker([this.defaultLat, this.defaultLng], { 
      draggable: true,
      icon: customIcon 
     }).addTo(this.leafletMap);

    this.marker.on('dragend', () => {
      const latlng = this.marker.getLatLng();
      this.createCarForm.patchValue({
        vehicleY: latlng.lat,
        vehicleX: latlng.lng
      });
    });
  }

  onImageSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedImageFile = file;
      this.imageError = null;
    }
  }

  isInvalidField(fieldName: string): boolean {
    const field = this.createCarForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  onCreateCar() {
    if (!this.createCarForm.valid) {
      this.createCarForm.markAllAsTouched();
      return;
    }
    if (!this.selectedImageFile) {
      this.imageError = 'Image is required.';
      return;
    }

    const pricePerDay = this.createCarForm.value.vehiclePricePerDay;
    const pricePerSecond = pricePerDay / 86400; 

    const formData = new FormData();

    const carData = {
      vehicleUid: this.createCarForm.value.vehicleUid,
      vehiclePurchasePrice: this.createCarForm.value.vehiclePurchasePrice,
      vehicleModel: this.createCarForm.value.vehicleModel,
      vehicleManufacturerId: this.createCarForm.value.vehicleManufacturerId,
      vehiclePricePerSecond: pricePerSecond,

      vehicleX: this.createCarForm.value.vehicleX,
      vehicleY: this.createCarForm.value.vehicleY,
      purchaseDate: this.createCarForm.value.purchaseDate,
      description: this.createCarForm.value.description
    };
    
    formData.append('carData', new Blob([JSON.stringify(carData)], { type: 'application/json' }));
    formData.append('image', this.selectedImageFile);

    this.http.post(`${this.baseUrl}/createCar`, formData)
      .subscribe({
        next: (res: any) => {
          this.toastr.success('Car created successfully!');
          this.loadCars();
          this.closeCreateCarModal();
        },
        error: (err) => {
          if(err.status === 500) {
            this.toastr.error('Car with this UID already exists!');
            this.createCarForm.get('vehicleUid')?.setErrors({ duplicate: true });
            return;
          }
          
          if (err.error && typeof err.error === 'string') {
            this.toastr.error(err.error);
          } else {
            this.toastr.error('Error creating car.');
          }
        }
      });
  }

  onViewCarDetails(carId: number) {
    
    this.router.navigate(['/administrator/car-details', carId]);
  }

  onDeleteCar(carId: number, carUid: string) {
    Swal.fire({
      title: 'Delete ' + carUid + '?',
      text: 'This action cannot be undone.',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
      buttonsStyling: false,
      customClass: {
        popup: 'rounded border',
        title: 'fs-5',
        confirmButton: 'btn btn-outline-danger me-2',
        cancelButton: 'btn btn-outline-secondary'
      },
      backdrop: false,
      allowOutsideClick: false,
      allowEscapeKey: true,
    }).then(result => {
      if (!result.isConfirmed) return;

      this.http.delete(`${this.baseUrl}/${carId}`, { responseType: 'text' })
        .subscribe({
          next: (res: any) => {
            this.toastr.success(res || 'Car deleted successfully!');
            this.loadCars();
          },
          error: (err) => {
            if (err.error && typeof err.error === 'string') {
              this.toastr.error(err.error);
            } else {
              this.toastr.error('Error deleting car.');
            }
          }
        });
    });
  }
}
