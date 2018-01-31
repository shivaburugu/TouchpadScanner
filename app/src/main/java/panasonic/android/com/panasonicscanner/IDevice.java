/******************************************************************************
* @author :Bubeshkumar
* @company: AA
* Description                     : This is the interface class for all the devices.
* Third Party applications used   :
* External Objects called         : 
* Known Bugs                      : 
* Modification Log
* Date                 Author                       Description
* ------------------------------------------------------------
*                       AA                             Created
*******************************************************************************/
package panasonic.android.com.panasonicscanner;

/**
 * The Interface IDevice.
 */
public interface IDevice {

    //Method to Initialize the external device
    /**
     * Inits the.
     *
     * @param strParam the str param
     * @return true, if successful
     */
    boolean Init(IDeviceListener ideviceListener);

    //Method to start reading from the external device
    /**
     * Start.
     *
     * @return true, if successful
     */
    boolean Start();

    //Method to stop reading from external device
    /**
     * Stop.
     *
     * @return true, if successful
     */
    boolean Stop();

    //Method to write data to the port of the external device
    /**
     * Write.
     *
     * @param strWriteData the str write data
     * @return true, if successful
     */
    boolean Write(String strWriteData);

    // Generic method to perform all kind of action supported 
    /**
     * Handle action.
     *
     * @param action the action
     * @param payload the payload
     * @param callbackListener the callback listener
     * @return the int
     */
    int  handleAction(String action, String payload, IDeviceListener callbackListener);
    
    //Method to get the device Information
    /**
     * Gets the device info.
     *
     * @return the i device info
     */
    IDeviceInfo GetDeviceInfo();

    //Method to get the device configuration information
    /**
     * Gets the device config.
     *
     * @param input the input
     * @return the object
     */
    Object GetDeviceConfig(Object input);

    //Method to free the resources
    /**
     * Clean up.
     *
     * @return true, if successful
     */
    boolean CleanUp();
    
    //
    boolean onResult(IDeviceResponse deviceResponsedata);
    
    // Method to acquire resources on activity onResume() call 
    void acquireResources();
    
    // Method to release resources on activity onPause() call 
    void releaseResources();
    
	
}
