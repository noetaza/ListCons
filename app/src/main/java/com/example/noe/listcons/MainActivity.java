package com.example.noe.listcons;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    protected int k;
    protected int[] color={Color.BLUE,Color.RED,Color.BLACK, Color.GREEN, Color.YELLOW, Color.WHITE, Color.CYAN};

    private boolean selected1, selected2;
    private float x, y , r;
    private float h, w;
    private Drawable drawable1, drawable2;
    private ImageView ai, pik;
    private Random random;
    private float  xpos, ypos;
    private int modificarX = 100;
    private int modificarY = 100;

    private android.widget.RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        w = size.x;
        h = size.y;
        r = 27;
        random =  new Random();

        setContentView(R.layout.activity_main);
        ai = (ImageView) findViewById(R.id.androidicon);
        ai.setVisibility(View.INVISIBLE);

        pik = (ImageView) findViewById(R.id.pikachu);
        pik.setX(w/2 - 256/2);
        pik.setY(h/2 - 256/2);
        pik.setVisibility(View.INVISIBLE);

        final ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.lay);
        final Button btn1 = (Button) findViewById(R.id.btn1);
        final Button btn2 = (Button) findViewById(R.id.btn2);
        final Button btn3 = (Button) findViewById(R.id.btn3);

        selected1 = false;
        selected2 = false;

        drawable1 = btn2.getBackground();
        drawable2 = btn2.getBackground();

        //Cambio de color al boton
        btn1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(v.getContext(), "Cambio de color", Toast.LENGTH_SHORT).show();

                btn1.setBackgroundColor(color[k%color.length]);
                k++;
                return true;
            }
        });

        //Cambio de Color a las letras del boton y la pantalla
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setTextColor(color[k%color.length]);
                cl.setBackgroundColor(color[k%color.length]);
                k++;
            }
        });

        //Coordenadas de la pantalla
        /*cl.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x1,y1;
                x1 = event.getX();
                y1 = event.getY();
                Toast.makeText(v.getContext(), "Coordenada: " + x1 + ", "+ y1 ,
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });*/

        cl.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch (View v, MotionEvent e) {
                x = e.getX(); y = e.getY();
                Log.d("a","Width : "+x+" Height"+y);
                if(selected1) {
                    if(Math.sqrt(((xpos+27)- x)*((xpos+27)- x)+((ypos+27)-y)*((ypos+27)-y)) < r){
                        int xx = random.nextInt(Math.round(w-r));
                        int yy =  random.nextInt(Math.round(h-r));
                        ai.setX(xx); ai.setY(yy);
                        xpos = ai.getX();
                        ypos = ai.getY();
                        Log.d("a","Touch x: "+xpos+" Touch y: "+ypos);
                        Log.d("a","Image x: "+x+" Image y: "+y);

                    }
                }
                return false;
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                selected1 = !selected1;
                if(selected1) {
                    btn2.setBackgroundColor(Color.CYAN);
                    ai.setVisibility(View.VISIBLE);
                }
                else {
                    btn2.setBackground(drawable1);
                    ai.setVisibility(View.INVISIBLE);
                }
            }
        });

        //Deslizar la imagen
        btn3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                selected2 = !selected2;
                if(selected2) {
                    btn3.setBackgroundColor(Color.GREEN);
                    pik.setVisibility(View.VISIBLE);
                }

                else {
                    btn3.setBackground(drawable2);
                    pik.setVisibility(View.INVISIBLE);
                }
            }
        });

        pik.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PointF DownPT = new PointF();
                PointF StartPT = new PointF();
                int eid = event.getAction();

                switch (eid) {
                    case MotionEvent.ACTION_MOVE :
                        StartPT = new PointF( v.getX(), v.getY() );
                        //Desplazamiento
                        PointF mv = new PointF( event.getX() - DownPT.x, event.getY() - DownPT.y);

                        v.setX((StartPT.x+mv.x) - modificarX);
                        v.setY((StartPT.y+mv.y) - modificarY);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        //Guardamos la posicion inicial
                        DownPT.x = event.getX();
                        DownPT.y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP :
                        break;
                    default :
                        break;
                }
                return true;
            }
        });

    }
}
