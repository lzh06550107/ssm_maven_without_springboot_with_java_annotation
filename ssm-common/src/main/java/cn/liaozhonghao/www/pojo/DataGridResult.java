package cn.liaozhonghao.www.pojo;

import java.util.List;

/**
 * 分页结果封装实体
 */
public class DataGridResult {
    /**
     * 总的记录数
     */
    Long total;
    /**
     * 数据集
     */
    List<?> rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}
