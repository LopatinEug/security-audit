package auditd.rules;

import java.util.ArrayList;

public class Rule {

    private String scroll;
    private String action;
    private String systemCall;
    private ArrayList<String> filters;

    public Rule(String systemCall) {
        this.systemCall = systemCall;
        scroll="exit";
        action="always";
        filters=new ArrayList<>();
        filters.add("arch=b64");
    }

    public Rule(String scroll, String action, String systemCall, ArrayList<String> filters) {
        this.scroll = scroll;
        this.action = action;
        this.systemCall = systemCall;
        this.filters = filters;
    }

    public String getScroll() {
        return scroll;
    }

    public void setScroll(String scroll) {
        this.scroll = scroll;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSystemCall() {
        return systemCall;
    }

    public void setSystemCall(String systemCall) {
        this.systemCall = systemCall;
    }

    public ArrayList<String> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<String> filters) {
        this.filters = filters;
    }
}
