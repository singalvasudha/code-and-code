package com.xebia.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.beans.Herd;
import com.xebia.command.AdminCommand;
import com.xebia.handler.XMLValidationEventHandler;
import com.xebia.service.RESTService;
import com.xebia.util.Helper;

@Controller
@RequestMapping("/yak-shop")
public class AdminController{
	
	private static Logger log = Logger.getLogger(AdminController.class.getName());
	private static String FILE_NAME = "herd.xsd";
	
	@Autowired 
	private RESTService service;
	@Autowired
	private ServletContext servletContext;

	public RESTService getService() {
		return service;
	}
	public ServletContext getServletContext() {
		return servletContext;
	}

	@RequestMapping("/adminform")
	public ModelAndView showAdminForm()
	{
		log.info("Showing Admin Landing Page");
		return new ModelAndView("admin","command",new AdminCommand()); 
	}
	
	@RequestMapping(value="/saveXml", method = RequestMethod.POST)
	public ModelAndView saveXml(@ModelAttribute("adminCommand") AdminCommand adminCommand)
	{

			try{
				log.info("XML Path: "+adminCommand.getPath());
				
				Herd herd = readXmlAndSave(adminCommand.getPath());
			
				double milk = Helper.calculateMilkStock(herd, adminCommand.getDaysElapsed()); 
				int skins = Helper.calculateSkinsStock(herd, adminCommand.getDaysElapsed());
				
				log.info("In Stock:");
				log.info(milk + " litres of milk");
				log.info(skins + " skins of wool");
				
				adminCommand.setMilk(milk);
				adminCommand.setSkins(skins);
				adminCommand.setHerd(herd);
			}
			catch(Exception e)
			{
				log.error(e);
				adminCommand.setError("There was an error. Please validate inputs and try again.");
				return new ModelAndView("admin","command",adminCommand);
			}
				return new ModelAndView("xmldata","command",adminCommand);
				
	}
	
	public Herd readXmlAndSave(String path) throws Exception {
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
	        Schema schema = sf.newSchema(new File(getServletContext().getRealPath("/WEB-INF/")+FILE_NAME));
	        log.info("File path: "+getServletContext().getRealPath("/WEB-INF/")+FILE_NAME);
	 
	        JAXBContext jc = JAXBContext.newInstance(Herd.class);
	        Unmarshaller unmarshaller = jc.createUnmarshaller();
	        unmarshaller.setSchema(schema);
	        unmarshaller.setEventHandler(new XMLValidationEventHandler());
	        
	        Herd herd = (Herd) unmarshaller.unmarshal(new File(path));
	        
	        log.info("Herd size in xml: "+herd.getLabYaks().size());
	        
	        log.info("Save herd in DB starts");
	        getService().saveHerd(herd);
	        log.info("Save herd in DB ends");
			return herd;
	}
	
	@RequestMapping(value = "/herd/{daysElapsed}", method = RequestMethod.GET)
	public ModelAndView getHerdFromDB(@ModelAttribute("adminCommand") AdminCommand adminCommand, @PathVariable long daysElapsed){
		try{
			Herd herd = getService().getHerd();
			log.info("Herd size from DB: "+herd.getLabYaks().size());
			
			String jsonHerd = getHerdJSON(herd);
			log.info("JSON Herd: "+jsonHerd);
			
			adminCommand.setJsonHerd(jsonHerd);
		}
		catch(Exception e)
		{
			log.error(e);
			return new ModelAndView("error","command",adminCommand);
		}
		return new ModelAndView("herd","command",adminCommand);
	}
	
	/**
	 * This method returns the JSON representation of Herd object
	 * @param herd
	 * @return
	 */
	private String getHerdJSON(Herd herd){
		ObjectMapper mapper = new ObjectMapper();
		String jsonHerd = "";
		try {
				jsonHerd = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(herd);
				Object json = mapper.readValue(jsonHerd, Object.class);
				jsonHerd = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		} 
		catch (JsonProcessingException e) {
			log.error(e);
		} 
		catch (IOException e)
		{
			log.error(e);
		}
		jsonHerd = jsonHerd.replaceAll("labYaks", "herd");
		return jsonHerd;
	}
	
	@RequestMapping(value = "/stock/{daysElapsed}", method = RequestMethod.GET)
	public ModelAndView getStockFromDB(@ModelAttribute("adminCommand") AdminCommand adminCommand, @PathVariable long daysElapsed){
		try{
			Herd herd = getService().getHerd();
			log.info("Herd size from DB: "+herd.getLabYaks().size());
			
			String jsonStock = getStockJSON(herd, daysElapsed);
			log.info("JSON Stock: "+jsonStock);
			
			adminCommand.setJsonStock(jsonStock);
		}
		catch(Exception e)
		{
			log.error(e);
			return new ModelAndView("error","command",adminCommand);
		}
		return new ModelAndView("stock","command",adminCommand);
	}
	
	/**
	 * This method returns the JSON representation of Stock
	 * @param herd
	 * @param daysElapsed
	 * @return
	 */
	private String getStockJSON(Herd herd, long daysElapsed) {

		double milk = Helper.calculateMilkStock(herd, daysElapsed); 
		int skins = Helper.calculateSkinsStock(herd, daysElapsed);
		
		log.info("In stock:");
		log.info("Milk: "+milk);
		log.info("Skins: "+skins);
		
		String jsonStock = "{"+"\n"
				+ "\"milk\" : "
				+milk
				+","+"\n"
				+ "\"skins\" : "
				+skins+"\n"
				+ "}";
		
		return jsonStock;
	}
	
}
