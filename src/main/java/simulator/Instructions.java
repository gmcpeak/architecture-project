package simulator;


public class Instructions {
    public static int LDR(DRAM dram, Register MAR, Register MBR, Register IX, Register registerTarget,
                          int indirect, Register MFR) {
        int faultCode = dram.load(MAR, MBR, IX, indirect, "R");

        if (faultCode != 0) return faultCode;

        MFR.setRegisterValue(Helper.intToBinArray(faultCode, MFR.size));

        registerTarget.setRegisterValue(MBR.getRegisterValue());

        return faultCode;
    }

    public static int LDA(DRAM dram, Register MAR, Register MBR, Register IX, Register registerTarget,
                          int indirect, Register MFR) {
        int faultCode = dram.load(MAR, MBR, IX, indirect, "A");

        if (faultCode != 0) return faultCode;

        MFR.setRegisterValue(Helper.intToBinArray(faultCode, MFR.size));
        registerTarget.setRegisterValue(MBR.getRegisterValue());

        return faultCode;
    }

    public static int LDX(DRAM dram, Register MAR, Register MBR, Register IX, int indirect, Register MFR) {
        int faultCode = dram.load(MAR, MBR, null, indirect, "X");

        if (faultCode != 0) return faultCode;

        MFR.setRegisterValue(Helper.intToBinArray(faultCode, MFR.size));
        IX.setRegisterValue(MBR.getRegisterValue());

        return faultCode;
    }

    public static int executeSTORE(DRAM dram, Register MAR, Register MBR, Register IX, Register registerTarget,
                            int indirect, Register MFR, String type) {
        int faultCode = dram.store(MAR, MBR, IX, indirect, type);

        if (faultCode != 0) return faultCode;

        MFR.setRegisterValue(Helper.intToBinArray(faultCode, MFR.size));


//        registerTarget.setRegisterValue(MBR.getRegisterValue());

        return faultCode;
    }
}

