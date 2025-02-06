package com.leyunone.dbshop.bean;


/**
 * LeYunone 
 *
 * 牢房单元格数据 封装两侧
 * @param <Cell>
 * @param <Mate>
 */
public class ResponseCell<Cell,Mate> {

    private Cell cellData;

    private Mate mateData;

    private ResponseCell(){}

    public ResponseCell(Cell cellData,Mate mateData) {
        this.cellData = cellData;
        this.mateData = mateData;
    }

    public static <Cell,Mate> ResponseCell<Cell,Mate> build(Cell cellData, Mate mateDate) {
        return new ResponseCell<>(cellData,mateDate);
    }

    public Cell getCellData() {
        return cellData;
    }

    public ResponseCell<Cell, Mate> setCellData(Cell cellData) {
        this.cellData = cellData;
        return this;
    }

    public Mate getMateData() {
        return mateData;
    }

    public ResponseCell<Cell, Mate> setMateData(Mate mateData) {
        this.mateData = mateData;
        return this;
    }
}
