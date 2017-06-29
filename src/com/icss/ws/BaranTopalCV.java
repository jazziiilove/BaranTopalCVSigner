/* 																														*
 * Programmer: Baran Topal                   																			*
 * Project name: BaranTopalCVSigner       																				*
 * Folder name: src        																								*
 * Package name: com.icss.ws  																							*		
 * File name: BaranTopalCV.java                     																	*
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

import java.io.File;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
public interface BaranTopalCV{

	@WebMethod String getCVAsString(File file);
	@WebMethod String verifyCVAsString(File file);

}
