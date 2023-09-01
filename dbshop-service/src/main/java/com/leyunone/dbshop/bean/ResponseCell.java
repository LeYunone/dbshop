package com.leyunone.dbshop.bean;

import lombok.*;

/**
 * LeYunone 
 *
 * 牢房单元格数据 封装两侧
 * @param <Cell>
 * @param <Mate>
 */
@Getter
@Setter
@ToString
@Builder
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
}
