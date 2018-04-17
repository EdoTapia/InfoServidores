package Nodo;

import java.util.ArrayList;
import java.util.List;

import Estado.Estado;

/*
 * @author Eduardo Tapia
 * 
 */

public class Nodo {
	private Estado estado;
	private Nodo padre;
	private int profundidad;
	private double costo;
	private int[] accion;

	public Nodo(Estado estado, Nodo padre, int profundidad, int[] accion) {
		this.estado = estado;
		this.padre = padre;
		this.profundidad = profundidad;
		this.costo = 0.0;
		this.accion = accion;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Nodo getPadre() {
		return this.padre;
	}

	public void setPadre(Nodo padre) {
		this.padre = padre;
	}

	public int getProfundidad() {
		return this.profundidad;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}

	public double getCosto() {
		return this.costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public int[] getAccion() {
		return this.accion;
	}

	public void setAccion(int[] accion) {
		this.accion = accion;
	}

	@Override
	public boolean equals(Object otro) {
		if (otro == null)
			return false;
		if (otro == this)
			return true;
		if (!(otro instanceof Nodo))
			return false;
		Nodo otroNodo = (Nodo) otro;
		if (this.padre != otroNodo.getPadre())
			return false;
		if (this.profundidad != otroNodo.getProfundidad())
			return false;
		if (this.costo != otroNodo.getCosto())
			return false;
		if (this.accion != otroNodo.getAccion())
			return false;
		for (int i = 0; i < this.getEstado().getDimension(); i++) {
			for (int j = 0; j < this.getEstado().getDimension(); j++) {
				if (this.getEstado().getTablero()[i][j] != otroNodo.getEstado()
						.getLuz(i, j))
					return false;
			}
		}
		return true;
	}

	/*
	 * Entrega una lista de nodos posibles a partir del estado actual, revisa
	 * que el nodo no se encuentre repetido en cola/pila
	 * 
	 * @param listaRepetidos
	 * @return una lista con los nodos no explorado a partir del estado del nodo
	 *         actual (this)
	 */
	public List<Nodo> expandir(List<Estado> listaRepetidos) {
		// TODO: Implementar la funcin que entrega el listado de nodos no
		// repetidos posibles a partir de un tablero
		
                List<Nodo> listaRetorna = new ArrayList<Nodo>();
                // Ciclo para generar estados a partir del nodo actual "This"
                for (int i = 0; i < this.estado.getDimension(); i++) {
                    for (int j = 0; j < this.estado.getDimension(); j++) {
                        // copia el estado actual
                        Estado nuevo=new Estado(this.estado);
                        // realiza el movimiento 
                        nuevo.tocarPieza(i, j);
                        // si el estado no se encuentra explorado previamente 
                        // crea un nuevo nodo a partir del estado y lo agrega a la lista
                        // de nodos expandidos
                        if (!listaRepetidos.contains(nuevo)){
                            //almacena la accion ejecutada al tocar un cuadrante
                               int[] accion= {i,j};
                            // agraga un nuevo nodo a la lista de expandidos
                            // el nodo actual se transforma en padre, se aunmenta la profundidad
                            // y se almacena la accion en el nodo
                                listaRetorna.add(new Nodo(nuevo, this, this.profundidad+1, accion));
                        
                         }
                            
                        
                        
                    }
                
            }
		return listaRetorna;
	}
}
