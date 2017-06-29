/* 																														*
 * Programmer: Baran Topal                   																			*
 * Project name: BaranTopalCVSigner       																				*
 * Folder name: src        																								*
 * Package name: com.icss.client  																						*
 * File name: BaranTopalCVClient.java                     																*
 *                                           																			*      
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *	                                                                                         							*
 *  LICENSE: This source file is subject to have the protection of GNU General Public License.             				*
 *	You can distribute the code freely but storing this license information. 											*
 *	Contact Baran Topal if you have any questions. barantopal@barantopal.com 										    *
 *	                                                                                         							*
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package com.icss.client;

import java.io.File;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.icss.ws.*;

public class BaranTopalCVClient{

	public static void main(String[] args) throws Exception {

		URL url = new URL("http://localhost:9999/ws/baran?wsdl");

		//1st argument service URI, refer to wsdl document above
		//2nd argument is service name, refer to wsdl document above
		QName qname = new QName("http://ws.icss.com/", "BaranTopalCVImplService");

		Service service = Service.create(url, qname);

		BaranTopalCV cv = service.getPort(BaranTopalCV.class);

		System.out.println("Baran Topal CV\n");
		
		//extract the CV in server side
		System.out.println(cv.getCVAsString(new File("BaranTopal_CV.pdf")));        
		System.out.println();
		BaranTopalCVSign sign = new BaranTopalCVSign();
		//sign the CV in client side
		sign.sign();

		//verify the CV in server side
		System.out.println("Baran Topal CV Verified\n");
		System.out.println(cv.verifyCVAsString(new File("BaranTopal_CV_signed.pdf")));
		System.out.println();

	}

}