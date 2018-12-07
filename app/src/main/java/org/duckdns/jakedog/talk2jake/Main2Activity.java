package org.duckdns.jakedog.talk2jake;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {

            //start execution of ssh commands
            @Override
            public void onClick(View v){

                // START AsyncTask
                // see: https://stackoverflow.com/questions/44309241/warning-this-asynctask-class-should-be-static-or-leaks-might-occur

                ExampleAsyncTask asyncTask = new ExampleAsyncTask();

                asyncTask.setListener(new ExampleAsyncTask.ExampleAsyncTaskListener() {
                    @Override
                    public void onExampleAsyncTaskFinished(Integer value) {
                        // update UI in Activity here

                    }	// onExampleAsyncTaskFinished()
                });

            }	// onClick(.)
        }); // View.OnClickListener()

    }   // onCreate(.)



    static class ExampleAsyncTask extends AsyncTask<Void, Void, Integer> {
        private ExampleAsyncTaskListener listener;

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                executeSSHcommand();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new Integer(0);

        }	// doInBackground()

        @Override
        protected void onPostExecute(Integer value) {
            super.onPostExecute(value);
            if (listener != null) {
                listener.onExampleAsyncTaskFinished(value);
            }
        }	// onPostExecute()

        public void setListener(ExampleAsyncTaskListener listener) {
            this.listener = listener;
        }	// setListener()

        public interface ExampleAsyncTaskListener {
            void onExampleAsyncTaskFinished(Integer value);
        }	// ExampleAnsyncTaskListener()


        public void executeSSHcommand(){
            String user = "kurtis";
            String password = "xxx";
            String host = "192.168.0.210";
            int port=22;
            try{

                JSch jsch = new JSch();
                Session session = jsch.getSession(user, host, port);
                session.setPassword(password);
                session.setConfig("StrictHostKeyChecking", "no");
                session.setTimeout(10000);
                session.connect();
                ChannelExec channel = (ChannelExec)session.openChannel("exec");
                channel.setCommand("uptime");
                channel.connect();
                System.out.println(channel);
                channel.disconnect();

            }
            catch(JSchException e){
                // show the error in the UI
                e.printStackTrace();
            }

        }   // executeSSHcommand()
    }	// class ExampleAsyncTask

}   // class Main2Activity
