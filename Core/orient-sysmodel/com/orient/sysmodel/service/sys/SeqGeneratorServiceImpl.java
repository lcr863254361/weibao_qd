package com.orient.sysmodel.service.sys;

import java.util.List;
import java.util.Map;

import com.orient.sysmodel.domain.sys.SeqGenerator;
import com.orient.sysmodel.domain.sys.SeqGeneratorDAO;

public class SeqGeneratorServiceImpl implements SeqGeneratorService {

	private SeqGeneratorDAO seqGeneratorDAO = null;

	public SeqGeneratorDAO getSeqGeneratorDAO() {
		return seqGeneratorDAO;
	}

	public void setSeqGeneratorDAO(SeqGeneratorDAO seqGeneratorDAO) {
		this.seqGeneratorDAO = seqGeneratorDAO;
	}

	@Override
	public List<SeqGenerator> findAll() {
		return seqGeneratorDAO.findAll();
	}

	@Override
	public SeqGenerator findById(Long id) {
		return seqGeneratorDAO.findById(id);
	}

	@Override
	public List<SeqGenerator> findByName(String name) {
		return seqGeneratorDAO.findByName(name);
	}

	@Override
	public Long findNextVal() {
		String sql = "SELECT CWM_GEN_SCRIPT_SEQ.NEXTVAL AS ID FROM DUAL";
		// 执行数据库查询		
		List list = seqGeneratorDAO.getSqlResult(sql.toString());
		Long nextVal = Long.parseLong(list.get(0).toString());
		return nextVal;
	}

	@Override
	public void insert(SeqGenerator map) {
		seqGeneratorDAO.save(map);	
	}
	
	@Override
	public List checkScriptUsed(String name) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A.SEQUENCE_NAME AS SEQUENCE_NAME, A.DISPLAY_NAME AS AD_NAME,");
		sql.append(" B.DISPLAY_NAME AS BD_NAME, C.NAME AS CNAME FROM CWM_TAB_COLUMNS A ,");
		sql.append(" CWM_TABLES B, CWM_SCHEMA C WHERE A.IS_VALID = 1 AND A.SEQUENCE_NAME IN(");
		sql.append(name).append(") AND A.TABLE_ID = B.ID AND B.SCHEMA_ID = C.ID");
		return seqGeneratorDAO.getSqlResult(sql.toString());
	}

	@Override
	public void update(SeqGenerator map) {
		seqGeneratorDAO.attachDirty(map);	
	}

	@Override
	public void delete(SeqGenerator map) {
		seqGeneratorDAO.delete(map);
	}
	
}
