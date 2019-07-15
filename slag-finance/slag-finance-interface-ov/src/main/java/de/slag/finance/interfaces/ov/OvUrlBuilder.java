package de.slag.finance.interfaces.ov;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.slag.common.base.BaseException;
import de.slag.common.utils.DateUtils;

class OvUrlBuilder {
	
	private String baseUrl;
	
	private Date date;
	
	private String notation;
	
	OvUrlBuilder baseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
		return this;
	}
	
	OvUrlBuilder date(Date date) {
		this.date = date;
		return this;
	}
	
	OvUrlBuilder notation(String notation) {
		this.notation = notation;
		return this;
	}
	
	
	
	
	
	URL build() {

		Date startDate = DateUtils.toDate(DateUtils.toLocalDate(date).minusYears(1));
		
		StringBuilder sb = new StringBuilder();
		sb.append(baseUrl);
		sb.append("?");
		
		sb.append("notationId=");
		sb.append(notation);
		
		sb.append("&");
		sb.append("dateStart=");
		sb.append(new SimpleDateFormat("dd.MM.yyyy").format(startDate));
		
		sb.append("&");
		sb.append("interval=");
		sb.append("Y1");
		
		
		
		try {
			return new URL(sb.toString());
		} catch (MalformedURLException e) {
			throw new BaseException(e);
		}
	}

}
