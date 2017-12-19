package com.example.loren.ejercicionotificaciones;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayAdapter adapter;
    private ListView list;

    private NotificationManager notifyMgr;
    private boolean segundaVez = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtener lista

        list = (ListView) findViewById(R.id.lista);

        // Crear contenido de los items
        String items[] = {
                "Notificación Simple",
                "Notificación + Acción",
                "Notificación + Actualización",
                "Notificación + Aviso",
                "Notificación + Progreso",
                "Notificación Personalizada",
                "Notificación Big View"
        };
        // Crear adaptador
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                items);
        // Relacionar el adaptador a la lista
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                notification1(
                        1,
                        R.drawable.icononotificacion,
                        "Ximena Claus",
                        ":D ¡Ya tengo el nuevo logo!"
                );
                break;
            case 1:
                notification2(
                        2,
                        R.drawable.icononotificacion,
                        "Ximena Claus",
                        ":D ¡Ya tengo el nuevo logo!"
                );
            case 2:
                notification3();
                break;
        }
    }

    public void notification1(int id, int iconId, String titulo, String
            contenido) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(iconId)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(),
                                R.drawable.icononotificacion
                                )
                        )

                        .setContentTitle(titulo)
                        .setContentText(contenido)
                        .setColor(getResources().getColor(R.color.colorAccent));
        // Construir la notificación y emitirla
        notifyMgr.notify(id, builder.build());
    }

    public void notification2(int id, int iconId, String titulo, String
            contenido ) {
        // Creación del builder
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(iconId)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(),
                                R.drawable.icononotificacion
                                )
                        )
                        .setContentTitle(titulo)
                        .setContentText(contenido)
                        .setColor(getResources().getColor(R.color.colorPrimaryDark));
                        // Nueva instancia del intent apuntado hacia Eslabon
                                Intent intent = new Intent(this, Eslabon.class);
                        // Crear pila
                                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                        // Añadir actividad padre
                                stackBuilder.addParentStack(Eslabon.class);
                        // Referenciar Intent para la notificación
                                stackBuilder.addNextIntent(intent);
                        // Obtener PendingIntent resultante de la pila
                                PendingIntent resultPendingIntent =
                                        stackBuilder.getPendingIntent(0,
                                                PendingIntent.FLAG_UPDATE_CURRENT);
                        // Asignación del pending intent
                                builder.setContentIntent(resultPendingIntent);
                        // Remover notificacion al interactuar con ella
                                builder.setAutoCancel(true);
                        // Construir la notificación y emitirla
                                notifyMgr.notify(id, builder.build());
                            }

    public void notification3() {

        String GRUPO_NOTIFICACIONES = "notificaciones_similares";
        Notification notificacion;

        // Comprobar si ya fue presionado el item
        if(!segundaVez) {
            notificacion = new NotificationCompat.Builder(this)
                    .setContentTitle("Mensaje Nuevo")
                    .setSmallIcon(R.drawable.icononotificacion)
                    .setContentText("Carlos Arándano   ¿Donde estás?")
                    .setColor(getResources().getColor(R.color.colorPrimaryDark))
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .build();

            // Activar la bandera
            segundaVez = true;

        }else {

            notificacion = new NotificationCompat.Builder(this)
                    .setContentTitle("2 mensajes nuevos")
                    .setSmallIcon(R.drawable.icononotificacion)
                    .setNumber(2)
                    .setColor(getResources().getColor(R.color.colorPrimaryDark))
                    .setStyle(
                            new NotificationCompat.InboxStyle()
                                    .addLine("Carlos Arándano   ¿Si lo viste?")
                                    .addLine("Ximena Claus   Nuevo diseño del logo")
                                    .setBigContentTitle("2 mensajes nuevos")
                    )
                    .setGroup(GRUPO_NOTIFICACIONES)
                    .setGroupSummary(true)
                    .build();
        }

        notifyMgr.notify(3, notificacion);
    }



}
