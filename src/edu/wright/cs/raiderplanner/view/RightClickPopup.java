/*
 * Copyright (C) 2018
 *
 *
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package edu.wright.cs.raiderplanner.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

/**
 * @author Dominick Hatton. Class that implements a right click pop up menu function.
 */
public class RightClickPopup implements ActionListener, ItemListener {
	JMenuItem item;
	JTextArea output;

	/**
	 * Initializes the popup menu.
	 */
	public void createPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		item = new JMenuItem("Delete");
		item.addActionListener(this);
		popup.add(item);
		// Add listener to the text area so the popup menu can come up.
		MouseListener popupListener = new PopupListener(popup);
		output.addMouseListener(popupListener);
	}

	/**
	 * Listens for a right click to create a popup menu.
	 */
	class PopupListener extends MouseAdapter {
		JPopupMenu menu;

		/**
		 * @param popupMenu.
		 *
		 */
		PopupListener(JPopupMenu popupMenu) {
			menu = popupMenu;
		}

		/**
		 * Called when there is a right click pressed.
		 *
		 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
		 */
		public void mousePressed(MouseEvent me) {
			showPopup(me);
		}

		/**
		 * Called when there is a right click release.
		 *
		 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
		 */
		public void mouseReleased(MouseEvent me) {
			showPopup(me);
		}

		/**
		 * When there is a right click event triggered, this method will begin to display the menu.
		 */
		private void showPopup(MouseEvent me) {
			if (me.isPopupTrigger()) {
				menu.show(me.getComponent(), me.getX(), me.getY());
			}
		}
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent me) {
		// TODO Auto-generated method stub

	}

	/**
	 * (non-Javadoc)
	 *
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
