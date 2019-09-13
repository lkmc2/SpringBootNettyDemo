package com.lin.service.impl;


import com.lin.dao.ProjectSummaryDao;
import com.lin.entity.ProjectSummary;
import com.lin.service.EngineQueryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 引擎信息查询服务实现类
 * @author lkmc2
 * @date 2019/9/13 14:11
 */
@Service
public class EngineQueryInfoServiceImpl implements EngineQueryInfoService {

    @Autowired
    private ProjectSummaryDao projectSummaryDao;

    @Override
    public List<ProjectSummary> queryList(List<String> idList) {
        return projectSummaryDao.queryList(idList);
    }

}
