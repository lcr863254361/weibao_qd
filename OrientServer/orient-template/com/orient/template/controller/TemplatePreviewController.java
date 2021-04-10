package com.orient.template.controller;

import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.template.business.TemplatePreviewBusiness;
import com.orient.template.model.CollabTemplatePreviewNode;
import com.orient.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * help to preview a template
 *
 * @author Seraph
 *         2016-09-21 下午4:39
 */
@Controller
@RequestMapping("/templatePreview")
@Transactional(rollbackFor = Exception.class)
public class TemplatePreviewController extends BaseController {

    @RequestMapping("/nextLayerNodes")
    @ResponseBody
    public List<CollabTemplatePreviewNode> getNextLayerNodes(String templateId, String parDataId){
        return this.templatePreviewBusiness.getNextLayerNodes(templateId, parDataId);
    }

    @RequestMapping("/comps")
    @ResponseBody
    public CollabTemplatePreviewNode getComp(String templateId, String nodeId, String previewType){
        return this.templatePreviewBusiness.getComp(templateId, nodeId, previewType);
    }

    @RequestMapping("/bmTemplateNodeToBmData")
    @ResponseBody
    public Map convertBmTemplateNodeToBmData(String nodeId){
        return this.templatePreviewBusiness.covertBmTemplateNodeToBmData(nodeId);
    }


    @Autowired
    private TemplatePreviewBusiness templatePreviewBusiness;
}
