package simulator;

import java.util.Arrays;

public class DRAM {
    /*
    DRAM class to manage main memory. Handles loads, stores, and the calculation of the effective address
     */
    int numberOfWords;
    int wordSize;

    int data[];
    int size;

    Cache cache;

    public DRAM(int wordSize, int numberOfWords) {
        this.wordSize = wordSize;
        this.numberOfWords = numberOfWords;
        this.size = wordSize * numberOfWords;
        this.data = new int[this.size];

        this.cache = new Cache(16, 16, 4);
    }


    public int load(Register MAR, Register MBR, Register IX, int I, String type) {
        /*
        MAR - memory address register
        MBR - memory buffer register

        IX - index register
        I - int, indirect bit

        Type - R, A or X for LDA, LDR, LDX

        This function handles the loading of a value from memory at an address stored in the MAR,
        to the MBR. It handles all three types of Loads.
         */
        int address;
        int EA;
        int returnCode;

        // get address from MAR and calculate the effective address
        address = Helper.arrToInt(MAR.getRegisterValue());
        EA = this.calculateEffectiveAddress(address, IX, I);

        boolean cache_hit = false;

        // BEGIN CACHING
        int cache_result = this.cache.cacheSearch(this, (int)EA / 16);
        if (cache_result != -1) { // HIT
            MBR.setRegisterValue(this.cache.getBlock(cache_result, address));
            return EA;
        } else{
            this.cache.placeBlock(this, (int)EA / 16, cache_result);
        }
        // END CACHING

        // validate address if fault occurs return with fault code
        returnCode = this.checkAddress(EA);
        if (returnCode != 0) {
            return returnCode;
        }

        // load the proper value from memory. R for LDR. X for LDX, A for LDA
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
            // machine fault 4
            returnCode = 4;
        }
        // default no machine fault
        return returnCode;
    }

    public int store(Register MAR, Register MBR, Register IX, int I, String type) {
        /*
        MAR - memory address register
        MBR - memory buffer register

        IX - index register
        I - int, indirect bit

        Type - R, A or X for LDA, LDR, LDX

        This function handles the loading of a value from memory at an address stored in the MAR,
        to the MBR. It handles all two types of stores.
         */
        int address;
        int EA;
        int returnCode;

        // get address from MAR and calculate the effective address
        address = Helper.arrToInt(MAR.getRegisterValue());
        EA = this.calculateEffectiveAddress(address, IX, I);




        System.out.println(String.format("Computed EA: %d", EA));

        // validate address if fault occurs return with fault code
        returnCode = this.checkAddress(EA);
        if (returnCode != 0) {
            return returnCode;
        }

        if (type.toUpperCase().compareTo("R") == 0 || type.toUpperCase().compareTo("X") == 0) {
            if (type.toUpperCase().compareTo("X") == 0) {
                EA = Helper.arrToInt(MAR.getRegisterValue());
            }
            int cacheResult = this.cache.cacheSearch(this, (int)EA / 16);
            if (cacheResult != -1) { // if cache hit
                this.cache.placeBlock(this, (int)EA / 16, cacheResult);
            }
            // copy data
            int[] regData = MBR.getRegisterValue();
            for (int i = 0; i < this.wordSize; i++) {
                this.data[i+EA] = regData[i];
            }

            // cache miss
            this.cache.placeBlock(this, (int)EA / 16, cacheResult);
        }  else {
            returnCode = 4;
        }

        return returnCode;

    }

    private int calculateEffectiveAddress(int address, Register IX, int I) {
        /*
        This is adapted from the psuedocode provided in the project outline
        given by Professor Lancaster.
         */
        if (I == 0) {
            if (IX == null) {
                return address * this.wordSize;
            }
            else {
                int IXValue = Helper.arrToInt(IX.getRegisterValue());
                return (IXValue + address) * this.wordSize;
            }
        }
        else {
            if (IX == null) {
                return fetchAddress(address*this.wordSize) *  this.wordSize;
            }
            else {
                int IXValue = Helper.arrToInt(IX.getRegisterValue());
                return   (fetchAddress(address*this.wordSize) + fetchAddress(IXValue*this.wordSize)) * this.wordSize  ;

            }
        }
    }

    public void memset(int[] value, int start) {
        /*
        Set a word sized block of memory to the value in the value array
         */
        for (int i = start; i < start + value.length; i++) {
            this.data[i] = value[i-start];
        }
    }

    public void memNuke() {
        /*
        Set all memory after reserved words to 0.
         */
        for (int i = 6; i < this.size; i++) {
            this.data[i] = 0;
        }
    }

    int fetchAddress(int EA) {
        /*
        Get the integer value for the word at the effective address EA
         */
        return Helper.arrToInt(Arrays.copyOfRange(this.data, EA, EA+wordSize));
    }

    int[] fetchBinaryValue(int EA) {
        /*
        Get the binary array for the word at the effective address EA
         */
        return Arrays.copyOfRange(this.data, EA, EA+wordSize);
    }

    private int checkAddress(int address)  {
        /*
        Validate address. Make sure its not on reserved word, and make sure it doesnt
        exceed bounds. return the proper fault if it does.
         */
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
