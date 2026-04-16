package com.ruoyi.pig.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * pig对象 pig
 * 
 * @author ruoyi
 * @date 2026-04-16
 */
public class Pig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 野猪id */
    private Long pigId;

    /** 野猪代号 */
    @Excel(name = "野猪代号")
    private String pigName;

    /** 野猪性别 */
    @Excel(name = "野猪性别")
    private String pigSex;

    /** 野猪年龄 */
    @Excel(name = "野猪年龄")
    private String pigAge;

    /** 野猪状态(0空闲 1配种 2分娩) */
    @Excel(name = "野猪状态(0空闲 1配种 2分娩)")
    private String pigStatus;

    /** 需要配种的野猪id */
    @Excel(name = "需要配种的野猪id")
    private Long pigPigid;

    public void setPigId(Long pigId) 
    {
        this.pigId = pigId;
    }

    public Long getPigId() 
    {
        return pigId;
    }

    public void setPigName(String pigName) 
    {
        this.pigName = pigName;
    }

    public String getPigName() 
    {
        return pigName;
    }

    public void setPigSex(String pigSex) 
    {
        this.pigSex = pigSex;
    }

    public String getPigSex() 
    {
        return pigSex;
    }

    public void setPigAge(String pigAge) 
    {
        this.pigAge = pigAge;
    }

    public String getPigAge() 
    {
        return pigAge;
    }

    public void setPigStatus(String pigStatus) 
    {
        this.pigStatus = pigStatus;
    }

    public String getPigStatus() 
    {
        return pigStatus;
    }

    public void setPigPigid(Long pigPigid) 
    {
        this.pigPigid = pigPigid;
    }

    public Long getPigPigid() 
    {
        return pigPigid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("pigId", getPigId())
            .append("pigName", getPigName())
            .append("pigSex", getPigSex())
            .append("pigAge", getPigAge())
            .append("pigStatus", getPigStatus())
            .append("pigPigid", getPigPigid())
            .toString();
    }
}
