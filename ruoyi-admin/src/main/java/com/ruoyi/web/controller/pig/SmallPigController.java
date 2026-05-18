package com.ruoyi.web.controller.pig;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.smallPig.domain.SmallPig;
import com.ruoyi.smallPig.service.ISmallPigService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * smallPigController
 * 
 * @author ruoyi
 * @date 2026-05-17
 */
@RestController
@RequestMapping("/smallPig/pig")
public class SmallPigController extends BaseController
{
    @Autowired
    private ISmallPigService smallPigService;

    /**
     * 查询smallPig列表
     */
    @PreAuthorize("@ss.hasPermi('smallPig:pig:list')")
    @GetMapping("/list")
    public TableDataInfo list(SmallPig smallPig)
    {
        startPage();
        List<SmallPig> list = smallPigService.selectSmallPigList(smallPig);
        return getDataTable(list);
    }

    /**
     * 导出smallPig列表
     */
    @PreAuthorize("@ss.hasPermi('smallPig:pig:export')")
    @Log(title = "smallPig", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SmallPig smallPig)
    {
        List<SmallPig> list = smallPigService.selectSmallPigList(smallPig);
        ExcelUtil<SmallPig> util = new ExcelUtil<SmallPig>(SmallPig.class);
        util.exportExcel(response, list, "smallPig数据");
    }

    /**
     * 获取smallPig详细信息
     */
    @PreAuthorize("@ss.hasPermi('smallPig:pig:query')")
    @GetMapping(value = "/{smallPigId}")
    public AjaxResult getInfo(@PathVariable("smallPigId") Long smallPigId)
    {
        return success(smallPigService.selectSmallPigBySmallPigId(smallPigId));
    }

    /**
     * 新增smallPig
     */
    @PreAuthorize("@ss.hasPermi('smallPig:pig:add')")
    @Log(title = "smallPig", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SmallPig smallPig)
    {
        try {
            return toAjax(smallPigService.insertSmallPig(smallPig));
        }catch (Exception e){
            return error("信息有误");
        }


    }

    /**
     * 修改smallPig
     */
    @PreAuthorize("@ss.hasPermi('smallPig:pig:edit')")
    @Log(title = "smallPig", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SmallPig smallPig)
    {
        return toAjax(smallPigService.updateSmallPig(smallPig));
    }

    /**
     * 删除smallPig
     */
    @PreAuthorize("@ss.hasPermi('smallPig:pig:remove')")
    @Log(title = "smallPig", businessType = BusinessType.DELETE)
	@DeleteMapping("/{smallPigIds}")
    public AjaxResult remove(@PathVariable Long[] smallPigIds)
    {
        return toAjax(smallPigService.deleteSmallPigBySmallPigIds(smallPigIds));
    }
}
