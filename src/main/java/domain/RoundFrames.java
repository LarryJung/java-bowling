package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoundFrames {

    private List<Frame> roundFrames;

    private RoundFrames() {
        roundFrames = new ArrayList<>(10);
        roundFrames.add(NormalFrame.of());
    }

    public static RoundFrames of() {
        return new RoundFrames();
    }

    public void lastFrameTry(int i) {
        roundFrames.get(roundFrames.size() - 1).trying(i);
    }

    public boolean lastFrameWasEnd() {
        return roundFrames.get(roundFrames.size() - 1).isFrameEnd();
    }

    public boolean isTenthFrameAndEnd() {
        return roundFrames.size() == 10 && lastFrameWasEnd();
    }

    public void addNextFrame() {
        if (roundFrames.size() == 9) {
            roundFrames.add(LastFrame.of());
            return;
        }
        if (roundFrames.size() != 10) {
            roundFrames.add(NormalFrame.of());
        }
    }

    public List<Frame> getRoundFrames() {
        return roundFrames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoundFrames that = (RoundFrames) o;
        return Objects.equals(roundFrames, that.roundFrames);
    }

    @Override
    public int hashCode() {

        return Objects.hash(roundFrames);
    }

    public int getFrameNumber() {
        return roundFrames.size();
    }
}
