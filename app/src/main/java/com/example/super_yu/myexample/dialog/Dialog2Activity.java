package com.example.super_yu.myexample.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.super_yu.myexample.R;
import com.example.super_yu.myexample.dialog.viewer.BussInessViewStateMachine;
import com.example.super_yu.myexample.dialog.viewer.FourView;
import com.example.super_yu.myexample.dialog.viewer.OneView;
import com.example.super_yu.myexample.dialog.viewer.ThreeView;
import com.example.super_yu.myexample.dialog.viewer.TwoView;
import com.example.superalertdialoglibrary.SweetAlertDialog;
import com.iwant.tt.super_dialog_library.MaterialDialog;


public class Dialog2Activity extends AppCompatActivity {

    private LinearLayout mContainerView;

    MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dialog2);

        mContainerView = (LinearLayout) findViewById(R.id.container);

        Button one = (Button) findViewById(R.id.one);
        Button two = (Button) findViewById(R.id.two);
        Button three = (Button) findViewById(R.id.three);
        Button four = (Button) findViewById(R.id.four);

        BussInessViewStateMachine.getInstance();
        BussInessViewStateMachine.getInstance().setContainerView(mContainerView);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContainerView.removeAllViews();
//                OneView one = new OneView(Dialog2Activity.this);
//                one.test();
//                mContainerView.addView(one);
                BussInessViewStateMachine.getInstance().test1();
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContainerView.removeAllViews();
//                TwoView two = new TwoView(Dialog2Activity.this);
//                two.test();
//                mContainerView.addView(two);
                BussInessViewStateMachine.getInstance().test2();

            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContainerView.removeAllViews();
//                ThreeView three = new ThreeView(Dialog2Activity.this);
//                three.test();
//                mContainerView.addView(three);
                BussInessViewStateMachine.getInstance().test3();

            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContainerView.removeAllViews();
//                FourView four = new FourView(Dialog2Activity.this);
//                four.test();
//                mContainerView.addView(four);
                BussInessViewStateMachine.getInstance().test4();
            }
        });


        Button successBtn = (Button) findViewById(R.id.success);
        successBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(Dialog2Activity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Good job!")
                        .setContentText("You clicked the button!")
                        .show();
            }
        });
//
//        Button wordBtn = (Button) findViewById(R.id.word);
//        wordBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActionMonitorDialog d = ActionMonitorDialog.showDialog(Dialog2Activity.this);
//                d.show();
//            }
//        });
    }

    public void init(View v) {
        mMaterialDialog = new MaterialDialog(this);

        Toast.makeText(getApplicationContext(), "Initializes successfully.",
                Toast.LENGTH_SHORT).show();
    }


    public void show(View v) {
        if (mMaterialDialog != null) {
            mMaterialDialog.setTitle("MaterialDialog")
                    .setMessage(
                            "Hi! This is a MaterialDialog. It's very easy to use, you just new and show() it " +
                                    "then the beautiful AlertDialog will show automatically. It is artistic, conforms to Google Material Design." +
                                    " I hope that you will like it, and enjoy it. ^ ^")
                    //mMaterialDialog.setBackgroundResource(R.drawable.background);
                    .setPositiveButton("OK", new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            mMaterialDialog.dismiss();
                            Toast.makeText(Dialog2Activity.this, "Ok",
                                    Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("CANCEL",
                            new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    mMaterialDialog.dismiss();
                                    Toast.makeText(Dialog2Activity.this,
                                            "Cancel", Toast.LENGTH_LONG)
                                            .show();
                                }
                            })
                    .setCanceledOnTouchOutside(true)
                    // You can change the message anytime.
                    // mMaterialDialog.setTitle("提示");
                    .setOnDismissListener(
                            new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    Toast.makeText(Dialog2Activity.this,
                                            "onDismiss",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                    .show();
            // You can change the message anytime.
            // mMaterialDialog.setMessage("嗨！这是一个 MaterialDialog. 它非常方便使用，你只需将它实例化，这个美观的对话框便会自动地显示出来。它简洁小巧，完全遵照 Google 2014 年发布的 Material Design 风格，希望你能喜欢它！^ ^");
        } else {
            Toast.makeText(getApplicationContext(), "You should init firstly!",
                    Toast.LENGTH_SHORT).show();
        }
    }


    static int i = 0;


    public void setView(View v) {
        switch (v.getId()) {
            case R.id.button_set_view: {
                mMaterialDialog = new MaterialDialog(this);
                View view = LayoutInflater.from(this)
                        .inflate(R.layout.progressbar_item,
                                null);
                mMaterialDialog.setCanceledOnTouchOutside(true);
                mMaterialDialog.setView(view).show();

            }
            break;
            case R.id.button_set_background: {
                mMaterialDialog = new MaterialDialog(this);
                if (mMaterialDialog != null) {
                    if (i % 2 != 0) {
                        mMaterialDialog.setBackgroundResource(
                                R.drawable.background);
                    } else {
                        Resources res = getResources();
                        Bitmap bmp = BitmapFactory.decodeResource(res,
                                R.drawable.background2);
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(
                                getResources(), bmp);
                        mMaterialDialog.setBackground(bitmapDrawable);
                    }
                    mMaterialDialog.setCanceledOnTouchOutside(true).show();
                    i++;
                    Toast.makeText(getApplicationContext(),
                            "Try to click again~", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "You should init firstly!", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            }
            case R.id.button_set_contentView: {
                final ArrayAdapter<String> arrayAdapter
                        = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1);
                for (int j = 0; j < 38; j++) {
                    arrayAdapter.add("This is item " + j);
                }

                ListView listView = new ListView(this);
                listView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                float scale = getResources().getDisplayMetrics().density;
                int dpAsPixels = (int) (8 * scale + 0.5f);
                listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
                listView.setDividerHeight(0);
                listView.setAdapter(arrayAdapter);

                final MaterialDialog alert = new MaterialDialog(this).setTitle(
                        "MaterialDialog").setContentView(listView);

                alert.setPositiveButton("OK", new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        alert.dismiss();
                    }
                });

                alert.show();
                break;
            }
            case R.id.button_set_contentViewById: {
                final MaterialDialog alert = new MaterialDialog(this).setTitle(
                        "MaterialDialog")
                        .setContentView(
                                R.layout.custom_message_content);

                alert.setPositiveButton("OK", new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        alert.dismiss();
                    }
                });
                alert.show();
                break;
            }
            case R.id.button_set_notitile: {
                final MaterialDialog materialDialog = new MaterialDialog(this);
                //materialDialog.setMessage("This is a dialog without title. This is a dialog without title. This is a dialog without title. This is a dialog without title. This is a dialog without title. ")
                materialDialog.setMessage(
                        "This is a dialog without title. This is a dialog without title. This is a dialog without title. " +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                "This is a dialog without title. This is a dialog without title." +
                                " ")
                        .setPositiveButton(android.R.string.yes,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        materialDialog.dismiss();
                                    }
                                });
                materialDialog.show();
            }
        }
    }


    public void buttonPress(View view) {
        // show imm
        InputMethodManager imm = (InputMethodManager) this.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
