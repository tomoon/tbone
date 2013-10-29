package com.tomoon.earset.demo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class TestBluetooth extends Activity {
	IntentFilter intentFilter;
	private BluetoothAdapter adapter;
	BluetoothDevice bluetoothDevice;
	
	public String tag = "TOMOON_BT";
	private AudioManager mAudioManager;
	private ComponentName mKeyeventResponder;
	private MediaPlayer mMediaPlayer;
	public static TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_bluetooth);
		mTextView = (TextView) findViewById(R.id.text_view);
		String text;
		adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter != null) {
			text = "本机拥有蓝牙设备";
			Log.d(tag, text);
			// 调用isEnabled()方法判断当前蓝牙设备是否可用
			if (!adapter.isEnabled()) {
				// 如果蓝牙设备不可用的话,创建一个intent对象,该对象用于启动一个Activity,提示用户启动蓝牙适配器
				Intent intent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivity(intent);
			}else {
				text = "蓝牙设备可用";
				Log.d(tag, text);
			}
		} else {
			text = "没有蓝牙设备";
			Log.d(tag, text);
		}
		
		mTextView.setText(text);
		// 独占音频，避免干扰其它程序
		mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);  
		mKeyeventResponder = new ComponentName(getPackageName(),BluetoothKeyeventReceiver.class.getName());  
        mAudioManager.registerMediaButtonEventReceiver(mKeyeventResponder); 
        
        // 播放一段空白音频，按键为播放暂停，上下键为上一曲，下一曲, 如果不播放，上下按键为音量大小
        int MP3_TO_PLAY = R.raw.silent;
		mMediaPlayer = MediaPlayer.create (this, MP3_TO_PLAY);	 //设置音频源
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置流类型
        mMediaPlayer.setLooping(true);	 //设置是否循环播放
        mMediaPlayer.start();	 //开始播放
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mMediaPlayer.stop();
		mAudioManager.unregisterMediaButtonEventReceiver(mKeyeventResponder);  
	}
	

}
