package com.example.tomer.sokoclient;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tomer.sokoclient.R;

public class MainActivity extends Activity {

    TextView textResponse;
    EditText editTextAddress, editTextPort;
    Button buttonClear,buttonUp,buttonDown,buttonLeft, buttonRight ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextAddress = (EditText)findViewById(R.id.address);
        editTextPort = (EditText)findViewById(R.id.port);
        //buttonConnect = (Button)findViewById(R.id.connect);
        buttonClear = (Button)findViewById(R.id.clear);

        buttonDown=(Button)findViewById(R.id.button_down);
        buttonUp=(Button)findViewById(R.id.button_up);
        buttonLeft=(Button)findViewById(R.id.button_left);
        buttonRight=(Button)findViewById(R.id.button_right);

        textResponse = (TextView)findViewById(R.id.response);

        buttonUp.setOnClickListener(buttonUpOnClickListener);
        buttonDown.setOnClickListener(buttonDownOnClickListener);
        buttonRight.setOnClickListener(buttonRightOnClickListener);
        buttonLeft.setOnClickListener(buttonLeftOnClickListener);

        buttonClear.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                textResponse.setText("");
                editTextPort.setText("");
            }});
    }

    OnClickListener buttonUpOnClickListener =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    MyClientTask myClientTask = new MyClientTask(
                            editTextAddress.getText().toString(),
                            Integer.parseInt(editTextPort.getText().toString()),"move up");
                    myClientTask.execute();
                }};
    OnClickListener buttonDownOnClickListener =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    MyClientTask myClientTask = new MyClientTask(
                            editTextAddress.getText().toString(),
                            Integer.parseInt(editTextPort.getText().toString()),"move down");
                    myClientTask.execute();
                }};

    OnClickListener buttonLeftOnClickListener =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    MyClientTask myClientTask = new MyClientTask(
                            editTextAddress.getText().toString(),
                            Integer.parseInt(editTextPort.getText().toString()),"move left");
                    myClientTask.execute();
                }};
    OnClickListener buttonRightOnClickListener =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    MyClientTask myClientTask = new MyClientTask(
                            editTextAddress.getText().toString(),
                            Integer.parseInt(editTextPort.getText().toString()),"move right");
                    myClientTask.execute();
                }};

    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;
        String response = "";
        String command="";
        MyClientTask(String addr, int port, String command){
            dstAddress = addr;
            dstPort = port;
            this.command=command;
        }
        String userInput[];
        @Override
        protected Void doInBackground(Void... arg0) {
            boolean toStop = false;
            Socket socket = null;

            try {
                socket = new Socket(dstAddress, dstPort);

                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())));

                String line = command;
                out.println(line);
                out.println("exit");
                out.close();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            }finally{

                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textResponse.setText(response);
            super.onPostExecute(result);
        }
    }

}
