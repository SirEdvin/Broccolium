package site.siredvin.broccolium.peripherals;

public interface IObservingPeripheralPlugin extends IPeripheralPlugin {
    void onFirstAttach();
    void onLastDetach();
}
