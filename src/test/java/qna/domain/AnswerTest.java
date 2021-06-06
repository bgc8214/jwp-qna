package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AnswerTest {
    @Autowired
    private AnswerRepository answerRepository;

    public static final Answer A1 = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(UserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @DisplayName("저장한_객체와_저장된_객체_비교")
    @Test
    void 저장한_객체와_저장된_객체_비교() {
        Answer answer = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "zz");
        Answer actual = answerRepository.save(answer);
        assertThat(actual).isEqualTo(answer);
    }

    @DisplayName("BaseEntity")
    @Test
    void base_entity_test() {
        Answer answer = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "zz");
        Answer actual = answerRepository.save(answer);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getCreatedAt()).isNotNull();
        assertThat(actual.getUpdatedAt()).isNotNull();
    }

    @DisplayName("update 테스트(변경감지)")
    @Test
    void update() {
        Answer answer = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "zz");
        Answer actual = answerRepository.save(answer);

        answer.setContents("gtgt");
        assertThat(actual.getContents()).isEqualTo("gtgt");
    }
}
