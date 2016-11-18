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

package org.opencps.vtcpay.model;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.servlet.http.HttpServletRequest;

import org.opencps.keypay.security.HashFunction;
import org.opencps.keypay.security.MD5;
import org.opencps.keypay.service.restful.KPJsonRest;
import org.opencps.keypay.service.restful.KPRest;
import org.opencps.paymentmgt.model.PaymentConfig;
import org.opencps.paymentmgt.model.PaymentFile;
import org.opencps.paymentmgt.model.PaymentGateConfig;
import org.opencps.paymentmgt.service.PaymentConfigLocalServiceUtil;
import org.opencps.paymentmgt.service.PaymentFileLocalServiceUtil;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

public class VTCPay {

	// các tham số gửi đi

	private static String website_id;
	private static String receiver_account;
	private static String language;
	private static String url_return;

	private static String reference_number;
	private static String amount;
	private static String currency;
	private String bill_to_email;
	private String bill_to_phone;
	private String bill_to_address;
	private String bill_to_address_city;
	private String bill_to_surname;
	private String bill_to_forename;
	private String paymentType;
	// secure key
	private static String secret_key;
	// pay url
	private static String request_url;
	// các tham số trả về từ VTCPay
	private static String trans_ref_no;
	private static String message;
	private static String status;

	//
	private static String data;
	private static String signature;

	public VTCPay() {

	}

	private static Log _log = LogFactoryUtil.getLog(VTCPay.class);

	public VTCPay(
		String website_id, String receiver_account, String language, String url_return,
		String secret_key, String reference_number, String amount, String currency,
		String request_url, String trans_ref_no, String status, String data, String signature) {

		this.website_id = website_id;
		this.receiver_account = receiver_account;
		this.language = language;
		this.url_return = url_return;
		this.secret_key = secret_key;
		this.reference_number = reference_number;
		this.amount = amount;
		this.currency = currency;
		this.request_url = request_url;
		this.trans_ref_no = trans_ref_no;
		this.status = status;
		this.data = data;
		this.signature = signature;
	}

	public VTCPay(String data) {

		String[] dataArrays = StringUtil.split(data, "|");

		_log.info("dataArrays:" + dataArrays.length);
		if (dataArrays.length > 0) {

			List<String> dataList = Arrays.asList(dataArrays);

			_log.info("dataList:" + dataList);
			if (dataList.size() > 0) {
				this.amount = dataList.get(0);
				this.message = dataList.get(1);
				this.paymentType = dataList.get(2);
				this.reference_number = dataList.get(3);
				this.status = dataList.get(4);
				this.trans_ref_no = dataList.get(5);
				this.website_id = dataList.get(6);
				this.secret_key = dataList.get(7);
			}
		}

	}

	public static String getSecureHashCodeResponse(VTCPay vtcPay) {

		PaymentFile paymentFile = null;
		PaymentConfig paymentConfig = null;

		_log.info("=====vtcPay.getReference_number():" + vtcPay.getReference_number());

		try {

			if (vtcPay.getReference_number().trim().length() > 0) {
				paymentFile =
					PaymentFileLocalServiceUtil.getByTransactionId(Long.parseLong(vtcPay.getReference_number()));

				_log.info("=====paymentFile.getPaymentConfig():" + paymentFile.getPaymentConfig());
			}

			if (Validator.isNotNull(paymentFile)) {

				paymentConfig =
					PaymentConfigLocalServiceUtil.getPaymentConfig(paymentFile.getPaymentConfig());
			}

		}
		catch (NumberFormatException | PortalException | SystemException e1) {
			// TODO Auto-generated catch block
			_log.error(e1);
		}

		StringBuffer merchantSignBuffer = new StringBuffer();
		merchantSignBuffer.append(vtcPay.getAmount());

		merchantSignBuffer.append("|").append(vtcPay.getMessage());

		merchantSignBuffer.append("|").append(vtcPay.getPaymentType());

		merchantSignBuffer.append("|").append(vtcPay.getReference_number());

		merchantSignBuffer.append("|").append(vtcPay.getStatus());

		merchantSignBuffer.append("|").append(vtcPay.getTrans_ref_no());

		merchantSignBuffer.append("|").append(vtcPay.getWebsite_id());

		merchantSignBuffer.append("|").append(
			Validator.isNotNull(paymentConfig)
				? paymentConfig.getKeypaySecureKey() : StringPool.BLANK);

		String merchantSign = StringPool.BLANK;
		_log.info("=====merchantSignBuffer.toString():" + merchantSignBuffer.toString());
		merchantSign = merchantSignBuffer.toString();

		merchantSign = VTCPay.sha256(merchantSign);

		return merchantSign;
	}

	public static String getSecureHashCodeRequest(PaymentConfig paymentConfig, VTCPay vtcPay) {

		StringBuffer merchantSignBuffer = new StringBuffer();


		merchantSignBuffer.append(vtcPay.getAmount());
		merchantSignBuffer.append("|").append(vtcPay.getCurrency());
		merchantSignBuffer.append("|").append(vtcPay.getReceiver_account());
		merchantSignBuffer.append("|").append(vtcPay.getReference_number());
		merchantSignBuffer.append("|").append(vtcPay.getUrl_return());
		merchantSignBuffer.append("|").append(vtcPay.getWebsite_id());
		merchantSignBuffer.append("|").append(paymentConfig.getKeypaySecureKey());

		String merchantSign = StringPool.BLANK;
		merchantSign = merchantSignBuffer.toString();

		merchantSign = VTCPay.sha256(merchantSign);

		return merchantSign;

	}

	public static boolean validateSign(VTCPay vtcPay) {

		String merchantSig = VTCPay.getSecureHashCodeResponse(vtcPay);

		merchantSig = merchantSig.toUpperCase();


		String signature = vtcPay.getSignature();


		if (merchantSig.contains(signature)) {
			return true;
		}
		else {
			return false;
		}

	}

	public static String sha256(String base) {

		try {

			if (base.trim().length() > 0) {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] hash = digest.digest(base.getBytes("UTF-8"));
				StringBuffer hexString = new StringBuffer();

				for (int i = 0; i < hash.length; i++) {
					String hex = Integer.toHexString(0xff & hash[i]);
					if (hex.length() == 1)
						hexString.append('0');
					hexString.append(hex);
				}

				return hexString.toString();
			}
			else {
				return StringPool.BLANK;
			}
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	/**
	 * Constructor - Lấy dữ liệu trả về từ VTCPay
	 *
	 * @param request
	 */
	public VTCPay(HttpServletRequest request) {

		try {
			this.amount = request.getParameter("amount");
			this.message = request.getParameter("message");
			this.paymentType = request.getParameter("payment_type");
			this.reference_number = request.getParameter("reference_number");
			this.status = request.getParameter("status");
			this.trans_ref_no = request.getParameter("trans_ref_no");
			this.website_id = request.getParameter("website_id");
			this.signature = request.getParameter("signature");

		}
		catch (Exception e) {
			_log.info("ERROE get data KeyPay return");
		}
	}

	/**
	 * Tính secure hash ở đại lý khi thanh toán xong
	 *
	 * @return
	 */

	/**
	 * Hàm map trạng thái trả về từ VTCPay
	 *
	 * @param response_code
	 * @return
	 */
	public String genMsgReturn(String response_code) {

		String msg;
		switch (Integer.parseInt(response_code)) {
		case 0:
			// success
			msg = "Giao dịch ở trạng thái khởi tạo";
			break;
		case 1:
			// merchant code sai
			msg = "Giao dịch thành công";
			break;
		case 7:
			// secure hash sai
			msg =
				"Tài khoản thanh toán của khách hàng đã bị trừ tiền nhưng tài khoản của Merchant chưa được cộng tiền. Bộ phận quản trị thanh toán của VTC sẽ duyệt để quyết định giao dịch thành công hay thất bại";
			break;
		case -1:
			// merchant trans id khong hop le
			msg = "Giao dịch thất bại";
			break;
		case -9:
			// trans id khong ton tai
			msg = "Khách hàng tự hủy giao dịch";
			break;
		case -3:
			// ma dich vu khong hop le
			msg = "Quản trị VTC hủy giao dịch";
			break;
		case -5:
			// giao dich da gui confirm
			msg =
				"Số dư tài khoản khách hàng (Ví VTC Pay, tài khoản ngân hàng) không đủ để thực hiện giao dịch";
			break;
		case -6:
			// ma qgia khong hop le
			msg = "Lỗi giao dịch tại VTC";
			break;
		case -7:
			// timeout
			msg =
				"Khách hàng nhập sai thông tin thanh toán ( Sai thông tin tài khoản hoặc sai OTP)";
			break;
		case -22:
			// mo ta don hang khong hop le
			msg = "Số tiền thanh toán đơn hàng quá nhỏ";
			break;
		case -24:
			// ma don hang khong hop le
			msg = "Đơn vị tiền tệ thanh toán đơn hàng không hợp lệ";
			break;
		case -25:
			// net cost fail
			msg = "Tài khoản VTC Pay nhận tiền của Merchant không tồn tại.";
			break;
		case -28:
			// ship fee fail
			msg = "Thiếu tham số bắt buộc phải có trong một đơn hàng thanh toán online";
			break;
		case -29:
			// tax fail
			msg = "Tham số request không hợp lệ";
			break;
		case -21:
			// merchant code chua duoc cau hinh phi de thanh toan
			msg =
				"Trùng mã giao dịch, Có thể do xử lý duplicate không tốt nên mạng chậm hoặc khách hàng nhấn F5 bị, hoặc cơ chế sinh mã GD của đối tác không tốt nên sinh bị trùng, đối tác cần kiểm tra lại để biết kết quả cuối cùng của giao dịch này";
			break;
		case -23:
			// sai ma ngan hang
			msg = "WebsiteID không tồn tại";
			break;
		case -99:
			// so tien dai ly ko nam trong khoang cho phep (dai ly o day la
			// keypay so voi BN)
			msg =
				"Lỗi chưa rõ nguyên nhân và chưa biết trạng thái giao dịch. Cần kiểm tra để biết giao dịch thành công hay thất bại";
			break;

		default:
			// loi ko xac dinh
			msg = "Đã có lỗi xảy ra khi thực hiện thanh toán";
			break;
		}
		return msg;
	}

	public static String getWebsite_id() {

		return website_id;
	}

	public static void setWebsite_id(String website_id) {

		VTCPay.website_id = website_id;
	}

	public static String getReceiver_account() {

		return receiver_account;
	}

	public static void setReceiver_account(String receiver_account) {

		VTCPay.receiver_account = receiver_account;
	}

	public static String getLanguage() {

		return language;
	}

	public static void setLanguage(String language) {

		VTCPay.language = language;
	}

	public static String getUrl_return() {

		return url_return;
	}

	public static void setUrl_return(String url_return) {

		VTCPay.url_return = url_return;
	}

	public static String getReference_number() {

		return reference_number;
	}

	public static void setReference_number(String reference_number) {

		VTCPay.reference_number = reference_number;
	}

	public static String getAmount() {

		return amount;
	}

	public static void setAmount(String amount) {

		VTCPay.amount = amount;
	}

	public static String getCurrency() {

		return currency;
	}

	public static void setCurrency(String currency) {

		VTCPay.currency = currency;
	}

	public String getBill_to_email() {

		return bill_to_email;
	}

	public void setBill_to_email(String bill_to_email) {

		this.bill_to_email = bill_to_email;
	}

	public String getBill_to_phone() {

		return bill_to_phone;
	}

	public void setBill_to_phone(String bill_to_phone) {

		this.bill_to_phone = bill_to_phone;
	}

	public String getBill_to_address() {

		return bill_to_address;
	}

	public void setBill_to_address(String bill_to_address) {

		this.bill_to_address = bill_to_address;
	}

	public String getBill_to_address_city() {

		return bill_to_address_city;
	}

	public void setBill_to_address_city(String bill_to_address_city) {

		this.bill_to_address_city = bill_to_address_city;
	}

	public String getBill_to_surname() {

		return bill_to_surname;
	}

	public void setBill_to_surname(String bill_to_surname) {

		this.bill_to_surname = bill_to_surname;
	}

	public String getBill_to_forename() {

		return bill_to_forename;
	}

	public void setBill_to_forename(String bill_to_forename) {

		this.bill_to_forename = bill_to_forename;
	}

	public String getPaymentType() {

		return paymentType;
	}

	public void setPaymentType(String paymentType) {

		this.paymentType = paymentType;
	}

	public static String getSecret_key() {

		return secret_key;
	}

	public static void setSecret_key(String secret_key) {

		VTCPay.secret_key = secret_key;
	}

	public static String getRequest_url() {

		return request_url;
	}

	public static void setRequest_url(String request_url) {

		VTCPay.request_url = request_url;
	}

	public static String getTrans_ref_no() {

		return trans_ref_no;
	}

	public static void setTrans_ref_no(String trans_ref_no) {

		VTCPay.trans_ref_no = trans_ref_no;
	}

	public static String getMessage() {

		return message;
	}

	public static void setMessage(String message) {

		VTCPay.message = message;
	}

	public static String getStatus() {

		return status;
	}

	public static void setStatus(String status) {

		VTCPay.status = status;
	}

	public static String getData() {

		return data;
	}

	public static void setData(String data) {

		VTCPay.data = data;
	}

	public static String getSignature() {

		return signature;
	}

	public static void setSignature(String signature) {

		VTCPay.signature = signature;
	}

}
