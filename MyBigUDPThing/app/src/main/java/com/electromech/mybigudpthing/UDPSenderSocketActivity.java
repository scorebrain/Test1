package com.electromech.mybigudpthing;

import android.content.Intent;
import android.os.Bundle;
//import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSenderSocketActivity extends AppCompatActivity {

    //private TextView mTextViewReplyFromServer;
    private EditText mEditTextSendMessage;
    private static final String TAG = "UDPSenderSocketActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String stringToSend = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        sendMessage(stringToSend);
    }

    private void sendMessage(final String message) {

        //final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {

            String stringData;

            @Override
            public void run() {

                DatagramSocket ds = null;
                try {
                    ds = new DatagramSocket();
                    // IP Address below is the IP address of that Device where server socket is opened.
                    InetAddress serverAddress = InetAddress.getByName("192.168.2.1");
                    DatagramPacket dp;
                    dp = new DatagramPacket(message.getBytes(), message.length(), serverAddress, 9001);
                    ds.send(dp);

                    for(byte b : message.getBytes()) {

                        Log.i(TAG, String.valueOf(b));
                        Log.i(TAG, message);
                    }

                    byte[] lMsg = new byte[1000];
                    dp = new DatagramPacket(lMsg, lMsg.length);
                    ds.receive(dp);
                    stringData = new String(lMsg, 0, dp.getLength());

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (ds != null) {
                        ds.close();
                    }
                }
                /**
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        String s = mTextViewReplyFromServer.getText().toString();
                        if (stringData.trim().length() != 0)
                            mTextViewReplyFromServer.setText(s + "\nFrom Server : " + stringData);

                    }
                });
                 */
            }
        });

        thread.start();
    }
}
