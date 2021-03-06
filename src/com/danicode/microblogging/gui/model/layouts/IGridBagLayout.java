package com.danicode.microblogging.gui.model.layouts;

import javax.swing.*;
import java.awt.*;

/**
 * Con esta interface, es mucho más fácil el uso de GridBagLayout.
 * <p>Para usarla, es necesario sobreescribir el método {@code pane()} para así,
 * indicar cuál es panel que va a utilizar el layout.</p>
 * <p>Al tener sólo un método abstracto, puedes usar el paradigma funcional.</p>
 */
@FunctionalInterface
public interface IGridBagLayout {
    /**
     * Espacio horizontal por defecto.
     */
    String HSPACE = "       ";
    /**
     * Espacio vertical o salto de línea por defecto.
     */
    String VSPACE = "<html><body> <br/> </body></html>";
    /**
     * Restricción utilizada por defecto, no es necesario crear más puesto que sólo
     * es un auxiliar para indicar las restricciones del elemento a añadir en el {@code pane()}.
     */
    GridBagConstraints GBC = new GridBagConstraints();

    /**
     * Es necesario sobreescribirlo para que los métodos puedan funcionar.
     * @return Es necesario que devuelva con panel que tenga GridBagLayout como layout.
     */
    JPanel pane();

    /**
     * Añade un componente gráfico con restricciones a un panel.
     * @param x índice inicial de la columna
     * @param y índice inicial de la fila
     * @param width ancho de la columna
     * @param height alto de la columna
     * @param wx relación de aspecto horizontal
     * @param wy relación de aspecto vertical
     * @param fill cómo debe estirarse la columna
     * @param obj componente gráfico a añadir en la columna
     */
    default void addGBC(int x, int y, int width, int height,
                        double wx, double wy, int fill, Component obj) {
        GBC.gridx = x;
        GBC.gridy = y;
        GBC.gridwidth = width;
        GBC.gridheight = height;
        GBC.weightx = wx;
        GBC.weighty = wy;
        GBC.fill = fill;
        pane().add(obj, GBC);
    }

    /**
     * Añade un espacio vertical y además un espacio de una sóla columna de forma horizontal.
     * En total añade dos filas.
     * @param y índice de la fila inicial
     * @param width ancho de las filas
     */
    default void addSpaces(int y, int width) {
        addGBC(0, y, width, 1, 1.0, 1.0, GridBagConstraints.BOTH, new JLabel(VSPACE));
        addGBC(0, y + 1, 1, 1, 0, 0, GridBagConstraints.NONE, new JLabel(HSPACE));
    }

    /**
     * Este método añade un espacio en blanco en la última columna de la fila.
     * Luego, añade una fila en blanco y finalmente, otra línea con un espacio en blanco en la primera columna.
     * En total añade dos filas.
     * @param width cuántas columnas de ancho tienen las filas
     * @param y índice de la fila inicial
     */
    default void addFinalSpaces(int width, int y) {
        addGBC(width - 1, y, 1, 1, 0, 0, GridBagConstraints.NONE, new JLabel(HSPACE));
        addSpaces(y + 1, width);
    }

    /**
     * Añade dos componentes gráficos uno al lado del otro y no agrega espacios, por lo que ocupa dos espacios horizontales.
     * @param x índice de la columna inicial
     * @param y índice de la fila inicial
     * @param width1 ancho del primer componente
     * @param height1 alto del primer componente
     * @param wx1 escala horizontal del primer componente
     * @param wy1 escala vertical del primer componente
     * @param fill1 cómo se debe ajustar el primer componente
     * @param obj1 primer componente
     * @param width2 ancho del segundo componente
     * @param height2 alto del segundo componente
     * @param wx2 escala horizontal del segundo componente
     * @param wy2 escala vertical del segundo componente
     * @param fill2 cómo se debe ajustar el segundo componente
     * @param obj2 segundo componente
     */
    default void addHorizontal(int x, int y,
                               int width1, int height1, double wx1, double wy1, int fill1, Component obj1,
                               int width2, int height2, double wx2, double wy2, int fill2, Component obj2) {
        addGBC(x, y, width1, height1, wx1, wy1, fill1, obj1);
        addGBC(x + 1, y, width2, height2, wx2, wy2, fill2, obj2);
    }

    /**
     * Añade un espacio vertical y luego dos componentes uno debajo del otro, por lo que ocupa 3 líneas verticales.
     * @param x índice inicial de la columna
     * @param y índice inicial de la fila
     * @param width largo de ambos componentes
     * @param wx1 escala horizontal del primer componente
     * @param wy1 escala vertical del primer componente
     * @param fill1 cómo se debe ajustar el primer componente
     * @param obj1 primer componente
     * @param wx2 escala horizontal del segundo componente
     * @param wy2 escala vertical del segundo componente
     * @param fill2 cómo se debe ajustar el segundo componente
     * @param obj2 segundo componente
     */
    default void addVertical(int x, int y, int width,
                             double wx1, double wy1, int fill1, Component obj1,
                             double wx2, double wy2, int fill2, Component obj2) {
        addGBC(x, y, width, 1, 1.0, 1.0, GridBagConstraints.BOTH, new JLabel(VSPACE));
        addGBC(x, y + 1, width, 1, wx1, wy1, fill1, obj1);
        addGBC(x, y + 2, width, 1, wx2, wy2, fill2, obj2);
    }
}
