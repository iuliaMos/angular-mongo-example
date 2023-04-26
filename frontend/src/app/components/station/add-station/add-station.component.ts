import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AppServiceService} from "../../../services/app-service.service";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-station',
  templateUrl: './add-station.component.html',
  styleUrls: ['./add-station.component.scss']
})
export class AddStationComponent implements OnInit{

  stationForm!: FormGroup;

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
    this.appService.saveStation(this.stationForm.value);
    this.dialogRef.close();
  }
}
