package simulator;


import java.util.Arrays;

public class Instructions {
    public static int LDR(DRAM dram, Register MAR, Register MBR, Register IX, Register registerTarget,
                          int indirect, Register MFR) {
        /*
        dram - pointer to dram object
        MAR - mar register that holds address
        MBR - MBR register
        IX- IX register to be used
        registerTarget - register to be loaded/stored to/from
        indirect - indirect bit
        MFR - machine fault register

        Function to execute the LDR instruction. Calls dram load and sets machine fault register MFR.
        Sets the target register value.
         */
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
        /*
        dram - pointer to dram object
        MAR - mar register that holds address
        MBR - MBR register
        IX- IX register to be used
        registerTarget - register to be loaded/stored to/from
        indirect - indirect bit
        MFR - machine fault register

        Function to execute the LDA instruction. Calls dram load and sets machine fault register MFR.
        Sets the target register value.
         */
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
        /*
        dram - pointer to dram object
        MAR - mar register that holds address
        MBR - MBR register
        IX- IX register to be used
        registerTarget - register to be loaded/stored to/from
        indirect - indirect bit
        MFR - machine fault register

        Function to execute the LDX instruction. Calls dram load and sets machine fault register MFR.
        Sets the target register value.
         */
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
        /*
        dram - pointer to dram object
        MAR - mar register that holds address
        MBR - MBR register
        IX- IX register to be used
        registerTarget - register to be loaded/stored to/from
        indirect - indirect bit
        MFR - machine fault register

        Function to execute the STR instruction. Calls dram load and sets machine fault register MFR.
        Sets the target register value.
         */
        System.out.println("------------------------------");
        MBR.setRegisterValue(registerTarget.getRegisterValue());
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

    public static int STX(DRAM dram, Register MAR, Register MBR, Register IX,
                          int indirect, Register MFR) {
        /*
        dram - pointer to dram object
        MAR - mar register that holds address
        MBR - MBR register
        IX- IX register to be used
        registerTarget - register to be loaded/stored to/from
        indirect - indirect bit
        MFR - machine fault register

        Function to execute the STX instruction. Calls dram load and sets machine fault register MFR.
        Sets the target register value.
         */
        // store the IX value in the MBR
        System.out.println("------------------------------");

        System.out.println("STX INSTRUCTION CALLED");
        System.out.println(String.format("MBR VALUE %s", Arrays.toString(MBR.getRegisterValue())));
        System.out.println(String.format("MAR VALUE %s", Arrays.toString(MAR.getRegisterValue())));

        if (IX != null){
            System.out.println(String.format("IX VALUE %s", Arrays.toString(IX.getRegisterValue())));
        }

        MBR.setRegisterValue(IX.getRegisterValue());
        int faultCode = dram.store(MAR, MBR, null, indirect, "X");

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

    public static int write_to_register(String val, Register r) {
        r.setRegisterValue(Helper.intToBinArray(Helper.binaryToInt(val), 16));
        return 0;
    }

    public static void IN(Register r, Register device) {
        r.setRegisterValue(device.getRegisterValue());
    }

    public static void OUT(Register r, Register device) {
        device.setRegisterValue(r.getRegisterValue());
    }

    public static void CHK(Register r, Register device) {
        int val = Helper.arrToInt(device.getRegisterValue());

        if (val > 0) {
            r.setRegisterValue(Helper.intToBinArray(1, 16));
        } else {
            r.setRegisterValue(Helper.intToBinArray(0, 16));
        }
    }

}

