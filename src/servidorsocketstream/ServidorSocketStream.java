package servidorsocketstream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorSocketStream extends Thread {

    public ServidorSocketStream() {
    }
    int num1, num2, total = 0;
    int operacion = 0;
    char opr = 0;
    static int puerto = 5555;
   

    public void run() {
        
       
        try {

            System.out.println("Creando socket servidor");

            ServerSocket serverSocket = new ServerSocket();//Creamos el servidor socket.

            System.out.println("Realizando el bind");

            InetSocketAddress addr = new InetSocketAddress("localhost", puerto);
            serverSocket.bind(addr);//Asociamos el servidor con la dirección que deseemos.
            puerto++;
            
            if(puerto<=5557){
            new  ServidorSocketStream().start();
            }
            
            System.out.println("Aceptando conexiones");

            Socket ns = serverSocket.accept();
           
            System.out.println("Conexion recibida");

            ObjectOutputStream resultado = new ObjectOutputStream(ns.getOutputStream());
            ObjectInputStream aux = new ObjectInputStream(ns.getInputStream());

            num1 = aux.readInt();//Leemos los numeros enviados desde el cliente
            num2 = aux.readInt();
            operacion = aux.readInt();//Leemos el número asociado al operando escogido
            //Escogeremos el tipo de operación que vamos a realizar dependiendo del número asociado.
            if (operacion == 1) {

                opr = '+';
                total = (num1 + num2);

            }
            if (operacion == 2) {

                opr = '-';
                total = (num1 - num2);
            }
            if (operacion == 3) {

                opr = 'x';
                total = (num1 * num2);
            }
            if (operacion == 4) {

                opr = '/';
                total = (num1 / num2);
            } else {

                System.out.printf("La operación es invalida");

            }
            //Escribimos el resultado para enviarlo al Cliente.
            resultado.writeInt(total);
            resultado.writeChar(opr);
            //Descargamos la memoria temporal mediante flush para dejar paso a futuros datos.
            resultado.flush();
            //cerramos la conexion
            serverSocket.close();

            System.out.println(
                    "Cerrando el nuevo socket");

            ns.close();

            System.out.println(
                    "Cerrando el socket servidor");

            serverSocket.close();

            System.out.println(
                    "Terminado");

        } catch (IOException ex) {
            Logger.getLogger(ServidorSocketStream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        
        new ServidorSocketStream().start();
        
}
}