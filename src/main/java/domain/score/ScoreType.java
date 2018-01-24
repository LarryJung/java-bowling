package domain.score;

import static domain.score.ScoreNumber.TEN;
import static domain.score.ScoreNumber.ZERO;

public enum ScoreType {
    STRIKE("X"),
    SPARE("/"),
    MISS(null),
    GUTTER("-"),
    NONE(null),
    UNDEFINED(null);

    private final String display;

    ScoreType(String display) {
        this.display = display;
    }

    static ScoreType valueOf(TotalScore totalScore) {
        if (totalScore.isStrike()) {
            return STRIKE;
        }
        if (totalScore.sumOfScore().equals(TEN)) {
            return SPARE;
        }
        return MISS;
    }

    static ScoreType valueOf(ScoreNumber score) {
        if (score.equals(TEN)) {
            return STRIKE;
        }
        if (score.equals(ZERO)) {
            return GUTTER;
        }
        return NONE;
    }

    String getDisplay() {
        return display;
    }

    boolean isDisplay() {
        return display != null;
    }
}
