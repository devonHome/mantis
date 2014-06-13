package com.siliconmtn.helper;


/****************************************************************************
 * <b>Title</b>: HelperFunctions.javaIncomingDataWebService.java <p/>
 * <b>Project</b>: MantisProjectRAMDataFeed <p/>
 * <b>Description: </b>
 * <b>Copyright:</b> Copyright (c) 2014<p/>
 * <b>Company:</b> Silicon Mountain Technologies<p/>
 * @author Devon Franklin
 * @version 1.0
 * @since 4:21:14 PM<p/>
 * <b>Changes: </b>
 ****************************************************************************/
/*
 * Class will have general functions that can be used throughout entire project
 */
public class Helper {


	/**
	 * Will loop through array list of parameters and return it to a formatted String
	 * @param parameters
	 * @return
	 */
	public String getParameters(String[] parameters){
		StringBuilder fieldParams = new StringBuilder();
		
		for (int i = 0; i < parameters.length; i++) {
			if (fieldParams.length() == 0) {
				fieldParams.append("fldName=" + parameters[i]);
			} else {
				fieldParams.append("&" + "fldName=" + parameters[i]);
			}
		}
		
		return fieldParams.toString();
	}
	
	/**
	 * Parses a string to double, returns true if input has numeric value 
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
}
