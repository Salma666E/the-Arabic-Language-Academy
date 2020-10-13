package com.example.magmaa.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magmaa.CircleTransform;
import com.example.magmaa.Fragments.AboutFragment;
import com.example.magmaa.Fragments.AddCommentFragment;
import com.example.magmaa.Fragments.LogOutFragment;
import com.example.magmaa.Fragments.MangMogamaFragment;
import com.example.magmaa.Fragments.PostFragment;
import com.example.magmaa.Fragments.ScWordsFragment;
import com.example.magmaa.R;
import com.example.magmaa.API_Mange.SessionMangment;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView mOpenDrawer;
    CircleImageView mUserImg;
    DrawerLayout mDrawer;
    TextView mHome,  mLogout , mArbWord , mScWord , mShare ,mRate,mPageTitle,mMogma
                     ,  mAboutas ,mUserName;
    public static int navItemIndex = 0 ,c=0;
    SessionMangment mSessionMangment;
    /*                   /////////to select profileImage/////////////
    1- check if can acsses to file
    2- open  glary
    3- get photo and show after selection
    4- save photo to shp
    5- get photo from shp
    6- show photo
    * */
    private int FileReq = 1000;
    private String path = "";
    private int GALLERY_CODE = 800;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!isConnected())
            buildDialog().show();
        else {
            if(c==0) {
                c++;
                Toast.makeText(MainActivity.this,"مرحبآ بك فالمجمع", Toast.LENGTH_SHORT).show();
            }
            init();
        }
    }
    private void init() {
        mSessionMangment = new SessionMangment(this);
        mOpenDrawer = findViewById(R.id.opne_drawer);
        mDrawer = findViewById(R.id.drawer_layout);
        mHome = findViewById(R.id.home);
        mMogma = findViewById(R.id.mogma);
        mArbWord = findViewById(R.id.arb_word);
        mScWord = findViewById(R.id.cs_word);
        mLogout = findViewById(R.id.logout);
        mShare = findViewById(R.id.share);
        mRate = findViewById(R.id.rate);
        mAboutas = findViewById(R.id.about);
        mPageTitle = findViewById(R.id.page_title);
        mUserName = findViewById(R.id.user_name);
        mUserImg = findViewById(R.id.user_img);
        mOpenDrawer.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mMogma.setOnClickListener(this);
        mUserImg.setOnClickListener(this);
        mArbWord.setOnClickListener(this);
        mScWord.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mRate.setOnClickListener(this);
        mAboutas.setOnClickListener(this);
        loadFragment(new PostFragment(), getString(R.string.home), 0);
    }
    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.opne_drawer:
                    mDrawer.openDrawer(Gravity.RIGHT);
                    break;
                case R.id.home:
                    mDrawer.closeDrawers();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    mPageTitle.setText(getString(R.string.home));
                    break;
                case R.id.arb_word:
                    mDrawer.closeDrawers();
                    loadFragment(new AddCommentFragment(), " قاموس اللغة العربية ", 5);
                    break;
                case R.id.cs_word:
                    mDrawer.closeDrawers();
                    loadFragment(new ScWordsFragment(), " قاموس المصطلحات العلمية ", 6);
                    break;
                case R.id.logout:
                    mDrawer.closeDrawers();
                    loadFragment(new LogOutFragment(), getString(R.string.logout), 1);
                    break;
                case R.id.about:
                    mDrawer.closeDrawers();
                    loadFragment(new AboutFragment(), getString(R.string.aboutas), 2);
                break;
                case R.id.share:
                    mDrawer.closeDrawers();
                    shareApp();
                    break;
                case R.id.rate:
                    mDrawer.closeDrawers();
                    rateApp();
                    break;
                case R.id.user_img:
                    mDrawer.closeDrawers();
                    choosePhotoFromGallary();
                    break;
                case R.id.mogma:
                    mDrawer.closeDrawers();
                    loadFragment(new MangMogamaFragment(), "ادارة المجمع", 3);
                    break;
                default:
                    mDrawer.closeDrawers();
                    break;
            }
    }
    private void rateApp() {
        launchMarket();
        final String appPackageName = "your.package.name";
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
    void loadFragment(Fragment fragment, String pageTitle, int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.page_container, fragment).commit();
        mPageTitle.setText(pageTitle);
        mDrawer.closeDrawers();
        navItemIndex = index;
    }
    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(Gravity.RIGHT)) {
            mDrawer.closeDrawer(Gravity.RIGHT);
            return;
        }
        else
        finishAffinity();
        if (true) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                mPageTitle.setText(getString(R.string.home));
                return;
            }
            c=0;
        }
        super.onBackPressed();
    }
    private void shareApp() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Share Application");
        i.putExtra(Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=" +
                        getApplicationContext().getPackageName());
        startActivity(Intent.createChooser(i, "مشاركة مع"));

    }
    @Override
    protected void onResume() {
        super.onResume();
        mUserName.setText(mSessionMangment.getUserDetails().get(SessionMangment.KEY_FNAME));
        if(mSessionMangment.getUserDetails().get(SessionMangment.KEY_IMAGE) != null)
            getUserPhoto(mUserImg);
        else
            mUserImg.setImageResource(R.drawable.avatar);
    }
    public boolean isConnected() {     //to check internet connection
        ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else
                return false;
        }
        else
        return false;
    }
    public AlertDialog.Builder buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
    public void choosePhotoFromGallary() {   // To Select Image From Gallery
        if (!checkForFile()) {  // 1 is done
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, FileReq);
            }
            return;
        }
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_CODE); // 2 is done
    }
    private void getUserPhoto(ImageView imageView) {
        String path = mSessionMangment.getUserDetails().get(SessionMangment.KEY_IMAGE);
        Uri myUri = Uri.parse(path);
        Picasso.get().load(myUri).centerCrop().fit().transform(new CircleTransform()).into(imageView);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), myUri);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.e("getUserPhoto", "Error " + e.getMessage());
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_CODE) {
            if (data != null) {   // 3 is done
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    saveToSherdPref(contentURI);
                    Toast.makeText(MainActivity.this, "تم حفظ الصورة", Toast.LENGTH_SHORT).show();
                    mSessionMangment.saveEdit(getImageUri(bitmap)+""); // 4 is done
                    mUserImg.setImageBitmap(bitmap);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("onActivityResult  ", "EditProfile Error -> " + e.getMessage());
                    Toast.makeText(MainActivity.this, "فشل حفظ الصورة !", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private boolean checkForFile() {
    return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED;
}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == FileReq) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhotoFromGallary();
            } else {
                Toast.makeText(this, "هذا يجب لأختيار صورة ", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void saveToSherdPref(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            mSessionMangment.saveEdit(path);         // 4 is done
        } catch (Exception e) {
            Log.e("Error File", "saveToSherdPref Error -> " + e.getMessage());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public Uri getImageUri(Bitmap inImage) {
        //ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}