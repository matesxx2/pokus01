/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pokusgithub;

import java.util.StringTokenizer;

/**
 *
 * @author petr
 */
public class PokusGitHub {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("NAZDAR");
        String s1[] = new String[]{"jedna","dva","tri"};
        System.out.println("s1:" + java.util.Arrays.toString(s1));
        
        String s2 = "123,11.11,,32";
        
        StringTokenizer st = new StringTokenizer(s2,",");
        System.out.println("s2:" + s2.substring(2, s2.length()));
        System.out.println("count of tokens:" + st.countTokens());
        while(st.hasMoreElements())
            System.out.println("token:" + st.nextElement());
        
        String s3 = "\"NAZDAR,VOLE\",\"NAZDAR\",\"HNUPE\"";
        System.out.println("s3:" + s3);
        
        String s3Split[] = s3.split("\",\"");
        for (String string : s3Split) {
            System.out.println("split:" + string);
        }
    }
    
}
