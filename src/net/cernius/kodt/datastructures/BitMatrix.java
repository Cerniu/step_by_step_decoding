package net.cernius.kodt.datastructures;

public class BitMatrix {

    protected final Bit[][] data;
    protected final int rowCount;
    protected final int colCount;

    public BitMatrix(int rowCount, int colCount){
        this.data = new Bit[rowCount][colCount];
        this.rowCount = rowCount;
        this.colCount = colCount;
    }

    public Bit getElement(int row, int col){
        return data[row][col];
    }

    public void setElement(int row, int col, Bit value){
        data[row][col] = value;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }
}
