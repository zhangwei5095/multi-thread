package com.jd.anno;

import java.util.Date;

/**
 * Created by caozhifei on 2016/7/20.
 */
public class ExcelVo {
    @Excel(columnName = "序号")
    private int id;
    @Excel(columnName = "商品名称")
    private String name;
    @Excel(columnName = "用户ID")
    private Long uid;
    @Excel(columnName = "创建时间")
    private Date created;
    private Date modified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}
