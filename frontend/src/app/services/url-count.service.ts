import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';

/**
 * This service is responsible for counting the number of shortened URLs.
 * It sends a GET request to the backend to get the count of shortened URLs.
 * The backend returns the count of shortened URLs.
 * The count is stored in a BehaviorSubject to listen to changes.
 */

@Injectable({
  providedIn: 'root'
})
export class UrlCountService {

  constructor(private http: HttpClient) {}

  private countUrl = environment.apiUrl + "/stats/count"
  private countSubject = new BehaviorSubject<number>(0);

  getCount() {
    return this.http.get(this.countUrl, { responseType: 'json' }).subscribe((count: any) => {
      this.countSubject.next(count.count);
    });
  }

  getCountObservable() {
    return this.countSubject.asObservable();
  }
}
