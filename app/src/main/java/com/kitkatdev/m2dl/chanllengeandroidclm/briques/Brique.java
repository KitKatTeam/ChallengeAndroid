package com.kitkatdev.m2dl.chanllengeandroidclm.briques;

/**
 * Created by loicleger on 28/01/16.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.kitkatdev.m2dl.chanllengeandroidclm.Palette;
import com.kitkatdev.m2dl.chanllengeandroidclm.R;

import java.util.Random;

public class Brique
{
    public enum EtatBrique {
        MOVING, OUT
    }

    public enum VitesseBrique {
        LOW, MEDIUM, HIGH, NIGHTMARE;

        public int getValue() {
            switch (this){
                case LOW:
                    return 5;
                case MEDIUM:
                    return 10;
                case HIGH:
                    return 15;
                case NIGHTMARE:
                    return 30;
                default :
                    return 5;
            }
        }
    }

    private static int HAUTEUR_PALETTE = 10;
    private BitmapDrawable img=null; // image de la balle
    private int x,y; // coordonnées x,y de la balle en pixel
    private int balleW, balleH; // largeur et hauteur de la balle en pixels
    private int wEcran,hEcran; // largeur et hauteur de l'écran en pixels
    private boolean move = true; // 'true' si la balle doit se déplacer automatiquement, 'false' sinon
    private EtatBrique etat;
    private VitesseBrique vitesseBrique;
    private int sens;


    // pour déplacer la balle on ajoutera INCREMENT à ses coordonnées x et y
    private static final int INCREMENT = 10;
    private int speedX=INCREMENT, speedY=INCREMENT;

    // contexte de l'application Android
    // il servira à accéder aux ressources, dont l'image de la balle
    private final Context mContext;

    // Constructeur de l'objet "Balle"
    public Brique(final Context c, int sens)
    {
        x = 0;
        y = 0;
        mContext=c; // sauvegarde du contexte
        etat = EtatBrique.MOVING;
        int random = new Random().nextInt(VitesseBrique.values().length);
        switch (random) {
            case 0:
                vitesseBrique = VitesseBrique.LOW;
                break;
            case 1:
                vitesseBrique = VitesseBrique.MEDIUM;
                break;
            case 2:
                vitesseBrique = VitesseBrique.HIGH;
                break;
            case 3:
                vitesseBrique = VitesseBrique.NIGHTMARE;
                break;
        }

        sens = sens;
    }
    // on attribue à l'objet "Balle" l'image passée en paramètre
    // w et h sont sa largeur et hauteur définis en pixels
    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h)
    {
        Drawable dr = c.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    // retourne 'true' si la balle se déplace automatiquement
    // 'false' sinon
    // car on la bloque sous le doigt du joueur lorsqu'il la déplace
    public boolean isMoving() {
        return move;
    }

    // définit si oui ou non la balle doit se déplacer automatiquement
    // car on la bloque sous le doigt du joueur lorsqu'il la déplace
    public void setMove(boolean move) {
        this.move = move;
    }

    // redimensionnement de l'image selon la largeur/hauteur de l'écran passés en paramètre
    public void resize(int wScreen, int hScreen) {
        // wScreen et hScreen sont la largeur et la hauteur de l'écran en pixel
        // on sauve ces informations en variable globale, car elles serviront
        // à détecter les collisions sur les bords de l'écran
        wEcran=wScreen;
        hEcran=hScreen;

        /*if(wEcran != 0) {
            x = 1 + new Random().nextInt(wEcran);
        }*/
        //y=hEcran; // position de départ
        if(sens == 0) {
            x= new Random().nextInt(wScreen);
        } else {
            y = new Random().nextInt(hScreen);
        }



        // on définit (au choix) la taille de la balle à 1/5ème de la largeur de l'écran
        balleW=wScreen/5;
        balleH=wScreen/5;
        img = setImage(mContext, R.drawable.brick,balleW,balleH);
    }

    // définit la coordonnée X de la balle
    public void setX(int x) {
        this.x = x;
    }

    // définit la coordonnée Y de la balle
    public void setY(int y) {
        this.y = y;
    }

    // retourne la coordonnée X de la balle
    public int getX() {
        return x;
    }

    // retourne la coordonnée Y de la balle
    public int getY() {
        return y;
    }

    // retourne la largeur de la balle en pixel
    public int getBalleW() {
        return balleW;
    }

    // retourne la hauteur de la balle en pixel
    public int getBalleH() {
        return balleH;
    }

    public EtatBrique getEtat() {
        return etat;
    }

    public void setEtat(EtatBrique etat) {
        this.etat = etat;
    }

    public int getSens() {
        return sens;
    }

    public void setSens(int sens) {
        this.sens = sens;
    }

    // déplace la balle en détectant les collisions avec les bords de l'écran
    public boolean moveWithCollisionDetection(Palette palette)
    {

        // on incrémente les coordonnées Y
        if(sens == 0){
            y+=vitesseBrique.getValue();
        } else {
            x += vitesseBrique.getValue();
        }

        Rect r1 = new Rect(200,200,200,200);
        Rect r2 = new Rect(250,200,200,200);
        if(r1.intersect(r2)) {
            Log.d("eee","ddd");
        }

        Boolean b = false;
        // si y dépasse la hauteur l'écran, on inverse le déplacement
        if(sens == 0){
            if(y+balleH >= hEcran - HAUTEUR_PALETTE) {
                Rect briqueRect = new Rect(x, y, x + balleW, y + balleH);
                Rect paletteRect = new Rect(palette.getX(), palette.getY(), palette.getX()+palette.getPaletteW(), palette.getY()+palette.getPaletteH());
                if(paletteRect.intersect(briqueRect)) {
                    b = true;
                }
                this.etat = EtatBrique.OUT;
            }
        } else {
            if(x+balleW >= wEcran - HAUTEUR_PALETTE) {
                Rect briqueRect = new Rect(x, y, x + balleW, y + balleH);
                Rect paletteRect = new Rect(palette.getX(), palette.getY(), palette.getX()+palette.getPaletteW(), palette.getY()+palette.getPaletteH());
                if(paletteRect.intersect(briqueRect)) {
                    b = true;
                }
                this.etat = EtatBrique.OUT;
            }
        }


        return b;
    }

    // on dessine la balle, en x et y
    public void draw(Canvas canvas)
    {
        if(img==null) {return;}
        canvas.drawBitmap(img.getBitmap(), x, y, null);
    }

    public int getPoints(){
        return vitesseBrique.getValue();
    }

}