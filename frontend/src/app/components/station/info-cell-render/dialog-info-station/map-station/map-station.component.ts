import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Station} from "../../../../../models/station";
import Map from "ol/Map";
import {Feature} from "ol";
import {Point} from "ol/geom";
import {fromLonLat} from "ol/proj";
import VectorSource from "ol/source/Vector";
import TileLayer from "ol/layer/Tile";
import {OSM} from "ol/source";
import VectorLayer from "ol/layer/Vector";
import {Icon, Style} from "ol/style";
import View from "ol/View";

@Component({
  selector: 'app-map-station',
  templateUrl: './map-station.component.html',
  styleUrls: ['./map-station.component.scss']
})
export class MapStationComponent implements OnInit, OnDestroy {
  @Input() selectedStation: Station = {} as Station;

  public mapInfo!: Map;

  constructor() {
  }

  ngOnInit(): void {
    let firstPoint: Point = new Point(fromLonLat([this.selectedStation.x, this.selectedStation.y]));

    let features: Feature<Point>[] = [
      new Feature({
        geometry: firstPoint,
        name: this.selectedStation.nameFi
      })];

    const vectorSource = new VectorSource({
      features: features,
      wrapX: false,
    });

    this.mapInfo = new Map({
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
        new VectorLayer({
          source: vectorSource,
          style: new Style({
            image: new Icon({
              anchor: [0.5, 46],
              anchorXUnits: 'fraction',
              anchorYUnits: 'pixels',
              src: 'assets/marker.png'
            })
          })
        })
      ],
      view: new View({
        center: firstPoint.getCoordinates(),
        zoom: 12, maxZoom: 20,
      })
    });

    setTimeout(() => {
      if (this.mapInfo) {
        this.mapInfo.setTarget("map-info");
      }
    }, 1000);
  }

  ngOnDestroy(): void {
    this.mapInfo.dispose();
  }
}
