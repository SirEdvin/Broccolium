package site.siredvin.broccolium.peripherals;

import java.util.Map;

public interface IPeripheralOperation<T> {
    String getName();
    int getCooldown(T context);
    int getCost(T context);
    Map<String, Object> getDescription();
}
