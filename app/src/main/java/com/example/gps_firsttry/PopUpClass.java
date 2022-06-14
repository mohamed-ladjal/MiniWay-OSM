package com.example.gps_firsttry;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class PopUpClass {
    private EditText test2;
     private PopupWindow popupWindow;
     private BottomSheetDialog bottomSheetDialog;
     private Button btn1;
     private Context mContext;

    //PopupWindow display method
    PopUpClass(BottomSheetDialog bottomSheetDialog,Button btn1 , Context mContext){
        this.bottomSheetDialog= bottomSheetDialog;
        this.btn1=btn1;
        this.mContext=mContext;
    }

    public void showPopupWindow(final View view) {


        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popupfavorite, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
         popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        test2 = popupView.findViewById(R.id.titleText);
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(test2, InputMethodManager.SHOW_IMPLICIT);

        //Initialize the elements of our window, install the handler


        Button buttonEdit = popupView.findViewById(R.id.messageButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //As an example, display the message
                Toast.makeText(view.getContext(), "Wow, popup action button", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                //bottomSheetDialog.show();
                btn1.setText(test2.getText().toString());

            }
        });



        //Handler for clicking on the inactive zone of the windowt--

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }
    public boolean isShowing(){
        return popupWindow.isShowing();
    }
    public String returntext() {
        return test2.getText().toString();

    }

}