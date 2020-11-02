package demo;

import java.util.ArrayList;

public class Main {


    public static void main (String[] args){
        ArrayList<String> lst = new ArrayList<>();
        // write your code here
        String url = "https://www.dr.dk/";
        url = "https://ekstrabladet.dk/";
        url = "https://www.bt.dk/";
        url = "https://tv2.dk/";
        url = "https://www.foxnews.com/";
        url = "https://www.nytimes.com/";

        URLGui urlgui = new URLGui();
        urlgui.creatGui();

        /*
        JDBCWriter jw = new JDBCWriter();
        boolean gotcon = jw.setconnection();
        System.out.println("Got connection=" + gotcon);
        int ires = jw.writeLines(url, lst);
        System.out.println("Gemt antal linjer =" + ires);
         */


    }





    public void mainTo(){

        /*
        https://www.jobindex.dk/jobsoegning/storkoebenhavn?page=2&q=java

        //læs alle sider fra jobnet med dette loop
        for (int i = 0; i < 10 ; i++) {
            url = "https://www.jobindex.dk/jobsoegning/storkoebenhavn?page=" + i + "&q=java";
          }
         */




        // gemt i database!!
        String url = "";
        URLReader uread = new URLReader();
        ArrayList<String> lst = uread.readUrl(url);

        ArrayList<String> lst2 = new ArrayList<>();
        for (String line: lst){
            String[] strArr;
            if(line.length() > 15000){
                strArr = line.split("<");
                for (String ss: strArr){
                    lst2.add(ss);
                }
            }
        }

        lst.addAll(lst2);

        System.out.println("lst2 size = " + lst2.size());
        System.out.println("Læst list = " + lst.size());

        JDBCWriter jw = new JDBCWriter();
        boolean gotcon = jw.setconnection();
        System.out.println("Got connection = " + gotcon);
        /*
        int ires = jw.writeLines(url, lst);
        System.out.println("gæmt antal linjer: " + ires);

            for (String line: lst){
            System.out.println(line);
            }
       */
    }
}
