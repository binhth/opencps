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

package org.opencps.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author trungnt
 */
public class ImageUtil {

	/**
	 * @param url
	 * @return
	 */
	public static String getSignatureImageBase64(String url) {

		String base64 = StringPool.BLANK;

		InputStream is = null;

		ByteArrayOutputStream os = null;

		try {
			is = new URL(url)
				.openStream();
			os = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];

			byte[] imageBuff = null;

			int length;

			while ((length = is
				.read(buffer)) != -1)
				os
					.write(buffer, 0, length); // copy streams

			imageBuff = os
				.toByteArray();

			base64 = Base64
				.encode(imageBuff);

		}
		catch (Exception e) {
			_log
				.error(e);
		}
		finally {
			try {
				if (is != null) {
					is
						.close();
				}
				if (os != null) {
					os
						.close();
				}
			}
			catch (IOException e) {
				_log
					.error(e);
			}
		}
		return base64;
	}

	/**
	 * @param fullPath
	 * @return
	 */
	public static String getSignatureImageBase64ByPath(String fullPath) {

		String base64 = StringPool.BLANK;

		InputStream is = null;

		ByteArrayOutputStream os = null;

		try {
			is = new FileInputStream(new File(fullPath));
			os = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];

			byte[] imageBuff = null;

			int length;

			while ((length = is
				.read(buffer)) != -1)
				os
					.write(buffer, 0, length); // copy streams

			imageBuff = os
				.toByteArray();

			base64 = Base64
				.encode(imageBuff);

		}
		catch (Exception e) {
			_log
				.error(e);
		}
		finally {
			try {
				if (is != null) {
					is
						.close();
				}
				if (os != null) {
					os
						.close();
				}
			}
			catch (IOException e) {
				_log
					.error(e);
			}
		}
		return base64;
	}

	/**
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static BufferedImage getImage(String url)
		throws IOException, URISyntaxException {

		InputStream is = null;
		BufferedImage bimg = null;
		try {
			is = new URL(url)
				.openStream();
			bimg = ImageIO
				.read(is);
		}
		catch (Exception e) {
			_log
				.error(e);
		}
		finally {
			if (is != null) {
				is
					.close();
			}
		}

		return bimg;
	}

	/**
	 * @param fullPath
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static BufferedImage getImageByPath(String fullPath)
		throws IOException, URISyntaxException {

		InputStream is = null;
		BufferedImage bimg = null;
		try {
			is = new FileInputStream(new File(fullPath));
			bimg = ImageIO
				.read(is);
		}
		catch (Exception e) {
			_log
				.error(e);
		}
		finally {
			if (is != null) {
				is
					.close();
			}
		}

		return bimg;
	}
	
	private static Log _log = LogFactoryUtil
		.getLog(ImageUtil.class);
}
