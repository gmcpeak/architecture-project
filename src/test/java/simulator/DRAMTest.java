package simulator;

import static org.junit.jupiter.api.Assertions.*;


import java.lang.Math;
import java.util.Arrays;

class DRAMTest {
    final static int wordSize = 16;
    final static int dramSize = 2048;
    @org.junit.jupiter.api.Test
     void loadTestNoIndexNoIndirect() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register IX = new Register(wordSize);
        Register testReg = new Register(wordSize);

        IX.setRegisterValue(Helper.intToBinArray(0, wordSize));
        testReg.setRegisterValue(Helper.intToBinArray((int) Math.pow(2,16) -1, wordSize));

        try {
            dram.load(16*2, null, 0, testReg);
        } catch (Exception e) {
            throw new RuntimeException("Failed DRAM load TEST");
        }

        assertEquals(Helper.arrToInt(testReg.getRegisterValue()), 0);
    }

    @org.junit.jupiter.api.Test
    void loadTestIndexNoIndirect() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register IX = new Register(wordSize);
        Register testReg = new Register(wordSize);

        IX.setRegisterValue(Helper.intToBinArray(8, wordSize));
        testReg.setRegisterValue(Helper.intToBinArray(0, wordSize));

        for (int i = 16; i < 32; i++) {
            dram.data[i] = 1;
        }

        try {
            dram.load(8, IX, 0, testReg);
        } catch (Exception e) {
            throw new RuntimeException("Failed DRAM load TEST");
        }
        int[] groundTruth = Helper.intToBinArray((int) Math.pow(2, 16) -1, wordSize);
        int[] result = testReg.getRegisterValue();
        assertArrayEquals(groundTruth, result);
    }

    @org.junit.jupiter.api.Test
    void storeTestIndexed() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register srcReg = new Register(wordSize);
        Register destReg = new Register(wordSize);
        Register IX = new Register(wordSize);

        IX.setRegisterValue(Helper.intToBinArray(8, wordSize));
        srcReg.setRegisterValue(Helper.intToBinArray(5, wordSize));
        destReg.setRegisterValue(Helper.intToBinArray(0, wordSize));

        try {
//            dram.load(8, IX, 0, testReg);
            dram.store(8, IX, 0, srcReg);
            dram.load(8, IX, 0, destReg);
        } catch (Exception e) {

            throw new RuntimeException("Failed DRAM load TEST");
        }
        int[] result =  destReg.getRegisterValue();
        int[] groundTruth = Helper.intToBinArray(5, wordSize);

        assertArrayEquals(result, groundTruth);
    }

    @org.junit.jupiter.api.Test
    void storeTest() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register srcReg = new Register(wordSize);
        Register destReg = new Register(wordSize);

        srcReg.setRegisterValue(Helper.intToBinArray(5, wordSize));
        destReg.setRegisterValue(Helper.intToBinArray(0, wordSize));
        try {
//            dram.load(8, IX, 0, testReg);
            dram.store(8, null, 0, srcReg);
            dram.load(8, null, 0, destReg);
        } catch (Exception e) {

            throw new RuntimeException("Failed DRAM load TEST");
        }
        int[] result =  destReg.getRegisterValue();
        int[] groundTruth = Helper.intToBinArray(5, wordSize);

        assertArrayEquals(result, groundTruth);
    }
//    public static void main(String[] args) {
//        loadTestIndexNoIndirect();
//    }
}