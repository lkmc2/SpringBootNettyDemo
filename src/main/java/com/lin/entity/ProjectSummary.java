package com.lin.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 汇总表实体类
 * @author lkmc2
 */
@Data
@Table(name = "ip_project_summary")
public class ProjectSummary implements Serializable {

    private static final long serialVersionUID = 816466803478349339L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String summaryId;

    // 就诊流水号
    private String serialNum;
    // 项目院内编码
    private String hospitalProCode;
    // 项目国家编码
    private String countryProCode;
    // 项目名称
    private String projectName;
    // 项目类型
    private String projectType;
    // 项目单价
    private Double unitPrice;
    // 数量
    private Double quantity;
    // 总金额
    private Double totalAmount;
    // 收费科室编码
    private String chargeDeptCode;
    // 收费科室
    private String chargeDeptName;
    // 医嘱名称
    private String docAdviceName;
    // 医嘱数量
    private Integer docAdviceNum;
    // 检验检查报告名称
    private String checkReportName;
    // 检验检查报告数量
    private Integer checkReportNum;
    // 规则引擎审核状态(0-未审核；1-已审核)
    private Integer status;
    // 创建时间
    private Object createDate;
    // 每天数量（频次）
    private Double frequency;
    // 每天数量(住院天数算前算后)
    private Integer dayCountHospitalBeforeAfter;
    // 医嘱流水号
    private String orderNo;
    // 医嘱类型
    private Integer orderPriority;
    // 医嘱开始时间
    private Date orderStartDateTime;
    // 医嘱开单科室
    private String orderingDept;
    // 医嘱结束时间
    private Date orderEndDateTime;
    // 医嘱天数
    private Integer orderDays;
    // 医嘱数量
    private Integer orderAmount;
    // 医嘱小时数
    private Integer orderHours;
    // 收费数量和医嘱数量之差
    private Integer hosUsedQuantity;
    // 是否初次使用，true：是，false：否
    private String firstUse;
    // 每小时数量
    private Integer perHourCount;
    // 每天数量(医嘱天数算前算后)
    private Integer orderPerDayCount;
    // 住院天数
    private Integer hospitalDays;
    // 违规天数
    private Integer violateDays;

}