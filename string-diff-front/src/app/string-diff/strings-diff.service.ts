import { Injectable } from '@angular/core';
import { StringPair } from '../string-pair/stringpair';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Response } from '@angular/http';
import { _throw } from 'rxjs/observable/throw';

@Injectable()
export class StringsDiffService {

  apiURL = 'http://localhost:8080';

  stringPair: StringPair;

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  diffService(strings): Promise<any> {
    let body = JSON.stringify(strings);
    return this.http
      .post(this.apiURL + '/diff', body, this.httpOptions)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  private extractData(res: Response) {
    let body = res;
    return body || {}; 
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred ', error);
    return Promise.reject(error.message || error);
  }

}
