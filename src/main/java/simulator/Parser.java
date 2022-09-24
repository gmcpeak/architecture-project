package simulator;

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
            default:
                System.out.println("ERROR: Invalid opcode");
        }
        return true;
    }
}