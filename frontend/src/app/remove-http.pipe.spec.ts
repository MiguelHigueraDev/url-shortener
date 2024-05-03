import { RemoveHttpPipe } from './remove-http.pipe';

describe('RemoveHttpPipe', () => {
  it('create an instance', () => {
    const pipe = new RemoveHttpPipe();
    expect(pipe).toBeTruthy();
  });

  it('remove http from url', () => {
    const pipe = new RemoveHttpPipe();
    expect(pipe.transform('http://example.com')).toEqual('example.com');
  });

  it('remove https from url', () => {
    const pipe = new RemoveHttpPipe();
    expect(pipe.transform('https://example.com')).toEqual('example.com');
  });
});
