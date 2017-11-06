package net.cernius.kodt;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Controller for Image Coding tab
 */
public class ImageCodingController {

    private JLabel originalPicture;
    private JLabel receivedPictureNoCoding;
    private JLabel receivedPictureWithCoding;

    private Channel channel;
    private Encoder encoder;
    private Decoder decoder;
    private int codeDimension;

    private BufferedImage image;

    public ImageCodingController(JLabel originalPicture, JLabel receivedPictureNoCoding, JLabel receivedPictureWithCoding, Channel channel, Encoder encoder, Decoder decoder, int codeDimension) {
        this.originalPicture = originalPicture;
        this.receivedPictureNoCoding = receivedPictureNoCoding;
        this.receivedPictureWithCoding = receivedPictureWithCoding;
        this.channel = channel;
        this.encoder = encoder;
        this.decoder = decoder;
        this.codeDimension = codeDimension;
    }

    public class Loader implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            int returnValue = jfc.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                try {
                    image = ImageIO.read(selectedFile);
                    originalPicture.setIcon(new ImageIcon(image));
                }catch (IOException ex){
                    //Ignore
                }
            }
        }
    }

    public class Coder implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private byte[] imageToBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "bmp", baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        return imageBytes;
    }

}
