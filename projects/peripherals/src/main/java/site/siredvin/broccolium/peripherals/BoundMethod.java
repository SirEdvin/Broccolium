package site.siredvin.broccolium.peripherals;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.core.asm.PeripheralMethod;

import javax.annotation.Nonnull;
import java.util.Objects;

public class BoundMethod {
    private final @Nonnull Object target;
    private final @Nonnull PeripheralMethod method;
    public @Nonnull String name;

    public BoundMethod(@Nonnull Object target, @Nonnull PeripheralMethod method, @Nonnull String name) {
        this.target = target;
        this.method = method;
        this.name = name;
    }

    public boolean equalWithoutTarget(Object other) {
        if (this == other) return true;
        if (!(other instanceof BoundMethod)) return false;
        return name.equals(((BoundMethod) other).name) && method.equals(((BoundMethod) other).method);
    }

    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof BoundMethod)) return false;
        return name.equals(((BoundMethod) other).name) && target.equals(((BoundMethod) other).target) && method.equals(((BoundMethod) other).method);
    }

    public int hashCode() {
        return Objects.hash(target, name, method);
    }

    public MethodResult apply(IComputerAccess access, ILuaContext context, IArguments arguments) throws LuaException {
        return method.apply(target, context, access, arguments);
    }
}
