/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousemove;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import static java.lang.System.exit;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m
 */
public class MouseMove {

    // X offsets for epoch safe keypad
    public static final double cx1 = 0.4585;
    public static final double cx2 = 0.5015;
    public static final double cx3 = 0.5438;
    // Y offsets
    public static final double cy1 = 0.4222;
    public static final double cy2 = 0.4653;
    public static final double cy3 = 0.5125;
    public static final double cy4 = 0.5584;
 
    public static class Point {
        protected int x;
        protected int y;
        
        public Point() { x = 0; y = 0; }
        
        public Point(int constrx, int constry) {
            x = constrx;
            y = constry;
        }
        
        public int getx() {return this.x; }
        public int gety() {return this.y; }
        
        public void setx (int px) {this.x = px; }
        public void sety (int py) {this.y = py; }
        
        @Override
        public String toString() {
//            return super.toString(); //To change body of generated methods, choose Tools | Templates.
            return this.x+","+this.y; //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    public static class Key {
        protected double cx = -1;
        protected double cy = -1;
        protected int resx = -1;
        protected int resy = -1;
        
        public Key(double icx, double icy, int iresx, int iresy) {
            this.cx = icx;
            this.cy = icy;
            this.resx = iresx;
            this.resy = iresy;
        }
        
        public int getx() {
            return (int)(resx*cx);
        }
        public int gety() {
            return (int)(resy * cy);
        }
    }
    
    /* Epoch safe keypad:
    1 2 3
    4 5 6
    7 8 9
    * 0 #
    */
    public static Point epochkeypad (int c, int xres, int yres) {
        Point pResult = new Point();
        switch(c) {
            case 1:   pResult.setx((int)(xres*cx1));  pResult.sety((int)(yres*cy1));  break;
            case 2:   pResult.setx((int)(xres*cx2));  pResult.sety((int)(yres*cy1));  break;
            case 3:   pResult.setx((int)(xres*cx3));  pResult.sety((int)(yres*cy1));  break;
            case 4:   pResult.setx((int)(xres*cx1));  pResult.sety((int)(yres*cy2));  break;
            case 5:   pResult.setx((int)(xres*cx2));  pResult.sety((int)(yres*cy2));  break;
            case 6:   pResult.setx((int)(xres*cx3));  pResult.sety((int)(yres*cy2));  break;
            case 7:   pResult.setx((int)(xres*cx1));  pResult.sety((int)(yres*cy3));  break;
            case 8:   pResult.setx((int)(xres*cx2));  pResult.sety((int)(yres*cy3));  break;
            case 9:   pResult.setx((int)(xres*cx3));  pResult.sety((int)(yres*cy3));  break;
            case 10:  pResult.setx((int)(xres*cx1));  pResult.sety((int)(yres*cy4));  break;    // *
            case 0:   pResult.setx((int)(xres*cx2));  pResult.sety((int)(yres*cy4));  break;
            case 11:  pResult.setx((int)(xres*cx3));  pResult.sety((int)(yres*cy4));  break;    // #
        }
        return pResult;
    }
    
    public static void pressKey(int key, int xres, int yres, int delayms) throws AWTException, InterruptedException {
        Robot rp = new Robot();
        rp.mouseMove(epochkeypad(key, xres, yres).getx(), epochkeypad(key, xres, yres).gety()); 
        sleep(delayms);
    }
        
    public static void brute(int start, int end, int xres, int yres) throws AWTException, InterruptedException {
        int t,h,z,e;
        // int keyCode = ke.getKeyCode();
        
        Robot r = new Robot();
        
        for (int i = start; i <= end; i++)  {
            PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();
            int xLoc = (int) b.getX();
            int yLoc = (int) b.getY();
            if ((xLoc < 100) && (yLoc < 100)) break;
            
            e = (i / 1)     % 10;
            z = (i / 10)    % 10;
            h = (i / 100)   % 10;
            t = (i / 1000)  % 10;
            
            // timing is not finished yet
            System.out.println("bruting:  "+ t + " " + h + " " + z + " " + e + "  MousePos is " + xLoc + "," +yLoc + "  - move cursor to top left corner (x<100, y<100)to abort ...");
            pressKey(t, xres, yres, 1);
            pressKey(h, xres, yres, 1);
            pressKey(z, xres, yres, 1);
            pressKey(e, xres, yres, 1);
            pressKey(11, xres, yres, 1000);    // press # to confirm combination and wait until player entered
            
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, AWTException {
            boolean DEBUG = true;
        
            Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
            int x = resolution.width;
            int y = resolution.height;

            Point res = new Point(x,y);            
            Robot r1 = new Robot();
            
            if(DEBUG) {
                System.out.println("Screen size is " + res.getx() + "x" + res.gety());
                for(int m=0; m<10; m++) {
                    //char c = (char)('0' + m); //cast int 0-9 tp char 0-9 
                    System.out.println(m+"(x,y) is: "+epochkeypad(m, x, y));
                    //r1.mouseMove(epochkeypad(m, x, y).getx(), epochkeypad(m, x, y).gety()); 
                    //sl    
                    sleep(10);
                }
                System.out.println("*(x,y) is: " + epochkeypad(10, x, y));
                System.out.println("#(x,y) is: " + epochkeypad(11, x, y));
            }
            // PointerInfo a = MouseInfo.getPointerInfo();
            brute(0, 9999, x, y);
    }
}
