package com.dhanya.jsp;

public class CrimeList {
	private String Id;
	private String crimeType;
	private String dateTime;
	private String address;
	private String agency;
	
	CrimeList(String Id,String crimeType,String dateTime,String address,String agency)
	{
		this.Id = Id;
		this.crimeType=crimeType;
		this.dateTime=dateTime;
		this.address=address;
		this.agency=agency;
		
	}
	

}
