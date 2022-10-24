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

        // registerTarget.setRegisterValue(Arrays.toString(MBR.getRegisterValue().toString()));

        return faultCode;
    }

    public static int AMR(DRAM dram, Register MAR, Register MBR, Register IX, Register registerTarget,
                          int indirect, Register MFR, Register CC){
        if (IX != null){
            System.out.println(String.format("IX VALUE %s", Arrays.toString(IX.getRegisterValue())));
        }

        int faultCode = dram.load(MAR, MBR, IX, indirect, "R");

        if (faultCode != 0) return faultCode;

        MFR.setRegisterValue(Helper.intToBinArray(faultCode, MFR.size));
        int sum = Helper.arrToInt(registerTarget.getRegisterValue())+Helper.arrToInt(MBR.getRegisterValue());
        int[] cc_val = CC.getRegisterValue();
        if (sum > 4095) {
            sum = sum % 4095;
            cc_val[0] = 1; // This bit indicates overflow
            CC.setRegisterValue(cc_val);
        } else {
            cc_val[0] = 0;
            CC.setRegisterValue(cc_val);
        }
        registerTarget.setRegisterValue(Helper.intToBinArray(sum, registerTarget.getRegisterValue().length));
        return faultCode;
    }

    public static int SMR(DRAM dram, Register MAR, Register MBR, Register IX, Register registerTarget,
                          int indirect, Register MFR, Register CC){
        if (IX != null){
            System.out.println(String.format("IX VALUE %s", Arrays.toString(IX.getRegisterValue())));
        }

        int faultCode = dram.load(MAR, MBR, IX, indirect, "R");

        if (faultCode != 0) return faultCode;

        MFR.setRegisterValue(Helper.intToBinArray(faultCode, MFR.size));
        int diff = Helper.arrToInt(registerTarget.getRegisterValue())-Helper.arrToInt(MBR.getRegisterValue());
        int[] cc_val = CC.getRegisterValue();
        if (diff < 0) {
            diff = diff + 4095;
            cc_val[1] = 1; // This bit indicates overflow
            CC.setRegisterValue(cc_val);
        } else {
            cc_val[1] = 0;
            CC.setRegisterValue(cc_val);
        }
        registerTarget.setRegisterValue(Helper.intToBinArray(diff, registerTarget.getRegisterValue().length));
        return faultCode;
    }

    public static int AIM(Register registerTarget, int immediate, Register CC) {
        int sum = Helper.arrToInt(registerTarget.getRegisterValue()) + immediate;
        int[] cc_val = CC.getRegisterValue();
        if (sum > 4095) {
            sum = sum % 4095;
            cc_val[0] = 1; // This bit indicates overflow
            CC.setRegisterValue(cc_val);
        } else {
            cc_val[0] = 0;
            CC.setRegisterValue(cc_val);
        }
        registerTarget.setRegisterValue(Helper.intToBinArray(sum, registerTarget.getRegisterValue().length));
        return 0;
    }

    public static int SIR(Register registerTarget, int immediate, Register CC) {
        int diff = Helper.arrToInt(registerTarget.getRegisterValue()) - immediate;
        int[] cc_val = CC.getRegisterValue();
        if (diff < 0) {
            diff = diff + 4095;
            cc_val[1] = 1; // This bit indicates overflow
            CC.setRegisterValue(cc_val);
        } else {
            cc_val[1] = 0;
            CC.setRegisterValue(cc_val);
        }
        registerTarget.setRegisterValue(Helper.intToBinArray(diff, registerTarget.getRegisterValue().length));
        return 0;
    }

    public static int JZ(Register pc, Register r, int address) {
        if (Helper.arrToInt(r.getRegisterValue()) == 0) {
            pc.setRegisterValue(Helper.intToBinArray(address-1, pc.getRegisterValue().length));
            // we make it address -1 because it's going to increase by 1 at the end of the instruction anyways
        }
        return 0;
    }

    public static int JNE(Register pc, Register r, int address) {
        if (Helper.arrToInt(r.getRegisterValue()) != 0) {
            pc.setRegisterValue(Helper.intToBinArray(address-1, pc.getRegisterValue().length));
            // we make it address -1 because it's going to increase by 1 at the end of the instruction anyways
        }
        return 0;
    }

    public static int JCC(Register cc, Register pc, int bit, int address) {
        if (cc.getRegisterValue()[bit] == 1) {
            pc.setRegisterValue(Helper.intToBinArray(address-1, pc.getRegisterValue().length));
        }
        return 0;
    }

    /**
     * Multiply two registers together either R0 and R2, or R0 and itself or R2 and itself
     * Output is an integer represented over the space of two words
     * @param CC
     * @param registers
     * @param rx_id
     * @param ry_id
     * @return
     */
    public static int MLT(Register CC, Register[] registers, int rx_id, int ry_id) {
        if (rx_id != 0 || ry_id != 0 || rx_id != 2 || ry_id != 2) {
            System.out.println("ERROR: Invalid register selection");
            return 0;
        }
        int x = Helper.arrToInt(registers[rx_id].getRegisterValue());
        int y = Helper.arrToInt(registers[ry_id].getRegisterValue());
        int product = x * y;
        // check for overflow, the product is greater than what can be stored in 2 registers
        int[] cc_val = CC.getRegisterValue();
        if (product > 4095) {
            product = product % 4095;
            cc_val[0] = 1; // This bit indicates overflow
            CC.setRegisterValue(cc_val);
        } else {
            cc_val[0] = 0; // This bit indicates overflow
            CC.setRegisterValue(cc_val);
        }
        int[] product_binary = Helper.intToBinArray(product, registers[0].size*2);
        registers[rx_id].setRegisterValue(Arrays.copyOfRange(product_binary, 0, 6));
        registers[rx_id+1].setRegisterValue(Arrays.copyOfRange(product_binary, 6, 12));
        return 0;
    }

    public static int DVD(Register CC, Register[] registers, int rx_id, int ry_id) {
        if (rx_id != 0 || ry_id != 0 || rx_id != 2 || ry_id != 2) {
            System.out.println("ERROR: Invalid register selection");
            return 0;
        }
        int dividend = Helper.arrToInt(registers[rx_id].getRegisterValue());
        int divisor = Helper.arrToInt(registers[ry_id].getRegisterValue());
        int[] cc = CC.getRegisterValue();
        if (divisor == 0) {
            System.out.println("ERROR: Invalid divisor");
            cc[2] = 1;
            CC.setRegisterValue(cc);
            return 0;
        } else {
            cc[2] = 0;
            CC.setRegisterValue(cc);
        }
        int quotient = dividend / divisor;
        int remainder = dividend % divisor;
        registers[rx_id].setRegisterValue(Helper.intToBinArray(quotient, registers[rx_id].size));
        registers[rx_id+1].setRegisterValue(Helper.intToBinArray(remainder, registers[rx_id+1].size));
        return 0;
    }

    public static int TRR(Register CC, Register rx, Register ry) {
        int[] cc = CC.getRegisterValue();
        int x = Helper.arrToInt(rx.getRegisterValue());
        int y = Helper.arrToInt(ry.getRegisterValue());
        if (x == y) {
            cc[3] = 1;
        } else {
            cc[3] = 0;
        }
        CC.setRegisterValue(cc);
        return 0;
    }

    public static int AND(Register rx, Register ry) {
        for (int i = 0; i < rx.getRegisterValue().length; i++) {
            if (rx.getRegisterValue()[i] != 1 || ry.getRegisterValue()[i] != 1) {
                rx.getRegisterValue()[i] = 0;
            }
        }
        return 0;
    }

    public static int ORR(Register rx, Register ry) {
        for (int i = 0; i < rx.getRegisterValue().length; i++) {
            if (rx.getRegisterValue()[i] == 1 || ry.getRegisterValue()[i] == 1) {
                rx.getRegisterValue()[i] = 1;
            }
        }
        return 0;
    }

    public static int SRC(Register CC, Register register, int al, int lr, int count) {
        int[] r = register.getRegisterValue();
        for (int i = 0; i < count; i++) {
            int[] new_r = new int[r.length];
            if (lr == 1) { // shift left
                r = Arrays.copyOfRange(r, 1, r.length);
                for (int j = 0; j < r.length; j++) {
                    new_r[j] = r[j];
                }
                new_r[new_r.length-1] = 0;
            } else { // right shift
                r = Arrays.copyOfRange(r, 0, r.length-1);
                for (int j = 0; j < r.length; j++) {
                    new_r[j+1] = r[j];
                }
                if (al == 0) { // arithmetic shift
                    new_r[0] = new_r[1];
                } else { // logical shift
                    new_r[0] = 0;
                }
            }
            r = new_r;
        }
        register.setRegisterValue(r);
        return 0;
    }

    public static int RRC(Register register, int al, int lr, int count) {
        int[] r = register.getRegisterValue();
        for (int i = 0; i < count; i++) {
            int[] new_r = new int[r.length];
            if (lr == 1) { // rotate left
                int[] r2 = Arrays.copyOfRange(r, 1, r.length);
                for (int j = 0; j < r2.length; j++) {
                    new_r[j] = r2[j];
                }
                new_r[new_r.length-1] = r[0];
            } else { // rotate right
                int[] r2 = Arrays.copyOfRange(r, 0, r.length-1);
                for (int j = 0; j < r.length-1; j++) {
                    new_r[j+1] = r2[j];
                }
                new_r[0] = r[r.length-1];
            }
            r = new_r;
        }
        register.setRegisterValue(r);
        return 0;
    }

    public static int NOT(Register rx) {
        for (int i = 0; i < rx.getRegisterValue().length; i++) {
            if (rx.getRegisterValue()[i] == 1) {
                rx.getRegisterValue()[i] = 0;
            } else {
                rx.getRegisterValue()[i] = 1;
            }
        }
        return 0;
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

