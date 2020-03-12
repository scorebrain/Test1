package com.electromech.networkingstuff;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    TextView bigTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bigTextView = findViewById(R.id.myTextView);
        try {
            Enumeration<NetworkInterface> myNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netIFCounter : Collections.list(myNetworkInterfaces)) {
                displayInterfaceInformation(netIFCounter, bigTextView);
                //displaySubInterfaces(netIFCounter, bigTextView);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    static void displayInterfaceInformation(NetworkInterface netIF, TextView tView) throws SocketException {
        tView.setText(tView.getText() + "\nDisplay name: " + netIF.getDisplayName()
            + "\nName: " + netIF.getName()
            + "\nIs up?  " + netIF.isUp()
            + "\nIs loopback? " + netIF.isLoopback()
            + "\nIs virtual? " + netIF.isVirtual()
            + "\nIs point-to-point? " + netIF.isPointToPoint()
            + "\nSupports multicast? " + netIF.supportsMulticast()
            + "\nMaximum Transfer Unit: " + netIF.getMTU()
            + "\nHardware Address: " + Arrays.toString(netIF.getHardwareAddress()));
        Enumeration<InetAddress> inetAdresses = netIF.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAdresses)) {
            tView.setText(tView.getText() + "\nInetAddress: " + inetAddress);
        }
    }

    static void displaySubInterfaces(NetworkInterface netIF, TextView tView) {
        try {
            Enumeration<NetworkInterface> subNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface subIF : Collections.list(subNetworkInterfaces)) {
                tView.setText(tView.getText() + "\n\tSub Display name: " + netIF.getDisplayName() + "\n\tSub Interface Name: " + netIF.getName());
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }
}
