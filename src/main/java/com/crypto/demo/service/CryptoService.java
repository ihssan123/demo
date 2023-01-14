package com.crypto.demo.service;

import com.crypto.demo.models.AffineBrutModel;
import com.crypto.demo.models.InputModel;
import com.crypto.demo.models.VigenereBrutModel;
import com.crypto.demo.models.VigenereModel;
import org.springframework.stereotype.Service;
import java.lang.*;
import javax.crypto.Cipher;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CryptoService {

    private final static KeyPair keyPair;

    static {
        try {
            keyPair = generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }
    public String AffineEncrypt(InputModel model){
        model.setMessage(model.getMessage().toUpperCase());
        char[] msg = model.getMessage().toCharArray();
        String cipher = "";
        for (int i = 0; i < msg.length; i++)
        {
            // Avoid space to be encrypted
			/* applying encryption formula ( a x + b ) mod m
			{here x is msg[i] and m is 26} and added 'A' to
			bring it in range of ascii alphabet[ 65-90 | A-Z ] */
            if (msg[i] != ' ')
            {
                cipher = cipher
                        + (char) ((((model.getA() * (msg[i] - 'A')) + model.getB()) % 26) + 'A');
            } else // else simply append space character
            {
                cipher += msg[i];
            }
        }
        return cipher;
    }
    public String AffineDecrypt(InputModel model){

            String msg = "";
            int a_inv = 0;
            int flag = 0;

            //Find a^-1 (the multiplicative inverse of a
            //in the group of integers modulo m.)
            for (int i = 0; i < 26; i++)
            {
                flag = (model.getA() * i) % 26;

                // Check if (a*i)%26 == 1,
                // then i will be the multiplicative inverse of a
                if (flag == 1)
                {
                    a_inv = i;
                }
            }
            for (int i = 0; i < model.getMessage().length(); i++)
            {
			/*Applying decryption formula a^-1 ( x - b ) mod m
			{here x is cipher[i] and m is 26} and added 'A'
			to bring it in range of ASCII alphabet[ 65-90 | A-Z ] */
                if (model.getMessage().charAt(i) != ' ')
                {
                    msg = msg + (char) (((a_inv *
                            ((model.getMessage().charAt(i) + 'A' - model.getB())) % 26)) + 'A');
                }
                else //else simply append space character
                {
                    msg += model.getMessage().charAt(i);
                }
            }

            return msg;

    }

    public InputModel GetModelAffineDecrypt(InputModel model){

        String msg = "";
        int a_inv = 0;
        int flag = 0;

        //Find a^-1 (the multiplicative inverse of a
        //in the group of integers modulo m.)
        for (int i = 0; i < 26; i++)
        {
            flag = (model.getA() * i) % 26;

            // Check if (a*i)%26 == 1,
            // then i will be the multiplicative inverse of a
            if (flag == 1)
            {
                a_inv = i;
            }
        }
        for (int i = 0; i < model.getMessage().length(); i++)
        {
			/*Applying decryption formula a^-1 ( x - b ) mod m
			{here x is cipher[i] and m is 26} and added 'A'
			to bring it in range of ASCII alphabet[ 65-90 | A-Z ] */
            if (model.getMessage().charAt(i) != ' ')
            {
                msg = msg + (char) (((a_inv *
                        ((model.getMessage().charAt(i) + 'A' - model.getB())) % 26)) + 'A');
            }
            else //else simply append space character
            {
                msg += model.getMessage().charAt(i);
            }
        }

        return new InputModel(msg, model.getA(), model.getB());

    }
     
    public List<InputModel> AffineBruteDecrypt(AffineBrutModel model) {
        List<InputModel> results = new ArrayList<InputModel>();
        for (int a = 1; a < 26; a++) {
            for (int b = 0; b < 26; b++) {
                InputModel myModel = new InputModel(model.getMessage(), a,b);
                var result = GetModelAffineDecrypt(myModel);
                results.add(result);
         }
        }
        return results;
    }

    public String RSAEncrypt(InputModel model) throws Exception{
        PublicKey publicKey = keyPair.getPublic();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedBytes = cipher.doFinal(model.getMessage().getBytes());

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }


    public String RSADecrypt(InputModel model) throws Exception{
        PrivateKey privateKey = keyPair.getPrivate();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        var message = model.getMessage();
        var base64M = Base64.getDecoder().decode(message);
        var bytes = cipher.doFinal(base64M);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(model.getMessage()));

        return new String(decryptedBytes);
    }
    public static String VigenereEncrypt(VigenereModel model)
    {
        String res = "";
        model.setMessage(model.getMessage().toUpperCase());
        for (int i = 0, j = 0; i < model.getMessage().length(); i++)
        {
            char c = model.getMessage().charAt(i);
            if (c < 'A' || c > 'Z')
                continue;
            res += (char) ((c + model.getKey().charAt(j) - 2 * 'A') % 26 + 'A');
            j = ++j % model.getKey().length();
        }
        return res;
    }
    public static String ViginereDecrypt(VigenereModel model)
    {
        String res = "";
        model.setMessage(model.getMessage().toUpperCase());
        for (int i = 0, j = 0; i < model.getMessage().length(); i++)
        {
            char c = model.getMessage().charAt(i);
            if (c < 'A' || c > 'Z')
                continue;
            res += (char) ((c - model.getKey().charAt(j) + 26) % 26 + 'A');
            j = ++j % model.getKey().length();
        }
        return res;
    }
    public static VigenereModel GetViginereDecrypt(VigenereModel model)
    {
        String res = "";
        model.setMessage(model.getMessage().toUpperCase());
        for (int i = 0, j = 0; i < model.getMessage().length(); i++)
        {
            char c = model.getMessage().charAt(i);
            if (c < 'A' || c > 'Z')
                continue;
            res += (char) ((c - model.getKey().charAt(j) + 26) % 26 + 'A');
            j = ++j % model.getKey().length();
        }
        return new VigenereModel(res,model.getKey());
    }
    public static List<String> generateCombinations(int length) {
        int n = 26;
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder combination = new StringBuilder();
        List<String> combinations = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            combination.append("A");
        }
        while (true) {
            combinations.add(combination.toString());
            int index = combination.length() - 1;
            while (index >= 0) {
                if (combination.charAt(index) != 'Z') {
                    combination.setCharAt(index, (char) (combination.charAt(index) + 1));
                    break;
                } else {
                    combination.setCharAt(index, 'A');
                    index--;
                }
            }
            if (index < 0) {
                break;
            }
        }
        return combinations;
    }
    public  List<VigenereModel> bruteForceVigenere(VigenereBrutModel model) {
        List<VigenereModel> results = new ArrayList<VigenereModel>();
        var keys =generateCombinations(model.getKeyLength());
        // Try all possible keys of the current length
        for(int i=0;i<keys.size();i++) {
            String key = keys.get(i);
            VigenereModel myModel = new VigenereModel(model.getMessage(),key);
            var result = GetViginereDecrypt(myModel);
           results.add(result);

        }
        return results;
    }

    }

