package net.cernius.kodt.math;

import net.cernius.kodt.datastructures.Bit;
import net.cernius.kodt.datastructures.BitMatrix;

import java.util.*;
import java.util.stream.Collectors;

public class CodeMath {

    public static BitMatrix parityCheckMatrixForGeneratorMatrix(BitMatrix generatorMatrix){
        int n = generatorMatrix.getColCount();
        int k = generatorMatrix.getRowCount();
        BitMatrix result = new BitMatrix(n - k, n);

        /*
            For standart generator matrix format
            START
         */
        for(int i = 0; i < k; i++) {
            for (int j = 0; j < n - k; j++) {
                result.setElement(j, i, generatorMatrix.getElement(i, j + k));
            }
        }
        for(int i = 0; i < n - k; i++){
            for(int j = 0; j < n - k; j++){
                if(i == j){
                    result.setElement(i, j + k, new Bit(1));
                }else{
                    result.setElement(i, j + k, new Bit(0));
                }
            }
        }
        /*
            For standart generator matrix format END
            For generator matrix format seen in [VO89] START
         */
//        for(int i = 0; i < k; i++){
//            for(int j = 0; j < k; j++){
//                if(i == j){
//                    result.setElement(i, j, new Bit(1));
//                }else{
//                    result.setElement(i, j, new Bit(0));
//                }
//            }
//        }
//        for(int i = 0; i < k; i++){
//            for(int j = k; j < n; j++){
//                result.setElement(i, j, generatorMatrix.getElement(j - k, i));
//            }
//        }
        /*
            For generator matrix format seen in [VO89] END
         */
        return result;
    }

    public static Map<String, Integer> getSyndromeToCosetLeaderWeightMapping(BitMatrix generatorMatrix, BitMatrix parityCheckMatrix){
        List<List<Bit>> allCodeWords = CodeMath.getAllWordsForCode(generatorMatrix);
        List<List<List<Bit>>> standardArray = CodeMath.getStandardArray(allCodeWords, generatorMatrix.getRowCount(), generatorMatrix.getColCount());
        List<List<Bit>> cosetLeaders = CodeMath.getCosetLeaders(standardArray);

        Map<String, Integer> result = new TreeMap<>();
        for(List<Bit> cosetLeader : cosetLeaders){
            List<Bit> syndrome = CodeMath.getSyndromeForVector(cosetLeader, parityCheckMatrix);
            String syndromeAsString = String.join("", syndrome.stream().map(bit -> bit.toString()).collect(Collectors.toList()));
            Integer cosetLeaderWeight = CodeMath.getVectorWeight(cosetLeader);
            result.put(syndromeAsString, cosetLeaderWeight);
        }
        return result;
    }

    private static List<Bit> getSyndromeForVector(List<Bit> vector, BitMatrix parityCheckMatrix){
        return BitMatrixMath.multiplyMatrixByTransposedVector(parityCheckMatrix, vector);
    }

    private static int getVectorWeight(List<Bit> vector){
        int result = 0;
        for(Bit bit : vector){
            if(bit.getValue() == 1){
                result++;
            }
        }
        return result;
    }

    private static List<List<Bit>> getCosetLeaders(List<List<List<Bit>>> stadardArray){
        List<List<Bit>> result = new LinkedList<>();
        for(int i = 0; i < stadardArray.size(); i++){
            result.add(stadardArray.get(i).get(0));
        }
        return result;
    }

    private static List<List<List<Bit>>> getStandardArray(List<List<Bit>> codeWords, int n, int k){
        List<List<List<Bit>>> result = new LinkedList<>();
        for(int i = 0; i < Math.pow(2, k)/codeWords.size(); i++){
            result.add(new LinkedList<>());
        }
        //Užpildome pirmą eilutę kodo žodžiais
        for(int i = 0; i < codeWords.size(); i++){
            result.get(0).add(codeWords.get(i));
        }
        for(int i = 1; i < Math.pow(2, k)/codeWords.size(); i++){
            List<Bit> cosetLeader = CodeMath.getNextCosetLeader(result, n, k);
            result.get(i).add(cosetLeader);
            for(int j = 1; j < codeWords.size(); j++){
                result.get(i).add(CodeMath.addVectors(codeWords.get(j), cosetLeader));
            }
        }
        return result;
    }

    private static List<Bit> getNextCosetLeader(List<List<List<Bit>>> currentStateOfStandardArray, int n, int k){
        List<List<Bit>> todo = new ArrayList<>();
        for(int i = 1; i < Math.pow(2, k); i++){
            List<Bit> vector = CodeMath.intToVector(i, k);
            if(!CodeMath.standardArrayContains(currentStateOfStandardArray, vector)){
                todo.add(vector);
            }
        }

        todo.sort((o1, o2) -> CodeMath.getVectorWeight(o1) - CodeMath.getVectorWeight(o2));

        return todo.get(0);
    }

    private static boolean standardArrayContains(List<List<List<Bit>>> standardArray, List<Bit> vector){
        boolean result = false;
        for(List<List<Bit>> row : standardArray){
            if(row == null){
                break;
            }
            for(List<Bit> element: row){
                if(element.equals(vector)){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    private static List<Bit> addVectors(List<Bit> vector1, List<Bit> vector2){
        List<Bit> result = new LinkedList<>();
        for(int i = 0; i < vector1.size(); i++){
            result.add(BitMath.sum(vector1.get(i), vector2.get(i)));
        }
        return result;
    }

    //TODO
    public static List<List<Bit>> getAllWordsForCode(BitMatrix generatorMatrix){
        List<List<Bit>> result = new ArrayList<>();
        for(int i = 0; i < Math.pow(2, generatorMatrix.getRowCount()); i++){
            List<Bit> vector = CodeMath.intToVector(i, generatorMatrix.getRowCount());
            result.add(BitMatrixMath.multiplyVectorByMatrix(vector, generatorMatrix));
        }
        return result;
    }

    private static List<Bit> intToVector(int number, int vectorLength){
        List<Bit> result = new LinkedList<>();
        for(int j = 0; j < vectorLength; j++){
            if((number & (1 << j)) == 0){
                result.add(new Bit(0));
            }else{
                result.add(new Bit(1));
            }
        }
        Collections.reverse(result);
        return result;
    }


}
