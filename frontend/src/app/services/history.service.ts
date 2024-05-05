import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

/**
 * This service is responsible for managing the history of shortened URLs.
 * It stores the history in the local storage and provides an observable to listen to changes.
 * The history is stored as an array of objects (Url) with the shortened URL and the original URL.
 */

const MAX_HISTORY_LENGTH = 100;

@Injectable({
  providedIn: 'root',
})
export class HistoryService {
  constructor() {}

  private historySubject = new BehaviorSubject<Url[]>([]);

  addToHistory(shortenedUrl: string, originalUrl: string) {
    const history = this.getHistory();
    if (history == null) {
      localStorage.setItem(
        'history',
        JSON.stringify([{ shortenedUrl, originalUrl }])
      );
    } else {
      const parsedHistory: Url[] = history;
      if (parsedHistory.some((entry: any) => entry.shortenedUrl === shortenedUrl)) {
        // Place it at the start and then remove the duplicate
        parsedHistory.unshift(parsedHistory.splice(parsedHistory.findIndex((entry: any) => entry.shortenedUrl === shortenedUrl), 1)[0]);
      } else {
        parsedHistory.unshift({ shortenedUrl, originalUrl });
      }

      if (parsedHistory.length > MAX_HISTORY_LENGTH) parsedHistory.shift();
      localStorage.setItem('history', JSON.stringify(parsedHistory));
    }
    this.historySubject.next(this.getHistory());
  }

  getHistory() {
    return JSON.parse(localStorage.getItem('history') || '[]');
  }

  getHistoryObservable() {
    return this.historySubject.asObservable();
  }
}

export interface Url {
  shortenedUrl: string;
  originalUrl: string;
}
