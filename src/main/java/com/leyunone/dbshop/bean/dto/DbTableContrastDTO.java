package com.leyunone.dbshop.bean.dto;

import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.TableInfo;
import com.leyunone.dbshop.bean.vo.TableColumnContrastVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 两个数据库对比总结果
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-28
 */
@Getter
@Setter
public class DbTableContrastDTO {

    private TableInfo leftTableInfo;
    
    private TableInfo rightTableInfo;
    
    private List<ColumnInfo> leftColumnInfo;
    
    private List<ColumnInfo> rightColumnInfo;
    
    private List<TableColumnContrastDTO> columnContrasts;
    
    private Boolean hasDifference;

    private Boolean nameDifference;
}
