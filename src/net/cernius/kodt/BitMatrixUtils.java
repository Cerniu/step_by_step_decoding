package net.cernius.kodt;

import net.cernius.kodt.datastructures.Bit;
import net.cernius.kodt.datastructures.BitMatrix;

import java.util.LinkedList;
import java.util.List;

public class BitMatrixUtils {

    public static BitMatrix textToBitMatrix(String text){
        String[] rows = text.split("\n");
        int colCount = rows[0].replaceAll("\\s","").length();
        BitMatrix result = new BitMatrix(rows.length, colCount);
        for(int i = 0; i < rows.length; i++){
            String trimmedRow = rows[i].replaceAll("\\s","");
            for(int j = 0; j < trimmedRow.length(); j++){
                int value = Integer.parseInt(trimmedRow.substring(j, j+1));
                result.setElement(i, j, new Bit(value));
            }
        }
        return result;
    }

    public static List<Bit> textToVector(String text){
        String trimmedText = text.replaceAll("\\s","");
        List<Bit> vector = new LinkedList<>();
        for(int i = 0; i < trimmedText.length(); i++){
            vector.add(new Bit(Integer.parseInt(trimmedText.substring(i, i+1))));
        }
        return vector;
    }

    public static String vectorToText(List<Bit> vector){
        String result = "";
        for(Bit bit : vector){
            result += bit.getValue();
        }
        return result;
    }

    public static List<Bit> bytesToVector(byte[] bytes){
        List<Bit> vector = new LinkedList<>();
        for(byte b: bytes) {
            for (int i = 7; i >= 0; i--) {
                if ((b & (1 << i)) != 0) {
                    vector.add(new Bit(1));
                } else {
                    vector.add(new Bit(0));
                }
            }
        }
        return vector;
    }

    public static byte[] vectorToBytes(List<Bit> vector){
        byte[] result = new byte[vector.size() / 8];
        for(int i = 0; i < vector.size() / 8; i++){
            int startingBit = i * 8;
            byte b = 0;
            for(int j = 0; j < 8; j++){
                b = (byte) (b | (vector.get(startingBit + j).getValue() << (7 - j)));
            }
            result[i] = b;
        }
        return result;
    }
}
