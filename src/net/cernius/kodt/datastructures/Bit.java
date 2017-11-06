package net.cernius.kodt.datastructures;

/*
    Class for a single bit.
 */
public class Bit {
    private int value;

    public Bit(int value){
        if(value == 0 || value == 1){
            this.value = value;
        }else{
            throw new IllegalArgumentException("net.cernius.kodt.datastructures.Bit value must be 0 or 1");
        }
    }

    public void invert(){
        this.value = this.value^1;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Bit){
            return this.getValue() == ((Bit) obj).getValue();
        }else{
            return false;
        }
    }
}
