package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    /**
     * 统计指定时间区间的数据
     * @param begin
     * @param end
     * @return
     */
     public  TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end){

         //当前集合用于存放从begin到end范围内的每天日期
         List<LocalDate> dateList = new ArrayList<>();
         dateList.add(begin);
         while (!begin.equals(end)){
             //日期计算 计算 指定日期的后一天对应的日期
             begin = begin.plusDays(1);
             dateList.add(begin);
         }
         //存放每天的营业额
         List<Double> turnoverList = new ArrayList<>();

         for(LocalDate date:dateList){
             //查询date日期对应的营业数据 状态为已完成的订单金额合计
             LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
             LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
             //select sum(amount) from orders where order_time >beginTime and order_time < endTime and status = ?
             Map map = new HashMap();
             map.put("begin",beginTime);
             map.put("end",endTime);
             map.put("status", Orders.COMPLETED);
             Double turnover =  orderMapper.sumByMap(map);
             turnover = turnover == null? 0.0 : turnover;
             turnoverList.add(turnover);

         }
         //封装返回结果
         return TurnoverReportVO
                 .builder()
                 .dateList(StringUtils.join(dateList,","))
                 .turnoverList(StringUtils.join(turnoverList,","))
                 .build();
     }

    /**
     * 统计指定时间区间的数据  用户
     * @param begin
     * @param end
     * @return
     */
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end){
        //当前集合用于存放从begin到end范围内的每天日期
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)){
            //日期计算 计算 指定日期的后一天对应的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        //存放每天的新增用户数量
        //select count(id) from user where create_time < ? and create_time>?
        List<Integer> newUserList = new ArrayList<>();
        //存放每天总的用户数量
        //select count(id) from user where create_time < ?
        List<Integer> totalUserList = new ArrayList<>();

        for(LocalDate date : dateList){
            LocalDateTime beginTime =  LocalDateTime.of(date,LocalTime.MIN);
            LocalDateTime endTime =  LocalDateTime.of(date,LocalTime.MAX);


            Map map = new HashMap();
            map.put("end",endTime);
            //总用户数量
            Integer totalUser =  userMapper.countByMap(map);
            totalUserList.add(totalUser);
            map.put("begin",beginTime);

            //新增用户数量
            Integer newUser =  userMapper.countByMap(map);
            newUserList.add(newUser);

        }
        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList,","))
                .newUserList(StringUtils.join(newUserList,","))
                .totalUserList(StringUtils.join(totalUserList,","))
                .build();
    }

    /**
     * 统计指定时间区间的数据  订单
     * @param begin
     * @param end
     * @return
     */
    public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end){
        //当前集合用于存放从begin到end范围内的每天日期
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)){
            //日期计算 计算 指定日期的后一天对应的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        //遍历datelist集合 获取每天有效订单和订单总数

        //存放每天的订单总数
        List<Integer> orderCountList = new ArrayList<>();
        //存放每天的有效订单总数
        List<Integer> validOrderCountList = new ArrayList<>();

        for(LocalDate date:dateList){
            //查询每天的订单总数
            LocalDateTime beginTime =  LocalDateTime.of(date,LocalTime.MIN);
            LocalDateTime endTime =  LocalDateTime.of(date,LocalTime.MAX);
            //select count(id) from orders where order_time > ? and order_time < ?
            Map map = new HashMap();
            map.put("begin",beginTime);
            map.put("end",endTime);


            Integer orderCount = orderMapper.countByMap(map);
            orderCountList.add(orderCount);

            map.put("status", Orders.COMPLETED);
            Integer validOrderCount = orderMapper.countByMap(map);
            validOrderCountList.add(validOrderCount);
            //查询每天的有效订单
            //select  count(id) from orders where order_time > ? and order_time < ? and status = 5
        }

        //计算时间区间的订单总数量
         Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).get();
        //计算时间区间的有效订单总数量
        Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).get();

        //计算订单完成率
        Double orderCompletionRate = 0.0;
        if(totalOrderCount != 0){
             orderCompletionRate =    validOrderCount.doubleValue() / totalOrderCount;
        }

        return OrderReportVO.builder()
                .validOrderCount(validOrderCount)
                .totalOrderCount(totalOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .dateList(StringUtils.join(dateList,","))
                .orderCountList(StringUtils.join(orderCountList,","))
                .validOrderCountList(StringUtils.join(validOrderCountList,","))
                .build();
    }


}
