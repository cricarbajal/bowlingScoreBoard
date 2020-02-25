package bowling;

import bowling.domain.BowlingScoreBoard;
import bowling.domain.Player;
import bowling.helpers.FileReaderHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Application {

    public static void main(final String[] args) throws IOException {
        FileReaderHelper readerHelper = new FileReaderHelper();
        String fileText = readerHelper.readFromInputStream(args);
        List<Player> players = determinePlayers(fileText,readerHelper);
        players.forEach(player -> {
            BowlingScoreBoard scoreBoard = setPlayerScore(player,fileText,readerHelper);
            player.setScoreBoard(scoreBoard);
        });
        players.forEach(player -> printPlayerGameSummary(player));
    }

    public static void printPlayerGameSummary(Player player){
        System.out.print("Frame  ");
        for (int i = 1; i <= 10 ; i++) {
            System.out.print(i + "  ");
        }
        System.out.println("\n" + player.getName());
        System.out.print("Pinfalls  ");
        for (int i = 0; i < player.getScoreBoard().getFrames().size(); i++) {
            if(player.getScoreBoard().isBonusFrame() && i == 10){
                System.out.print(10 + "  ");
            }
            System.out.print(player.getScoreBoard().getFrames().get(i).getScores()[0] + "   ");
            System.out.print(player.getScoreBoard().getFrames().get(i).getScores()[1] + "   ");
        }
        System.out.print("\nScore   ");
        Map<Integer,Integer> frameScoreMap = player.getScoreBoard().getFrameScoreMap();
        for (int i = 0; i < frameScoreMap.size(); i++) {
            if(frameScoreMap.get(i) != null){
                System.out.print(frameScoreMap.get(i).intValue() + "    ");
            }
        }
        System.out.println("\n");
    }

    public static BowlingScoreBoard setPlayerScore(Player player, String fileText, FileReaderHelper readerHelper){
        BowlingScoreBoard scoreBoard = new BowlingScoreBoard();
        String[] lines = readerHelper.slitLines(fileText);
        int linesLength = readerHelper.slitLines(fileText).length;
        for (int i = 0; i < linesLength; i++) {
            if(readerHelper.getNameFromLine(lines[i]).equalsIgnoreCase(player.getName())){
                scoreBoard.roll(readerHelper.getScoreFromLine(lines[i]));
                scoreBoard.score();
            }
        }

        return scoreBoard;
    }

    public static List<Player> determinePlayers(String fileText, FileReaderHelper readerHelper){
        List<Player> players = new ArrayList<>();
        String[] lines = readerHelper.slitLines(fileText);
        int linesLength = readerHelper.slitLines(fileText).length;
        List<String> names = new ArrayList<>();
        for (int i = 0; i < linesLength; i++){
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