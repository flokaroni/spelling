package at.linguathek.spelling.Data;

import java.io.Serializable;

public class Statement implements Serializable {
    String question;
    String rightAnswer;
    String[] rightAnswers;
    String[] wrongAnswers;
    StatementType type = StatementType.QUESTION_MULTI_WRONG;
    private int soundId;

    public enum StatementType {QUESTION_MULTI_WRONG, WORD, LISTEN}

    public Statement() {
    }

    public String getQuestion() {
        return question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }


    public Statement(String question) {
        this.question = question;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public StatementType getType() {
        return type;
    }

    public void setType(StatementType type) {
        this.type = type;
    }

    public String[] getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(String[] wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    public String[] getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(String[] rightAnswers) {
        this.rightAnswers = rightAnswers;
    }
}
