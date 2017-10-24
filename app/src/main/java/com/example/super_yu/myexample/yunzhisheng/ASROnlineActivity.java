package com.example.super_yu.myexample.yunzhisheng;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.super_yu.myexample.R;
import com.iwant.tt.super_yunzhisheng.Config;
import com.unisound.client.SpeechConstants;
import com.unisound.client.SpeechSynthesizer;
import com.unisound.client.SpeechSynthesizerListener;
import com.unisound.client.SpeechUnderstander;
import com.unisound.client.SpeechUnderstanderListener;

/**
 * 云知声识别实例程序
 * 
 * @author
 * 
 */

public class ASROnlineActivity extends Activity implements OnClickListener {

	/**
	 * 当前识别状态
	 */
	enum AsrStatus {
		idle, recording, recognizing
	}

	private ProgressBar mVolume;
	private EditText mRecognizerResultText, mNluResultText;
	private Button mRecognizerButton;
	private View mStatusView;
	private View mStatusLayout;
	private ImageView mLogoImageView;
	private TextView mStatusTextView;
	private Dialog mLanguageDialog;
	private Button mSampleButton;
	private Dialog mSampleDialog;
	private Button mDomainButton;
	private Dialog mDomainDialog;
	private static int currentDomain = 0;
	private static String arrayDomain[] = new String[] { "general", "poi", "song", "movietv", "medical" };
	private static String arrayDomainChina[] = new String[] { "通用识别  ", "地名识别  ", "歌名识别  ", "影视名识别  ", "医药领域识别  " };
	private static String arraySampleStr[] = new String[] { "RATE_AUTO  ", "RATE_16K  ", "RATE_8K  " };
	private static String arrayLanguageStr[] = new String[] { SpeechConstants.LANGUAGE_MANDARIN,
		SpeechConstants.LANGUAGE_ENGLISH, SpeechConstants.LANGUAGE_CANTONESE };
	private static int arraySample[] = new int[] { SpeechConstants.ASR_SAMPLING_RATE_BANDWIDTH_AUTO,
		SpeechConstants.ASR_SAMPLING_RATE_16K, SpeechConstants.ASR_SAMPLING_RATE_8K };
	private static int currentSample = 0;
	private static int currentLanguage = 0;

	private AsrStatus statue = AsrStatus.idle;
	private SpeechUnderstander mUnderstander;
	@SuppressWarnings("unused")
	private String mRecognizerText = "";
	private SpeechSynthesizer mTTSPlayer;
	private StringBuffer mAsrResultBuffer;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_online_asr);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.status_bar_main);

		mVolume = (ProgressBar) findViewById(R.id.volume_progressbar);
		mRecognizerResultText = (EditText) findViewById(R.id.recognizer_result_et);
		mNluResultText = (EditText) findViewById(R.id.nlu_result_et);
		mDomainButton = (Button) findViewById(R.id.domain_button);
		mDomainButton.setOnClickListener(this);
		mDomainButton.setText(arrayDomainChina[0]);
		mSampleButton = (Button) findViewById(R.id.sample_button);
		mStatusView = findViewById(R.id.status_panel);
		mStatusTextView = (TextView) findViewById(R.id.status_show_textview);
		mStatusLayout = findViewById(R.id.offline_asr_status_layout);
		mLogoImageView = (ImageView) findViewById(R.id.logo_imageview);
		mSampleButton.setOnClickListener(this);
		mSampleButton.setText(arraySampleStr[0]);

		mRecognizerButton = (Button) findViewById(R.id.recognizer_btn);
		mAsrResultBuffer = new StringBuffer();

		initData();

		// 初始化对象
		initRecognizer();
	}

	/**
	 * 初始化
	 */
	private void initRecognizer() {

		// 创建语音理解对象，appKey和 secret通过 http://dev.hivoice.cn/ 网站申请
		mUnderstander = new SpeechUnderstander(this, Config.appKey, Config.secret);
		// 开启可变结果
		mUnderstander.setOption(SpeechConstants.ASR_OPT_TEMP_RESULT_ENABLE, true);

		// 创建语音合成对象
		mTTSPlayer = new SpeechSynthesizer(this, Config.appKey, Config.secret);
		mTTSPlayer.setOption(SpeechConstants.TTS_SERVICE_MODE, SpeechConstants.TTS_SERVICE_MODE_NET);
		// 设置语音合成回调监听
		mTTSPlayer.setTTSListener(new SpeechSynthesizerListener() {

			@Override
			public void onEvent(int type) {
				switch (type) {
					case SpeechConstants.TTS_EVENT_INIT:
						// 初始化成功回调
						break;
					case SpeechConstants.TTS_EVENT_SYNTHESIZER_START:
						// 开始合成回调
						break;
					case SpeechConstants.TTS_EVENT_SYNTHESIZER_END:
						// 合成结束回调
						break;
					case SpeechConstants.TTS_EVENT_BUFFER_BEGIN:
						// 开始缓存回调
						break;
					case SpeechConstants.TTS_EVENT_BUFFER_READY:
						// 缓存完毕回调
						break;
					case SpeechConstants.TTS_EVENT_PLAYING_START:
						// 开始播放回调
						break;
					case SpeechConstants.TTS_EVENT_PLAYING_END:
						// 播放完成回调
						break;
					case SpeechConstants.TTS_EVENT_PAUSE:
						// 暂停回调
						break;
					case SpeechConstants.TTS_EVENT_RESUME:
						// 恢复回调
						break;
					case SpeechConstants.TTS_EVENT_STOP:
						// 停止回调
						break;
					case SpeechConstants.TTS_EVENT_RELEASE:
						// 释放资源回调
						break;
					default:
						break;
				}

			}

			@Override
			public void onError(int type, String errorMSG) {
				// 语音合成错误回调
				hitErrorMsg(errorMSG);
				
			}
		});
		mTTSPlayer.init("");

		// 保存录音数据
		// recognizer.setRecordingDataEnable(true);
		mUnderstander.setListener(new SpeechUnderstanderListener() {
			@Override
			public void onResult(int type, String jsonResult) {
				switch (type) {
				case SpeechConstants.ASR_RESULT_NET:
					// 在线识别结果，通常onResult接口多次返回结果，保留识别结果组成完整的识别内容。
					log_v("onRecognizerResult");
					if (jsonResult.contains("net_asr")
							&& jsonResult.contains("net_nlu")) {
						try {
							mNluResultText.setText(jsonResult);
							JSONObject json = new JSONObject(jsonResult);
							JSONArray jsonArray = json.getJSONArray("net_asr");
							JSONObject jsonObject = jsonArray.getJSONObject(0);
							String status = jsonObject.getString("result_type");
							log_v("jsonObject = " + jsonObject.toString());

							if (status.equals("full")) {
								log_v("full");
								String result = (String) jsonObject
										.get("recognition_result");
								if (jsonResult != null) {
									mTTSPlayer.playText(result.trim());
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						//取出语音识别结果
						asrResultOperate(jsonResult);
					}
					break;
			default:
					break;
				}
			}
			
			@Override
			public void onEvent(int type, int timeMs) {
				switch (type) {
				case SpeechConstants.ASR_EVENT_NET_END:
					log_v("onEnd");

					mRecognizerButton.setEnabled(true);
					statue = AsrStatus.idle;
					mRecognizerButton.setText(R.string.click_say);
					mStatusLayout.setVisibility(View.GONE);
					mRecognizerResultText.requestFocus();
					mRecognizerResultText.setSelection(0);
					mNluResultText.requestFocus();
					mNluResultText.setSelection(0);
					break;
				case SpeechConstants.ASR_EVENT_VOLUMECHANGE:
					// 说话音量实时返回
					int volume = (Integer)mUnderstander.getOption(SpeechConstants.GENERAL_UPDATE_VOLUME);
					mVolume.setProgress(volume);
					break;
				case SpeechConstants.ASR_EVENT_VAD_TIMEOUT:
					// 说话音量实时返回
					log_v("onVADTimeout");
					// 收到用户停止说话事件，停止录音
					stopRecord();
					break;
				case SpeechConstants.ASR_EVENT_RECORDING_STOP:
					// 停止录音，请等待识别结果回调
					log_v("onRecordingStop");
					statue = AsrStatus.recognizing;
					mRecognizerButton.setText(R.string.give_up);
					mStatusTextView.setText(R.string.just_recognizer);
					break;
				case SpeechConstants.ASR_EVENT_SPEECH_DETECTED:
					//用户开始说话
					log_v("onSpeakStart");
					mStatusTextView.setText(R.string.speaking);
					break;
				case SpeechConstants.ASR_EVENT_RECORDING_START:
					//录音设备打开，开始识别，用户可以开始说话
					mStatusTextView.setText(R.string.please_speak);
					mRecognizerButton.setEnabled(true);
					statue = AsrStatus.recording;
					mRecognizerButton.setText(R.string.say_over);
					break;
				default:
					break;
				}
			}
			
			@Override
			public void onError(int type, String errorMSG) {
				if (errorMSG != null) {
					// 显示错误信息
					hitErrorMsg(errorMSG);
				} else {
					if ("".equals(mRecognizerResultText.getText().toString())) {
						mRecognizerResultText.setText(R.string.no_hear_sound);
					}
				}
			}
		});
		mUnderstander.init("");
	}

	/**
	 * 初始化按钮
	 */
	private void initData() {

		// 识别领域
		mDomainDialog = new Dialog(this, R.style.dialog);
		mDomainDialog.setContentView(R.layout.domain_list_item);
		mDomainDialog.findViewById(R.id.medical_text).setOnClickListener(this);
		mDomainDialog.findViewById(R.id.general_text).setOnClickListener(this);
		mDomainDialog.findViewById(R.id.movietv_text).setOnClickListener(this);
		mDomainDialog.findViewById(R.id.poi_text).setOnClickListener(this);
		mDomainDialog.findViewById(R.id.song_text).setOnClickListener(this);
		// 采样率
		mSampleDialog = new Dialog(this, R.style.dialog);
		mSampleDialog.setContentView(R.layout.sample_list_item);
		mSampleDialog.findViewById(R.id.rate_16k_text).setOnClickListener(this);
		mSampleDialog.findViewById(R.id.rate_8k_text).setOnClickListener(this);
		mSampleDialog.findViewById(R.id.rate_auto_text).setOnClickListener(this);

		// 语言
		mLanguageDialog = new Dialog(this, R.style.dialog);
		mLanguageDialog.setContentView(R.layout.language_list_item);
		mLanguageDialog.findViewById(R.id.chinese_text).setOnClickListener(this);
		mLanguageDialog.findViewById(R.id.cantonese_text).setOnClickListener(this);
		mLanguageDialog.findViewById(R.id.english_text).setOnClickListener(this);

		mRecognizerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (statue == AsrStatus.idle) {

					mRecognizerButton.setEnabled(false);
					mAsrResultBuffer.delete(0, mAsrResultBuffer.length());
					mRecognizerResultText.setText("");
					mNluResultText.setText("");
					mStatusView.setVisibility(View.VISIBLE);
					mStatusLayout.setVisibility(View.VISIBLE);
					mLogoImageView.setVisibility(View.GONE);
					// 在收到 onRecognizerStart 回调前，录音设备没有打开，请添加界面等待提示，
					// 录音设备打开前用户说的话不能被识别到，影响识别效果。
					mStatusTextView.setText(R.string.opening_recode_devices);
					// 修改录音采样率
					mUnderstander.setOption(SpeechConstants.ASR_SAMPLING_RATE, arraySample[currentSample]);
					// 修改识别领域
					mUnderstander.setOption(SpeechConstants.ASR_DOMAIN, arrayDomain[currentDomain]);
					// 修改识别语音
					mUnderstander.setOption(SpeechConstants.ASR_LANGUAGE,arrayLanguageStr[currentLanguage]);
					mUnderstander.start();
				} else if (statue == AsrStatus.recording) {
					stopRecord();
				} else if (statue == AsrStatus.recognizing) {
					// 取消识别
					mUnderstander.cancel();

					mRecognizerButton.setText(R.string.click_say);
					statue = AsrStatus.idle;
				}
			}
		});
	}

	/**
	 * 打印日志信息
	 * 
	 * @param msg
	 */
	private void log_v(String msg) {
		Log.v("demo", msg);
	}

	@SuppressWarnings("unused")
	private void log_e(String msg) {
		Log.e("demo", msg);
	}

	private void hitErrorMsg(String msg) {
		Toast.makeText(ASROnlineActivity.this, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * 停止录音
	 */
	public void stopRecord() {
		mStatusTextView.setText(R.string.just_recognizer);
		mUnderstander.stop();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.domain_button:
				mDomainDialog.show();
				break;
			case R.id.sample_button:
				mSampleDialog.show();
				break;
			case R.id.general_text:
				currentDomain = 0;
				setDomain(currentDomain);
				break;

			case R.id.poi_text:
				currentDomain = 1;
				setDomain(currentDomain);
				break;

			case R.id.song_text:
				currentDomain = 2;
				setDomain(currentDomain);
				break;

			case R.id.movietv_text:
				currentDomain = 3;
				setDomain(currentDomain);
				break;

			case R.id.medical_text:
				currentDomain = 4;
				setDomain(currentDomain);
				break;
			case R.id.rate_auto_text:
				currentSample = 0;
				setSample(currentSample);
				break;

			case R.id.rate_16k_text:
				currentSample = 1;
				setSample(currentSample);
				break;

			case R.id.rate_8k_text:
				currentSample = 2;
				setSample(currentSample);
				break;

			default:
				break;
		}

	}

	private void setSample(int index) {
		mSampleButton.setText(arraySampleStr[index]);
		mSampleDialog.dismiss();
	}

	private void setDomain(int index) {
		mDomainButton.setText(arrayDomainChina[index]);
		mDomainDialog.dismiss();
	}


	@Override
	protected void onStop() {
		super.onStop();
		if (mUnderstander != null) {
			mUnderstander.stop();
		}
		// 关闭语音合成引擎
		if (mTTSPlayer != null) {
			mTTSPlayer.stop();
		}
	}

	private void asrResultOperate (String jsonResult) {
		JSONObject asrJson;
		try {
			asrJson = new JSONObject(jsonResult);
			JSONArray asrJsonArray = asrJson.getJSONArray("net_asr");
			JSONObject asrJsonObject = asrJsonArray.getJSONObject(0);
			String asrJsonStatus = asrJsonObject.getString("result_type");
			mRecognizerResultText.setText("");
			if (asrJsonStatus.equals("change")) {
				mRecognizerResultText.append(mAsrResultBuffer.toString());
				mRecognizerResultText.append(asrJsonObject.getString("recognition_result"));
			} else {
				mAsrResultBuffer.append(asrJsonObject.getString("recognition_result"));
				mRecognizerResultText.append(mAsrResultBuffer.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
