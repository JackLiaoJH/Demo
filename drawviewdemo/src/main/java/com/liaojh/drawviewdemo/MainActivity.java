package com.liaojh.drawviewdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.liaojh.drawviewdemo.widget.ProgressWheel;
import com.liaojh.drawviewdemo.widget.SpringProgressView;

public class MainActivity extends AppCompatActivity {

    boolean running;
    ProgressWheel pw_two;
    ProgressWheel pw_three;
    ProgressWheel pw_four;
    //ProgressWheel pw_five;
    int progress = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_wheel_activity);

//        setContentView(new LinearGradientView(this));
//        setContentView(new RadialGradientView(this));
//        setContentView(new WaterRipplesView(this));
//        setContentView(new SweepGradientView(this));


//        SpringProgressView view = (SpringProgressView) findViewById(R.id.view);
//        view.setMaxCount(100);
//        view.setCurrentCount(30);
//
//        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainActivity.this.startActivity(new Intent(MainActivity.this, SecondActivity.class));
//            }
//        });


        pw_two = (ProgressWheel) findViewById(R.id.progressBarTwo);
        pw_three = (ProgressWheel) findViewById(R.id.progressBarThree);
        pw_four = (ProgressWheel) findViewById(R.id.progressBarFour);
        //pw_five = (ProgressWheel) findViewById(R.id.progressBarFive);

        ShapeDrawable bg = new ShapeDrawable(new RectShape());
        int[] pixels = new int[] { 0xFF2E9121, 0xFF2E9121, 0xFF2E9121,
                0xFF2E9121, 0xFF2E9121, 0xFF2E9121, 0xFFFFFFFF, 0xFFFFFFFF};
        Bitmap bm = Bitmap.createBitmap(pixels, 8, 1, Bitmap.Config.ARGB_8888);
        Shader shader = new BitmapShader(bm,
                Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        pw_three.setRimShader(shader);

        pw_three.spin();
        pw_four.spin();

        final Runnable r = new Runnable() {
            public void run() {
                running = true;
                while(progress<361) {
                    pw_two.incrementProgress();
                    progress++;
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pw_two.setText(progress+"%");
                        }
                    });

                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                running = false;
            }
        };

        Button spin = (Button) findViewById(R.id.btn_spin);
        spin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!running) {
                    if(pw_two.isSpinning()) {
                        pw_two.stopSpinning();
                    }
                    pw_two.resetCount();
                    pw_two.setText("Loading...");
                    pw_two.spin();
                }
            }
        });

        Button increment = (Button) findViewById(R.id.btn_increment);
        increment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!running) {
                    progress = 0;
                    pw_two.resetCount();
                    Thread s = new Thread(r);
                    s.start();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        progress = 361;
        pw_two.stopSpinning();
        pw_two.resetCount();
        pw_two.setText("Click\none of the\nbuttons");
    }
}
