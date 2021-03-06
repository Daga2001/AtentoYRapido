/*
 * Author: David Alberto Guzm?n
 */

package atentoYRapido;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import misComponentes.Titulos;

// TODO: Auto-generated Javadoc
/**
 * The Class InstructionsGUI. The frame which displays instructions.
 */
public class InstructionsGUI extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The image icon. */
	//attributes
	private ImageIcon imageIcon;
	
	/** The listener. */
	private Listener listener;
	
	/** The buttons panel. */
	private JPanel buttonsPanel;
	
	/** The exit. */
	private JButton play, exit;
	
	/** The instruction gif. */
	private JLabel background, instructionsBody, instructionGif;
	
	/** The constraints. */
	private GridBagConstraints constraints;
	
	/** The g 2 d. */
	private Graphics2D g2d;
	
	/** The b image. */
	private BufferedImage bImage;
	
	/** The img. */
	private Image img;
	
	/** The line inner border. */
	private Border compound, lineOuterBorder, lineInnerBorder;
	
	/** The my GUI. */
	private JFrame myGUI;
	
	/** The audio in. */
	private AudioInputStream audioIn;
	
	/** The roll over. */
	private Clip postClicked, rollOver;
	
	/**
	 * Instantiates a new instructions GUI.
	 */
	public InstructionsGUI() {
		
		//inits the GUI
		init();
		
		//default window configuration
		this.setUndecorated(true);
		this.pack();
		this.getContentPane().setBackground(new Color(4,26,0));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	/**
	 * Inits the GUI.
	 */
	private void init() {
		
		//set window icon
		imageIcon = new ImageIcon(getClass().getClassLoader().getResource("mainIcon.png"));
		this.setIconImage(imageIcon.getImage());
		
		//listener and constraints
		listener = new Listener();
		constraints = new GridBagConstraints();
		
		//audio
		try {
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("post-click sound effect.wav"));
			postClicked = AudioSystem.getClip();
			postClicked.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("rollover-sound-effect.wav"));
			rollOver = AudioSystem.getClip();
			rollOver.open(audioIn);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error! audio file not found!");
		}
		
		//myGUI
		myGUI = this;
		
		//make GUI
		//background
		lineOuterBorder = BorderFactory.createLineBorder(new Color(43,255,0));
		lineInnerBorder = BorderFactory.createLineBorder(new Color(43,255,0));
		compound = BorderFactory.createCompoundBorder(lineOuterBorder, lineInnerBorder);
		img = new ImageIcon(getClass().getClassLoader().getResource("instructions-background.gif")).getImage();
		imageIcon = new ImageIcon(img);
		background = new JLabel();
		background.setIcon(imageIcon);
		background.setBorder(compound);
		background.setLayout(new GridBagLayout());
		
		//title
		Titulos tittle = new Titulos("Instrucciones", 40, null);
		tittle.setOpaque(false);
		tittle.setForeground(new Color(25,255,25));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		constraints.weighty = 0.01;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		background.add(tittle, constraints);
		
		//instructions background
		String text = "<html><body style='text-align: center;'>Pon mucha atenci?n a cada cuadro que cambia, deber?s pulsar el bot?n"
				+ " de stop cuando se muestren dos im?genes iguales ante tu vista, perder?s vidas si no pulsas cuando haya dos im?genes iguales en pantalla"
				+ " o si pulsas el bot?n en el momento inadecuado. Puedes tambi?n pausar el juego cuando desees. "
				+ "Buena suerte, dale clic al bot?n de play cuando est?s preparad@</body></html>";
		instructionsBody = new JLabel(text);
		instructionsBody.setFont(newFont(Font.SERIF, Font.BOLD+Font.ITALIC, 25));
		instructionsBody.setForeground(Color.WHITE);
		instructionsBody.setOpaque(false);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 3;
		background.add(instructionsBody, constraints);
		
		//Gif Image
		imageIcon = new ImageIcon(getClass().getClassLoader().getResource("instruction.gif"));
		instructionGif = new JLabel(imageIcon);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 3;
		background.add(instructionGif, constraints);
		
		//exit and play buttons
		buttonsPanel = new JPanel();
		buttonsPanel.setOpaque(false);
		Color colorButtons= new Color(119,255,51);
		Color foregroundButtons = new Color(51,102,0);
		LineBorder borderButtons = new LineBorder(new Color(51,153,0), 5, true);
		Font fontButtons = newFont(Font.SERIF, Font.BOLD+Font.ITALIC, 25);
		play = new JButton("Play");
		play.setCursor(new Cursor(Cursor.HAND_CURSOR));
		play.setBackground(colorButtons);
		play.setForeground(foregroundButtons);
		play.setBorder(borderButtons);
		play.setFocusPainted(false);
		play.setFont(fontButtons);
		play.addActionListener(listener);
		play.addMouseListener(listener);
		exit = new JButton("Salir");
		exit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		exit.setBackground(colorButtons);
		exit.setForeground(foregroundButtons);
		exit.setBorder(borderButtons);
		exit.setFocusPainted(false);
		exit.setFont(fontButtons);
		exit.addActionListener(listener);
		exit.addMouseListener(listener);
		buttonsPanel.setLayout(new GridBagLayout());
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0.7;
		buttonsPanel.add(play, constraints);
		constraints.gridx = 3;
		constraints.gridwidth = 1;
		buttonsPanel.add(exit, constraints);
		//------------space btw btns--------------------
		int space = 50;
		constraints.gridx = 0;
		constraints.gridwidth = 1;
		buttonsPanel.add(Box.createHorizontalStrut(space),constraints);
		constraints.gridx = 2;
		constraints.gridwidth = 1;
		buttonsPanel.add(Box.createHorizontalStrut(0),constraints);
		constraints.gridx = 4;
		constraints.gridwidth = 1;
		buttonsPanel.add(Box.createHorizontalStrut(space),constraints);
		//----------------------------------------------
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 3;
		background.add(buttonsPanel, constraints);
		
		this.add(background, BorderLayout.NORTH);

	}

	/**
	 * Sets the transparency.
	 *
	 * @param image the image
	 * @param flt the flt
	 * @return the image
	 */
	@SuppressWarnings("unused")
	private Image setTransparency(Image image, Float flt) {
		bImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g2d = (Graphics2D) bImage.createGraphics();
		//here i set the transparency of the background
		g2d.setComposite(AlphaComposite.SrcOver.derive(flt));
		g2d.drawImage(image, 0, 0, null);
		return bImage;
	}
	
	/**
	 * New font. sets a new font.
	 *
	 * @param family the family
	 * @param style the style
	 * @param size the size
	 * @return the font
	 */
	private Font newFont(String family, int style, int size) {
		Font font= new Font(family, style, size);
		return font;
	}
	
	/**
	 * The Class Listener.
	 */
	private class Listener extends MouseAdapter implements ActionListener{

		/**
		 * Action performed.
		 *
		 * @param event the event
		 */
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			if(event.getSource() == play) {
				myGUI.setVisible(false);
				VistaGUIAtentoYRapido myVista = new VistaGUIAtentoYRapido();
				myVista.setVisible(true);
			}
			if(event.getSource() == exit) {
				System.exit(0);
			}
		}
		
		/**
		 * Mouse pressed.
		 *
		 * @param e the e
		 */
		public void mousePressed(MouseEvent e) {
			if(e.getSource() == play) {
				postClicked.setFramePosition(0);
				postClicked.start();
			}
		}
		
		/**
		 * Mouse exited.
		 *
		 * @param e the e
		 */
		public void mouseExited(MouseEvent e) {
			if(e.getSource().getClass() == JButton.class) {
				e.getComponent().setBackground(new Color(119,255,51));
			}
		}

	    /**
    	 * Mouse entered.
    	 *
    	 * @param e the e
    	 */
    	public void mouseEntered(MouseEvent e) {
	    	if(e.getSource().getClass() == JButton.class) {
	    		rollOver.stop();
	    		rollOver.setMicrosecondPosition(0);
	    		rollOver.start();
	    		e.getComponent().setBackground(new Color(170,255,153));
	    	}
	    }
		
	}
	
}
