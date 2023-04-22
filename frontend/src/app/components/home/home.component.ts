import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import Map from 'ol/Map';
import View from 'ol/View';
import {OSM} from 'ol/source';
import TileLayer from 'ol/layer/Tile';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, AfterViewInit {
  public map!: Map;

  constructor(private route: ActivatedRoute) {

  }

  ngAfterViewInit() {
   // this.map.setTarget("map");
  }

  ngOnInit(): void {
    this.map = new Map({
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
      ],
      target: 'map',
      view: new View({
        center: [0, 0],
        zoom: 2,maxZoom: 18,
      }),
    });
  }

}
