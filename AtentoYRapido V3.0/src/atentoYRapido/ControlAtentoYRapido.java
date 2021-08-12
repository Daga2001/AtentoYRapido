/*
 * Programación Interactiva
 * Autor: David Alberto Guzmán - 201942789
 * MiniProyecto 1: Atento y Rapido.
 */

package atentoYRapido;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class ControlAtentoYRapido. Esta clase sirve para manejar la parte lógica del juego.
 */
public class ControlAtentoYRapido {
	
	//atributos
	
	/** The numeroAciertos. Representa La cantidad de aciertos del usuario*/
	/** The numeroErrores. Es la cantidad de errores del usuario*/
	/** The puntuacion. Es el puntuaje total del usuario*/
	/** The vidas. Es la cantidad de vidas del usuario.*/
	private int numeroAciertos, numeroErrores, puntuacion, vidas;
	
	/** The estado. Es el valor que representa si se acertó o falló en una ronda*/
	private int estado;
	
	/** The cuadros. Es el conjunto de cuadros que se muestran en la partida*/
	private ArrayList<Cuadro> cuadros;
	
	/** The cuadro. Representa un cuadro*/
	private Cuadro cuadro;
	
	//metodos
	
	/**
	 * Instantiates a new control atento Y rapido. Es el constructor, asigna valores a los atributos de la clase.
	 *
	 * @param numeroCuadros the numero cuadros. Es la cantidad de cuadros que se desean crear en el constructor.
	 */
	public ControlAtentoYRapido(int numeroCuadros) {
		//creación atributos
		cuadros = new ArrayList<Cuadro>(numeroCuadros);
		vidas = 3;
		estado = 3;
		
		cuadro = new Cuadro();
		cuadros.add(cuadro);
		
		for(int nCuadros = 0; nCuadros < (numeroCuadros-1); nCuadros++) {
			
			cuadro = new Cuadro();
			
			if(!isInList(cuadro.getCaraCuadro())) {
				cuadros.add(cuadro);
			}
			else {
				nCuadros--;
			}
			
		}
		
	}
	
	/**
	 * Calcular puntuacion. Actualiza la cantidad de aciertos, errores, vidas y la puntuacion conforme avanza el tiempo
	 *
	 * @param clicked the clicked. Es una variable que me indica si el usuario dió click o no al boton de stop.
	 */
	public void calcularPuntuacion(boolean clicked) {
			
			if(clicked && hayCuadrosIguales()) {
				puntuacion += 5;
				numeroAciertos ++;
			}
			
			else {
				numeroErrores ++;
				vidas --;
			}
	}
	
	/**
	 * Hay cuadros iguales. Determina si se muestran 2 cuadros identicos en pantalla.
	 *
	 * @return true, if successful
	 */
	public boolean hayCuadrosIguales() {
		
		int frameDesing;
		int frameDesing2;
		
		for(int nCuadros = 0; nCuadros < cuadros.size(); nCuadros++) {
			
			frameDesing = cuadros.get(nCuadros).getCaraCuadro();
			
			for(int nFrames = 0; nFrames < cuadros.size(); nFrames++) {
				
				frameDesing2 = cuadros.get(nFrames).getCaraCuadro();
				
				if(nCuadros == nFrames ) {
					
					nFrames++;
					
					if(nFrames < cuadros.size()) {
						
						frameDesing2 = cuadros.get(nFrames).getCaraCuadro();
						
						if(frameDesing == frameDesing2) {
						
							return true;
						}
						
					}
					
				}
				else {
					
					if(frameDesing == frameDesing2) {
						
						return true;
						
					}
					
				}
				
			}
			
		}
		return false;
	}
	
	/**
	 * Reemplazar cuadro. Reemplaza un cuadro dentro del array de cuadros dado un indice y un sustituto.
	 *
	 * @param index the index. Posición del cuadro a reemplazar.
	 * @param sustituto the sustituto. Cuadro que va a sustituir al actual.
	 */
	public void reemplazarCuadro(int index, int sustituto) {
		
		cuadro = new Cuadro();
		
		//it tries to get the frame that replace the another.
		while(cuadro.getCaraCuadro() != sustituto) {
			cuadro = new Cuadro();
		}
		
		cuadros.set(index, cuadro);
		
	}
	
	/**
	 * Determinar estado. Identifica el estado actual del juego. estados: 1 = perder la partida, 2 = cambio de ronda - error, 
	 * 3 = cambio de cuadro con borde azul.
	 */
	public void determinarEstado() {
		
		if(vidas == 0) {
			estado = 1;
		}
		
		else if(hayCuadrosIguales()) {
			estado = 2;
		}
		
		else {
			estado = 3;
		}
		
	}
	
	/**
	 * Reiniciar juego. Si el jugador desea volver a jugar, reinicia los valores del juego (puntuacion, vidas, estado,...)
	 * a su estado original.
	 */
	public void reiniciarJuego() {
		numeroAciertos = 0; 
		numeroErrores = 0; 
		puntuacion = 0; 
		vidas = 3;
		estado = 3;
	}
	
	/**
	 * Number cols. Me ayuda a determinar la cantidad de columnas de la GridbagLayout.
	 *
	 * @param number the number. El numero de cuadros que hay en pantalla.
	 * @return the int
	 */
	public int numberCols(int number) {
		int x = 1;
		int y = 1;
		
		while(true) {
			if(x*y <= number) {
				
				if(x*(y+1) <= number) {
					x++;
					y++;
				}
				else {
					return x;
				}
			}
			else {
				return x;
			}
		}
	}
	
	/**
	 * Number rows. Me ayuda a determinar el número de filas de la GridBagLayout
	 *
	 * @param number the number. El numero de cuadros que hay en pantalla.
	 * @return the int
	 */
	public int numberRows(int number) {
		int x = 1;
		int y = 1;
		
		while(true) {
			if(x*y <= number) {
				
				if(x*(y+1) <= number) {
					x++;
					y++;
				}
				else {
					return y+1;
				}
			}
			else {
				return y;
			}
		}
	}
	
	/**
	 * Checks if is in list. Verifica si hay un diseño repetido en el array de cuadros.
	 *
	 * @param element the element. El elemento a verificar, debe ser un numero.
	 * @return true, if is in list
	 */
	public boolean isInList(int element) {
		
		for(int nElement = 0; nElement < cuadros.size(); nElement++) {
			
			if(cuadros.get(nElement).getCaraCuadro() == element) {
				return true;
			}
			
		}
		
		return false;
	}
	
	/**
	 * Reiniciar cuadros. reinicia todos los diseños de los cuadros en el array de cuadros.
	 *
	 * @param numeroCuadros the numero cuadros. Cantidad de cuadros que hay que colocar.
	 */
	public void reiniciarCuadros(int numeroCuadros) {
		
		cuadros.clear();
		
		cuadro = new Cuadro();
		cuadros.add(cuadro);
		
		for(int nCuadros = 0; nCuadros < (numeroCuadros-1); nCuadros++) {
			cuadro = new Cuadro();
			
			if(!isInList(cuadro.getCaraCuadro())) {
				cuadros.add(cuadro);
			}
			else {
				nCuadros--;
			}
			
		}
		
	}
	
	/**
	 * Adicionar cuadro. Agrega un cuadro diferente al array de cuadros.
	 */
	public void adicionarCuadro() {
		
		Cuadro frame = new Cuadro();
		
		if(isInList(frame.getCaraCuadro())) {
			
			while(isInList(frame.getCaraCuadro())) {
				
				frame = new Cuadro();
				
			}
			
			cuadros.add(frame);
		}
		else {
			cuadros.add(frame);
		}
		
	}

	/**
	 * Gets the cuadro. Obtiene un cuadro.
	 *
	 * @param number the number. Es el indice, la n-esima posicion del cuadro en el array (debe colocarse desde el numero 1 en adelante)
	 * @return the cuadro
	 */
	public Cuadro getCuadro(int number) {
		return cuadros.get(number-1);
	}
	
	/**
	 * Cuantos cuadrados. Retorna la cantidad de cuadros que hay en el array de cuadros.
	 *
	 * @return the int
	 */
	public int cuantosCuadrados() {
		return cuadros.size();
	}

	/**
	 * Gets the numero aciertos. Devuelve la cantidad de aciertos.
	 *
	 * @return the numero aciertos
	 */
	public int getNumeroAciertos() {
		return numeroAciertos;
	}

	/**
	 * Gets the numero errores. Devuelve la cantidad de errores.
	 *
	 * @return the numero errores
	 */
	public int getNumeroErrores() {
		return numeroErrores;
	}

	/**
	 * Gets the puntuacion. Devuelve la puntuacion total obtenida.
	 *
	 * @return the puntuacion
	 */
	public int getPuntuacion() {
		return puntuacion;
	}

	/**
	 * Gets the vidas. Devuelve la cantidad de vidas.
	 *
	 * @return the vidas
	 */
	public int getVidas() {
		return vidas;
	}
	
	/**
	 * Gets the estado. Devuelve el estado actual del juego.
	 *
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}
}
