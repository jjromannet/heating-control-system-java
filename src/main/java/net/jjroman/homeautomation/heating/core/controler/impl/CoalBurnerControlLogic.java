package net.jjroman.homeautomation.heating.core.controler.impl;

import net.jjroman.homeautomation.heating.core.StringPool;
import net.jjroman.homeautomation.heating.core.controler.ControlAction;
import net.jjroman.homeautomation.heating.core.controler.ControlLogic;
import net.jjroman.homeautomation.heating.core.controler.EnvironmentSnapshot;
import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;
import net.jjroman.homeautomation.heating.core.modules.impl.CoalBurnerModule;
import net.jjroman.homeautomation.heating.core.modules.impl.CoalBurnerModuleState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * State transition Logic:
 * OFF -> OFF                       NopControlAction        turnedOn = false
 * OFF -> ACTIVE_HEATING            ChangeStateAction       currentState = OFF AND turnedOn = true
 * STANDBY -> OFF                   ChangeStateAction       currentState = STANDBY AND turnedOn = false
 * ACTIVE_HEATING -> OFF            ChangeStateAction       currentState = ACTIVE_HEATING AND turnedOn = false
 * STANDBY -> ACTIVE_HEATING        ChangeStateAction       currentState = STANDBY AND CurrentTemperature < OnTemp AND turnedOn = true
 * ACTIVE_HEATING -> STANDBY        ChangeStateAction       currentState = ACTIVE_HEATING AND CurrentTemperature > OffTemp AND turnedOn = true
 * STANDBY -> STANDBY               NopControlAction        currentState = STANDBY AND CurrentTemperature > OnTemp AND turnedOn = true
 * ACTIVE_HEATING -> ACTIVE_HEATING NopControlAction        currentState = ACTIVE_HEATING AND CurrentTemperature < OffTemp AND turnedOn = true
 * Created by Jan on 08/02/2015.
 */
public class CoalBurnerControlLogic implements ControlLogic {

    private static final Logger logger = LoggerFactory.getLogger(CoalBurnerControlLogic.class);

    @Override
    public ControlAction generateAction(LogicalModule logicalModule, EnvironmentSnapshot environmentSnapshot) {

        if(logicalModule.getClass().isAssignableFrom(CoalBurnerModule.class)){
            logger.debug("CoalBurnerControlLogic is able to handle passed object");

            Double turnOnTemp = environmentSnapshot.getDoubleValue(StringPool.COALBURNER_TURN_ON_TEMPERATURE_KEY);
            Double turnOffTemp = environmentSnapshot.getDoubleValue(StringPool.COALBURNER_TURN_OFF_TEMPERATURE_KEY);
            Double currentTemp = environmentSnapshot.getDoubleValue(StringPool.COALBURNER_CURRENT_WATER_TEMPERATURE_KEY);
            Boolean moduleTurnedOn = environmentSnapshot.getBooleanValue(StringPool.COALBURNER_MODULE_TURNED_ON);

            // TODO: Configs category - configuration key / env indicator value
            ModuleState currentState = environmentSnapshot.getCurrentState(logicalModule);
            ModuleState wantedState = currentState; // By default remain on the state

            switch((CoalBurnerModuleState)currentState){
                case OFF:

                    if(moduleTurnedOn){
                        wantedState = CoalBurnerModule.ACTIVE_HEATING;
                        logger.debug("CoalBurnerModule is turning on");
                    }else{
                        wantedState = CoalBurnerModule.OFF;
                        logger.debug("CoalBurnerModule remains turned off");
                    }

                    break;
                case STANDBY:
                    if(moduleTurnedOn == false){
                        wantedState = CoalBurnerModule.OFF;
                        logger.debug("CoalBurnerModule remains turned off");
                    }else{
                        if ( turnOnTemp > currentTemp ) {
                            wantedState = CoalBurnerModule.ACTIVE_HEATING;
                            logger.debug("turn on temperature threshold reached - go to ACTIVE_HEATING");
                        }
                    }
                    break;
                case ACTIVE_HEATING:
                    if(moduleTurnedOn == false){
                        wantedState = CoalBurnerModule.OFF;
                        logger.debug("CoalBurnerModule remains turned off");
                    }else{
                        if (turnOffTemp < currentTemp) {
                            wantedState = CoalBurnerModule.STANDBY;
                            logger.debug("turn off temperature threshold reached - go to STANDBY");
                        }
                    }
                    break;
                default:
                    throw new UnsupportedOperationException();

            }

            if( !wantedState.equals(currentState)){
                logger.info(String.format("State needs to be changed current state: %s, required state: %s", currentState, wantedState));
                return new ChangeStateAction(currentState, wantedState);
            }else{
                logger.debug(String.format("Already on  required state: %s - NopControlAction to be returned", wantedState));
                return new NopControlAction(wantedState);
            }
        }else{
            logger.debug("CoalBurnerControlLogic is not able to handle passed object - pass responsibility forward");
        }
        logger.debug("Default return of NopControlAction");
        return new NopControlAction(logicalModule.getCurrentStatus());
    }
}
