import { Component } from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { UrlCountService } from '../../services/url-count.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [MatToolbarModule, MatIconModule, MatButtonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  constructor(private urlCountService: UrlCountService) { }
  
  count = 0;

  ngOnInit() {
    this.urlCountService.getCount();
    this.urlCountService.getCountObservable().subscribe((count) => {
      this.count = count;
    });
  }
}
