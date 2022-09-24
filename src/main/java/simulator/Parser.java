package simulator;

public class Parser {
    public Parser(){}

    public int[] parse_for_load_store(String in) {
        int[] separated = new int[4];
        separated[0] = Helper.binaryToInt(in.substring(6, 8));
        separated[1] = Helper.binaryToInt(in.substring(8, 10));
        separated[2] = Helper.binaryToInt(in.substring(10, 11));
        separated[3] = Helper.binaryToInt(in.substring(11, 16));
        return separated;
    }

    public boolean parse_and_call(String in, Computer c) {
        if (in.length() != 16) {return false;}
        String opcode = in.substring(0, 6);
        switch (opcode) {
            case "000001": // Octal 01, load register from memory
                int[] params = parse_for_load_store(in);
//                System.out.println(params[0]);
//                System.out.println(params[1]);
//                System.out.println(params[2]);
//                System.out.println(params[3]);
                c.MAR.setRegisterValue(Helper.intToBinArray(params[3], 16));
                Instructions.LDR(c.dram, c.MAR, c.MBR, c.IXs[params[1]], c.GPRs[params[0]], params[2], c.MFR);
                break;
            case "000010": // Octal 02, store register to memory
                int[] params = parse_for_load_store(in);
                c.MAR.setRegisterValue(Helper.intToBinArray(params[3], 16));
                Instructions.STR(c.dram, c.MAR, c.MBR, c.IXs[params[1]], c.GPRs[params[0]], params[2], c.MFR);
                break;
            case "000011": // Octal 03, load register with address
                int[] params = parse_for_load_store(in);
                c.MAR.setRegisterValue(Helper.intToBinArray(params[3], 16));
                Instructions.LDA(c.dram, c.MAR, c.MBR, c.IXs[params[1]], c.GPRs[params[0]], params[2], c.MFR);
                break;
            case "100001": // Octal 41, load index register from memory
                int[] params = parse_for_load_store(in);
                c.MAR.setRegisterValue(Helper.intToBinArray(params[3], 16));
                Instructions.LDX(c.dram, c.MAR, c.MBR, c.IXs[params[1]], params[2], c.MFR);
                break;
            case "100010": // Octal 42, store index register to memory
                int[] params = parse_for_load_store(in);
                c.MAR.setRegisterValue(Helper.intToBinArray(params[3], 16));
                Instructions.STX(c.dram, c.MAR, c.MBR, c.IXs[params[1]], params[2], c.MFR);
                break;
            default:
                System.out.println("ERROR: Invalid opcode");
        }
        return true;
    }
}

/*
package com.mycompany.mslgui;
        package simulator;

        import java.util.List;

public class Parser {
    private List<String> mainMemory;
    public Simulator simulator;

    Parser() {
        this.mainMemory = Utils.mockMainMemory();
        this.simulator = Utils.setDefaultValuesSimulator();
    }

    public void programControlListener(String value) {
        simulator.setMemoryAddressRegister(value);
        int programControlValueInDecimal = Utils.convertHexadecimalToDecimal(value)+1;
        String programControlValueInHexadecimal = Utils.convertDecimalToHexadecimal(
                Utils.convertIntegerToString(programControlValueInDecimal));
        simulator.setProgramControl(programControlValueInHexadecimal);
        simulator.setMemoryBufferRegister(getDataFromMainMemoryByLocation(
                simulator.getMemoryAddressRegister()));
        simulator.setInstructionRegister(simulator.getMemoryBufferRegister());

        decodeOpcode();
    }

    public void memoryAddressRegisterListener(String value) {
        String memoryData = getDataFromMainMemoryByLocation(value);
        simulator.setMemoryBufferRegister(memoryData);
    }

    private String getDataFromMainMemoryByLocation(String value) {
        int memoryLocation = Utils.convertHexadecimalToDecimal(value);
        return Utils.convertDecimalToHexadecimal(mainMemory.get(memoryLocation));
    }

    private void decodeOpcode() {
        String binaryValue = Utils.convertHexadecimalToBinary(simulator.getInstructionRegister());
        assignOpcodeValue(binaryValue);
        performOperations();
    }

    private void assignOpcodeValue(String value) {
        Opcode opcode = simulator.getOpcode();
        opcode.setOperations(value.substring(0,6));
        opcode.setGeneralPurposeRegister(value.substring(6,8));
        opcode.setIndexRegister(value.substring(8,10));
        opcode.setIndirectMode(value.substring(10,11));
        opcode.setAddress(value.substring(11,16));
        simulator.setOpcode(opcode);
    }

    private void performOperations() {
        String operationsCodeInOctal = Utils.convertBinaryToOctalNumber(
                simulator.getOpcode().getOperations());
        String octalInProperForm = Utils.convertOctalToProperTwoDigitOctalNumber(operationsCodeInOctal);
        calculateEffectiveAddress();
        switch (octalInProperForm) {
            case "01":
                performLoadRegisterFromMemoryOperation();
                break;
            case "02":
                performStoreRegisterToMemoryOperation();
                break;
            case "03":
                performLoadRegisterWithAddressOperation();
                break;
            case "41":
                performLoadIndexRegisterFromMemoryOperation();
                break;
            case "42":
                performStoreIndexRegisterToMemoryOperation();
                break;
            default:
                System.out.println("Invalid operations");
        }
    }

    private void calculateEffectiveAddress() {
        if (simulator.getOpcode().getIndexRegister().equals("00")) {
            String effectiveAddressInHexadecimal = Utils.convertBinaryToHexadecimal(
                    simulator.getOpcode().getAddress());
            simulator.getOpcode().setEffectiveAddress(effectiveAddressInHexadecimal);
            return;
        }
        if (simulator.getOpcode().getIndirectMode().equals("0")) {
            calculateEffectiveAddressForFalseIndirectMode();
            return;
        }
        calculateEffectiveAddressForTrueIndirectMode();
    }

    private void calculateEffectiveAddressForFalseIndirectMode() {
        int effectiveAddressInDecimal = Utils.convertBinaryToDecimal(
                simulator.getOpcode().getAddress());
        int indexRegisterDataInDecimalInteger = Utils.convertHexadecimalToDecimal(
                getDataFromIndexRegisterByAddress());
        int calculatedEffectiveAddressInDecimal = effectiveAddressInDecimal +
                indexRegisterDataInDecimalInteger;
        String calculatedEffectiveAddressInDecimalString = Utils.convertIntegerToString(
                calculatedEffectiveAddressInDecimal);
        String calculatedEffectiveAddressInHexadecimal = Utils.convertDecimalToHexadecimal(
                calculatedEffectiveAddressInDecimalString);
        simulator.getOpcode().setEffectiveAddress(calculatedEffectiveAddressInHexadecimal);
    }


    private void calculateEffectiveAddressForTrueIndirectMode() {
        int indexRegisterDataInDecimal = Utils.convertHexadecimalToDecimal(
                getDataFromIndexRegisterByAddress());
        int addressInOpcodeInDecimalInteger = Utils.convertBinaryToDecimal(
                simulator.getOpcode().getAddress());

        String addressInOpcodeInDecimalString = Utils.convertIntegerToString(addressInOpcodeInDecimalInteger);
        String addressInOpcodeInHexadecimalString = Utils.convertDecimalToHexadecimal(addressInOpcodeInDecimalString);
        String effectiveAddressDataFromMemoryInHexadecimal = getDataFromMainMemoryByLocation(
                addressInOpcodeInHexadecimalString);
        int effectiveAddressDataFromMemoryInDecimalInteger = Utils.convertHexadecimalToDecimal(
                effectiveAddressDataFromMemoryInHexadecimal);

        int preCalculatedEffectiveAddressInDecimalInteger = indexRegisterDataInDecimal +
                effectiveAddressDataFromMemoryInDecimalInteger;
        String preCalculatedEffectiveAddressInDecimalString = Utils.convertIntegerToString(
                preCalculatedEffectiveAddressInDecimalInteger);
        String preCalculatedEffectiveAddressInHexadecimalString = Utils.convertDecimalToHexadecimal(
                preCalculatedEffectiveAddressInDecimalString);
        String calculatedEffectiveAddressInHexadecimal = getDataFromMainMemoryByLocation(
                preCalculatedEffectiveAddressInHexadecimalString);
        simulator.getOpcode().setEffectiveAddress(calculatedEffectiveAddressInHexadecimal);
    }

    private String getDataFromIndexRegisterByAddress() {
        int indexRegisterInDecimal = Utils.convertBinaryToDecimal(
                simulator.getOpcode().getIndexRegister());
        String indexRegisterDataInDecimalString;
        if (indexRegisterInDecimal == 1) {
            indexRegisterDataInDecimalString = Utils.convertDecimalToHexadecimal(
                    simulator.getIndexRegister().getRegisterOne());
        } else if (indexRegisterInDecimal == 2) {
            indexRegisterDataInDecimalString = Utils.convertDecimalToHexadecimal(
                    simulator.getIndexRegister().getRegisterTwo());
        } else {
            indexRegisterDataInDecimalString = Utils.convertDecimalToHexadecimal(
                    simulator.getIndexRegister().getRegisterThree());
        }
        return indexRegisterDataInDecimalString;
    }

    private void performLoadRegisterFromMemoryOperation() {
        String getValueFromMainMemoryInHexadecimal = getDataFromMainMemoryByLocation(
                simulator.getOpcode().getEffectiveAddress());
        loadGPRFromOpcode(getValueFromMainMemoryInHexadecimal);
    }

    private void performStoreRegisterToMemoryOperation() {
        String  data1 = simulator.getGeneralPurposeRegister().getRegisterZero();
        mainMemory.add(10,data1);

    }

    private void performLoadRegisterWithAddressOperation() {
        loadGPRFromOpcode(simulator.getOpcode().getEffectiveAddress());
    }

    private void performLoadIndexRegisterFromMemoryOperation() {
        String getValueFromMainMemoryInHexadecimal = getDataFromMainMemoryByLocation(
                simulator.getOpcode().getEffectiveAddress());
        loadIndexRegisterFromOpcode(getValueFromMainMemoryInHexadecimal);
    }

    private void performStoreIndexRegisterToMemoryOperation() {
        String  data2  = simulator.getIndexRegister().getRegisterOne();
        mainMemory.add(9,data2);


    }

    private void loadGPRFromOpcode(String data) {
        String gprRegisterSelect = simulator.getOpcode().getGeneralPurposeRegister();
        GeneralPurposeRegister generalPurposeRegister = simulator.getGeneralPurposeRegister();
        if (gprRegisterSelect.equals("00")) {
            generalPurposeRegister.setRegisterZero(data);
            return;
        }
        if (gprRegisterSelect.equals("01")) {
            generalPurposeRegister.setRegisterOne(data);
            return;
        }
        if (gprRegisterSelect.equals("10")) {
            generalPurposeRegister.setRegisterTwo(data);
            return;
        }
        generalPurposeRegister.setRegisterThree(data);
    }

    private void loadIndexRegisterFromOpcode(String data) {
        String ixrRegisterSelect = simulator.getOpcode().getIndexRegister();
        IndexRegister indexRegister = simulator.getIndexRegister();
        if (ixrRegisterSelect.equals("01")) {
            indexRegister.setRegisterOne(data);
            return;
        }
        if (ixrRegisterSelect.equals("10")) {
            indexRegister.setRegisterTwo(data);
            return;
        }
        indexRegister.setRegisterThree(data);
    }
}
*/