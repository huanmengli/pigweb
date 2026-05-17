package com.ruoyi.smallPig.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * smallPig对象 small_pig
 * 
 * @author ruoyi
 * @date 2026-05-17
 */
public class SmallPig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 小猪代号 */
    private Long smallPigId;

    /** 小猪性别(1母2公) */
    @Excel(name = "小猪性别(1母2公)")
    private String smallPigSex;

    /** 小猪年龄 */
    @Excel(name = "小猪年龄")
    private Long smallPigAge;

    /** 小猪出生时那一窝的数量 */
    @Excel(name = "小猪出生时那一窝的数量")
    private Long smallPigBirthnum;

    /** 小猪状态(1未断奶2断奶) */
    @Excel(name = "小猪状态(1未断奶2断奶)")
    private String smallPigStatus;

    /** 小猪的母猪 */
    @Excel(name = "小猪的母猪")
    private Long smallPigPigId;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date smallPigCreatetime;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date smallPigUpdatetime;

    public void setSmallPigId(Long smallPigId) 
    {
        this.smallPigId = smallPigId;
    }

    public Long getSmallPigId() 
    {
        return smallPigId;
    }

    public void setSmallPigSex(String smallPigSex) 
    {
        this.smallPigSex = smallPigSex;
    }

    public String getSmallPigSex() 
    {
        return smallPigSex;
    }

    public void setSmallPigAge(Long smallPigAge) 
    {
        this.smallPigAge = smallPigAge;
    }

    public Long getSmallPigAge() 
    {
        return smallPigAge;
    }

    public void setSmallPigBirthnum(Long smallPigBirthnum) 
    {
        this.smallPigBirthnum = smallPigBirthnum;
    }

    public Long getSmallPigBirthnum() 
    {
        return smallPigBirthnum;
    }

    public void setSmallPigStatus(String smallPigStatus) 
    {
        this.smallPigStatus = smallPigStatus;
    }

    public String getSmallPigStatus() 
    {
        return smallPigStatus;
    }

    public void setSmallPigPigId(Long smallPigPigId) 
    {
        this.smallPigPigId = smallPigPigId;
    }

    public Long getSmallPigPigId() 
    {
        return smallPigPigId;
    }

    public void setSmallPigCreatetime(Date smallPigCreatetime) 
    {
        this.smallPigCreatetime = smallPigCreatetime;
    }

    public Date getSmallPigCreatetime() 
    {
        return smallPigCreatetime;
    }

    public void setSmallPigUpdatetime(Date smallPigUpdatetime) 
    {
        this.smallPigUpdatetime = smallPigUpdatetime;
    }

    public Date getSmallPigUpdatetime() 
    {
        return smallPigUpdatetime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("smallPigId", getSmallPigId())
            .append("smallPigSex", getSmallPigSex())
            .append("smallPigAge", getSmallPigAge())
            .append("smallPigBirthnum", getSmallPigBirthnum())
            .append("smallPigStatus", getSmallPigStatus())
            .append("smallPigPigId", getSmallPigPigId())
            .append("smallPigCreatetime", getSmallPigCreatetime())
            .append("smallPigUpdatetime", getSmallPigUpdatetime())
            .toString();
    }
}
