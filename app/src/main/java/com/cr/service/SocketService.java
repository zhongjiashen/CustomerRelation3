package com.cr.service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.cr.activity.LoginActivity;
import com.cr.tools.ShareUserInfo;

public class SocketService extends Service {
    public static Socket  socket;
    private String        ip;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            ip = intent.getExtras().getString("ip");
            ip = ip.replace("http://", "");
            Handler handler = new Handler() {
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Intent intent = new Intent("LoginSuccess");
                    sendBroadcast(intent);
                    switch (msg.what) {
                        case 0:
                            Log.v("dddd", "成功");
//                            Toast.makeText(SocketService.this, "连接服务器成功！", Toast.LENGTH_SHORT)
//                                .show();
                            Intent intent2 = new Intent(SocketService.this, LoginActivity.class);
                            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent2);
                            break;
                        case 1:
                            Log.v("dddd", "失败");
                            Toast.makeText(SocketService.this, "连接数超过最大连接数，请与管理员联系",
                                Toast.LENGTH_SHORT).show();
                            closeSocket();
                            break;
                        case 2:
                            Log.v("dddd", "连接服务器失败");
                            //                  closeSocket();
                            Toast.makeText(SocketService.this, "连接服务器失败，请与管理员联系！",
                                Toast.LENGTH_SHORT).show();
                            SocketService.this.stopSelf();
                            break;
                        case 3:
                            Log.v("dddd", "连接服务器失败3");
                            //                  closeSocket();
//                            Toast.makeText(SocketService.this, "连接服务器失败，请与管理员联系！",
//                                Toast.LENGTH_SHORT).show();
//                            Intent intent3=new Intent("LoginMax");
//                            sendBroadcast(intent3);
                            ShareUserInfo.setKey(SocketService.this.getApplicationContext(), "networklock", "true");
                            break;
                    }
                };
            };
            ShareUserInfo.setKey(SocketService.this.getApplicationContext(), "networklock", "false");
            SocThread socThread = new SocThread(handler, ip, this, socket);
            socThread.start();

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {

        return null;
    }

    public static boolean sendMsg(String msg) {
        try {
            if(!SocThread.client.isConnected()){
                return false;
            }
            try{
                socket.sendUrgentData(0xFF);
              }catch(Exception ex){
                    return false;
              }
            PrintWriter out = new PrintWriter(new OutputStreamWriter(
                SocThread.client.getOutputStream(), "gb2312"));
            out.println(msg);
            out.flush();
            return true;
            //            return in.readLine();
        } catch (IOException e) {
            Log.v("dddd", "出错误了");
            e.printStackTrace();
            return false;
        }
        //        return "";
    }

    public static void closeSocket() {
        try {
            SocThread.client.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
