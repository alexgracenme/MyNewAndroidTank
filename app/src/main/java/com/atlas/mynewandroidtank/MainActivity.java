package com.atlas.mynewandroidtank;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import java.util.Locale;
import java.util.ArrayList;
import android.speech.RecognizerIntent;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.ArrayAdapter;
import android.hardware.SensorManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";


    //private Button mRightUp;
    private Button bnFoward; // mRightUp 대체
    private Button bnBackward;  // mLeftUp 대체
    //private Button mLeftUp;

    //private Button mTurnRight;
    private Button bnRightCCW; // mTurnRight
    //private Button mTurnLeft;
    private Button bnLeftCW;  // mTurnLeft
    private Button bnStop;    // mStop

    private Button bnLeftCCW;
    private Button bnRightCW;
    private Button bnRotateCW;
    private Button bnRotateCCW;

    private TextView mTitle;
    private TextView mTextViewBT;
    int gyroX;
    int gyroY;
    int gyroZ;




    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_NEW_ITEM1 = 3;
    private static final int REQUEST_VOICE = 4;


    private Boolean bacc_sensor = false;

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothChatService mChatService = null;

    private SensorManager sensorManager = null;
    private Sensor orientationSensor = null;
    private Sensor accelerometerSensor = null;
    private Sensor proximitySensor = null;

    private int m_nPreTouchPosX = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            } else if(event.sensor.getType() == Sensor.TYPE_ORIENTATION) {

            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @SuppressLint("ResourceType")
    private void setupChat() {

        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.id.textView);


        bnFoward = (Button) findViewById(R.id.bnFoward);
        bnFoward.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    //bright_up = true;
                    sendMessage("d");
                if (event.getAction() == MotionEvent.ACTION_UP)
                    //bright_up = false;
                    sendMessage("z");

                return false;
            }
        });


        bnBackward = (Button) findViewById(R.id.bnBackward);
        bnBackward.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    //bleft_up = true;
                    sendMessage("m");
                if (event.getAction() == MotionEvent.ACTION_UP)
                    //bleft_up = false;
                    sendMessage("z");

                return false;
            }
        });

        bnRightCCW = (Button) findViewById(R.id.bnRightCCW);
        bnRightCCW.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    //bleft_up = true;
                    sendMessage("c");
                if (event.getAction() == MotionEvent.ACTION_UP)
                    //bleft_up = false;
                    sendMessage("z");

                return false;
            }
        });

        bnRightCW = (Button) findViewById(R.id.bnRightCW);
        bnRightCW.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    //bleft_up = true;
                    sendMessage("k");
                if (event.getAction() == MotionEvent.ACTION_UP)
                    //bleft_up = false;
                    sendMessage("z");

                return false;
            }
        });



        bnLeftCW = (Button) findViewById(R.id.bnLeftCW);
        bnLeftCW.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    //bleft_up = true;
                    sendMessage("b");
                if (event.getAction() == MotionEvent.ACTION_UP)
                    //bleft_up = false;
                    sendMessage("z");

                return false;
            }
        });

        bnLeftCCW = (Button) findViewById(R.id.bnLeftCCW);
        bnLeftCCW.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    //bleft_up = true;
                    sendMessage("y");
                if (event.getAction() == MotionEvent.ACTION_UP)
                    //bleft_up = false;
                    sendMessage("z");

                return false;
            }
        });


        bnStop  = (Button) findViewById(R.id.bnStop);
        bnStop.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    //bleft_up = true;
                    sendMessage("z");
                if (event.getAction() == MotionEvent.ACTION_UP)
                    //bleft_up = false;
                    sendMessage("z");

                return false;
            }
        });


        bnRotateCW  = (Button) findViewById(R.id.bnRotateCW);
        bnRotateCW.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    //bleft_up = true;
                    sendMessage("v");
                if (event.getAction() == MotionEvent.ACTION_UP)
                    //bleft_up = false;
                    sendMessage("z");

                return false;
            }
        });

        bnRotateCCW  = (Button) findViewById(R.id.bnRotateCCW);
        bnRotateCCW.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    //bleft_up = true;
                    sendMessage("p");
                if (event.getAction() == MotionEvent.ACTION_UP)
                    //bleft_up = false;
                    sendMessage("z");

                return false;
            }
        });






        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:

                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            mTitle.setText(R.string.title_connected_to);
                            mTitle.append(mConnectedDeviceName);
                            mConversationArrayAdapter.clear();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            mTitle.setText(R.string.title_connecting);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            mTitle.setText(R.string.title_not_connected);
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    //mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public synchronized void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        if(accelerometerSensor != null || orientationSensor != null)
            sensorManager.unregisterListener(this);

        super.onStop();
    }

    @Override
    public void onDestroy() {
        if(accelerometerSensor != null || orientationSensor != null)
            sensorManager.unregisterListener(this);



        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
    }

    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    mChatService.connect(device);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case REQUEST_VOICE:
                if (resultCode == Activity.RESULT_OK)  {
                    ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String strTemp = "P:";
                    for (int i = 0; i < results.size(); i ++)
                    {
                        strTemp += results.get(i) + ";";
                    }
                    sendMessage(strTemp);

                }

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }

        if(accelerometerSensor != null)
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        if(orientationSensor != null)
            sensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_GAME);
        if(proximitySensor != null)
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_GAME );

    }

    @Override
    public synchronized void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */


        mTitle = (TextView) findViewById(R.id.textView);
        mTitle.setText(R.string.app_name);
        //mTitle = (TextView) findViewById(R.id.title_right_text);

        mTextViewBT = (TextView) findViewById(R.id.textViewBT);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */

        TextView mTextView = (TextView) findViewById(R.id.textView);
        TextView mTextViewBT = (TextView) findViewById(R.id.textViewBT);
        switch (item.getItemId()) {
            case R.id.action_settings:
                /*
                if (mChatService != null)
                {
                    if (mChatService.getState() == BluetoothChatService.STATE_CONNECTED)
                    {
                        String strData = "action_setting menu selected..\n";
                        sendMessage(strData);
                    }
                }
                return true;
                */
                return true;
            case R.id.scanning:
                Intent serverIntent = new Intent (MainActivity.this, DeviceListActivity.class);// use activity context
                //Intent serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                mTextViewBT.setText("scanning...");
                return true;
            case R.id.scan:
                // Launch the DeviceListActivity to see devices and do scan

                Intent serverIntent2 = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent2, REQUEST_CONNECT_DEVICE);
                mTextViewBT.setText("scan .. and selected..");
                // mTextViewBluetooth.setText("scan finished..");
                return true;
            case R.id.discoverable:
                // Ensure this device is discoverable by others
                ensureDiscoverable();
                return true;
            default:

                // mTextViewBluetooth.setText("No Listed Menu Item selected ..");
                return true;
        }


        //return super.onOptionsItemSelected(item);
    }

    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            //mOutEditText.setText(mOutStringBuffer);
        }
    }

    public void voice()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 40);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        startActivityForResult(intent, REQUEST_VOICE);
    }
}
