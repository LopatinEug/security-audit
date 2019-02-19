import app.Audit;
import auditd.controller.AuditRuntime;
import auditd.controller.Controller;
import auditd.controller.ControllerImpl;
import auditd.rules.Record;
import auditd.rules.Rule;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Test {

    public static void main(String[] args) throws IOException, InterruptedException {
        EventQueue.invokeLater(() ->
        {
            JFrame frame = null;
            try {
                frame = new Audit();
            } catch (IOException e) {
                e.printStackTrace();
            }
            frame.setTitle("Security Audit");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true) ;
        });
    }
}
