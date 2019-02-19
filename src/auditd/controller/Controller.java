package auditd.controller;

import auditd.rules.Record;
import auditd.rules.Rule;

import java.io.IOException;
import java.util.ArrayList;

public interface Controller {
    public String add(String rule) throws IOException;
    public String remove(Rule rule) throws IOException;
    public ArrayList<Rule> checkRules() throws IOException;
    public ArrayList<Record> showResult(Rule rule,boolean onlyToday) throws IOException, InterruptedException;
}
