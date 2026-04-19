package com.ruoyi.pig.mapper;

import java.util.List;
import com.ruoyi.pig.domain.Pig;
import org.apache.ibatis.annotations.Param;

/**
 * pigMapper接口
 * 
 * @author ruoyi
 * @date 2026-04-19
 */
public interface PigMapper 
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
    public int updatePig(Pig pig,@Param("id") Long id);

    /**
     * 删除pig
     * 
     * @param pigId pig主键
     * @return 结果
     */
    public int deletePigByPigId(Long pigId);

    /**
     * 批量删除pig
     * 
     * @param pigIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePigByPigIds(Long[] pigIds);
}
