package com.orient.collab.business.projectCore.cmd;

import org.springframework.stereotype.Component;

/**
 * a default {@link CommandService}'s implementation
 *
 * @author Seraph
 *         2016-07-21 上午11:39
 */
@Component
public class DefaultCommandServiceImpl implements CommandService{
    @Override
    public <T> T execute(Command<T> cmd) throws Exception{
        if(AbstractCommand.class.isAssignableFrom(cmd.getClass())){
            ((AbstractCommand) cmd).setCommandService(this);
        }
        return cmd.execute();
    }
}
