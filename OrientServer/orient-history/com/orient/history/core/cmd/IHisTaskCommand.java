package com.orient.history.core.cmd;

/**
 * Created by Administrator on 2016/9/26 0026.
 * TODO 可采用命令队列方式 执行保存历史任务信息
 * 1.打包任务基本信息
 * 2.打包任务绑定信息
 *      2.1 绑定模型信息
 *      2.2 绑定任务设置信息
 *      2.3 绑定任务意见信息
 *      2.4 绑定任务控制流信息
 *      2.5 绑定任务数据流信息
 *      2.6 绑定设计数据信息
 *      2.7 绑定团队信息
 *      2.8 等等
 * 3.序列化后存储至数据库
 * 4.保存任务执行队列 方便回滚
 */
public interface IHisTaskCommand<T> {
    T execute();
}
