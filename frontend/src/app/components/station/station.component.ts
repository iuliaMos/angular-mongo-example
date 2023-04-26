import {Component, OnInit} from '@angular/core';
import {ColDef, GridApi, GridOptions, IGetRowsParams} from "ag-grid-community";
import {ActivatedRoute} from "@angular/router";
import {AppServiceService} from "../../services/app-service.service";
import {InfoCellRenderComponent} from "./info-cell-render/info-cell-render.component";
import {MatDialog} from "@angular/material/dialog";
import {AddStationComponent} from "./add-station/add-station.component";

@Component({
  selector: 'app-station',
  templateUrl: './station.component.html',
  styleUrls: ['./station.component.scss']
})
export class StationComponent implements OnInit {
  private gridApi!: GridApi;

  constructor(private route: ActivatedRoute, private appService: AppServiceService, public dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  columnDefs: ColDef[] = [
    {headerName: 'Info', width: 50, cellRenderer: InfoCellRenderComponent},
    {headerName: 'ExternalId', field: 'externalId'},
    {headerName: 'NameEn', field: 'nameEn'},
    {headerName: 'NameFi', field: 'nameFi'},
    {headerName: 'Address Fi', field: 'addressFi'},
    {headerName: 'City Fi', field: 'cityFi'},
    {headerName: 'Operator', field: 'operator'},
    {
      headerName: 'Capacities',
      field: 'capacities',
      filter: 'agNumberColumnFilter',
      filterParams: {filterOptions: ['equals'], maxNumConditions: 1}
    },
    {headerName: 'X', field: 'x', filter: false},
    {headerName: 'Y', field: 'y', filter: false}
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
        this.appService.getStations(params, page, size);
      }
    }
    this.gridApi.setDatasource(datasource);
  }

  resetGridFilters() {
    this.gridApi.setFilterModel({});
    this.gridApi.refreshInfiniteCache();
  }

  addNewStation() {
    this.dialog.open(AddStationComponent, {
      data: {
        animal: 'new station',
      },
    });
  }
}
