package com.orient.background.statistic.impl;

import com.orient.background.statistic.StatisticPreProcessor;
import com.orient.background.statistic.bean.StatisticChartResult;
import com.orient.background.statistic.parse.StatisticSqlParser;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.sysmodel.domain.statistic.CfStatiscticEntity;
import com.orient.sysmodel.domain.statistic.CfStatisticChartEntity;
import com.orient.utils.StringUtil;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/5 0005.
 */
@Component
public class DefaultStatisticPerProcessor<M extends StatisticChartResult> implements StatisticPreProcessor {

    @Autowired
    protected MetaDAOFactory metaDaoFactory;

    @Autowired
    StatisticSqlParser statisticSqlParser;

    @Override
    public M doPreProcess(CfStatiscticEntity statisticSet, HttpServletRequest request, CfStatisticChartEntity statisticChartSet) throws IllegalAccessException, InstantiationException {
        Class<?> c = this.getClass();
        Type t = c.getGenericSuperclass();
        Class entityClass = StatisticChartResult.class;
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            entityClass = (Class<M>) p[0];
        }
        M statisticResult = (M) entityClass.newInstance();
        if (!StringUtil.isEmpty(statisticSet.getSql())) {
            try {
                String parsedSql = statisticSqlParser.doParser(statisticSet.getSql(), request);
                List<Map<String, Object>> data = metaDaoFactory.getJdbcTemplate().queryForList(parsedSql);
                statisticResult.setData(data);
            } catch (IOException e) {
                statisticResult.setErrorMsg(e.toString());
                e.printStackTrace();
            } catch (TemplateException e) {
                statisticResult.setErrorMsg(e.toString());
                e.printStackTrace();
            }
        }
        return statisticResult;
    }
}
