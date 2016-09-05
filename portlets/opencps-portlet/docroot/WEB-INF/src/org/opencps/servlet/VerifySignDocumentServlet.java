/**
* OpenCPS is the open source Core Public Services software
* Copyright (C) 2016-present OpenCPS community

* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Affero General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* any later version.

* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Affero General Public License for more details.
* You should have received a copy of the GNU Affero General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>
*/

package org.opencps.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.security.Security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opencps.dossiermgt.model.DossierFile;
import org.opencps.dossiermgt.service.DossierFileLocalServiceUtil;
import org.opencps.pki.PdfVerifier;
import org.opencps.util.DLFileEntryUtil;
import org.opencps.util.PDFUtil;
import org.opencps.util.PortletUtil;
import org.opencps.util.WebKeys;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

/**
 * @author trungnt
 */
public class VerifySignDocumentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected synchronized void doGet(
		HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		long dossierFileId = ParamUtil
			.getLong(request, "dossierFileId");
		PrintWriter writer = response
			.getWriter();

		String pdfSignedFilePath = StringPool.BLANK;

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) request
				.getAttribute(WebKeys.THEME_DISPLAY);
			// Get dossier file
			DossierFile dossierFile = DossierFileLocalServiceUtil
				.getDossierFile(dossierFileId);

			User user = UserLocalServiceUtil
				.getUserById(20624);

			PermissionThreadLocal
				.setPermissionChecker(PermissionCheckerFactoryUtil
					.create(user, true));

			/*
			 * FileEntry fileEntry = DLFileEntryUtil .getFileEntry(dossierFile
			 * .getFileEntryId());
			 */

			pdfSignedFilePath = PDFUtil
				.saveAsPdf(PortletUtil
					.getTempFolderPath(request), dossierFile
						.getFileEntryId());

			PdfVerifier verifier = new PdfVerifier();
			

			FileInputStream is = new FileInputStream(PortletUtil
				.getResourceFolderPath(request) + "opencps-ks.jks");

			// FileInputStream is = new FileInputStream(new File(pdfSigned));
			
			/*Security
			.addProvider(new BouncyCastleProvider());*/
			KeyStore keystore = verifier.loadKeyStore(PortletUtil
				.getResourceFolderPath(request) + "opencps-ks.jks", "fds@2016");
		/*	KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			keystore
				.load(is, "fds@2016"
					.toCharArray());*/
			
			Boolean isVerify = verifier
				.verifySignature(pdfSignedFilePath, keystore);

			String url = DLFileEntryUtil
				.getDossierFileAttachmentURL(dossierFileId, themeDisplay);

			String embedURL = "http://docs.google.com/gview?url=" + request
				.getServerName() + ":" + request
					.getServerPort() +
				url + "&embedded=true";

			writer
				.write(
					"<div><div class=\"left-panel\" style=\"border-right: 1px solid #ccc; box-shadow: 5px 3px 5px #ccc;   float: left;   padding-right: 10px;  width: 80%;\"><iframe width=\"100%\" height=\"100%\" src=\"" +
						embedURL +
						"\"></iframe></div><div class=\"right-panel\" style=\"float: right; width: 18%;\">" +
						isVerify + "</div></div>");
		}
		catch (Exception e) {
			e.printStackTrace();
			writer
				.write("<div class=\"portlet-msg-alert\">" + LanguageUtil
					.get(request
						.getLocale(), "error-while-verify-sign") +
					"</div>");
		}
		finally {
			if (writer != null) {
				writer
					.println();
				writer
					.flush();
				writer
					.close();
			}

			if (Validator
				.isNotNull(pdfSignedFilePath)) {
				File file = new File(pdfSignedFilePath);
				if (file
					.exists()) {
					file
						.delete();
				}
			}
		}
	}

	@Override
	public void destroy() {

		// TODO Auto-generated method stub
		super.destroy();
	}
}
