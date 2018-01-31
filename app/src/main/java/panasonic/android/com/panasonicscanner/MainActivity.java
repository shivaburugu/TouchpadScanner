package panasonic.android.com.panasonicscanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.grabba.Grabba;
import com.grabba.GrabbaConnectionListener;
import com.grabba.GrabbaPassport;
import com.grabba.GrabbaPassportListener;
import com.panasonic.toughpad.android.api.ToughpadApi;
import com.panasonic.toughpad.android.api.ToughpadApiListener;
import com.panasonic.toughpad.android.api.barcode.BarcodeData;
import com.panasonic.toughpad.android.api.barcode.BarcodeException;
import com.panasonic.toughpad.android.api.barcode.BarcodeListener;
import com.panasonic.toughpad.android.api.barcode.BarcodeReader;
import com.panasonic.toughpad.android.api.barcode.BarcodeReaderManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements ToughpadApiListener, BarcodeListener, IDevice, IDeviceListener,GrabbaPassportListener,GrabbaConnectionListener {

    private String TAG = MainActivity.class.getSimpleName();
    private List<BarcodeReader> readers;
    private BarcodeReader selectedReader;
    private BarcodeResultListener barResultListener;
    private Handler mResultHandler = new Handler();
    private String webLayerCallBackEvent;
    private IDeviceListener mCallbackListener = null;
    private String mScanResult;
    static ToughpadDeviceInfo toughpadDeviceInfo = new ToughpadDeviceInfo();
    private GrabbaPassport mGrabbaPassport = GrabbaPassport.getInstance();
    private Grabba mGrabba = Grabba.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Start();
    }

    @Override
    public boolean Init(IDeviceListener ideviceListener) {
        mScanResult = null;
        mCallbackListener = ideviceListener;
        toughpadDeviceInfo = new ToughpadDeviceInfo();
        Start();
        //webLayerCallBackEvent = "5000";
        return false;
    }

    @Override
    public boolean Start() {
        toughpadDeviceInfo.setDeviceState(true);
        initiateThoughpadAPI();
        setBarcodeResultListener(new BarcodeResultListener() {
            @Override
            public void getBarcodeResult(String barcodeNumber) {
                barResultListener.getBarcodeResult(barcodeNumber);
            }
        });
        return true;
    }

    @Override
    public boolean onResult(IDeviceResponse deviceResponsedata) {
        Log.i(TAG, "onResult: ");
        /*IntentResult scanResult = IntentIntegrator.parseActivityResult(deviceResponsedata.getRequestCode(), deviceResponsedata.getResponseCode(), (Intent) deviceResponsedata.getDeviceResponseData());
        if (scanResult != null) {
            mScanResult = "\"" + scanResult.getContents() + "\"";
            //innowiDeviceInfo.setDeviceState(false);
            mResultHandler.postDelayed(mupdateUI, 0);
        }*/
        return false;
    }

    @Override
    public int handleAction(String action, String payload, IDeviceListener callbackListener) {
        mCallbackListener = callbackListener;
//		if(payload !=null && payload.trim().length() > 0)
//		{
//			webLayerCallBackEvent = payload;
//		}
        Log.i(TAG, "handleAction: action  " + action);
        Log.i(TAG, "handleAction: payload  " + payload);

        /*webLayerCallBackEvent = "5000";
        if (action.equals(AndroidDeviceLayerConstant.DL_SCAN_BARCODE)) {
            Start();
        }*/
        return 0;
    }

    @Override
    public IDeviceInfo GetDeviceInfo() {
        return toughpadDeviceInfo;
    }

    @Override
    public Object GetDeviceConfig(Object input) {
        return null;
    }

    @Override
    public void acquireResources() {
        // TODO Auto-generated method stub
        initiateThoughpadAPI();
    }

    @Override
    public void releaseResources() {
        // TODO Auto-generated method stub
        stopScanner();
        clearBarcodeListener();
    }

    @Override
    public boolean Stop() {
        toughpadDeviceInfo.setDeviceState(false);
        stopScanner();
        clearBarcodeListener();
        return false;
    }

    private void stopScanner() {
        try {
            selectedReader.pressSoftwareTrigger(false);
        } catch (BarcodeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean CleanUp() {
        toughpadDeviceInfo.setDeviceState(false);
        clearBarcodeListener();
        stopScanner();
        return false;
    }

    @Override
    public boolean Write(String strWriteData) {
        return false;
    }


    public void setBarcodeResultListener(BarcodeResultListener barResultListener) {
        this.barResultListener = barResultListener;

    }

    public void clearBarcodeListener() {
        if (selectedReader != null)
            selectedReader.clearBarcodeListener();
        ToughpadApi.destroy();
    }

    public void initiateGrabbAPI(){
        mGrabba.acquireGrabba();
        mGrabba.addConnectionListener(this);
    }



    // initiating thoughpad API..calls OnAPIConnected method
    public void initiateThoughpadAPI() {
        try {
            if (selectedReader != null && selectedReader.isEnabled())
                selectedReader.disable();
        } catch (BarcodeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ToughpadApi.isAlreadyInitialized())
            ToughpadApi.destroy();

        ToughpadApi.initialize(MainActivity.this, this);
    }

    private Runnable mupdateUI = new Runnable() {
        @Override
        public void run() {
            if (webLayerCallBackEvent == null || webLayerCallBackEvent.trim().length() == 0) {
                webLayerCallBackEvent = "85";
            }
            String scanResult = populateJSONString(mScanResult);
            Log.i(TAG, "RESULT:" + scanResult);
            mCallbackListener.HandleExternalEvent(scanResult);
            webLayerCallBackEvent = null;

        }
    };

    private String populateJSONString(String result) {
        String jsonString = "{\"eventId\":\"" + webLayerCallBackEvent + "\",\"data\":\"" + result + "\"}";
        Log.i(TAG, "jsonString:" + jsonString);
        return jsonString;
    }

    //Called by barcode reader when ever scanning performed with the device...
    @Override
    public void onRead(BarcodeReader arg0, final BarcodeData result) {
        // Read barcode data
        //barResultListener.getBarcodeResult(result.getTextData());
        if (result != null) {
            byte[] barcodeResult = result.getBinaryData();
            String res = "\"" + barcodeResult + "\"";
            mScanResult = result.getTextData();
            mResultHandler.postDelayed(mupdateUI, 0);

            stopScanner();
            //clearBarcodeListener();
        } else {
            mScanResult = null;
        }
    }

    // 1st method called by thoughtpad API after initialization...
    //Initiate barcode reader ...
    @Override
    public void onApiConnected(int arg0) {
        initiateBarcodeReader();
    }

    //Last method called by Thoughpad API Interface..
    @Override
    public void onApiDisconnected() {
        if (selectedReader != null)
            selectedReader.clearBarcodeListener();
    }

    private void displayAlertDialog(String title, String message, String pstvbtnText) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        // set title
        alertDialogBuilder.setTitle(title);
        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(pstvbtnText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //Get available barcode readers and enable first barcode reader to perform scanning..
    public void initiateBarcodeReader() {
        readers = BarcodeReaderManager.getBarcodeReaders();
        List<String> readerNames = new ArrayList<String>();
        for (BarcodeReader reader : readers) {
            readerNames.add(reader.getDeviceName());
        }
        if (readers.size() == 0) {
            displayAlertDialog("Reader Availability !", "Barcode Reader is not available. Please have a barcode reader in device.", "OK");
        } else {
            selectedReader = readers.get(0);
            try {
                initScanner();
            } catch (BarcodeException e) {
                e.printStackTrace();
            }
        }
    }


    //Enable the scanner..
    public void initScanner() throws BarcodeException {
        if (selectedReader.isEnabled())
            selectedReader.disable();
        EnableReaderTask task = new EnableReaderTask();
        task.execute(selectedReader);
    }

    @Override
    public int HandleExternalEvent(String event) {
        Log.i(TAG, "HandleExternalEvent: "+event);
        return 0;
    }

    @Override
    public int HandleError(String event) {
        Log.i(TAG, "HandleExternalEvent: "+event);

        return 0;
    }

    @Override
    public void passportReadEvent(String s) {
        Log.i(TAG, "passportReadEvent: "+s);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        Log.i(TAG, "onPointerCaptureChanged: "+hasCapture);
    }

    @Override
    public void grabbaConnectedEvent() {
        Log.i(TAG, "grabbaConnectedEvent: ");
        mGrabbaPassport.addEventListener(this);
    }

    @Override
    public void grabbaDisconnectedEvent() {
        Log.i(TAG, "grabbaDisconnectedEvent: ");
        mGrabbaPassport.removeEventListener(this);
    }

    //Enable the scanner in separate thread...
    private class EnableReaderTask extends AsyncTask<BarcodeReader, Void, Boolean> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setIndeterminate(true);
            //dialog.setMessage(mContext.getString(R.string.dlg_enabling_bcr));
            //dialog.show();
        }

        @Override
        protected Boolean doInBackground(BarcodeReader... params) {
            try {
                // params[0].enable(10000);
                params[0].addBarcodeListener(MainActivity.this);
                return true;
            } catch (Exception e) {
                return false;
            }

//			try {
//				params[0].enable(10000);
//				params[0].addBarcodeListener(MainActivity.this);
//				return true;
//			} catch (BarcodeException ex) {
//				return false;
//			} catch (TimeoutException ex) {
//				return false;
//			}
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();
            if (result) {

                if (selectedReader.isHardwareTriggerAvailable()) {
                    try {
                        selectedReader.setHardwareTriggerEnabled(result);
                    } catch (BarcodeException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
