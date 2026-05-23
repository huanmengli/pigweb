package com.ruoyi.smallPig.service;

import java.util.List;
import com.ruoyi.smallPig.domain.SmallPig;

/**
 * smallPigService接口
 * 
 * @author ruoyi
 * @date 2026-05-17
 */
public interface ISmallPigService 
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
     * 批量删除smallPig
     * 
     * @param smallPigIds 需要删除的smallPig主键集合
     * @return 结果
     */
    public int deleteSmallPigBySmallPigIds(Long[] smallPigIds);

    /**
     * 删除smallPig信息
     * 
     * @param smallPigId smallPig主键
     * @return 结果
     */
    public int deleteSmallPigBySmallPigId(Long smallPigId);
}
