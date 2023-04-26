import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

@Component({
  selector: 'app-add-journey',
  templateUrl: './add-journey.component.html',
  styleUrls: ['./add-journey.component.scss']
})
export class AddJourneyComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    console.log(data);
  }
}
