package daily.yiyuan.com.test_java.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/12/2
 */
public class TestWriteAndRead {
    public static void main(String[] args) {
        String fileName = "hello.txt";
        writeToFile(fileName);

        String content = readFromFile(fileName);
        System.out.println(content);
    }

    private static void writeToFile(String fileName) {
        File file = new File(fileName);
        String content = "I am a good boy, I like playing basketball ! \r\n I am 32 years old. I am a good boy, I like playing basketball ! I am 32 years old. I am a good boy, I like playing basketball ! I am 32 years old. ";
        System.out.println("content = " + content);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("file size : " + file.length() + ", content size = " + content.length());
    }

    private static String readFromFile(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            int cnt = 0;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                System.out.println("cnt "+ (++cnt));
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }


}
