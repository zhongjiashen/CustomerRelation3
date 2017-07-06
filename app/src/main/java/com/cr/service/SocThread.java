package com.cr.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import com.cr.tools.ShareUserInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

public class SocThread extends Thread {
//	 private String ip = "hengshicrm.eicp.net";
//	private String ip = "192.168.10.3";
    private String ip="";
	private int port = 3010;
	// private String TAG = "socket thread";
	// private int timeout = 10000;

	public static Socket client = null;
	PrintWriter out;
	BufferedReader in;
	public boolean isRun = true;
	Handler inHandler;
	Context ctx;
	SharedPreferences sp;

	public SocThread(Handler handlerin, String ip, Context context,Socket socket) {
		inHandler = handlerin;
		 this.ip=ip;
		inHandler = handlerin;
		ctx = context;
//		this.client=socket;
	}

	/**
	 * 
	 */
	public void conn() {

		try {
//			client = new Socket(ip, port);
			client = new Socket();
			port=Integer.parseInt(ShareUserInfo.getKey(ctx, "socketPort"));
			if(ShareUserInfo.getKey(ctx, "socketPort").equals("")){
				port=3010;//默认3010
			}
			SocketAddress socAddress = new InetSocketAddress(ip, port);       
			client.connect(socAddress, 5000);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			inHandler.sendEmptyMessage(2);
            isRun=false;
			close();
		} catch (IOException e) {
			e.printStackTrace();
			inHandler.sendEmptyMessage(2);
            isRun=false;
            close();
		} catch (Exception e) {
			e.printStackTrace();
			inHandler.sendEmptyMessage(2);
            isRun=false;
            close();
		}
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		conn();
//		final InputStream in;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (isRun) {
					try {
					    if(client.isConnected()){
					        InputStream in = client.getInputStream();
					        BufferedReader bff = new BufferedReader(
					            new InputStreamReader(in));
					        String line;
					        String buffer = null;
					        while ((line = bff.readLine()) != null) {
					            buffer = line + buffer;
					            if (buffer.subSequence(0, 11).equals("####gzr####")) {
					                inHandler.sendEmptyMessage(1);
					            } else if(buffer.subSequence(0, 21).equals("hellogzr123456#server")){
					                inHandler.sendEmptyMessage(0);
					            }
					            Log.v("dddd", buffer);
					        }
							isRun=false;
							close();;
					    }
					} catch (IOException e) {
						e.printStackTrace();
						inHandler.sendEmptyMessage(3);
						isRun=false;
						close();
						Log.v("dddd","出错了");
					}
				}
			}
		}).start();
	}

	/**
	 * 
	 */
	public void close() {
		try {
			if (client != null) {
				if(in!=null)
				in.close();
				if(out!=null)out.close();
				client.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}