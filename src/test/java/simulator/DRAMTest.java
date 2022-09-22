package simulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import java.lang.Math;

class DRAMTest {
    final static int wordSize = 16;
    final static int dramSize = 2048;

    /*
    BEGIN LDR TESTING
     */
    @org.junit.jupiter.api.Test
    void loadTestNoIndexNoIndirect() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register IX = new Register(wordSize);
        Register testReg = new Register(wordSize);
        Register MAR = new Register(wordSize);
        Register MBR = new Register(wordSize);
        Register MFR = new Register(wordSize);

        // address 16
        MAR.setRegisterValue(Helper.intToBinArray(16, wordSize));

        // set mem
        for (int i = 16; i < 32; i++) {
            dram.data[i] = 1;
        }

        Instructions.LDR(dram, MAR, MBR, IX, testReg, 0, MFR);

        // after load MBR and testReg should be all 1s
        assertArrayEquals(Helper.intToBinArray((int) Math.pow(2,16) -1, wordSize), MBR.getRegisterValue());
        assertArrayEquals(Helper.intToBinArray((int) Math.pow(2,16) -1, wordSize), testReg.getRegisterValue());
    }
    @org.junit.jupiter.api.Test
    void loadTestIndexNoIndirect() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register IX = new Register(wordSize);
        Register testReg = new Register(wordSize);
        Register MAR = new Register(wordSize);
        Register MBR = new Register(wordSize);
        Register MFR = new Register(wordSize);

        // address 16
        MAR.setRegisterValue(Helper.intToBinArray(16, wordSize));

        // offset of 15
        IX.setRegisterValue(Helper.intToBinArray(15, wordSize));

        // set mem
        for (int i = 16; i < 32; i++) {
            dram.data[i] = 1;
        }

        Instructions.LDR(dram, MAR, MBR, IX, testReg, 0, MFR);

        // after load MBR and testReg should be shifted by 15 bits. [1,0,...,0] = 2^15
        assertArrayEquals(Helper.intToBinArray((int)Math.pow(2, 15), wordSize), MBR.getRegisterValue());
        assertArrayEquals(Helper.intToBinArray((int)Math.pow(2, 15), wordSize), testReg.getRegisterValue());
    }

    @org.junit.jupiter.api.Test
    void loadIndirectNoIndex() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register IX = new Register(wordSize);
        Register testReg = new Register(wordSize);
        Register MAR = new Register(wordSize);
        Register MBR = new Register(wordSize);
        Register MFR = new Register(wordSize);

        // address 16
        MAR.setRegisterValue(Helper.intToBinArray(16, wordSize));

        // set mem to 48, the other address I want to read :)
        int[] otherMemoryAddress = Helper.intToBinArray(48, wordSize);
        for (int i = 16; i < 32; i++) {
            dram.data[i] = otherMemoryAddress[i-16];
        }

        // put 5 at 48, the other location
        int[] value = Helper.intToBinArray(5, wordSize);
        for (int i = 48; i < 48+16; i++) {
            dram.data[i] = value[i-48];
        }

        Instructions.LDR(dram, MAR, MBR, null, testReg, 1, MFR);

        // after load MBR and testReg should be 5, value at the location stored at 16
        assertArrayEquals(Helper.intToBinArray(5, wordSize), MBR.getRegisterValue());
        assertArrayEquals(Helper.intToBinArray(5, wordSize), testReg.getRegisterValue());
    }

    @Test
    void loadIndirectIndex() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register IX = new Register(wordSize);
        Register testReg = new Register(wordSize);
        Register MAR = new Register(wordSize);
        Register MBR = new Register(wordSize);
        Register MFR = new Register(wordSize);

        // address 16
        MAR.setRegisterValue(Helper.intToBinArray(16, wordSize));
        IX.setRegisterValue(Helper.intToBinArray(100, wordSize));

        int[] IXvalue = Helper.intToBinArray(1, wordSize);
        for (int i = 100; i < 116; i++) {
            dram.data[i] = IXvalue[i-100];
        }

        // set mem to 48, the other address I want to read :). poimnter to 48
        int[] otherMemoryAddress = Helper.intToBinArray(48, wordSize);
        for (int i = 16; i < 32; i++) {
            dram.data[i] = otherMemoryAddress[i-16];
        }


        // put 5 at 48, the other location
        int[] value = Helper.intToBinArray(5, wordSize);
        for (int i = 48; i < 48+16; i++) {
            dram.data[i] = value[i-48];
        }

        Instructions.LDR(dram, MAR, MBR, IX, testReg, 1, MFR);

        // after load MBR and testReg should be 5, value at the location stored at 16
        assertArrayEquals(Helper.intToBinArray(10, wordSize), MBR.getRegisterValue());
        assertArrayEquals(Helper.intToBinArray(10, wordSize), testReg.getRegisterValue());
    }
    /*
    END LDR TESTING
     */


    /*
    BEGIN LDA TESTING
     */
//    @org.junit.jupiter.api.Test
    void LDANoIndexNoIndirect() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register IX = new Register(wordSize);
        Register testReg = new Register(wordSize);
        Register MAR = new Register(wordSize);
        Register MBR = new Register(wordSize);
        Register MFR = new Register(wordSize);

        // address 16
        MAR.setRegisterValue(Helper.intToBinArray(16, wordSize));

        // set mem
        for (int i = 16; i < 32; i++) {
            dram.data[i] = 1;
        }

        Instructions.LDA(dram, MAR, MBR, IX, testReg, 0, MFR);

        // after load MBR and testReg should 16, the address we wanted to load from
        assertArrayEquals(Helper.intToBinArray(16, wordSize), MBR.getRegisterValue());
//        assertArrayEquals(Helper.intToBinArray((int) Math.pow(2,16) -1, wordSize), testReg.getRegisterValue());
    }
    @org.junit.jupiter.api.Test
    void LDAIndexNoIndirect() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register IX = new Register(wordSize);
        Register testReg = new Register(wordSize);
        Register MAR = new Register(wordSize);
        Register MBR = new Register(wordSize);
        Register MFR = new Register(wordSize);

        // address 16
        MAR.setRegisterValue(Helper.intToBinArray(16, wordSize));

        // offset of 15
        IX.setRegisterValue(Helper.intToBinArray(15, wordSize));

        // set mem
        for (int i = 16; i < 32; i++) {
            dram.data[i] = 1;
        }

        Instructions.LDA(dram, MAR, MBR, IX, testReg, 0, MFR);

        // after load MBR and testReg should be orginal address + 15
        assertArrayEquals(Helper.intToBinArray(16+15, wordSize), MBR.getRegisterValue());
    }
    /*
    END LDA TESTING
     */

    /*
    BEGIN LDX TESTING
     */
    @org.junit.jupiter.api.Test
    void LDXNoIndexNoIndirect() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register IX = new Register(wordSize);
        Register testReg = new Register(wordSize);
        Register MAR = new Register(wordSize);
        Register MBR = new Register(wordSize);
        Register MFR = new Register(wordSize);

        // address 16
        MAR.setRegisterValue(Helper.intToBinArray(16, wordSize));

        // set mem
        for (int i = 16; i < 32; i++) {
            dram.data[i] = 1;
        }

        Instructions.LDX(dram, MAR, MBR, IX, 0, MFR);

        // after load MBR and testReg should be all 1s
        assertArrayEquals(Helper.intToBinArray((int) Math.pow(2,16) -1, wordSize), MBR.getRegisterValue());
        assertArrayEquals(Helper.intToBinArray((int) Math.pow(2,16) -1, wordSize), IX.getRegisterValue());
    }
    @org.junit.jupiter.api.Test
    void LDXIndexNoIndirect() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register IX = new Register(wordSize);
        Register testReg = new Register(wordSize);
        Register MAR = new Register(wordSize);
        Register MBR = new Register(wordSize);
        Register MFR = new Register(wordSize);

        // address 16
        MAR.setRegisterValue(Helper.intToBinArray(16, wordSize));

        // offset of 15
        IX.setRegisterValue(Helper.intToBinArray(15, wordSize));

        // set mem
        for (int i = 16; i < 32; i++) {
            dram.data[i] = 1;
        }

        Instructions.LDX(dram, MAR, MBR, IX, 0, MFR);

        // after load MBR and testReg should be shifted by 15 bits. [1,0,...,0] = 2^15
        assertArrayEquals(Helper.intToBinArray((int)Math.pow(2, 16)-1, wordSize), MBR.getRegisterValue());
        assertArrayEquals(Helper.intToBinArray((int)Math.pow(2, 16)-1, wordSize), IX.getRegisterValue());
    }

    @org.junit.jupiter.api.Test
    void LDAIndirectNoIndex() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register IX = new Register(wordSize);
        Register testReg = new Register(wordSize);
        Register MAR = new Register(wordSize);
        Register MBR = new Register(wordSize);
        Register MFR = new Register(wordSize);

        // address 16
        MAR.setRegisterValue(Helper.intToBinArray(16, wordSize));

        // set mem to 48, the other address I want to read :)
        int[] otherMemoryAddress = Helper.intToBinArray(48, wordSize);
        for (int i = 16; i < 32; i++) {
            dram.data[i] = otherMemoryAddress[i-16];
        }

        // put 5 at 48, the other location
        int[] value = Helper.intToBinArray(5, wordSize);
        for (int i = 48; i < 48+16; i++) {
            dram.data[i] = value[i-48];
        }

        Instructions.LDA(dram, MAR, MBR, null, testReg, 1, MFR);

        // after load MBR and testReg should be 48, value at the location stored at 16 (effective addres with indirect)
        assertArrayEquals(Helper.intToBinArray(48, wordSize), MBR.getRegisterValue());
        assertArrayEquals(Helper.intToBinArray(48, wordSize), testReg.getRegisterValue());
    }

    @Test
    void LDAIndirectIndex() {
        DRAM dram = new DRAM(wordSize, dramSize);
        Register IX = new Register(wordSize);
        Register testReg = new Register(wordSize);
        Register MAR = new Register(wordSize);
        Register MBR = new Register(wordSize);
        Register MFR = new Register(wordSize);

        // address 16
        MAR.setRegisterValue(Helper.intToBinArray(16, wordSize));
        IX.setRegisterValue(Helper.intToBinArray(100, wordSize));

        int[] IXvalue = Helper.intToBinArray(1, wordSize);
        for (int i = 100; i < 116; i++) {
            dram.data[i] = IXvalue[i-100];
        }

        // set mem to 48, the other address I want to read :). poimnter to 48
        int[] otherMemoryAddress = Helper.intToBinArray(48, wordSize);
        for (int i = 16; i < 32; i++) {
            dram.data[i] = otherMemoryAddress[i-16];
        }


        // put 5 at 48, the other location
        int[] value = Helper.intToBinArray(5, wordSize);
        for (int i = 48; i < 48+16; i++) {
            dram.data[i] = value[i-48];
        }

        Instructions.LDA(dram, MAR, MBR, IX, testReg, 1, MFR);

        // after load MBR and testReg should be 49, value at the location stored at 16 (effective addres with indirect) +
        // the value (1) address stored in the IX register (100)
        assertArrayEquals(Helper.intToBinArray(49, wordSize), MBR.getRegisterValue());
        assertArrayEquals(Helper.intToBinArray(49, wordSize), testReg.getRegisterValue());
    }



    /*
    END LDX TESTING
     */

//    @org.junit.jupiter.api.Test
//    static void storeNoIndex() {
//        DRAM dram = new DRAM(wordSize, dramSize);
//        Register IX = new Register(wordSize);
//        Register testReg = new Register(wordSize);
//        Register MAR = new Register(wordSize);
//        Register MBR = new Register(wordSize);
//        Register MFR = new Register(wordSize);
//
//        // address 16
//        MAR.setRegisterValue(Helper.intToBinArray(16, wordSize));
//
//        // value to store
//        MBR.setRegisterValue(Helper.intToBinArray((int)Math.pow(2,16)-1, wordSize));
//
//        Instructions.executeSTORE(dram, MAR, MBR, IX, testReg, 0, MFR, "R");
//
//
//        // set mem
//        int[] data = new int[wordSize];
//        for (int i = 16; i < 32; i++) {
//            data[i-16] = dram.data[i];
//        }
//
//
//        // after store data should be all 1s
//        assertArrayEquals(Helper.intToBinArray((int) Math.pow(2,16) -1, wordSize), data);
//    }
//
//    @org.junit.jupiter.api.Test
//    static void storeNoIndex() {
//        DRAM dram = new DRAM(wordSize, dramSize);
//        Register IX = new Register(wordSize);
//        Register testReg = new Register(wordSize);
//        Register MAR = new Register(wordSize);
//        Register MBR = new Register(wordSize);
//        Register MFR = new Register(wordSize);
//
//        // address 16
//        MAR.setRegisterValue(Helper.intToBinArray(16, wordSize));
//
//        // value to store
//        MBR.setRegisterValue(Helper.intToBinArray((int)Math.pow(2,16)-1, wordSize));
//
//        Instructions.executeSTORE(dram, MAR, MBR, IX, testReg, 0, MFR, "R");
//
//
//        // set mem
//        int[] data = new int[wordSize];
//        for (int i = 16; i < 32; i++) {
//            data[i-16] = dram.data[i];
//        }
//
//
//        // after store data should be all 1s
//        assertArrayEquals(Helper.intToBinArray((int) Math.pow(2,16) -1, wordSize), data);
//    }
//
//    @org.junit.jupiter.api.Test
//    static void storeIndex() {
//        DRAM dram = new DRAM(wordSize, dramSize);
//        Register IX = new Register(wordSize);
//        Register testReg = new Register(wordSize);
//        Register MAR = new Register(wordSize);
//        Register MBR = new Register(wordSize);
//        Register MFR = new Register(wordSize);
//
//        // address 16
//        MAR.setRegisterValue(Helper.intToBinArray(16, wordSize));
//
//        // value to store
//        MBR.setRegisterValue(Helper.intToBinArray((int)Math.pow(2,16)-1, wordSize));
//
//        // set offset
//        IX.setRegisterValue(Helper.intToBinArray(15, wordSize));
//
//        Instructions.executeSTORE(dram, MAR, MBR, IX, testReg, 0, MFR, "R");
//
//
//        // set mem
//        int[] data = new int[wordSize];
//        for (int i = 16; i < 32; i++) {
//            data[i-16] = dram.data[i];
//        }
//
//
//        // after store data should be all 1s
//        assertArrayEquals(Helper.intToBinArray((int) Math.pow(2,16) -1, wordSize), data);
//    }
//
//    @org.junit.jupiter.api.Test
//    void storeTestIndexed() {
//        DRAM dram = new DRAM(wordSize, dramSize);
//        Register srcReg = new Register(wordSize);
//        Register destReg = new Register(wordSize);
//        Register IX = new Register(wordSize);
//
//        IX.setRegisterValue(Helper.intToBinArray(8, wordSize));
//        srcReg.setRegisterValue(Helper.intToBinArray(5, wordSize));
//        destReg.setRegisterValue(Helper.intToBinArray(0, wordSize));
//
//        try {
////            dram.load(8, IX, 0, testReg);
//            dram.STR(8, IX, 0, srcReg);
//            dram.LDR(8, IX, 0, destReg);
//        } catch (Exception e) {
//
//            throw new RuntimeException("Failed DRAM load TEST");
//        }
//        int[] result =  destReg.getRegisterValue();
//        int[] groundTruth = Helper.intToBinArray(5, wordSize);
//
//        assertArrayEquals(result, groundTruth);
//    }
//
//    @org.junit.jupiter.api.Test
//    void storeTest() {
//        DRAM dram = new DRAM(wordSize, dramSize);
//        Register srcReg = new Register(wordSize);
//        Register destReg = new Register(wordSize);
//
//        srcReg.setRegisterValue(Helper.intToBinArray(5, wordSize));
//        destReg.setRegisterValue(Helper.intToBinArray(0, wordSize));
//        try {
////            dram.load(8, IX, 0, testReg);
//            dram.STR(8, null, 0, srcReg);
//            dram.LDR(8, null, 0, destReg);
//        } catch (Exception e) {
//
//            throw new RuntimeException("Failed DRAM load TEST");
//        }
//        int[] result =  destReg.getRegisterValue();
//        int[] groundTruth = Helper.intToBinArray(5, wordSize);
//
//        assertArrayEquals(result, groundTruth);
//    }
//
//    @org.junit.jupiter.api.Test
//    void LDXTest() {
//        DRAM dram = new DRAM(wordSize, dramSize);
//        Register IX = new Register(wordSize);
//
//        // set IX to 0
//        IX.setRegisterValue(Helper.intToBinArray(0, wordSize));
//
//        // IX should be 16 at the end
//        try{
//            dram.LDX(16, IX, 0);
//        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        int IXVal = Helper.arrToInt(IX.getRegisterValue());
//        int truth = 16;
//
//        assertEquals(truth, IXVal);
//
//    }
//
//    @org.junit.jupiter.api.Test
//    void LDATest() {
//        DRAM dram = new DRAM(wordSize, dramSize);
//        Register r = new Register(wordSize);
//
//        // set IX to 0
//        r.setRegisterValue(Helper.intToBinArray(0, wordSize));
//
//        // IX should be 16 at the end
//        try{
//            dram.LDA(16, null, 0, r);
//        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        int IXVal = Helper.arrToInt(r.getRegisterValue());
//        int truth = 16;
//
//        assertEquals(truth, IXVal);
//
//    }
//
//
//    @org.junit.jupiter.api.Test()
//    void outOfBoundsTest() {
//        DRAM dram = new DRAM(wordSize, dramSize);
//        Register destReg = new Register(wordSize);
//
//        Exception exception = assertThrows(Exception.class, () -> {
//            dram.LDR(40000, null, 0, destReg);
//        });
//    }
//
//    @org.junit.jupiter.api.Test()
//    static void reservedMemoryTest() {
//        DRAM dram = new DRAM(wordSize, dramSize);
//        Register destReg = new Register(wordSize);
//
//        for (int i = 0; i <= 5; i++) {
//            int finalI = i;
//            Exception exception = assertThrows(Exception.class, () -> {
//                dram.LDR(finalI, null, 0, destReg);
//            });
//        }
//
//
//    }
//    public static void main(String[] args) {
//        loadIndirectIndex();
//    }
}