package site.siredvin.broccolium.peripherals;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IDynamicPeripheral;
import dan200.computercraft.api.peripheral.IPeripheral;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

public class PluggablePeripheral<T> implements IPluggablePeripheral, IDynamicPeripheral {
    protected @Nonnull String peripheralType;
    protected @Nullable T peripheralTarget;
    protected List<IComputerAccess> connectedComputers = new ArrayList<>();
    protected boolean initialized = false;
    protected @Nonnull List<BoundMethod> pluggedMethods = new ArrayList<>();
    protected @Nullable List<IPeripheralPlugin> plugins = null;
    protected @Nonnull String[] methodNames = new String[0];
    protected @Nullable Set<String> additionalTypes = null;

    public PluggablePeripheral(@Nonnull String peripheralType, @Nullable T peripheralTarget) {
        this.peripheralTarget = peripheralTarget;
        this.peripheralType = peripheralType;
    }

    public List<IComputerAccess> getConnectedComputers() {
        return connectedComputers;
    }

    protected void addAdditionalType(@Nullable String additionalType) {
        if (additionalType != null && !additionalType.equals(peripheralType)) {
            if (additionalTypes == null) {
                additionalTypes = new HashSet<>();
            }
            additionalTypes.add(additionalType);
        }
    }

    protected void connectPlugin(@Nonnull IPeripheralPlugin plugin) {
        pluggedMethods.addAll(plugin.getMethods());
        addAdditionalType(plugin.getAdditionalType());
        plugin.setConnectedPeripheral(this);
    }

    protected void collectPluginMethods() {
        if (plugins != null) {
            plugins.forEach(this::connectPlugin);
        }
    }

    protected void buildPlugins() {
        if (!initialized) {
            initialized = true;
            pluggedMethods.clear();
            if (additionalTypes != null) additionalTypes.clear();
            collectPluginMethods();
            methodNames = pluggedMethods.stream().map(it -> it.name).toArray(String[]::new);
        }
    }

    public void addPlugin(@Nonnull IPeripheralPlugin plugin) {
        if (plugins == null) plugins = new ArrayList<>();
        plugins.add(plugin);
        addAdditionalType(plugin.getAdditionalType());
    }

    @Override
    public synchronized void attach(@NotNull IComputerAccess computer) {
        connectedComputers.add(computer);
        if (connectedComputers.size() == 1 && plugins != null) {
            plugins.forEach(it -> {
                if (it instanceof IObservingPeripheralPlugin observingPeripheralPlugin) observingPeripheralPlugin.onFirstAttach();
            });
        }
    }

    @Override
    public synchronized void detach(@NotNull IComputerAccess computer) {
        connectedComputers.remove(computer);
        if (connectedComputers.isEmpty() && plugins != null) {
            plugins.forEach(it -> {
                if (it instanceof IObservingPeripheralPlugin observingPeripheralPlugin) observingPeripheralPlugin.onLastDetach();
            });
        }
    }

    @Override
    public synchronized void forEachComputer(Consumer<IComputerAccess> func) {
        connectedComputers.forEach(func);
    }

    @Override
    public synchronized boolean isComputerPresent(int computerID) {
        return connectedComputers.stream().anyMatch(it -> it.getID() == computerID);
    }

    @Override
    public synchronized int getConnectedComputersCount() {
        return connectedComputers.size();
    }

    public boolean equals(@Nonnull PluggablePeripheral<?> other) {
        if (!other.peripheralType.equals(peripheralType) || !Objects.equals(other.peripheralTarget, peripheralTarget)) return false;
        if (initialized) {
            return pluggedMethods.stream().allMatch(it -> other.pluggedMethods.stream().anyMatch(it::equalWithoutTarget));
        }
        if (other.initialized) return false;
        return Objects.equals(plugins, other.plugins);
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        if (other instanceof PluggablePeripheral<?> pluggablePeripheral)
            return equals(pluggablePeripheral);
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof PluggablePeripheral<?> pluggablePeripheral)
            return equals(pluggablePeripheral);
        return false;
    }

    @Override
    @Nonnull
    public String[] getMethodNames() {
        if (!initialized) buildPlugins();
        return methodNames;
    }

    @Nonnull
    @Override
    public Set<String> getAdditionalTypes() {
        if (additionalTypes == null)
            return Collections.emptySet();
        return additionalTypes;
    }

    @Nonnull
    @Override
    public String getType() {
        return peripheralType;
    }

    @Nullable
    @Override
    public Object getTarget() {
        return peripheralTarget;
    }

    @Nonnull
    @Override
    public MethodResult callMethod(@NotNull IComputerAccess computer, @NotNull ILuaContext context, int method, @NotNull IArguments arguments) throws LuaException {
        if (!initialized) buildPlugins();
        return pluggedMethods.get(method).apply(computer, context, arguments);
    }

    @Override
    public int hashCode() {
        var result = peripheralType.hashCode();
        if (peripheralTarget != null)
            result = 31 * result + (peripheralTarget.hashCode());
        result = 31 * result + pluggedMethods.hashCode();
        return result;
    }
}
