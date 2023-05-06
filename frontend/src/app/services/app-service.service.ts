import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {IGetRowsParams} from "ag-grid-community";
import {GenericGrid} from "../models/genericGrid";
import {Station} from "../models/station";
import {Journey} from "../models/journey";
import {StationMarker} from "../models/stationMarker";
import {catchError, map, Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AppServiceService {

  constructor(private http: HttpClient) {
  }

  getStations(params: IGetRowsParams, page: number, size: number) {
    let emptyValue: GenericGrid<Station>;
    this.http.post<GenericGrid<Station>>("http://localhost:8181/stations", {
      page: page,
      size: size,
      sortModel: params.sortModel,
      filterModel: params.filterModel
    })
      .pipe(
        catchError(() => of(emptyValue))
      )
      .subscribe(data => {
        params.successCallback(data.records, data.totalRecords);
      });
  }

  getJourneys(params: IGetRowsParams, page: number, size: number) {
    let emptyValue: GenericGrid<Journey>;
    this.http.post<GenericGrid<Journey>>("http://localhost:8181/journeys", {
      page: page,
      size: size,
      sortModel: params.sortModel,
      filterModel: params.filterModel
    })
      .pipe(
        catchError(() => of(emptyValue))
      )
      .subscribe(data => {
        params.successCallback(data.records, data.totalRecords);
      });
  }

  getStationsGeo(): Observable<StationMarker | StationMarker[]> {
    let stationsGeo: StationMarker;
    return this.http.get<StationMarker[]>("http://localhost:8181/stationsGeo")
      .pipe(
        map((data: StationMarker[]) => data),
        catchError(() => of(stationsGeo))
      );
  }

  getJourneysTopDepart(params: IGetRowsParams, station: string) {
    let emptyValue: Journey[];
    this.http.get<Journey[]>("http://localhost:8181/top5Depart", {params: {stationId: station}})
      .pipe(
        catchError(() => of(emptyValue))
      )
      .subscribe(data => {
        params.successCallback(data, data.length);
      });
  }

  getJourneysTopReturn(params: IGetRowsParams, station: string) {
    let emptyValue: Journey[];
    this.http.get<Journey[]>("http://localhost:8181/top5Ret", {params: {stationId: station}})
      .pipe(
        catchError(() => of(emptyValue))
      )
      .subscribe(data => {
        params.successCallback(data, data.length);
      });
  }

  getJourneysAvg(station: string): Observable<string[]> {
    return this.http.get<string[]>("http://localhost:8181/avgDistance", {params: {stationId: station}})
      .pipe(
        catchError(() => of([]))
      );
  }

  saveStation(station: Station): Observable<any> {
    return this.http.post<any>("http://localhost:8181/savestation", station);
  }

  saveJourney(journey: Journey) {
    this.http.post<void>("http://localhost:8181/savejourney", journey).subscribe(() => {
    });
  }
}
