/******************************************************************************
* @author :Bubeshkumar
* @company: AA
* Description                     : Interface class for querying device info.
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
 * The Interface IDeviceInfo.
 */
public interface IDeviceInfo 
{
	
	public boolean isDeviceActive();
	
	public String getPackageName();

}
