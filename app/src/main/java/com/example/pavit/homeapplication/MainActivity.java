package com.example.pavit.homeapplication;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.w3c.dom.Text;

import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connect = (Button) findViewById(R.id.button3);
        final EditText IpAddr = (EditText) findViewById(R.id.editText3);
        final TextView status = (TextView) findViewById(R.id.textView3);

        connect.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {

                final AsyncTask<Integer, Void, Void> button_was_clicked = new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            executeV4L2();
                            Log.i(TAG, "Button was clicked");
                            status.setText(IpAddr.getText());
                        } catch (
                                Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);

            }
        });
    }

    public void executeV4L2(){
        String user ="dietpi";
        String password = "welcome123";
        String host ="192.168.0.100";
        int port =22;
        try {
            Log.i(TAG, "execute v4l2");

            JSch jsch = new JSch();
            Session session =jsch.getSession(user,host,port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking","no");
            session.connect();
            ChannelExec channel = (ChannelExec)session.openChannel("exec");
            channel.setCommand("sudo reboot");
            channel.connect();
            channel.disconnect();
            Log.i(TAG, "v4l2 completed");
  /*          Snackbar.make(findViewById(R.id.constraint),
                    "Success!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
  */      }
        catch (JSchException e){
            Log.i(TAG, "v4l2 error"+e.getMessage());
           /* Snackbar.make(findViewById(R.id.constraint),
                    "Check WIFI or Server! Error : "+e.getMessage(),
                    Snackbar.LENGTH_LONG)
                    .setDuration(20000).setAction("Action", null).show();
*/
        }
    }
}
