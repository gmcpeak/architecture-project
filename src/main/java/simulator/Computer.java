package simulator;

public class Computer {
    int wordSize;
//    Memory dram;
    UI ui;

    public Computer(int wordSize, int dramSize) {
        this.wordSize = wordSize;
//        this.dram = new Memory(wordSize, dramSize);

        // Start UI
        this.ui = new UI();
        UI.run_ui();
    }

    public static void main(String[] args) {
        Computer cpu = new Computer(16, 2048);
    }
}

