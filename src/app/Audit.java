package app;

import auditd.controller.Controller;
import auditd.controller.ControllerImpl;
import auditd.rules.Record;
import auditd.rules.Rule;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Audit extends JFrame {

    JPanel leftPanel;
    JPanel rightPanel;
    ArrayList<String> data;
    JList<String> list;
    JTextArea textArea;
    DefaultListModel<String> model=new DefaultListModel<>();
    ArrayList<Rule> rules=new ArrayList<>();
    Controller audit;

    public Audit() throws IOException {
        audit = new ControllerImpl();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);

        setLocationByPlatform(true);

        Image img = new ImageIcon("./src/app/logo.jpg").getImage();
        setIconImage(img);

        leftPanel=new JPanel();
        rightPanel=new JPanel();

        JPanel bottonLeftPanel=new JPanel();
        JButton buttonAdd=new JButton("add");
        JButton buttonDelete=new JButton("delete");

        buttonAdd.addActionListener(e -> {
            try {
                addRule();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        buttonDelete.addActionListener(e -> {
            try {
                deleteRule();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        bottonLeftPanel.add(buttonAdd);
        bottonLeftPanel.add(buttonDelete);

        leftPanel.setLayout(new BorderLayout());

        leftPanel.add(bottonLeftPanel,BorderLayout.SOUTH);

        /*data=new ArrayList<>();
        data.add("sasa");
        data.add("sasd");
        data.add("sasb");
        data.add("sasc");

        for (String el:data) {
            model.addElement(el);
        }*/

        list = new JList(model);
        JScrollPane scrollPane = new JScrollPane(list);

        leftPanel.add(scrollPane);



        textArea=new JTextArea();
        Border border=BorderFactory.createEtchedBorder();
        textArea.setBorder(border);
        textArea.setLineWrap(true);
        list.setBorder(border);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel bottonRightPanel=new JPanel();
        JButton buttonCheck=new JButton("Check");
        JButton buttonCheckToday=new JButton("Check today");


        buttonCheck.addActionListener(e -> {
            try {
                showAllStatistic();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });

        bottonRightPanel.add(buttonCheck);
        bottonRightPanel.add(buttonCheckToday);

        JScrollPane scrollPane2 = new JScrollPane(textArea);

        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(bottonRightPanel,BorderLayout.SOUTH);
        rightPanel.add(scrollPane2);

        add(leftPanel,BorderLayout.WEST);
        add(rightPanel);

        checkRules();

    }


    public void checkRules() throws IOException {
        model.removeAllElements();
        rules=audit.checkRules();
        if(!rules.isEmpty()){
            for (Rule rule:rules) {
                model.addElement(rule.getSystemCall());
            }
        }else{
            JOptionPane.showMessageDialog(this,"There is no rules, add them","Warning",2);
        }
    }

    public void addRule() throws IOException {
        String rule=JOptionPane.showInputDialog("Input Rule");
        String message=audit.add(rule);
        if(message.equals("")){
            checkRules();
            JOptionPane.showMessageDialog(this,"Rule was added","Info",1);
        }else{
         JOptionPane.showMessageDialog(this,message,"Error",0);
        }
    }

    //TODO cgange this method
    public void deleteRule() throws IOException {
        Rule rule=rules.get(list.getSelectedIndex());
        String message=audit.remove(rule);
        if(message==null){
            checkRules();
            JOptionPane.showMessageDialog(this,"Rule was deleted","Info",1);
        }else{
         JOptionPane.showMessageDialog(this,message,"Error",0);
        }
    }

    public void showAllStatistic() throws IOException, InterruptedException {
        textArea.setText("");
        Rule rule=rules.get(list.getSelectedIndex());
        ArrayList<Record> records=audit.showResult(rule,false);
        StringBuilder result=new StringBuilder();
        for (Record r:records) {
            result.append(r.toString()+"\n------\n");
        }
        textArea.setText(String.valueOf(result));
    }


}
