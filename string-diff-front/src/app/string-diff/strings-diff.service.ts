import { Injectable } from '@angular/core';
import { StringPair } from '../string-pair/stringpair';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Response } from '@angular/http';
import { Observable } from 'rxjs';
import { _throw } from 'rxjs/observable/throw';
import { retry, catchError } from 'rxjs/operators';

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

  /*
  differentiate(strings): Observable<String> {
    this.stringPair = new StringPair(strings.sFirst, strings.sSecond);
    console.log('strings: ' + this.stringPair.first + ', ' + this.stringPair.second);

    return this.http.post(this.apiURL + '/strings', JSON.stringify(this.stringPair), this.httpOptions)
      .pipe(
      retry(1),
      catchError(this.handleError)
      );
  }
*/
  /*
    handleError(error) {
       let errorMessage = '';
       if (error.error instanceof ErrorEvent) {
         errorMessage = error.error.message;
       } else {
         errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
       }
       window.alert(errorMessage);
       return _throw(errorMessage);
    }
   */
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
