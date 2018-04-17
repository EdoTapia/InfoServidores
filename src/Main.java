
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Arrays;

import Estado.Estado;
import Nodo.Nodo;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author Eduardo Tapia
 *
 */
public class Main {

    /*
     * @param args
     */
    public static void main(String[] args) {
		// TODO: Programa principal
        // obtiene la dimension del tablero de luces
        int dimension = deepMenu();
        System.out.println("Creando Tablero de " + dimension + " X " + dimension);
        //inicializa es Estado incial
        Estado inicio = new Estado(dimension);
        inicio.generarAleatorio();
        imprimirEstado(inicio);

        //Solucion del problema
        Nodo resultado;
        int seleccion = menu2();
        switch (seleccion) {
            // Busqueda Anchura
            case 1:
                resultado = busquedaAnchura(inicio);
                imprimirResultados(resultado);
                break;
            // Busqueda Profundidad
            case 2:
                resultado = busquedaProfundidad(inicio);
                imprimirResultados(resultado);
                break;
            // Busqueda Profundidad Limitada   
            case 3:
                resultado = busquedaProfundidadLimitada(inicio);
                imprimirResultados(resultado);
                break;
            // salida del sistema    
            case 4:
                System.out.println("Saliendo del Programa");
                System.out.println("Adios.. que tenga un buen dia");
                System.exit(0);
        }

    }

    /**
     * Imprime la Solucion del Problema por pantalla
     *
     * @param solucion nodo con la solucion del problema (nodo final)
     */
    public static void imprimirResultados(Nodo solucion) {
        if (solucion == null) {
            System.out.println("Sin solucion para el problema");
        } else {

            List<int[]> camino = getCamino(solucion);
            printMovimientos(camino);
            List<Nodo> aux = getCaminoNodo(solucion);
            printMovimientosNodo(aux);
        }
    }


    /*
     * Imprime un estado por pantalla
     * 
     * @param estado
     */
    public static void imprimirEstado(Estado estado) {
        int[][] estadoInt = estado.transformarInt();
        for (int i = 0; i < estado.getDimension(); i++) {
            for (int j = 0; j < estado.getDimension(); j++) {
                System.out.print(estadoInt[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /*
     * Improme un nodo por pantalla con su costo, la profundidad a la que se
     * encuentra y la accion que lleva al estado del nodo
     * 
     * @param nodo
     */
    public static void imprimirNodo(Nodo nodo) {
        int[][] estadoInt = nodo.getEstado().transformarInt();
        for (int i = 0; i < nodo.getEstado().getDimension(); i++) {
            for (int j = 0; j < nodo.getEstado().getDimension(); j++) {
                System.out.print(estadoInt[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Costo: " + nodo.getCosto());
        System.out.println("Profundidad: " + nodo.getProfundidad());
        System.out.println("Accion: " + nodo.getAccion()[0] + " "
                + nodo.getAccion()[1]);
        System.out.println();
    }

    /**
     * Realiza una busqueda por anchura en el árbol de solucion Los nodos con
     * posibles solicuones se almacena en una cola FIFO
     *
     * @param inicial Estado inicial del problema
     * @return Nodo con la estado final aceptado, null si no encuentra solucion
     */
    public static Nodo busquedaAnchura(Estado inicial) {
        //Estructuras de datos utilizadas
        Queue<Nodo> cola = new LinkedList<Nodo>();
        List<Estado> recorridos = new LinkedList<Estado>();
        //inicializacion del nodo raiz del arbol con el estado inicial del problema
        int[] accion = {0, 0};
        Nodo root = new Nodo(inicial, null, 0, accion);
        // agrega el nodo raiz  a la cola            
        cola.add(root);
        //guarda el estado incial como utilizado
        recorridos.add(inicial);
        // mientras la cola no se encuntre vacia, osea no existan mas nodos expandidos
        while (!cola.isEmpty()) {
            // remueve el primer elemento de la cola
            Nodo prueba = cola.poll();

            // comprueba que el estado contenido por el nodo es una solucion
            // de ser solucion retorna el nodo que lo contiene.
            if (testObjetivo(prueba.getEstado())) {
                return prueba;
            }
            // De no encontrarse la solucion expande el nodo actual de la cola
            // y lo almaccena en una lista que contiene los nodos expandidos
            // frontera del problema.
            //@param recorridos Lista de Estados ya visitados 
            List<Nodo> frontera = prueba.expandir(recorridos);
            //Agrega los nodos contenidos en la lista de forma ordenada a la cola de solucion.
            cola.addAll(frontera);
            // almacena los estados contenidos en la lista frontera como visitados 
            for (Nodo nodo : frontera) {
                recorridos.add(nodo.getEstado());
            }

        }
        // no encontro solucion retorna nulo
        return null;
    }

    /**
     * Implementacion de la busqueda en profundidad, como estructura de datos se
     * utiliza una pila implementada con Deque
     *
     * @param inicial
     * @return
     */
    public static Nodo busquedaProfundidad(Estado inicial) {
        Deque<Nodo> pila = new LinkedList<Nodo>();
        List<Estado> recorridos = new LinkedList<Estado>();
        int[] accion = {0, 0};
        Nodo root = new Nodo(inicial, null, 0, accion);
        // guarda el estado inicial en la pila
        pila.addFirst(root);
        while (!pila.isEmpty()) {
            // remueve el primer elemento de la pila
            Nodo prueba = pila.removeFirst();
                 // comprueba que el estado contenido por el nodo es una solucion
            // de ser solucion retorna el nodo que lo contiene.
            if (testObjetivo(prueba.getEstado())) {
                return prueba;
            }
               // De no encontrarse la solucion expande el nodo actual de la cola
            // y lo almaccena en una lista que contiene los nodos expandidos
            // frontera del problema.
            //@param recorridos Lista de Estados ya visitados
            List<Nodo> frontera = prueba.expandir(recorridos);
            //Agrega los nodos contenidos en la lista de forma ordenada a la cola de solucion.
            pila.addAll(frontera);
            // almacena los estados contenidos en la lista frontera como visitados 
            for (Nodo nodo : frontera) {
                recorridos.add(nodo.getEstado());
            }

        }

        return null;
    }

    /**
     * Implementacion de la busqueda en profundidad limitada, el limite de
     * profundidad se encuentra dado por el tamaño del tablero
     *
     * @param inicial
     * @return
     */
    public static Nodo busquedaProfundidadLimitada(Estado inicial) {
        int profundidad = 0;
        Deque<Nodo> pila = new LinkedList<Nodo>();
        List<Estado> recorridos = new LinkedList<Estado>();
        int[] accion = {0, 0};
        Nodo root = new Nodo(inicial, null, profundidad, accion);

        pila.addFirst(root);
        while (!pila.isEmpty()) {
            Nodo prueba = pila.removeFirst();
            if (testObjetivo(prueba.getEstado())) {
                return prueba;
            }
            // crea la lista de los nodos expandidos
            List<Nodo> frontera = new ArrayList<Nodo>();
                    // si la profundidad de los nodos no es mayor a las combinaciones
            // del tablero expande los nodos 
            // esta pensado para tableros cuadrados, de lo contrario de debe modificar
            if (prueba.getProfundidad() < Math.pow(inicial.getDimension(), 2)) {
                frontera = prueba.expandir(recorridos);

            }

            pila.addAll(frontera);
            for (Nodo nodo : frontera) {
                recorridos.add(nodo.getEstado());
            }

        }

        return null;
    }

    /**
     * Revisa que el estado sea final
     *
     * @param estado
     * @return true si el estado es final, false en otro caso
     */
    public static boolean testObjetivo(Estado estado) {
        /* TODO: Implementar la comprobacin de que se ha alcanzado el objetivo*/
        boolean flag = true;
        for (int i = 0; i < estado.getDimension(); i++) {
            for (int j = 0; j < estado.getDimension(); j++) {
                if (estado.getTablero()[i][j] == true) {
                    flag = false;
                    return flag;
                }
            }
        }
        return flag;
    }

    /*
     * Retorna la secuencia de acciones desde el estado inicial al estado actual
     * de un nodo
     * 
     * @param nodo final solucion
     * @return listaCamino List<int[]> lista de vectores que contienen los movimientos que
     * se utilizaron para resolver el problema
     */
    public static List<int[]> getCamino(Nodo nodo) {
		// TODO: Implementar como se muestra la secuencia de acciones que
        // cumplen el objetivo
        List<int[]> listaCamino = new ArrayList<int[]>();
                // realiza un loop para llegar al nodo superior raiz

        while (nodo.getPadre() != null) {
            //almacena la accion realizada en la lista de camino recorrido
            listaCamino.add(nodo.getAccion());
            nodo = nodo.getPadre();
        }
                // revierte la lista de posisicion para una lectur mas natural
        // las primeras acciones realizadas se encuentran en las primeras posiciones
        // de la lista luego del cambio
        Collections.reverse(listaCamino);
        return listaCamino;
    }

    /**
     * Entrega una lista con los nodos solucion
     *
     * @param nodo final solucion
     * @return List<Nodo> con los nodos que pertenecen a la solucion
     */
    public static List<Nodo> getCaminoNodo(Nodo nodo) {
        List<Nodo> caminoNodo = new ArrayList<Nodo>();

        while (nodo.getPadre() != null) {
            caminoNodo.add(nodo);
            nodo = nodo.getPadre();
        }
            // guarda el ultimo nodo, osea el nodo inical del problema para
        // mostrarlo de una forma mas natural
        caminoNodo.add(nodo);
        Collections.reverse(caminoNodo);
        return caminoNodo;
    }

    /**
     * Imprime por pantalla el camino soluccion a partir de un lista de vectores
     *
     * @param camino List<int[]>
     */
    public static void printMovimientos(List<int[]> camino) {
        System.out.println("Tamaño camino: " + camino.size());
        System.out.println("Movimientos Realizados :");
        for (int[] is : camino) {
            System.out.print(Arrays.toString(is));

        }

    }

    /**
     * imprime por pantalla los nodos que pertenecen a la solucion en el orden
     * como se soluciona con sus respecivas acciones
     *
     * @param camino
     */
    public static void printMovimientosNodo(List<Nodo> camino) {
        System.out.println("\n");
        System.out.println("A continuacion se presenta el camino solucion del Problema \na partir del estado inicial:");
        System.out.println("\n");

        for (Nodo is : camino) {
            imprimirNodo(is);
        }
    }

    /**
     * Implementacion de un Menu
     */
    public static void Menu() {

        System.out.println("*****************************************************************");
        System.out.println("******  Bienvenido al Taller 1 de Inteligencía Artificial  ******");
        System.out.println("*****************************************************************");
        System.out.println("******  Integrantes:  Eduardo Tapia - Maximiliano Linares *******");
        System.out.println("*****************************************************************");
        System.out.println("");
        System.out.println("");
        System.out.println("******************         LIGTHS OUT         *******************");
        System.out.println("******************           Menu             *******************");
        System.out.println("");
        System.out.println("La solucion se encuentra Implementada para tableros \nde tamaños limitados");
        System.out.println("Porfavor seleccione alguna de las siguientes opciones:");
        System.out.println("");
        System.out.println("[1] Tablero de 3x3");
        System.out.println("[2] Tablero de 5x5");
        System.out.println("[3] Salir[X]");
        System.out.println("\n\n\n");

    }

    /**
     * Implementacion de un menu
     *
     * @return retorna seleccion del usuario
     */
    public static int menu2() {
        int seleccion;
        Scanner in = new Scanner(System.in);
        System.out.println("*****************************************************************");
        System.out.println("                           Sub-Menu                      ");
        System.out.println("*****************************************************************");
        System.out.println("");
        System.out.println("");

        System.out.println("[1] Resolucion del Problema mediante Busqueda de Anchura");
        System.out.println("[2] Resolucion del Problema mediante Busqueda de Profundidad");
        System.out.println("[3] Resolucion del Problema mediante Busqueda de Profundidad Limitada");
        System.out.println("[4] Salir[X]");
        seleccion = in.nextInt();
        while (seleccion != 1 && seleccion != 2 && seleccion != 3 && seleccion != 4) {
            System.out.println("La opcion ingresa no es Valida......");
            System.out.println("Porfavor Intente nuevamente \n\n\n");
            menu2();
            seleccion = in.nextInt();
        }
        return seleccion;
    }

    /**
     * Implemetacion de control de menu, usa el metodo Menu() como base permite
     * obtener la dimension del tablero que se utilizara en el problema
     *
     * @return la dimension del tablero
     */
    public static int deepMenu() {
        Menu();
        int seleccion;
        Scanner in = new Scanner(System.in);
        seleccion = in.nextInt();
        while (seleccion != 1 && seleccion != 2 && seleccion != 3) {
            System.out.println("La opcion ingresa no es Valida......");
            System.out.println("Porfavor Intente nuevamente \n\n\n");
            Menu();
            seleccion = in.nextInt();
        }

        int dimension; // dimension del tablero
        while (true) {
            switch (seleccion) {
                case 1:
                    //tablero de 3x3
                    dimension = 3;
                    return dimension;
                    //tablero de 5x5
                case 2:
                    dimension = 5;
                    return dimension;
                    // salida del sistema
                case 3:
                    System.out.println("Saliendo del Programa");
                    System.out.println("Adios.. que tenga un buen dia");
                    System.exit(0);
            }

        }

    }

}
