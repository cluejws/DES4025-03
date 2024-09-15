package com.des4025.smartsunglass.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class StringConverter {

    public static String byteArrayToHex(byte[] byteArr) {
        StringBuilder sb = new StringBuilder();
        for (final byte b : byteArr)
            sb.append(String.format("%02x ", b & 0xff));
        
        return sb.toString();
    }

    public static String hexToAscii(String hexStr) {
        StringBuilder sb = new StringBuilder("");
        String[] hexStrArr = hexStr.split(" ");
        for (int i = 0; i < hexStrArr.length; i++) {
            sb.append((char) Integer.parseInt(hexStrArr[i], 16));
        }
        
        return sb.toString();
    }

    public static String strToAns(String str){
        String strArr[] = str.split("/");
        String result = "";


        int countArr[] = new int[21];
        String objects[] = {" 차량"," 이륜차량"," 사람"," 계단"," 신호등"," 기둥"," 진입 방지 봉"};
        String directions[] = {"왼쪽에","오른쪽에","전방에"};

        Vector<String> leftVector = new Vector<>();
        Vector<String> rightVector = new Vector<>();
        Vector<String> aheadVector= new Vector<>();

        // 1.
        for (int i = 0; i < strArr.length; i++) {
            // 차량
            if (strArr[i].contains("car")) {
                if (strArr[i].charAt(strArr[i].length() - 1) == 'l') {
                    countArr[0]++;
                } else if (strArr[i].charAt(strArr[i].length() - 1) == 'r') {
                    countArr[1]++;
                } else {
                    countArr[2]++;
                }
            }
            // 이륜차량
            else if (strArr[i].contains("cycle")) {
                if (strArr[i].charAt(strArr[i].length() - 1) == 'l') {
                    countArr[3]++;
                } else if (strArr[i].charAt(strArr[i].length() - 1) == 'r') {
                    countArr[4]++;
                } else {
                    countArr[5]++;
                }
            }
            // 사람
            else if (strArr[i].contains("person")) {
                if (strArr[i].charAt(strArr[i].length() - 1) == 'l') {
                    countArr[6]++;
                } else if (strArr[i].charAt(strArr[i].length() - 1) == 'r') {
                    countArr[7]++;
                } else {
                    countArr[8]++;
                }
            }
            // 계단
            else if (strArr[i].contains("stair")) {
                if (strArr[i].charAt(strArr[i].length() - 1) == 'l') {
                    countArr[9]++;
                } else if (strArr[i].charAt(strArr[i].length() - 1) == 'r') {
                    countArr[10]++;
                } else {
                    countArr[11]++;
                }
            }
            // 신호등
            else if (strArr[i].contains("signal")) {
                if (strArr[i].charAt(strArr[i].length() - 1) == 'l') {
                    countArr[12]++;
                } else if (strArr[i].charAt(strArr[i].length() - 1) == 'r') {
                    countArr[13]++;
                } else {
                    countArr[14]++;
                }
            }
            // 기둥
            else if (strArr[i].contains("power")) {
                if (strArr[i].charAt(strArr[i].length() - 1) == 'l') {
                    countArr[15]++;
                } else if (strArr[i].charAt(strArr[i].length() - 1) == 'r') {
                    countArr[16]++;
                } else {
                    countArr[17]++;
                }

            }
            // 진입 방지봉
            else if (strArr[i].contains("bolard")) {
                if (strArr[i].charAt(strArr[i].length() - 1) == 'l') {
                    countArr[18]++;
                } else if (strArr[i].charAt(strArr[i].length() - 1) == 'r') {
                    countArr[19]++;
                } else {
                    countArr[20]++;
                }
            }
        }

        // 2.
        int cnt = 0;
        for (int i=0; i<21; i+=3)
        {
            List<String> tempList = new ArrayList<String>();

            // 2-1
            int cnt2 = 0;
            boolean flag = false;
            for (int j=i; j<i+3; j++)
            {
                if(countArr[j]!=0)
                {
                    String tmp = "";
                    tmp+=directions[j-i];
                    tmp+=objects[i/3];
                    tempList.add(tmp);
                    cnt2+=countArr[j];
                    flag =true;

                }
            }

            //2-2
            if(cnt2>=2)
            {
                String tmp = "";
                tempList.clear();
                tmp = "전방에 다수의";
                tmp+=objects[i/3];
                tempList.add(tmp);
            }

            //2-3
            if(flag == true)
                cnt++;

            //2-4
            for(int j=0;j<tempList.size();j++)
            {
                if(tempList.get(j).charAt(0)== '왼')
                    leftVector.add(tempList.get(j).substring(3));
                else if(tempList.get(j).charAt(0) == '오')
                    rightVector.add(tempList.get(j).substring(4));
                else if(tempList.get(j).charAt(0) == '전')
                    aheadVector.add(tempList.get(j).substring(3));
            }

            //2-5
            if(cnt == 3)
                break;
        }

        // 전방 출력
        if(!aheadVector.isEmpty())
            result += "전방에";
        for (int i=0; i<aheadVector.size(); i++)
        {
            result += aheadVector.get(i);
            if(i != aheadVector.size() - 1)
                result += "과";
            else
                result += "이 있습니다 ";
        }

        // 왼쪽 출력
        if(!leftVector.isEmpty())
            result += "왼쪽에";
        for (int i=0; i<leftVector.size(); i++)
        {
            result += leftVector.get(i);
            if(i != leftVector.size() - 1)
                result += "과";
            else
                result += "이 있습니다 ";
        }

        // 오른쪽 출력
        if(!rightVector.isEmpty())
            result += "오른쪽에";
        for (int i=0; i<rightVector.size(); i++)
        {
            result += rightVector.get(i);
            if(i != rightVector.size() - 1)
                result += "과";
            else
                result += "이 있습니다 ";
        }

        return result;
    }
}
