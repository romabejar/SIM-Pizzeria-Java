package upc.edu;

import java.util.Random;

/**
 * Created by romabejar on 30/03/17.
 */
public class Recepcionista {
    public int ocupatFins; // (segons)
    Random random;

    public Recepcionista() {
        ocupatFins = -1;
        random = new Random();
    }

    public boolean estaLliure(){
        return ocupatFins == -1;
    }

    public boolean haAcabat(int segonActual){
        boolean resultat = false; // No
        if (ocupatFins == segonActual) {
            ocupatFins = -1;
            resultat = true;
        }
        return resultat;
    }


    public void agafarComanda(Character comanda, int segonActual){
        ocupatFins = segonActual + 60;
    }

}
