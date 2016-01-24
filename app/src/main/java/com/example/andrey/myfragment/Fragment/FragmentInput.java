package com.example.andrey.myfragment.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.andrey.myfragment.MainActivity;
import com.example.andrey.myfragment.R;

import java.util.Date;


public class FragmentInput extends android.app.Fragment {
    SharedPreferences userName;
    EditText editUserName;
    EditText editText;

    public interface onSaveText {
        void saveText (String text, long time);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onResume() {
       setUserHint();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_input, container, false);
        editText = (EditText) v.findViewById(R.id.editText);
        Button saveButton = (Button) v.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final onSaveText onSaveText = (onSaveText) getActivity();
                if (editText.getText().length()>0) {
                   onSaveText.saveText(String.valueOf(editText.getText()), new Date().getTime());
                   editText.setText("");

                }else{
                    Toast.makeText(MainActivity.context,"Введите текст",Toast.LENGTH_LONG).show();
                }

            }
        });


        editUserName = (EditText) v.findViewById(R.id.userName);
        Button saveUserName = (Button) v.findViewById(R.id.saveUserName);
        setUserHint();

        saveUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor ed = userName.edit();
                ed.putString(MainActivity.SAVED_USER_NAME, editUserName.getText().toString());
                ed.commit();
                setUserHint();
                editUserName.setText("");
                Toast.makeText(MainActivity.context, "Save User Name", Toast.LENGTH_LONG);

            }
        });




        return v;
    }


    private void setUserHint(){
        userName = getActivity().getPreferences(MainActivity.context.MODE_PRIVATE);
        editUserName.setHint(userName.getString(MainActivity.SAVED_USER_NAME, "User1"));
    }


}