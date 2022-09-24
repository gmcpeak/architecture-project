package simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * All of the GUI components are defined in this file
 */
public class UI {
    JFrame base_frame;
    JPanel register_area;
    JPanel GPR_area;
    JPanel idxR_area;
    JPanel instruction_area;
    JPanel program_area;
    static ArrayList <JLabel> GPRs;
    static ArrayList <JLabel> idxRs;
    static JLabel program_counter;
    static JLabel instruction_register;
    static JLabel mar;
    static JLabel value_at_mar;

    /**
     * When the user executed a binary string in the input field, this is the function that is used to make that happen
     * @param input a binary code to be interpreted for execution
     * @param c the internal components of the simulated machine
     */
    private static void execute_binary_code(String input, Computer c) {
        c.parser.parse_and_call(input, c);
    }

    /**
     * After executing a command, update the displayed values of registers in the GUI so that they reflect the changes
     * made internally
     * @param c the computer object pointing to the registers, dram, etc
     */
    public static void refresh(Computer c) {
        for (int i = 0; i < 4; i++) {
            GPRs.get(i).setText("R"+String.valueOf(i)+ ": "+Helper.arrToDisplayString(c.GPRs[i].data));
            if (i > 0) {
                idxRs.get(i-1).setText("X"+String.valueOf(i)+ ": "+Helper.arrToDisplayString(c.IXs[i].data));
            }
        }
        program_counter.setText("PC: "+Helper.arrToDisplayString(c.PC.data));
        instruction_register.setText("IR: "+Helper.arrToDisplayString(c.IR.data));
        mar.setText("MAR: "+Helper.arrToDisplayString(c.MAR.data));
        value_at_mar.setText("Value: "+Helper.arrToDisplayString(c.dram.fetchBinaryValue(Helper.arrToInt(c.MAR.data) * 16)));
    }

    /**
     * Initialize GUI and organize its components
     * @param c Pointer to internal simulated computer
     */
    private void createAndShowGUI(Computer c) {
        base_frame = new JFrame();
        base_frame.setLayout(new GridLayout(3, 1));
        base_frame.setSize(1200,600);
        base_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        register_area = new JPanel();
        register_area.setLayout(new GridLayout(2, 1));
        base_frame.add(register_area);
        GPR_area = new JPanel();
        GPR_area.setLayout(new GridLayout(1, 4));
        register_area.add(GPR_area);
        idxR_area = new JPanel();
        idxR_area.setLayout(new GridLayout(1, 3));
        register_area.add(idxR_area);

        instruction_area = new JPanel();
        instruction_area.add(new JLabel("Input field: "));
        JTextField in_text = new JTextField("0000000000000000", 16);
        JButton execute_button = new JButton("Execute as instruction");
        execute_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                execute_binary_code(in_text.getText(), c);
                refresh(c);
            }
        });

        GPRs = new ArrayList<JLabel>();
        ArrayList <JButton> load_to_gpr_buttons = new ArrayList();
        for (int i = 0; i < 4; i++) {
            GPRs.add(new JLabel("R"+String.valueOf(i)+": 0000 0000 0000 0000"));
            GPRs.get(i).setVerticalAlignment(JLabel.CENTER);
            GPRs.get(i).setHorizontalAlignment(JLabel.CENTER);
            load_to_gpr_buttons.add(new JButton("Load value from input field"));
            int reg_num = i;
            load_to_gpr_buttons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Instructions.write_to_register(in_text.getText(), c.GPRs[reg_num]);
                    refresh(c);
                }
            });
            JPanel gpr_area_panel = new JPanel();
            GPR_area.add(gpr_area_panel);
            gpr_area_panel.add(GPRs.get(i));
            gpr_area_panel.add(load_to_gpr_buttons.get(i));
        }

        idxRs = new ArrayList<JLabel>();
        ArrayList <JButton> load_to_idxr_buttons = new ArrayList();
        for (int i = 1; i < 4; i++) {
            idxRs.add(new JLabel("X"+String.valueOf(i)+": 0000 0000 0000 0000"));
            idxRs.get(i-1).setVerticalAlignment(JLabel.CENTER);
            idxRs.get(i-1).setHorizontalAlignment(JLabel.CENTER);
            load_to_idxr_buttons.add(new JButton("Load value from input field"));
            int reg_num = i;
            load_to_idxr_buttons.get(i-1).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Instructions.write_to_register(in_text.getText(), c.IXs[reg_num]);
                    refresh(c);
                }
            });
            JPanel idxr_area_panel = new JPanel();
            idxR_area.add(idxr_area_panel);
            idxr_area_panel.add(idxRs.get(i-1));
            idxr_area_panel.add(load_to_idxr_buttons.get(i-1));
        }

        instruction_area.add(in_text);
        instruction_area.add(execute_button);
        base_frame.add(instruction_area);

        program_area = new JPanel();
        program_area.setLayout(new GridLayout(1, 2));
        JPanel run_area = new JPanel();
        program_counter = new JLabel("PC: 0000 0000 0000");
        instruction_register = new JLabel("IR: 0000 0000 0000 0000");
        run_area.add(program_counter);
        run_area.add(instruction_register);
        JButton load_program_button = new JButton("IPL (Load program.txt)");
        load_program_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileReader.fileReader(c.dram, c.PC, c.IR);
                refresh(c);
            }
        });
        run_area.add(load_program_button);
        JButton step_button = new JButton("Step");
        step_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.step();
            }
        });
        run_area.add(step_button);
        JButton run_program_button = new JButton("Run");
        run_program_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.run();
            }
        });
        run_area.add(run_program_button);
        program_area.add(run_area);
        JPanel memory_area = new JPanel();
        mar = new JLabel("MAR: 0000 0000 0000");
        memory_area.add(mar);
        value_at_mar = new JLabel("Value: 0000 0000 0000 0000");
        memory_area.add(value_at_mar);
        program_area.add(memory_area);
        base_frame.add(program_area);
        base_frame.setVisible(true);

    }

    /**
     * Creates a swing window for the GUI
     * @param c
     */
    public void run_ui(Computer c) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(c);
            }
        });
    }
}