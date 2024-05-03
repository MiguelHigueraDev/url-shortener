import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'removeHttp',
  standalone: true
})
export class RemoveHttpPipe implements PipeTransform {

  transform(value: string, ...args: string[]): string {
    return value.replace(/https?:\/\//, '');
  }

}
