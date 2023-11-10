package org.jepetto.sql;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.xpath.XPath;

public class Sample {
	public static void main(String args[]) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("%merchantCode%", "YOUR_MERCHANT_CODE");
		map.put("%orderCode%", "YOUR_ORDER_CODE");
		map.put("%description%", "YOURDESCRIPTION");
		map.put("%currencyCode%", "GBP");
		map.put("%amount%", "5000");
		map.put("%cardNumber%", "4444333322221111");
		map.put("%month%", "01");
		map.put("%year%", "2020");
		map.put("%cardHolderName%", "A Shopper");
		map.put("%address1%", "47A");
		map.put("%address2%", "Queensbridge Road");
		map.put("%address3%", "Suburbia");
		map.put("%postalCode%", "CB94BQ");
		map.put("%city%", "Cambridge");
		map.put("%state%", "Cambridgeshire");
		map.put("%countryCode%", "GB");
		map.put("%shopperIPAddress%", "123.123.123.123");
		map.put("%shopperEmailAddress%", "ashopper@myprovider.com");
		map.put("%id%", "0215ui8ib1");

		map.put("%userAgentHeader%", "Mozilla/5.0 ...");
		map.put("%dynamicMCC%", "5045");

		Set<String> keySet = map.keySet();
		Iterator<String> iter = keySet.iterator();
		String key = null;
		String value = null;
		String xml = append();

		while (iter.hasNext()) {
			key = iter.next().toString();
			value = (String) map.get(key);
			xml = xml.replaceAll(key, value);
		}
		System.out.println(xml);

		xml = response();
		
		Document doc = XmlTransfer.string2dom(xml);
		XPath servletPath = null;
		try {
			servletPath = XPath.newInstance("//orderStatus");
			List<Element> list = (List)servletPath.selectNodes(doc);
			Element ele = (Element)list.get(0);
			String orderCode = ele.getAttribute("orderCode").getValue();
			System.out.println(orderCode);
			ele = ele.getChild("payment");
			list = ele.getChildren();
			for(int i = 0 ; i < list.size() ; i++) {
				System.out.println(list.get(i).toString());
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	}



	public static String append() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version='1.0' encoding='UTF-8'?>");
		buffer.append("<!DOCTYPE paymentService PUBLIC '-//Worldpay//DTD Worldpay PaymentService v1//EN' 'http://dtd.worldpay.com/paymentService_v1.dtd'>");
		buffer.append("<paymentService version='1.4' merchantCode='%merchantCode%'>");
		buffer.append("<submit>");
		buffer.append("<order orderCode='%orderCode%'>");
		buffer.append("<description>%description%</description>");
		buffer.append("<amount currencyCode='%currencyCode%' exponent='2' value='%amount%'></amount>");
		buffer.append("<paymentDetails>");
		buffer.append("<CARD-SSL>");
		buffer.append("<cardNumber>%cardNumber%</cardNumber>");
		buffer.append("<expiryDate>");
		buffer.append("<date month='%month%' year='%year%'/>");
		buffer.append("</expiryDate>");
		buffer.append("<cardHolderName>%cardHolderName%</cardHolderName>");
		buffer.append("<cardAddress>");
		buffer.append("<address>");
		buffer.append("<address1>%address1%</address1>");
		buffer.append("<address2>%address2%</address2>");
		buffer.append("<address3>%address3%</address3>");
		buffer.append("<postalCode>%postalCode%</postalCode>");
		buffer.append("<city>%city%</city>");
		buffer.append("<state>%state%</state>");
		buffer.append("<countryCode>%countryCode%</countryCode>");
		buffer.append("</address>");
		buffer.append("</cardAddress>");
		buffer.append("</CARD-SSL>");
		buffer.append("<session shopperIPAddress='%shopperIPAddress%' id='%id%'/>");
		buffer.append("</paymentDetails>");
		buffer.append("<shopper>");
		buffer.append("<shopperEmailAddress>%shopperEmailAddress%</shopperEmailAddress>");
		buffer.append("<browser>");
		buffer.append("<acceptHeader>text/html</acceptHeader>");
		buffer.append("<userAgentHeader>%userAgentHeader%</userAgentHeader>");
		buffer.append("</browser>");
		buffer.append("</shopper>");
		buffer.append("<dynamicMCC>%dynamicMCC%</dynamicMCC>");
		buffer.append("<dynamicInteractionType type='ECOMMERCE'/>");
		buffer.append("<dynamic3DS overrideAdvice='do3DS'/>");
		buffer.append("</order>");
		buffer.append("</submit>");
		buffer.append("</paymentService>");

		return buffer.toString();

	}

	public static String response() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append("<!DOCTYPE paymentService PUBLIC \"-//WorldPay//DTD WorldPay PaymentService v1//EN\" \"http://dtd.worldpay.com/paymentService_v1.dtd\">");
		buffer.append("<paymentService version=\"1.4\" merchantCode=\"ExampleCode1\">");
		buffer.append("<reply>");
		buffer.append("<orderStatus orderCode=\"ExampleOrder1\">");
		buffer.append("<payment>");
		buffer.append("<paymentMethod>VISA_CREDIT-SSL</paymentMethod>");
		buffer.append("<amount value=\"5000\" currencyCode=\"GBP\" exponent=\"2\" debitCreditIndicator=\"credit\" />");
		buffer.append("<lastEvent>AUTHORISED</lastEvent>");
		buffer.append("<CVCResultCode description=\"APPROVED\" />");
		buffer.append("<balance accountType=\"IN_PROCESS_AUTHORISED\">");
		buffer.append("<amount value=\"5000\" currencyCode=\"GBP\" exponent=\"2\" debitCreditIndicator=\"credit\" />");
		buffer.append("</balance>");
		buffer.append("<cardNumber>4444********1111</cardNumber>");
		buffer.append("<riskScore value=\"0\" />");
		buffer.append("</payment>");
		buffer.append("</orderStatus>");
		buffer.append("</reply>");
		buffer.append("</paymentService>");
		return buffer.toString();
	}

}
