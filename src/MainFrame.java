import net.cernius.kodt.*;
import net.cernius.kodt.datastructures.Bit;
import net.cernius.kodt.datastructures.BitMatrix;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by tomas on 11/6/17.
 */
public class MainFrame {

    //Kodo ilgis
    private int n;
    //Kodo dimensija
    private int k;
    private BitMatrix generatorMatrix;

    private static JFrame frame;
    private JButton backToCodeParametersButton;
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTextField inputVector;
    private JTextField encodedVector;
    private JTextField receivedVector;
    private JLabel errorsLabel;
    private JButton decodeButton;
    private JTextField resultVector;
    private JButton sendVectorButton;
    private JTextArea inputText;
    private JButton sendButton;
    private JTextArea receivedTextWithoutCoding;
    private JTextArea receivedTextWithCoding;
    private JButton choosePictureButton;
    private JButton sendPictureButton;
    private JLabel originalPicture;
    private JLabel receivedPictureNoCoding;
    private JLabel receivedPictureWithCoding;

    public MainFrame(int n, int k, BitMatrix generatorMatrix, JFrame caller) {
        this.n = n;
        this.k = k;
        this.generatorMatrix = generatorMatrix;
        /*
         * COMMON LISTENERS
         */
        backToCodeParametersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.frame.setVisible(false);
                caller.setVisible(true);
            }
        });

        Encoder encoder = new Encoder(generatorMatrix);
        Decoder decoder = new Decoder(generatorMatrix);
        Channel channel = new BinarySymmetricChannel(0.05);

        VectorCodingController vectorCodingController =
            new VectorCodingController(inputVector, encodedVector, receivedVector, errorsLabel, resultVector, );
        sendVectorButton.addActionListener(vectorCodingController.new Sender(0.2));
        decodeButton.addActionListener(vectorCodingController.new Receiver());
        receivedVector.getDocument().addDocumentListener(vectorCodingController.new ErrorCounter());

        TextCodingController textCodingController = new TextCodingController(inputText, sendButton, receivedTextWithoutCoding, receivedTextWithCoding, generatorMatrix, 0.005);
        sendButton.addActionListener(textCodingController);


    }

    public static void show(int n, int k, BitMatrix generatorMatrix, JFrame caller) {
        if(MainFrame.frame == null) {
            MainFrame.frame = new JFrame("Kodavimas");
            MainFrame form = new MainFrame(n, k, generatorMatrix, caller);
            MainFrame.frame.setContentPane(form.panel1);
            MainFrame.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            MainFrame.frame.setPreferredSize(new Dimension(700, 400));
            MainFrame.frame.pack();
            MainFrame.frame.setVisible(true);
        }else{
            MainFrame.frame.setVisible(true);
        }
    }
}
