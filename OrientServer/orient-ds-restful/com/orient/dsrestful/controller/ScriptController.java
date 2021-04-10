package com.orient.dsrestful.controller;

import com.orient.dsrestful.business.ScriptBusiness;
import com.orient.web.base.dsbean.CommonResponse;
import com.orient.dsrestful.domain.script.*;
import com.orient.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-26 20:31
 */
@Controller
@RequestMapping("/script")
public class ScriptController extends BaseController {

    @Autowired
    ScriptBusiness scriptBusiness;

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById", method = RequestMethod.POST)
    @ResponseBody
    public ScriptResponse getById(@RequestParam long id) {
        return scriptBusiness.getById(id);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public ScriptListResponse getList() {
        return scriptBusiness.getList();
    }

    /**
     * @param addScriptRequest
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public AddScriptResponse insert(@RequestBody AddScriptRequest addScriptRequest) {
        return scriptBusiness.insert(addScriptRequest);
    }

    /**
     * @param updateScriptRequest
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public AddScriptResponse update(@RequestBody UpdateScriptRequest updateScriptRequest) {
        return scriptBusiness.update(updateScriptRequest);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse delete(@RequestBody List<String> idList) {
        return scriptBusiness.delete(idList);
    }

}
