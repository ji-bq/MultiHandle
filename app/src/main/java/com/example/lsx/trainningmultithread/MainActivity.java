package com.example.lsx.trainningmultithread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    private ImageView  mImageView;
    private Button mLbutton;
    private Button mTbutton;
    private ProgressBar mProgressBar=null;

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    mProgressBar.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    mProgressBar.setProgress((int)msg.obj);
                    break;
                case 2:
                    mImageView.setImageBitmap((Bitmap) msg.obj);
            }
            //super.handleMessage(msg);
           // mProgressBar.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.mai_image);
        mLbutton = (Button) findViewById(R.id.activity_Lbutton);
        mTbutton = (Button) findViewById(R.id.activity_Tbutton);
        mProgressBar = (ProgressBar) findViewById(R.id.mPregressBar);

        mLbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = 0;
                                mHandler.sendMessage(msg);
                                for (int i = 1;i < 11;i++){
                                    sleep();
                                    Message msg2 = new Message();
                                    msg2.what = 1;
                                    msg2.obj =i * 10;
                                    mHandler.sendMessage(msg2);
                                }
                              Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                                       R.drawable.ic_launcher);
                                Message msgBitMap=mHandler.obtainMessage();
                                msgBitMap.what = 2;
                                msgBitMap.obj = bitmap;
                                mHandler.sendMessage(msgBitMap);
                            }

                           private void sleep(){
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e){
                                    e.printStackTrace();
                                }
                            }

                        }

                ).start();
            }
        });
        mTbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"this is time wait",Toast.LENGTH_SHORT).show();
            }
        });


    }

    class LoadImageTask extends AsyncTask<Void,Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void...params) {

            try {
                sleep(4000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mImageView.setImageBitmap(bitmap);
        }
    }
}
