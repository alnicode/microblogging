package com.danicode.microblogging.gui.mainmenu;

import com.danicode.microblogging.gui.model.GUIDialog;

import javax.swing.*;
import java.awt.*;

public class GUIAboutUs extends GUIDialog {
    private JPanel northPane, centerPane, southPane;
    private JLabel lWelcome, lAuthor;
    private JScrollPane spAbout;
    private JButton bLinkdIn, bGitHub, bAccept;

    public GUIAboutUs(JFrame owner) {
        super(owner, 500, 420, "Acerca de", true, true, new BorderLayout());
    }

    private void createPanels() {
        this.northPane = new JPanel();
        this.centerPane = new JPanel();
        this.southPane = new JPanel();

        this.northPane.setPreferredSize(new Dimension(500, 70));

        this.northPane.setLayout(new FlowLayout(FlowLayout.LEFT, 18, 10));
        this.centerPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.southPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 18, 10));
    }

    private void createLabels() {
        this.lWelcome = new JLabel("Microblogging 1.0");
        this.lWelcome.setFont(this.lWelcome.getFont().deriveFont(Font.BOLD, 24f));
        this.lWelcome.setForeground(new Color(103, 7, 129));

        this.lAuthor = new JLabel("Creador y Desarrollador: danicode32");
    }

    private void createButtons() {
        this.bAccept = new JButton("Aceptar");
        this.bAccept.addActionListener(e -> this.dispose());

        this.bGitHub = new JButton("GitHub");
        this.bLinkdIn = new JButton("LinkedIn");
    }

    private void createAreaText() {
        var taAbout = new JTextArea(
                "\nEs un software básico basado en la web de Twitter.\n\n" +
                "De inicio te habrás dado cuenta que es necesario registrarse, puedes crearte más de una" +
                "cuenta (preferiblemente con correos y contraseñas ficticias) y publicar mensajes cortos," +
                "de máximo 140 caracteres, lo cual fue por lo que se caracterizó Twitter en sus inicios.\n" +
                "Puedes ver los mensajes publicados por otros usuarios, así como ver el perfil de cada usuario" +
                "y su información." +
                "\n\nSi te ha gustado el programa, puedes seguirme en GitHub y ver mi perfil de LinkedIn" +
                "\nIgualmente, si tienes alguna sugerencia, será bien recibida.");

        taAbout.setWrapStyleWord(true);
        taAbout.setLineWrap(true);
        taAbout.setEditable(false);
        taAbout.setBackground(this.centerPane.getBackground());

        this.spAbout = new JScrollPane(taAbout);
        this.spAbout.setBorder(null);
        this.spAbout.setPreferredSize(new Dimension(470, 220));
    }

    private void design() {
        this.northPane.add(this.lWelcome);
        this.northPane.add(this.lAuthor);

        this.centerPane.add(this.spAbout);
        this.centerPane.add(this.bGitHub);
        this.centerPane.add(this.bLinkdIn);

        this.southPane.add(this.bAccept);

        this.centerPane.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));

        this.getContentPane().add(this.northPane, BorderLayout.NORTH);
        this.getContentPane().add(this.centerPane, BorderLayout.CENTER);
        this.getContentPane().add(this.southPane, BorderLayout.SOUTH);
    }

    @Override
    protected void close() {
        this.dispose();
    }

    @Override
    protected void init() {
        this.createPanels();
        this.createAreaText();
        this.createLabels();
        this.createButtons();
        this.design();
    }
}

class test {
    public static void main(String[] args) {
        new GUIAboutUs(null);
    }
}