package simulator;

import java.util.ResourceBundle;

public class Computer {
    int wordSize;
    DRAM dram;

    // Registers
    Register[] GPRs;
    Register[] IXs;

    // UI
    UI ui;

    public Computer(int wordSize, int dramSize, int numGPR, int numIX) {
        this.wordSize = wordSize;
        this.dram = new DRAM(wordSize, dramSize);

        // Initialize GPRs
        this.GPRs = new Register[numGPR];
        for (int i = 0; i < numGPR; i++)
            this.GPRs[i] = new Register(this.wordSize);

        // Initialize IX Registers
        this.IXs = new Register[numIX];
        for (int i = 0; i < numIX; i++)
            this.IXs[i] = new Register(this.wordSize);

        // Start UI
        this.ui = new UI();
        UI.run_ui();
    }

    public static void main(String[] args) {
        Computer cpu = new Computer(16, 2048, 4, 3);
    }
}

