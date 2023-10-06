package site.siredvin.broccolium.peripherals;

import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

import java.util.function.Consumer;

public interface IPluggablePeripheral extends IPeripheral {
    void forEachComputer(Consumer<IComputerAccess> func);
    boolean isComputerPresent(int computerID);
    int getConnectedComputersCount();

    default void queueEvent(String event, Object... arguments) {
        forEachComputer(it -> it.queueEvent(event, arguments));
    }
}
