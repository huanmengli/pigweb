package com.ruoyi.smallPig.mapper;

import java.util.List;
import com.ruoyi.smallPig.domain.SmallPig;

/**
 * smallPigMapper接口
 * 
 * @author ruoyi
 * @date 2026-05-17
 */
public interface SmallPigMapper 
{
    /**
     * 查询smallPig
     * 
     * @param smallPigId smallPig主键
     * @return smallPig
     */
    public SmallPig selectSmallPigBySmallPigId(Long smallPigId);

    /**
     * 查询smallPig列表
     * 
     * @param smallPig smallPig
     * @return smallPig集合
     */
    public List<SmallPig> selectSmallPigList(SmallPig smallPig);

    /**
     * 新增smallPig
     * 
     * @param smallPig smallPig
     * @return 结果
     */
    public int insertSmallPig(SmallPig smallPig);

    /**
     * 修改smallPig
     * 
     * @param smallPig smallPig
     * @return 结果
     */
    public int updateSmallPig(SmallPig smallPig);

    /**
     * 删除smallPig
     * 
     * @param smallPigId smallPig主键
     * @return 结果
     */
    public int deleteSmallPigBySmallPigId(Long smallPigId);

    /**
     * 批量删除smallPig
     * 
     * @param smallPigIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSmallPigBySmallPigIds(Long[] smallPigIds);
}
