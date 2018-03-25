/**
 * EPP RTK Java Extensions
 * Copyright (C) 2018 masalachai.net.

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details. 

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.masalachai.epprtk.extensions.rtk.xml;

import java.util.Date;

/**
 * Restore report object with all RGP data needed
 */
public class ExtRestoreReport {
	protected String preDeleteWhois = null;
	protected String postDeleteWhois = null;
	protected String reason = null;
	protected String statement1 = null;
	protected String statement2 = null;
	protected String other = null;
	protected Date delDate = null;
	protected Date resDate = null;

	/**
	 * Gets the other field in the restore report
	 * @return		the other field in the restore report
	 */
	public String getOther() {
		return other;
	}

	/**
	 * Gets the postDeleteWhois field in the restore report
	 * @return		the postDeleteWhois field in the restore report
	 */
	public String getPostDeleteWhois() {
		return postDeleteWhois;
	}

	/**
	 * Gets the preDeleteWhois field in the restore report
	 * @return		the preDeleteWhois field in the restore report
	 */
	public String getPreDeleteWhois() {
		return preDeleteWhois;
	}

	/**
	 * Gets the reason field in the restore report
	 * @return		the reason field in the restore report
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Gets the statement1 field in the restore report
	 * @return		the statement1 field in the restore report
	 */
	public String getStatement1() {
		return statement1;
	}

	/**
	 * Gets the statement2 field in the restore report
	 * @return		the statement2 field in the restore report
	 */
	public String getStatement2() {
		return statement2;
	}

	/**
	 * Gets the delDate field in the restore report
	 * @return		the delDate field in the restore report
	 */
	public Date getDelDate() {
		return delDate;
	}

	/**
	 * Gets the resDate field in the restore report
	 * @return		the resDate field in the restore report
	 */
	public Date getResDate() {
		return resDate;
	}

	/**
	 * Sets the delDate field in the restore report
	 * 
	 * @param delDate		the date on which the domain was deleted
	 */
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

	/**
	 * Sets the resDate field in the restore report
	 * 
	 * @param resDate		the date on which the domain restore was requested report
	 */
	public void setResDate(Date resDate) {
		this.resDate = resDate;
	}
	
	/**
	 * Sets the preDeleteWhois field in the restore report
	 * 
	 * @param preDeleteWhois	the whois data before deletion
	 */
	public void setPreDeleteWhois(String preDeleteWhois) {
		this.preDeleteWhois = preDeleteWhois;
	}
	/**
	 * Sets the postDeleteWhois field in the restore report
	 * 
	 * @param postDeleteWhois	the whois information after deletion
	 */
	public void setPostDeleteWhois(String postDeleteWhois) {
		this.postDeleteWhois = postDeleteWhois;
	}
	
	/**
	 * Sets the reason field in the restore report
	 * 
	 * @param report		the reason why the domain was deleted
	 */
	public void setReason(String report) {
		this.reason = report;
	}
	/**
	 * Sets the statement1 field in the restore report
	 * 
	 * @param statement1	any statement detailing the accidental deletion
	 */
	public void setStatement1(String statement1) {
		this.statement1 = statement1;
	}
	
	/**
	 * Sets the statement2 field in the restore report
	 * 
	 * @param statement2	any statement detailing the accidental deletion
	 */
	public void setStatement2(String statement2) {
		this.statement2 = statement2;
	}
	/**
	 * Sets the other field in the restore report
	 * 
		* @param other	extra optional data to be sent in the request
	 */
	public void setOther(String other) {
		this.other = other;
	}
}
