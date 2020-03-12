package com.electromech.updclienttest;

import android.os.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpClientThread extends Thread{

    private String dstAddress;
    private int dstPort;
    // private boolean running;
    private MainActivity.UdpClientHandler handler;

    private DatagramSocket socket;
    // private InetAddress address;

    public UdpClientThread(String addr, int port, MainActivity.UdpClientHandler handler) {
        super();
        dstAddress = addr;
        dstPort = port;
        this.handler = handler;
    }
/*
    public void setRunning(boolean running){
        this.running = running;
    }
*/
    private void sendState(String state){
        handler.sendMessage(
                Message.obtain(handler,
                        MainActivity.UdpClientHandler.UPDATE_STATE, state));
    }

    @Override
    public void run() {
    //    DatagramSocket socket;

        sendState("connecting...");

    //    running = true;

        try {
            socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName(dstAddress);

            // send request
            byte[] buf = new byte[256];
            buf[0] = 'H';
            buf[1] = 'E';
            buf[2] = 'L';
            buf[3] = 'L';
            buf[4] = 'O';

            DatagramPacket packet =
                    new DatagramPacket(buf, buf.length, address, dstPort);
            socket.send(packet);

            sendState("connected");

            // get response
            packet = new DatagramPacket(buf, buf.length);


            socket.receive(packet);
            String line = new String(packet.getData(), 0, packet.getLength());

            handler.sendMessage(
                    Message.obtain(handler, MainActivity.UdpClientHandler.UPDATE_MSG, line));

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null){
                socket.close();
                handler.sendEmptyMessage(MainActivity.UdpClientHandler.UPDATE_END);
            }
        }

    }
}