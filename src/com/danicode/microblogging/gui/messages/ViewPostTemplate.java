package com.danicode.microblogging.gui.messages;

import com.danicode.microblogging.gui.model.layouts.IGridBagLayout;

import javax.swing.*;
import java.awt.*;

public class ViewPostTemplate extends JPanel {
    private JPopupMenu popupMenu;
    private JLabel lFullName, lUsername, lDateTime;
    private JTextArea taMessage;
    private JScrollPane spMessage;
    private IGridBagLayout gbc;
    private final JMenuItem[] menuItems = new JMenuItem[3];

    public ViewPostTemplate() {
        this.init();
    }

    private void createPopupMenu() {
        this.popupMenu = new JPopupMenu();
        String[] titles = {"Ver perfil", "Editar mensaje", "Eliminar mensaje"};
        for (int i = 0; i < this.menuItems.length; i ++) {
            this.menuItems[i] = new JMenuItem(titles[i]);
            this.popupMenu.add(this.menuItems[i]);
        }
    }

    private void createLabels() {
        this.lFullName = new JLabel();
        this.lUsername = new JLabel();
        this.lDateTime = new JLabel();
    }

    private void createMessageArea() {
        this.taMessage = new JTextArea(7, 10);
        this.taMessage.setWrapStyleWord(true);
        this.taMessage.setLineWrap(true);
        this.taMessage.setBackground(this.getBackground());
        this.taMessage.setEditable(false);
        this.spMessage = new JScrollPane(this.taMessage);
        this.spMessage.setBorder(null);
    }

    private void initLayout() {
        this.gbc = () -> this;
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    private void design() {
        final int WIDTH = 4;
        this.gbc.GBC.ipadx = 8;
        this.gbc.addSpaces(0, WIDTH);

        this.gbc.addGBC(1, 1, 1, 1, 1.0, 0, GridBagConstraints.HORIZONTAL, this.lFullName);
        this.gbc.addGBC(2, 1, 1, 1, 1.0, 0, GridBagConstraints.HORIZONTAL, this.lUsername);
        this.gbc.addFinalSpaces(WIDTH, 1);

        this.gbc.addGBC(1, 3, 2, 1, 1.0, 0, GridBagConstraints.HORIZONTAL, this.lDateTime);
        this.gbc.addFinalSpaces(WIDTH, 3);
        this.gbc.addGBC(0, 5, 4, 1, 1.0, 0, GridBagConstraints.HORIZONTAL, new JSeparator());
        this.gbc.addFinalSpaces(WIDTH, 5);

        this.gbc.addSpaces(7, WIDTH);
        this.gbc.addSpaces(9, WIDTH);
        this.gbc.addSpaces(11, WIDTH);
        this.gbc.addSpaces(13, WIDTH);
        this.gbc.addSpaces(15, WIDTH);
        this.gbc.addGBC(1, 7, 2, 9, 1.0, 1.0, GridBagConstraints.BOTH, this.spMessage);
    }

    private void init() {
        this.setPreferredSize(new Dimension(500, 240));
        this.createPopupMenu();
        this.createLabels();
        this.createMessageArea();
        this.initLayout();
        this.design();
    }

    public JLabel getLFullName() { return this.lFullName; }

    public JLabel getLUsername() { return this.lUsername; }

    public JLabel getLDateTime() { return this.lDateTime; }

    public JTextArea getTaMessage() { return this.taMessage; }

    public JPopupMenu getPopupMenu() { return this.popupMenu; }

    public JMenuItem getMenuViewProfile() { return this.menuItems[0]; }

    public JMenuItem getMenuEditMessage() { return this.menuItems[1]; }

    public JMenuItem getMenuDeleteMessages() { return this.menuItems[2]; }
}
