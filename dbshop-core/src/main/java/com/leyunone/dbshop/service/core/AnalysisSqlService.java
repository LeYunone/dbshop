package com.leyunone.dbshop.service.core;

import com.leyunone.dbshop.bean.bo.AnalysisSqlBO;
import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.TableColumnContrastDTO;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.constant.DbShopConstant;
import com.leyunone.dbshop.enums.SqlModelEnum;
import com.leyunone.dbshop.excutor.SqlProductionExcutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * :)
 * 解析sql的服务
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-23
 */
public interface AnalysisSqlService {

    List<AnalysisSqlBO> modifyColumnSql(TableColumnContrastDTO columnContrastDTO, SqlProductionDTO sqlProductionDTO);
}
