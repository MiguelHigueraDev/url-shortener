import { Component } from '@angular/core';
import { UrlCountService } from '../../services/url-count.service';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {
  constructor(private urlCountService: UrlCountService) {}

  count = 0;

  ngOnInit() {
    this.urlCountService.getCount();
    this.urlCountService.getCountObservable().subscribe((count) => {
      this.count = count;
    });
  }
}
