package net.jjroman.homeautomation.heating.io.gpioexecutor;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

/**
 * MockExecutor to be used in unit tests.
 * Created by Jan on 13/02/2015.
 */
public enum MockExecutor implements HardwareExecutor{
    INSTANCE;

    public PinState changeGpioPinDigitalOutputState(Pin pin, PinState state) {
        return state;
    }


}
