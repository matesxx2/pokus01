/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pokusgithub;

import java.awt.BorderLayout;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import simpledraw.GraphPane;
import simpledraw.drawable.Point;
import simplegraph.base.Dataset;
import simplegraph.base.Graph;
import simplegraph.base.GraphSettings;
import simplegraph.base.TimeLevel;
import simplegraph.graphs.GraphBuilder;

/**
 *
 * @author kramarm
 */
public class Main {
    public static void main(String[] args){
        //test1();
        //test2();
        test3();
    }
    
    public static void test1(){
        GraphPane gp = new GraphPane(200, 200);
        gp.addDrawable(Point.createPointCrossMark(20, 20, 30));
        gp.addDrawable(Point.createPointPlusMark(720, 720, 50));
        gp.addDrawable(Point.createPointCrossMark(400, 400, 600));
        
        
        JFrame frame = new JFrame("TEST 1");
        frame.getContentPane().add(gp.getDrawingArea(),BorderLayout.CENTER);
        frame.getContentPane().add(new JLabel("VOLE"),BorderLayout.SOUTH);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        gp.resizeDrawingArea(1000, 1000);
        
    }
    
    public static void test2(){
        System.out.println((float)5/3*6);
    }
    
    public static void test3(){
        Date start = new Date(System.currentTimeMillis() - 10*60*1000);//-10min
        Date end = new Date(System.currentTimeMillis());
        Date step = new Date(30000);
        Date[] dates = createDates(start, end, step);
        System.out.println("Dates:" + dates.length);
        
        double data[] = createRandomNumbers(1.0, 10.5, dates.length);
        System.out.println("Numbers:" + data.length);
        
        
        Dataset d =  new Dataset(dates, data);
        GraphSettings gs = new GraphSettings();
        gs.setNumOfDislplayedUnits(5);
        gs.setTimeLevel(TimeLevel.MINUTE);
        Graph g = GraphBuilder.createTestGraph(d, gs);
        
        
        g.setVerticalLabels(new String[]{"jedna","dva","tri"});
        g.setHorizontalLabels(new String[]{"1","2","3","4"});
        
        
        
        JFrame frame = new JFrame("TEST 1");
        frame.getContentPane().add(g.getDrawingArea(),BorderLayout.CENTER);
        frame.getContentPane().add(new JLabel("VOLE"),BorderLayout.SOUTH);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        //g.resizeDrawingArea(1000, 1000);
    }
    
    public static Date[] createDates(Date start, Date end, Date stepSize){
        Calendar c1 = createCalendarFromDate(start);
        Calendar c2 = createCalendarFromDate(end);
        Calendar step = createCalendarFromDate(stepSize);
        
        int count = 0;
        while(c1.before(c2)){
            count++;
            c1.setTimeInMillis(c1.getTimeInMillis()+step.getTimeInMillis());
        }
        
        c1 = createCalendarFromDate(start);
        
        Date dates[] = new Date[count];
        for(int i=0; i<count; i++){
            dates[i] = new Date(c1.getTimeInMillis());
            c1.setTimeInMillis(c1.getTimeInMillis()+step.getTimeInMillis());
        }
        
        return dates;
    }
    
    public static double[] createRandomNumbers(double min, double max, int count){
        double numbers[] = new double[count];
        for(int i=0; i<numbers.length; i++)
            numbers[i]=(max-min)*Math.random()+min;
        
        return numbers;
    }
    
    public static Calendar createCalendarFromDate(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }
    
}
