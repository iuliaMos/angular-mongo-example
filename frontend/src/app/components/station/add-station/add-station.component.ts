import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AppServiceService} from "../../../services/app-service.service";
import {MatDialogRef} from "@angular/material/dialog";
import {catchError, of} from "rxjs";

@Component({
  selector: 'app-add-station',
  templateUrl: './add-station.component.html',
  styleUrls: ['./add-station.component.scss']
})
export class AddStationComponent implements OnInit{

  stationForm!: FormGroup;
  messageValid:Map<string,string> = new Map();

  constructor(private fb: FormBuilder, private appService: AppServiceService, private dialogRef: MatDialogRef<AddStationComponent>) {
    this.stationForm = this.fb.group({
      nr: [''],
      externalId: ['', Validators.required],
      nameFi: ['', Validators.required],
      nameSe: [''],
      nameEn: [''],
      addressFi: ['', Validators.required],
      addressSe: [''],
      cityFi: [''],
      citySe: [''],
      operator: [''],
      capacities: [''],
      x: ['', Validators.required],
      y: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }
  onSubmit() {
    this.messageValid = new Map();
    this.appService.saveStation(this.stationForm.value)
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
    return this.stationForm.controls[control].hasError(error);
  }
}
