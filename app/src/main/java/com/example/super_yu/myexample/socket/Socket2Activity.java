package com.example.super_yu.myexample.socket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.super_yu.myexample.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Socket2Activity extends AppCompatActivity implements Runnable{

    private TextView tv_msg = null;
    private EditText ed_msg = null;
    private Button btn_send = null;
    private static final String HOST = "192.168.1.115";//服务器地址
    private static final int PORT = 8888;//连接端口号
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    //接收线程发送过来信息，并用TextView追加显示
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_msg.append((CharSequence) msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket2);

        tv_msg = (TextView) findViewById(R.id.txt_1);
        ed_msg = (EditText) findViewById(R.id.et_talk);
        btn_send = (Button) findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                String msg = ed_msg.getText().toString();
                if (socket.isConnected()) {//如果服务器连接
                    if (!socket.isOutputShutdown()) {//如果输出流没有断开
                        out.println(msg);//点击按钮发送消息
                        ed_msg.setText("");//清空编辑框
                        Log.d("whsgzcy","send success");
                    }
                }
                Log.d("whsgzcy","onClick()");
            }
        });
        //启动线程，连接服务器，并用死循环守候，接收服务器发送过来的数据
        new Thread(this).start();
    }


    /**
     * 连接服务器
     */
    private void connection() {
        try {
            socket = new Socket(HOST, PORT);//连接服务器
            in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));//接收消息的流对象
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())), true);//发送消息的流对象
            Log.d("whsgzcy","connection()");
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.d("whsgzcy","connection() IOException");

        }
    }



    /**
     * 读取服务器发来的信息，并通过Handler发给UI线程
     */
    public void run() {
        connection();// 连接到服务器
        try {
            while (true) {//死循环守护，监控服务器发来的消息
                if (!socket.isClosed()) {//如果服务器没有关闭
                    Log.d("whsgzcy","服务器没有关闭");
                    if (socket.isConnected()) {//连接正常
                        Log.d("whsgzcy","连接正常");
                        if (!socket.isInputShutdown()) {//如果输入流没有断开
                            Log.d("whsgzcy","输入流没有断开");
                            String getLine;
                            if ((getLine = in.readLine()) != null) {//读取接收的信息
                                getLine += "\n";
                                Message message = new Message();
                                message.obj = getLine;
                                mHandler.sendMessage(message);//通知UI更新
                                Log.d("whsgzcy","读取接收消息");
                            } else {

                            }
                        }
                    }
                }else{
                    Log.d("whsgzcy","服务器关闭");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("whsgzcy","异常");
        }
    }
}
