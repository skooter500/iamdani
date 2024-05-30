package ie.tudublin;

public class AKAIControllerHandler implements ControllerHandler {

    IAMDANI v;

    public AKAIControllerHandler(IAMDANI v) {
        this.v = v;
    }
    @Override
    public void controllerChange(int channel, int number, int value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'controllerChange'");
    }

    @Override
    public void noteOn(int channel, int pitch, int velocity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'noteOn'");
    }
    
}
