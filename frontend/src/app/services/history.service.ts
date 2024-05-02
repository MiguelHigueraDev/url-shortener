import { Injectable, signal } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

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
      if (parsedHistory.some((entry: any) => entry.shortenedUrl === shortenedUrl)) return;

      parsedHistory.push({ shortenedUrl, originalUrl });
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
