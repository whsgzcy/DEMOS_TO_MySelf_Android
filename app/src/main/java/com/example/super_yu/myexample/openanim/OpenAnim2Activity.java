package com.example.super_yu.myexample.openanim;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.super_yu.myexample.R;
import com.iwant.tt.super_open_anim_library.NormalDrawStrategy;
import com.iwant.tt.super_open_anim_library.OpeningStartAnimation;

public class OpenAnim2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_anim2);


        Resources resources = getResources();
        Drawable drawable = resources.getDrawable(R.drawable.ic_launcher);

        OpeningStartAnimation openingStartAnimation2 = new OpeningStartAnimation.Builder(this)
                .setDrawStategy(new NormalDrawStrategy())
                .setAppName("朗曼智能")
                .setAppIcon(drawable)
//                .setColorOfAppName(getResources().getColor(R.color.text_black_color))
//                .setAppStatement("智能无处不在")
//                .setColorOfAppStatement(getResources().getColor(R.color.text_black_color))
                .create();
        openingStartAnimation2.show(this, new OpeningStartAnimation.OnAnimationFinishedCallBack() {
            @Override
            public void onAnimationFinished() {
//                startActivity(new Intent(WelcomeActivity.this, LoaderActivity.class));
//                WelcomeActivity.this.finish();
//                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

    }
}
