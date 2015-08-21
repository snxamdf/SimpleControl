package sc.yhy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtils {
	private static SecretKey A = null;

	static {
		byte[] arrayOfByte = { 11, 25, 79, 110, 49, 41, 16, 25 };
		A = new SecretKeySpec(arrayOfByte, 0, arrayOfByte.length, "DES");
	}

	public static String encryptToMD5(String paramString) {
		byte[] arrayOfByte = null;
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramString.getBytes());
			arrayOfByte = localMessageDigest.digest();
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
			localNoSuchAlgorithmException.printStackTrace();
		}
		String str = byte2hex(arrayOfByte);
		return str;
	}

	public static String encryptToSHA(String paramString) {
		byte[] arrayOfByte = null;
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
			localMessageDigest.update(paramString.getBytes());
			arrayOfByte = localMessageDigest.digest();
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
			localNoSuchAlgorithmException.printStackTrace();
		}
		return byte2hex(arrayOfByte);
	}

	public static String encryptToDES(String paramString) {
		return encryptToDES(paramString, A);
	}

	public static String encryptToDES(String paramString1, String paramString2) {
		return encryptToDES(paramString1, A(paramString2));
	}

	public static String encryptToDES(String paramString, SecretKey paramSecretKey) {
		String str = "DES";
		SecureRandom localSecureRandom = new SecureRandom();
		byte[] arrayOfByte = null;
		try {
			Cipher localCipher = Cipher.getInstance(str);
			localCipher.init(1, paramSecretKey, localSecureRandom);
			arrayOfByte = localCipher.doFinal(paramString.getBytes());
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return byte2hex(arrayOfByte);
	}

	private static SecretKey A(String paramString) {
		byte[] arrayOfByte = hexToBytes(paramString);
		return new SecretKeySpec(Arrays.copyOfRange(arrayOfByte, 0, 8), "DES");
	}

	public static String decryptByDES(String paramString) {
		return decryptByDES(paramString, A);
	}

	public static String decryptByDES(String paramString1, String paramString2) {
		return decryptByDES(paramString1, A(paramString2));
	}

	public static String decryptByDES(String paramString, SecretKey paramSecretKey) {
		String str = "DES";
		SecureRandom localSecureRandom = new SecureRandom();
		byte[] arrayOfByte = null;
		try {
			Cipher localCipher = Cipher.getInstance(str);
			localCipher.init(2, paramSecretKey, localSecureRandom);
			arrayOfByte = localCipher.doFinal(hexToBytes(paramString));
		} catch (Exception localException) {
			return null;
		}
		return new String(arrayOfByte);
	}

	public static String byte2hex(byte[] paramArrayOfByte) {
		String str = "";
		for (int i = 0; i < paramArrayOfByte.length; i++)
			str = str + Integer.toString((paramArrayOfByte[i] & 0xFF) + 256, 16).substring(1);
		return str.toUpperCase();
	}

	public static byte[] hexToBytes(char[] paramArrayOfChar) {
		int i = paramArrayOfChar.length / 2;
		byte[] arrayOfByte = new byte[i];
		for (int j = 0; j < i; j++) {
			int k = Character.digit(paramArrayOfChar[(j * 2)], 16);
			int m = Character.digit(paramArrayOfChar[(j * 2 + 1)], 16);
			int n = k << 4 | m;
			if (n > 127)
				n -= 256;
			arrayOfByte[j] = (byte) n;
		}
		return arrayOfByte;
	}

	public static byte[] hexToBytes(String paramString) {
		return hexToBytes(paramString.toCharArray());
	}

	public static void main(String[] paramArrayOfString) {
	}
}