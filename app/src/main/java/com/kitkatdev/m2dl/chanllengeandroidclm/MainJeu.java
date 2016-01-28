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

import com.kitkatdev.m2dl.chanllengeandroidclm.briques.Brique;
import com.kitkatdev.m2dl.chanllengeandroidclm.briques.TimerBrique;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


// SurfaceView est une surface de dessin.
// référence : http://developer.android.com/reference/android/view/SurfaceView.html
public class MainJeu extends SurfaceView implements SurfaceHolder.Callback {

    // déclaration de l'objet définissant la boucle principale de déplacement et de rendu
    private CustomThread CustomThread;
    private List<Brique> briques;
    private Palette palette;
    private Integer cpt = 0;


    private Bitmap scaled;


    // création de la surface de dessin
    public MainJeu(Context context ) {
        super(context);
        setWillNotDraw(false);
        getHolder().addCallback(this);
        CustomThread = new CustomThread(this);

        // Creation briques
        briques = new ArrayList<>();
        briques.add(new Brique(getContext()));
        briques.add(new Brique(getContext()));
        briques.add(new Brique(getContext()));
        briques.add(new Brique(getContext()));briques.add(new Brique(getContext()));

        //briques.add(new Brique(this.getContext()));


        // création d'un objet "palette", dont on définira la largeur/hauteur
        // selon la largeur ou la hauteur de l'écran
        palette = new Palette(this.getContext());
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
        for (Brique brique : briques) {
            brique.draw(canvas);
        }
    }

    // Fonction appelée par la boucle principale (CustomThread)
    // On gère ici le déplacement des objets
    public void update() {
        palette.moveWithCollisionDetection();
        Iterator<Brique> it = briques.iterator();
        Brique currentBrique = null;
        while (it.hasNext()) {
            currentBrique = it.next();
            currentBrique.moveWithCollisionDetection();
            if(currentBrique.getEtat() == Brique.EtatBrique.OUT){
                it.remove();
            }
        }
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

        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.font1);
        scaled = Bitmap.createScaledBitmap(background, palette.getMaxPaletteWidth(), palette.getMaxPaletteHeight(), true);


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                changeFont();
            }
        }, 0, 3000);

    }


    public void changeFont(){
        cpt++;
        Bitmap background = null;
        if (cpt % 3 == 0){
            background = BitmapFactory.decodeResource(getResources(), R.drawable.font1);

        }
        if (cpt % 3 == 1){
            background = BitmapFactory.decodeResource(getResources(), R.drawable.font2);
        }
        if (cpt % 3 == 2){
            background = BitmapFactory.decodeResource(getResources(), R.drawable.font3);
        }

        scaled = Bitmap.createScaledBitmap(background, palette.getMaxPaletteWidth(), palette.getMaxPaletteHeight(), true);

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
        for (Brique brique : briques) {
            brique.resize(w,h);
        }
    }

    public void addBrique(Brique brique) {
        this.briques.add(brique);
    }
} // class MainJeu