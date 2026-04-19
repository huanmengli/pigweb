package com.ruoyi.pig.service;

import java.util.List;
import com.ruoyi.pig.domain.Pig;

/**
 * pigService接口
 * 
 * @author ruoyi
 * @date 2026-04-19
 */
public interface IPigService 
{
    /**
     * 查询pig
     * 
     * @param pigId pig主键
     * @return pig
     */
    public Pig selectPigByPigId(Long pigId);

    /**
     * 查询pig列表
     * 
     * @param pig pig
     * @return pig集合
     */
    public List<Pig> selectPigList(Pig pig);

    /**
     * 新增pig
     * 
     * @param pig pig
     * @return 结果
     */
    public int insertPig(Pig pig);

    /**
     * 修改pig
     * 
     * @param pig pig
     * @return 结果
     */
    public int updatePig(Pig pig,Long id);

    /**
     * 批量删除pig
     * 
     * @param pigIds 需要删除的pig主键集合
     * @return 结果
     */
    public int deletePigByPigIds(Long[] pigIds);

    /**
     * 删除pig信息
     * 
     * @param pigId pig主键
     * @return 结果
     */
    public int deletePigByPigId(Long pigId);
}
