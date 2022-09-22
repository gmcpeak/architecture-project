package simulator;

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

    public int load(Register MAR, Register MBR, Register IX, int I, String type) {
        int address;
        int EA;
        int returnCode;

        address = Helper.arrToInt(MAR.getRegisterValue());
        EA = this.calculateEffectiveAddress(address, IX, I);

        returnCode = this.checkAddress(EA);
        if (returnCode != 0) {
            return returnCode;
        }

        if (type.toUpperCase().compareTo("R") == 0 || type.toUpperCase().compareTo("X") == 0) {
            if (type.toUpperCase().compareTo("X") == 0) {
                EA = Helper.arrToInt(MAR.getRegisterValue());
            }
            int[] data = fetchBinaryValue(EA);
            MBR.setRegisterValue(data);
        } else if (type.toUpperCase().compareTo("A") == 0) {
            int [] binaryEA = Helper.intToBinArray(EA, this.wordSize);
            MBR.setRegisterValue(binaryEA);
        } else {
            returnCode = 4;
        }

        return returnCode;
    }

    public int store(Register MAR, Register MBR, Register IX, int I, String type) {
        int address;
        int EA;
        int returnCode;

        address = Helper.arrToInt(MAR.getRegisterValue());
        EA = this.calculateEffectiveAddress(address, IX, I);

        returnCode = this.checkAddress(EA);
        if (returnCode != 0) {
            return returnCode;
        }

        if (type.toUpperCase().compareTo("R") == 0 || type.toUpperCase().compareTo("X") == 0) {
            if (type.toUpperCase().compareTo("X") == 0) {
                EA = Helper.arrToInt(MAR.getRegisterValue());
            }
            int[] regData = MBR.getRegisterValue();
            for (int i = 0; i < this.wordSize; i++) {
                this.data[i+EA] = regData[i];
            }
        }  else {
            returnCode = 4;
        }

        return returnCode;

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
                return fetchAddress(address);
            }
            else {
                int IXValue = Helper.arrToInt(IX.getRegisterValue());
                return   fetchAddress(address) + fetchAddress(IXValue)  ;

            }
        }
    }

    private int fetchAddress(int EA) {
        return Helper.arrToInt(Arrays.copyOfRange(this.data, EA, EA+wordSize));
    }

    private int[] fetchBinaryValue(int EA) {
        return Arrays.copyOfRange(this.data, EA, EA+wordSize);
    }

    private int checkAddress(int address)  {
        int endAddress = address + this.wordSize;
        if (address >= 0 && address <= 5) {
            return 1;
        } else if (address < 0 || address >= this.size || endAddress >= this.size) {
            return 8;
        } else if (address % this.wordSize != 0) {
            System.out.println("Address is not on a word");
        }
        return 0;
    }
}
