package com.leise.flow;

/**
 * Created by JY-IT-D001 on 2018/8/23.
 */
public class demo {
    public static void main(String[] args) {
        String str1 = "天行健，君子以自强不息";
        String str2 = "忘";
        String str3 = "初";
        String str4 = "心";
        String str5 = "，";
        String str6 = "努";
        String str7 = "力";
        String str8 = "前";
        String str9 = "行";
        String str10 = "19981";
        String demoArray = "22825,34892,20581,65292,21531,23376,20197,33258,24378,19981,24687,";

        String[] strArray = new String[]{"不", "忘", "初", "心", ",", "努", "力", "前", "行"};
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<strArray.length; i++){
            stringBuilder.append(strArray[i]);
        }
        System.out.println(stringBuilder.toString());

        System.out.println(stringToascii(str1));
        System.out.println(ascii2String(demoArray));
    }

    public static String stringToascii(String s){
        char[] chars = s.toCharArray();
        int[] asciiArray = new int[chars.length];

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            asciiArray[i] = (int)chars[i];
            sb.append(char2ASCII(chars[i])).append(",");
        }
        return sb.toString();
    }

    public static String ascii2String(String ASCIIs) {
        String[] ASCIIss = ASCIIs.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ASCIIss.length; i++) {
            sb.append(ascii2Char(Integer.parseInt(ASCIIss[i])));
        }
        return sb.toString();
    }

    public static char ascii2Char(int ascii) {
        return (char) ascii;
    }

    public static int char2ASCII(char c) {
        return (int) c;
    }
}
