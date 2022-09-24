package simulator;

import java.util.Arrays;

/**
 * Used to represent GPRs, IXs, MAR, MBR, etc.
 */
public class Register {
    int[] data;
    int size;

    /**
     * Creates register for simulation
     * @param size the number of bits the regsiter holds
     */
    public Register(int size) {
        this.size = size; this.data = new int[size];
    }

    /**
     * Returns an array representing the status of each of the bits in the register
     * @return
     */
    public  int[] getRegisterValue() {
        return this.data.clone();
    }

    /**
     * Update the value of a simulated register
     * @param val representaito of a number in binary of length wordLength
     */
    public void setRegisterValue(int[] val) {
        if (this.data.length == val.length) {

            this.data = val.clone();
        } else {
            int[] newArr = new int[this.data.length];
            int offset = val.length - newArr.length;
            System.out.println(Arrays.toString(val));
            System.out.println(Arrays.toString(this.data));
            for (int i = 0; i < newArr.length; i++) {
                newArr[i] = val[offset+i];
            }
            this.data = newArr;
        }
    }
}
