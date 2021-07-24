import {Component, Input, OnInit, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-reactions',
  templateUrl: './reactions.component.html',
  styleUrls: ['./reactions.component.css']
})
export class ReactionsComponent implements OnInit {
  selectable: boolean = false;
  showAll: boolean = false;

  @Input() reactions: any[] = [];
  @Input() allReactions: any[] = []
  @Input() myReaction: any = null;

  @Output() onSelect = new EventEmitter<any>();
  @Output() onRemove = new EventEmitter<void>();

  constructor() { }

  ngOnInit(): void {
  }

  selectReaction(index: number) {
    this.selectable = false;

    if (this.myReaction && this.myReaction.type === this.reactions[index].type) {
      this.removeReaction();
      return;
    }

    if (this.myReaction) {
      this.myReaction.type = this.reactions[index].type;
      this.myReaction.index = index;
    } else {
      this.myReaction = {
        type: this.reactions[index].type,
        index,
      }
      this.allReactions.push(this.myReaction);
    }
    this.onSelect.emit(this.myReaction);
  }

  removeReaction() {
    this.onSelect.emit(this.myReaction);
    this.allReactions = this.allReactions.filter(x => x !== this.myReaction);

    this.myReaction = null;
  }

}
