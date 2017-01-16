package preetam.jecrc.kishaanbazaar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddressClass extends AppCompatActivity {
    private EditText add_name, add_number, add_city, add_addrone, add_addrtwo, add_state,add_postal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_class);
        add_name = (EditText) findViewById(R.id.add_name);
        add_number = (EditText) findViewById(R.id.add_number);
        add_city = (EditText) findViewById(R.id.add_city);
        add_addrone = (EditText) findViewById(R.id.add_addrone);
        add_addrtwo = (EditText) findViewById(R.id.add_addrtwo);
        add_state = (EditText) findViewById(R.id.add_state);
        add_postal= (EditText) findViewById(R.id.add_postal);
        Button next = (Button) findViewById(R.id.addr_button);
        assert next != null;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = add_name.getText().toString(), number = add_number.getText().toString(), city = add_city.getText().toString(),
                        addrone = add_addrone.getText().toString(), addrtwo = add_addrtwo.getText().toString(), state = add_state.getText().toString()
                        ,postal=add_postal.getText().toString();


                if (name.length() != 0 && number.length() != 0 && city.length() != 0 && addrone.length() != 0 &&
                        addrtwo.length() != 0 && state.length() != 0) {
                    DataBasehelper dataBasehelper = new DataBasehelper(AddressClass.this);
                    dataBasehelper.open();
                    dataBasehelper.deleteAll();
                    dataBasehelper.createEntry(name, addrone, addrtwo, state, city,postal, number);
                    dataBasehelper.close();
                    Intent intent = new Intent(AddressClass.this, ProceedClass.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Fill all details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}