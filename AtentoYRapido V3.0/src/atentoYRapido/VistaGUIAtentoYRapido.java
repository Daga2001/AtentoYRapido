/*
 * Programación Interactiva
 * Autor: David Alberto Guzmán - 201942789
 * MiniProyecto 1: Atento y Rapido.
 */

package atentoYRapido;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;
import misComponentes.Titulos;

// TODO: Auto-generated Javadoc
/**
 * The Class VistaGUIAtentoYRapido.
 */
public class VistaGUIAtentoYRapido extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//atributos
	
	/** The zona botones. */
	private JPanel zonaCuadros, zonaPuntuacion1, zonaPuntuacion2, zonaBotones;
	
	/** The vida. */
	private JLabel cuadro, vida;
	
	/** The valor puntuacion. */
	private JTextField valorAciertos, valorErrores, valorPuntuacion;
	
	/** The reanudar. */
	private JButton stop, salir, jugar, pausar, reanudar;
	
	/** The imagen. */
	private ImageIcon imagen;
	
	/** The titulo. */
	private Titulos titulo;
	
	/** The control game. */
	private ControlAtentoYRapido controlGame;
	
	/** The escucha. */
	private Escucha escucha;
	
	/** The Vista GUI. */
	private JFrame vistaGUI;
	
	/** The dificultad. */
	private int numeroCuadros, numeroFilas, numeroColumnas, dificultad;
	
	/** The timer. */
	private Timer timer;
	
	/** The personalizated color. */
	private Color personalizatedColor, 
	darkGreen = new Color(73,195,64), 
	lightGreen = new Color(132,255,64);
	
	/** The compound. */
	private Border bordeAnterior, blueLine, personalizatedLine, compound,
	lightGreenLine = BorderFactory.createLineBorder(lightGreen);
	
	/** The cuadros. */
	private ArrayList<JLabel> vidas, cuadros;
	
	/** The audio in. */
	private AudioInputStream audioIn;
	
	/** The clip. */
	private Clip mainMusic, right, wrong, fail, wind, pause, rollOver;
	
	
	
	//metodos
	
	/**
	 * Instantiates a new vista GUI atento Y rapido. Es el constructor, asigna valores a las caracteristicas de la ventana.
	 */
	public VistaGUIAtentoYRapido() {
		
		//inits the GUI.
		initGUI();
		
		//set default window configuration
		this.setTitle("Juego Atento Y Rapido");
		this.setUndecorated(true);
		this.setBackground(Color.WHITE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	

	/**
	 * Inits the GUI. Inicia la interfaz gráfica de usuario.
	 */
	private void initGUI() {
		// TODO Auto-generated method stub
		
		//ask for difficult
		askForDifficult();
		
		//ask for number of squares	
		askForSquares();
		
		//Icon
		imagen = new ImageIcon(getClass().getClassLoader().getResource("mainIcon.png"));
		this.setIconImage(imagen.getImage());
		
		//Audios
		try {
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("background music.wav"));
			mainMusic = AudioSystem.getClip();
			mainMusic.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("right answer.wav"));
			right = AudioSystem.getClip();
			right.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("wrong.wav"));
			wrong = AudioSystem.getClip();
			wrong.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("fail.wav"));
			fail = AudioSystem.getClip();
			fail.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("Wind-Shoowsh.wav"));
			wind = AudioSystem.getClip();
			wind.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("pause.wav"));
			pause = AudioSystem.getClip();
			pause.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("rollover-sound-effect.wav"));
			rollOver = AudioSystem.getClip();
			rollOver.open(audioIn);
			
			mainMusic.start();
			mainMusic.loop(Clip.LOOP_CONTINUOUSLY);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error! no se encontró el archivo de música o está dańado.");
		}
		
		//set up container - layout
		Container contenedor = this.getContentPane();
		contenedor.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		//create objetos Escucha, control and others.
		escucha = new Escucha();
		vistaGUI = this;
		timer = new Timer(dificultad*1000, escucha);
		controlGame = new ControlAtentoYRapido(numeroCuadros);
		numeroFilas = controlGame.numberRows(numeroCuadros);
		numeroColumnas = controlGame.numberCols(numeroCuadros);
		BorderFactory.createLineBorder(Color.RED);
		blueLine = BorderFactory.createLineBorder(Color.BLUE);
		BorderFactory.createLineBorder(Color.BLACK);
		BorderFactory.createLineBorder(Color.CYAN);
		BorderFactory.createLineBorder(Color.WHITE);
		BorderFactory.createLineBorder(new Color(0,100,100));
		personalizatedColor = new Color(0,200,250);
		personalizatedLine = BorderFactory.createLineBorder(personalizatedColor);
		vidas = new ArrayList<JLabel>(3);
		cuadros = new ArrayList<JLabel>();
		
		//set up GUIComponents
		
		//title
		titulo = new Titulos("Juego Atento Y Rapido",30, darkGreen);
		compound = BorderFactory.createCompoundBorder(lightGreenLine, lightGreenLine);
		titulo.addMouseMotionListener(escucha);
		titulo.addMouseListener(escucha);
		titulo.setBorder(compound);
		titulo.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = numeroColumnas;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		this.add(titulo, constraints);
		
		//zona de cuadros
		zonaCuadros = new JPanel();
		ubicarCuadros(numeroCuadros);
		zonaCuadros.setBackground(Color.WHITE);
		makeSquares(numeroCuadros, lightGreen);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = numeroColumnas;
		constraints.gridheight = numeroFilas;
		constraints.fill = GridBagConstraints.HORIZONTAL ;
		this.add(zonaCuadros, constraints);
		
		//boton STOP, salir y Jugar
		Border compound = BorderFactory.createCompoundBorder(lightGreenLine, lightGreenLine);
		zonaBotones = new JPanel();
		zonaBotones.setLayout(new GridLayout(1,2));
		pausar = new JButton("Pausar");
		pausar.addActionListener(escucha);
		pausar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pausar.setBackground(darkGreen);
		pausar.setForeground(Color.WHITE);
		pausar.setFocusPainted(false);
		pausar.setBorder(compound);
		pausar.addMouseListener(escucha);
		reanudar = new JButton("Reanudar");
		reanudar.addActionListener(escucha);
		reanudar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		reanudar.setBackground(darkGreen);
		reanudar.setForeground(Color.WHITE);
		reanudar.setFocusPainted(false);
		reanudar.setBorder(compound);
		reanudar.addMouseListener(escucha);
		jugar = new JButton("Jugar");
		jugar.addActionListener(escucha);
		jugar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jugar.setBackground(darkGreen);
		jugar.setForeground(Color.WHITE);
		jugar.setFocusPainted(false);
		jugar.setBorder(compound);
		jugar.addMouseListener(escucha);
		salir = new JButton("Salir");
		salir.addActionListener(escucha);
		salir.setCursor(new Cursor(Cursor.HAND_CURSOR));
		salir.setBackground(darkGreen);
		salir.setForeground(Color.WHITE);
		salir.setFocusPainted(false);
		salir.setBorder(compound);
		salir.addMouseListener(escucha);
		stop = new JButton("STOP");
		stop.addActionListener(escucha);
		stop.setCursor(new Cursor(Cursor.HAND_CURSOR));
		stop.setBackground(darkGreen);
		stop.setForeground(Color.WHITE);
		stop.setFocusPainted(false);
		stop.setBorder(compound);
		stop.addMouseListener(escucha);
		zonaBotones.add(salir);
		zonaBotones.add(jugar);
		constraints.gridx = 0;
		constraints.gridy = 1+numeroFilas;
		constraints.gridwidth = numeroColumnas;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(zonaBotones, constraints);
		
		//Zona de puntuacion1: vidas, aciertos y errores
		titulo = new Titulos("Vidas",15, darkGreen);
		compound = BorderFactory.createCompoundBorder(lightGreenLine, lightGreenLine);
		zonaPuntuacion1 = new JPanel();
		valorAciertos = new JTextField(2);
		valorAciertos.setHorizontalAlignment(JTextField.CENTER);
		valorAciertos.setText(String.valueOf(0));
		valorAciertos.setBorder(compound);
		valorErrores = new JTextField(2);
		valorErrores.setHorizontalAlignment(JTextField.CENTER);
		valorErrores.setText(String.valueOf(0));
		valorErrores.setBorder(compound);
		zonaPuntuacion1.setBackground(Color.WHITE);
		zonaPuntuacion1.setLayout(new GridLayout(1,8));
		titulo.setBorder(compound);
		zonaPuntuacion1.add(titulo);
		vida = new JLabel();
		vida.setBorder(compound);
		zonaPuntuacion1.add(vida);
		vida = new JLabel();
		vida.setBorder(compound);
		zonaPuntuacion1.add(vida);
		vida = new JLabel();
		vida.setBorder(compound);
		zonaPuntuacion1.add(vida);
		createLifes();
		titulo = new Titulos("Aciertos",15, darkGreen);
		titulo.setBorder(compound);
		zonaPuntuacion1.add(titulo);
		zonaPuntuacion1.add(valorAciertos);
		titulo = new Titulos("Errores",15, darkGreen);
		titulo.setBorder(compound);
		zonaPuntuacion1.add(titulo);
		zonaPuntuacion1.add(valorErrores);
		constraints.gridx = 0;
		constraints.gridy = 6+numeroFilas;
		constraints.gridwidth = numeroColumnas; 
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(zonaPuntuacion1, constraints);
		
		//zona de puntuacion2: puntuacion total
		zonaPuntuacion2 = new JPanel();
		zonaPuntuacion1.setLayout(new GridLayout(1,2));
		titulo = new Titulos("Puntuación total",15, darkGreen);
		valorPuntuacion = new JTextField(9);
		valorPuntuacion.setHorizontalAlignment(JTextField.CENTER);
		valorPuntuacion.setText(String.valueOf(0));
		valorPuntuacion.setBorder(compound);
		zonaPuntuacion2.add(titulo);
		zonaPuntuacion2.setBackground(darkGreen);
		zonaPuntuacion2.setBorder(compound);
		zonaPuntuacion2.add(valorPuntuacion);
		constraints.gridx = 0;
		constraints.gridy = 11+numeroFilas;
		constraints.gridwidth = numeroColumnas;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(zonaPuntuacion2, constraints);
		
	}
	
	/**
	 * Resize frame. Redimensiona la ventana a medida que los cuadros aumentan.
	 */
	private void resizeFrame() {
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Ask for difficult. Pregunta la dificultad al usuario.
	 */
	private void askForDifficult() {
		//La dificultad imposible, es literalmente imposible.
		String dificultades[] = {"Fácil", "Normal", "Dificil", "Imposible"};
		String difficult;
		imagen = new ImageIcon(getClass().getClassLoader().getResource("difficult.png"));
		
		difficult = (String) JOptionPane.showInputDialog(jugar, "seleccione la dificultad del juego", 
															 "                                                 "
															 + "Dificultad", JOptionPane.QUESTION_MESSAGE, imagen, 
															 dificultades, "Normal");
		
		if(difficult == "Fácil") {
			dificultad = 3;
		}
		else if(difficult == "Normal") {
			dificultad = 2;
		}
		else if(difficult == "Dificil") {
			dificultad = 1;
		}
		else if(difficult == "Imposible") {
			dificultad = 9/10;
		}
		else {
			System.exit(0);
		}
	}
	
	/**
	 * Ask for squares. Pregunta la cantidad de cuadros para empezar la partida.
	 */
	private void askForSquares() {
		String alternativas[] = {"2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
		String nSquares;
		imagen = new ImageIcon(getClass().getClassLoader().getResource("question.png"));
		
		nSquares = (String) JOptionPane.showInputDialog(jugar, "Cuantos cuadros quieres poner al inicio?", 
															 "                                                 "
															 + "Cantidad de cuadros", JOptionPane.QUESTION_MESSAGE, imagen, 
															 alternativas, "2");
		
		if(nSquares == null) {
			
			String titulo = "                                               HASTA LUEGO!";
			imagen = new ImageIcon(getClass().getClassLoader().getResource("final.png"));
			JOptionPane.showMessageDialog(rootPane, "Espero que vuelvas a jugar pronto!" , titulo, JOptionPane.INFORMATION_MESSAGE, imagen);
			System.exit(0);
			
		}
		
		numeroCuadros = Integer.parseInt(nSquares);
	}
	
	/**
	 * Try again. Pregunta al usuario si quiere jugar de nuevo.
	 */
	private void tryAgain() {
		
		String titulo = "                                 JUGAR DE NUEVO?!";
		
		imagen = new ImageIcon(getClass().getClassLoader().getResource("perdiste.png"));
		
		String alternativas[] = {"Sí","No"};
		String desicion;
		imagen = new ImageIcon(getClass().getClassLoader().getResource("playAgain.png"));
		
		desicion = (String) JOptionPane.showInputDialog(jugar, "Quieres volver a jugar?", 
															 titulo, JOptionPane.QUESTION_MESSAGE, imagen, 
															 alternativas, "Sí");
		if(desicion == "Sí") {
			
			askForDifficult();
			
			String alternatives[] = {"2","3","4","5","6","7","8","9","14","15","16"};
			String squares;
			imagen = new ImageIcon(getClass().getClassLoader().getResource("question.png"));
			
			squares = (String) JOptionPane.showInputDialog(jugar, "Cuantos cuadros quieres poner al inicio?", 
																 "                                                          "
																 + "Dificultad", JOptionPane.QUESTION_MESSAGE, imagen, 
																 alternatives, "2");
			
			if(squares == null) {
				
				titulo = "                                               HASTA LUEGO!";
				imagen = new ImageIcon("src/Imagenes/final.png");
				JOptionPane.showMessageDialog(rootPane, "Espero que vuelvas a jugar pronto!" , titulo, JOptionPane.INFORMATION_MESSAGE, imagen);
				System.exit(0);
				
			}
			
			wind.setFramePosition(0);
			wind.start();
			
			numeroCuadros = Integer.parseInt(squares);
			
			timer.setDelay(dificultad*1000);
			timer.restart();
			valorAciertos = (JTextField) zonaPuntuacion1.getComponent(5);
			valorAciertos.setText("0");
			valorErrores = (JTextField) zonaPuntuacion1.getComponent(7);
			valorErrores.setText("0");
			valorPuntuacion = (JTextField) zonaPuntuacion2.getComponent(1);
			valorPuntuacion.setText("0");
			
			createLifes();
			controlGame.reiniciarJuego();
			controlGame.reiniciarCuadros(numeroCuadros);
			cuadros.clear();
			zonaCuadros.removeAll();
			makeSquares(numeroCuadros, lightGreen);
			ubicarCuadros(numeroCuadros);
			resizeFrame();
			changeDesingSquares(lightGreen);
		}
		
		else {
			titulo = "                                               HASTA LUEGO!";
			imagen = new ImageIcon(getClass().getClassLoader().getResource("final.png"));
			JOptionPane.showMessageDialog(rootPane, "Espero que vuelvas a jugar pronto!" , titulo, JOptionPane.INFORMATION_MESSAGE, imagen);
			System.exit(0);
		}
	}
	
	/**
	 * Removes the life. Remueve vidas del array de vidas y del panel de puntuacion 1.
	 *
	 * @param number the number. es el indice, la posicion de la n-esima vida a borrar.
	 */
	private void removeLife(int number) {
		
		compound = BorderFactory.createCompoundBorder(lightGreenLine, lightGreenLine);
		
		JLabel vida;
		
		vida = (JLabel) zonaPuntuacion1.getComponent(number);
		vida.setIcon(null);
		vida.setBorder(compound);
		
		zonaPuntuacion1.revalidate();
		vidas.remove(0);
		
	}
	
	/**
	 * Creates the lifes. Crea todas las vidas en el panel de puntuacion 1 y guarda imagenes vacias en el array de vidas.
	 */
	private void createLifes() {
		
		compound = BorderFactory.createCompoundBorder(lightGreenLine, lightGreenLine);
		
		JLabel life;
		
		for(int nLifes = 1; nLifes <= 3; nLifes++ ) {
			
			imagen = new ImageIcon(getClass().getClassLoader().getResource("lifes.png"));
			life = (JLabel) zonaPuntuacion1.getComponent(nLifes);
			vidas.add(null);
			life.setIcon(imagen);
			life.setBorder(compound);
			life.setHorizontalAlignment(JLabel.CENTER);
			
		}
	}
	
	/**
	 * Make squares. Crea todos los cuadros en el panel de cuadros.
	 *
	 * @param numberSquares the number squares. La cantidad de cuadros a crear.
	 * @param color the color. El color de los bordes de los cuadros.
	 */
	private void makeSquares(int numberSquares, Color color) {
		
		personalizatedLine = BorderFactory.createLineBorder(color);
		compound = BorderFactory.createCompoundBorder(personalizatedLine, personalizatedLine);
		
		for(int nCuadros = 0; nCuadros < numberSquares; nCuadros++) {
			int caraCuadro = controlGame.getCuadro(nCuadros+1).getCaraCuadro();
			
			imagen = new ImageIcon(getClass().getClassLoader().getResource("cuadro"+String.valueOf(caraCuadro)+".png"));
			cuadro = new JLabel(imagen);
			cuadro.setBorder(compound);
			zonaCuadros.add(cuadro);
			cuadros.add(cuadro);
			
			zonaCuadros.revalidate();
			zonaCuadros.repaint();
		}
	}
	
	/**
	 * Change desing square. Cambia el diseńo de un cuadro y le inserta el borde azul.
	 */
	private void changeDesingSquare() {
		
		compound = BorderFactory.createCompoundBorder(blueLine, blueLine);
		bordeAnterior = compound;
		
		//In this part I'll take an existing JLabel from JPanel.
		Random aleatorio = new Random();
		int sustituto, index;
		
		int number = aleatorio.nextInt( controlGame.cuantosCuadrados() ) + 1;
	
		//JLabel caraCuadro;
		JLabel cuadro;
		
		index = number-1;
		
		//caraCuadro = cuadros.get(index);
		cuadro = (JLabel) zonaCuadros.getComponent(index);
		
		//Here I'll take a new random image
		number = aleatorio.nextInt(27) + 1;
		
		while(number == controlGame.getCuadro(index+1).getCaraCuadro()) {
			number = aleatorio.nextInt(27) + 1;
		}
		
		sustituto = number;
		controlGame.reemplazarCuadro(index, sustituto);
		
		imagen = new ImageIcon(getClass().getClassLoader().getResource("cuadro" + String.valueOf(number) + ".png"));
	
		cuadro.setIcon(imagen);
		cuadro.setBorder(compound);
		
		zonaCuadros.revalidate();
		zonaCuadros.repaint();
		
	}
	
	/**
	 * Erase blue border. Borra el borde azul de un cuadro antes de que otro cambie.
	 *
	 * @param color the color. El color del borde del nuevo cuadro.
	 */
	private void eraseBlueBorder(Color color) {
		//this loop works for change border before set the other frame.
		for(int nCuadro = 0; nCuadro < cuadros.size(); nCuadro++) {
			
			personalizatedLine = BorderFactory.createLineBorder(color);
			compound = BorderFactory.createCompoundBorder(personalizatedLine, personalizatedLine);
			
			cuadro = (JLabel) zonaCuadros.getComponent(nCuadro);

			if(cuadro.getBorder() == bordeAnterior) {
				
				cuadro.setBorder(compound);
				zonaCuadros.revalidate();
				zonaCuadros.repaint();

			}

		}
	}
	
	/**
	 * Integer labels. Después de reiniciar la ronda, integra las imagenes nuevas en el panel de cuadros.
	 *
	 * @param color the color. Color del borde de los cuadros.
	 */
	private void integerLabels(Color color) {
		
		JLabel caraCuadro;
		JLabel cuadro;
		
		personalizatedLine = BorderFactory.createLineBorder(color);
		compound = BorderFactory.createCompoundBorder(personalizatedLine, personalizatedLine);
		
		//here i'm including again the frames
		for(int nCuadro = 0; nCuadro < controlGame.cuantosCuadrados(); nCuadro++) {
			
			imagen = new ImageIcon(getClass().getClassLoader().getResource("cuadro" + String.valueOf(controlGame.getCuadro(nCuadro+1).getCaraCuadro()) + ".png"));
			caraCuadro = cuadros.get(nCuadro);
			cuadro = (JLabel) zonaCuadros.getComponentAt(caraCuadro.getX(), caraCuadro.getY());
			
			caraCuadro.setIcon(imagen);
			caraCuadro.setBorder(compound);
			cuadro.setIcon(imagen);
			cuadro.setBorder(compound);
			
			zonaCuadros.revalidate();
			zonaCuadros.repaint();
			
		}
	}
	
	/**
	 * Change desing squares. Cambia el diseńo de los cuadros, en el reinicio de la ronda
	 *
	 * @param color the color. Color del borde de los cuadros.
	 */
	private void changeDesingSquares(Color color) {
		
		personalizatedLine = BorderFactory.createLineBorder(color);
		compound = BorderFactory.createCompoundBorder(personalizatedLine, personalizatedLine);
	
		JLabel cuadro;
		int disehnoCuadro;
		
		//here i changed all the designs from JLabel
		for(int nCuadro = 0; nCuadro < cuadros.size(); nCuadro++) {
			
			cuadro = (JLabel) zonaCuadros.getComponent(nCuadro);
			
			disehnoCuadro = controlGame.getCuadro(nCuadro+1).getCaraCuadro();
			imagen = new ImageIcon(getClass().getClassLoader().getResource("cuadro" + String.valueOf(disehnoCuadro) + ".png"));
			
			cuadro.setIcon(imagen);
			cuadro.setBorder(compound);
			
			zonaCuadros.revalidate();
			zonaCuadros.repaint();
			
		}
		
	}
	
	/**
	 * Adds the label. Ańade un nuevo cuadro a la zona de cuadros.
	 *
	 * @param color the color. Borde del cuadro a ańadir.
	 */
	private void addLabel(Color color) {
		
		personalizatedLine = BorderFactory.createLineBorder(color);
		compound = BorderFactory.createCompoundBorder(personalizatedLine, personalizatedLine);
		integerLabels(color);
		controlGame.adicionarCuadro();
		
		imagen = new ImageIcon(getClass().getClassLoader().getResource("cuadro" + 
							   String.valueOf( controlGame.getCuadro(controlGame.cuantosCuadrados()).getCaraCuadro() ) + ".png"));
		cuadro = new JLabel(imagen);
		cuadro.setBorder(compound);
		cuadros.add(cuadro);
		
		zonaCuadros.add(cuadro);
		zonaCuadros.revalidate();
		zonaCuadros.repaint();
	
	}
	
	/**
	 * Ubicar cuadros. Cambia las dimensiones del GridBagLayout, esto para evitar que la ventana se deforme.
	 *
	 * @param number the number. Numero de cuadros en la zona de cuadros.
	 */
	private void ubicarCuadros(int number) {
		zonaCuadros.setLayout(new GridLayout(controlGame.numberCols(number),controlGame.numberRows(number)));
	}
	 
	/**
	 * The Class Escucha. Esta clase es la que se encarga de reaccionar ante las interacciones del usuario.
	 */
	private class Escucha implements ActionListener, MouseListener, MouseMotionListener{
		
		/** The x. coordenada x del mouse */
		/** The y. coordenada y del mouse */
		private int x, y;
		
		/**
		 * Action performed. Es la funcion que recibe un evento de accion y dependiendo de este ejecuta
		 * una instrucción.
		 *
		 * @param event the event. Es el evento que se recibe por parte de la interacción del usuario.
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			
			//Si da clic al boton salir
			if(event.getSource() == salir) {
				
				System.exit(0);
				
			}
			
			//Si da clic al boton pausar
			else if(event.getSource() == pausar) {
				
				pause.setFramePosition(0);
				pause.start();
				timer.stop();
				zonaBotones.remove(1);
				zonaBotones.add(reanudar, 1);
				zonaBotones.revalidate();

			}
			//Si da clic al boton reanudar.
			else if(event.getSource() == reanudar) {
				wind.setFramePosition(0);
				wind.start();
				timer.start();
				zonaBotones.remove(1);
				zonaBotones.add(pausar, 1);
				zonaBotones.revalidate();
			}
			
			//Si da clic al boton jugar.
			else if(event.getSource() == jugar) {
				wind.setFramePosition(0);
				wind.start();
				
				zonaBotones.remove(jugar);
				zonaBotones.add(pausar);
				zonaBotones.add(stop);
				
				zonaBotones.revalidate();
				zonaBotones.repaint();
				
				timer.start();
				
			}
			
			//Si el temporizador se ejecuta.
			else if(event.getSource() == timer) {
				
				controlGame.determinarEstado();
				
				switch(controlGame.getEstado()) {
				
				case 1: //perder partida
						fail.setFramePosition(0);
						fail.start();
						
						String titulo = "                                             PERDISTE!";
						imagen = new ImageIcon(getClass().getClassLoader().getResource("perdiste.png"));
						JOptionPane.showMessageDialog(rootPane, "Tu puntuación final fue: " + controlGame.getPuntuacion() + "\n" + 
												  	  "Tuviste " + controlGame.getNumeroAciertos() + " aciertos y " + 
												  	  controlGame.getNumeroErrores() + 
												  	  " errores." , titulo, JOptionPane.INFORMATION_MESSAGE, imagen);
						
						
						tryAgain();
					
						break;
						
				case 2: //cambio de ronda - error
						wrong.setFramePosition(0);
						wrong.start();
					
						removeLife(vidas.size());
	
						controlGame.calcularPuntuacion(false);
	
						valorAciertos.setText(String.valueOf(controlGame.getNumeroAciertos()));
						valorErrores.setText(String.valueOf(controlGame.getNumeroErrores()));
						valorPuntuacion.setText(String.valueOf(controlGame.getPuntuacion()));
						
						controlGame.reiniciarCuadros(controlGame.cuantosCuadrados());
						
						changeDesingSquares(lightGreen);
						ubicarCuadros(cuadros.size());
						resizeFrame();
						
						break;
						
				case 3: //cambio de cuadro
						
						eraseBlueBorder(lightGreen);
						changeDesingSquare();
					
						break;
						
				}

			}
				
			else{
				//Si da clic al boton stop
				if(event.getSource() == stop) {
					//Si no hay vidas.
					if(vidas.size() == 0) {
						wrong.setFramePosition(0);
						wrong.start();
						//this condition is for avoid an error,
					}
					//Si hay cuadros iguales
					else if(controlGame.hayCuadrosIguales()) {
						right.setFramePosition(0);
						right.start();
						
						controlGame.calcularPuntuacion(true);
						
						valorAciertos.setText(String.valueOf(controlGame.getNumeroAciertos()));
						valorErrores.setText(String.valueOf(controlGame.getNumeroErrores()));
						valorPuntuacion.setText(String.valueOf(controlGame.getPuntuacion()));
						
						//cambio de ronda - acierto
						
						controlGame.reiniciarCuadros(controlGame.cuantosCuadrados());
						
						if(controlGame.cuantosCuadrados() < 16) {
							addLabel(lightGreen);
						}
						
						changeDesingSquares(lightGreen);
						ubicarCuadros(cuadros.size());
						resizeFrame();
					}
					//Si no hay cuadros iguales.
					else {
						wrong.setFramePosition(0);
						wrong.start();
						
						removeLife(vidas.size());

						controlGame.calcularPuntuacion(true);

						valorAciertos.setText(String.valueOf(controlGame.getNumeroAciertos()));
						valorErrores.setText(String.valueOf(controlGame.getNumeroErrores()));
						valorPuntuacion.setText(String.valueOf(controlGame.getPuntuacion()));
						
						//cambio de ronda - error
						
						controlGame.reiniciarCuadros(controlGame.cuantosCuadrados());
						
						changeDesingSquares(lightGreen);
						ubicarCuadros(cuadros.size());
						resizeFrame();
					}
		
				}
				
			}
		}

		/**
		 * Mouse clicked. Este metodo se ejecuta cuando doy clic y suelto el boton izquierdo del mouse
		 *
		 * @param eventMouse the event mouse. Es el evento generado por dicha interaccion.
		 */
		@Override
		public void mouseClicked(MouseEvent eventMouse) {
			// TODO Auto-generated method stub
			
		}

		/**
		 * Mouse pressed. Este metodo se ejecuta cuando mantengo presionado el boton izquierdo del mouse.
		 *
		 * @param eventMouse the event mouse. Es el evento generado por dicha interaccion.
		 */
		@Override
		public void mousePressed(MouseEvent eventMouse) {
			// TODO Auto-generated method stub
			x = eventMouse.getX();
			y = eventMouse.getY();
		}

		/**
		 * Mouse released. Este metodo se ejecuta cuando suelto el boton izquierdo del mouse.
		 *
		 * @param eventMouse the event mouse. Es el evento generado por dicha interaccion.
		 */
		@Override
		public void mouseReleased(MouseEvent eventMouse) {
			// TODO Auto-generated method stub
			
		}

		/**
		 * Mouse entered. Este metodo se ejecuta cuando el cursor del mouse entra por un componente gráfico.
		 *
		 * @param eventMouse the event mouse. Es el evento generado por dicha interaccion.
		 */
		@Override
		public void mouseEntered(MouseEvent eventMouse) {
			// TODO Auto-generated method stub
	    	if(eventMouse.getSource().getClass() == JButton.class) {
	    		rollOver.setFramePosition(0);
	    		rollOver.start();
	    		eventMouse.getComponent().setBackground(new Color(128,255,102));
	    	}
		}

		/**
		 * Mouse exited. Este metodo se ejecuta cuando el cursor del mouse sale de un componente gráfico.
		 *
		 * @param eventMouse the event mouse. Es el evento generado por dicha interaccion.
		 */
		@Override
		public void mouseExited(MouseEvent eventMouse) {
			// TODO Auto-generated method stub
			if(eventMouse.getSource().getClass() == JButton.class) {
				eventMouse.getComponent().setBackground(darkGreen);
			}
		}
		
		/**
		 * Mouse dragged. Se ejecuta cuando intento arrastrar algo con el mouse.
		 *
		 * @param eventMouse the event mouse. Es el evento generado por dicha interaccion.
		 */
		@Override
		public void mouseDragged(MouseEvent eventMouse) {
			// TODO Auto-generated method stub
			setLocation(vistaGUI.getLocation().x + eventMouse.getX()-x,
						vistaGUI.getLocation().y + eventMouse.getY()-y);
		}

		/**
		 * Mouse moved. Se ejecuta cuando muevo el mouse dentro de los limites de un componente gráfico.
		 *
		 * @param eventMouse the event mouse. Es el evento generado por dicha interaccion.
		 */
		@Override
		public void mouseMoved(MouseEvent eventMouse) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
