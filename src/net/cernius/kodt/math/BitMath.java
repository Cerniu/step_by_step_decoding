package net.cernius.kodt.math;

import net.cernius.kodt.datastructures.Bit;

public class BitMath {

    public static Bit sum(Bit bit1, Bit bit2){
        int sum = bit1.getValue() + bit2.getValue();
        if(sum > 1){
            return new Bit(0);
        }else{
            return new Bit(sum);
        }
    }

    public static Bit multiply(Bit bit1, Bit bit2){
        return new Bit(bit1.getValue() * bit2.getValue());
    }
}
