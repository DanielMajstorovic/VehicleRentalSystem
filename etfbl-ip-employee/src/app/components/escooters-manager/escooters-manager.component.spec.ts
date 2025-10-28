import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EscootersManagerComponent } from './escooters-manager.component';

describe('EscootersManagerComponent', () => {
  let component: EscootersManagerComponent;
  let fixture: ComponentFixture<EscootersManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EscootersManagerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EscootersManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
