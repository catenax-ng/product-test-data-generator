package com.catenax.tdm.testdata;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.fluttercode.datafactory.AddressDataValues;
import org.fluttercode.datafactory.ContentDataValues;
import org.fluttercode.datafactory.NameDataValues;
import org.fluttercode.datafactory.impl.DataFactory;

import com.fasterxml.jackson.databind.util.ISO8601Utils;

public class TestDataFactory {
	
	private static final String UUID_PREFIX = "urn:uuid:";
	// "2022-02-04T14:48:54"
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-ddTHH:mm:ss";
	private static final int VAN_LENGTH = 115;
	
	private DataFactory df = new DataFactory();
		
	public TestDataFactory() {
		
	}
	
	public String getAny(String ... str) {
		String result = "";
		
		int r = this.getNumberBetween(0, str.length);
		result = str[r];
		
		return result;
	}
	
	public String getPattern(String pattern) {
		return RandomString.fromPattern(pattern);
	}

	public <T> T getItem(List<T> items) {
		return this.df.getItem(items);
	}

	public <T> T getItem(List<T> items, int probability) {
		return this.df.getItem(items, probability);
	}

	public <T> T getItem(List<T> items, int probability, T defaultItem) {
		return this.df.getItem(items, probability, defaultItem);
	}

	public <T> T getItem(T[] items) {
		return this.df.getItem(items, 100, null);
	}

	public <T> T getItem(T[] items, int probability) {
		return this.df.getItem(items, probability, null);
	}

	public <T> T getItem(T[] items, int probability, T defaultItem) {
		return this.df.getItem(items, probability, defaultItem);
	}

	public String getFirstName() {
		return this.df.getFirstName();
	}

	public String getName() {
		return this.df.getName();
	}

	public String getLastName() {
		return this.df.getLastName();
	}

	public String getStreetName() {
		return this.df.getStreetName();
	}

	public String getStreetSuffix() {
		return this.df.getStreetSuffix();
	}

	public String getCity() {
		return this.df.getCity();
	}

	public String getAddress() {
		return this.df.getAddress();
	}

	public String getAddressLine2(int probability) {
		return this.df.getAddressLine2(probability);
	}

	public String getAddressLine2(int probability, String defaultValue) {
		return this.df.getAddressLine2(probability, defaultValue);
	}

	public String getAddressLine2() {
		return this.df.getAddressLine2();
	}
	
	public Date getDate() {
		return this.getBirthDate();
	}

	public Date getBirthDate() {
		return this.df.getBirthDate();
	}

	public int getNumber() {
		return this.df.getNumber();
	}

	public int getNumberUpTo(int max) {
		return this.df.getNumberUpTo(max);
	}

	public int getNumberBetween(int min, int max) {
		return this.df.getNumberBetween(min, max);
	}
	
	public String formatDate(Date date) {
		String result = "";
		
		try {
			if(date != null) {
				result = ISO8601Utils.format(date, true);
			}
		} catch (Exception e) {
			result = date.toGMTString();
		}
		
		return result;
	}
	
	public String formatDate(Date date, String format) {
		String result = "";
		
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			result = df.format(date);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			result = date.toGMTString();
		}
		
		return result;
	}
	
	public Date getToday() {
		return getNow();
	}
	
	public Date getNow() {
		return new Date();
	}
	
	public Date getPastDate(int daysInThePast) {
		Date minDate = this.getToday();
		LocalDateTime ldt = LocalDateTime.ofInstant(minDate.toInstant(), ZoneId.of("UTC"));
		ldt = ldt.minusDays((Integer.valueOf(daysInThePast)).longValue());
		minDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		
		return this.getDateBetween(minDate, this.getToday());
	}

	public Date getDate(int year, int month, int day) {
		// return this.df.getDate(getBirthDate(), day, day);
		LocalDateTime ldt = LocalDateTime.of(year, month, day, 0, 0);
		return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
	}

	public Date getDate(Date baseDate, int minDaysFromDate, int maxDaysFromDate) {
		return this.df.getDate(baseDate, minDaysFromDate, maxDaysFromDate);
	}

	public Date getDateBetween(Date minDate, Date maxDate) {
		return this.df.getDateBetween(minDate, maxDate);
	}

	public String getRandomText(int length) {
		return this.df.getRandomText(length, length);
	}

	public String getRandomText(int minLength, int maxLength) {
		return this.df.getRandomText(minLength, maxLength);
	}

	public char getRandomChar() {
		return this.df.getRandomChar();
	}

	public String getRandomChars(int length) {
		return this.df.getRandomChars(length, length);
	}

	public String getRandomChars(int minLength, int maxLength) {
		return this.df.getRandomChars(minLength, maxLength);
	}

	public String getRandomWord() {
		return this.df.getRandomWord();
	}

	public String getRandomWord(int length) {
		return this.df.getRandomWord(length, length);
	}

	public String getRandomWord(int length, boolean exactLength) {
		return this.df.getRandomWord(length, exactLength);
	}

	public String getRandomWord(int minLength, int maxLength) {
		return this.df.getRandomWord(minLength, maxLength);
	}

	public String getSuffix(int chance) {
		return this.df.getSuffix(chance);
	}

	public String getPrefix(int chance) {
		return this.df.getPrefix(chance);
	}

	public String getNumberText(int digits) {
		return this.df.getNumberText(digits);
	}

	public String getBusinessName() {
		return this.df.getBusinessName();
	}

	public String getEmailAddress() {
		return this.df.getEmailAddress();
	}

	public boolean chance(int chance) {
		return this.df.chance(chance);
	}

	public NameDataValues getNameDataValues() {
		return this.df.getNameDataValues();
	}
	
	public String getUuid() {
		String result = null;
		
		UUID uuid = UUID.randomUUID();
		result = UUID_PREFIX + uuid.toString();
		
		return result;
	}
	
	public String getVan() {
		String result = "";
		
		// result = this.getRandomChars(VAN_LENGTH) + "=";
		for(int i = 0; i < VAN_LENGTH; i++) {
			String c = this.getRandomChar() + "";
			if(this.getNumberBetween(0, 2) == 0) {
				c = c.toUpperCase();
			}
			result += c;
		}
		
		result += "=";
		
		return result;
	}

	public void randomize(int seed) {
		this.df.randomize(seed);
	}

	public void setNameDataValues(NameDataValues nameDataValues) {
		this.df.setNameDataValues(nameDataValues);
	}

	public void setAddressDataValues(AddressDataValues addressDataValues) {
		this.df.setAddressDataValues(addressDataValues);
	}

	public void setContentDataValues(ContentDataValues contentDataValues) {
		this.df.setContentDataValues(contentDataValues);
	}
	
	public String getSerialNo(int len) {
		String result = this.getNumberText(len);

		return result;
	}
	
	public String getSerialNoNum(int len) {
		String result = this.getNumberText(len);

		return result;
	}
	
	public String getSerialNoAlphaNum(int len) {
		// TODO: alpha numeric
		String result = this.getNumberText(len);

		return result;
	}


}
