package net.cernius.kodt.math;

import net.cernius.kodt.datastructures.Bit;
import net.cernius.kodt.datastructures.BitMatrix;

import java.util.LinkedList;
import java.util.List;

public class BitMatrixMath {

    public static BitMatrix transpose(BitMatrix matrix){
        BitMatrix result = new BitMatrix(matrix.getColCount(), matrix.getRowCount());
        for(int i = 0; i < matrix.getRowCount(); i++){
            for(int j = 0; j < matrix.getColCount(); j++){
                result.setElement(j, i, new Bit(matrix.getElement(i, j).getValue()));
            }
        }
        return result;
    }

    public static BitMatrix matrixFromVector(List<Bit> vector){
        BitMatrix result = new BitMatrix(1, vector.size());
        for(int i = 0; i < vector.size(); i++){
            result.setElement(1, i, new Bit(vector.get(i).getValue()));
        }
        return result;
    }

    public static BitMatrix matrixFromListOfVectors(List<List<Bit>> listOfVectors){
        BitMatrix result = new BitMatrix(listOfVectors.size(), listOfVectors.get(0).size());
        for(int i = 0; i < result.getRowCount(); i++){
            if(result.getColCount() != listOfVectors.get(i).size()){
                throw new IllegalArgumentException("All rows must have the same column count");
            }
            for(int j = 0; j < result.getColCount(); j++){
                result.setElement(i, j, new Bit(listOfVectors.get(i).get(j).getValue()));
            }
        }
        return result;
    }

    public static BitMatrix multiply(BitMatrix matrix1, BitMatrix matrix2){
        BitMatrix result = new BitMatrix(matrix1.getRowCount(), matrix2.getColCount());
        for(int i = 0; i < matrix1.getRowCount(); i++){
            for(int j = 0; j < matrix2.getColCount(); j++){
                Bit product = new Bit(0);
                for(int k = 0; k < matrix1.getColCount(); k++){
                    product = BitMath.sum(product, BitMath.multiply(matrix1.getElement(i, k), matrix2.getElement(k, j)));
                }
                result.setElement(i, j, product);
            }
        }
        return result;
    }

    public static List<Bit> multiplyVectorByMatrix(List<Bit> vector, BitMatrix generatorMatrix) {
        List<Bit> result = new LinkedList<>();
        for(int i = 0; i < generatorMatrix.getColCount(); i++){
            Bit product = new Bit(0);
            for(int j = 0; j < vector.size(); j++){
                product = BitMath.sum(product, BitMath.multiply(vector.get(j), generatorMatrix.getElement(j, i)));
            }
            result.add(product);
        }
        return result;
    }

    public static List<Bit> multiplyMatrixByTransposedVector(BitMatrix parityCheckMatrix, List<Bit> vector) {
        return multiplyVectorByMatrix(vector, transpose(parityCheckMatrix));
    }

    public static void print(BitMatrix matrix){
        for(int i = 0; i < matrix.getRowCount(); i++){
            for(int j = 0; j < matrix.getColCount(); j++){
                System.out.print(matrix.getElement(i, j) + " ");
            }
            System.out.println("");
        }
    }

    public static void print(List<Bit> vector){
        for(Bit bit : vector){
            System.out.print(bit + " ");
        }
        System.out.println("");
    }
}
