package simulator;

import java.io.File;
import java.util.Scanner;

public class FileReader {
    public static void fileReader(DRAM dram, Register PC, Register IR){
        String fn = "src/main/java/simulator/program.txt";

        try {
            File f = new File(fn);
            Scanner reader = new Scanner(f);

            dram.memNuke(); // memset all to 0

            int index = 16;


            while (reader.hasNextLine()) {
                int[] data = Helper.intToBinArray(Helper.binaryToInt(reader.nextLine()), 16);

                dram.memset(data, index);
                index += 16;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Program Loaded into Memory!");
        PC.setRegisterValue(Helper.intToBinArray(32, PC.size));
        IR.setRegisterValue(Helper.intToBinArray(dram.fetchAddress(16), IR.size));
    }

//    public static void main(String args[]) {
//        DRAM dram = new DRAM(16, 2048);
//        fileReader(dram);
//    }
}
