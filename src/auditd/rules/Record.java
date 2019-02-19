package auditd.rules;

import java.util.ArrayList;

public class Record {
    private String date;
    private String success;
    private String command;
    private String path;
    private ArrayList<String> args;
    private String uid;
    private String exitCode;

    public String getExitCode() {
        return exitCode;
    }

    public void setExitCode(String exitCode) {
        this.exitCode = exitCode;
    }

    public Record(String date, String success, String command, String path, ArrayList<String> args, String uid, String exitCode) {
        this.date = date;
        this.success = success;
        this.command = command;
        this.path = path;
        this.args = args;
        this.uid = uid;
        this.exitCode = exitCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return  date.toUpperCase() +
                ", success= " + success.toUpperCase() +
                ", command= " + command +
                ", path= " + path +
                ", args=" + args +
                ", uid= " + uid +
                ", exitCode= " + exitCode +
                ';';
    }
}
