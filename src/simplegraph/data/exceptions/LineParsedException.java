/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.data.exceptions;

import java.util.Date;

/**
 * It raises when string line cannot be parsed to {@link Date} and {@link Double}s
 * @author Martin Kramar
 */
public class LineParsedException extends Exception{

    private String line;
    public LineParsedException(String line, Exception e) {
        super("Parsing of :"+line+" failed", e);
    }
    
}
