package com.zac4j.droidbootstrap.util;

import android.util.Base64;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {

  private static final String AESTYPE = "AES/ECB/PKCS5Padding";

  /**
   * 加
   */
  public static String encrypt(String keyStr, String plainText) {
    byte[] encrypt = null;
    try {
      Key key = generateKey(keyStr);
      Cipher cipher = Cipher.getInstance(AESTYPE);
      cipher.init(Cipher.ENCRYPT_MODE, key);
      encrypt = cipher.doFinal(plainText.getBytes("utf-8"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Base64.encodeToString(encrypt, Base64.DEFAULT);
  }

  /**
   * 解
   */
  public static String decrypt(String keyStr, String encryptData) {
    byte[] decrypt = null;
    try {
      Key key = generateKey(keyStr);
      Cipher cipher = Cipher.getInstance(AESTYPE);
      cipher.init(Cipher.DECRYPT_MODE, key);
      decrypt = cipher.doFinal(Base64.decode(encryptData.getBytes("utf-8"), Base64.DEFAULT));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new String(decrypt).trim();
  }

  /**
   * 制key
   *
   * @throws Exception
   */
  private static Key generateKey(String key) throws Exception {
    try {
      SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
      return keySpec;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * 摘
   *
   * @return MD5值
   */
  public static final String md5(final String s) {
    final String MD5 = "MD5";
    try {
      // Create MD5 Hash
      MessageDigest digest = MessageDigest.getInstance(MD5);
      digest.update(s.getBytes());
      byte messageDigest[] = digest.digest();

      // Create Hex String
      StringBuffer hexString = new StringBuffer();
      for (byte aMessageDigest : messageDigest) {
        String h = Integer.toHexString(0xFF & aMessageDigest);
        while (h.length() < 2) h = "0" + h;
        hexString.append(h);
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return "";
  }
}