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
        MAR.setRegisterValue(Helper.intToBinArray(1, wordSize));

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


        MBR.setRegisterValue(Helper.intToBinArray((int)Math.pow(2, 16)-1, MBR.size));
        testReg.setRegisterValue(Helper.intToBinArray((int)Math.pow(2, 16)-1, testReg.size));
        // address 16
        MAR.setRegisterValue(Helper.intToBinArray(1, wordSize));

        // offset of 15
        IX.setRegisterValue(Helper.intToBinArray(1, wordSize));

        // set mem
        for (int i = 16; i < 32; i++) {
            dram.data[i] = 1;
        }

        Instructions.LDR(dram, MAR, MBR, IX, testReg, 0, MFR);

        // after load MBR and testReg should be shifted by 15 bits. [1,0,...,0] = 2^15
        assertArrayEquals(Helper.intToBinArray(0, wordSize), MBR.getRegisterValue());
        assertArrayEquals(Helper.intToBinArray(0, wordSize), testReg.getRegisterValue());
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
        MAR.setRegisterValue(Helper.intToBinArray(1, wordSize));

        // set mem to 48, the other address I want to read :)
        int[] otherMemoryAddress = Helper.intToBinArray(2, wordSize);
        for (int i = 16; i < 32; i++) {
            dram.data[i] = otherMemoryAddress[i-16];
        }

        // put 5 at 48, the other location
        int[] value = Helper.intToBinArray(5, wordSize);
        for (int i = 32; i < 32+16; i++) {
            dram.data[i] = value[i-32];
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
        MAR.setRegisterValue(Helper.intToBinArray(1, wordSize));
        IX.setRegisterValue(Helper.intToBinArray(1, wordSize));

        int[] IXvalue = Helper.intToBinArray(1, wordSize);
        for (int i = 16; i < 32; i++) {
            dram.data[i] = IXvalue[i-16];
        }

        // set mem to 48, the other address I want to read :). poimnter to 48
        int[] otherMemoryAddressValue = Helper.intToBinArray(5, wordSize);
        for (int i = 32; i < 48; i++) {
            dram.data[i] = otherMemoryAddressValue[i-32];
        }


        // put 5 at 48, the other location
//        int[] value = Helper.intToBinArray(5, wordSize);
//        for (int i = 48; i < 48+16; i++) {
//            dram.data[i] = value[i-48];
//        }

        Instructions.LDR(dram, MAR, MBR, IX, testReg, 1, MFR);

        // after load MBR and testReg should be 5, value at the location stored at 16
        assertArrayEquals(Helper.intToBinArray(5, wordSize), MBR.getRegisterValue());
        assertArrayEquals(Helper.intToBinArray(5, wordSize), testReg.getRegisterValue());
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
        MAR.setRegisterValue(Helper.intToBinArray(1, wordSize));

        // offset of 15
        IX.setRegisterValue(Helper.intToBinArray(1, wordSize));

        // set mem
        for (int i = 16; i < 32; i++) {
            dram.data[i] = 1;
        }

        Instructions.LDA(dram, MAR, MBR, IX, testReg, 0, MFR);

        // after load MBR and testReg should be orginal address + 15
        assertArrayEquals(Helper.intToBinArray(32, wordSize), MBR.getRegisterValue());
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
        MAR.setRegisterValue(Helper.intToBinArray(1, wordSize));

//        // set mem to 48, the other address I want to read :)
        int[] otherMemoryAddress = Helper.intToBinArray(2, wordSize);
        for (int i = 16; i < 32; i++) {
            dram.data[i] = otherMemoryAddress[i-16];
        }

        // put 5 at 48, the other location
        int[] value = Helper.intToBinArray(5, wordSize);
        for (int i = 32; i < 48; i++) {
            dram.data[i] = value[i-32];
        }

        Instructions.LDA(dram, MAR, MBR, null, testReg, 1, MFR);

        // after load MBR and testReg should be 48, value at the location stored at 16 (effective addres with indirect)
        assertArrayEquals(Helper.intToBinArray(32, wordSize), MBR.getRegisterValue());
        assertArrayEquals(Helper.intToBinArray(32, wordSize), testReg.getRegisterValue());
    }

//    @Test
//    void LDAIndirectIndex() {
//        DRAM dram = new DRAM(wordSize, dramSize);
//        Register IX = new Register(wordSize);
//        Register testReg = new Register(wordSize);
//        Register MAR = new Register(wordSize);
//        Register MBR = new Register(wordSize);
//        Register MFR = new Register(wordSize);
//
//        // address 16
//        MAR.setRegisterValue(Helper.intToBinArray(1, wordSize));
//        IX.setRegisterValue(Helper.intToBinArray(1, wordSize));
//
//        int[] IXvalue = Helper.intToBinArray(1, wordSize);
//        for (int i = 32; i < 48; i++) {
//            dram.data[i] = IXvalue[i-32];
//        }
//
//        // set mem to 48, the other address I want to read :). poimnter to 48
//        int[] otherMemoryAddress = Helper.intToBinArray(48, wordSize);
//        for (int i = 16; i < 32; i++) {
//            dram.data[i] = otherMemoryAddress[i-16];
//        }
//
//
//        // put 5 at 48, the other location
//        int[] value = Helper.intToBinArray(5, wordSize);
//        for (int i = 48; i < 48+16; i++) {
//            dram.data[i] = value[i-48];
//        }
//
//        Instructions.LDA(dram, MAR, MBR, IX, testReg, 1, MFR);
//
//        // after load MBR and testReg should be 49, value at the location stored at 16 (effective addres with indirect) +
//        // the value (1) address stored in the IX register (100)
//        assertArrayEquals(Helper.intToBinArray(32, wordSize), MBR.getRegisterValue());
//        assertArrayEquals(Helper.intToBinArray(32, wordSize), testReg.getRegisterValue());
//    }
//
//
//
//    /*
//    END LDX TESTING
//     */
//
//    @org.junit.jupiter.api.Test
//     void storeNoIndex() {
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
//        testReg.setRegisterValue(Helper.intToBinArray((int)Math.pow(2,16)-1, wordSize));
//        MBR.setRegisterValue(Helper.intToBinArray((int)Math.pow(2,16)-1, wordSize));
//
//        Instructions.STR(dram, MAR, MBR, null, testReg, 0, MFR);
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
//        @org.junit.jupiter.api.Test
//        void storeIndex() {
//        DRAM dram = new DRAM(wordSize, dramSize);
//        Register IX = new Register(wordSize);
//        Register testReg = new Register(wordSize);
//        Register MAR = new Register(wordSize);
//        Register MBR = new Register(wordSize);
//        Register MFR = new Register(wordSize);
//
//        // address 16
//        MAR.setRegisterValue(Helper.intToBinArray(16, wordSize));
//        // value to store
//            testReg.setRegisterValue(Helper.intToBinArray((int)Math.pow(2,16)-1, wordSize));
//        MBR.setRegisterValue(Helper.intToBinArray((int)Math.pow(2,16)-1, wordSize));
//
//        IX.setRegisterValue(Helper.intToBinArray(16, wordSize));
//
//        Instructions.STR(dram, MAR, MBR, IX, testReg, 0, MFR);
//
//
//        // set mem
//        int[] data = new int[wordSize];
//        for (int i = 32; i < 48; i++) {
//            data[i-32] = dram.data[i];
//        }
//
//
//        // after store data should be all 1s
//        assertArrayEquals(Helper.intToBinArray((int) Math.pow(2,16) -1, wordSize), data);
//    }


//    public static void main(String[] args) {
//        loadTestNoIndexNoIndirect();
//    }
}