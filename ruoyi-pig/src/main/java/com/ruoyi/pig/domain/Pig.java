package com.ruoyi.pig.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * pig对象 pig
 * 
 * @author ruoyi
 * @date 2026-04-19
 */
public class Pig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 家猪代码 */
    private Long pigId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String pigName;

    /** 家猪性别 */
    @Excel(name = "家猪性别")
    private String pigSex;

    /** 家猪日龄 */
    @Excel(name = "家猪日龄")
    private String pigAge;

    /** 家猪状态(0空闲 1配种 2怀孕 3未断奶 4断奶小猪) */
    @Excel(name = "家猪状态(0空闲 1配种 2怀孕 3未断奶 4断奶小猪)")
    private String pigStatus;

    /** 需要配种的家猪id */
    @Excel(name = "需要配种的家猪id")
    private Long pigPigid;

    /** 家猪创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "家猪创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pigCreatetime;

    /** 家猪修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "家猪修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pigUpdatetime;

    /** 家猪创建人 */
    @Excel(name = "家猪创建人")
    private String pigCreateby;

    /** 家猪修改人 */
    @Excel(name = "家猪修改人")
    private String pigUpdateby;

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

    public void setPigCreatetime(Date pigCreatetime) 
    {
        this.pigCreatetime = pigCreatetime;
    }

    public Date getPigCreatetime() 
    {
        return pigCreatetime;
    }

    public void setPigUpdatetime(Date pigUpdatetime) 
    {
        this.pigUpdatetime = pigUpdatetime;
    }

    public Date getPigUpdatetime() 
    {
        return pigUpdatetime;
    }

    public void setPigCreateby(String pigCreateby) 
    {
        this.pigCreateby = pigCreateby;
    }

    public String getPigCreateby() 
    {
        return pigCreateby;
    }

    public void setPigUpdateby(String pigUpdateby) 
    {
        this.pigUpdateby = pigUpdateby;
    }

    public String getPigUpdateby() 
    {
        return pigUpdateby;
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
            .append("pigCreatetime", getPigCreatetime())
            .append("pigUpdatetime", getPigUpdatetime())
            .append("pigCreateby", getPigCreateby())
            .append("pigUpdateby", getPigUpdateby())
            .toString();
    }
}
