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
import { InfoCellRenderComponent } from './components/station/info-cell-render/info-cell-render.component';
import { DialogInfoStationComponent } from './components/station/info-cell-render/dialog-info-station/dialog-info-station.component';
import { AddStationComponent } from './components/station/add-station/add-station.component';
import { AddJourneyComponent } from './components/journey/add-journey/add-journey.component';
import { MapStationComponent } from './components/station/info-cell-render/dialog-info-station/map-station/map-station.component';
import { JourneysComponent } from './components/station/info-cell-render/dialog-info-station/journeys/journeys.component';

export const routes: Routes = [
  {path: '', component: HomeComponent, title: 'Home'},
  {path: 'stations', component: StationComponent, title: 'Stations'},
  {path: 'journeys', component: JourneyComponent, title: 'Journeys'},
  {path: '**', redirectTo: ''}
];

@NgModule({
  declarations: [
    AppComponent,
    StationComponent,
    JourneyComponent,
    HomeComponent,
    InfoCellRenderComponent,
    DialogInfoStationComponent,
    AddStationComponent,
    AddJourneyComponent,
    MapStationComponent,
    JourneysComponent
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
