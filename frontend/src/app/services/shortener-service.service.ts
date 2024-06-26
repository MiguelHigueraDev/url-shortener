import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs';

/**
 * This service is responsible for shortening URLs.
 * It sends a POST request to the backend to shorten a URL.
 * The backend returns the shortened URL.
 */

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
    ).pipe(
      catchError((error: any) => {
        console.error('Error shortening URL:', error);
        throw error;
      })
    );
  }
}
