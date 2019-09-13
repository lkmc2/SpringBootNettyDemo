package com.lin.dao;


import com.lin.entity.ProjectSummary;
import com.lin.utils.MyMapper;

import java.util.List;

/**
 * 汇总信息 Mapper
 * @author lkmc2
 */
public interface ProjectSummaryDao extends MyMapper<ProjectSummary> {
    List<ProjectSummary> queryList(List<String> idList);
}