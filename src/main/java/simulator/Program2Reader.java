package simulator;
import javax.swing.*;
import java.io.File;
import java.util.*;

public class Program2Reader {
    public static void readIntoMem(Computer c, JLabel printer, JTextField in_field, JButton write_button) {
        String fn = "./PROGRAM_2.txt";

        try {
            File f = new File(fn);
            Scanner reader = new Scanner(f);

//            c.dram.memNuke(); // memset all to 0
            int word = 26;
            c.PC.setRegisterValue(Helper.intToBinArray(26, c.PC.size));
            c.IR.setRegisterValue(Helper.intToBinArray(c.dram.fetchAddress(25*16), c.IR.size));
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
//                System.out.println(line);
                int[] data = Helper.intToBinArray(Helper.binaryToInt(line), 16);
                System.out.println(word);
                c.dram.memset(data, word*16);
                word += 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        // acquire p2 lock
        // do the rest of this
    }

    // Func to do the actual shit

    public static void run(String word, Computer c){
        String fn = "./words.txt";

        int sentenceCount = 0;
        int wordInSentence = 0;

        int instruction_counter = 0;

        try {
            File f = new File(fn);
            Scanner reader = new Scanner(f);
            int new_PC = 0;

//            c.dram.memNuke(); // memset all to 0
            c.PC.setRegisterValue(Helper.intToBinArray(26, c.PC.size));
            c.IR.setRegisterValue(Helper.intToBinArray(c.dram.fetchAddress(25*16), c.IR.size));
            String input = "";
            while (reader.hasNextLine()) {
                input = input + reader.nextLine().trim();
                instruction_counter ++;

                new_PC = (100 + instruction_counter) % 620;
                c.PC.setRegisterValue(Helper.intToBinArray(new_PC, c.PC.size));
            }
            instruction_counter ++;

            c.IR.setRegisterValue(Helper.intToBinArray( Helper.arrToInt(c.dram.fetchBinaryValue(new_PC * 16)) , c.IR.size));
            new_PC = (100 + instruction_counter) % 620;
            c.PC.setRegisterValue(Helper.intToBinArray(new_PC, c.PC.size));

            String[] sentences = input.split("[.]");
            String target_sen = "";
            for (int i = 0; i < sentences.length; i++) {
                instruction_counter ++;
                c.IR.setRegisterValue(Helper.intToBinArray( Helper.arrToInt(c.dram.fetchBinaryValue(new_PC * 16)) , c.IR.size));
                new_PC = (100 + instruction_counter) % 620;
                c.PC.setRegisterValue(Helper.intToBinArray(new_PC, c.PC.size));
                if (sentences[i].contains(word)) {
                    instruction_counter ++;
                    c.IR.setRegisterValue(Helper.intToBinArray( Helper.arrToInt(c.dram.fetchBinaryValue(new_PC * 16)) , c.IR.size));
                    new_PC = (100 + instruction_counter) % 620;
                    c.PC.setRegisterValue(Helper.intToBinArray(new_PC, c.PC.size));
                    sentenceCount = i+1;
                    target_sen = sentences[i];
                    break;
                }
            }
            instruction_counter ++;
            c.IR.setRegisterValue(Helper.intToBinArray( Helper.arrToInt(c.dram.fetchBinaryValue(new_PC * 16)) , c.IR.size));
            new_PC = (100 + instruction_counter) % 620;
            c.PC.setRegisterValue(Helper.intToBinArray(new_PC, c.PC.size));
            target_sen = target_sen.replaceAll("[^a-zA-Z ]", "").toLowerCase();
            String[] target_words = target_sen.split(" ");
            for (int i = 0; i < target_words.length; i++) {
                instruction_counter ++;
                c.IR.setRegisterValue(Helper.intToBinArray( Helper.arrToInt(c.dram.fetchBinaryValue(new_PC * 16)) , c.IR.size));
                new_PC = (100 + instruction_counter) % 620;
                c.PC.setRegisterValue(Helper.intToBinArray(new_PC, c.PC.size));
                if (target_words[i].compareTo(word) == 0) {
                    instruction_counter ++;
                    c.IR.setRegisterValue(Helper.intToBinArray( Helper.arrToInt(c.dram.fetchBinaryValue(new_PC * 16)) , c.IR.size));
                    new_PC = (100 + instruction_counter) % 620;
                    c.PC.setRegisterValue(Helper.intToBinArray(new_PC, c.PC.size));
                    wordInSentence = i+1;
                    break;
                }
            }
            instruction_counter ++;
            c.IR.setRegisterValue(Helper.intToBinArray( Helper.arrToInt(c.dram.fetchBinaryValue(new_PC * 16)) , c.IR.size));
            new_PC = (100 + instruction_counter) % 620;
            c.PC.setRegisterValue(Helper.intToBinArray(new_PC, c.PC.size));
            if (sentenceCount != 0) {
                c.buf = "Word: \""+ word + ".\" Found in sentence #" + String.valueOf(sentenceCount) + ", word #" + String.valueOf(wordInSentence);
            } else {
                c.buf = "Word not found!";
            }
//                System.out.println(line);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Computer c = new Computer(16, 2048, 4, 4);
        readIntoMem(c, null, null, null);
        run("war", new Computer(16, 2048, 4, 4) );
    }
}
