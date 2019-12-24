package com.example.alarmclock.Alarm.CancelAlarmScreen;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.alarmclock.Alarm.Other.AlarmCountDownTimer;
import com.example.alarmclock.Alarm.Other.AlarmVibration;
import com.example.alarmclock.R;

import java.util.Random;

public class SolveMathDialog extends DialogFragment {
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    private ImageButton btnXoa, btnXacNhan;
    private  EditText editKetQua;
    private TextView txtBaiToan;
    private Random random = new Random();
    private  int x=0,y=0,kq;
    private boolean isCancel=false;
    private Context context;
    private TurnOffAlarm listener;
    public SolveMathDialog(final Context context, int mathDifficult) {
        this.context=context;
        if(mathDifficult==0)
        {
            x=random.nextInt(50);
            y=random.nextInt(50);
        }else if(mathDifficult==1)
        {
            x=50+random.nextInt(100);
            y=50+random.nextInt(100);
        }else if(mathDifficult==2)
        {
            x=300+random.nextInt(700);
            y=600+random.nextInt(700);
        }
        kq=x+y;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlarmCountDownTimer.getInstance().PauseTimer();
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.alarm_cancel_screen_math_dialog);
        editKetQua= dialog.findViewById(R.id.editKetQua);
        txtBaiToan= dialog.findViewById(R.id.txtBaiToan);
        btnXacNhan= dialog.findViewById(R.id.btnXacNhanKetQua);
        btnXoa= dialog.findViewById(R.id.btnXoa);
        btn1= dialog.findViewById(R.id.btn1);
        btn2= dialog.findViewById(R.id.btn2);
        btn3= dialog.findViewById(R.id.btn3);
        btn4= dialog.findViewById(R.id.btn4);
        btn5= dialog.findViewById(R.id.btn5);
        btn6= dialog.findViewById(R.id.btn6);
        btn7= dialog.findViewById(R.id.btn7);
        btn8= dialog.findViewById(R.id.btn8);
        btn9= dialog.findViewById(R.id.btn9);
        btn0= dialog.findViewById(R.id.btn0);

        txtBaiToan.setText(x+" + "+y+" = ???");
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (kq == Integer.parseInt(editKetQua.getText().toString())) {
                        isCancel = true;
                        dismiss();
                        listener.onTurnOffAlarm();
                    } else {
                        AlarmVibration.getInstance().ShortVibrate(context);
                    }
                }catch (Exception ex)
                {
                    AlarmVibration.getInstance().ShortVibrate(context);
                }
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a=editKetQua.getText().toString();
                if(!a.equals(""))
                    a=a.substring(0,a.length()-1);
                editKetQua.setText(a);
            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editKetQua.setText(editKetQua.getText()+"0");
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editKetQua.setText(editKetQua.getText()+"1");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editKetQua.setText(editKetQua.getText()+"2");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editKetQua.setText(editKetQua.getText()+"3");
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editKetQua.setText(editKetQua.getText()+"4");
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editKetQua.setText(editKetQua.getText()+"5");
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editKetQua.setText(editKetQua.getText()+"6");
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editKetQua.setText(editKetQua.getText()+"7");
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editKetQua.setText(editKetQua.getText()+"8");
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editKetQua.setText(editKetQua.getText()+"9");
            }
        });
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(!isCancel)
            AlarmCountDownTimer.getInstance().ResumeTimer(context);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener=(TurnOffAlarm) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }
}
