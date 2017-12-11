package com.example.super_yu.myexample.iflytek;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.super_yu.myexample.R;

import java.util.Locale;

public class SpeechTTS2Activity extends AppCompatActivity {

    private Button btn_to_speak;
    private TextToSpeech mSpeech;
    private EditText et_input;
    private static final String TAG_TTS = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_tts2);
        init();
    }

    private <T> T f(int resId) {
        return (T) findViewById(resId);
    }

    private void init() {
        btn_to_speak = f(R.id.btn_to_speak);
        et_input = f(R.id.et_input);
        speechInit();
        btn_to_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et_input.getText().toString();
                if (TextUtils.isEmpty(text)) text = "请输入要测试的内容";
                playTTS(text);
            }
        });
    }

    /**
     * 初始化TextToSpeech，在onCreate中调用
     */
    private void speechInit() {
        if (mSpeech != null) {
            mSpeech.stop();
            mSpeech.shutdown();
            mSpeech = null;
        }
        // 创建TTS对象
        mSpeech = new TextToSpeech(SpeechTTS2Activity.this, new TTSListener());
    }

    /**
     * 将文本用TTS播放
     *
     * @param str 播放的文本内容
     */
    private void playTTS(String str) {
        if (mSpeech == null) mSpeech = new TextToSpeech(this, new TTSListener());
        mSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, null);
        Log.i(TAG_TTS, "播放语音为：" + str);
    }

    private final class TTSListener implements TextToSpeech.OnInitListener {
        @Override
        public void onInit(int status) {
            Log.e(TAG_TTS, "初始化结果：" + (status == TextToSpeech.SUCCESS));
            int result = mSpeech.setLanguage(Locale.CHINESE);
            //如果返回值为-2，说明不支持这种语言
            Log.e(TAG_TTS, "是否支持该语言：" + (result != TextToSpeech.LANG_NOT_SUPPORTED));
        }
    }

    @Override
    protected void onDestroy() {
        if (mSpeech != null) {
            mSpeech.stop();
            mSpeech.shutdown();
            mSpeech = null;
        }
        super.onDestroy();
    }
}
