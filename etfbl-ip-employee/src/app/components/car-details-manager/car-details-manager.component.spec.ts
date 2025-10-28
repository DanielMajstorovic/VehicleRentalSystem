import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarDetailsManagerComponent } from './car-details-manager.component';

describe('CarDetailsManagerComponent', () => {
  let component: CarDetailsManagerComponent;
  let fixture: ComponentFixture<CarDetailsManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarDetailsManagerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CarDetailsManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
