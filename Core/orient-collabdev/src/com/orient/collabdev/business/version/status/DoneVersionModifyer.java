package com.orient.collabdev.business.version.status;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 已完成的数据修改
 *
 * @author panduanduan
 * @create 2018-07-28 10:20 AM
 */
@Component
@MngStatus(status = ManagerStatusEnum.DONE)
public class DoneVersionModifyer extends AbstractVersionModifyer {

    @Override
    public void influentByCreate(IBusinessModel bm, Map<String, String> dataMap, String id) {
        throw new OrientBaseAjaxException("", "当前状态为已完成，不可创建");
    }

    @Override
    public void influentByUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId, String type) {
        throw new OrientBaseAjaxException("", "当前状态为已完成，不可创建");
    }

    @Override
    public void influentByDelete(IBusinessModel bm, String dataIds, String type) {
        throw new OrientBaseAjaxException("", "当前状态为已完成，不可创建");
    }
}
