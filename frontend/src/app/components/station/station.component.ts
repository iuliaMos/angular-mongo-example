import {Component, OnInit} from '@angular/core';
import {GridApi, GridOptions} from "ag-grid-community";
import {ActivatedRoute} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {AppServiceService} from "../../services/app-service.service";

@Component({
  selector: 'app-station',
  templateUrl: './station.component.html',
  styleUrls: ['./station.component.scss']
})
export class StationComponent implements OnInit {
  private gridApi!: GridApi;

  constructor(private route: ActivatedRoute, private appService: AppServiceService) {
  }

  ngOnInit(): void {
  }

  columnDefs = [
    {headerName: 'Make', field: 'make' },
    {headerName: 'Model', field: 'model' },
    {headerName: 'Price', field: 'price'}
  ];

  rowData = [
    { make: 'Toyota', model: 'Celica', price: 35000 },
    { make: 'Ford', model: 'Mondeo', price: 32000 },
    { make: 'Porsche', model: 'Boxter', price: 89000 },
    { make: 'Abcd', model: 'Type3', price: 8000 },
    { make: 'Defg', model: 'Type4', price: 4000 },
    { make: 'Uyg', model: 'Type5', price: 3000 }
  ];

  gridOptions : GridOptions = {
    rowData: this.rowData,
    columnDefs: this.columnDefs,
    pagination: true,
    paginationPageSize: 5
  }

  onPageSizeChanged(newPageSize: any) {
    this.gridOptions.paginationPageSize = Number(newPageSize.target.value);
    this.gridApi.paginationSetPageSize(Number(newPageSize.target.value));
  }

  onGridReady(params: any) {
    this.gridApi = params.api;

    /*this.http
      .get("http://localhost:8080/stations")
      .subscribe(data => {
        this.rowData = data;
      });*/
  }
}
