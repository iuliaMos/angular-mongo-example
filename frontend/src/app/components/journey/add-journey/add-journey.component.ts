import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AppServiceService} from "../../../services/app-service.service";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-journey',
  templateUrl: './add-journey.component.html',
  styleUrls: ['./add-journey.component.scss']
})
export class AddJourneyComponent {

  journeyForm!: FormGroup;

  constructor(private fb: FormBuilder, private appService: AppServiceService, private dialogRef: MatDialogRef<AddJourneyComponent>) {
    this.journeyForm = this.fb.group({
      departureTime: ['', Validators.required],
      returnTime: ['', Validators.required],
      departureStationId: ['', Validators.required],
      departureStationName: [''],
      returnStationId: ['', Validators.required],
      returnStationName: [''],
      distance: ['', Validators.required],
      duration: ['', Validators.required]
    });
  }

  onSubmit() {
    this.appService.saveJourney(this.journeyForm.value);
    this.dialogRef.close();
  }

  public errorHandling = (control: string, error: string) => {
    return this.journeyForm.controls[control].hasError(error);
  }
}
