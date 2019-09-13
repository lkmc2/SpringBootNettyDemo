package com.lin.dao;

import com.lin.BaseTest;
import com.lin.entity.ProjectSummary;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author lkmc2
 * @date 2019/9/13 16:17
 */
public class ProjectSummaryDaoTest extends BaseTest {

    @Autowired
    private ProjectSummaryDao projectSummaryDao;

    @Test
    public void testQueryList() {
        List<ProjectSummary> projectSummaryList = projectSummaryDao.queryList(Arrays.asList("4245f666cee711e980a70050563f55cc", "424967f5cee711e980a70050563f55cc", "42498412cee711e980a70050563f55cc"));
        assertTrue(projectSummaryList.size() > 0);

        projectSummaryList.forEach(System.out::println);
    }

}