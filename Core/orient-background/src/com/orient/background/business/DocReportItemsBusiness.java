package com.orient.background.business;

import com.orient.sysmodel.domain.doc.CwmDocHandlerEntity;
import com.orient.sysmodel.domain.doc.CwmDocReportItemsEntity;
import com.orient.sysmodel.service.doc.IDocHandlerService;
import com.orient.sysmodel.service.doc.IDocReportItemsService;
import com.orient.utils.CommonTools;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class DocReportItemsBusiness extends BaseHibernateBusiness<CwmDocReportItemsEntity> {

    @Autowired
    IDocReportItemsService docReportItemsService;

    @Autowired
    IDocHandlerService docHandlerService;

    @Override
    public IDocReportItemsService getBaseService() {
        return docReportItemsService;
    }

    public Map<String, String> saveBookMarks(String[] bookMarkNames) {
        Map<String, String> retVal = new LinkedHashMap<>();
        for (String bookMarkName : bookMarkNames) {
            CwmDocReportItemsEntity cwmDocReportItemsEntity = new CwmDocReportItemsEntity();
            //get handlerId
            cwmDocReportItemsEntity.setBookmarkName(bookMarkName);
            //
            String handlerName = bookMarkName.substring(bookMarkName.lastIndexOf(".") + 1, bookMarkName.length());
            List<CwmDocHandlerEntity> handlerEntities = docHandlerService.list(Restrictions.eq("showName", handlerName));
            if (!CommonTools.isEmptyList(handlerEntities)) {
                cwmDocReportItemsEntity.setDocHandlerId(handlerEntities.get(0).getId());
            } else
                throw new OrientBaseAjaxException("", "未找到先关文档处理器【" + bookMarkName + "】");
            docReportItemsService.save(cwmDocReportItemsEntity);
            retVal.put(cwmDocReportItemsEntity.getId().toString(), "[" + bookMarkName + "]");
        }
        return retVal;
    }
}
