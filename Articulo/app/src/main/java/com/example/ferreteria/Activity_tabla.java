package com.example.ferreteria;

/**
 * Created by pimpo on 16/02/16.
 */

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;
        import android.widget.Toast;

public class Activity_tabla extends Activity {

    TableLayout TL;
    TableRow tr1, tr2, tr3;
    TextView t1, t2, dummy;
    EditText et1, et2;
    Button login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TL = new TableLayout(Activity_tabla.this);

        tr1 = new TableRow(Activity_tabla.this);
        tr2 = new TableRow(Activity_tabla.this);
        tr3 = new TableRow(Activity_tabla.this);

        t1 = new TextView(Activity_tabla.this);
        t2 = new TextView(Activity_tabla.this);
        dummy = new TextView(Activity_tabla.this);

        et1 = new EditText(Activity_tabla.this);
        et2 = new EditText(Activity_tabla.this);

        login = new Button(Activity_tabla.this);

        t1.setText("Username");
        t1.setTextSize(15);
        t1.setPadding(20, 20, 20, 20);
        tr1.addView(t1);

        et1.setHint("Enter Username");
        et1.setTextSize(15);
        et1.setPadding(20, 20, 20, 20);
        tr1.addView(et1);

        t2.setText("Password");
        t2.setTextSize(15);
        t2.setPadding(20, 20, 20, 20);
        tr2.addView(t2);

        et2.setHint("Enter Password");
        et2.setTextSize(15);
        et2.setPadding(20, 20, 20, 20);
        et2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        tr2.addView(et2);

        tr3.addView(dummy);

        login.setText("Username");
        login.setTextSize(15);
        login.setPadding(20, 20, 20, 20);
        tr3.addView(login);

        TL.addView(tr1);
        TL.addView(tr2);
        TL.addView(tr3);

        setContentView(TL);

        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                if(et1.getText().toString().equals("pimpo") && et2.getText().toString().equals("yo"))
                {
                    Toast.makeText(getBaseContext(), "Login Successfully !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Login Failure \n\n Try Again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}