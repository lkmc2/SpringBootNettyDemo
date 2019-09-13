package com.lin.service;


import com.lin.entity.ProjectSummary;

import java.util.List;

/**
 * 引擎信息查询服务
 * @author lkmc2
 * @date 2019/9/13 13:59
 */
public interface EngineQueryInfoService {
    List<ProjectSummary> queryList(List<String> idList);
}
