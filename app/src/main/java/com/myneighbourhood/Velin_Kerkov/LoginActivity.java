package com.myneighbourhood.Velin_Kerkov;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.Address;
import com.myneighbourhood.utils.CustomNotification;
import com.myneighbourhood.utils.Request;
import com.myneighbourhood.utils.User;
import com.myneighbourhood.utils.Utils;

import java.util.Calendar;

public class LoginActivity extends BaseActivity {

    private EditText passwordET;
    private EditText usernameET;
    private RelativeLayout mainLayoutLL;

    @Override
    protected boolean useToolbar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bitmap defaultProfPicture = BitmapFactory.decodeResource(getResources(), R.drawable.ic_account_circle_black_36dp);


        User admin = new User("Bob", "fName", "lName", "pass", "mail@mail.com", "080808", BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_house));
        User vili = new User("vili", "fName", "lName", "pass", "mail@mail.com", "080808", defaultProfPicture);
//        DB.deleteUser(admin);
//        DB.deleteUser(vili);
        User newAdmin = DB.getUser(admin.getUsername(), admin.getPassword());
        User newVili = DB.getUser(vili.getUsername(), vili.getPassword());

        if (newAdmin == null && newVili == null) {
            newAdmin = DB.registerUser(admin, new Address("100 Gibson Street", 55.8734611, -4.2890117));
            newVili = DB.registerUser(vili, new Address("100 Gibson Street", 55.8734611, -4.2890117));

            System.out.println("Admin: " + admin.getId());
            System.out.println("Vili: " + vili.getId());
        }

        System.out.println(newAdmin.toString());
        System.out.println(newVili.toString());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 10);

        Request adminRequest = new Request(newAdmin, "Chess buddy", "I need a chess buddy for tomorrow evening", 1, cal.getTimeInMillis(), 0);
        Request viliRequest = new Request(newVili, "Computer technician", "Hello,\nI am new to this neighbourhood and looking for friends. \nI am great with computers so I would gladly help someone in need for a beer in return.", 1, cal.getTimeInMillis(), 0);

        CustomNotification notification = new CustomNotification(CustomNotification.Type.NEW_REQUEST, null, newAdmin );
        Request adminRequestRes = DB.addRequest(adminRequest, notification);
        if (adminRequestRes == null) {
            // zna4i ima ve4e takav
            System.out.println("adminRequestRes == null");
            adminRequestRes = DB.getRequest(adminRequest.getCreator().getId(), adminRequest.getTitle());
        }
        DB.deleteAdminViliChat(admin, vili);
        DB.addChat(newAdmin, newVili, adminRequestRes);

        System.out.println("----------------------------------------------------------------------------------------------------------");

        // bind to UI elements
        mainLayoutLL = (RelativeLayout) findViewById(R.id.login_RL_main);
        passwordET = (EditText) findViewById(R.id.login_ET_password);
        usernameET = (EditText) findViewById(R.id.login_ET_username);
        Button loginBTN = (Button) findViewById(R.id.login_B_login);
        Button registerBTN = (Button) findViewById(R.id.login_B_register);


        mainLayoutLL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

        // set up listeners
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordET.getText().toString();
                String username = usernameET.getText().toString();

                User user = DB.getUser(username, password);
                if (user != null) {
                    SP_VILI_EDITOR.putLong(Utils.SP_LAST_USER_ID, user.getId());
                    SP_VILI_EDITOR.apply();
                    setLoggedInUser(user);

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "User credentials wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

    }

}
