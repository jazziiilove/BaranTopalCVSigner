/* 																														*
 * Programmer: Baran Topal                   																			*
 * Project name: BaranTopalCVSigner       																				*
 * Folder name: src        																								*
 * Package name: com.icss.ws  																							*
 * File name: BaranTopalCVImpl.java                     																*
 *                                           																			*      
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *	                                                                                         							*
 *  LICENSE: This source file is subject to have the protection of GNU General Public License.             				*
 *	You can distribute the code freely but storing this license information. 											*
 *	Contact Baran Topal if you have any questions. barantopal@barantopal.com 										    *
 *	                                                                                         							*
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package com.icss.ws;

import javax.jws.WebService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.security.PdfPKCS7;

import java.security.*;

//Service Implementation
@WebService(endpointInterface = "com.icss.ws.BaranTopalCV")
public class BaranTopalCVImpl implements BaranTopalCV{

	//process CV and extract the data
	public String getCVAsString(File file) 
	{
		return process(file.getAbsolutePath());
	}

	//verify CV and signed data
	public String verifyCVAsString(File file)
	{	
		return verify(file.getAbsolutePath());
	}
	
	public static String verify(String path)
	{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		StringBuilder stringbuilder = new StringBuilder();
		try{

			PdfReader reader = new PdfReader(path);
			AcroFields af = reader.getAcroFields();

			//Search of the whole signature
			ArrayList names = af.getSignatureNames();

			//For every signature :
			for (int k = 0; k < names.size(); ++k) {
				String name = (String)names.get(k);
				
				//Signature name
				System.out.println("Signature name: " + name);
				stringbuilder.append("\nSignature name: " + name);

				System.out.println("Signature covers whole document: "
						+ af.signatureCoversWholeDocument(name));
				stringbuilder.append("\nSignature covers whole document: " + af.signatureCoversWholeDocument(name));
				
				//Start of extraction revision
				System.out.println("Document revision: " + af.getRevision(name) + " of "
						+ af.getTotalRevisions());

				FileOutputStream out = new FileOutputStream("revision_"
						+ af.getRevision(name) + ".pdf");
				byte bb[] = new byte[8192];
				InputStream ip = af.extractRevision(name);
				int n = 0;
				while ((n = ip.read(bb)) > 0) out.write(bb, 0, n);
				out.close();
				ip.close();
				//End of extraction revision

				stringbuilder.append("\nDocument revision: " + af.getRevision(name) + " of " + af.getTotalRevisions());
				PdfPKCS7 pkcs7 = af.verifySignature(name);

				System.out.println("Integrity check OK? " + pkcs7.verify());
				stringbuilder.append("\nIntegrity check OK? " + pkcs7.verify());

			}
		}
		catch(Exception ex)
		{ex.printStackTrace();}

		return (new String(stringbuilder));
	}

	public static String process(String filePath)
	{
		StringBuilder stringbuilder = new StringBuilder();
		try {
			PdfReader reader = new PdfReader(filePath);
			int n = reader.getNumberOfPages();

			// just retrieve the size of the first page
			Rectangle psize = reader.getPageSize(1);

			//build Pdf version	            
			stringbuilder.append("PDF version used: " + reader.getPdfVersion() + "\n");

			//build file length	            
			stringbuilder.append("File length: " + reader.getFileLength() + "\n");

			//build height of the first document	            
			stringbuilder.append("First page height: " + psize.getHeight() + "\n");

			//build height of the first document
			stringbuilder.append("First page width: " + psize.getWidth() + "\n");

			stringbuilder.append("\n//////////\n");

			//initialize extraction
			stringbuilder.append("\nExtract CV:\n");

			//Loop per pdf page content
			for(int i = 1; i <= n; i++)
			{
				//build the pages
				stringbuilder.append(PdfTextExtractor.getTextFromPage(reader, i));
				stringbuilder.append("\n");
			}	            
		}
		catch (Exception de) {
			de.printStackTrace();
		}
		return (new String(stringbuilder));
	}		
}