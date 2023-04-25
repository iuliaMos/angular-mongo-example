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
    this.http.post<GenericGrid<Station>>("http://localhost:8080/stations", {
      page: page,
      size: size,
      sortModel: params.sortModel,
      filterModel: params.filterModel
    })
      .subscribe(data => {
        params.successCallback(data.records, data.totalRecords);
      });
  }

  getJourneys(params: IGetRowsParams, page: number, size: number) {
    this.http.post<GenericGrid<Journey>>("http://localhost:8080/journeys", {
      page: page,
      size: size,
      sortModel: params.sortModel,
      filterModel: params.filterModel
    })
      .subscribe(data => {
        params.successCallback(data.records, data.totalRecords);
      });
  }

  getStationsGeo(): Observable<StationMarker | StationMarker[]> {
    let stationsGeo: StationMarker;
    return this.http.get<StationMarker[]>("http://localhost:8080/stationsGeo")
      .pipe(
        map((data: StationMarker[]) => data),
        catchError(() => of(stationsGeo))
      );
  }
}
