import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
  selector: 'app-add-station',
  templateUrl: './add-station.component.html',
  styleUrls: ['./add-station.component.scss']
})
export class AddStationComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    console.log(data);
  }
}
