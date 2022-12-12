package simulator;

import java.io.File;
import java.util.Scanner;

public class FileReader {
    public static void fileReader(DRAM dram, Register PC, Register IR){
        String fn = "./PROGRAM_1.txt";

        try {
            File f = new File(fn);
            Scanner reader = new Scanner(f);

            dram.memNuke(); // memset all to 0

            int word = 25;

            String t2 = "1100010000000000";
            while (reader.hasNextLine()) {
                int[] data = Helper.intToBinArray(Helper.binaryToInt(reader.nextLine()), 16);
                if (word == 31) {
                    for (int i = 0; i < 16; i++){
                        char x = t2.charAt(i);
                        data[i] = Integer.parseInt(Character.toString(x));
                    }
                }
                dram.memset(data, word*16);
                word += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Program Loaded into Memory!");
        PC.setRegisterValue(Helper.intToBinArray(26, PC.size));
        IR.setRegisterValue(Helper.intToBinArray(dram.fetchAddress(25*16), IR.size));
    }

//    public static void main(String args[]) {
//        DRAM dram = new DRAM(16, 2048);
//        fileReader(dram);
//    }
}
