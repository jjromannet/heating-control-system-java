package net.jjroman.homeautomation.heating.io.gpioexecutor;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
/**
 * As used library Pi4j is not thread safe, this layer is to assure thread safety.
 * Implementation should be thread safe!
 * Created by Jan on 13/02/2015.
 */
public interface HardwareExecutor {
    PinState changeGpioPinDigitalOutputState(Pin pin, PinState state);
}
