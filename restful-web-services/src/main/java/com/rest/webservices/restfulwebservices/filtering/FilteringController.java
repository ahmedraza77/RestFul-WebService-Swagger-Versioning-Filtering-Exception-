package com.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;


@RestController
public class FilteringController {
	
	@GetMapping("/filtering")
	public MappingJacksonValue filteringMembers() {
		
		Members member = new Members("Ahmed", "ar@123", "123");
		
		//invoking static method filterOutAllExcept()  
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name","email"); 
		
		//creating filter using FilterProvider class  
		FilterProvider filters = new SimpleFilterProvider().addFilter("MemberFilter", filter);
		
		//constructor of MappingJacksonValue class  that has bean as constructor argument  
		MappingJacksonValue mapping = new MappingJacksonValue(member);
		mapping.setFilters(filters);      //configuring filters  
		
		return mapping;
	}
	
	@GetMapping("/filtering-list")
	public MappingJacksonValue filteringMembersList() {
		List<Members> member = Arrays.asList(new Members("Bharat", "bg@123", "456"), 
				new Members("Rishab", "ro@123", "789"));
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("MemberFilter", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(member);
		mapping.setFilters(filters);
		
		return mapping;
	}

}
