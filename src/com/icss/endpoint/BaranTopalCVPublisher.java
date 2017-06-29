/* 																														*
 * Programmer: Baran Topal                   																			*
 * Project name: BaranTopalCVSigner       																				*
 * Folder name: src        																								*
 * Package name: com.icss.endpoint  																					*
 * File name: BaranTopalCVPublisher.java                     															*
 *                                           																			*      
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *	                                                                                         							*
 *  LICENSE: This source file is subject to have the protection of GNU General Public License.             				*
 *	You can distribute the code freely but storing this license information. 											*
 *	Contact Baran Topal if you have any questions. barantopal@barantopal.com 										    *
 *	                                                                                         							*
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package com.icss.endpoint;

import javax.xml.ws.Endpoint;

import com.icss.ws.BaranTopalCVImpl;

//Endpoint publisher
public class BaranTopalCVPublisher{

	public static void main(String[] args) {
		Endpoint.publish("http://localhost:9999/ws/baran", new BaranTopalCVImpl());
	}

}