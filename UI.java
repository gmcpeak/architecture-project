import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UI {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void execute_code(String input) {
        System.out.println(input);
    }

    private static void createAndShowGUI() {
        JFrame base_frame = new JFrame();
        base_frame.setLayout(new GridLayout(2, 1));
        base_frame.setSize(1200,600);
        base_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel register_area = new JPanel();
        register_area.setLayout(new GridLayout(2, 1));
        base_frame.add(register_area);
        JPanel GPR_area = new JPanel();
        GPR_area.setLayout(new GridLayout(1, 4));
        register_area.add(GPR_area);
        JPanel idxR_area = new JPanel();
        idxR_area.setLayout(new GridLayout(1, 3));
        register_area.add(idxR_area);

        ArrayList <JLabel> GPRs = new ArrayList<JLabel>();
        for (int i = 0; i < 4; i++) {
            GPRs.add(new JLabel("R"+String.valueOf(i)+": 0000 0000 0000 0000"));
            GPRs.get(i).setVerticalAlignment(JLabel.CENTER);
            GPRs.get(i).setHorizontalAlignment(JLabel.CENTER);
            GPR_area.add(GPRs.get(i));
        }

        ArrayList <JLabel> idxRs = new ArrayList<JLabel>();
        for (int i = 1; i < 4; i++) {
            idxRs.add(new JLabel("X"+String.valueOf(i)+": 0000 0000 0000 0000"));
            idxRs.get(i-1).setVerticalAlignment(JLabel.CENTER);
            idxRs.get(i-1).setHorizontalAlignment(JLabel.CENTER);
            idxR_area.add(idxRs.get(i-1));
        }

        JPanel instruction_area = new JPanel();
        JTextField in_text = new JTextField("0000000000000000", 16);
        JButton execute_button = new JButton("Execute");
        execute_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                execute_code(in_text.getText());
            }
        });
        instruction_area.add(in_text);
        instruction_area.add(execute_button);
        base_frame.add(instruction_area);
        base_frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}