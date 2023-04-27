import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {Station} from "../../../../models/station";
import {AppServiceService} from "../../../../services/app-service.service";

@Component({
  selector: 'app-dialog-info-station',
  templateUrl: './dialog-info-station.component.html',
  styleUrls: ['./dialog-info-station.component.scss']
})
export class DialogInfoStationComponent implements OnInit {
  station: Station = {} as Station;
  infoStation: string[] = [];

  constructor(@Inject(MAT_DIALOG_DATA) public data: Station, private appService: AppServiceService) {
    this.station = data;
    this.appService.getJourneysAvg(this.station.externalId)
      .subscribe(data => {
        this.infoStation = data;
      });
  }

  ngOnInit(): void {
  }
}
