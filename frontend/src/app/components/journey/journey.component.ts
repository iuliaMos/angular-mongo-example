import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AppServiceService} from "../../services/app-service.service";
import {ColDef, GridApi, GridOptions, IGetRowsParams} from "ag-grid-community";
import {formatDate} from "@angular/common";
import {MatDialog} from "@angular/material/dialog";
import {AddJourneyComponent} from "./add-journey/add-journey.component";

@Component({
  selector: 'app-journey',
  templateUrl: './journey.component.html',
  styleUrls: ['./journey.component.scss']
})
export class JourneyComponent implements OnInit {
  private gridApi!: GridApi;

  constructor(private route: ActivatedRoute, private appService: AppServiceService, public dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  columnDefs: ColDef[] = [
    {
      headerName: 'Departure Time',
      field: 'departureTime',
      filter: 'agDateColumnFilter',
      minWidth: 220,
      filterParams: {filterOptions: ['inRange'], maxNumConditions: 1},
      valueFormatter: this.dateFormatter
    },
    {
      headerName: 'Return Time',
      field: 'returnTime',
      filter: 'agDateColumnFilter',
      minWidth: 220,
      filterParams: {filterOptions: ['inRange'], maxNumConditions: 1},
      valueFormatter: this.dateFormatter
    },
    {headerName: 'Departure Station Id', field: 'departureStationId'},
    {headerName: 'Departure Station Name', field: 'departureStationName'},
    {headerName: 'Return Station Id', field: 'returnStationId'},
    {headerName: 'Return Station Name', field: 'returnStationName'},
    {
      headerName: 'Distance',
      field: 'distance',
      filter: 'agNumberColumnFilter',
      filterParams: {filterOptions: ['equals'], maxNumConditions: 1}
    },
    {
      headerName: 'Duration',
      field: 'duration',
      filter: 'agNumberColumnFilter',
      filterParams: {filterOptions: ['equals'], maxNumConditions: 1}
    }
  ];

  public defaultColDef: ColDef = {
    flex: 1,
    sortable: true,
    filter: 'agTextColumnFilter',
    filterParams: {filterOptions: ['contains'], maxNumConditions: 1}
  };


  gridOptions: GridOptions = {
    columnDefs: this.columnDefs,
    pagination: true,
    paginationPageSize: 10,
    cacheBlockSize: 10,
    rowModelType: 'infinite'
  };

  onPageSizeChanged(newPageSize: any) {
    let size = Number(newPageSize.target.value)
    this.gridOptions.paginationPageSize = size;
    this.gridOptions.cacheBlockSize = size;
    this.gridApi.paginationSetPageSize(size);
    this.gridApi.refreshInfiniteCache();
  };

  onGridReady(params: any) {
    this.gridApi = params.api;

    let datasource = {
      getRows: (params: IGetRowsParams) => {
        const size = this.gridOptions.paginationPageSize ? this.gridOptions.paginationPageSize : 10;
        const page = (params.startRow / size) | 0;
        this.appService.getJourneys(params, page, size);
      }
    }
    this.gridApi.setDatasource(datasource);
  }

  resetGridFilters() {
    this.gridApi.setFilterModel({});
    this.gridApi.refreshInfiniteCache();
  }

  dateFormatter(params: any) {
    let date: number[] = params.value;
    if (date === undefined || date.length != 6) {
      return '';
    }
    return formatDate(new Date(date[0], date[1] - 1, date[2], date[3], date[4], date[5]), 'yyyy-MM-dd hh:mm:ss', 'en-GB');
  }

  addNewJourney() {
    this.dialog.open(AddJourneyComponent, {
      data: {
        animal: 'new journey',
      },
    });
  }
}
