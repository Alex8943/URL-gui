package demo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class URLGui {

    public URLGui() {
        jdbcWriter = new JDBCWriter();
    }

    JTextField txtUrl;
    JTextField txtResult;
    ArrayList<String> urlList;
    JDBCWriter jdbcWriter;
    JButton pbSaveToDB;
    JLabel lblcount;
    JButton pbDelete;
    JButton pbSearch;
    JList lbLines;

    final JFrame frame = new JFrame("URL Gui");

    public void retrievLinesDB(){
        Vector<String> v1 = new Vector<>();
        v1.add("Test 1");
        v1.add("Test 2");
        v1.add("Test 2");

        String url = txtUrl.getText();
        String srch = txtResult.getText();
        v1 = jdbcWriter.getLines(url, srch);
        lbLines.setListData(v1);
    }


    public void retrievURL(){
        String url = txtUrl.getText();
        System.out.println(url);
        URLReader uread = new URLReader();
        urlList = uread.readUrl(url);
        int sz = urlList.size();
        txtResult.setText("" + sz);

        ArrayList<String> lst2 = new ArrayList<>();
        for (String line: urlList){
            String[] strArr;
            //if(line.length() > 15000){}
                if (line.length() > 1){
                strArr = line.split("<");
                for (String ss: strArr) {
                    lst2.add(ss);
                }
            }
        }

        urlList.addAll(lst2);
    }

    public void connect(){
        boolean gotCon = jdbcWriter.setconnection();
        System.out.println("Got connection " + gotCon);
        pbSaveToDB.setEnabled(gotCon);// saver knappen
        pbDelete.setEnabled(gotCon);
        pbSearch.setEnabled(gotCon);
    }


    int rowcnt = 0;
    public void saveToDB(){
        String url = txtUrl.getText();
        rowcnt = jdbcWriter.writeLines(url, urlList);
        System.out.println("Linjer get = " + rowcnt);
        txtResult.setText("" + rowcnt);
    }

    public void deleteDB(){
        lblcount.setText("");
        String url = txtUrl.getText();
        String srch = txtResult.getText();
        int cnt = jdbcWriter.deleteRow(url, srch);
        lblcount.setText("" + cnt);
    }

    public void searchDB(){
        String url = txtUrl.getText();
        String srch = txtResult.getText();
        int cnt = jdbcWriter.searchDB(url, srch);
        lblcount.setText("" + cnt);
    }

    public void creatGui(){
        //Designer af Gui
        JPanel panelTop = new JPanel();
        JButton pbConnect = new JButton("Connect");
        JButton pbRetrievURl = new JButton("Hent url");
        pbSaveToDB = new JButton("Save to DB");
        pbSearch = new JButton("Search");
        pbDelete = new JButton("Delete");

        frame.getContentPane().add(panelTop, BorderLayout.NORTH);
        panelTop.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
        frame.add(panelTop);


        //her skabes knappen
        panelTop.add(pbConnect);
        panelTop.add(pbRetrievURl);
        panelTop.add(pbSaveToDB);
        panelTop.add(pbSearch);
        panelTop.add(pbDelete);
        pbSaveToDB.setEnabled(false);
        pbSearch.setEnabled(false);
        pbDelete.setEnabled(false);


        //Action liste
        txtUrl = new JTextField("", 50);
        txtResult = new JTextField("", 20);
        lblcount = new JLabel("-1");

        panelTop.add(txtUrl);
        panelTop.add(txtResult);
        panelTop.add(lblcount);
        pbRetrievURl.addActionListener(a -> retrievURL()); // lampda kode
        pbConnect.addActionListener(a-> connect());
        pbSaveToDB.addActionListener(a -> saveToDB());
        pbSearch.addActionListener(a -> searchDB());
        pbDelete.addActionListener(a -> deleteDB());


        //DETTE ER BUNDEN AF VORES PANEL
        JPanel pnBotton = new JPanel();
        JButton pbTest = new JButton("get lines");
        pnBotton.add(pbTest);

        Vector<String> v1 = new Vector<>();
        v1.add("Linje 1");
        v1.add("set for");
        v1.add("Sidste linje");
        lbLines = new JList(v1);
        pnBotton.add(lbLines);
        pbTest.addActionListener(a -> retrievLinesDB());

        frame.getContentPane().add(pnBotton, BorderLayout.SOUTH);

        //MAIN WINDOW
        frame.pack();
        frame.setBounds(100, 100,600,200);
        frame.setVisible(true);
    }


}
