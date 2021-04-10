package com.orient.sysmodel.domain.file;
// default package

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * A data access object (DAO) providing persistence and search support for CwmFile entities.
 * Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions.
 * Each of these methods provides additional information for how to configure it for the desired type of transaction control.
 *
 * @author MyEclipse Persistence Tools
 * @see .CwmFile
 */

public class CwmFileDAO extends HibernateDaoSupport {
    private static final Log log = LogFactory.getLog(CwmFileDAO.class);
    //property constants
    public static final String SCHEMAID = "schemaid";
    public static final String TABLEID = "tableid";
    public static final String FILENAME = "filename";
    public static final String FILEDESCRIPTION = "filedescription";
    public static final String FILETYPE = "filetype";
    public static final String FILELOCATION = "filelocation";
    public static final String PARSE_RULE = "parseRule";
    public static final String UPLOAD_USER_ID = "uploadUser";
    public static final String DELETE_USER_ID = "deleteUser";
    public static final String DATAID = "dataid";
    public static final String FINALNAME = "finalname";
    public static final String EDITION = "edition";
    public static final String IS_VALID = "isValid";
    public static final String FILESECRECY = "filesecrecy";
    public static final String UPLOAD_USER_MAC = "uploadUserMac";
    public static final String UPLOAD_STATUS = "uploadStatus";
    public static final String FILE_FOLDER = "fileFolder";
    public static final String FOLDER_SERIAL = "folderSerial";
    public static final String IS_WHOLE_SEARCH = "isWholeSearch";
    public static final String IS_DATA_FILE = "isDataFile";
    public static final String CWM_FOLDER_ID = "cwmFolderId";
    public static final String CONVER_STATE = "converState";


    public void save(CwmFile transientInstance) {
        log.debug("saving CwmFile instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CwmFile persistentInstance) {
        log.debug("deleting CwmFile instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CwmFile findById(java.lang.String id) {
        log.debug("getting CwmFile instance with id: " + id);
        try {
            CwmFile instance = (CwmFile) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.file.CwmFile", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }


    public List findByExample(CwmFile instance) {
        log.debug("finding CwmFile instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CwmFile instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from CwmFile as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findBySchemaid(Object schemaId) {
        return findByProperty(SCHEMAID, schemaId);
    }

    public List findByTableid(Object tableid) {
        return findByProperty(TABLEID, tableid);
    }

    public List findByFilename(Object filename) {
        return findByProperty(FILENAME, filename);
    }

    public List findByFiledescription(Object fileDescription) {
        return findByProperty(FILEDESCRIPTION, fileDescription);
    }

    public List findByFiletype(Object filetype) {
        return findByProperty(FILETYPE, filetype);
    }

    public List findByFilelocation(Object fileLocation) {
        return findByProperty(FILELOCATION, fileLocation);
    }

    public List findByParseRule(Object parseRule) {
        return findByProperty(PARSE_RULE, parseRule);
    }

    public List findByUploadUserId(Object uploadUserId) {
        return findByProperty(UPLOAD_USER_ID, uploadUserId);
    }

    public List findByDeleteUserId(Object deleteUserId) {
        return findByProperty(DELETE_USER_ID, deleteUserId);
    }

    public List findByDataid(Object dataid) {
        return findByProperty(DATAID, dataid);
    }

    public List findByFinalname(Object finalname) {
        return findByProperty(FINALNAME, finalname);
    }

    public List findByEdition(Object edition) {
        return findByProperty(EDITION, edition);
    }

    public List findByIsValid(Object isValid) {
        return findByProperty(IS_VALID, isValid);
    }

    public List findByFilesecrecy(Object fileSecrecy) {
        return findByProperty(FILESECRECY, fileSecrecy);
    }

    public List findByUploadUserMac(Object uploadUserMac) {
        return findByProperty(UPLOAD_USER_MAC, uploadUserMac);
    }

    public List findByUploadStatus(Object uploadStatus) {
        return findByProperty(UPLOAD_STATUS, uploadStatus);
    }

    public List findByFileFolder(Object fileFolder) {
        return findByProperty(FILE_FOLDER, fileFolder);
    }

    public List findByFolderSerial(Object folderSerial) {
        return findByProperty(FOLDER_SERIAL, folderSerial);
    }

    public List findByIsWholeSearch(Object isWholeSearch) {
        return findByProperty(IS_WHOLE_SEARCH, isWholeSearch);
    }

    public List findByIsDataFile(Object isDataFile) {
        return findByProperty(IS_DATA_FILE, isDataFile);
    }


    public List findAll() {
        log.debug("finding all CwmFile instances");
        try {
            String queryString = "from CwmFile";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CwmFile merge(CwmFile detachedInstance) {
        log.debug("merging CwmFile instance");
        try {
            CwmFile result = (CwmFile) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }


    public void attachDirty(CwmFile instance) {
        log.debug("attaching dirty CwmFile instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CwmFile instance) {
        log.debug("attaching clean CwmFile instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public List getHqlResult(String searchsql) {
        log.debug("getting  CwmFile searchsql sentence" + searchsql);
        try {
            List result = this.getHibernateTemplate().find(searchsql);
            return result;
        } catch (RuntimeException re) {
            log.error("getting  CwmFile searchsql failed", re);
            return null;
        }
    }

    public List findBySchemaidAndIsValid(Object schemaid, Object isvalid) {
        try {
            String queryString = "from CwmFile as model where model." + IS_VALID + "= ? and model." + SCHEMAID + "= ?";
            return getHibernateTemplate().find(queryString, new Object[]{isvalid, schemaid});
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByFileGroup(String tableId, String dataId, String groupName) {
        List ret = null;
        String suffixHql = "SELECT g from CwmFileGroup g where g.groupName=?";
        List list = getHibernateTemplate().find(suffixHql, new Object[]{groupName});
        if (!list.isEmpty()) {
            String suffixes = ((CwmFileGroup) list.get(0)).getGroupType();
            //转换为可以作为查询条件
            String suffSql = "'" + suffixes.replaceAll(",", "','") + "'";
            String hql = new String();
            hql = "FROM CwmFile f where f.filetype in (" + suffSql + ") AND f.tableid=? and f.dataid=?";
            ret = getHibernateTemplate().find(hql, new Object[]{tableId, dataId});
        } else {
            //找不到类型，显示所有数据
            ret = findAll();
        }
        return ret;
    }

    public long findFileByModelAndDataAndGroupCount(String modelId, String dataId, List<String> fileGroupFilter) {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(CwmFile.class);
        criteria.add(Restrictions.eq("tableid", modelId));
        criteria.add(Restrictions.eq("dataid", dataId));
        if (!fileGroupFilter.isEmpty()) {
            criteria.add(Restrictions.in("filetype", fileGroupFilter));
        }
        return criteria.list().size();
    }

    public List<CwmFile> findFileByModelAndDataAndGroup(String modelId, String dataId, List<String> suffixFilter, Integer page, Integer limit) {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(CwmFile.class);
        criteria.add(Restrictions.eq("tableid", modelId));
        criteria.add(Restrictions.eq("dataid", dataId));
        criteria.addOrder(Order.desc("fileid"));
        if (!suffixFilter.isEmpty()) {
            criteria.add(Restrictions.in("filetype", suffixFilter));
        }
        if (null != page) {
            //增加分页
            criteria.setFirstResult((page - 1) * limit);
            criteria.setMaxResults(limit);
        }
        return criteria.list();
    }

    public List<CwmFile> findByFinalNames(List<String> finalNames) {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(CwmFile.class);
        criteria.add(Restrictions.in("finalname", finalNames));
        return criteria.list();
    }

}