import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EbikeDetailsManagerComponent } from './ebike-details-manager.component';

describe('EbikeDetailsManagerComponent', () => {
  let component: EbikeDetailsManagerComponent;
  let fixture: ComponentFixture<EbikeDetailsManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EbikeDetailsManagerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EbikeDetailsManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
