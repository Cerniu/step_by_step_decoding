package net.cernius.kodt;

import net.cernius.kodt.datastructures.Bit;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for Vector Coding tab
 */
public class VectorCodingController {

    private JTextField inputVectorField;
    private JTextField encodedVectorField;
    private JTextField receivedVectorField;
    private JLabel errorsLabel;
    private JTextField resultVectorField;

    private Channel channel;
    private Encoder encoder;
    private Decoder decoder;

    public VectorCodingController(JTextField inputVectorField, JTextField encodedVectorField, JTextField receivedVectorField, JLabel errorsLabel, JTextField resultVectorField, Channel channel, Encoder encoder, Decoder decoder){
        this.inputVectorField = inputVectorField;
        this.encodedVectorField = encodedVectorField;
        this.receivedVectorField = receivedVectorField;
        this.errorsLabel = errorsLabel;
        this.resultVectorField = resultVectorField;

        this.channel = channel;
        this.encoder = encoder;
        this.decoder = decoder;
    }

    /**
     * Action listener that reads input vector from UI,
     * encodes, sends it through the channel.
     * Then shows the results in UI
     */
    public class Sender implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Bit> inputVector = BitMatrixUtils.textToVector(inputVectorField.getText());
            List<Bit> encodedVector = encoder.encodeVector(inputVector);
            List<Bit> receivedVector = channel.send(encodedVector);
            encodedVectorField.setText(BitMatrixUtils.vectorToText(encodedVector));
            receivedVectorField.setText(BitMatrixUtils.vectorToText(receivedVector));
        }
    }

    /**
     * Action listener that reads received vector from UI,
     * decodes it and shows the result in UI
     */
    public class Receiver implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Bit> receivedVector = BitMatrixUtils.textToVector(receivedVectorField.getText());
            List<Bit> resultVector = decoder.decode(receivedVector);
            resultVectorField.setText(BitMatrixUtils.vectorToText(resultVector));
        }
    }

    /**
     * Input change listener that compares encoded vector to received vector,
     * finds the error count and their positions. Displays results in UI
     */
    public class ErrorCounter implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            this.countErrors();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            this.countErrors();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            this.countErrors();
        }

        public void countErrors(){
            List<Bit> encodedVector = BitMatrixUtils.textToVector(encodedVectorField.getText());
            List<Bit> receivedVector = BitMatrixUtils.textToVector(receivedVectorField.getText());
            if(encodedVector.size() == receivedVector.size()) {
                List<Integer> errorPositions = new ArrayList<>();
                for (int i = 0; i < encodedVector.size(); i++) {
                    if (!encodedVector.get(i).equals(receivedVector.get(i))) {
                        errorPositions.add(i + 1);
                    }
                }
                if (errorPositions.size() == 0) {
                    errorsLabel.setText("Klaidų neivyko");
                } else {
                    String error = "";
                    if(errorPositions.size() == 1){
                        error += "Įvyko 1 klaida. Klaidos pozicija: ";
                    }else{
                        error += "Įvyko " + errorPositions.size() + " klaidos. Klaidu pozicijos: ";
                    }
                    for (Integer position : errorPositions) {
                        error += position + " ";
                    }
                    errorsLabel.setText(error);
                }
            }
        }
    }
}
