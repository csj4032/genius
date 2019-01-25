package com.genius.backend.application.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.apache.commons.codec.binary.Hex.decodeHex;
import static org.apache.commons.codec.binary.Hex.encodeHexString;
import static org.apache.commons.lang3.StringUtils.rightPad;

@Slf4j
public class CipherUtil {

	private static final String EMAIL_KEY = "roqkftlfghkdlxld";
	private static final int BLOCK_SIZE = 16;

	private CipherUtil() {

	}

	public static String encryptEmail(String source) {
		try {
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(EMAIL_KEY.getBytes(), "AES"));
			return encodeHexString(cipher.doFinal(rightPad(source, source.length() + BLOCK_SIZE - (source.length() % BLOCK_SIZE)).getBytes()));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public static String decryptEmail(String source) {
		try {
			var cipher = Cipher.getInstance("AES/GCM/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(EMAIL_KEY.getBytes(), "AES"));
			return new String(cipher.doFinal(decodeHex(source.toCharArray()))).trim();
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | DecoderException e) {
			log.error(e.getMessage());
		}
		return "";
	}

	public static String hmacDigest(String msg, String keyString, String algo) {
		String digest = null;
		try {
			SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
			Mac mac = Mac.getInstance(algo);
			mac.init(key);

			byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

			StringBuilder hash = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				String hex = Integer.toHexString(0xFF & bytes[i]);
				if (hex.length() == 1) {
					hash.append('0');
				}
				hash.append(hex);
			}
			digest = hash.toString();
		} catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException e) {
			log.error(e.getMessage());
		}
		return digest;
	}
}