/*
 * This file is part of org.kalmeo.kuix.
 * 
 * org.kalmeo.kuix is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.kalmeo.kuix is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with org.kalmeo.kuix.  If not, see <http://www.gnu.org/licenses/>.
 *  
 * Creation date : 21 nov. 2007
 * Copyright (c) Kalmeo 2007-2008. All rights reserved.
 * http://www.kalmeo.org
 */

package org.kalmeo.kuix.widget;

import org.kalmeo.kuix.core.KuixConstants;
import org.kalmeo.kuix.core.focus.FocusManager;
import org.kalmeo.kuix.layout.BorderLayout;
import org.kalmeo.kuix.layout.BorderLayoutData;
import org.kalmeo.kuix.layout.Layout;
import org.kalmeo.kuix.layout.LayoutData;
import org.kalmeo.kuix.layout.StaticLayoutData;
import org.kalmeo.kuix.util.Alignment;
import org.kalmeo.util.worker.Worker;
import org.kalmeo.util.worker.WorkerTask;

/**
 * This class represent a popup box.
 * 
 * <table border="1" width="100%" cellpadding="3" cellspacing="0" >
 * 	<tr class="TableHeadingColor">
 * 		<th align="left" colspan="5"><font size="+2"> Attributes </font></th>
 * 	</tr>
 * 	<tr class="TableRowColor">
 * 		<th width="1%"> Attribute </th>
 * 		<th width="1%"> Object </th>
 * 		<th width="1%"> Set </th>
 * 		<th width="1%"> Get </th>
 * 		<th> Description </th>
 *	</tr>
 * 	<tr class="TableRowColor">
 * 		<td colspan="5"> Inherited attributes : see {@link AbstractActionWidget} </td>
 * 	</tr>
 * </table>
 * <br>
 * <table border="1" width="100%" cellpadding="3" cellspacing="0" >
 * 	<tr class="TableHeadingColor">
 * 		<th align="left" colspan="4"> <font size="+2"> Style properties </font> </th>
 * 	</tr>
 * 	<tr class="TableRowColor">
 * 		<th width="1%"> Property </th>
 * 		<th width="1%"> Default </th>
 * 		<th width="1%"> Inherit </th>
 * 		<th> Description </th>
 *	</tr>
 * 	<tr class="TableRowColor">
 * 		<td> <code>layout</code> </th>
 * 		<td> <code>borderlayout</code> </td>
 * 		<td> <code>No</code> </td>
 * 		<td> <b>Uneditable</b>, see {@link Widget} </td>
 *	</tr>
 * 	<tr class="TableRowColor">
 * 		<td> <code>layout-data</code> </th>
 * 		<td> <code>sld(center,-1,-1)</code> </td>
 * 		<td> <code>No</code> </td>
 * 		<td> see {@link Widget} </td>
 *	</tr>
 * 	<tr class="TableRowColor">
 * 		<td colspan="4"> Inherited style properties : see {@link AbstractActionWidget} </td>
 * 	</tr>
 * </table>
 * <br>
 * <table border="1" width="100%" cellpadding="3" cellspacing="0" >
 * 	<tr class="TableHeadingColor">
 * 		<th align="left" colspan="2"> <font size="+2"> Available style pseudo-classes </font> </th>
 * 	</tr>
 * 	<tr class="TableRowColor">
 * 		<th width="1%"> Pseudo-class </th>
 * 		<th> Description </th>
 *	</tr>
 * 	<tr class="TableRowColor">
 * 		<td colspan="2"> Inherited style pseudo-classes : see {@link AbstractActionWidget} </td>
 * 	</tr>
 * </table>
 * <br>
 * <table border="1" width="100%" cellpadding="3" cellspacing="0" >
 * 	<tr class="TableHeadingColor">
 * 		<th align="left" colspan="2"> <font size="+2"> Available internal widgets </font> </th>
 * 	</tr>
 * 	<tr class="TableRowColor">
 * 		<th width="1%"> internal widget </th>
 * 		<th> Description </th>
 *	</tr>
 * 	<tr class="TableRowColor">
 * 		<td colspan="2"> Inherited internal widgets : see {@link AbstractActionWidget} </td>
 * 	</tr>
 * </table>
 * 
 * @author bbeaulant
 */
public class PopupBox extends AbstractActionWidget {

	// Defaults
	private static final LayoutData POPUP_BOX_DEFAULT_LAYOUT_DATA = new StaticLayoutData(Alignment.CENTER);
	
	// The associated DefaultFocusManager
	private final FocusManager focusManager;
	
	// The duration of presence of this popup (in ms)
	private int duration = -1;
	
	// Internal widgets
	private final Widget container = new Widget(KuixConstants.POPUP_BOX_CONTAINER_WIDGET_TAG) {

		/* (non-Javadoc)
		 * @see org.kalmeo.kuix.widget.Widget#getLayout()
		 */
		public Layout getLayout() {
			return BorderLayout.instance;
		}

		/* (non-Javadoc)
		 * @see org.kalmeo.kuix.widget.Widget#getLayoutData()
		 */
		public LayoutData getLayoutData() {
			return BorderLayoutData.instanceCenter;
		}
		
	};
	private final Widget buttonsContainer = new Widget(KuixConstants.POPUP_BOX_BUTTONS_CONTAINER_WIDGET_TAG) {

		/* (non-Javadoc)
		 * @see org.kalmeo.kuix.widget.Widget#getDefaultStylePropertyValue(java.lang.String)
		 */
		protected Object getDefaultStylePropertyValue(String name) {
			if (KuixConstants.LAYOUT_DATA_STYLE_PROPERTY.equals(name)) {
				return BorderLayoutData.instanceSouth;
			}
			return super.getDefaultStylePropertyValue(name);
		}
		
	};
	
	// Widget include via setContent method
	private Widget contentWidget;
	
	// The optional gauge (add via setProgress method)
	private Gauge gauge;

	/**
	 * Construct a {@link PopupMenu}
	 */
	public PopupBox() {
		super(KuixConstants.POPUP_BOX_WIDGET_TAG);
		
		// Create FocusManager
		focusManager = new FocusManager(this, false);
		
		// Define popupBax close shortcut key
		setShortcutKeyCodes(KuixConstants.KUIX_KEY_BACK, KuixConstants.KEY_PRESSED_EVENT_TYPE);
		focusManager.addShortcutHandler(this);
		
		// Add content containers
		add(container);
		add(buttonsContainer);
	}

	/* (non-Javadoc)
	 * @see org.kalmeo.kuix.widget.Widget#getFocusManager()
	 */
	public FocusManager getFocusManager() {
		return focusManager;
	}
	
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @param content
	 */
	public void setContent(Object content) {
		if (content instanceof String) {
			TextArea textArea;
			if (contentWidget instanceof TextArea) {
				textArea = (TextArea) contentWidget;
			} else {
				if (contentWidget != null) {
					contentWidget.remove();
				}
				textArea = new TextArea();
				contentWidget = textArea;
				container.add(textArea);
			}
			textArea.setText((String) content);
		} else if (content instanceof Widget) {
			if (contentWidget != null) {
				contentWidget.remove();
			}
			contentWidget = (Widget) content;
			container.add(contentWidget);
		} else {
			if (contentWidget != null) {
				contentWidget.remove();
				contentWidget = null;
			}
		}
	}
	
	/**
	 * @param progress a fixed-point integer representing progress value
	 */
	public void setProgress(int progress) {
		if (progress == -1) {
			if (gauge != null) {
				gauge.remove();
				gauge = null;
			}
		} else {
			if (gauge == null) {
				gauge = new Gauge() {
	
					/* (non-Javadoc)
					 * @see org.kalmeo.kuix.widget.Widget#getLayoutData()
					 */
					public LayoutData getLayoutData() {
						return BorderLayoutData.instanceSouth;
					}
					
				};
				container.add(gauge);
			}
			gauge.setValue(progress);
		}
	}

	/**
	 * @param buttonTexts
	 * @param buttonsShortcutKeyCodes
	 * @param buttonOnActions
	 */
	public void setButtons(String[] buttonTexts, int[] buttonsShortcutKeyCodes, String[] buttonOnActions) {
		buttonsContainer.removeAll();
		if (buttonTexts != null) {
			for (int i = 0; i<buttonTexts.length; ++i) {
				addButton(	buttonTexts[i], 
							buttonsShortcutKeyCodes != null && buttonsShortcutKeyCodes.length >= i ? buttonsShortcutKeyCodes[i] : -1, 
							buttonOnActions != null && buttonOnActions.length >= i ? buttonOnActions[i] : null);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.kalmeo.kuix.widget.Widget#getLayout()
	 */
	public Layout getLayout() {
		return BorderLayout.instance;
	}
	
	/* (non-Javadoc)
	 * @see org.kalmeo.kuix.widget.Widget#getDefaultStylePropertyValue(java.lang.String)
	 */
	protected Object getDefaultStylePropertyValue(String name) {
		if (KuixConstants.LAYOUT_DATA_STYLE_PROPERTY.equals(name)) {
			return POPUP_BOX_DEFAULT_LAYOUT_DATA;
		}
		return super.getDefaultStylePropertyValue(name);
	}

	/**
	 * Add a new Button
	 * 
	 * @param text
	 * @param shortcutKeyCodes
	 * @param onAction
	 */
	private void addButton(String text, int shortcutKeyCodes, String onAction) {
		Button button = new Button() {

			/* (non-Javadoc)
			 * @see org.kalmeo.kuix.widget.AbstractActionWidget#processActionEvent()
			 */
			public boolean processActionEvent() {
				super.processActionEvent();
				PopupBox.this.remove();
				return true;
			}

		};
		if (text != null) {
			button.add(new Text().setText(text));
		}
		button.setOnAction(onAction);
		if (shortcutKeyCodes != -1) {
			button.setShortcutKeyCodes(shortcutKeyCodes, KuixConstants.KEY_PRESSED_EVENT_TYPE);
		}
		buttonsContainer.add(button);
		
	}
	
	/* (non-Javadoc)
	 * @see org.kalmeo.kuix.widget.AbstractActionWidget#processShortcutKeyEvent(byte, int)
	 */
	public boolean processShortcutKeyEvent(byte type, int kuixKeyCode) {
		if (type == KuixConstants.KEY_PRESSED_EVENT_TYPE) {
			remove();
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.kalmeo.kuix.widget.Widget#onAdded(org.kalmeo.kuix.widget.Widget)
	 */
	protected void onAdded(Widget parent) {
		if (duration != -1) {
			Worker.instance.pushTask(new WorkerTask() {
				
				private long startTime = System.currentTimeMillis();
				
				/* (non-Javadoc)
				 * @see org.kalmeo.kuix.core.worker.WorkerTask#execute()
				 */
				public boolean run() {
					if ((System.currentTimeMillis() - startTime) > duration) {
						remove();
						return true;
					}
					return false;
				}
				
			});
		}
	}

	/* (non-Javadoc)
	 * @see org.kalmeo.kuix.widget.Widget#onRemoved(org.kalmeo.kuix.widget.Widget)
	 */
	protected void onRemoved(Widget parent) {
		processActionEvent();
		parent.getDesktop().getCanvas().repaintNextFrame();
	}
	
}
