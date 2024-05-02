import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ShortenerService {
  private baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  shortenUrl(url: string) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });

    return this.http.post(
      `${this.baseUrl}/shorten`,
      {
        originalUrl: url,
      },
      { headers }
    );
  }
}
