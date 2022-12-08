package com.example.capstone2;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashKey {
    public void migrateSignatures(Context context) {
        if(BuildConfig.DEBUG) {
            PackageInfo packageInfo = null;

            try {
                packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                        PackageManager.GET_SIGNING_CERTIFICATES);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            if (packageInfo == null)
                Log.e("KeyHash", "KeyHash:null");

            assert packageInfo != null;
            Signature[] signatureInfo = packageInfo.signingInfo.getApkContentsSigners();

            for(Signature signature : signatureInfo) {
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                } catch (NoSuchAlgorithmException e) {
                    Log.e("KeyHash", "Unable to get MessageDigest. signature = " + signature, e);
                }
            }
        }
    }
}
