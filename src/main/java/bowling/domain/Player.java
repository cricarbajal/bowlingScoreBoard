package bowling.domain;

public class Player {
    private String name;
    private BowlingScoreBoard scoreBoard;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public BowlingScoreBoard getScoreBoard() {

        return scoreBoard;
    }

    public void setScoreBoard(BowlingScoreBoard scoreBoard) {

        this.scoreBoard = scoreBoard;
    }
}
