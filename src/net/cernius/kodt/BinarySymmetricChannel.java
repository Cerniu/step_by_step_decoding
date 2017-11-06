package net.cernius.kodt;

import net.cernius.kodt.datastructures.Bit;

import java.util.LinkedList;
import java.util.List;

public class BinarySymmetricChannel implements Channel {

    private double crossoverProbability;

    public BinarySymmetricChannel(double crossoverProbability){
        if(crossoverProbability < 0 || crossoverProbability > 1){
            throw new IllegalArgumentException("Crossover probability in the interval [0,1]");
        }else {
            this.crossoverProbability = crossoverProbability;
        }
    }


    @Override
    public List<Bit> send(List<Bit> data) {
        List<Bit> result = new LinkedList<>();
        for(Bit bit : data){
            if(Math.random() < crossoverProbability){
                result.add(new Bit(bit.getValue()^1));
            }else{
                result.add(new Bit(bit.getValue()));
            }
        }
        return result;
    }
}
