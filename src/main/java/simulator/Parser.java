package simulator;

import java.util.Arrays;

public class Parser {
    public Parser(){}

    /**
     * Load and store are very similar commands, so we will split and interpret the binary strings for all of those
     * instructions in here
     * @param in String to be split and interpreted as integers
     * @return separated string
     */
    public int[] parse_for_load_store(String in) {
        int[] separated = new int[4];
        separated[0] = Helper.binaryToInt(in.substring(6, 8));
        separated[1] = Helper.binaryToInt(in.substring(8, 10));
        separated[2] = Helper.binaryToInt(in.substring(10, 11));
        separated[3] = Helper.binaryToInt(in.substring(11, 16));
        return separated;
    }

    public int[] parse_for_arithmetic_op(String in) {
        int[] separated = new int[4];
        separated[0] = Helper.binaryToInt(in.substring(6, 8));
        separated[1] = Helper.binaryToInt(in.substring(8, 10));
        separated[2] = Helper.binaryToInt(in.substring(10, 11));
        separated[3] = Helper.binaryToInt(in.substring(11, 16));
        return separated;
    }

    /**
     * Register-register operation parsing
     */
    public int[] parse_for_register_register_op(String in) {
        int[] separated = new int[2];
        separated[0] = Helper.binaryToInt(in.substring(6, 8));
        separated[1] = Helper.binaryToInt(in.substring(8, 10));
        return separated;
    }
    
    public int[] parse_for_io(String in) {
        int[] separated = new int[2];
        separated[0] = Helper.binaryToInt(in.substring(6, 8));
        separated[1] = Helper.binaryToInt(in.substring(11, 16));

        return separated;
    }

    /**
     * Decodes the opcode and calls the correect instruction
     * @param in Binary string
     * @param c pointer to computer
     * @return True if not halt code
     */
    public boolean parse_and_call(String in, Computer c) {
        System.out.println(in);
        if (in.length() != 16) {return false;}
        String opcode = in.substring(0, 6);
        switch (opcode) {
            case "000000":
                return false;
            case "000001": // Octal 01, load register from memory
                int[] params_01 = parse_for_load_store(in);
                c.MAR.setRegisterValue(Helper.intToBinArray(params_01[3], 16));
                Instructions.LDR(c.dram, c.MAR, c.MBR, c.IXs[params_01[1]], c.GPRs[params_01[0]], params_01[2], c.MFR);
                break;
            case "000010": // Octal 02, store register to memory
                int[] params_02 = parse_for_load_store(in);
                c.MAR.setRegisterValue(Helper.intToBinArray(params_02[3], 16));
                Instructions.STR(c.dram, c.MAR, c.MBR, c.IXs[params_02[1]], c.GPRs[params_02[0]], params_02[2], c.MFR);
                break;
            case "000011": // Octal 03, load register with address
                int[] params_03 = parse_for_load_store(in);
                c.MAR.setRegisterValue(Helper.intToBinArray(params_03[3], 16));
                Instructions.LDA(c.dram, c.MAR, c.MBR, c.IXs[params_03[1]], c.GPRs[params_03[0]], params_03[2], c.MFR);
                break;
            case "101001": // Octal 41, load index register from memory
                int[] params_41 = parse_for_load_store(in);
                c.MAR.setRegisterValue(Helper.intToBinArray(params_41[3], 16));
                Instructions.LDX(c.dram, c.MAR, c.MBR, c.IXs[params_41[1]], params_41[2], c.MFR);
                break;
            case "101010": // Octal 42, store index register to memory
                int[] params_42 = parse_for_load_store(in);
                c.MAR.setRegisterValue(Helper.intToBinArray(params_42[3], 16));
                Instructions.STX(c.dram, c.MAR, c.MBR, c.IXs[params_42[1]], params_42[2], c.MFR);
                break;
            case "000100": // Octal 04, add memory to register
                int[] params_04 = parse_for_arithmetic_op(in);
                c.MAR.setRegisterValue(Helper.intToBinArray(params_04[3], 16));
                Instructions.AMR(c.dram, c.MAR, c.MBR, c.IXs[params_04[1]], c.GPRs[params_04[0]], params_04[2], c.MFR,
                        c.CC);
                break;
            case "000101": // Octal 05, subtract memory from register
                int[] params_05 = parse_for_arithmetic_op(in);
                c.MAR.setRegisterValue(Helper.intToBinArray(params_05[3], 16));
                Instructions.SMR(c.dram, c.MAR, c.MBR, c.IXs[params_05[1]], c.GPRs[params_05[0]], params_05[2], c.MFR,
                        c.CC);
                break;
            case "000110": // Octal 06, Add immediate to register
                int[] params_06 = parse_for_arithmetic_op(in);
                Instructions.AIM(c.GPRs[params_06[0]], params_06[3], c.CC);
                break;
            case "000111": // Octal 07, Subtract immediate from register
                int[] params_07 = parse_for_arithmetic_op(in);
                Instructions.SIR(c.GPRs[params_07[0]], params_07[3], c.CC);
                break;
            case "010000": // Octal 20, multiply register by register
                int[] params_20 = parse_for_register_register_op(in);
                Instructions.MLT(c.CC, c.GPRs, params_20[0], params_20[1]);
                break;
            case "010001": // Octal 21, divide register by register
                int[] params_21 = parse_for_register_register_op(in);
                Instructions.DVD(c.CC, c.GPRs, params_21[0], params_21[1]);
                break;
            case "010010": // Octal 22, test equality of registers
                int[] params_22 = parse_for_register_register_op(in);
                Instructions.TRR(c.CC, c.GPRs[params_22[0]], c.GPRs[params_22[1]]);
                break;
            case "010011": // Octal 23, logical AND of two registers
                int[] params_23 = parse_for_register_register_op(in);
                Instructions.AND(c.GPRs[params_23[0]], c.GPRs[params_23[1]]);
                break;
            case "010100": // Octal 24, logical OR of two registers
                int[] params_24 = parse_for_register_register_op(in);
                Instructions.ORR(c.GPRs[params_24[0]], c.GPRs[params_24[1]]);
                break;
            case "010101": // Octal 25, NOT two registers together
                int[] params_25 = parse_for_register_register_op(in);
                Instructions.NOT(c.GPRs[params_25[0]]);
                break;
            case "110001": // 61, IN
                System.out.println("IN");
                int[] params_61 = parse_for_io(in);
                System.out.println(Arrays.toString(params_61));
                Instructions.IN(c.GPRs[params_61[0]], c.deviceBuffers[params_61[1]]);
                break;
            case "110010": // 62, OUT
                int[] params_62 = parse_for_io(in);
                Instructions.OUT(c.GPRs[params_62[0]], c.deviceBuffers[params_62[1]]);
                break;
            case "110011": // 63, CHK
                int[] params_63 = parse_for_io(in);
                Instructions.CHK(c.GPRs[params_63[0]], c.deviceBuffers[params_63[1]]);
                break;
            default:
                System.out.println("ERROR: Invalid opcode");
        }
        return true;
    }
}