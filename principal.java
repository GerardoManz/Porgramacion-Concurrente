/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prgramación.concurrente;

import java.util.Random;
       
public class PrgramaciónConcurrente extends Thread{
    private static double [] vec= new double[0000000];
    private int inicio, fin;
    
    public PrgramaciónConcurrente (int inicio, int fin){
        this.inicio = inicio;
        this.fin = fin;
    }
    
    public static void main(String [] args){
        iniciavec();
        vec_NOconcurrente();
        vec_concurrente();
    }
    
    private static void iniciavec(){
        Random rand = new Random (System.nanoTime());
        for(int i=0; i<vec.length; i++){
            vec [i]=rand.nextInt();
        }
    }
    
    private static void vec_NOconcurrente(){
        double tiempo = System.nanoTime();
        for(int i=0; i<vec.length; i++){
            vec [i] /= 10;
            vec [i] *= 10;
            vec [i] /= 10;
        }
        System.out.println("Versión NO concurrente: " + ((System.nanoTime()-tiempo)/1000000)+ " milisegundos.");
    }
    
    public void run(){
        for(int i=inicio; i<fin; i++){
            vec [i] /= 10;
            vec [i] *= 10;
            vec [i] /= 10;
        }
    }
    
    private static void vec_concurrente(){
        int nproc = Runtime.getRuntime().availableProcessors();
        int inicio = 0, fin = 0;
        PrgramaciónConcurrente [] prin = new PrgramaciónConcurrente[nproc];
        
        double tiempo = System.nanoTime();
        
        for(int i = 0; i<nproc; i++){
            inicio=fin;
            fin += vec.length/nproc;
            prin[i] = new PrgramaciónConcurrente(inicio, fin);
            prin[i].start();
        }
        
        for(int i=0; i<nproc; i++){
            try{
                prin[i].join();
            }catch(Exception e){}
        }
        System.out.println("Versión concurrente: " + ((System.nanoTime() - tiempo) / 1000000) + " milisegundos.");
    }
}
