import {Component, Input, OnInit} from '@angular/core';
import {Station} from "../../../../../models/station";
import {ColDef, GridApi, IGetRowsParams} from "ag-grid-community";
import {AppServiceService} from "../../../../../services/app-service.service";
import {formatDate} from "@angular/common";

@Component({
  selector: 'app-journeys',
  templateUrl: './journeys.component.html',
  styleUrls: ['./journeys.component.scss']
})
export class JourneysComponent implements OnInit {
  @Input() selectedStation: Station = {} as Station;
  @Input() typeTop: string = "";

  private gridApi!: GridApi;

  constructor(private appService: AppServiceService) {
  }

  ngOnInit(): void {
  }

  columnDefs: ColDef[] = [
    {headerName: 'Departure Time', field: 'departureTime', minWidth: 220, valueFormatter: this.dateFormatter},
    {headerName: 'Return Time', field: 'returnTime', minWidth: 220, valueFormatter: this.dateFormatter},
    {headerName: 'Departure Station Id', field: 'departureStationId'},
    {headerName: 'Departure Station Name', field: 'departureStationName'},
    {headerName: 'Return Station Id', field: 'returnStationId'},
    {headerName: 'Return Station Name', field: 'returnStationName'},
    {headerName: 'Distance', field: 'distance'},
    {headerName: 'Duration', field: 'duration'}
  ];

  onGridReady(params: any) {
    this.gridApi = params.api;
    let datasource = {
      getRows: (params: IGetRowsParams) => {
        if (this.typeTop == "DEPARTURE") {
          this.appService.getJourneysTopDepart(params, this.selectedStation.externalId);
        } else {
          this.appService.getJourneysTopReturn(params, this.selectedStation.externalId);
        }
      }
    }
    this.gridApi.setDatasource(datasource);
  }

  dateFormatter(params: any) {
    let date: number[] = params.value;
    if (date === undefined || date.length != 6) {
      return '';
    }
    return formatDate(new Date(date[0], date[1] - 1, date[2], date[3], date[4], date[5]), 'yyyy-MM-dd hh:mm:ss', 'en-GB');
  }
}
