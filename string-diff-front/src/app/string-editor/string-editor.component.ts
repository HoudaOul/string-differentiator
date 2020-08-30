import { Component, OnInit, Input, Output } from '@angular/core';

import { StringsDiffService } from '../string-diff/strings-diff.service';

@Component({
  selector: 'app-string-editor',
  templateUrl: './string-editor.component.html',
  styleUrls: ['./string-editor.component.css'],
  providers: [StringsDiffService]
})
export class StringEditorComponent implements OnInit {

  @Input() strings = { first: '', second: '' };

  output = {result: ''};

  constructor(public service: StringsDiffService) { }

  ngOnInit() { }

  differentiate() {
    this.service.diffService(this.strings)
      .then(result => {
        this.output = result;
        console.log('result: ' + this.output.result);
      })
      .catch(error => console.log('error: ' + error));
  }
}
