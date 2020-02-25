import bowling.domain.Bowling;
import bowling.domain.BowlingScoreBoard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class BowlingTest {

    private Bowling game;
    private static final int MAX_ATTEMPTS = 20;

    @Before
    public void setUp() {
        game = new BowlingScoreBoard();
    }

    @Test
    public void testScoreNoSpareOrStrike() {

        game.roll(4);
        game.roll(4);
        int score  = game.score();
        Assert.assertEquals(8, score);
    }

    @Test
    public void testSpare() {

        game.roll(4);
        game.roll(6);
        int score  = game.score();
        Assert.assertEquals(10, score);
        game.roll(5);
        game.roll(2);
        score  += game.score();
        Assert.assertEquals(17, score);
    }

    @Test
    public void testStrikeOnSecondAttempt() {

        game.roll(0);
        game.roll(10);
        int score  = game.score();
        Assert.assertEquals(10, score);
        game.roll(5);
        game.roll(4);
        score += game.score();
        Assert.assertEquals(29, score);
    }

    @Test
    public void testStrikeOnFirstAttempt() {

        game.roll(10);
        int score  = game.score();
        Assert.assertEquals(10, score);
        game.roll(5);
        game.roll(5);
        score += game.score();
        Assert.assertEquals(30, score);
    }

    @Test
    public void testStrikeEveryRoll() {

        for (int i = 0; i < 10 ; i++) {
            game.roll(10);
        }
        int score = game.score();
        Assert.assertEquals(300, score);
    }

    @Test
    public void testLastFrameSpare() {

        for (int i = 0; i < 10 ; i++) {
            game.roll(5);
            game.roll(5);
        }
        game.roll(5);
        int score = game.score();
        Assert.assertEquals(15, score);
    }

    @Test
    public void testLastFrameStrike() {

        for (int i = 0; i < 10 ; i++) {
            game.roll(10);
        }
        game.roll(3);
        game.roll(4);
        int score = game.score();
        Assert.assertEquals(17, score);
    }

    @Test(expected = BowlingScoreBoard.BowlingException.class)
    public void testLastFrameNoStrike() {

        for (int i = 0; i < 10 ; i++) {
            game.roll(3);
            game.roll(5);
        }
        game.roll(3);
        game.roll(4);
    }

    @Test(expected = BowlingScoreBoard.BowlingException.class)
    public void testPlayMoreThanAllFrames() {

        for (int i = 0; i <= MAX_ATTEMPTS  ; i++) {
            game.roll(i/10);
        }
    }

    @Test(expected = BowlingScoreBoard.BowlingException.class)
    public void testIllegalBowlException() {

        game.roll(200);
    }
}
