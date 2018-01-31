package panasonic.android.com.panasonicscanner;

/**
 * Created by jeffbreunig on 6/8/17.
 */

public class ToughpadDeviceInfo implements IDeviceInfo {
    private boolean isActive;

    @Override
    public boolean isDeviceActive()
    {

        return isActive;
    }

    public void setDeviceState(boolean state)
    {

        this.isActive = state;
    }


    @Override
    public String getPackageName()
    {

        return "com.panasonic.toughpad.android";
    }
}
