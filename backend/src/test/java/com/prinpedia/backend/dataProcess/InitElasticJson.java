package com.prinpedia.backend.dataProcess;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class InitElasticJson {

    @Test
    public void InitElasticJsonFile() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\page_summary\\";
        int num = 1;

        while(num <= 1) {
            String src = path + "Page_00" + num + "_summary.txt";
            String dest = path + "Page_00" + num + "_summary_json.json";

            File srcFile = new File(src);
            FileReader fileReader = new FileReader(srcFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            File destFile = new File(dest);
            if (!destFile.exists()) {
                if (!destFile.createNewFile()) {
                    System.out.println("Create file error");
                    return;
                }
                destFile = destFile.getAbsoluteFile();
            }
            FileOutputStream fos = new FileOutputStream(destFile);
            OutputStreamWriter osw =
                    new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osw);
            System.out.println("Opening file " + src +
                    " succeeded, starting writing");

            String tmp, content;
            int count = 0;
            while ((tmp = bufferedReader.readLine()) != null) {
                content = "{ \"index\": {} }\n";
                bw.write(content);
                String[] strings = tmp.split("\\t");
                if (strings.length > 2)
                    content = "{ \"entryTitle\": \"" + strings[1] + "\", " +
                            "\"entrySummary\": \"" + strings[2] + "\"}\n";
                else if (strings.length > 1)
                    content = "{ \"entryTitle\": \"" + strings[1] + "\" }\n";
                bw.write(content);
                count++;
            }

            bw.write("\n");
            bw.close();
            osw.close();
            fos.close();
            bufferedReader.close();
            fileReader.close();
            System.out.println("Writing to file " + dest +
                    " finished, count: " + count);

            num++;
        }
    }

    @Test
    public void InitMongoJsonFile() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\";
        int num = 1;

        while(num <= 1) {
            String src = path + "Page_00" + num + "_simplified.txt";
            String dest = path + "Page_00" + num + "_simplified_csv.csv";

            File srcFile = new File(src);
            FileReader fileReader = new FileReader(srcFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            File destFile = new File(dest);
            if (!destFile.exists()) {
                if (!destFile.createNewFile()) {
                    System.out.println("Create file error");
                    return;
                }
                destFile = destFile.getAbsoluteFile();
            }
            FileOutputStream fos = new FileOutputStream(destFile);
            OutputStreamWriter osw =
                    new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osw);
            System.out.println("Opening file " + src +
                    " succeeded, starting writing");

            String tmp, content;
            int count = 0;
            bw.write("index,title,wikiText\n");
            while ((tmp = bufferedReader.readLine()) != null) {
                content = "";
                String[] strings = tmp.split("\\t");
                if (strings.length > 3) {
                    strings[3] = strings[3]
                            .replace("\"", "\"\"");
                    content = strings[1] + "," + strings[2] + ",\"" +
                            strings[3] + "\"\n";
                }
                else if (strings.length > 2)
                    content = strings[1] + "," + strings[2] + "\n";
                bw.write(content);
                count++;
            }

            bw.write("\n");
            bw.close();
            osw.close();
            fos.close();
            bufferedReader.close();
            fileReader.close();
            System.out.println("Writing to file " + dest +
                    " finished, count: " + count);

            num++;
        }
    }
}
