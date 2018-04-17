package Estado;

/*
 * @author Eduardo Tapia
 * 
 */

public class Estado {
	private boolean[][] tablero;
	private int dimension;

	public Estado(int dimension) {
		this.dimension = dimension;
		this.tablero = new boolean[dimension][dimension];
	}

	public Estado(Estado otro) {
		this.dimension = otro.getDimension();
		this.tablero = new boolean[dimension][dimension];
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				this.tablero[i][j] = otro.getTablero()[i][j];
			}
		}
	}

	/*
	 * Genera un estado aleatorio
	 */
	public void generarAleatorio() {
		// TODO: generar un estado aleatorio
            int val=(int)(Math.random()*10+1);
            //System.out.println("Se harán: "+val+" movimientos aleatorios.");
            int coord_x;
            int coord_y;
            
            for(int i =0;i<val;i++){
                coord_x=(int)(Math.random()*3+1); 
                //System.out.println("el valor que salio de x es :"+coord_x);
                
                coord_y=(int)(Math.random()*3+1);
                //System.out.println("el valor que salio de y es :"+coord_y);
                tocarPieza(coord_x,coord_y);
                //System.out.println("");
                
            }
	}
        

	/*
	 * Convierte un tablero de boolean a un tablero de enteros
	 * 
	 * @return una matriz de enteros equivalente al estado
	 */
	public int[][] transformarInt() {
		int[][] nueva = new int[this.dimension][this.dimension];
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				if (this.tablero[i][j]) {
					nueva[i][j] = 1;
				} else {
					nueva[i][j] = 0;
				}
			}
		}
		return nueva;
	}

	@Override
	public boolean equals(Object otro) {
		if (otro == null)
			return false;
		if (otro == this)
			return true;
		if (!(otro instanceof Estado))
			return false;
		Estado otroEstado = (Estado) otro;
		if (this.dimension != otroEstado.dimension) {
			return false;
		}
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				if (this.tablero[i][j] != otroEstado.getLuz(i, j))
					return false;
			}
		}
		return true;
	}

	/*
	 * modifica el tablero en la posicin (fila,columna) y las posiciones
	 * (fila+1,columna), (fila-1,columna), (fila,columna+1), (fila,columna-1)
	 * 
	 * @param fila
	 * @param columna
	 */
	public void tocarPieza(int fila, int columna) {
		if (fila >= 0 && fila < this.dimension && columna >= 0
				&& columna < this.dimension)
			this.tablero[fila][columna] = !this.tablero[fila][columna];
		if (fila - 1 >= 0 && fila - 1 < this.dimension && columna >= 0
				&& columna < this.dimension)
			this.tablero[fila - 1][columna] = !this.tablero[fila - 1][columna];
		if (fila + 1 >= 0 && fila + 1 < this.dimension && columna >= 0
				&& columna < this.dimension)
			this.tablero[fila + 1][columna] = !this.tablero[fila + 1][columna];
		if (fila >= 0 && fila < this.dimension && columna - 1 >= 0
				&& columna - 1 < this.dimension)
			this.tablero[fila][columna - 1] = !this.tablero[fila][columna - 1];
		if (fila >= 0 && fila < this.dimension && columna + 1 >= 0
				&& columna + 1 < this.dimension)
			this.tablero[fila][columna + 1] = !this.tablero[fila][columna + 1];
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public boolean[][] getTablero() {
		return tablero;
	}

	public void setTablero(boolean[][] tablero) {
		this.tablero = tablero;
	}

	public boolean getLuz(int fila, int columna) {
		return this.tablero[fila][columna];
	}

}
