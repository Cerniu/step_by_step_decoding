package net.cernius.kodt;

import net.cernius.kodt.datastructures.Bit;
import net.cernius.kodt.datastructures.BitMatrix;
import net.cernius.kodt.math.BitMatrixMath;
import net.cernius.kodt.math.CodeMath;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Decoder {

    private final BitMatrix generatorMatrix;
    private final BitMatrix parityCheckMatrix;
    private final Map<String, Integer> syndromeToCosetLeaderWeightMapping;

    public Decoder(BitMatrix generatorMatrix){
        this.generatorMatrix = generatorMatrix;
        this.parityCheckMatrix = CodeMath.parityCheckMatrixForGeneratorMatrix(generatorMatrix);
        this.syndromeToCosetLeaderWeightMapping = CodeMath.getSyndromeToCosetLeaderWeightMapping(generatorMatrix, parityCheckMatrix);
    }

    public List<Bit> decode(List<Bit> vector){
        List<Bit> syndrome = BitMatrixMath.multiplyMatrixByTransposedVector(parityCheckMatrix, vector);
        String syndromeAsString = String.join("", syndrome.stream().map(bit -> bit.toString()).collect(Collectors.toList()));

        int index = 0;
        int currentWeight = syndromeToCosetLeaderWeightMapping.get(syndromeAsString);

        while(currentWeight > 0 || index < vector.size()){
            vector.get(index).invert();
            syndrome = BitMatrixMath.multiplyMatrixByTransposedVector(parityCheckMatrix, vector);
            syndromeAsString = String.join("", syndrome.stream().map(bit -> bit.toString()).collect(Collectors.toList()));
            if(this.syndromeToCosetLeaderWeightMapping.get(syndromeAsString) < currentWeight){
                currentWeight = this.syndromeToCosetLeaderWeightMapping.get(syndromeAsString);
            }else{
                vector.get(index).invert();
            }
            index++;
        }

        List<Bit> result = new LinkedList<>();
        for(int i = 0; i < this.generatorMatrix.getRowCount(); i++){
            result.add(vector.get(i));
        }
        return result;
    }
}
