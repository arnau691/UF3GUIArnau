/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectefin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 *
 * @author arnau
 */
public class Projectefin {

    
    private static final int MAX_PILOTS = 2000;
    private static Pilotsf1[] array = new Pilotsf1[MAX_PILOTS];
    private static int opcio;
    private static File fitxer=new File("pilots.db");

    public static Pilotsf1[] getArray() {
        return array;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        inicialitzarVariables();
        do {
            demanarOpcio();
            tractarOpcio();
        } while (!opcioFinal());

    }

    public static void inicialitzarVariables() {
        
        int i=0;
        
        if(fitxer.exists()){
            boolean acabar=false;
            
            ObjectInputStream lectura=null;
            try{
                lectura=new ObjectInputStream(new BufferedInputStream(new FileInputStream(fitxer)));
                
                while(true){
                    array[i]=(Pilotsf1) lectura.readObject();
                    i++;
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.err.println("Atenció, no caben tots els objectes. Si continues pots perdre dades. Vols continuar?(S/N):");
                Scanner ent = new Scanner(System.in);
                char siNo=' ';
                do {                    
                    siNo = ent.skip("[\r\n]*").nextLine().toUpperCase().charAt(0); 
                } while (siNo != 'S' && siNo != 'N');
                if(siNo=='N') acabar=true;
                
            } catch (IOException ex) {
            } catch (ClassNotFoundException ex) {
            }finally{
                try {
                    if(lectura!=null) lectura.close();
                } catch (IOException ex) {
                }
                if(acabar) System.exit(0);
            }
        
        }
        for (; i < array.length; i++) {
            array[i] = new Pilotsf1();
            array[i].setOmplit(false);
        }
    }

    public static void demanarOpcio() {
        Scanner ent = new Scanner(System.in);

        do{
            System.out.println("\n\nMenu pilots Formula1");
            System.out.println("0. Sortir.");
            System.out.println("1. Afegir un pilot");
            System.out.println("2. Modificar un pilot");
            System.out.println("3. Borrar un pilot");
            System.out.println("4. Listar els pilots");
            System.out.println("5. Recuperar pilot borrat");
            try{
                opcio = ent.nextInt();
                break;
            }catch(java.util.InputMismatchException e){
                System.out.println("Opció incorrecta!!");
                ent.next();
                continue;
            }
        }while(true);

    }

    public static void tractarOpcio() {

        switch (opcio) {
            case 0:                             
                finalitzar();
                break;
            case 1:                             
                introduirPilot();
                break;
            case 2:                             
                modificarPilot();
                break;
            case 3:                                    
                borrarPilot();
                break;
            case 4:                                     
                llistarPilots();
                break;
            case 5:                                     
                recuperarPilot();
                break;
            default:
                System.out.println("\nOpció incorrecta!!");
        }

    }

    public static boolean opcioFinal() {
        return opcio == 0;
    }
    
    public static void finalitzar(){
        ObjectOutputStream escriptura=null;
        try{
            escriptura=new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fitxer)));

            for(int i=0;i<array.length;i++){
                if(array[i].isOmplit())escriptura.writeObject(array[i]);
            }
        } catch (IOException ex) {
            System.err.println("Error en guardar les dades!!");
        } finally{
            try {
                if(escriptura!=null) escriptura.close();
            } catch (IOException ex) {
            }

        }

        System.out.println("Adéu!!");

    }

    public static void introduirPilot() {
        Scanner ent = new Scanner(System.in);
        int i;
        for (i = 0; i < array.length && array[i].isOmplit(); i++);
        if (i < array.length) {
            System.out.println("\nNom:");
            array[i].setNom(ent.skip("[\r\n]*").nextLine());
            do{
                try{
                    System.out.println("pes:");
                    array[i].setpes(ent.nextInt());
                    break;
                }catch(java.util.InputMismatchException e){
                    System.out.println("pes incorrecte!!");
                    ent.next();
                }
            }while(true);
            do{
                try{
                    System.out.println("minut_per_volta:");                
                    array[i].setminut_per_volta(ent.nextDouble());
                    break;
                }catch(java.util.InputMismatchException e){
                    System.out.println("No és un número correcte!!");
                    ent.next();
                }
            }while(true);
            char esacciden_pilot;
            do {
                System.out.println("Te algun acciden el pilot?(S/N):");
                esacciden_pilot = ent.skip("[\r\n]*").nextLine().toUpperCase().charAt(0);
                
            } while (esacciden_pilot != 'S' && esacciden_pilot != 'N');
            array[i].setSi(esacciden_pilot == 'S'); 
            array[i].setOmplit(true);
        } else {
            System.out.println("\nNo hi caben més pilots, si vols, primer borra'n.");
        }
    }

    public static void modificarPilot() {
        Scanner ent = new Scanner(System.in);
        char siNo = 'N';
        int cont = 1, i;
        for (i = 0; i < array.length && siNo != 'S' && siNo != 'F'; i++) {
            if (array[i].isOmplit()) {
                System.out.format("\nPilot %d:\n", cont++);
                System.out.println(array[i].toString());
                do {
                    System.out.println("\nVols modificar el pilot(S/N) o finalitzar la cerca (F)?:");
                    siNo = ent.skip("[\r\n]*").nextLine().toUpperCase().charAt(0);
                } while (siNo != 'S' && siNo != 'N' && siNo != 'F');
            }
            if (siNo == 'S') {
                break;
            }
        }
        if (siNo == 'S') {

            System.out.println("\nNom: " + array[i].getNom());
            do {
                System.out.println("\nVols modificar el nom?(S/N):");
                siNo = ent.skip("[\r\n]*").nextLine().toUpperCase().charAt(0);
            } while (siNo != 'S' && siNo != 'N');
            if (siNo == 'S') {
                System.out.print("Nou nom: ");
                array[i].setNom(ent.skip("[\r\n]*").nextLine());
            }

            System.out.println("\npes: " + array[i].getpes());
            do {
                System.out.println("\nVols modificar el pes?(S/N):");
                siNo = ent.skip("[\r\n]*").nextLine().toUpperCase().charAt(0);
            } while (siNo != 'S' && siNo != 'N');
            if (siNo == 'S') {
                System.out.print("Nou pes: ");
                array[i].setpes(ent.skip("[\r\n]*").nextInt());
            }

            System.out.println("\nminut_per_volta: " + array[i].getminut_per_volta());
            do {
                System.out.println("\nVols modificar els minut_per_volta?(S/N):");
                siNo = ent.skip("[\r\n]*").nextLine().toUpperCase().charAt(0);
            } while (siNo != 'S' && siNo != 'N');
            if (siNo == 'S') {
                System.out.print("Nou minut_per_volta: ");
                array[i].setminut_per_volta(ent.skip("[\r\n]*").nextDouble());
            }

            if (array[i].isacciden_pilot()) {
                System.out.println("\nÉs Si");
            } else {
                System.out.println("\nÉs No");
            }
            do {
                System.out.println("\nVols mofidica si te un acciden?(S/N):");
                siNo = ent.skip("[\r\n]*").nextLine().toUpperCase().charAt(0);
            } while (siNo != 'S' && siNo != 'N');
            if (siNo == 'S') {
                char esacciden_pilot;
                do {
                    System.out.println("És Si o No?(S/N):");
                    esacciden_pilot = ent.skip("[\r\n]*").nextLine().toUpperCase().charAt(0);
                } while (esacciden_pilot != 'H' && esacciden_pilot != 'D');
                array[i].setSi(esacciden_pilot == 'S');     
                System.out.print("Nou gènere: ");
                if (array[i].isacciden_pilot()) {
                    System.out.println("Si");
                } else {
                    System.out.println("No");
                }
            }

            System.out.println("Pilot modificat correctament.");

        } else {
            System.out.println("\nNo hi ha pilots per modificar, o no n'has triat cap per modificar.");
        }

    }

    public static void borrarPilot() {       
        Pilotsf1 p = null;   
        Scanner ent = new Scanner(System.in);
        char siNo = 'N';
        int i;
        for (i = 0; i < array.length && siNo != 'F'; i++) {
            p = array[i];
            if (p.isOmplit()) {
                System.out.println(p);
                do {
                    System.out.println("\nVols borrar el pilot(S/N) o finalitzar la cerca (F)?:");
                    siNo = ent.skip("[\r\n]*").nextLine().toUpperCase().charAt(0); 
                } while (siNo != 'S' && siNo != 'N' && siNo != 'F');
            }
            if (siNo == 'S') {
                break;
            }
        }

        if (siNo == 'S') {
            p.setOmplit(false);
            System.out.println("Pilot borrat correctament.");

        } else {
            System.out.println("\nNo s'ha borrat cap pilot.");
        }
    }

    public static void llistarPilots() {
        Scanner ent = new Scanner(System.in);

        boolean algun = false;
        char siNo = 'S';
        int i;
        for (i = 0; i < array.length; i++) {
            Pilotsf1 p = array[i];
            if (p.isOmplit()) {
                algun = true;
                System.out.println(p);
                do {
                    System.out.println("\nVols vore més pilots(S/N)?:");
                    siNo = ent.skip("[\r\n]*").nextLine().toUpperCase().charAt(0); 
                   
                } while (siNo != 'S' && siNo != 'N');
            }
            if (siNo == 'N') {
                break;
            }
        }
        if (!algun) {
            System.out.println("\nNo hi ha pilots per mostrar, si vols, primer crea'n.");
        }
    }

    public static void recuperarPilot(){
        Scanner ent = new Scanner(System.in);
        char siNo = 'N';
        int cont = 0, i;
        for (i = 0; i < array.length && siNo != 'S' && siNo != 'F'; i++) {
            if (!array[i].isOmplit()) {
                System.out.format("\nPilot %d:\n", ++cont);
                System.out.println(array[i].toString());
                do {
                    System.out.println("\nVols recuperar el pilot(S/N) o finalitzar la cerca (F)?:");
                    siNo = ent.skip("[\r\n]*").nextLine().toUpperCase().charAt(0);
                } while (siNo != 'S' && siNo != 'N' && siNo != 'F');
            }
            if (siNo == 'S') {
                break;
            }
        }    
        if (siNo == 'S') {
            array[i].setOmplit(true);
            System.out.println("Pilot recuperat correctament.");
        } else {
            if(cont==0) System.out.println("No hi ha pilots per recuperat.");
            else System.out.println("Pilot no recuperat.");
        }
    
    }
}