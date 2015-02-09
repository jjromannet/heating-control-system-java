package net.jjroman.homeautomation.heating.core.controler.impl;

import net.jjroman.homeautomation.heating.core.ExceptionHandler;
import net.jjroman.homeautomation.heating.core.controler.ControlAction;
import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;
import net.jjroman.homeautomation.heating.core.modules.UnknownModuleStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jan on 09/02/2015.
 */
public class ChangeStateAction implements ControlAction {

    private static final Logger logger = LoggerFactory.getLogger(CoalBurnerControlLogic.class);

    private final ModuleState from;
    private final ModuleState to;
    private final LogicalModule targetModule;

    public ChangeStateAction(ModuleState from, ModuleState to, LogicalModule targetModule){
        super();
        this.from = from;
        this.to = to;
        this.targetModule = targetModule;
        logger.debug(String.format("Created ChangeStateAction object from state: %s to state: %s, target module: %s", from,to,targetModule));
    }


    @Override
    public boolean execute() {
        boolean returnValue = false;
        try {
            returnValue = targetModule.changeStatus(from, to);
        } catch (UnknownModuleStateException ex){
            ExceptionHandler.handle(ex);
        }
        return returnValue;
    }
}
