package integrationTests;

import bowling.Application;
import bowling.domain.BowlingScoreBoard;
import bowling.domain.Player;
import bowling.helpers.BowlingScoreHelper;
import bowling.helpers.FileReaderHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class BowlingScoreBoardTest {

    FileReaderHelper readerHelper;
    BowlingScoreHelper scoreHelper;
    File file;

    @Before
    public void setup(){
        String fileName = "input.txt";
        ClassLoader classLoader = BowlingScoreBoardTest.class.getClassLoader();
        file = new File(classLoader.getResource(fileName).getPath());
        readerHelper = new FileReaderHelper();
        scoreHelper = new BowlingScoreHelper();
    }

    @Test
    public void testCompleteBowlingGame() throws IOException {
        String[] args = new String[1];
        args[0] = file.toString();
        String textFile = readerHelper.readFromInputStream(args);
        List<Player> players = scoreHelper.determinePlayers(textFile,readerHelper);
        Assert.assertEquals(2, players.size());

        players.forEach(player -> {
            BowlingScoreBoard scoreBoard = scoreHelper.setPlayerScore(player,textFile,readerHelper);
            player.setScoreBoard(scoreBoard);
        });
        int firstPlayerScore = players.get(0).getScoreBoard().getScoreCounter();
        int secondPlayerScore = players.get(1).getScoreBoard().getScoreCounter();
        Assert.assertEquals(167, firstPlayerScore);
        Assert.assertEquals(151, secondPlayerScore);

    }
}
