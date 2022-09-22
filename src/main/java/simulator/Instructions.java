package simulator;


import java.util.Arrays;

public class Instructions {
    public static int LDR(DRAM dram, Register MAR, Register MBR, Register IX, Register registerTarget,
                          int indirect, Register MFR) {
        System.out.println("------------------------------");
        System.out.println("LDR CALLED");
        System.out.println(String.format("MBR VALUE %s", Arrays.toString(MBR.getRegisterValue())));
        System.out.println(String.format("MAR VALUE %s", Arrays.toString(MAR.getRegisterValue())));
        if (IX != null){
            System.out.println(String.format("IX VALUE %s", Arrays.toString(IX.getRegisterValue())));
        }

        int faultCode = dram.load(MAR, MBR, IX, indirect, "R");

        System.out.println("AFTER LDR");
        System.out.println(String.format("MBR VALUE %s", Arrays.toString(MBR.getRegisterValue())));
        System.out.println(String.format("MAR VALUE %s", Arrays.toString(MAR.getRegisterValue())));

        if (IX != null){
            System.out.println(String.format("IX VALUE %s", Arrays.toString(IX.getRegisterValue())));
        }
        System.out.println("------------------------------");

        if (faultCode != 0) return faultCode;

        MFR.setRegisterValue(Helper.intToBinArray(faultCode, MFR.size));

        registerTarget.setRegisterValue(MBR.getRegisterValue());

        return faultCode;
    }

    public static int LDA(DRAM dram, Register MAR, Register MBR, Register IX, Register registerTarget,
                          int indirect, Register MFR) {
        System.out.println("------------------------------");
        System.out.println("LDA CALLED");
        System.out.println(String.format("MBR VALUE %s", Arrays.toString(MBR.getRegisterValue())));
        System.out.println(String.format("MAR VALUE %s", Arrays.toString(MAR.getRegisterValue())));
        if (IX != null){
            System.out.println(String.format("IX VALUE %s", Arrays.toString(IX.getRegisterValue())));
        }

        int faultCode = dram.load(MAR, MBR, IX, indirect, "A");

        System.out.println("ATER LDA");
        System.out.println(String.format("MBR VALUE %s", Arrays.toString(MBR.getRegisterValue())));
        System.out.println(String.format("MAR VALUE %s", Arrays.toString(MAR.getRegisterValue())));
        if (IX != null){
            System.out.println(String.format("IX VALUE %s", Arrays.toString(IX.getRegisterValue())));
        }
        System.out.println("------------------------------");


        if (faultCode != 0) return faultCode;

        MFR.setRegisterValue(Helper.intToBinArray(faultCode, MFR.size));
        registerTarget.setRegisterValue(MBR.getRegisterValue());

        return faultCode;
    }

    public static int LDX(DRAM dram, Register MAR, Register MBR, Register IX, int indirect, Register MFR) {
        System.out.println("------------------------------");
        System.out.println("LDX CALLED");
        System.out.println(String.format("MBR VALUE %s", Arrays.toString(MBR.getRegisterValue())));
        System.out.println(String.format("MAR VALUE %s", Arrays.toString(MAR.getRegisterValue())));
        if (IX != null){
            System.out.println(String.format("IX VALUE %s", Arrays.toString(IX.getRegisterValue())));
        }

        int faultCode = dram.load(MAR, MBR, null, indirect, "X");

        System.out.println("AFTER LDX");
        System.out.println(String.format("MBR VALUE %s", Arrays.toString(MBR.getRegisterValue())));
        System.out.println(String.format("MAR VALUE %s", Arrays.toString(MAR.getRegisterValue())));
        if (IX != null){
            System.out.println(String.format("IX VALUE %s", Arrays.toString(IX.getRegisterValue())));
        }
        System.out.println("------------------------------");

        if (faultCode != 0) return faultCode;

        MFR.setRegisterValue(Helper.intToBinArray(faultCode, MFR.size));
        IX.setRegisterValue(MBR.getRegisterValue());

        return faultCode;
    }

    public static int STR(DRAM dram, Register MAR, Register MBR, Register IX, Register registerTarget,
                          int indirect, Register MFR) {
        System.out.println("------------------------------");

        System.out.println("STR INSTRUCTION CALLED");
        System.out.println(String.format("MBR VALUE %s", Arrays.toString(MBR.getRegisterValue())));
        System.out.println(String.format("MAR VALUE %s", Arrays.toString(MAR.getRegisterValue())));
        if (IX != null){
            System.out.println(String.format("IX VALUE %s", Arrays.toString(IX.getRegisterValue())));
        }

        int faultCode = dram.store(MAR, MBR, IX, indirect, "R");

        System.out.println("AFTER STR");
        System.out.println(String.format("MBR VALUE %s", Arrays.toString(MBR.getRegisterValue())));
        System.out.println(String.format("MAR VALUE %s", Arrays.toString(MAR.getRegisterValue())));
        if (IX != null){
            System.out.println(String.format("IX VALUE %s", Arrays.toString(IX.getRegisterValue())));
        }
        System.out.println("------------------------------");

        if (faultCode != 0) return faultCode;

        MFR.setRegisterValue(Helper.intToBinArray(faultCode, MFR.size));

//        registerTarget.setRegisterValue(Arrays.toString(MBR.getRegisterValue().toString()));

        return faultCode;
    }

    public static int STX(DRAM dram, Register MAR, Register MBR, Register IX, Register registerTarget,
                          int indirect, Register MFR) {
        // store the IX value in the MBR
        System.out.println("------------------------------");

        System.out.println("STX INSTRUCTION CALLED");
        System.out.println(String.format("MBR VALUE %s", Arrays.toString(MBR.getRegisterValue())));
        System.out.println(String.format("MAR VALUE %s", Arrays.toString(MAR.getRegisterValue())));

        if (IX != null){
            System.out.println(String.format("IX VALUE %s", Arrays.toString(IX.getRegisterValue())));
        }

        MBR.setRegisterValue(IX.getRegisterValue());
        int faultCode = dram.store(MAR, MBR, IX, indirect, "R");

        System.out.println("AFTER STX");
        System.out.println(String.format("MBR VALUE %s", Arrays.toString(MBR.getRegisterValue())));
        System.out.println(String.format("MAR VALUE %s", Arrays.toString(MAR.getRegisterValue())));
        if (IX != null){
            System.out.println(String.format("IX VALUE %s", Arrays.toString(IX.getRegisterValue())));
        }
        System.out.println("------------------------------");

        if (faultCode != 0) return faultCode;

        MFR.setRegisterValue(Helper.intToBinArray(faultCode, MFR.size));

//        registerTarget.setRegisterValue(Arrays.toString(MBR.getRegisterValue().toString()));

        return faultCode;
    }

}

