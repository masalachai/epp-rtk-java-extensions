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

public class ExtLaunchNotice {
	protected String noticeID = null;
	protected Date notAfter = null;
	protected Date notBefore = null;
	protected Date acceptedDate = null;
	
	public String getNoticeID() { return noticeID; }
	public Date getNotAfter() { return notAfter; }
	public Date getAcceptedDate() { return acceptedDate; }
	
	public void setNoticeID(String noticeID) { this.noticeID = noticeID; }
	public void setNotAfter(Date notAfter) { this.notAfter = notAfter; }
	public void setAcceptedDate(Date acceptedDate) { this.acceptedDate = acceptedDate; }
	
	public ExtLaunchNotice(String noticeID) {
		this.noticeID = noticeID;
	}
}
