package com.orient.collab.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.businessmodel.service.impl.QueryOrder;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.CalendarEvent;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Task;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import static com.orient.businessmodel.service.impl.CustomerFilter.BETWEEN_AND_FILTER_SPLIT;
import static com.orient.collab.config.CollabConstants.*;
import static com.orient.collab.model.CalendarEvent.TYPE_COLLAB;
import static com.orient.collab.model.CalendarEvent.TYPE_PLAN;

/**
 * CalendarViewBusiness
 *
 * @author Seraph
 *         2016-08-29 下午3:25
 */
@Service
public class CalendarViewBusiness extends BaseBusiness {


    public List<CalendarEvent> getCalendarEvent(String userId, Date filterStartDate, Date filterEndDate) {
        if (filterStartDate == null) {
            ZonedDateTime zdt = ZonedDateTime.now();
            zdt = zdt.minusMonths(1);
            filterStartDate = Date.from(zdt.toInstant());
        }

        if (filterEndDate == null) {
            filterEndDate = new Date();
        }

        List<CalendarEvent> events = UtilFactory.newArrayList();
        events.addAll(getFlowTasks(userId, filterStartDate, filterEndDate));
        //events.addAll(getPlans(userId, filterStartDate, filterEndDate));

        return events;
    }

    private List<CalendarEvent> getFlowTasks(String userId, Date filterStartDate, Date filterEndDate) {
        List<CalendarEvent> events = UtilFactory.newArrayList();
        //只保留未开始的 在时间区间内的协同任务
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateRange = dateFormat.format(filterStartDate) + "<!=!>" + dateFormat.format(filterEndDate);
        CustomerFilter planStartDate = new CustomerFilter("plannedStartDate", EnumInter.SqlOperation.BetweenAnd, dateRange, EnumInter.SqlConnection.Or);
        CustomerFilter actualStartDate = new CustomerFilter("actualStartDate", EnumInter.SqlOperation.BetweenAnd, dateRange, EnumInter.SqlConnection.Or);
        CustomerFilter planEndDate = new CustomerFilter("plannedEndDate", EnumInter.SqlOperation.BetweenAnd, dateRange, EnumInter.SqlConnection.And);
        actualStartDate.setParent(planStartDate);
        planEndDate.setParent(actualStartDate);
        List<Task> tasks = orientSqlEngine.getTypeMappingBmService().get(Task.class,new CustomerFilter("principal", EnumInter.SqlOperation.Equal, userId),
                new CustomerFilter("status", EnumInter.SqlOperation.In, CollabConstants.STATUS_UNSTARTED + "," + CollabConstants.STATUS_PROCESSING), planEndDate);
        if (!CommonTools.isEmptyList(tasks)) {
            try {
                for (Task task : tasks) {
                    CalendarEvent event = new CalendarEvent();
                    event.setTid(TYPE_COLLAB);
                    event.setId(task.getId());
                    event.setTitle(task.getName());
                    event.setStart(!StringUtil.isEmpty(task.getActualStartDate()) ? dateFormat.parse(task.getActualStartDate()) : dateFormat.parse(task.getPlannedStartDate()));
                    event.setEnd(dateFormat.parse(task.getPlannedEndDate()));
                    events.add(event);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return events;
    }

    private Plan getParentPlan(String taskId) {

        return null;
    }

    private List<CalendarEvent> getPlans(String userId, Date filterStartDate, Date filterEndDate) {
        List<CalendarEvent> events = UtilFactory.newArrayList();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Plan> plans = this.orientSqlEngine.getTypeMappingBmService().get(Plan.class,
                QueryOrder.desc(Plan.ACTUAL_END_DATE),
                new CustomerFilter(Plan.PRINCIPAL, EnumInter.SqlOperation.Equal, userId),
                new CustomerFilter(Plan.ACTUAL_END_DATE, EnumInter.SqlOperation.BetweenAnd,
                        simpleDateFormat.format(filterStartDate) + BETWEEN_AND_FILTER_SPLIT + simpleDateFormat.format(filterEndDate)),
                new CustomerFilter(Plan.STATUS, EnumInter.SqlOperation.In, STATUS_FINISHED + "," + STATUS_PROCESSING));

        for (Plan plan : plans) {
            CalendarEvent event = new CalendarEvent();
            event.setId(PLAN + plan.getId());
            event.setTid(TYPE_PLAN);
            event.setTitle(plan.getName());
            event.setMn(PLAN);
            event.setDi(plan.getId());
            try {
                event.setStart(CommonTools.isNullString(plan.getActualStartDate()) ? null : simpleDateFormat.parse(plan.getActualStartDate()));
                event.setEnd(CommonTools.isNullString(plan.getActualEndDate()) ? null : simpleDateFormat.parse(plan.getActualEndDate()));
            } catch (ParseException e) {
            }
            events.add(event);
        }

        return events;
    }
}
