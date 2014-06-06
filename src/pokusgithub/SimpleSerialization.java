/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pokusgithub;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Collection;
import java.util.HashMap;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kramarm
 */
public class SimpleSerialization {
    public static void main(String[] args){
        doSave();
        doLoad();
    }
    
    private static void printMap(HashMap map){
        //pridan cvicny komentar...
        System.out.println("Obsah mapy:");
        Collection<Object> col = map.values();
        Iterator iterator = col.iterator();
        while(iterator.hasNext()){
            System.out.println("objekt:" + iterator.next());
        }
    }
    
    private static void doSave(){
        System.out.println("...doSave()...");
        
        System.out.println("create HashMap object...");
        HashMap<String,Object> map = new HashMap<>();
        
        System.out.println("insert:" + "NAZDAR," + "10 and " + Math.PI);
        map.put("string", "NAZDAR");
        map.put("int", 10);
        map.put("double", Math.PI);
        
        printMap(map);
        
        System.out.println("save to example.obj");
        try {
            FileOutputStream fos = new FileOutputStream("example.obj");
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(map);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimpleSerialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SimpleSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void doLoad(){
        System.out.println("...doLoad()...");
        try {
            FileInputStream fis = new FileInputStream("example.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            System.out.println("reading and converting object to map...");
            HashMap<String,Object> map = (HashMap<String, Object>)ois.readObject();
            printMap(map);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimpleSerialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SimpleSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
