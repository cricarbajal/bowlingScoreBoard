package bowling;

import bowling.domain.BowlingScoreBoard;
import bowling.domain.Player;
import bowling.helpers.BowlingScoreHelper;
import bowling.helpers.FileReaderHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Application {

    public static void main(final String[] args) throws IOException {

        try {
            FileReaderHelper readerHelper = new FileReaderHelper();
            BowlingScoreHelper scoreHelper = new BowlingScoreHelper();
            String fileText = readerHelper.readFromInputStream(args);
            List<Player> players = scoreHelper.determinePlayers(fileText, readerHelper);
            players.forEach(player -> {
                BowlingScoreBoard scoreBoard =
                        scoreHelper.setPlayerScore(player, fileText, readerHelper);
                player.setScoreBoard(scoreBoard);
                scoreHelper.printPlayerGameSummary(player);
            });
        } catch (Exception e) {
            System.err.println("Invalid arguments count:" + args.length);
            System.exit(1);
        }
    }
}