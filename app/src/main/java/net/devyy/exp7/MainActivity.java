package net.devyy.exp7;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;

    private EditText et_finput;
    private Button bt_write;
    private Button bt_read;
    private TextView tv_show;

    String FILE_NAME="fileDemo.ini";

    public static final String PREFERENCE_NAME="SaveSetting";
    public static final int MODE=Context.MODE_PRIVATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name=(EditText)findViewById(R.id.et_name);
        et_age=(EditText)findViewById(R.id.et_age);
        et_height=(EditText)findViewById(R.id.et_height);

        et_finput=(EditText)findViewById(R.id.et_finput);
        bt_write=(Button)findViewById(R.id.bt_write);
        bt_read=(Button)findViewById(R.id.bt_read);
        tv_show=(TextView)findViewById(R.id.tv_show);

        bt_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream fos = null;
                try{
                    fos=openFileOutput(FILE_NAME,Context.MODE_PRIVATE);
                    String text=et_finput.getText().toString();
                    fos.write(text.getBytes());
                    et_finput.setText("");
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }finally {
                    if(fos!=null){
                        try{
                            fos.flush();
                            fos.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        bt_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_show.setText("");
                FileInputStream fis=null;
                try{
                    fis=openFileInput(FILE_NAME);
                    if(fis.available()==0){
                        return;
                    }
                    byte[] readBytes=new byte[fis.available()];
                    while (fis.read(readBytes)!=-1){

                    }
                    String text=new String(readBytes);
                    tv_show.setText(text);

                }catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart( ) {
        loadSharedPreferences();
        super.onStart();
    }

    @Override
    protected void onStop( ) {
        saveSharedPreferences();
        super.onStop();
    }

    private void loadSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
        String name = sharedPreferences.getString("Name","");
        int age = sharedPreferences.getInt("Age",0);
        float height = sharedPreferences.getFloat("Height", 0);

        et_name.setText(name);
        et_age.setText(String.valueOf(age));
        et_height.setText(String.valueOf(height));
    }

    private void saveSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString("Name",et_name.getText().toString());
        editor.putInt("Age",Integer.parseInt(et_age.getText().toString()));
        editor.putFloat("Height",Float.parseFloat(et_height.getText().toString()));
        editor.commit();
    }
}
