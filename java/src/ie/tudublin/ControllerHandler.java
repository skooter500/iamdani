package ie.tudublin;

public interface ControllerHandler {
    public void controllerChange(int channel, int number, int value);
    public void noteOn(int channel, int pitch, int velocity);
}
