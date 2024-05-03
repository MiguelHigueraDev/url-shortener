import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { HistoryService, Url } from '../../services/history.service';
import { MatButtonModule } from '@angular/material/button';
import { RemoveHttpPipe } from '../../remove-http.pipe';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, RemoveHttpPipe],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent implements OnInit {
  history: Url[] = [];
  constructor(private historyService: HistoryService) {
  }

  ngOnInit(): void {
    this.historyService.getHistoryObservable().subscribe((history) => {
      this.history = history;
    });
    // Get current history
    this.history = this.historyService.getHistory();
  }

  copyShortUrl(url: string) {
    navigator.clipboard.writeText(url);
  }

}
