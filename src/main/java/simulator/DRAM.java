package simulator;

import java.lang.reflect.Array;
import java.util.Arrays;

public class DRAM {
    int numberOfWords;
    int wordSize;

    int data[];
    int size;


    public DRAM(int wordSize, int numberOfWords) {
        this.wordSize = wordSize;
        this.numberOfWords = numberOfWords;
        this.size = wordSize * numberOfWords;
        this.data = new int[this.size];
    }

    public void load(int address, Register IX, int I, Register dest) throws Exception {
        // 1. Calculate Effective Address..
        int EA = this.calculateEffectiveAddress(address, IX, I);

        // 2. validate address
        this.checkAddress(EA);

        int[] data = fetchBinaryValue(EA);
        dest.setRegisterValue(data);
    }

    public void store(int address, Register IX, int I, Register src) throws Exception {
        // 1. Calculate Effective Address..
        int EA = this.calculateEffectiveAddress(address, IX, I);

        // 2. validate address
        this.checkAddress(EA);

        int[] data = fetchBinaryValue(EA);
        int[] regData = src.getRegisterValue();
        for (int i = 0; i < this.wordSize; i++) {
            this.data[i+EA] = regData[i];
        }
    }

    private int calculateEffectiveAddress(int address, Register IX, int I) {
        if (I == 0) {
            if (IX == null) {
                return address;
            }
            else {
                int IXValue = Helper.arrToInt(IX.getRegisterValue());
                return IXValue + address;
            }
        }
        else {
            if (IX == null) {
                return fetchAddress(fetchAddress(address));
            }
            else {
                int IXValue = Helper.arrToInt(IX.getRegisterValue());
                return fetchAddress(  fetchAddress(address) + IXValue  );

            }
        }
    }

    private int fetchAddress(int EA) {
        return Helper.arrToInt(Arrays.copyOfRange(this.data, EA, EA+wordSize));
    }

    private int[] fetchBinaryValue(int EA) {
        return Arrays.copyOfRange(this.data, EA, EA+wordSize);
    }

    private void checkAddress(int address) throws Exception {
        if (address >= 0 && address <= 5) {
            throw new Exception("Illegal Memory Address to Reserved Locations MFR set to binary 0001");
        } else if (address < 0 || address >= this.size) {
            throw new Exception("Illegal Memory Address beyond 2048(or under 0) (memory installed) MFR set to binary 1000");
        } else if (address % this.wordSize != 0) {
            System.out.println("Address is not on a word");
        }
    }
}
