package bowling.helpers;

import java.io.*;


public class FileReaderHelper {

    public String readFromInputStream(String[] args)
            throws IOException {

        File inputFile = null;
        if (args.length == 1) {
            inputFile = new File(args[0]);
        } else {
            System.err.println("Invalid arguments count:" + args.length);
            System.exit(1);
        }

        String fileText = "";
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            fileText = sb.toString();
        } finally {
            br.close();
        }
        return fileText;
    }

    public String[] slitLines(String inputFile){
        return inputFile.split("\\r?\\n");
    }

    public String getNameFromLine(String line){
        return line.split(" ")[0];
    }

    public int getScoreFromLine(String line){
        String score = line.split(" ")[1];
        if(score.equalsIgnoreCase("f")){
            return 0;
        }else {
            return Integer.parseInt(line.split(" ")[1]);
        }
    }

}
