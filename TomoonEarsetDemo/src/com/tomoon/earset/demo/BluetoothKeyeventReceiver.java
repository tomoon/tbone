package com.tomoon.earset.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

public class BluetoothKeyeventReceiver extends BroadcastReceiver {
	String tag = "BluetoothKeyeventReceiver";
	private static String text = "start";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(tag, "--------->onReceive" + intent.getAction());
		text = "\n" + text;
		if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
			Log.d(tag, "---------------> ACTION_MEDIA_BUTTON");
			KeyEvent key = (KeyEvent) intent
					.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
			int keycode = key.getKeyCode();
			Log.d(tag, "----> keycode = " + keycode);
			if (keycode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
				Log.d(tag, "--------------->KEYCODE_MEDIA_PAUSE");
			} else if (keycode == KeyEvent.KEYCODE_MEDIA_PREVIOUS) {
				Log.d(tag, "--------------->KEYCODE_MEDIA_PREVIOUS");
			} else if (keycode == KeyEvent.KEYCODE_MEDIA_NEXT) {
				Log.d(tag, "--------------->KEYCODE_MEDIA_NEXT");
			} else if (keycode == KeyEvent.KEYCODE_MEDIA_PLAY) {
				Log.d(tag, "--------------->KEYCODE_MEDIA_PLAY");
			}
			text = intent.getAction() + "; keycode = " + keycode + text;
		}
		if ("android.media.VOLUME_CHANGED_ACTION".equals(intent.getAction())) {
			Log.d(tag, "---------------> VOLUME_CHANGED_ACTION");
			int volume = (Integer) intent.getExtras().get(
					"android.media.EXTRA_VOLUME_STREAM_VALUE");
			Log.d(tag, "----------->I got volume changed = " + volume);
			text = intent.getAction() + "; volume = " + volume + text;
		}
		TestBluetooth.mTextView.setText(text);
	}
}
