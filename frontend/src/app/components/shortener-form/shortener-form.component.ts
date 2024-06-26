import { Component } from '@angular/core';
import {
  FormControl,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ShortenerService } from '../../services/shortener-service.service';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { HistoryService } from '../../services/history.service';
import { RemoveHttpPipe } from '../../remove-http.pipe';
import { UrlCountService } from '../../services/url-count.service';

@Component({
  selector: 'app-shortener-form',
  standalone: true,
  imports: [
    FormsModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatCardModule,
    RemoveHttpPipe
  ],
  templateUrl: './shortener-form.component.html',
  styleUrl: './shortener-form.component.css',
})
export class ShortenerFormComponent {
  constructor(private shortenerService: ShortenerService, private historyService: HistoryService, private urlCountService: UrlCountService) {
    // Update error message when input changes
    this.originalUrl.valueChanges.subscribe(() => {
      this.updateErrorMessage();
    });
    this.originalUrl.statusChanges.subscribe(() => {
      this.updateErrorMessage();
    });
  }

  originalUrl = new FormControl('', [
    Validators.required,
    Validators.pattern('https?://[^.]+\\..+'),
    Validators.maxLength(255),
    Validators.minLength(10),
  ]);
  errorMessage = '';
  shortenedUrl = '';

  // Shorten the URL and add it to the history
  shortenUrl() {
    if (this.originalUrl.invalid) return;
    this.shortenerService
      .shortenUrl(this.originalUrl.value!)
      .subscribe(
        (response: any) => {
          this.shortenedUrl = response.url;
          this.historyService.addToHistory(this.shortenedUrl, this.originalUrl.value!);
          this.urlCountService.getCount();
        },
        (error: any) => {
          alert('Error shortening URL: ' + error.statusText);
        }
      );
  }

  updateErrorMessage() {
    if (this.originalUrl.hasError('required')) {
      this.errorMessage = 'A URL is required';
    } else if (this.originalUrl.hasError('pattern')) {
      this.errorMessage = 'Must be a valid URL';
    } else {
      this.errorMessage = '';
    }
  }

  copyToClipboard() {
    navigator.clipboard.writeText(this.shortenedUrl);
  }
}
