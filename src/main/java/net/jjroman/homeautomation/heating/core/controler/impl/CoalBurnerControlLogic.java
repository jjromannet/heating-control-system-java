package net.jjroman.homeautomation.heating.core.controler.impl;

import net.jjroman.homeautomation.heating.core.controler.ControlAction;
import net.jjroman.homeautomation.heating.core.controler.ControlLogic;
import net.jjroman.homeautomation.heating.core.controler.EnvironmentSnapshot;
import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.impl.CoalBurnerModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by Jan on 08/02/2015.
 */
public class CoalBurnerControlLogic implements ControlLogic {

    private static final Logger logger = LoggerFactory.getLogger(CoalBurnerControlLogic.class);

    private static final String SET_TEMPERATURE_KEY = "coalburner.settemperature";
    private static final String CURRENT_WATER_TEMPERATURE_KEY = "coalburner.currentwatertemperature";


    @Override
    public ControlAction generateAction(LogicalModule logicalModule, EnvironmentSnapshot environmentSnapshot) {

        if(logicalModule.getClass().isAssignableFrom(CoalBurnerModule.class)){
            logger.debug("CoalBurnerControlLogic is able to handle passed object");

            Double setTemp = environmentSnapshot.getValue(SET_TEMPERATURE_KEY);
            Double currentTemp = environmentSnapshot.getValue(CURRENT_WATER_TEMPERATURE_KEY);

            // TODO: STATE Class,
            // TODO: Configs category - configuration key / state value

            /*
             * PSEUDO CODE for operation:

                if(setTemp < currentTemp){
                    WANTED_STATE = ACTIVE_HEATING;
                }else{
                    WANTED_STATE = ACTIVE_HEATING;
                }
                environmentSnapshot.getCurrentState(logicalModule);

                if(WANTED_STATE != CURRENT_STATE){

                    return new ChangeStateAction(CURRENT_STATE, WANTED_STATE, logicalModule);

                }

            */
        }else{
            logger.debug("CoalBurnerControlLogic is not able to handle passed object - pass responsibility forward");
        }
        return new NopControlAction();
    }
}
