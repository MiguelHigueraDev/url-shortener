import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ShortenerService } from './services/shortener-service.service';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from './components/header/header.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, FormsModule, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'url-shortener';

  constructor(private shortenerService: ShortenerService) {}

  originalUrl: string = '';

  shortenUrl() {
    this.shortenerService.shortenUrl(this.originalUrl).subscribe((response: any) => {
      console.log(response);
    });
  }
}
