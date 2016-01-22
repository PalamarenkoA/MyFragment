package com.example.andrey.myfragment.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrey.myfragment.MainActivity;
import com.example.andrey.myfragment.R;

import java.util.Date;


public class FragmentInput extends android.app.Fragment {
    public interface onSaveText {
        public void saveText (String Text, long time);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_input, container, false);
        final EditText editText = (EditText) v.findViewById(R.id.editText);
        Button saveButton = (Button) v.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final onSaveText onSaveText = (onSaveText) getActivity();
                if (editText.getText().length()>0) {
                    onSaveText.saveText(String.valueOf(editText.getText()), new Date().getTime());
                }else{
                    Toast.makeText(MainActivity.context,"Заполните окно!",Toast.LENGTH_LONG).show();
                }

            }
        });


        return v;
    }


}