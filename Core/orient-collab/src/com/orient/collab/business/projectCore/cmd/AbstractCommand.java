package com.orient.collab.business.projectCore.cmd;

/**
 * a default command
 *
 * @author Seraph
 *         2016-08-15 下午5:02
 */
public abstract class AbstractCommand<T> implements Command<T> {

    public CommandService getCommandService() {
        return commandService;
    }

    public void setCommandService(CommandService commandService) {
        this.commandService = commandService;
    }

    private CommandService commandService;
}
