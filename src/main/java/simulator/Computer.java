package simulator;

import java.util.ResourceBundle;

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

    // UI
    UI ui;
    Instructions instructions;
    Parser parser;

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

    public static void main(String[] args) {
        Computer cpu = new Computer(16, 2048, 4, 3+1);
    }
}

