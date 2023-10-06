package site.siredvin.broccolium.peripherals;

import dan200.computercraft.core.asm.PeripheralMethod;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface IPeripheralPlugin {
    default List<IPeripheralOperation<Object>> getOperations() {
        return Collections.emptyList();
    }

    default @Nullable String getAdditionalType() {
        return null;
    }

    default @Nullable IPluggablePeripheral getConnectedPeripheral() {
        return null;
    }

    default void setConnectedPeripheral(@Nullable IPluggablePeripheral peripheral) {

    }

    default List<BoundMethod> getMethods() {
        return PeripheralMethod.GENERATOR.getMethods(this.getClass()).stream().map(it -> new BoundMethod(this, it.getMethod(), it.getName()))
            .collect(Collectors.toList());
    }

}
