package domain;

import java.util.Objects;

public class Score {

    private int score;

    private Score(int n) {
        if (!isValidScore(n)) {
            throw new IllegalArgumentException("0에서 10사이의 값을 넣어주세요.");
        }
        this.score = n;
    }

    private Score() {
        this.score = 0;
    }

    public static Score of() {
        return new Score();
    }

    public static Score of(int score) {
        return new Score(score);
    }

    private boolean isValidScore(int n) {
        return 0 <= n && n <= 10;
    }

    public void sum(Score otherScore) {
        this.score += otherScore.score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return score == score1.score;
    }

    @Override
    public int hashCode() {

        return Objects.hash(score);
    }

    @Override
    public String toString() {
        if (score == 0) return "-";
        return Integer.toString(score);
    }

    public boolean isUnderTen() {
        return score < 10;
    }

    public boolean isTwenty() {
        return score == 20;
    }

    public boolean isThirty() {
        return score == 30;
    }

    public boolean isValidValue(int score) {
        return score <= 10 - this.score;
    }
}

