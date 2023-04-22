import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AppServiceService {

  constructor(private http: HttpClient) {
    /*this.http.get('http://localhost:8080/station')
      .map(data => data.json())
      .subscribe(data => this.rowData = data)*/
  }
}
