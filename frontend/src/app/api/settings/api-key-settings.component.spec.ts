import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApiKeySettingsComponent} from './api-key-settings.component';

describe('ApiKeySettingsComponent', () => {
  let component: ApiKeySettingsComponent;
  let fixture: ComponentFixture<ApiKeySettingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApiKeySettingsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApiKeySettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
