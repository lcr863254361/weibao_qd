package com.orient.sysmodel.service.sys;

import com.orient.sysmodel.domain.sys.SeqGenerator;

import java.util.List;

public interface SeqGeneratorService {

    /**
     * 获取自增值生产器脚本信息记录列表
     *
     * @return List<SeqGenerator>
     * @Method: findAll
     */
    List<SeqGenerator> findAll();

    /**
     * 根据编号获取自增值生产器脚本信息
     *
     * @param id 编号
     * @return SeqGenerator
     * @Method: findById
     */
    SeqGenerator findById(Long id);

    /**
     * 根据名称获取自增值生产器脚本信息
     *
     * @param name 名称
     * @return List<SeqGenerator>
     * @Method: findByName
     */
    List<SeqGenerator> findByName(String name);

    /**
     * 获取自增值生产器脚本的下一个自增编号
     *
     * @return String 自增编号
     * @Method: findNextVal
     */
    Long findNextVal();

    /**
     * 新增SeqGenerator对象
     *
     * @param map SeqGenerator对象
     * @return void
     * @Method: insert
     */
    void insert(SeqGenerator map);

    /**
     * 检查脚本是否被使用
     *
     * @param name 脚本名称
     * @return List
     * @Method: checkScriptUsed
     */
    List checkScriptUsed(String name);

    /**
     * 修改SeqGenerator对象
     *
     * @param map SeqGenerator对象
     * @return void
     * @Method: update
     */
    void update(SeqGenerator map);

    /**
     * 删除SeqGenerator对象
     *
     * @param map SeqGenerator对象
     * @return void
     * @Method: insert
     */
    void delete(SeqGenerator map);

}
