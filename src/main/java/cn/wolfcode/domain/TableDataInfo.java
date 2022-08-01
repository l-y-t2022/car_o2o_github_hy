package cn.wolfcode.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

@Data
@AllArgsConstructor
public class TableDataInfo {
    //记录总数
    private long total;
    //列表数据
    private List rows;
}
