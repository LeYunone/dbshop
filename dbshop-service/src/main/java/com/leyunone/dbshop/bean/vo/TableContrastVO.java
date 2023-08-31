package com.leyunone.dbshop.bean.vo;

import lombok.*;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-08-31
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableContrastVO {

    private List<TableColumnContrastVO> columnContrasts;
    
    private List<IndexContrastVO> indexContrasts;
}

