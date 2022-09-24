package simulator;

import java.io.File;
import java.util.Scanner;

public class FileReader {
    public void fileReader(DRAM dram, Register PC, Register IR){
        String fn = "/Users/mdt/Documents/program.txt";

        try {
            File f = new File(fn);
            Scanner reader = new Scanner(f);

            dram.memNuke(); // memset all to 0

            int index = 1024;


            while (reader.hasNextLine()) {
                int[] data = Helper.intToBinArray(Helper.binaryToInt(reader.nextLine()), 16);

                dram.memset(data, index);
                index += 16;
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        dram.memset(Helper.intToBinArray(5,16), 16);

        System.out.println("Program Loaded into Memory!");
        PC.setRegisterValue(Helper.intToBinArray(1024, PC.size));
        IR.setRegisterValue(Helper.intToBinArray(1040, IR.size));
    }

//    public static void main(String args[]) {
//        DRAM dram = new DRAM(16, 2048);
//        fileReader(dram);
//    }
}
