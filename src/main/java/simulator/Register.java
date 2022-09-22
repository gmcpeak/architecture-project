package simulator;

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
        this.data = val.clone();
    }
}
