package com.kitkatdev.m2dl.chanllengeandroidclm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;

import com.kitkatdev.m2dl.chanllengeandroidclm.briques.Brique;

public class Palette
{
    private Drawable img = null; // image de la balle
    private int x, y; // coordonnées x,y de la balle en pixel
    private int paletteW, paletteH; // largeur et hauteur de la balle en pixels
    private int wEcran, hEcran; // largeur et hauteur de l'écran en pixels
    private boolean move = true; // 'true' si la balle doit se déplacer automatiquement, 'false' sinon


    private int maxPaletteWidth = 0, minPaletteWidth = 0;
    private int maxPaletteHeight = 0, minPaletteHeight = 0;

    // pour déplacer la balle on ajoutera INCREMENT à ses coordonnées x et y
    private static final int INCREMENT = 5;
    private int speedX=INCREMENT, speedY=INCREMENT;

    // contexte de l'application Android
    // il servira à accéder aux ressources, dont l'image de la balle
    private final Context mContext;

    // Constructeur de l'objet "Balle"
    public Palette(final Context c)
    {
        Display display = WindowManagerInstancier.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        maxPaletteWidth = width;
        maxPaletteHeight = height;
        paletteW =width/5;
        paletteH =height/10;

        x = width / 2 - paletteW / 2;
        y = height - 10 - paletteH; // position de départ
        mContext=c; // sauvegarde du contexte
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
        this.move = false;
    }

    // redimensionnement de l'image selon la largeur/hauteur de l'écran passés en paramètre
    public void resize(int wScreen, int hScreen) {
        // wScreen et hScreen sont la largeur et la hauteur de l'écran en pixel
        // on sauve ces informations en variable globale, car elles serviront
        // à détecter les collisions sur les bords de l'écran
        wEcran=wScreen;
        hEcran=hScreen;
        maxPaletteWidth = wEcran;
        maxPaletteHeight = hEcran;

        // on définit (au choix) la taille de la balle à 1/5ème de la largeur de l'écran
        paletteW =wScreen/5;
        paletteH =wScreen/10;
        img = setImage(mContext,R.drawable.palette, paletteW, paletteH);
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
    public int getPaletteW() {
        return paletteW;
    }

    // retourne la hauteur de la balle en pixel
    public int getPaletteH() {
        return paletteH;
    }

    // déplace la balle en détectant les collisions avec les bords de l'écran
    public void moveWithCollisionDetection()
    {
        // si on ne doit pas déplacer la balle (lorsqu'elle est sous le doigt du joueur)
        // on quitte
        if(!move) {return;}

        // on incrémente les coordonnées X et Y
        x += speedX;
        y += speedY;

        // si x dépasse la largeur de l'écran, on inverse le déplacement
        if(x+ paletteW > wEcran) {speedX=-INCREMENT;}

        // si y dépasse la hauteur l'écran, on inverse le déplacement
        if(y+ paletteH > hEcran) {speedY=-INCREMENT;}

        // si x passe à gauche de l'écran, on inverse le déplacement
        if(x<0) {speedX=INCREMENT;}

        // si y passe à dessus de l'écran, on inverse le déplacement
        if(y<0) {speedY=INCREMENT;}
    }

    // on dessine la balle, en x et y
    public void draw(Canvas canvas)
    {
        if(img==null) {return;}
        canvas.drawBitmap(((BitmapDrawable) img).getBitmap(), x, y, null);
    }

    public int getMaxPaletteWidth() {
        return maxPaletteWidth;
    }

    public void setMaxPaletteWidth(int maxPaletteWidth) {
        this.maxPaletteWidth = maxPaletteWidth;
    }

    public int getMinPaletteWidth() {
        return minPaletteWidth;
    }

    public void setMinPaletteWidth(int minPaletteWidth) {
        this.minPaletteWidth = minPaletteWidth;
    }

    public int getMaxPaletteHeight() {
        return maxPaletteHeight;
    }

    public void setMaxPaletteHeight(int maxPaletteHeight) {
        this.maxPaletteHeight = maxPaletteHeight;
    }

    public int getMinPaletteHeight() {
        return minPaletteHeight;
    }

    public void setMinPaletteHeight(int minPaletteHeight) {
        this.minPaletteHeight = minPaletteHeight;
    }

} // public class Balle