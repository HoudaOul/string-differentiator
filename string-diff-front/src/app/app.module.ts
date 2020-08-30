import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
//import { StringEditorComponent } from './string-editor/string-editor.component';
import { StringEditorModule } from './string-editor/string-editor.module';

@NgModule({
  declarations: [
    AppComponent
    //StringEditorComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    HttpClientModule,
    StringEditorModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
