package com.orient.dsrestful.controller;

import com.orient.dsrestful.business.SequenceBusiness;
import com.orient.dsrestful.domain.sequence.SequenceRequestBean;
import com.orient.web.base.BaseController;
import com.orient.web.base.dsbean.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by GNY on 2018/3/26
 */
@Controller
@RequestMapping("/sequence")
public class SequenceController extends BaseController {

    @Autowired
    SequenceBusiness sequenceBusiness;

    /**
     * 把sequence重新初始化，间隔默认为1
     *
     * @param sequenceRequestBean
     * @return
     */
    @RequestMapping(value = "/resetInitialValue", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse updateSequence(@RequestBody SequenceRequestBean sequenceRequestBean) {
        return sequenceBusiness.updateSequence(sequenceRequestBean);
    }

}
