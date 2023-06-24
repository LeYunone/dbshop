package com.leyunone.dbshop.bean.bo;

import com.leyunone.dbshop.enums.SqlModelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-24
 */
@Getter
@Setter
@Builder
@NotNull
@AllArgsConstructor
public class AnalysisSqlBO {

    private String sql;
    
    private SqlModelEnum sqlModel;
}
