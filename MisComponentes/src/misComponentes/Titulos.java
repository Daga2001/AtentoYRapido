/*
 * Programación Interactiva
 * Autor: David Alberto Guzmán - 201942789
 * Caso 1: Juego Craps.
 */

package misComponentes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

// TODO: Auto-generated Javadoc
/**
 * The Class Titulos. Sirve para crear titulos con un tamaño y color de fondo definidos.
 */
public class Titulos extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//atributos
	/**
	 * Instantiates a new titulos.
	 *
	 * @param texto the texto
	 * @param tamano the tamano
	 * @param colorFondo the color fondo
	 */
	//metodos
	public Titulos(String texto, int tamano, Color colorFondo) {
		this.setText(texto);
		Font font = new Font(Font.SERIF, Font.BOLD+Font.ITALIC,tamano);
		this.setFont(font);
		this.setBackground(colorFondo);
		this.setForeground(Color.WHITE);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setOpaque(true);
	}
}
