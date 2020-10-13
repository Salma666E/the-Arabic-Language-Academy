package com.example.magmaa.pages.ForAnyOne;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magmaa.Fragments.PostFragment;
import com.example.magmaa.R;
import com.example.magmaa.pages.Login.LoginActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnyOneActivity extends AppCompatActivity {
    TextView  mCsWord ,mLogin , mArWord ;
    CircleImageView mTranslate;
    Toast t;
    public static int c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_any_one);
        if(!isConnected())
            buildDialog().show();
        else {
            if(c==0) {
                t=Toast.makeText(AnyOneActivity.this,"مرحبآ بك فالمجمع", Toast.LENGTH_SHORT);
                t.show();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.l_post, new PostFragment()).commit();
            init();
        }
    }
    public static void setC(){c++;}
    private void init() {
        mArWord = findViewById(R.id.arword);
        mCsWord = findViewById(R.id.csword);
        mLogin = findViewById(R.id.login);
        mTranslate = findViewById(R.id.trans_img);
        mTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=google+translate&rlz=1C1CHBF_enEG865EG865&oq=google+translate&aqs=chrome..69i57j0l4j69i60j69i65j69i60.13111j0j4&sourceid=chrome&ie=UTF-8"));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(AnyOneActivity.this, "No application can handle this request."
                            + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnyOneActivity.this , LoginActivity.class ));
            }
        });
        mCsWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnyOneActivity.this , AnyOneCSActivity.class ));
            }
        });
        mArWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnyOneActivity.this , AnyOneArActivity.class ));

            }
        });
    }
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) AnyOneActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else
                return false;
        }
        else
            return false;
    }
    public AlertDialog.Builder buildDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AnyOneActivity.this);
        builder.setTitle("الانترنت غير متصل");
        builder.setMessage(" تأكد باتصال هاتفك بالانترنت او بشبكة الواى فاى");
        builder.setPositiveButton("حسنآ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        return builder;
    }
    public void onBackPressed() {
        finishAffinity();
        if (t != null)
            t.cancel();
        super.onBackPressed();
    }
}
