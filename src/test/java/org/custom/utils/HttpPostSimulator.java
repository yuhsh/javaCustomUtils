package org.custom.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class HttpPostSimulator {
    public static boolean post(String userid, String password) throws IOException {

        URL url = new URL("http://localhost:8080/simpleSpringMVC/userManager/userLogin");
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");

        // post的数据
        out.write("userid=" + userid + "&password=" + password);
        // 向页面传递数据。post的关键所在！
        out.flush();
        out.close();

        // 一旦发送成功，用以下方法就可以得到服务器的回应：
        String sCurrentLine;
        String sTotalString;
        sCurrentLine = "";
        sTotalString = "";
        // 传说中的三层包装阿！
        BufferedReader resultReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((sCurrentLine = resultReader.readLine()) != null) {
            if (sCurrentLine.contains("ERROR")) {
                return false;
            }
            sTotalString += sCurrentLine + "\r\n";

        }
        System.out.println(sTotalString);
        return true;

    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        String password = null;
        for (int index = 0; index < 1000000; index++) {
            password = ("000000" + index).substring(String.valueOf(index).length());
            boolean result = post("123", password);
            if (result) {
                break;
            }
        }
        long endTime = System.currentTimeMillis();
        float seconds = (endTime - startTime) / 1000F;
        System.out.println(Float.toString(seconds) + " seconds.");
        System.out.println(password);
    }
}
