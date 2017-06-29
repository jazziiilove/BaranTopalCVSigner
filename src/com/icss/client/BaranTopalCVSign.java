/* 																														*
 * Programmer: Baran Topal                   																			*
 * Project name: BaranTopalCVSigner       																				*
 * Folder name: src        																								*
 * Package name: com.icss.client  																						*
 * File name: BaranTopalCVSign.java                     																*
 *                                           																			*      
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *	                                                                                         							*
 *  LICENSE: This source file is subject to have the protection of GNU General Public License.             				*
 *	You can distribute the code freely but storing this license information. 											*
 *	Contact Baran Topal if you have any questions. barantopal@barantopal.com 										    *
 *	                                                                                         							*
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

/*
 * Sign sequence:
 * c:\>keytool -list -v -keystore KEYSTORE2.jks
 * Enter keystore password:
 * Keystore type: JKS
 * Keystore provider: SUN
 * Your keystore contains 1 entry
 * Alias name: selfsigned
 * Creation date: Jan 4, 2013
 * Entry type: PrivateKeyEntry
 * Certificate chain length: 1
 * Certificate[1]:
 * Owner: CN=baran topal, OU=ICSS, O=KTH, L=Stockholm, ST=Sweden, C=SV
 * Issuer: CN=baran topal, OU=ICSS, O=KTH, L=Stockholm, ST=Sweden, C=SV
 * Serial number: 299be07a
 * Valid from: Fri Jan 04 23:19:46 CET 2013 until: Mon Dec 30 23:19:46 CET 2013
 * Certificate fingerprints:
         MD5:  D7:5F:4C:DC:B2:3F:93:06:40:07:7A:32:7B:B9:96:30
         SHA1: 90:44:2A:C5:AD:F4:BA:A7:4E:98:48:4E:DC:0C:8B:3B:55:EE:24:A3
         SHA256: 40:F7:BB:C4:DC:5B:D3:A5:ED:0A:EB:1F:DA:EF:4A:F9:FA:80:3D:FF:D4:
6F:D4:BE:52:17:DA:D8:BA:B0:E4:82
         Signature algorithm name: SHA256withRSA
         Version: 3

Extensions:

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: 28 CB DC 76 6C 11 A1 F0   4F B2 EE 1E CA 71 24 D9  (..vl...O....q$.
0010: D2 88 A1 E4                                        ....
]
]

 *******************************************
 *******************************************

c:\>
 */

package com.icss.client;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;

import java.security.cert.*;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PrivateKeySignature;

public class BaranTopalCVSign {

	//signing the pdf
	public void sign()
	{
		try{		
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			String keystore_password = "barantopal";
			String key_password = "barantopal";

			//Java Key store
			KeyStore ks = KeyStore.getInstance("JKS");			
			ks.load(new FileInputStream("keystore2.jks"), keystore_password.toCharArray());						
			String alias = (String)ks.aliases().nextElement();
			PrivateKey pk = (PrivateKey) ks.getKey(alias, key_password.toCharArray());
			Certificate[] chain = ks.getCertificateChain(alias);

			// reader and stamper
			PdfReader reader = new PdfReader("BaranTopal_CV.pdf");
			FileOutputStream os = new FileOutputStream("BaranTopal_CV_signed.pdf");
			PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');

			// appearance
			PdfSignatureAppearance appearance = stamper .getSignatureAppearance();
			appearance.setImage(Image.getInstance("hal.gif"));
			appearance.setReason("I've written this.");
			appearance.setLocation("Foobar");
			appearance.setVisibleSignature(new Rectangle(72, 732, 144, 780), 1, "first");

			// digital signature
			ExternalSignature es = new PrivateKeySignature(pk, "SHA-256", "BC");
			ExternalDigest digest = new BouncyCastleDigest();	
			MakeSignature.signDetached(appearance, digest, es, chain, null, null, null, 0, CryptoStandard.CMS);
		}catch(Exception ex){ex.printStackTrace();}

	}
}
