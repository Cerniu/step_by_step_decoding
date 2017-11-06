package net.cernius.kodt;

import net.cernius.kodt.datastructures.Bit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Controller for Text Coding tab
 */
public class TextCodingController implements ActionListener {

    private JTextArea inputTextField;
    private JTextArea receivedTextWithoutCodingField;
    private JTextArea receivedTextWithCodingField;
    private Encoder encoder;
    private Decoder decoder;
    private Channel channel;
    private int codeDimension;

    public TextCodingController(JTextArea inputText, JButton sendButton, JTextArea receivedTextWithoutCoding, JTextArea receivedTextWithCoding, Channel channel, Encoder encoder, Decoder decoder, int codeDimension) {
        this.inputTextField = inputText;
        this.receivedTextWithoutCodingField = receivedTextWithoutCoding;
        this.receivedTextWithCodingField = receivedTextWithCoding;
        this.encoder = encoder;
        this.decoder = decoder;
        this.channel = channel;
        this.codeDimension = codeDimension;
    }

    /**
     * Converts text into a vector and then
     * sends the vector through the channel
     * without using coding and with coding
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String input = inputTextField.getText();
        byte[] inputBytes = input.getBytes();
        List<Bit> inputBits = BitMatrixUtils.bytesToVector(inputBytes);
        this.sendTextWithNoCoding(inputBits);
        this.sendTextWithCoding(inputBits);
    }

    /**
     * Sends vector without using coding,
     * reconstructs text from vector and puts
     * the result to GUI
     */
    private void sendTextWithNoCoding(List<Bit> inputBits){
        List<Bit> receivedBits= channel.send(inputBits);
        byte[] receivedBytes = BitMatrixUtils.vectorToBytes(receivedBits);
        String receivedText = new String(receivedBytes);
        receivedTextWithoutCodingField.setText(receivedText);
    }

    /**
     * Sends vector using coding,
     * reconstructs text from vector and puts
     * the result to GUI
     */
    private void sendTextWithCoding(List<Bit> inputBits){
        /**
         * Pad the vector so it is divisible into pieces of
         * length that is code dimension
         */
        int padding = inputBits.size() % this.codeDimension;
        for(int i = 0; i < padding; i++){
            inputBits.add(new Bit(0));
        }
        /**
         * Send the vector piece by piece with coding
         */
        List<Bit> receivedBits = new LinkedList<>();
        for(int i = 0; i < inputBits.size() / this.codeDimension; i++){
            List<Bit> piece = new LinkedList<>();
            int pieceStart = i * this.codeDimension;
            for(int j = 0; j < this.codeDimension; j++) {
                piece.add(inputBits.get(pieceStart + j));
            }
            List<Bit> encodedPiece = encoder.encodeVector(piece);
            List<Bit> receivedPiece = channel.send(encodedPiece);
            List<Bit> decodedPiece = decoder.decode(receivedPiece);
            receivedBits.addAll(decodedPiece);
        }
        /**
         * Remove padding that was added before
         */
        for(int i = 0; i < padding; i++){
            inputBits.remove(inputBits.size() - 1);
        }
        /**
         * Construct text from received vector and show it in the GUI
         */
        byte[] receivedBytes = BitMatrixUtils.vectorToBytes(receivedBits);
        String receivedText = new String(receivedBytes);
        receivedTextWithCodingField.setText(receivedText);
    }
}
