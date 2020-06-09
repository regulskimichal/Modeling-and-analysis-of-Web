import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { PageSpeedSettingsComponent } from './page-speed-settings.component';

describe('PagespeedComponent', () => {
  let component: PageSpeedSettingsComponent;
  let fixture: ComponentFixture<PageSpeedSettingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [PageSpeedSettingsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PageSpeedSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
