package com.example.super_yu.myexample.yunzhisheng;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.super_yu.myexample.R;
import com.iwant.tt.super_yunzhisheng.Config;
import com.unisound.client.SpeechConstants;
import com.unisound.client.SpeechSynthesizer;
import com.unisound.client.SpeechSynthesizerListener;

public class TTSOfflineActivity extends Activity {

	private static boolean TTS_PLAY_FLAGE = false;

	private EditText mTTSText;
	private TextView mTextViewTip;
	private TextView mTextViewStatus;
	private Button mTTSPlayBtn;
	private SpeechSynthesizer mTTSPlayer;
	private final String mFrontendModel= "/sdcard/unisound/tts/frontend_model";
	private final String mBackendModel = "/sdcard/unisound/tts/backend_lzl";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offline_tts);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.status_bar_main);

		mTTSText = (EditText) findViewById(R.id.textViewResult);
		mTextViewStatus = (TextView) findViewById(R.id.textViewStatus);
		mTextViewTip = (TextView) findViewById(R.id.textViewTip);
		mTTSPlayBtn = (Button) findViewById(R.id.recognizer_btn);
		mTTSPlayBtn.setEnabled(false);
		mTTSPlayBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TTSPlay();
			}
		});
		// 初始化本地TTS播报
		initTts();
	}

	/**
	 * 初始化本地离线TTS
	 */
	private void initTts() {

		// 初始化语音合成对象
		mTTSPlayer = new SpeechSynthesizer(this, Config.appKey, Config.secret);
		// 设置本地合成
		mTTSPlayer.setOption(SpeechConstants.TTS_SERVICE_MODE, SpeechConstants.TTS_SERVICE_MODE_LOCAL);
		File _FrontendModelFile = new File(mFrontendModel);
		if (!_FrontendModelFile.exists()) {
			toastMessage("文件：" + mFrontendModel + "不存在，请将assets下相关文件拷贝到SD卡指定目录！");
		}
		File _BackendModelFile = new File(mBackendModel);
		if (!_BackendModelFile.exists()) {
			toastMessage("文件：" + mBackendModel + "不存在，请将assets下相关文件拷贝到SD卡指定目录！");
		}
		// 设置前端模型
		mTTSPlayer.setOption(SpeechConstants.TTS_KEY_FRONTEND_MODEL_PATH, mFrontendModel);
		// 设置后端模型
		mTTSPlayer.setOption(SpeechConstants.TTS_KEY_BACKEND_MODEL_PATH, mBackendModel);
		// 设置回调监听
		mTTSPlayer.setTTSListener(new SpeechSynthesizerListener() {

			@Override
			public void onEvent(int type) {
				switch (type) {
					case SpeechConstants.TTS_EVENT_INIT:
						// 初始化成功回调
						log_i("onInitFinish");
						mTTSPlayBtn.setEnabled(true);
						break;
					case SpeechConstants.TTS_EVENT_SYNTHESIZER_START:
						// 开始合成回调
						log_i("beginSynthesizer");
						break;
					case SpeechConstants.TTS_EVENT_SYNTHESIZER_END:
						// 合成结束回调
						log_i("endSynthesizer");
						break;
					case SpeechConstants.TTS_EVENT_BUFFER_BEGIN:
						// 开始缓存回调
						log_i("beginBuffer");
						break;
					case SpeechConstants.TTS_EVENT_BUFFER_READY:
						// 缓存完毕回调
						log_i("bufferReady");
						break;
					case SpeechConstants.TTS_EVENT_PLAYING_START:
						// 开始播放回调
						log_i("onPlayBegin");
						break;
					case SpeechConstants.TTS_EVENT_PLAYING_END:
						// 播放完成回调
						log_i("onPlayEnd");
						setTTSButtonReady();
						break;
					case SpeechConstants.TTS_EVENT_PAUSE:
						// 暂停回调
						log_i("pause");
						break;
					case SpeechConstants.TTS_EVENT_RESUME:
						// 恢复回调
						log_i("resume");
						break;
					case SpeechConstants.TTS_EVENT_STOP:
						// 停止回调
						log_i("stop");
						break;
					case SpeechConstants.TTS_EVENT_RELEASE:
						// 释放资源回调
						log_i("release");
						break;
					default:
						break;
				}

			}

			@Override
			public void onError(int type, String errorMSG) {
				// 语音合成错误回调
				log_i("onError");
				toastMessage(errorMSG);
				setTTSButtonReady();
			}
		});
		// 初始化合成引擎
		mTTSPlayer.init("");
	}

	private void TTSPlay() {
		if (!TTS_PLAY_FLAGE) {
			mTTSPlayer.playText(mTTSText.getText().toString());
			setTTSButtonStop();
		} else {
			mTTSPlayer.stop();
			setTTSButtonReady();
		}

	}

	private void setTTSButtonStop() {
		TTS_PLAY_FLAGE = true;
		mTTSPlayBtn.setText(R.string.stop_tts);
	}

	private void setTTSButtonReady() {
		mTTSPlayBtn.setText(R.string.start_tts);
		TTS_PLAY_FLAGE = false;
	}

	protected void setTipText(String tip) {

		mTextViewTip.setText(tip);
	}

	protected void setStatusText(String status) {

		mTextViewStatus.setText(getString(R.string.lable_status) + "(" + status + ")");
	}

	@Override
	public void onPause() {
		super.onPause();
		// 主动停止识别
		if (mTTSPlayer != null) {
			mTTSPlayer.stop();
		}
	}

	private void log_i(String log) {
		Log.i("demo", log);
	}

	@Override
	protected void onDestroy() {
		// 主动释放离线引擎
		if (mTTSPlayer != null) {
			mTTSPlayer.release(SpeechConstants.TTS_RELEASE_ENGINE, null);
		}
		super.onDestroy();
	}

	private void toastMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
