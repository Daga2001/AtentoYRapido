/*
 * Programaci?n Interactiva
 * Autor: David Alberto Guzm?n - 201942789
 * MiniProyecto 1: Atento y Rapido.
 */

package atentoYRapido;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class Cuadro. Esta clase sirve para crear cuadros con dise?os definidos.
 */
public class Cuadro {
	
	/** The cara cuadro. Es el dise?o del cuadro */
	//attributes
	private int caraCuadro;
	
	//methods
	
	/**
	 * Instantiates a new cuadro. Es el constructor, asigna valores a los atributos de la clase.
	 */
	public Cuadro(){
		Random aleatorio = new Random();
		caraCuadro = aleatorio.nextInt(27) + 1;
	}
	
	/**
	 * Gets the cara cuadro. obtiene el dise?o del cuadro.
	 *
	 * @return the cara cuadro
	 */
	public int getCaraCuadro() {
		return caraCuadro;	
	}
	
}
