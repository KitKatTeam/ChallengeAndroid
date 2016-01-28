package com.kitkatdev.m2dl.chanllengeandroidclm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.kitkatdev.m2dl.chanllengeandroidclm.briques.Brique;
import com.kitkatdev.m2dl.chanllengeandroidclm.briques.TimerBrique;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


// SurfaceView est une surface de dessin.
// référence : http://developer.android.com/reference/android/view/SurfaceView.html
public class MainJeu extends SurfaceView implements SurfaceHolder.Callback {

    // déclaration de l'objet définissant la boucle principale de déplacement et de rendu
    private CustomThread CustomThread;
    private List<Brique> briques;
    private Brique brique1;
    private Brique brique2;
    private Brique brique3;
    private Palette palette;
    private int nbPoints;


    private Bitmap scaled;

    public void onDraw(Canvas canvas) {
        //canvas.drawBitmap(scaled, 0, 0, null); // draw the background
    }


    // création de la surface de dessin
    public MainJeu(Context context ) {
        super(context);
        setWillNotDraw(false);
        getHolder().addCallback(this);
        CustomThread = new CustomThread(this);

        // Creation briques
        /*briques = new ArrayList<>();
        briques.add(new Brique(getContext()));
        briques.add(new Brique(getContext()));
        briques.add(new Brique(getContext()));
        briques.add(new Brique(getContext()));briques.add(new Brique(getContext()));*/


        //briques.add(new Brique(this.getContext()));


        // création d'un objet "palette", dont on définira la largeur/hauteur
        // selon la largeur ou la hauteur de l'écran
        palette = new Palette(this.getContext());
        brique1 = new Brique(this.getContext());
        nbPoints = 0;
        //brique2 = new Brique(this.getContext(),10);
        //brique3 = new Brique(this.getContext(),50);
    }

    // Fonction qui "dessine" un écran de jeu
    public void doDraw(Canvas canvas) {
        if (canvas == null) {
            return;
        }

        // on efface l'écran, en blanc
        canvas.drawColor(Color.WHITE);

        if (scaled != null){
            canvas.drawBitmap(scaled, 0, 0, null); // draw the background
        }

        // on dessine la palette
        palette.draw(canvas);
        /*for (Brique brique : briques) {
            brique.draw(canvas);
        }*/
        brique1.draw(canvas);
        //brique2.draw(canvas);brique3.draw(canvas);

    }

    // Fonction appelée par la boucle principale (CustomThread)
    // On gère ici le déplacement des objets
    public void update() {
        palette.moveWithCollisionDetection();
        boolean collision = brique1.moveWithCollisionDetection(palette);

        if(collision){
            nbPoints++;
            Toast.makeText(getContext(),"Points : "+nbPoints,Toast.LENGTH_LONG);
        }
        /*Iterator<Brique> it = briques.iterator();
        Brique currentBrique = null;
        while (it.hasNext()) {
            currentBrique = it.next();
            currentBrique.moveWithCollisionDetection();
            if(currentBrique.getEtat() == Brique.EtatBrique.OUT){
                it.remove();
            }
        }*/

        //brique2.moveWithCollisionDetection();
        //brique3.moveWithCollisionDetection();
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée immédiatement après la création de l'objet SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // création du processus CustomThread si cela n'est pas fait
        if (CustomThread.getState() == Thread.State.TERMINATED) {
            CustomThread = new CustomThread(this);
        }
        CustomThread.setRunning(true);
        CustomThread.start();


        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.fond1);
        float scale = (float) background.getHeight() / (float) getHeight();
        int newWidth = Math.round(background.getWidth() / scale);
        int newHeight = Math.round(background.getHeight() / scale);
        scaled = Bitmap.createScaledBitmap(background, newWidth, newHeight, true);
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée juste avant que l'objet ne soit détruit.
    // on tente ici de stopper le processus de CustomThread
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        CustomThread.setRunning(false);
        while (retry) {
            try {
                CustomThread.join();
                retry = false;
            } catch (InterruptedException e) {}
        }
    }

    // Gère les touchés sur l'écran
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int)event.getX();
        int currentY = (int)event.getY();

        switch (event.getAction()) {

            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN:
                // si le doigt touche la palette :
                if (currentX >= palette.getX() &&
                        currentX <= palette.getX() + palette.getPaletteW() &&
                        currentY >= palette.getY() && currentY <= palette.getY() + palette.getPaletteH()) {
                    // on arrête de déplacer la palette
                    palette.setMove(false);
                }
                break;

            // code exécuté lorsque le doight glisse sur l'écran.
            case MotionEvent.ACTION_MOVE:
                // on déplace la palette sous le doigt du joueur
                // si elle est déjà sous son doigt (oui si on a setMove à false)
                if (!palette.isMoving()) {
                    if (currentX >= palette.getMinPaletteWidth() && currentX + palette.getPaletteW()  - palette.getPaletteW() <= palette.getMaxPaletteWidth()) {
                        palette.setX(currentX - palette.getPaletteW() / 2);
                        if(currentY >= palette.getMaxPaletteHeight() - palette.getPaletteH() && currentY + palette.getPaletteH() <= palette.getMaxPaletteHeight() + palette.getPaletteW() / 2)
                        {
                            palette.setY(currentY - palette.getPaletteH() / 2);

                        }
                    }
                }
                break;

            // lorsque le doigt quitte l'écran
            case MotionEvent.ACTION_UP:
                // on reprend le déplacement de la palette
                palette.setMove(true);
        }

        return true;  // On retourne "true" pour indiquer qu'on a géré l'évènement
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée à la CREATION et MODIFICATION et ONRESUME de l'écran
    // nous obtenons ici la largeur/hauteur de l'écran en pixels
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
        palette.resize(w,h); // on définit la taille de la palette selon la taille de l'écran
        /*for (Brique brique : briques) {
            brique.resize(w,h);
        }*/
        brique1.resize(w,h);
        //brique2.resize(w,h);brique3.resize(w,h);

    }

    public void addBrique(Brique brique) {
        this.briques.add(brique);
    }
} // class MainJeu