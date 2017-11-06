import net.cernius.kodt.BitMatrixUtils;
import net.cernius.kodt.datastructures.BitMatrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SettingsFrame {
    private JPanel panel1;
    private JTextField nInput;
    private JTextField kInput;
    private JTextArea generatorMatrixInput;
    private JButton generateRandomMatrixButton;
    private JButton setParametersButton;

    public SettingsFrame(JFrame frame) {
        generateRandomMatrixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        setParametersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = Integer.parseInt(nInput.getText());
                int k = Integer.parseInt(kInput.getText());
                BitMatrix generatorMatrix = BitMatrixUtils.textToBitMatrix(generatorMatrixInput.getText());
                MainFrame.show(n, k, generatorMatrix, frame);
                frame.setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Kodo parametrų nustatymas");
        frame.setContentPane(new SettingsFrame(frame).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(700, 400));
        frame.pack();
        frame.setVisible(true);
    }


}
