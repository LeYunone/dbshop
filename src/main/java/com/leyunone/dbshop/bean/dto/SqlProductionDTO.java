package com.leyunone.dbshop.bean.dto;

import com.leyunone.dbshop.bean.vo.TableColumnContrastVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/7 15:56
 */
@Getter
@Setter
public class SqlProductionDTO {

    private List<TableColumnContrastDTO> columns;
}
