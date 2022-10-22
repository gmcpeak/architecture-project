package simulator;

import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * The main class containing the essential data and the main method
 */
public class Computer {
    int wordSize;
    DRAM dram;

    // Registers
    Register[] GPRs;
    Register[] IXs;


    Register PC;
    Register CC;
    Register IR;
    Register MBR;
    Register MAR;
    Register MFR;

    Register[] deviceBuffers;
    // UI
    UI ui;
    Instructions instructions;
    Parser parser;


    /**
     * Creates object and sets proper conditions for architecture
     * @param wordSize the length of a word in our word-addressable memory
     * @param dramSize the total size, of our memory
     * @param numGPR the number of general-purpose registers in our simulation
     * @param numIX the number of index registers in our simulation
     */
    public Computer(int wordSize, int dramSize, int numGPR, int numIX) {
        this.wordSize = wordSize;
        this.dram = new DRAM(wordSize, dramSize);

        // initialize some registers
        this.PC = new Register(12);
        this.CC = new Register(4);
        this.IR = new Register(16);
        this.MAR = new Register(12);
        this.MBR = new Register(16);
        this.MFR = new Register(4);

        // device buffers
//        this.printer_buffer = new Register(16);
//        this.keyboard_buffer = new Register(16);
        this.deviceBuffers = new Register[2];
        this.deviceBuffers[0] = new Register(16);
        this.deviceBuffers[1] = new Register(16);

        // Initialize GPRs
        System.out.println("Initializing General Purpose Registers");
        this.GPRs = new Register[numGPR];
        for (int i = 0; i < numGPR; i++)
            this.GPRs[i] = new Register(this.wordSize);

        // Initialize IX Registers
        System.out.println("Initializing Index Registers");
        this.IXs = new Register[numIX];
        for (int i = 0; i < numIX; i++) {
            if (i == 0) {
                this.IXs[i] = null;
            }
            else {
                this.IXs[i] = new Register(this.wordSize);
            }
        }

        this.parser = new Parser();

        // Start UI
        System.out.println("Starting GUI");
        this.ui = new UI();
        this.ui.run_ui(this);
    }

    /**
     * When a program file is being executed, this function is used to load and execute each instruciton of the program
     * @return true if halt code was not reached
     */
    public boolean step() {
        if (parser.parse_and_call(Helper.arrToString(this.IR.getRegisterValue()), this)) {
            int curr_pc_val = Helper.arrToInt(this.PC.getRegisterValue());

            this.IR.setRegisterValue(this.dram.fetchBinaryValue(curr_pc_val));
            int new_pc_val = curr_pc_val + 16;
            this.PC.setRegisterValue(Helper.intToBinArray(new_pc_val, 12));
            this.ui.refresh(this);
            return true;
        }
        return false;
    }

    /**
     * Executes all the instructions of a loaded program one right after the other without stopping
     */
    public void run() {
        while (step());
    }

    /**
     * Where the simulation begins
     * @param args
     */
    public static void main(String[] args) {
        Computer cpu = new Computer(16, 2048, 4, 3+1);
    }
}

