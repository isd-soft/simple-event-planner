import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Attachment} from "../models/attachment.model";
import {FILES_URL} from "../urls.config";

@Injectable({
  providedIn: 'root'
})
export class AttachmentsService {

  constructor(private httpClient: HttpClient) { }

  getAttachment(id: number) {
    return this.httpClient.get<Attachment>(`${FILES_URL}/${id}`);
  }


  setImageFromAttachment(image: Attachment, element: HTMLImageElement) {
    let bytes: Uint8Array = this.string2bytes(image.content);
    let blob = new Blob([bytes]);

    let reader = new FileReader();
    reader.onload = function (ev) {
      // @ts-ignore
      element.src = ev.target.result;
    }
    reader.readAsDataURL(blob);
  }

  downloadAttachment(attachment: Attachment) {
    const url = window.URL.createObjectURL(new Blob(
      [this.string2bytes(attachment.content)]
    ))

    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', attachment.name);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }


  string2bytes(text: string): Uint8Array {
    return new Uint8Array( JSON.parse(`[${text}]`) );
  }

  bytes2string(bytes: Uint8Array): string {
    return bytes.toString();
  }

}
