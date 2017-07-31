package com.example.super_yu.myexample.websocket.okhttp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.super_yu.myexample.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okio.ByteString;

public class OkHttpWebSocketActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private WsManager wsManager;
    private TextView btn_send, btn_clear, tv_content;
    private Button btn_connect, btn_disconnect;
    private EditText edit_url, edit_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_web_socket);

        btn_send = (TextView) findViewById(R.id.btn_send);
        btn_clear = (TextView) findViewById(R.id.btn_clear);
        tv_content = (TextView) findViewById(R.id.tv_content);
        btn_connect = (Button) findViewById(R.id.btn_connect);
        btn_disconnect = (Button) findViewById(R.id.btn_disconnect);
        edit_url = (EditText) findViewById(R.id.edit_url);
        edit_content = (EditText) findViewById(R.id.edit_content);
        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edit_url.getText().toString();
                if (!TextUtils.isEmpty(url) && url.contains("ws")) {
                    if (wsManager != null) {
                        wsManager.stopConnect();
                        wsManager = null;
                    }
                    wsManager = new WsManager.Builder(getBaseContext())
                            .client(
                                    new OkHttpClient().newBuilder()
                                            .pingInterval(15, TimeUnit.SECONDS)
                                            .retryOnConnectionFailure(true)
                                            .build())
                            .needReconnect(true)
                            .wsUrl(url)
                            .build();
                    wsManager.setWsStatusListener(wsStatusListener);
                    wsManager.startConnect();
                } else {
                    Toast.makeText(getBaseContext(), "请填写需要链接的地址", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wsManager != null) {
                    wsManager.stopConnect();
                    wsManager = null;
                }
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edit_content.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    if (wsManager != null && wsManager.isWsConnected()) {
                        boolean isSend = wsManager.sendMessage(content);
                        if (isSend) {
                            tv_content.append(Spanny.spanText(
                                    "我 " + DateUtils.formatDateTime(getBaseContext(), System.currentTimeMillis(),
                                            DateUtils.FORMAT_SHOW_TIME) + "\n", new ForegroundColorSpan(
                                            ContextCompat.getColor(getBaseContext(), android.R.color.holo_green_light))));
                            tv_content.append(content + "\n\n");
                        } else {
                            tv_content.append(Spanny.spanText("消息发送失败\n", new ForegroundColorSpan(
                                    ContextCompat.getColor(getBaseContext(), android.R.color.holo_red_light))));
                        }
                        showOrHideInputMethod();
                        edit_content.setText("");
                    } else {
                        Toast.makeText(getBaseContext(), "请先连接服务器", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "请填写需要发送的内容", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_content.setText("");
            }
        });
    }

    private WsStatusListener wsStatusListener = new WsStatusListener() {
        @Override
        public void onOpen(Response response) {
            Log.d(TAG, "WsManager-----onOpen");
            tv_content.append(Spanny.spanText("服务器连接成功\n\n", new ForegroundColorSpan(
                    ContextCompat.getColor(getBaseContext(), R.color.colorPrimary))));
        }

        @Override
        public void onMessage(String text) {
            Log.d(TAG, "WsManager-----onMessage");
            tv_content.append(Spanny
                    .spanText("服务器 " + DateUtils.formatDateTime(getBaseContext(), System.currentTimeMillis(),
                            DateUtils.FORMAT_SHOW_TIME) + "\n",
                            new ForegroundColorSpan(
                                    ContextCompat.getColor(getBaseContext(), R.color.colorPrimary))));
            tv_content.append(fromHtmlText(text) + "\n\n");
        }

        @Override
        public void onMessage(ByteString bytes) {
            Log.d(TAG, "WsManager-----onMessage");
        }

        @Override
        public void onReconnect() {
            Log.d(TAG, "WsManager-----onReconnect");
            tv_content.append(Spanny.spanText("服务器重连接中...\n", new ForegroundColorSpan(
                    ContextCompat.getColor(getBaseContext(), android.R.color.holo_red_light))));
        }

        @Override
        public void onClosing(int code, String reason) {
            Log.d(TAG, "WsManager-----onClosing");
            tv_content.append(Spanny.spanText("服务器连接关闭中...\n", new ForegroundColorSpan(
                    ContextCompat.getColor(getBaseContext(), android.R.color.holo_red_light))));
        }

        @Override
        public void onClosed(int code, String reason) {
            Log.d(TAG, "WsManager-----onClosed");
            tv_content.append(Spanny.spanText("服务器连接已关闭\n", new ForegroundColorSpan(
                    ContextCompat.getColor(getBaseContext(), android.R.color.holo_red_light))));
        }

        @Override
        public void onFailure(Throwable t, Response response) {
            Log.d(TAG, "WsManager-----onFailure");
            tv_content.append(Spanny.spanText("服务器连接失败\n", new ForegroundColorSpan(
                    ContextCompat.getColor(getBaseContext(), android.R.color.holo_red_light))));
        }
    };

    @Override
    protected void onDestroy() {
        if (wsManager != null) {
            wsManager.stopConnect();
            wsManager = null;
        }
        super.onDestroy();
    }

    private Spanned fromHtmlText(String s) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(s);
        }
        return result;
    }

    private void showOrHideInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
