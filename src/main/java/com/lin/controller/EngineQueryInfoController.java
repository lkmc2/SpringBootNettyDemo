package com.lin.controller;

import cn.hutool.core.collection.CollUtil;

import com.lin.entity.ProjectSummary;
import com.lin.service.EngineQueryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 规则引擎信息查询控制器
 * @author lkmc2
 * @date 2019/9/12 12:45
 */
@RestController
@RequestMapping("engineQuery")
public class EngineQueryInfoController {

    @Autowired
    private EngineQueryInfoService engineQueryInfoService;

    @GetMapping("/getInfo")
    public List<ProjectSummary> getInfo(String idListString) {
        List<String> idList = CollUtil.toList(idListString.split(","));
        return engineQueryInfoService.queryList(idList);
    }

}
