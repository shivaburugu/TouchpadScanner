/******************************************************************************
* @author :Bubeshkumar
* @company: AA
* Description                     : The listener interface for recieving Device callbacks.
* Third Party applications used   :
* External Objects called         : 
* Known Bugs                      : 
* Modification Log
* Date                 Author                       Description
* ------------------------------------------------------------
*                       AA                             Created
*******************************************************************************/
package panasonic.android.com.panasonicscanner;


// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving IDevice events.
 * The class that is interested in processing a IDevice
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addIDeviceListener<code> method. When
 * the IDevice event occurs, that object's appropriate
 * method is invoked.
 *
 * @see IDeviceEvent
 */
public interface IDeviceListener{
    
    /**
     * Handle external event.
     *
     * @param event the event
     * @return the int
     */
    public int HandleExternalEvent(String event);
    
    /**
     * Handle error.
     *
     * @param event the event
     * @return the int
     */
    public int HandleError(String event);
    
}