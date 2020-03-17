package com.avon.remindfeedback.etcProcess;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Util {

    public static String testSHA256(String str){
        String SHA = "";
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");//SHA-256방식으로 암호화
            sh.update(str.getBytes()); //갱신
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < byteData.length ; i++){//입력
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            SHA = null;
        }
        return SHA;

    }
}
