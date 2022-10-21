package simulator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CacheTest {
    final static int wordSize = 16;
    final static int dramSize = 2048;

    /*
    BEGIN LDR TESTING
     */
    @org.junit.jupiter.api.Test
    static void cache() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register IX = new Register(wordSize);
        Register testReg = new Register(wordSize);
        Register MAR = new Register(wordSize);
        Register MBR = new Register(wordSize);
        Register MFR = new Register(wordSize);

        // address 16
        MAR.setRegisterValue(Helper.intToBinArray(1, wordSize));

        // set mem
        for (int i = 16; i < 32; i++) {
            dram.data[i] = 1;
        }


        Instructions.LDR(dram, MAR, MBR, IX, testReg, 0, MFR);
        Instructions.LDR(dram, MAR, MBR, IX, testReg, 0, MFR);


        // after load MBR and testReg should be all 1s
        assertArrayEquals(Helper.intToBinArray((int) Math.pow(2,16) -1, wordSize), MBR.getRegisterValue());
        assertArrayEquals(Helper.intToBinArray((int) Math.pow(2,16) -1, wordSize), testReg.getRegisterValue());
    }

    @org.junit.jupiter.api.Test
    static void cache_offset() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register IX = new Register(wordSize);
        Register testReg = new Register(wordSize);
        Register MAR = new Register(wordSize);
        Register MBR = new Register(wordSize);
        Register MFR = new Register(wordSize);

        // address 16
        MAR.setRegisterValue(Helper.intToBinArray(1, wordSize));

        // set mem
        for (int i = 16; i < 32; i++) {
            dram.data[i] = 1;
        }


        Instructions.LDR(dram, MAR, MBR, IX, testReg, 0, MFR);

        MAR.setRegisterValue(Helper.intToBinArray(2, wordSize));
        Instructions.LDR(dram, MAR, MBR, IX, testReg, 0, MFR);

        MAR.setRegisterValue(Helper.intToBinArray(10, wordSize));
        Instructions.LDR(dram, MAR, MBR, IX, testReg, 0, MFR);


        // after load MBR and testReg should be all 1s
//        assertArrayEquals(Helper.intToBinArray((int) Math.pow(2,16) -1, wordSize), MBR.getRegisterValue());
//        assertArrayEquals(Helper.intToBinArray((int) Math.pow(2,16) -1, wordSize), testReg.getRegisterValue());
    }

    public static void main(String[] args) {
        cache_offset();
    }
}
