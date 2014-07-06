/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.graphs;

/**
 *
 * @author Martin Kramar
 */
public class GraphBuilderException extends Exception{
    public GraphBuilderException(String s){
        super(s);
    }
    
    public GraphBuilderException(String s, Exception e){
        super(s, e);
    }
    
}
