/******************************************************************************
* @author :Bubeshkumar
* @company: AA
* Description                     : This is the main activity for the application.
									. It loads the webView on which whole UI runs. It also initializes the device layer objects. 
* Third Party applications used   :
* External Objects called         : 
* Known Bugs                      : 
* Modification Log
* Date                 				Author                       Description
* ----------------------------------------------------------------------------
* Mar 12, 2013                   AA - Bubesh                       Created
*******************************************************************************/
package panasonic.android.com.panasonicscanner;

public class IDeviceResponse 
{
	
	private int requestCode;
	
	private int responseCode;
	
	private Object deviceResponseData;

	/**
	 * @return the requestCode
	 */
	public int getRequestCode() 
	{
		return requestCode;
	}

	/**
	 * @param requestCode the requestCode to set
	 */
	public void setRequestCode(int requestCode) 
	{
		this.requestCode = requestCode;
	}

	/**
	 * @return the responseCode
	 */
	public int getResponseCode()
	{
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(int responseCode) 
	{
		this.responseCode = responseCode;
	}

	/**
	 * @return the deviceResponseData
	 */
	public Object getDeviceResponseData()
	{
		return deviceResponseData;
	}

	/**
	 * @param deviceResponseData the deviceResponseData to set
	 */
	public void setDeviceResponseData(Object deviceResponseData)
	{
		this.deviceResponseData = deviceResponseData;
	}

}
