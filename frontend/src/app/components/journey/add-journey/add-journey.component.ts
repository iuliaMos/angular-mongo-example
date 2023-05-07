import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AppServiceService} from "../../../services/app-service.service";
import {MatDialogRef} from "@angular/material/dialog";
import {catchError, of} from "rxjs";

@Component({
  selector: 'app-add-journey',
  templateUrl: './add-journey.component.html',
  styleUrls: ['./add-journey.component.scss']
})
export class AddJourneyComponent {

  journeyForm!: FormGroup;
  messageValid:Map<string,string> = new Map();

  constructor(private fb: FormBuilder, private appService: AppServiceService, private dialogRef: MatDialogRef<AddJourneyComponent>) {
    this.journeyForm = this.fb.group({
      departureTime: ['', Validators.required],
      returnTime: ['', Validators.required],
      departureStationId: ['', Validators.required],
      departureStationName: [''],
      returnStationId: ['', Validators.required],
      returnStationName: [''],
      distance: ['', Validators.required, Validators.min(10)],
      duration: ['', Validators.required, Validators.min(10)]
    });
  }

  onSubmit() {
    this.messageValid = new Map();
    this.appService.saveJourney(this.journeyForm.value)
      .pipe(
        catchError(error => {
          this.messageValid = error.error;
          return of(error);
        })
      )
      .subscribe(() => {
        if (this.messageValid.size == 0) {
          this.dialogRef.close();
        }
      });
  }

  public errorHandling = (control: string, error: string) => {
    return this.journeyForm.controls[control].hasError(error);
  }
}
