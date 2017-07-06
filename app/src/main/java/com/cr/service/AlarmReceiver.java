package com.cr.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.widget.Toast;

import com.crcxj.activity.R;

public class AlarmReceiver extends BroadcastReceiver {
    private SoundPool sp;//声明一个SoundPool
    private int music;//定义一个整型用load（）；来设置suondID
    
    public AlarmReceiver() {
		
	}
    @Override
    public void onReceive(final Context arg0, Intent arg1) {
//    	Log.v("dddd", "222"+new Date().toString());
		sp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
		music = sp.load(arg0, R.raw.music, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
		try {
			Thread.sleep(1000);
			sp.play(music, 1, 1, 0, 0, 1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String name="";
		if(arg1.getExtras().getInt("type")==0){
			name="个人日程提醒";
		}else if(arg1.getExtras().getInt("type")==1){
			name="项目提醒";
		}
		Toast.makeText(arg0, name, Toast.LENGTH_SHORT).show();
        return;
    }
}
