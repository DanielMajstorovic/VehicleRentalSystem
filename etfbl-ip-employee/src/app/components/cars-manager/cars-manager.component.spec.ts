import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarsManagerComponent } from './cars-manager.component';

describe('CarsManagerComponent', () => {
  let component: CarsManagerComponent;
  let fixture: ComponentFixture<CarsManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarsManagerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CarsManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
