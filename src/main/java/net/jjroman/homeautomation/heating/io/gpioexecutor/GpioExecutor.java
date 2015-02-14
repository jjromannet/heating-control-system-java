package net.jjroman.homeautomation.heating.io.gpioexecutor;

import com.pi4j.io.gpio.*;

import java.util.*;

/**
 * Executor to use concrete Pi4J implementation to use physical GPIO ports and signals.
 * Created by Jan on 13/02/2015.
 */
public enum GpioExecutor implements HardwareExecutor {
    INSTANCE;
    final Map<Pin, GpioPinDigitalOutput> pins;

    private GpioExecutor(){
        this.pins = Collections.synchronizedMap(new HashMap<Pin, GpioPinDigitalOutput>());
    }

    public synchronized PinState changeGpioPinDigitalOutputState(Pin pin, PinState state) {

        if(!pins.containsKey(pin)){
            final GpioController gpio = GpioFactory.getInstance();
            GpioPinDigitalOutput gpioPinDigitalOutput = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04,   // PIN NUMBER
                    state);
            pins.put(pin, gpioPinDigitalOutput);
        }
        pins.get(pin).setState(state);
        return state;
    }

}
