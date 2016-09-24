package com.java_women.cocorobopet.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Http送信クラス
 */
public class HttpPostTask extends AsyncTask<Void, Void, byte[]>{

    final static private String BOUNDARY = "MyBoundaryString";
    //Activityへ送信結果通知リスナー
    private HttpPostListener mListener;
    //送信先URL
    private String mURL;
    //送信テキストパラメータ用
    private HashMap<String, String> mTexts;
    //送信画像パラメータ用
    private HashMap<String, byte[]> mImages;

    private ProgressDialog progressDialog;

    private Context mContext;

    public HttpPostTask(Context context)
    {
        super();

        mContext  = context;
        //送信先URL
        mURL = null;
        //Activityへ送信結果通知リスナー
        mListener = null;
        //送信テキストパラメータ用
        mTexts = new HashMap<String, String>();
        //送信画像パラメータ用
        mImages = new HashMap<String, byte[]>();
    }

    /**
     * リスナーをセットする。
     * @param listener
     */
    public void setListener(HttpPostListener listener)
    {
        mListener = listener;
    }

    /**
     * 送信先URLを追加する。
     * @param url
     */
    public void addURL(String url)
    {
        mURL = url;
    }


    /**
     * 送信するテキストを追加する。
     * @param key
     * @param text
     */
    public void addText(String key, String text)
    {
        mTexts.put(key, text);
    }

    /**
     * 送信する画像を追加する。
     * @param key
     * @param data
     */
    public void addImage(String key, byte[] data)
    {
        mImages.put(key, data);
    }

    /**
     * 送信を行う。
     * @return レスポンスデータ
     */
    private byte[] send(byte[] data)
    {
        if (data == null)
            return null;

        byte[] result = null;
        HttpURLConnection connection = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;

        try {
            URL url = new URL(mURL);
            connection = (HttpURLConnection)url.openConnection();
            //マルチパートデータで送信するよ！！
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // 接続
            connection.connect();

            // 送信
            OutputStream os = connection.getOutputStream();
            os.write(data);
            os.close();

            // レスポンスを取得する
            byte[] buf = new byte[10240];
            int size;
            is = connection.getInputStream();
            while ((size = is.read(buf)) != -1)
            {
                baos.write(buf, 0, size);
            }
            result = baos.toByteArray();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                connection.disconnect();
                baos.close();
            } catch (Exception e) {}
        }

        return result;
    }

    /**
     * POSTするデータを作成する。
     * @return
     */
    private byte[] makePostData()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            // テキスト部分の設定
            for (Entry<String, String> entry : mTexts.entrySet())
            {
                String key = entry.getKey();
                String text = entry.getValue();

                baos.write(("--" + BOUNDARY + "\r\n").getBytes());
                baos.write(("Content-Disposition: form-data;").getBytes());
                baos.write(("name=\"" + key + "\"\r\n\r\n").getBytes());
                baos.write((text + "\r\n").getBytes());
            }

            // 画像の設定
            int count = 1;
            for (Entry<String, byte[]> entry: mImages.entrySet())
            {
                String key = entry.getKey();
                byte[] data = entry.getValue();
                String name = "upload_file" + count++;

                baos.write(("--" + BOUNDARY + "\r\n").getBytes());
                baos.write(("Content-Disposition: form-data;").getBytes());
                baos.write(("name=\"" + name + "\";").getBytes());
                baos.write(("filename=\"" + key + "\"\r\n").getBytes());
                baos.write(("Content-Type: image/jpeg\r\n\r\n").getBytes());
                baos.write(data);
                baos.write(("\r\n").getBytes());
            }

            // 最後にバウンダリを付ける
            baos.write(("--" + BOUNDARY + "--\r\n").getBytes());

            return baos.toByteArray();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                baos.close();
            } catch (Exception e) {}
        }
    }

    /**
     * タスク処理
     */
    @Override
    protected byte[] doInBackground(Void... params) {

        // POSTするデータを作成する。
        byte[] data = makePostData();
        //送信処理
        byte[] result = send(data);
        //送信処理のレスポンスを返却
        return result;
    }

    @Override
    protected void onPreExecute() {
        // プロセスダイアログ表示
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("サーバーに送信中...");
        progressDialog.show();
    }

    /**
     * 送信処理結果をリスナーを介してActivityに通知
     */
    @Override
    protected void onPostExecute(byte[] result){

        if ( progressDialog != null && progressDialog.isShowing() )
        {
            //プログレスダイアログを消去
            progressDialog.dismiss();
        }

        if (mListener != null)
        {
            if (result != null)
            {
                //成功
                mListener.postCompletion(result);
            }
            else
            {
                //失敗
                mListener.postFailure();
            }
        }
    }
}