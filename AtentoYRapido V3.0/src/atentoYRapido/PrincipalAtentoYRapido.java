/*
 * Programación Interactiva
 * Autor: David Alberto Guzmán - 201942789
 * MiniProyecto 1: Atento y Rapido.
 */

package atentoYRapido;

import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

// TODO: Auto-generated Javadoc
/**
 * The Class PrincipalAtentoYRapido.
 */
public class PrincipalAtentoYRapido {

	/**
	 * The main method. Es el metodo principal, el que se ejecuta al inicio del programa.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			String javaLookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(javaLookAndFeel);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Hubo un daño en la JVM");
		};
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				@SuppressWarnings("unused")
				InstructionsGUI introGUI = new InstructionsGUI();
			}
			
		});
	}

}
