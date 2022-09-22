package simulator;

import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.*;


import java.lang.Math;

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
            dram.LDR(16*2, null, 0, testReg);
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
            dram.LDR(8, IX, 0, testReg);
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
            dram.STR(8, IX, 0, srcReg);
            dram.LDR(8, IX, 0, destReg);
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
            dram.STR(8, null, 0, srcReg);
            dram.LDR(8, null, 0, destReg);
        } catch (Exception e) {

            throw new RuntimeException("Failed DRAM load TEST");
        }
        int[] result =  destReg.getRegisterValue();
        int[] groundTruth = Helper.intToBinArray(5, wordSize);

        assertArrayEquals(result, groundTruth);
    }

    @org.junit.jupiter.api.Test
    void LDATest() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register IX = new Register(wordSize);

        // set IX to 0
        IX.setRegisterValue(Helper.intToBinArray(0, wordSize));

        // IX should be 16 at the end
        try{
            dram.LDA(16, IX, 0);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        int IXVal = Helper.arrToInt(IX.getRegisterValue());
        int truth = 16;

        assertEquals(truth, IXVal);

    }


    @org.junit.jupiter.api.Test()
    void outOfBoundsTest() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register destReg = new Register(wordSize);

        Exception exception = assertThrows(Exception.class, () -> {
            dram.LDR(40000, null, 0, destReg);
        });
    }

    @org.junit.jupiter.api.Test()
    static void reservedMemoryTest() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register destReg = new Register(wordSize);

        for (int i = 0; i <= 5; i++) {
            int finalI = i;
            Exception exception = assertThrows(Exception.class, () -> {
                dram.LDR(finalI, null, 0, destReg);
            });
        }


    }
//    public static void main(String[] args) {
//        reservedMemoryTest();
//    }
}