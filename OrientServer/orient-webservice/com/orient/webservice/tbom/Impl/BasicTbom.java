package com.orient.webservice.tbom.Impl;

import com.orient.metamodel.operationinterface.ISchema;
import com.orient.sysmodel.domain.tbom.Tbom;
import com.orient.sysmodel.domain.tbom.TbomDir;
import com.orient.utils.StringUtil;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BasicTbom extends TbomBean {
    /* (non-Javadoc)
     * @see com.orient.webservice.tbom.ITbom#IsLock(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("rawtypes")
    public String IsLock(String tbomname, String schemaid) {
        List list = tbomService.getTbomDirDAO().findByNameAndSchemaid(tbomname,
                schemaid.substring(6));
        if (!list.isEmpty()) {
            TbomDir tbomDir = (TbomDir) list.get(0);
            if (tbomDir.getIsLock().equals(new Long(1))) {
                return "true" + tbomDir.getUserid();
            }
        }
        return "false";
    }

    /* (non-Javadoc)
     * @see com.orient.webservice.tbom.ITbom#saveTbomOrder(java.lang.String)
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public String saveTbomOrder(String undateTbomOrderStr) {
        String[] tbomdir = undateTbomOrderStr.split(",;.,,");
        for (int j = 0; j < tbomdir.length; j++) {
            String[] tbom_arr = tbomdir[j].split("=,.==");
            TbomDir tbomDir = tbomService.getTbomDirDAO().findById(tbom_arr[0]);
            tbomDir.setOrder_sign(Long.valueOf(tbom_arr[3]));
            tbomService.getTbomDirDAO().save(tbomDir);
        }

        return "true";
    }

    /* (non-Javadoc)
     * @see com.orient.webservice.tbom.ITbom#getTbomName()
     */
    @SuppressWarnings("unchecked")
    public String getTbomName() {
        Map<String, TbomDir> tbomDirMap = roleEngine.getRoleModel(false).getTbomDirs();
        List<TbomDir> dirList = tbomDirMap.values().stream().sorted((t1, t2) -> t1.getOrder_sign().intValue() - t2.getOrder_sign().intValue()).collect(Collectors.toList());
        StringBuffer str = new StringBuffer();
        for (TbomDir dir : dirList) {
            if (StringUtil.isEmpty(dir.getId()) || dir.getIsdelete() != 1) {
                continue;
            }
            String schemaId = dir.getSchemaid();
            ISchema schema = metaEngine.getMeta(false).getISchemaById(schemaId);
            if (null != schema.getIsdelete() && schema.getIsdelete() != 1) {
                continue;
            }
            Long type = dir.getType();
            String tt_type;
            if (type == null) {
                tt_type = "0";
            } else {
                tt_type = type.toString();
            }
            str.append(dir.getId()).append("=,.==").append(dir.getName());
            str.append("=,.==").append(tt_type).append("=,.==schema");
            str.append(dir.getSchemaid()).append("=,.==");
            str.append(dir.getOrder_sign()).append(",;.,,");
        }
        return str.toString().equals("") ? null : str.toString();
    }


    /* (non-Javadoc)
    * @see com.orient.webservice.tbom.ITbom#setLock(java.lang.String, java.lang.String, java.lang.Long, java.lang.String)
    */
    public String setLock(String id, String schemaid, Long lock, String username) {
        TbomDir oldtbomdir = roleEngine.getRoleModel(false).getTbomDirs().get(id);
        if (oldtbomdir == null) {
            return 5 + username;
        }
        Long oldLock = oldtbomdir.getIsLock();
        String oldusername = oldtbomdir.getUserid();
        String sql = "UPDATE CWM_TBOM_DIR SET IS_LOCK=?,USERNAME=? WHERE ID = ?";
        if (lock.equals(new Long(1))) {
            if (oldLock.equals(new Long(1))) {
                if (!username.equals(oldusername)) {
                    return 2 + oldusername;// 已有用户将该业务库锁定
                } else {
                    return 1 + username;
                }
            } else {
                oldtbomdir.setIsLock(lock);
                oldtbomdir.setUserid(username);
                metadaofactory.getJdbcTemplate().update(sql,lock,username,id);
                return 1 + username;
            }
        } else if (lock.equals(new Long(0))) {
            if (username.equals(oldusername)) {
                oldtbomdir.setIsLock(lock);
                oldtbomdir.setUserid(username);
                //tbomService.getTbomDirDAO().merge(oldtbomdir);
                metadaofactory.getJdbcTemplate().update(sql,lock,username,id);
                return 3 + username;// 解锁成功
            } else {
                return 4 + username;// 当前用户并没有锁定该数据库，不需要解锁
            }
        } else if (lock.equals(new Long(3))) {
            if (oldLock.equals(new Long(1))) {
                if (!username.equals(oldusername)) {
                    return 2 + oldusername;// 已有用户将该业务库锁定
                } else {
                    return 1 + username;
                }
            } else {
                return 1 + username;
            }
        }
        return 0 + username;
    }


    /* (non-Javadoc)
    * @see com.orient.webservice.tbom.ITbom#deleteTbom(java.lang.String, java.lang.String, java.lang.String)
    */
    @SuppressWarnings("rawtypes")
    public String deleteTbom(String tbomname, String schemaid, String username) {
        List list = tbomService.getTbomDirDAO().findByNameAndSchemaid(tbomname, schemaid);
        if (!list.isEmpty()) {
            TbomDir tbomDir = (TbomDir) list.get(0);
            if (tbomDir.getIsLock().equals(new Long(1))) {
                String name = tbomDir.getUserid();
                return 1 + name;
            }
            tbomService.getTbomDirDAO().delete(tbomDir);
            List tl = tbomService.getDao().findByNameAndIsrootAndSchemaid(tbomname, new Long(1), schemaid);
            Tbom tbom = null;
            if (!tl.isEmpty()) {
                tbom = (Tbom) tl.get(0);
            } else {
                return "true";
            }

            // 将涉及该TBOM树的所有动态子节点的权限删除
            tbomService.getDynamicTbomRoleDAO().deleteCascade(tbom.getId());

			 /*将涉及该TBOM树的静态子节点权限删除*/
            tbomService.getTbomRoleDAO().deleteCascade(tbom.getId());

            //删除内存中tbom
            //roleEngine.getRoleModel(false).deleteTbom(tbomDir);
            roleEngine.getRoleModel(true);
            //级联删除tbom
            try {
                tbomService.getDao().delete(tbom);
            } catch (Exception e) {
                e.printStackTrace();
                return "false";
            }
            return "true";
        } else {
            return "false";
        }
    }
}
