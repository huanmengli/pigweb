package com.ruoyi.web.controller.pig;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

import com.ruoyi.pig.domain.Pig;
import com.ruoyi.pig.service.IPigService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * pigController
 * 
 * @author ruoyi
 * @date 2026-04-16
 */
@RestController
@RequestMapping("/pig/pig")
public class PigController extends BaseController
{
    @Autowired
    private IPigService pigService;

    /**
     * 查询pig列表
     */
    @PreAuthorize("@ss.hasPermi('pig:pig:list')")
    @GetMapping("/list")
    public TableDataInfo list(Pig pig)
    {
        System.err.println(pig);
        startPage();
        List<Pig> list = pigService.selectPigList(pig);
        return getDataTable(list);
    }

    /**
     * 导出pig列表
     */
    @PreAuthorize("@ss.hasPermi('pig:pig:export')")
    @Log(title = "pig", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Pig pig)
    {
        List<Pig> list = pigService.selectPigList(pig);
        ExcelUtil<Pig> util = new ExcelUtil<Pig>(Pig.class);
        util.exportExcel(response, list, "pig数据");
    }

    /**
     * 获取pig详细信息
     */
    @PreAuthorize("@ss.hasPermi('pig:pig:query')")
    @GetMapping(value = "/{pigId}")
    public AjaxResult getInfo(@PathVariable("pigId") Long pigId)
    {
        return success(pigService.selectPigByPigId(pigId));
    }

    /**
     * 新增pig
     */
    @PreAuthorize("@ss.hasPermi('pig:pig:add')")
    @Log(title = "pig", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Pig pig)
    {
        pig.setPigStatus("0");
        return toAjax(pigService.insertPig(pig));
    }

    /**
     * 修改pig
     */
    @PreAuthorize("@ss.hasPermi('pig:pig:edit')")
    @Log(title = "pig", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Pig pig)
    {
        return toAjax(pigService.updatePig(pig));
    }

    /**
     * 删除pig
     */
    @PreAuthorize("@ss.hasPermi('pig:pig:remove')")
    @Log(title = "pig", businessType = BusinessType.DELETE)
	@DeleteMapping("/{pigIds}")
    public AjaxResult remove(@PathVariable Long[] pigIds)
    {
        return toAjax(pigService.deletePigByPigIds(pigIds));
    }
}
