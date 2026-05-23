package com.ruoyi.smallPig.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.smallPig.mapper.SmallPigMapper;
import com.ruoyi.smallPig.domain.SmallPig;
import com.ruoyi.smallPig.service.ISmallPigService;

/**
 * smallPigService业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-17
 */
@Service
public class SmallPigServiceImpl implements ISmallPigService 
{
    @Autowired
    private SmallPigMapper smallPigMapper;

    /**
     * 查询smallPig
     * 
     * @param smallPigId smallPig主键
     * @return smallPig
     */
    @Override
    public SmallPig selectSmallPigBySmallPigId(Long smallPigId)
    {
        return smallPigMapper.selectSmallPigBySmallPigId(smallPigId);
    }

    /**
     * 查询smallPig列表
     * 
     * @param smallPig smallPig
     * @return smallPig
     */
    @Override
    public List<SmallPig> selectSmallPigList(SmallPig smallPig)
    {
        return smallPigMapper.selectSmallPigList(smallPig);
    }

    /**
     * 新增smallPig
     * 
     * @param smallPig smallPig
     * @return 结果
     */
    @Override
    public int insertSmallPig(SmallPig smallPig)
    {
        return smallPigMapper.insertSmallPig(smallPig);
    }

    /**
     * 修改smallPig
     * 
     * @param smallPig smallPig
     * @return 结果
     */
    @Override
    public int updateSmallPig(SmallPig smallPig)
    {
        return smallPigMapper.updateSmallPig(smallPig);
    }

    /**
     * 批量删除smallPig
     * 
     * @param smallPigIds 需要删除的smallPig主键
     * @return 结果
     */
    @Override
    public int deleteSmallPigBySmallPigIds(Long[] smallPigIds)
    {
        return smallPigMapper.deleteSmallPigBySmallPigIds(smallPigIds);
    }

    /**
     * 删除smallPig信息
     * 
     * @param smallPigId smallPig主键
     * @return 结果
     */
    @Override
    public int deleteSmallPigBySmallPigId(Long smallPigId)
    {
        return smallPigMapper.deleteSmallPigBySmallPigId(smallPigId);
    }
}
