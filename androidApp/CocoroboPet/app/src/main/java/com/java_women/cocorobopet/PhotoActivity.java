package com.java_women.cocorobopet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.java_women.cocorobopet.network.HttpPostListener;
import com.java_women.cocorobopet.network.HttpPostTask;

import java.io.ByteArrayOutputStream;


/**
 * 飼い主の写真を撮影送信
 */
public class PhotoActivity extends AppCompatActivity implements HttpPostListener {

    final static private String TAG = "HttpPost";
    private final static int RESULT_CAMERA = 1001;
    public Context context = null;
    private ImageView imageView;
    private EditText ownerNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        //画像表示・保持用のImageView
        imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setImageResource(R.drawable.javajo);
        //飼い主名のテキスト
        ownerNameText = (EditText) findViewById(R.id.ownerNameTxt);
        //Activityコンテキスト
        context = PhotoActivity.this;

        //さつえいボタン。カメラを起動する。
        findViewById(R.id.camera).setOnClickListener(view ->
            //カメラ起動
            //戻り値をstartActivityForResultへ
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), RESULT_CAMERA)

        );

        //送信ボタン。撮影後の画像を送信する
        findViewById(R.id.photoSend).setOnClickListener(view -> {

                String ownerName = ownerNameText.getText().toString();     // 入力文字の取得
                 //かいぬし名未入力check
                if(ownerName.length() == 0){
                    Toast.makeText(context, "かいぬしの名前を入れてね！", Toast.LENGTH_LONG).show();
                    //未入力なら抜ける
                    return;
                }

                Log.i(TAG, "post start!");

                //Http送信クラス
                HttpPostTask task = new HttpPostTask(context);
                //URL
               task.addURL("http://javajo-api.azurewebsites.net/cocorobo-pet/api/registerImage");

              // task.addURL("http://10.0.2.2:8080/api/registerImage");

            //Http送信クラスに飼い主名を設定
                task.addText("userId", ownerName);

                //ImageView画像をbmp→byteに変換
                Bitmap bmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                //JPEG, クオリティー100としてbyte配列にデータを格納
                byte[] bytes=  byteArrayOutputStream.toByteArray();
                //Http送信クラスに画像を設定
                task.addImage("faceImage", bytes);
                // リスナーをセットする
                task.setListener(PhotoActivity.this);

                //実行（非同期処理）
                task.execute();

        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //カメラを起動しコールバックされたとき
        if (requestCode == RESULT_CAMERA) {
            //データが存在しなければfalse
            if (data != null && data.hasExtra("data")) {
                //画像を取得する
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
           }
        }
    }

    @Override
    public void postCompletion(byte[] response) {
        Log.i(TAG, "post completion!");
        Log.i(TAG, new String(response));
        Toast.makeText(context, "送信に成功しました", Toast.LENGTH_LONG).show();
    }

    @Override
    public void postFailure() {
        Log.i(TAG, "post failure!");
        Toast.makeText(context, "送信に失敗しました", Toast.LENGTH_LONG).show();
    }
}
