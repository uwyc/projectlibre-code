/*******************************************************************************
 * The contents of this file are subject to the Common Public Attribution License 
 * Version 1.0 (the "License"); you may not use this file except in compliance with 
 * the License. You may obtain a copy of the License at 
 * http://www.projectlibre.com/license . The License is based on the Mozilla Public 
 * License Version 1.1 but Sections 14 and 15 have been added to cover use of 
 * software over a computer network and provide for limited attribution for the 
 * Original Developer. In addition, Exhibit A has been modified to be consistent 
 * with Exhibit B. 
 *
 * Software distributed under the License is distributed on an "AS IS" basis, 
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for the 
 * specific language governing rights and limitations under the License. The 
 * Original Code is ProjectLibre. The Original Developer is the Initial Developer 
 * and is ProjectLibre Inc. All portions of the code written by ProjectLibre are 
 * Copyright (c) 2012-2019. All Rights Reserved. All portions of the code written by 
 * ProjectLibre are Copyright (c) 2012-2019. All Rights Reserved. Contributor 
 * ProjectLibre, Inc.
 *
 * Alternatively, the contents of this file may be used under the terms of the 
 * ProjectLibre End-User License Agreement (the ProjectLibre License) in which case 
 * the provisions of the ProjectLibre License are applicable instead of those above. 
 * If you wish to allow use of your version of this file only under the terms of the 
 * ProjectLibre License and not to allow others to use your version of this file 
 * under the CPAL, indicate your decision by deleting the provisions above and 
 * replace them with the notice and other provisions required by the ProjectLibre 
 * License. If you do not delete the provisions above, a recipient may use your 
 * version of this file under either the CPAL or the ProjectLibre Licenses. 
 *
 *
 * [NOTE: The text of this Exhibit A may differ slightly from the text of the notices 
 * in the Source Code files of the Original Code. You should use the text of this 
 * Exhibit A rather than the text found in the Original Code Source Code for Your 
 * Modifications.] 
 *
 * EXHIBIT B. Attribution Information for ProjectLibre required
 *
 * Attribution Copyright Notice: Copyright (c) 2012-2019, ProjectLibre, Inc.
 * Attribution Phrase (not exceeding 10 words): 
 * ProjectLibre, open source project management software.
 * Attribution URL: http://www.projectlibre.com
 * Graphic Image as provided in the Covered Code as file: projectlibre-logo.png with 
 * alternatives listed on http://www.projectlibre.com/logo 
 *
 * Display of Attribution Information is required in Larger Works which are defined 
 * in the CPAL as a work which combines Covered Code or portions thereof with code 
 * not governed by the terms of the CPAL. However, in addition to the other notice 
 * obligations, all copies of the Covered Code in Executable and Source Code form 
 * distributed must, as a form of attribution of the original author, include on 
 * each user interface screen the "ProjectLibre" logo visible to all users. 
 * The ProjectLibre logo should be located horizontally aligned with the menu bar 
 * and left justified on the top left of the screen adjacent to the File menu. The 
 * logo must be at least 144 x 31 pixels. When users click on the "ProjectLibre" 
 * logo it must direct them back to http://www.projectlibre.com. 
 *******************************************************************************/
package com.projectlibre1.dialog;

import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.projectlibre1.main.Main;
import com.projectlibre1.pm.graphic.IconManager;
import com.projectlibre1.strings.Messages;
import com.projectlibre1.util.BrowserControl;
import com.projectlibre1.util.ClassLoaderUtils;
import com.projectlibre1.util.Environment;
import com.projectlibre1.util.VersionUtils;

public final class DonateDialog extends AbstractDialog {
	private static final long serialVersionUID = 1L;
	private boolean showRunsSince;

	public static void maybeShow(Frame owner,boolean force) {
		int runNumber = Main.getRunNumber();
		boolean periodic = false;
		boolean show=false;
		String[] runs = Messages.getString("DonateDialog.show").split(",");
		for (String s:runs) {
			if (Integer.parseInt(s) == runNumber) {
				show = true;
				break;
			}
		}
		int showEvery = Integer.parseInt(Messages.getString("DonateDialog.showEvery"));
		int showEveryStagger = Integer.parseInt(Messages.getString("DonateDialog.showEveryStagger"));
		if ((runNumber -showEveryStagger) % showEvery == 0) {
			show = true;
			periodic=true;
		}
		boolean showRunsSince = force || periodic;
		if (show || force)
			new DonateDialog(owner,showRunsSince).doModal();
	}


	private DonateDialog(Frame owner, boolean showRunsSince) {
		super(owner, Messages.getString("Text.donateTitle"), true); //$NON-NLS-1$
		this.showRunsSince = showRunsSince;
	}

	public JComponent createContentPanel() {
		FormLayout layout = new FormLayout("default" , // cols //$NON-NLS-1$
		(showRunsSince ? "p, 6dlu," : "") +  "170px"); // rows //$NON-NLS-1$
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.setDefaultDialogBorder();

		if (showRunsSince) {
			builder.append(Main.getRunSinceMessage());
			builder.nextLine(2);
		}
		builder.append(HelpDialog.makeDonatePanel(false));
		return builder.getPanel();
	}
	protected boolean hasCloseButton() {
		return true;
	}

	protected boolean hasOkAndCancelButtons() {
		return false;
	}

}