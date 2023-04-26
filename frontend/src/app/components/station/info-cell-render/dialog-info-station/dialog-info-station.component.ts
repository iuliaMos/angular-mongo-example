import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
  selector: 'app-dialog-info-station',
  templateUrl: './dialog-info-station.component.html',
  styleUrls: ['./dialog-info-station.component.scss']
})
export class DialogInfoStationComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    console.log(data);
  }
}
