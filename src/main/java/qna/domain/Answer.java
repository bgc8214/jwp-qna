package qna.domain;

import org.hibernate.annotations.Where;
import qna.CannotDeleteException;
import qna.NotFoundException;
import qna.UnAuthorizedException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "answer")
@Entity
@Where(clause = "deleted = false")
public class Answer extends BaseEntity {

    @Lob
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writerId;

    protected Answer() {
        //JPA need no-arg constructor
    }

    public Answer(User writer, Question question, String contents) {
        if (Objects.isNull(writer)) {
            throw new UnAuthorizedException();
        }

        if (Objects.isNull(question)) {
            throw new NotFoundException();
        }

        this.writerId = writer;
        this.question = question;
        this.contents = contents;
    }

    public boolean isOwner(User writer) {
        return this.writerId.equals(writer);
    }

    public void toQuestion(Question question) {
        this.question = question;
        question.addAnswer(this);
    }



    public User getWriterId() {
        return writerId;
    }

    public void setWriterId(User writerId) {
        this.writerId = writerId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public DeleteHistory delete() {
        this.setDeleted(true);
        return new DeleteHistory(ContentType.ANSWER, this.getId(), this.getWriterId(), LocalDateTime.now());
    }
}
