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

import com.kitkatdev.m2dl.chanllengeandroidclm.Palette;
import com.kitkatdev.m2dl.chanllengeandroidclm.R;

import java.util.Random;

public class Brique
{
    public enum EtatBrique {
        MOVING, OUT
    }

    private static int HAUTEUR_PALETTE = 10;
    private BitmapDrawable img=null; // image de la balle
    private int x,y; // coordonnées x,y de la balle en pixel
    private int balleW, balleH; // largeur et hauteur de la balle en pixels
    private int wEcran,hEcran; // largeur et hauteur de l'écran en pixels
    private boolean move = true; // 'true' si la balle doit se déplacer automatiquement, 'false' sinon
    private EtatBrique etat;


    // pour déplacer la balle on ajoutera INCREMENT à ses coordonnées x et y
    private static final int INCREMENT = 10;
    private int speedX=INCREMENT, speedY=INCREMENT;

    // contexte de l'application Android
    // il servira à accéder aux ressources, dont l'image de la balle
    private final Context mContext;

    // Constructeur de l'objet "Balle"
    public Brique(final Context c)
    {
        x = 0;
        y = 0;
        mContext=c; // sauvegarde du contexte
        etat = EtatBrique.MOVING;
    }

    public Brique(final Context c,int x)
    {
        this.x = x;
        y =1;
        mContext=c; // sauvegarde du contexte
        etat = EtatBrique.MOVING;
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

        x= new Random().nextInt(wScreen);

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

    // déplace la balle en détectant les collisions avec les bords de l'écran
    public boolean moveWithCollisionDetection(Palette palette)
    {

        // on incrémente les coordonnées Y
        y+=speedY;


        // si y dépasse la hauteur l'écran, on inverse le déplacement
        if(y+balleH >= hEcran - HAUTEUR_PALETTE) {
            Rect briqueRect = new Rect(x, y, balleW, balleH);
            Rect paletteRect = new Rect(palette.getX(), palette.getY(), palette.getPaletteW(), palette.getPaletteH());
            if(paletteRect.intersect(briqueRect)) {
                return true;
            }
            y=0;
            if(wEcran != 0) {
                x= Math.min(new Random().nextInt(wEcran),wEcran-balleW);
                this.etat = EtatBrique.OUT;
            } else
            {
                x = new Random().nextInt(200);
            }
        }
        return false;
    }

    // on dessine la balle, en x et y
    public void draw(Canvas canvas)
    {
        if(img==null) {return;}
        canvas.drawBitmap(img.getBitmap(), x, y, null);
    }

}