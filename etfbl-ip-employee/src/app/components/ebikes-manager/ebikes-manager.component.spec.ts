import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EbikesManagerComponent } from './ebikes-manager.component';

describe('EbikesManagerComponent', () => {
  let component: EbikesManagerComponent;
  let fixture: ComponentFixture<EbikesManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EbikesManagerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EbikesManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
