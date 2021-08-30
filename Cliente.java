package com;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.*;

public class Cliente {
    /**
     * Cliente
     * Contiene la clase Cliente, cuenta con interfaz y cálculos
     * @Autor: Nicol Otárola Porras
     * @Versión: 29/08/2021
     */

    // Declaración de variables
    JFrame ventana_chat;
    JButton boton_enviar;

    // campos de texto para ingresar datos
    JTextField txt_mensaje1;
    JTextField txt_mensaje2;
    JTextField txt_mensaje3;
    JTextArea area_chat;

    // labels
    JLabel precio;
    JLabel impuesto;
    JLabel peso;

    // contenedores
    JPanel contenedor_areachat;
    JPanel contnedor_botontexto;
    JPanel contenedor_labels;

    JScrollPane scroll; // scroll

    Socket socket;// socket

    // escritor y lector
    BufferedReader lector;
    PrintWriter escritor;

    /**
     * método para generar la interfaz del cliente
     */
    public Cliente() {
        hacerInterfaz();

    }

    /**
     * método para construir la interfaz del cliente
     */
    public void hacerInterfaz() {

        // Toda la distrubición de los elementos que contiene la interfaz

        ventana_chat = new JFrame("Cliente");// ventana principal de la interfaz
        boton_enviar = new JButton("Enviar");// botón para enviar los datos ingresados en los campos de texto

        // campos de texto para los datos
        txt_mensaje1 = new JTextField(4);
        txt_mensaje2 = new JTextField(5);
        txt_mensaje3 = new JTextField(6);
        area_chat = new JTextArea(20, 12);

        //labels
        precio = new JLabel("Precio ");
        impuesto = new JLabel("Impuesto");
        peso = new JLabel("Peso");

        scroll = new JScrollPane(area_chat);//scroll

        // contenedores y tamaños
        contenedor_areachat = new JPanel();
        contenedor_areachat.setLayout(new GridLayout(1, 1));
        contenedor_areachat.add(scroll);
        contenedor_labels = new JPanel();
        contenedor_labels.setLayout(new GridLayout(1, 1));
        contenedor_labels.add(precio);
        contenedor_labels.add(impuesto);
        contenedor_labels.add(peso);
        contnedor_botontexto = new JPanel();
        contnedor_botontexto.setLayout(new GridLayout(1, 4));
        contnedor_botontexto.add(txt_mensaje1);
        contnedor_botontexto.add(txt_mensaje2);
        contnedor_botontexto.add(txt_mensaje3);
        contnedor_botontexto.add(boton_enviar);

        // características de la ventana principal y ubicciones de los contenedores
        ventana_chat.setLayout(new BorderLayout());
        ventana_chat.add(contenedor_labels, BorderLayout.CENTER);
        ventana_chat.add(contenedor_areachat, BorderLayout.NORTH);
        ventana_chat.add(contnedor_botontexto, BorderLayout.SOUTH);
        ventana_chat.setSize(400, 400);
        ventana_chat.setVisible(true);
        ventana_chat.setResizable(false);
        ventana_chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Thread principal = new Thread(new Runnable() {
            @Override
            /**
             * método para establecer el puerto de conexión que va a utilizar el cliente
             * con el servidor, y mantener activos los métodos de escritura y lectura.
             *@throws Exception
             */
            public void run() {
                try {
                    socket = new Socket("Localhost", 9000);
                    leer();
                    escribir();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        principal.start(); // se inicializa el hilo principal para el socket
    }
    /**
     * método crear un hilo del lector de datos
     */
    public void leer() {
        Thread leer_hilo = new Thread(new Runnable() {
            @Override
            /**
             *método para leer los datos ingresados en los espacios de texto
             * @throws Exception
             */
            public void run() {
                try {
                    lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (true) {
                        String mensaje_recibido = lector.readLine();
                        area_chat.append("Servidor dice: " + mensaje_recibido + "\n");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        leer_hilo.start();
    }
    /**
     * método para crear el hilo del escritor de datos
     */
    public void escribir() {
        Thread escribir_hilo = new Thread(new Runnable() {
            @Override
            /**
             * método para crear el escritor de los datos ingresados en los espacios de texto
             */
            public void run() {
                try {
                    escritor = new PrintWriter(socket.getOutputStream(), true);
                    boton_enviar.addActionListener(new ActionListener() {
                        @Override
                        /**
                         * método para escribir los datos ingresados en los espacios de texto
                         * @throws Exception
                         */
                        public void actionPerformed(ActionEvent e) {
                            String enviar_mensaje1 = txt_mensaje1.getText();
                            String enviar_mensaje2 = txt_mensaje2.getText();
                            String enviar_mensaje3 = txt_mensaje3.getText();
                            int precio = Integer.parseInt(enviar_mensaje1);
                            int impuesto = Integer.parseInt(enviar_mensaje2);
                            int peso = Integer.parseInt(enviar_mensaje3);
                            escritor.println(precio);
                            escritor.println(impuesto);
                            escritor.println(peso);
                            escritor.println(calculo(precio,impuesto,peso));
                            txt_mensaje1.setText("");
                            txt_mensaje2.setText("");
                            txt_mensaje3.setText("");
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        escribir_hilo.start();
    }

    /**
     * método para calcular el resultado final de la operación en función de sus parametros
     * @param precio
     * @param impuesto
     * @param peso
     * @return
     * resiltaod final del cálculo
     */
    public double calculo(int precio, int impuesto, int peso) {
        double total = (precio * impuesto / 100) + (peso * 0.15d);
        return total;
    }
    /**
     * método para inicializar un nuevo servidor
     * @param args
     */
    public static void main (String[]args){
        new Cliente();
    }
}