package com.example.to_do;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Saver {

    public void save(List<String> list, OutputStream os) throws IOException {
        String listAsString = listToLines(list);
        os.write(listAsString.getBytes());
        os.close();
    }

    public List<String> load(InputStream is) throws IOException {
        List<String> result = new ArrayList<>();
        if (is == null) {
            return result;
        }

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            result.add(line);
        }

        is.close();

        return result;
    }

    private String listToLines(List<String> list) {
        return String.join(System.lineSeparator(), list);
    }

}
