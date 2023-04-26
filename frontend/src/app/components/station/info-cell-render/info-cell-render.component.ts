import {Component} from '@angular/core';
import {ICellRendererAngularComp} from "ag-grid-angular";
import {ICellRendererParams} from "ag-grid-community";
import {MatDialog} from "@angular/material/dialog";
import {DialogInfoStationComponent} from "./dialog-info-station/dialog-info-station.component";
import {Station} from "../../../models/station";

@Component({
  selector: 'app-info-cell-render',
  templateUrl: './info-cell-render.component.html',
  styleUrls: ['./info-cell-render.component.scss']
})
export class InfoCellRenderComponent implements ICellRendererAngularComp {

  station?: Station;

  constructor(public dialog: MatDialog) {
  }

  agInit(params: ICellRendererParams): void {
    this.station = params.value;
  }

  refresh(params: ICellRendererParams<any>): boolean {
    return false;
  }

  onClickHandler() {
    this.dialog.open(DialogInfoStationComponent, {
      data: this.station
    });
  }

}
