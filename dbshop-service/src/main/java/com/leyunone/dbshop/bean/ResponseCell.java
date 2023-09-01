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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCell<Cell,Mate> {

    private Cell cellData;

    private Mate mateData;

    public static <Cell,Mate> ResponseCell build(Cell cellData, Mate mateDate) {
        return ResponseCell.builder().cellData(cellData).mateData(mateDate).build();
    }
}
