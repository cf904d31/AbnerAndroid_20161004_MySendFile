package iii.org.tw.mysendfile;

import android.app.ProgressDialog;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private File sdroot;
    private ProgressDialog pDialog;
    private UIHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sdroot = Environment.getExternalStorageDirectory();

        pDialog = new ProgressDialog(this);
        pDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        pDialog.setTitle("下載中請稍後...");

        handler = new UIHandler();

    }

    public void addFile(View v) {
        File newFile = new File(sdroot.getAbsolutePath() + "/Abner.txt");
        try {
            FileOutputStream fout = new FileOutputStream(newFile);
            fout.write("我是祝哥!!".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this,"Write OK", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFile(View v) {
        new Thread() {
            @Override
            public void run() {
                try {
                    MultipartUtility mu = new MultipartUtility("http://www.brad.tw/iii2003/upload.php","UTF-8");
                    mu.addFilePart("upload", new File(sdroot.getAbsolutePath() + "/Abner.txt"));
                    mu.finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void getData(View v) {
        pDialog.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    MultipartUtility mu = new MultipartUtility("http://data.coa.gov.tw/Service/OpenData/EzgoAttractions.aspx","UTF-8");
                    List<String> ret = mu.finish();
                    for (String line : ret){
                        Log.v("brad", line.length() + ":" + line);
                    }
                    handler.sendEmptyMessage(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(0);
                }
            }
        }.start();
    }

    public void camera(View v) {

    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pDialog.dismiss();
        }
    }
}
