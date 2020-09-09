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
    public void InitMongoCsvFile() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\dataImport\\page\\";
        int num = 2;

        while(num <= 2) {
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

    @Test
    public void InitNeo4jCsvFile() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\page_summary\\";
        int num = 1;

        while(num <= 7) {
            String src = path + "Page_00" + num + "_summary.txt";
            String dest = path + "Page_00" + num + "_node_csv.csv";

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
            bw.write("id,index,title,LABEL\n");
            while ((tmp = bufferedReader.readLine()) != null) {
                content = "";
                String[] strings = tmp.split("\\t");
                if (strings.length > 1) {
                    content = strings[0] + "," + strings[0] + "," +
                            strings[1] + ",Entry\n";
                }
                bw.write(content);
                count++;
            }

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
    public void InitNeo4jRelationCsvFile() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\dataImport\\neo4j-relation\\";
        int num = 10;

        while(num <= 40) {
            String src = path + "test_inlink_final_0" + num + ".txt";
            String dest = path + "Page_0" + num + "_relation_csv.csv";

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
            bw.write("start,end,weight\n");
            while ((tmp = bufferedReader.readLine()) != null) {
                content = "";
                String[] strings = tmp.split("\\t");
                if (strings.length > 2) {
                    content = strings[0] + "," + strings[1] + "," +
                            strings[2] + "\n";
                }
                bw.write(content);
                count++;
            }

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
    public void InitMySQLEntryInfoCsvFile() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\page_summary\\";
        int num = 1;

        while(num <= 1) {
            String src = path + "Page_00" + num + "_summary.txt";
            String dest = path + "Page_00" + num + "_entry_info_csv.csv";

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
                content = "";
                String[] strings = tmp.split("\\t");
                if (strings.length > 1) {
                    strings[1] = strings[1]
                            .replace("\"", "\"\"");
                    content = strings[0] + ",\"" + strings[1] + "\"\n";
                }
                bw.write(content);
                count++;
            }

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
    public void InitMySQLCategoryCsvFile() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\dataImport\\tag\\";
        int num = 1;

        while(num <= 1) {
            String src = path + "Category_simplified.txt";
            String dest = path + "category_csv.csv";

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
                content = "";
                String[] strings = tmp.split("\\t");
                if (strings.length > 2) {
                    strings[2] = strings[2]
                            .replace("\"", "\"\"");
                    content = strings[0] + ",\"" + strings[2] + "\"\n";
                }
                bw.write(content);
                count++;
            }

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
    public void InitMySQLCategoryPageCsvFile() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\dataImport\\tag\\";
        int num = 1;

        while(num <= 1) {
            String src = path + "category_pages.txt";
            String dest = path + "category_page_08_csv.csv";

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
                count++;
                if(count <=7000000) continue;
                content = "";
                String[] strings = tmp.split("\\t");
                if (strings.length > 1) {
                    content = strings[1] + "," + strings[0] + "\n";
                }
                bw.write(content);
                if(count >= 8000000) break;
            }

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
