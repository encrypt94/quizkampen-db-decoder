import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

class QuizkampenDecoder
{
    public static SecretKeySpec generateSecretKey(String s)
    {
        byte key[];
        SecretKeySpec secretKey;
        try
	    {
		SecureRandom randomMachine = SecureRandom.getInstance("SHA1PRNG");
		randomMachine.setSeed("any data used as random seed".getBytes());
		KeyGenerator keyMachine = KeyGenerator.getInstance("AES");
		keyMachine.init(128, randomMachine);
		keyMachine.generateKey().getEncoded();
		key = new byte[16];
	    }
        catch(Exception exception)
	    {
		return null;
	    }
	for(int i = 0; i < 16; i++)
	    {
		key[i] = (byte)((i + (byte) s.charAt(Math.abs((s.length() - i) % s.length()))) % 256);
	    }
        secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }    

    
    public static String decode(String data, Long id) throws Exception
    {
        SecretKeySpec secretKey = generateSecretKey("var"+id+"la");
        byte decodedData[];
        Cipher aes = Cipher.getInstance("AES");
        aes.init(2, secretKey);
	data = data.trim().replaceAll("(\n|\\?| )","");
        decodedData = aes.doFinal(Base64.getDecoder().decode(data.getBytes()));
        if(decodedData != null)
            return new String(decodedData);
        else
            return "";
    }
}
