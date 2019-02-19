package auditd.controller;

import auditd.rules.Rule;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AuditRuntime {

    public String auditAdd(Rule rule) throws IOException{
        String line="auditctl -a "+ rule.getScroll()+
                "," + rule.getAction();
        if (!rule.getFilters().equals(null)){
            for (String s:rule.getFilters()) {
                line+=" -F "+s;
            }
        }
        line+=" -S "+rule.getSystemCall();

        Process p = Runtime.getRuntime().exec(line);
        StringBuilder stringBuilder=new StringBuilder();

        BufferedReader input =
                new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while ((line = input.readLine()) != null) {
           stringBuilder.append(line);
        }
        return String.valueOf(stringBuilder);
    }

    public String auditShow() throws IOException{
        String line;
        StringBuilder result=new StringBuilder();

        Process p = Runtime.getRuntime().exec("auditctl -l");

        BufferedReader input =
                new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = input.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        return String.valueOf(result);

    }

    public String auditDelete(Rule rule) throws IOException{
        String line="auditctl -d "+ rule.getScroll()+
                "," + rule.getAction();
        if (!rule.getFilters().equals(null)){
            for (String s:rule.getFilters()) {
                line+=" -F "+s;
            }
        }
        line+=" -S "+rule.getSystemCall();

        Process p = Runtime.getRuntime().exec(line);

        StringBuilder stringBuilder=new StringBuilder();
        BufferedReader input =
                new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while ((line = input.readLine()) != null) {
            stringBuilder.append(line);
        }
        return line;
    }


    public String auditDeleteAll() throws IOException{
        String line;

        Process p = Runtime.getRuntime().exec("auditctl -D");

        BufferedReader input =
                new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = input.readLine()) != null) {
            System.out.println(line);
        }
        return line;
    }

    public String ausearch(String rule) throws IOException, InterruptedException {

        String [] commands = { "xterm", "-e", "ausearch -x "+rule+" > ./src/temp.txt" };
        Runtime.getRuntime().exec(commands);
        String s;
        Thread.sleep(1500);
        s=new String(Files.readAllBytes(Paths.get("./src/temp.txt")));
        File file=Paths.get("./src/temp.txt").toFile();
        file.delete();
        return s;
    }
}
