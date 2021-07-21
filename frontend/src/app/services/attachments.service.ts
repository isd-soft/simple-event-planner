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

  async fillAttachmentContent(attachment: Attachment) {
    if (!attachment || typeof attachment.id === "undefined") {
      return attachment;
    }

    if (attachment.content) {
      return attachment;
    }

    return await this.getAttachment(attachment.id).toPromise();
  }

  async setImageFromAttachment(image: Attachment, element: HTMLElement) {
    console.log(image,element);
    image = await this.fillAttachmentContent(image);
    
    let bytes: Uint8Array = AttachmentsService.string2bytes(image.content);
    let blob = new Blob([bytes]);

    let reader = new FileReader();
    reader.onload = function (ev) {
      // @ts-ignore
      element.src = ev.target.result;
    }
    reader.readAsDataURL(blob);
  }

  async downloadAttachment(attachment: Attachment) {
    attachment = await this.fillAttachmentContent(attachment);

    const url = window.URL.createObjectURL(new Blob(
      [AttachmentsService.string2bytes(attachment.content)]
    ))

    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', attachment.name);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }

  private static string2bytes(text: string): Uint8Array {
    return new Uint8Array( JSON.parse(`[${text}]`) );
  }

  private static bytes2string(bytes: Uint8Array): string {
    return bytes.toString();
  }

  fileToAttachment(file: File): Promise<Attachment> {
    return file.arrayBuffer()
      .then((buffer: ArrayBuffer) => ({
        name: file.name,
        type: file.type,
        content: AttachmentsService.bytes2string(new Uint8Array(buffer))
      }));
  }

}
