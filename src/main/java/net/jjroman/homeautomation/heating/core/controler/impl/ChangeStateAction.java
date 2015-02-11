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

    public ChangeStateAction(ModuleState from, ModuleState to){
        super();
        this.from = from;
        this.to = to;
        logger.debug(String.format("Created ChangeStateAction object from state: %s to state: %s", from,to));
    }


    @Override
    public boolean execute(LogicalModule targetModule) {
        boolean returnValue = false;
        try {
            logger.debug(String.format("Applying ChangeStateAction from state: %s to state: %s to targetModule: %s", from,to, targetModule));
            returnValue = targetModule.changeStatus(from, to);
            if(returnValue)
                    logger.debug("ChangeStateAction applied");
            else
                    logger.warn("ChangeStateAction failed");

        } catch (UnknownModuleStateException ex){
            ExceptionHandler.handle(ex);
        }
        return returnValue;
    }

    public ModuleState getNewState(){
        return to;
    }
}
