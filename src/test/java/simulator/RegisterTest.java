package simulator;

import static org.junit.jupiter.api.Assertions.*;

class RegisterTest {
//    @org.junit.jupiter.api.Test
    static void setTest() {
        Register reg = new Register(16);
        reg.setRegisterValue(Helper.intToBinArray(5, 16));

        assertEquals(Helper.arrToInt(reg.getRegisterValue()), 5);

        reg.setRegisterValue(Helper.intToBinArray(6, 16));
        assertEquals(Helper.arrToInt(reg.getRegisterValue()), 6);


    }

    public static void main(String[] args) {
        setTest();
    }
}