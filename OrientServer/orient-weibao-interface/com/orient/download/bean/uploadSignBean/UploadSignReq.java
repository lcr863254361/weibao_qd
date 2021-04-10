package com.orient.download.bean.uploadSignBean;

import java.util.List;

/**
 * 上传签署的模型
 *
 * Author WangJie
 * Created on 2019/3/27.
 */
public class UploadSignReq {
    private List<SignModel> signModelList;

    public UploadSignReq(List<SignModel> signModelList) {
        this.signModelList = signModelList;
    }

    public List<SignModel> getSignModelList() {
        return signModelList;
    }

    public void setSignModelList(List<SignModel> signModelList) {
        this.signModelList = signModelList;
    }

    public int getListSize(){
        return signModelList.size();
    }
}
