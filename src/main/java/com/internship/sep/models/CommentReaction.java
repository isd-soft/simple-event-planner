package com.internship.sep.models;
import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Entity
@Table(name = "comment_reactions")
public class CommentReaction extends AbstractEntity{

    @Column(name="type")
    private ReactionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentReaction commentReaction = (CommentReaction) o;
        return Objects.equals(id, commentReaction.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "CommentReaction{" +
                "type=" + type +
                ", comment=" + comment +
                ", user=" + user +
                '}';
    }
}
