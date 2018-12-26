package cn.kylin.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.kylin.utils.encrypt.encode.Base64Util;
import cn.kylin.utils.encrypt.oneway.MD5Util;

/**
 * Created by kylinhuang on 03/08/2017.
 */

public class SPUtils {

    private static Map<String, SPUtils> sSPMap = new HashMap<>();
    private SharedPreferences sp;
    boolean isEncrypt = false;

    private String password = "";

    public static SPUtils getInstance() {
        return getInstance("");
    }

    public void setEncrypt(boolean isEncrypt, String password) {
        Log.e("SPUtils",  isEncrypt  + "  "+ password);

        this.isEncrypt = isEncrypt;
        this.password = password;
    }

    public static SPUtils getInstance(String spName) {
        if (isSpace(spName)) spName = "spUtils";
        SPUtils sp = sSPMap.get(spName);
        if (sp == null) {
            sp = new SPUtils(spName);
            sSPMap.put(spName, sp);
        }
        return sp;
    }

    private SPUtils(final String spName) {
        sp = Utils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }


    public void put(@NonNull final String key, @NonNull final String value) {
        Log.e("SPUtils"," put " + key  + "  "+ value);
        sp.edit().putString(hashPrefKey(key), encrypt(value) ).commit();
    }


    public String getString(@NonNull final String key) {
        String encryptedValue = sp.getString( hashPrefKey(key), null);
        Log.e("SPUtils", " getString " + key  + "  "+ ((encryptedValue != null) ? decrypt(encryptedValue) : ""));
        return (encryptedValue != null) ? decrypt(encryptedValue) : "";
    }


    public String getString(@NonNull final String key, @NonNull final String defaultValue) {
        String encryptedValue = sp.getString( hashPrefKey(key), null);
        Log.e("SPUtils", " getString " + key  + "  "+ ((encryptedValue != null) ? decrypt(encryptedValue) : ""));

        return (encryptedValue != null) ? decrypt(encryptedValue) : defaultValue;
    }


    public void put(@NonNull final String key, final int value) {
        Log.e("SPUtils"," put " + key  + "  "+ value);

        sp.edit().putString(hashPrefKey(key), encrypt(Integer.toString(value))).commit();
    }


    public int getInt(@NonNull final String key) {
        String encryptedValue = sp.getString( hashPrefKey(key), null);
        try {
            Log.e("SPUtils", " getString " + key  + "  " + Integer.parseInt(decrypt(encryptedValue))  );

            return Integer.parseInt(decrypt(encryptedValue));
        } catch (NumberFormatException e) {

        }
        return -1;
    }


    public int getInt(@NonNull final String key, final int defaultValue) {
        String encryptedValue = sp.getString( hashPrefKey(key), null);
        try {
            Log.e("SPUtils", " getString " + key  + "  " + Integer.parseInt(decrypt(encryptedValue))  );

            return Integer.parseInt(decrypt(encryptedValue));
        } catch (NumberFormatException e) {

        }
        return defaultValue;
    }


    public void put(@NonNull final String key, final long value) {
        Log.e("SPUtils"," put " + key  + "  "+ value);

        sp.edit().putString(hashPrefKey(key), encrypt(Long.toString(value)) ).commit();
    }


    public long getLong(@NonNull final String key) {
        Log.e("SPUtils", " getString " + key  + "  " + sp.getLong(key, -1L)  );

        return sp.getLong(key, -1L);
    }


    public long getLong(@NonNull final String key, final long defaultValue) {
        String encryptedValue = sp.getString( hashPrefKey(key), null);
        try {
            Log.e("SPUtils", " getString " + key  + "  " + Long.parseLong(decrypt(encryptedValue))  );

            return Long.parseLong(decrypt(encryptedValue));
        } catch (NumberFormatException e) {

        }
        return defaultValue;
    }


    public void put(@NonNull final String key, final float value) {
        Log.e("SPUtils"," put " + key  + "  "+ value);

        sp.edit().putString(hashPrefKey(key), encrypt(Float.toString(value)) ).commit();
    }


    public float getFloat(@NonNull final String key) {
        String encryptedValue = sp.getString( hashPrefKey(key), null);
        try {
            Log.e("SPUtils", " getString " + key  + "  " + Float.parseFloat(decrypt(encryptedValue))  );

            return Float.parseFloat(decrypt(encryptedValue));
        } catch (NumberFormatException e) {

        }
        return -1f;
    }


    public float getFloat(@NonNull final String key, final float defaultValue) {
        String encryptedValue = sp.getString( hashPrefKey(key), null);
        try {
            Log.e("SPUtils", " getString " + key  + "  " + Float.parseFloat(decrypt(encryptedValue)) );

            return Float.parseFloat(decrypt(encryptedValue));
        } catch (NumberFormatException e) {

        }
        return defaultValue;
    }


    public void put(@NonNull final String key, final boolean value) {
        Log.e("SPUtils"," put " + key  + "  "+ value);

        sp.edit().putString(hashPrefKey(key), encrypt(Boolean.toString(value)) ).commit();
    }


    public boolean getBoolean(@NonNull final String key) {
        String encryptedValue = sp.getString( hashPrefKey(key), null);
        try {
            return Boolean.parseBoolean(decrypt(encryptedValue));
        } catch (NumberFormatException e) {

        }
        return false;
    }


    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        String encryptedValue = sp.getString( hashPrefKey(key), null);
        try {
            return Boolean.parseBoolean(decrypt(encryptedValue));
        } catch (NumberFormatException e) {

        }
        return defaultValue;
    }


    public void put(@NonNull final String key, @NonNull final Set<String> values) {
        Log.e("SPUtils"," put " + key  + "  "+ values);

        final Set<String> encryptedValues = new HashSet<String>(
                values.size());
        for (String value : values) {
            encryptedValues.add(encrypt(value));
        }
        sp.edit().putStringSet(hashPrefKey(key),
                encryptedValues).commit();
    }


    public Set<String> getStringSet(@NonNull final String key) {

        final Set<String> encryptedSet = sp.getStringSet( hashPrefKey(key), null);
        if (encryptedSet == null) {
            return encryptedSet;
        }
        final Set<String> decryptedSet = new HashSet<String>(
                encryptedSet.size());
        for (String encryptedValue : encryptedSet) {
            decryptedSet.add(decrypt(encryptedValue));
        }
        return decryptedSet;
    }


    public Set<String> getStringSet(@NonNull final String key, @NonNull final Set<String> defaultValue) {

        final Set<String> encryptedSet = sp.getStringSet( hashPrefKey(key), null);
        if (encryptedSet == null) {
            return defaultValue;
        }
        final Set<String> decryptedSet = new HashSet<String>(
                encryptedSet.size());
        for (String encryptedValue : encryptedSet) {
            decryptedSet.add(decrypt(encryptedValue));
        }
        return decryptedSet;
    }

    public Map<String, ?> getAll() {
        if (isEncrypt ==false) return sp.getAll();


        final Map<String, ?> encryptedMap = getAll();
        final Map<String, String> decryptedMap = new HashMap<String, String>(
                encryptedMap.size());
        for (Map.Entry<String, ?> entry : encryptedMap.entrySet()) {
            try {
                Object cipherText = entry.getValue();
                //don't include the key
                if (cipherText != null ) {
                    //the prefs should all be strings
                    decryptedMap.put(entry.getKey(), decrypt(cipherText.toString()));
                }
            } catch (Exception e) {
                LogUtils.w("error during getAll" + e);
                // Ignore issues that unencrypted values and use instead raw cipher text string
                decryptedMap.put(entry.getKey(),
                        entry.getValue().toString());
            }
        }
        return decryptedMap;




    }


    public boolean contains(@NonNull final String key) {
        if (isEncrypt) return sp.contains(hashPrefKey(key));
        return sp.contains(key);
    }


    public void remove(@NonNull final String key) {
        if (isEncrypt)  {
            sp.edit().remove(hashPrefKey(key)).commit();
        }else {
            sp.edit().remove(key).commit();
        }
    }


    public void clear() {
        sp.edit().clear().commit();
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * The Pref keys must be same each time so we're using a hash to obscure the stored value
     *
     * @param prefKey
     * @return SHA-256 Hash of the preference key
     */
    public  String hashPrefKey(String prefKey) {
        if (TextUtils.isEmpty(prefKey)) {
            return prefKey;
        }
        if (isEncrypt){
            return MD5Util.md5(prefKey);
//            final MessageDigest digest;
//            try {
//                digest = MessageDigest.getInstance("SHA-256");
//                byte[] bytes = prefKey.getBytes("UTF-8");
//                digest.update(bytes, 0, bytes.length);
//
//                return Base64.encodeToString(digest.digest(), AesCbcWithIntegrity.BASE64_FLAGS);
//
//            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
//                LogUtils.w( "Problem generating hash"  + e.toString());
//            }
        }
        return prefKey ;
    }

    private String encrypt(String cleartext) {
        if (TextUtils.isEmpty(cleartext)) {
            return cleartext;
        }
        if (isEncrypt){
            return Base64Util.base64EncodeStr(cleartext);
//            return AESUtil.aes(cleartext, password, Cipher.ENCRYPT_MODE);
        }
        return cleartext ;
    }

    /**
     * @param ciphertext
     * @return decrypted plain text, unless decryption fails, in which case null
     */
    private String decrypt(final String ciphertext) {
        if (TextUtils.isEmpty(ciphertext)) {
            return ciphertext;
        }
        if (isEncrypt){
            return Base64Util.base64DecodedStr(ciphertext);
//            return AESUtil.aes(ciphertext, password, Cipher.DECRYPT_MODE);
        }
        return ciphertext ;
    }
}
