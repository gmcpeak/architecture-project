package simulator;

public class Parser {
    public Parser(){}

    public boolean parse_and_call(String in, Computer c) {
        if (in.length() != 16) {return false;}
        String opcode = in.substring(0, 6);
        switch (opcode) {
            case "000001":
                break;
        }
        return true;
    }
}
