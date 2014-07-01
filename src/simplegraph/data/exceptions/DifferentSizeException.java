/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.data.exceptions;

import java.util.Arrays;

/**
 * Used when two arrays has different size 
 * @author Martin Kramar
 */
public class DifferentSizeException extends Exception {
    
    Object[] o1;
    Object[] o2;
    
    public DifferentSizeException(String s) {
        super(s);
        o1 = null;
        o2 = null;
    }
    
    public DifferentSizeException(Object[] o1, Object[] o2){
        super("Objects o1 and o2 have different dimension:" 
                + "dim o1:" + o1.length + "(o1=" + Arrays.toString(o1) + ")\n" 
                + "dim o2:" + o2.length + "(o2=" + Arrays.toString(o2) + ")");
        this.o1 = o1;
        this.o2 = o2;
    }

    public Object[] getO1() {
        return o1;
    }

    public Object[] getO2() {
        return o2;
    }
    
    
}
