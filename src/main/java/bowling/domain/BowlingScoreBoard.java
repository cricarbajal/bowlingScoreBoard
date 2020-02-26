package bowling.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BowlingScoreBoard implements Bowling {

    private final List<Frame> frames;
    private static final int MAX_FRAMES = 10;
    private static final int MAX_PINS = 10;
    private static final int MAX_ATTEMPTS_PER_FRAME = 2;
    private int frameCounter = 0;
    private int strikeCounter = 0;
    private int scoreCounter = 0;
    private static final int ALL_STRIKE_SCORE = 300;
    Map<Integer, Integer> frameScoreMap = new HashMap<>();

    public BowlingScoreBoard() {

        frames = new ArrayList<Frame>(MAX_FRAMES);

        for (int i = 0; i < MAX_FRAMES; i++) {
            frames.add(new Frame());
        }
    }

    @Override
    public void roll(int noOfPins) {

        if (noOfPins > MAX_PINS) {
            throw new BowlingException("illegal argument " + noOfPins);
        }

        Frame frame = getFrame();

        if (frame == null) {
            if(strikeCounter == 11){
                Frame bonus = new Frame();
                bonus.setFirstScore(10);
                bonus.setSecondScore(10);
                frames.add(bonus);
                generateAllStrikeMap();
                return;
            }
            throw new BowlingException("all attempts exhausted - start new game");
        }

        frame.setScore(noOfPins);

        if (isBonusFrame()) {
            Frame prev = getPreviousFrame();
            // restrict to one attempt, when last frame was spare
            if (prev.isSpare()) {
                frame.limitToOneAttempt();
            }
        }

    }

    /**
     * returns a frame and moves to next frame if current has used all the attempts
     */
    private Frame getFrame() {

        Frame frame = getCurrentFrame();

        if (frame.isDone()) {

            // new bonus frame
            if (isLastFrame() && (frame.isSpare() || frame.isStrike())) {
                Frame bonus = new Frame();
                frames.add(bonus);
                frameCounter++;
                return bonus;
            }

            frameCounter++;
            if (frameCounter == MAX_FRAMES || isBonusFrame()) {
                return null;
            }

            frame = getCurrentFrame();
        }

        return frame;
    }

    @Override
    public int score() {

        int score;

        if (frameCounter == 0) {
            Frame curr = getCurrentFrame();
            return curr.score();
        } else {
            if (isLastFrame() && isAllStrikes()) {
                return ALL_STRIKE_SCORE;
            }
            Frame curr = getCurrentFrame();
            Frame prev = getPreviousFrame();

            score = curr.score();

            if (isBonusFrame()) {
                Frame prevPrev = frames.get(frameCounter - 2);
                if (prevPrev.isStrike() && curr.noAttempts == 1) {
                    score = 20 + curr.getFirstScore();
                    scoreCounter += score;
                    frameScoreMap.put(frameCounter - 2, scoreCounter);
                } else if (prev.isStrike() && curr.noAttempts == MAX_ATTEMPTS_PER_FRAME) {
                    score = (10 + curr.getFirstScore() + curr.getSecondScore());
                    scoreCounter += score;
                    frameScoreMap.put(frameCounter - 1, scoreCounter);
                } else if (curr.noAttempts == MAX_ATTEMPTS_PER_FRAME) {
                    scoreCounter += score;
                }
                return prev.score() + curr.score();
            }

            if (prev.isSpare() && curr.noAttempts == 1) {
                score = 10 + curr.getFirstScore();
                scoreCounter += score;
                frameScoreMap.put(frameCounter - 1, scoreCounter);
            } else if (prev.isStrike() && curr.noAttempts == MAX_ATTEMPTS_PER_FRAME) {
                if (curr.isStrike()) {
                    Frame prevPrev = frameCounter > 2 ? frames.get(frameCounter - 2) : null;
                    if (prevPrev != null && prevPrev.isStrike()) {
                        score = 20 + curr.score();
                        scoreCounter += score;
                        frameScoreMap.put(frameCounter - 2, scoreCounter);

                    }
                } else {
                    Frame prevPrev =  frameCounter > 2 ? frames.get(frameCounter - 2) : null;
                    if(prevPrev != null && prevPrev.isStrike()){
                        scoreCounter+= 20 + curr.getFirstScore();
                        frameScoreMap.put(frameCounter -2, scoreCounter);
                    }
                    score = (10 + curr.getFirstScore() + curr.getSecondScore());
                    scoreCounter += score;
                    frameScoreMap.put(frameCounter - 1, scoreCounter);

                }
            }
            if (curr.noAttempts == MAX_ATTEMPTS_PER_FRAME && (!curr.isStrike && !curr.isSpare())) {
                scoreCounter += curr.score();
                frameScoreMap.put(frameCounter, scoreCounter);

            }

        }
        return score;
    }

    private Frame getPreviousFrame() {

        return frames.get(frameCounter - 1);
    }

    private Frame getCurrentFrame() {

        return frames.get(frameCounter);
    }

    private boolean isAllStrikes() {

        return strikeCounter == MAX_FRAMES;
    }

    public boolean isBonusFrame() {

        return frames.size() > MAX_FRAMES;
    }

    private boolean isLastFrame() {

        return frameCounter == MAX_FRAMES - 1;
    }

    public List<Frame> getFrames() {

        return frames;
    }

    public Map<Integer, Integer> getFrameScoreMap() {

        return frameScoreMap;
    }

    public int getScoreCounter() {

        return scoreCounter;
    }

    private void generateAllStrikeMap(){
        int points = 30;
        for (int i = 0; i < 10; i++) {
            frameScoreMap.put(i,points);
            points += 30;
        }
    }

    public class Frame {

        private int[] scores = new int[MAX_ATTEMPTS_PER_FRAME];
        private int noOfPins = 10;
        private int noAttempts = 0;
        private boolean isStrike = false;

        private boolean isSpare() {

            return noOfPins == 0 && noAttempts == MAX_ATTEMPTS_PER_FRAME && !isStrike;
        }

        public boolean isStrike() {

            return noOfPins == 0 && noAttempts >= MAX_ATTEMPTS_PER_FRAME && isStrike;
        }

        private boolean isDone() {

            return noAttempts >= MAX_ATTEMPTS_PER_FRAME;
        }

        private void setScore(int score) {

            scores[noAttempts++] = score;
            noOfPins -= score; // keep track of remaining pins/frame

            if (score == MAX_PINS) {
                isStrike = true;
                strikeCounter++;
                noAttempts++;
            }
        }

        private void limitToOneAttempt() {

            scores[1] = 0;
            noAttempts++;
        }

        private int score() {

            return scores[0] + scores[1];
        }

        private int getFirstScore() {

            return scores[0];
        }

        private int getSecondScore() {

            return scores[1];
        }

        private void setFirstScore(int newScore) {

            scores[0] = newScore;
        }

        private void setSecondScore(int newScore) {

            scores[1] = newScore;
        }

        public int[] getScores() {

            return scores;
        }

    }

    public class BowlingException extends RuntimeException {

        BowlingException(String message) {
            super(message);
        }
    }
}
