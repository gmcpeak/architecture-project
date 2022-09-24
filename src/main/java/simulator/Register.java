package simulator;

import java.util.Arrays;

public class Register {
    int[] data;
    int size;
    public Register(int size) {
        this.size = size; this.data = new int[size];
    }

    public  int[] getRegisterValue() {
        return this.data.clone();
    }

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
