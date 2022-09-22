package simulator;

public class Register {
    int[] data;
    public Register(int size) {
        this.data = new int[size];
    }

    public  int[] getRegisterValue() {
        return this.data.clone();
    }

    public void setRegisterValue(int[] val) {
        this.data = val.clone();
    }
}
