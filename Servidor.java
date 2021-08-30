package com;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;

/**
 * Servidor:
 * Esta es la clase del servidor, contien una interfaz
 * funcionalidad de calculo y el factor de coonexión.
 * @Autor: Nicol Otárola Porras
 * @Versión: 29/08/2021
 */
public class Servidor {

    // declaración de variables

    JFrame ventana_chat; // ventana principal
    JButton btn_enviar; // botón para envier los datos

    // campos de texto para ingresar los datos

    JTextField txt_mensaje1;
    JTextField txt_mensaje2;
    JTextField txt_mensaje3;

    JTextArea area_chat; // area de texto para mostrar los dato

    // contenedores
    JPanel contenedorChat;
    JPanel contenerdorBtntxt;

    JScrollPane scroll; // scroll

    // Sockets
    ServerSocket servidor;
    Socket socket;

    // Lector y escritor
    BufferedReader lector;
    PrintWriter escritor;

    // Labels
    JLabel precio;
    JLabel impuesto;
    JLabel peso;
    JPanel contenedor_labels;


    /**
     * método para generar la interfaz del servidor
     */
    public Servidor(){
        hacerInterfaz();
    }

    /**
     * Método para construir la interfaz del servidor
     */
    public void hacerInterfaz(){

        // Toda la distribución de los elementos que contiene la interfaz

        ventana_chat = new JFrame("Servidor"); // ventana para el servidor
        btn_enviar = new JButton("Enviar");// botón para enviar los datos

        // campos de texto para los datos
        txt_mensaje1 = new JTextField(4);
        txt_mensaje2 = new JTextField(5);
        txt_mensaje3 = new JTextField(6);
        area_chat= new JTextArea(20,12);

        scroll = new JScrollPane(area_chat); // scroll

        // labels
        precio = new JLabel("Precio ");
        impuesto = new JLabel("Impuesto");
        peso = new JLabel("Peso");

        // contenedores y tamaños
        contenedor_labels = new JPanel();
        contenedor_labels.setLayout(new GridLayout(1,1));
        contenedor_labels.add(precio);
        contenedor_labels.add(impuesto);
        contenedor_labels.add(peso);
        contenedorChat = new JPanel();
        contenedorChat.setLayout(new GridLayout(1,1));
        contenedorChat.add(area_chat);
        contenerdorBtntxt = new JPanel();
        contenerdorBtntxt.setLayout(new GridLayout(1,4));
        contenerdorBtntxt.add(txt_mensaje1);
        contenerdorBtntxt.add(txt_mensaje2);
        contenerdorBtntxt.add(txt_mensaje3);
        contenerdorBtntxt.add(btn_enviar);

        // características de la ventana principal y ubicciones de los contenedores
        ventana_chat.setLayout(new BorderLayout());
        ventana_chat.add(contenedor_labels, BorderLayout.CENTER);
        ventana_chat.add(contenedorChat, BorderLayout.NORTH);
        ventana_chat.add(contenerdorBtntxt, BorderLayout.SOUTH);
        ventana_chat.setSize(400,400);
        ventana_chat.setVisible(true);
        ventana_chat.setResizable(false);
        ventana_chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // hilo principal para el socket
        Thread principal = new Thread(new Runnable() {
            @Override
            /**
             * método para establecer el puerto de conexión que va a utilizar el servidor
             * con los cliente, y mantener activos los métodos de escritura y lectura.
             * @throws IOException
             */
            public void run() {
                try{
                    servidor = new ServerSocket(9000);

                    while (true){
                        socket = servidor.accept();
                        leer();
                        escribir();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        principal.start(); // se inicia el hilo principal para el socket

    }

    /**
     * método crear un hilo del lector de datos
     */
    public void leer(){
        Thread leer_hilo = new Thread(new Runnable() {
            @Override
            /**
             * método para leer los datos ingresados en los espacios de texto
             * @throws IOException
             */
            public void run() {
                try {
                    lector= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (true){
                        String mensaje_recibido = lector.readLine();
                        area_chat.append("Cliente dice: " + mensaje_recibido +"\n");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        leer_hilo.start(); // se inicia el hilo para leer los datos
    }

    /**
     * método para crear el hilo del escritor de datos
     */
    public void escribir() { // método para escribir los datos
        Thread escribir_hilo = new Thread(new Runnable() {
            @Override
            /**
             * método para crear el escritor de los datos ingresados en los espacios de texto
             */
            public void run() {
                try {
                    escritor = new PrintWriter(socket.getOutputStream(), true);
                    btn_enviar.addActionListener(new ActionListener() {
                        @Override
                        /**
                         * método para escribir los datos ingresados en los espacios de texto
                         * @throws IOException
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
        escribir_hilo.start(); // se inicia el hilo para escribir los datos
    }

    /**
     * método para calcular el resultado final de la operación en función de sus parametros
     * @param precio
     * @param impuesto
     * @param peso
     * @return
     * Resultado final del cálculo
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
        new Servidor();
    }
}