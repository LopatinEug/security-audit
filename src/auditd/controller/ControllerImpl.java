package auditd.controller;

import auditd.rules.Record;
import auditd.rules.Rule;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerImpl implements Controller {

    AuditRuntime auditRuntime=new AuditRuntime();

    @Override
    public String add(String rule) throws IOException {
        String message=auditRuntime.auditAdd(new Rule(rule));
        return message;
    }

    @Override
    public String remove(Rule rule) throws IOException {
        String message=auditRuntime.auditDelete(rule);
        return message;
    }

    @Override
    public ArrayList<Rule> checkRules() throws IOException {
        ArrayList<Rule> rules =new ArrayList<>();
        String[] results=auditRuntime.auditShow().split("\n");
        if(!results[0].equals("No rules")){
            for (int i = 0; i < results.length; i++) {
                rules.add(parse(results[i]));
            }
        }
        return rules;
    }

    @Override
    public ArrayList<Record> showResult(Rule rule,boolean onlyToday) throws IOException, InterruptedException {
        String result = auditRuntime.ausearch(rule.getSystemCall());
        ArrayList<Record> recordList =new ArrayList<>();
        if(!result.equals("")){
           String []records =result.split("----");

            for (int i = 1; i <records.length; i++) {
                String[] types=records[i].split("\n");
                String date="";
                String success="";
                String command="";
                String path="";
                ArrayList<String> args=new ArrayList<>();
                String uid="";
                String exitCode="";
                for (int j = 1; j <types.length ; j++) {
                    if(types[j].startsWith("time")){
                        date=types[j].substring(types[j].indexOf('>')+1,types[j].length());
                        continue;
                    }
                    if(types[j].startsWith("type=PATH")){
                        String[] params=types[j].split(" ");
                        args.add(params[3].substring(5,params[3].length()));
                        continue;
                    }
                    if(types[j].startsWith("type=CWD")){
                        String[] params=types[j].split(" ");
                        path=params[2].substring(4,params[2].length());
                        continue;
                    }
                    if(types[j].startsWith("type=SYSCALL")){
                        String[] params=types[j].split(" ");
                        success=params[4].substring(8,params[4].length());
                        exitCode=params[5].substring(5,params[5].length());
                        uid=params[14].substring(4,params[14].length());
                        command=params[24].substring(5,params[24].length());
                        continue;
                    }
                }
                recordList.add(new Record(date,success,command,path,args,uid,exitCode));
            }
            return recordList;
        }
        return null;
    }

    private Rule parse(String s){
        String scroll="";
        String action="";
        ArrayList<String> f=new ArrayList<>();
        String sysCall="";

        String[] result = s.split("-");

        for (int i = 1; i <result.length ; i++) {
            if(result[i].charAt(0)=='a'){
                action =result[i].substring(2,result[i].indexOf(','));
                scroll =result[i].substring(result[i].indexOf(',')+1,result[i].length()-1);
            }else if(result[i].charAt(0)=='F'){
                f.add(result[i].substring(2,result[i].length()-1));
            }else if(result[i].charAt(0)=='S'){
                sysCall = result[i].substring(2,result[i].length());
            }
        }

        return new Rule(scroll,action,sysCall,f);
    }

}
