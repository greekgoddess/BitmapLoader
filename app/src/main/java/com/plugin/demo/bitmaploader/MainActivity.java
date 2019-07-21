package com.plugin.demo.bitmaploader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ding.learn.imageloader.*;
import com.ding.learn.imageloader.ImageLoader;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image_view);
        ImageLoaderConfig config = new ImageLoaderConfig();
        config.enableDiskCache = true;
        ImageLoader.with().init(MainActivity.this, config);

        final String url = "http://imgsrc.baidu.com/forum/pic/item/fdfaaf51f3deb48ff2869338fe1f3a292cf5786c.jpg";
//        ImageLoader.getInstance().getBitmap(url, new ImageLoader.BitmapLoadCallback() {
//            @Override
//            public void onFail() {
//
//            }
//
//            @Override
//            public void onSuccess(Bitmap bitmap) {
//                if (bitmap != null) {
//                    imageView.setImageBitmap(bitmap);
//                }
//            }
//        });

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SecondActivity.class);
                startActivity(intent);
//                System.out.println("jajajaj");
//                String path = "file:/mnt/sdcard/imageloader/test.png";
//                String tes = "https://himg.bdimg.com/sys/portrait/item/07f97fd1.jpg?time=8396";
//                ImageLoader.with()
//                        .load(tes)
//                        .setPlaceHolderResId(R.mipmap.ic_launcher)
////                        .resize(imageView.getMeasuredWidth(), imageView.getMeasuredHeight())
////                        .setCenterCrop(true)
////                        .setCenterInside(true)
////                        .setRotationDegrees(90)
//                        .setErrorResId(R.drawable.error)
//                        .into(imageView, new CallBack() {
//                    @Override
//                    public void onSuccess(Bitmap bitmap) {
//
//                    }
//
//                    @Override
//                    public void onFailed() {
//
//                    }
//                });

//                ImageLoader.with()
//                        .load(url)
//                        .resize(250, 150)
//                        .loadBitmap(new CallBack() {
//                            @Override
//                            public void onSuccess(Bitmap bitmap) {
//                                imageView.setImageBitmap(bitmap);
//                            }
//
//                            @Override
//                            public void onFailed() {
//
//                            }
//                        });
            }
        });


    }

}
