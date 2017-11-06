package net.cernius.kodt;

import net.cernius.kodt.datastructures.Bit;
import net.cernius.kodt.datastructures.BitMatrix;
import net.cernius.kodt.math.BitMatrixMath;

import java.util.List;

public class Encoder {

    private BitMatrix generatorMatrix;

    public Encoder(BitMatrix generatorMatrix){
        this.generatorMatrix = generatorMatrix;
    }

    public List<Bit> encodeVector(List<Bit> vector){
        return BitMatrixMath.multiplyVectorByMatrix(vector, this.generatorMatrix);
    }

}
