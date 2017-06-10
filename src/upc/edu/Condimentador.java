package upc.edu;

import java.util.Random;

/**
 * Created by romabejar on 30/03/17.
 */
public class Condimentador {

    public Character comanda; // 'T' trucada , 'C' cua
    public int ocupatFins; // (segons)
    private Random random;

    public Condimentador() {
        comanda = null;
        ocupatFins = -1;
        random = new Random();
    }

    public boolean estaLliure(){
        return ocupatFins == -1;
    }

    public boolean haAcabat(int segonActual){
        boolean resultat = false;
        if (ocupatFins == segonActual) {
            ocupatFins = -1;
            resultat = true;
        }
        return resultat;
    }

    public char getComanda(){
        return comanda;
    }

    public void agafarComanda(Character comanda, int segonActual){
        this.comanda = comanda;
        ocupatFins = segonActual + 2*60+random.nextInt()%(60) ; // CONDIMENTADORS: [2.5,0.5] minuts
    }

}
