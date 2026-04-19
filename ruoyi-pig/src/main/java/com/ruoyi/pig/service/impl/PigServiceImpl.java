package com.ruoyi.pig.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.pig.mapper.PigMapper;
import com.ruoyi.pig.domain.Pig;
import com.ruoyi.pig.service.IPigService;

/**
 * pigService业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-19
 */
@Service
public class PigServiceImpl implements IPigService 
{
    @Autowired
    private PigMapper pigMapper;

    /**
     * 查询pig
     * 
     * @param pigId pig主键
     * @return pig
     */
    @Override
    public Pig selectPigByPigId(Long pigId)
    {
        return pigMapper.selectPigByPigId(pigId);
    }

    /**
     * 查询pig列表
     * 
     * @param pig pig
     * @return pig
     */
    @Override
    public List<Pig> selectPigList(Pig pig)
    {
        return pigMapper.selectPigList(pig);
    }

    /**
     * 新增pig
     * 
     * @param pig pig
     * @return 结果
     */
    @Override
    public int insertPig(Pig pig)
    {
        return pigMapper.insertPig(pig);
    }

    /**
     * 修改pig
     * 
     * @param pig pig
     * @return 结果
     */
    @Override
    public int updatePig(Pig pig)
    {
        return pigMapper.updatePig(pig);
    }

    /**
     * 批量删除pig
     * 
     * @param pigIds 需要删除的pig主键
     * @return 结果
     */
    @Override
    public int deletePigByPigIds(Long[] pigIds)
    {
        return pigMapper.deletePigByPigIds(pigIds);
    }

    /**
     * 删除pig信息
     * 
     * @param pigId pig主键
     * @return 结果
     */
    @Override
    public int deletePigByPigId(Long pigId)
    {
        return pigMapper.deletePigByPigId(pigId);
    }
}
