import java.math.BigInteger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Scanner;

public class Dictionnaire {
    
    private HTNaive dico;
    
    public Dictionnaire(int m){
        this.dico = new HTNaive(m); 
    }

    public Dictionnaire(String fileName, int m){
        ListeBigI listeMots = calculeListeInt(fileName);
        this.dico = new HTNaive(listeMots, m);
        System.out.println("Temps pour h : "+ this.dico.getTimeH()+" ms"); 
        System.out.println("Temps pour contient : " + this.dico.getTimeContient()+" ms");
    }

    public Dictionnaire(String fileName, double f){
        ListeBigI listeMots = calculeListeInt(fileName);
        this.dico = new HTNaive(listeMots, f);
        System.out.println("Temps pour h : "+ this.dico.getTimeH()+ " ms"); 
        System.out.println("Temps pour contient : " + this.dico.getTimeContient()+" ms");
    }

    private static BigInteger stringToBigInteger(String s){
        BigInteger big = BigInteger.valueOf(0);
        for(int i=0;i<=s.length()-1;i++){
            int asciivalue = s.charAt(i);
            BigInteger val = BigInteger.valueOf(1);
            BigInteger asciivalueBig = BigInteger.valueOf(asciivalue);
            if(i>0){
                val = BigInteger.valueOf(256);
            }else{
                val = BigInteger.valueOf(1);
            }
            big = big.add(asciivalueBig).multiply(val);
        }
        return big;
    }

    public boolean ajout(String s){
        BigInteger motBigI = stringToBigInteger(s);
        return this.dico.ajout(motBigI);
    }

    public boolean contient(String s){
        BigInteger motBigI = stringToBigInteger(s);
        return this.dico.contient(motBigI);
    }

    public int getCardinal() {
        return this.dico.getCardinal();
    } 
    public int getMaxSize(){
        return this.dico.getMaxSize();
    }
    public int getNbListes(){
        return this.dico.getNbListes();
    } 
    public String toString(){
        return this.dico.toString();
    } 
    public String toStringV2(){
        return this.dico.toStringV2();
    } 

    public int lectureMotsTexte(String filename){
        //on suppose que fichier.txt est un fichier dans le meme dossier
        //que les .java
        File f = new File(filename);
        ListeBigI res = new ListeBigI();
        Scanner sc;
        //un scanner est un objet permettant de "scanner" (parcourir)
        //une entr??e (clavier, ou une cha??ne, ou un File, etc)
        try {
        sc = new Scanner(f);
        //ici on construit le scanner avec comme entr??e f
        //cette construction peut ??chouer (si par exemple fichier.txt n???existe pas)
        }
        catch(FileNotFoundException e){
        //si la construction ??choue, on passe ici
        System.out.println(("probl??me d???acc??s au fichier " + e.getMessage()));
        return 0;
        }
        sc.useDelimiter(" |\\n|,|;|:|\\.|!|\\?|-");
        //on d??finit les d??limiteurs comme le caract??re ???\n???, le caract??re ???,??? etc...
        //cela d??finit maintenant la notion de "morceau" comme une suite
        //de caract??res entre deux d??limiteurs
        int nbmots = 0;
        while (sc.hasNext()) { //sc.hasNext() renvoie vrai ssi
        //il reste encore un morceau ?? d??couvrir dans f
        String mot = sc.next(); //sc.next() renvoie le prochain morceau
        nbmots ++;
        }
        return nbmots;
    }
    
    public static ListeBigI calculeListeInt(String fileName){
        ListeBigI liste = new ListeBigI();
        Dictionnaire d=new Dictionnaire(1000);
        String[] listeString = d.listeMotsTexte(fileName);
        int nbMots = d.lectureMotsTexte(fileName);
        for(int i=0; i<nbMots;i++){
            liste.ajoutTete(stringToBigInteger(listeString[i]));
        }
        return liste;
    }

    private String[] listeMotsTexte(String filename){
        File f = new File(filename);
        ListeBigI res = new ListeBigI();
        String[] tabMots = new String[this.lectureMotsTexte(filename)];
        Scanner sc;
        try {
            sc = new Scanner(f);
        }
        catch(FileNotFoundException e){
            System.out.println(("probl??me d???acc??s au fichier " + e.getMessage()));
            return tabMots;
        }
        sc.useDelimiter(" |\\n|,|;|:|\\.|!|\\?|-");
        int i=0;
        while (sc.hasNext()) { 
            String mot = sc.next(); 
            tabMots[i] = mot;
            i++;
        }
        return tabMots;
    }
}
