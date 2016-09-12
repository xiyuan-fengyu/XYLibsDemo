package com.xiyuan.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Created by xiyuan_fengyu on 2016/9/12.
 */
public class SignatureUtil {

    public static final String MD5 = "MD5";

    public static final String SHA1 = "SHA1";

    public static String get(Context context, String typeStr) {
        try {
            PackageManager pm = context.getPackageManager();
            String packageName = context.getPackageName();
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);

            Signature[] signatures = packageInfo.signatures;
            byte[] cert = signatures[0].toByteArray();
            InputStream input = new ByteArrayInputStream(cert);
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate c = (X509Certificate) cf.generateCertificate(input);
            MessageDigest md = MessageDigest.getInstance(typeStr);
            byte[] publicKey = md.digest(c.getEncoded());
            return byte2HexStr(publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String byte2HexStr(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (byte b: arr) {
            String h = Integer.toHexString(b);
            int l = h.length();
            if (l == 1)
                h = "0" + h;
            if (l > 2)
                h = h.substring(l - 2, l);
            str.append(h.toLowerCase());
        }
        return str.toString();
    }

}
