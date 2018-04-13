package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static domain.CalculateStatus.DO;
import static domain.CalculateStatus.DONOT;
import static domain.Figure.FRAMEBAR;
import static domain.Figure.SPARE;

public abstract class Frame {

    private Score frameScore;
    private Score totalScore;
    private List<Score> scores;
    private CalculateStatus calculateStatus;

    public abstract boolean isFrameEnd();

    Frame() {
        this.scores = new ArrayList<>();
        this.frameScore = Score.of();
        this.totalScore = Score.of();
    }

    public void trying(final int score) {
        if (!isValidScore(score)) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        addition(score);
    }

    void addition(final int score) {
        this.scores.add(Score.of(score));
        this.frameScore.sum(Score.of(score));
        this.totalScore.sum(Score.of(score));
    }

    boolean isTrySecond() {
        return scores.size() == 2;
    }

    boolean isSpare() {
        return frameScore.isTen() && isTrySecond();
    }

    boolean isFirstStrike() {
        return scores.get(0).isTen();
    }

    Score getFrameScore() {
        return frameScore;
    }

    boolean isValidScore(final int score) {
        return isValidScoreToTotalScore(score);
    }

    boolean isValidScoreToTotalScore(final int score) {
        return frameScore.isValidAdditionScore(score);
    }

    private String toScoreString() {
        return scores.stream().map(Score::toString).collect(Collectors.joining(FRAMEBAR.toString()));
    }

    private String spareString() {
        return scores.get(0).toString() + FRAMEBAR + SPARE;
    }

    String toFrameString() {
        if (isSpare()) return spareString();
        return toScoreString();
    }

    private boolean changeStatus(Frame beforeFrame) {
        if (beforeFrame.calculateStatus == DONOT)
            return StatusChanger.beforeFrameDoNotCase(beforeFrame, this);
        return StatusChanger.beforeFrameDoCase(this);
    }

    public void assignCalculableState(Frame beforeFrame) {
        calculateStatus = CalculateStatus.of(changeStatus(beforeFrame));
    }

    public void assignCalculableState() {
        calculateStatus = CalculateStatus.of(StatusChanger.beforeFrameDoCase(this));
    }

    public CalculateStatus getCalculateStatus() {
        return calculateStatus;
    }

    @Override
    public String toString() {
        return toFrameString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Frame frame = (Frame) o;
        return Objects.equals(scores, frame.scores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scores);
    }

    public Score getTotalScore() {
        return totalScore;
    }

    public boolean isDoCalculation() {
        return calculateStatus == DO;
    }

    public void giveMessageFrom(Frame frame) {
        if (frame.calculateStatus == DO) {
            if (!frame.isTrySecond() && this.isSpare()) {
                frameScore.sum(frame.scores.get(0));
                this.calculateStatus = DO;
                return;
            }
            if (frame.isTrySecond() && this.isFirstStrike()) {
                frameScore.sum(frame.frameScore);
                this.calculateStatus = DO;
            }
        }
    }

    public void giveMessageFromBefore(Frame beforeFrame) {
        if (isFrameEnd()) {
            frameScore.sum(beforeFrame.frameScore);
        }
    }
}

