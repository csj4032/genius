package com.genius.backend.application.util;

import org.apache.commons.codec.DecoderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.apache.commons.codec.binary.Hex.decodeHex;
import static org.apache.commons.codec.binary.Hex.encodeHexString;
import static org.apache.commons.lang3.StringUtils.rightPad;

public class CipherUtil {

	private static final String EMAIL_KEY = "roqkftlfghkdlxld";
	private static final int BLOCK_SIZE = 16;

	private CipherUtil() {

	}

	public static String encryptEmail(String source) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(EMAIL_KEY.getBytes(), "AES"));
			return encodeHexString(cipher.doFinal(rightPad(source, source.length() + BLOCK_SIZE - (source.length() % BLOCK_SIZE)).getBytes()));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decryptEmail(String source) {
		try {
			var cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(EMAIL_KEY.getBytes(), "AES"));
			return new String(cipher.doFinal(decodeHex(source.toCharArray()))).trim();
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | DecoderException e) {
			e.printStackTrace();
		}
		return "";
	}
}