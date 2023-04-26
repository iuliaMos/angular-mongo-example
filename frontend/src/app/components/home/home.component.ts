import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import Map from 'ol/Map';
import View from 'ol/View';
import {OSM} from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import {AppServiceService} from "../../services/app-service.service";
import {Feature, Overlay} from "ol";
import {Point} from "ol/geom";
import VectorLayer from "ol/layer/Vector";
import VectorSource from "ol/source/Vector";
import {fromLonLat} from "ol/proj";
import {Icon, Style} from "ol/style";
import {StationMarker} from "../../models/stationMarker";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, AfterViewInit {
  public map!: Map;

  constructor(private route: ActivatedRoute, private appService: AppServiceService) {

  }

  ngAfterViewInit() {
  }

  ngOnInit(): void {
      this.appService.getStationsGeo()
        .subscribe(data => {
          let stationsGeo: StationMarker[] = [];
          if (data instanceof Array) {
            stationsGeo = data;
            const features = this.getFeaturesFromStations(stationsGeo);
            this.addStationsToMap(features);
          } else {
            this.map = new Map({
              layers: [
                new TileLayer({
                  source: new OSM(),
                })
              ],
              target: 'map',
              view: new View({
                center: [0, 0],
                zoom: 2, maxZoom: 20,
              })
            });
        }
    });
  }

  getFeaturesFromStations(stationsGeo: StationMarker[]): Feature<Point>[] {
    let features: Feature<Point>[] = [];
    stationsGeo.forEach(coord => {
      features.push(new Feature({
        geometry: new Point(fromLonLat([coord.lon, coord.lat])),
        name: coord.name
      }));
    });
    return features;
  }

  addStationsToMap(features: Feature<Point>[]) {
    const vectorSource = new VectorSource({
      features: features,
      wrapX: false,
    });

    const container = document.getElementById('popup')!;
    const content = document.getElementById('popup-content')!;
    const closer = document.getElementById('popup-closer')!;

    const overlay = new Overlay({
      element: container,
      positioning: 'bottom-center',
      autoPan: {
        animation: {
          duration: 250,
        }
      }
    });

    closer.onclick = function () {
      overlay.setPosition(undefined);
      closer.blur();
      return false;
    };

    let firstPoint: Point = new Point([0, 0]);
    if (features.length > 0) {
      firstPoint = features[0].getGeometry()!;
    }
    this.map = new Map({
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
      target: 'map',
      overlays: [overlay],
      view: new View({
        center: firstPoint.getCoordinates(),
        zoom: 12, maxZoom: 20,
      })
    });

    this.map.on('click', function (this: any, event: any) {
      const feature = this.forEachFeatureAtPixel(event.pixel, function (feature: any) {
        return feature;
      });
      if (feature) {
        const coordinates = feature.getGeometry().getCoordinates();
        content.innerHTML = '<p>' + feature.get('name') + '</p>'
        overlay.setPosition(coordinates);
      }
    });
  }

}
