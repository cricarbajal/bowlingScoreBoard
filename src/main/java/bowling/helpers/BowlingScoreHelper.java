package bowling.helpers;

import bowling.domain.BowlingScoreBoard;
import bowling.domain.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BowlingScoreHelper {

    public void printPlayerGameSummary(Player player) {

        System.out.print("Frame  ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + "  ");
        }
        System.out.println("\n" + player.getName());
        System.out.print("Pinfalls  ");
        for (int i = 0; i < player.getScoreBoard().getFrames().size(); i++) {
            int firstScore = player.getScoreBoard().getFrames().get(i).getScores()[0];
            int secondScore = player.getScoreBoard().getFrames().get(i).getScores()[1];
            if (player.getScoreBoard().isBonusFrame() && i == 10) {
                System.out.print("X" + "   ");
            }
            if (firstScore == 10) {
                System.out.print("X" + "   ");
            } else {
                System.out.print(firstScore + "   ");
            }
            if (firstScore + secondScore == 10 && firstScore != 10) {
                System.out.print("/" + "   ");
            } else if (firstScore == 10 && secondScore == 0) {
                System.out.print("   ");
            } else if (firstScore == 10 && secondScore == 10) {
                System.out.print("X" + "   ");
            } else {
                System.out.print(secondScore + "   ");
            }
        }
        System.out.print("\nScore   ");
        Map<Integer, Integer> frameScoreMap = player.getScoreBoard().getFrameScoreMap();
        for (int i = 0; i < frameScoreMap.size(); i++) {
            if (frameScoreMap.get(i) != null) {
                System.out.print(frameScoreMap.get(i) + "    ");
            }
        }
        System.out.println("\n");
    }

    public BowlingScoreBoard setPlayerScore(Player player, String fileText,
            FileReaderHelper readerHelper) {

        BowlingScoreBoard scoreBoard = new BowlingScoreBoard();
        String[] lines = readerHelper.slitLines(fileText);
        int linesLength = readerHelper.slitLines(fileText).length;
        for (int i = 0; i < linesLength; i++) {
            if (readerHelper.getNameFromLine(lines[i]).equalsIgnoreCase(player.getName())) {
                scoreBoard.roll(readerHelper.getScoreFromLine(lines[i]));
                scoreBoard.score();
            }
        }

        return scoreBoard;
    }

    public List<Player> determinePlayers(String fileText, FileReaderHelper readerHelper) {

        List<Player> players = new ArrayList<>();
        String[] lines = readerHelper.slitLines(fileText);
        int linesLength = readerHelper.slitLines(fileText).length;
        List<String> names = new ArrayList<>();
        for (int i = 0; i < linesLength; i++) {
            names.add(readerHelper.getNameFromLine(lines[i]));
        }
        names.stream().distinct().forEach(p -> {
            Player player = new Player();
            player.setName(p);
            players.add(player);
        });
        return players;
    }
}
