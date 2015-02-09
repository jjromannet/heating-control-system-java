package net.jjroman.homeautomation.heating.core.controler.impl;

import net.jjroman.homeautomation.heating.core.controler.ControlAction;
import net.jjroman.homeautomation.heating.core.controler.ControlLogic;
import net.jjroman.homeautomation.heating.core.controler.EnvironmentSnapshot;
import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;
import net.jjroman.homeautomation.heating.core.modules.impl.CoalBurnerModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by Jan on 08/02/2015.
 */
public class CoalBurnerControlLogic implements ControlLogic {

    private static final Logger logger = LoggerFactory.getLogger(CoalBurnerControlLogic.class);

    private static final String TURN_ON_TEMPERATURE_KEY = "coalburner.turn_on_water_temperature";
    private static final String TURN_OFF_TEMPERATURE_KEY = "coalburner.turn_off_water_temperature";
    private static final String CURRENT_WATER_TEMPERATURE_KEY = "coalburner.current_water_temperature";
    private static final String MODULE_TURNED_ON = "coalburner.module_turned_on";


    @Override
    public ControlAction generateAction(LogicalModule logicalModule, EnvironmentSnapshot environmentSnapshot) {

        if(logicalModule.getClass().isAssignableFrom(CoalBurnerModule.class)){
            logger.debug("CoalBurnerControlLogic is able to handle passed object");

            Double turnOnTemp = environmentSnapshot.getDoubleValue(TURN_ON_TEMPERATURE_KEY);
            Double turnOffTemp = environmentSnapshot.getDoubleValue(TURN_OFF_TEMPERATURE_KEY);
            Double currentTemp = environmentSnapshot.getDoubleValue(CURRENT_WATER_TEMPERATURE_KEY);
            Boolean moduleTurnedOn = environmentSnapshot.getBooleanValue(MODULE_TURNED_ON);

            // TODO: Configs category - configuration key / env indicator value
            ModuleState currentState = environmentSnapshot.getCurrentState(logicalModule);
            ModuleState wantedState = null;
            if(moduleTurnedOn == false){
                wantedState = CoalBurnerModule.OFF;
                logger.debug("CoalBurnerModule is turned off");
            }else {
                if (turnOnTemp > currentTemp) {
                    wantedState = CoalBurnerModule.ACTIVE_HEATING;
                    logger.debug("turn on temperature threshold reached - go to ACTIVE_HEATING");
                } else if(turnOffTemp < currentTemp) {
                    wantedState = CoalBurnerModule.STANDBY;
                    logger.debug("turn off temperature threshold reached - go to STANDBY");
                }else{
                    wantedState = currentState;
                    logger.debug("required temperature between thresholds stay at " + currentState);
                }
            }

            if( !wantedState.equals(currentState)){
                logger.info(String.format("State needs to be changed current state: %s, required state: %s", currentState, wantedState));
                return new ChangeStateAction(currentState, wantedState, logicalModule);
            }else{
                logger.debug(String.format("Already on  required state: %s - NopControlAction to be returned", wantedState));
            }
        }else{
            logger.debug("CoalBurnerControlLogic is not able to handle passed object - pass responsibility forward");
        }
        logger.debug("Default return of NopControlAction");
        return new NopControlAction();
    }
}
