import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {MaterialModule} from './material.module';

import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatNativeDateModule} from "@angular/material/core";
import {HttpClientModule} from "@angular/common/http";
import {AgGridModule} from "ag-grid-angular";
import {CommonModule} from "@angular/common";
import {StationComponent} from './components/station/station.component';
import {JourneyComponent} from './components/journey/journey.component';
import {HomeComponent} from './components/home/home.component';
import {RouterModule, Routes} from "@angular/router";

export const routes: Routes = [
  {path: '', component: HomeComponent, title: 'Home'},
  {path: 'stations', component: StationComponent, title: 'Stations'},
  {path: 'journeys', component: JourneyComponent, title: 'Journeys'}
];

@NgModule({
  declarations: [
    AppComponent,
    StationComponent,
    JourneyComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    CommonModule,
    HttpClientModule,
    MatNativeDateModule,
    MaterialModule,
    ReactiveFormsModule,
    AgGridModule,
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
