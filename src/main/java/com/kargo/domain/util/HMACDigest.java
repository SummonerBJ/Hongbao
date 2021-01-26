package main.java.com.kargo.domain.util;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HMACDigest
{
	public static String HMACSHA256(String macData, String macKey)
	{
	        try
			{
				Mac mac = Mac.getInstance("HmacSHA256");
				//get the bytes of the hmac key and data string
				byte[] secretByte = macKey.getBytes("UTF-8");
				byte[] dataBytes = macData.getBytes("UTF-8");
				SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");

				mac.init(secret);
				byte[] doFinal = mac.doFinal(dataBytes);
				
				String checksum = bytes2Hex(doFinal);
				
				return checksum;
			}
			catch (InvalidKeyException e)
			{
				e.printStackTrace();
			}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			catch (IllegalStateException e)
			{
				e.printStackTrace();
			}
	        
	        return null;
	}


	public static String bytes2Hex(byte[] bts)
	{
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++)
		{
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1)
			{
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	/**
	 * @param args
	 */

}
