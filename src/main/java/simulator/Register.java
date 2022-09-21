package simulator;

public class Register {
    int[] data;
    public Register(int wordSize) {
        this.data = new int[wordSize];
    }

    public  int[] getRegisterValue() {
        return this.data.clone();
    }

    public void setRegisterValue(int[] val) {
        this.data = val.clone();
    }
}
