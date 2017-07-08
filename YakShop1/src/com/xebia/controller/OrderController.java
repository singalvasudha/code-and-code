package com.xebia.controller;

import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xebia.beans.Herd;
import com.xebia.beans.Order;
import com.xebia.command.OrderCommand;
import com.xebia.service.RESTService;
import com.xebia.util.Helper;

@Controller
@RequestMapping("/yak-shop")
public class OrderController {
	
	private static Logger log = Logger.getLogger(OrderController.class.getName());
	
	@Autowired 
	private RESTService service;

	public RESTService getService() {
		return service;
	}
	
	@RequestMapping(value = "/placeorder", method = RequestMethod.GET)
	public ModelAndView getOrderForm(@ModelAttribute("orderCommand") OrderCommand orderCommand){
		return new ModelAndView("order","command",orderCommand);
	}
	

	@RequestMapping(value = "/submitorder", method = RequestMethod.POST)
	public @ResponseBody void submitOrder(@ModelAttribute("orderCommand") OrderCommand orderCommand, HttpServletResponse servResponse, HttpServletRequest request){
		
		String uri = "";
		try {
				URI requestUri = new URI(request.getRequestURL().toString());
				uri = requestUri.toString().replaceFirst("submitorder", "order") + "/" + orderCommand.getDaysElapsed();
				log.info("Web Service url: "+uri);
		} 
		catch (URISyntaxException e1) {
			log.error("Failed to get URI");
		}
		
		log.info("Days elapsed: "+orderCommand.getDaysElapsed());
		log.info("Customer Name: "+orderCommand.getCustName());
		log.info("Milk ordered: "+orderCommand.getMilkReqd());
		log.info("Skins ordered: "+orderCommand.getSkinsReqd());
	    
	    Order order = new Order(orderCommand.getMilkReqd(),orderCommand.getSkinsReqd());
	    String custName = orderCommand.getCustName();
	    
	    HttpEntity<String> entity = generateHttpEntityFromInputs(order, custName);
	    try
	    {
	    	RestTemplate restTemplate = new RestTemplate();
	    	ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

	    	log.info("Received response from web service");
		    PrintWriter out = servResponse.getWriter();
	        out.write(response.getBody());
	    }
	    catch(Exception e)
	    {
	    	log.error(e);
	    }
	}
	
	/**
	 * This method sets http headers and creates HTTPEntity object
	 * @param order
	 * @param custName
	 * @return
	 */
	private HttpEntity<String> generateHttpEntityFromInputs(Order order, String custName) {
		
		Gson gson = new GsonBuilder().create();
	    String jsonOrder = gson.toJson(order);
	    
	    JSONObject object = new JSONObject();
	    try {
	    	object.put("customer", custName);
	    	object.put("order", jsonOrder);
		} 
	    catch (JSONException e) {
			log.error(e);
		}
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", "Basic " + "xxxxxxxxxxxx");
	    
	    HttpEntity<String> entity = new HttpEntity<String>(object.toString(), headers);
		return entity;
	}

	/**
	 * This method creates order with the given inputs
	 * @param orderCommand
	 * @param daysElapsed
	 * @param str - it contains string representation of JSON representation of Customer name & Order object
	 * @return
	 */
	@RequestMapping(value = "/order/{daysElapsed}", method = RequestMethod.POST)
	public ModelAndView createOrder(@ModelAttribute("orderCommand") OrderCommand orderCommand, @PathVariable long daysElapsed, @RequestBody String str){
		
		log.info("Number of days elapsed: "+orderCommand.getDaysElapsed());
		Gson gson = new GsonBuilder().create();
		String cust="";
		Order order = new Order(0,0);
		try {
				JSONObject result = new JSONObject(str);
				String jsonOrder = result.getString("order");
				cust = result.getString("customer");
				order = gson.fromJson(jsonOrder, Order.class);
				
				log.info("Customer name: "+cust);
	            log.info("Milk Ordered: "+order.getMilk());
	            log.info("Skins Ordered: "+order.getSkins());
		} 
		catch (JSONException e) 
		{
			log.error(e);
			return new ModelAndView("error","command",orderCommand);
		}
		
		Herd herd = getService().getHerd();
		
		double milk = Helper.calculateMilkStock(herd, daysElapsed); 
		int skins = Helper.calculateSkinsStock(herd, daysElapsed);
		
		log.info("In stock:");
		log.info("Milk: "+milk);
		log.info("Skins: "+skins);
		
		if(milk >= order.getMilk() && skins >= order.getSkins()){
			orderCommand.setStatus(201);
			orderCommand.setMilkDelivered(order.getMilk());
			orderCommand.setSkinsDelivered(order.getSkins());
			log.info("Milk & Skins delivered");
		}else
			if(milk < order.getMilk() && skins < order.getSkins()){
				orderCommand.setStatus(202);
				orderCommand.setMilkDelivered(0);
				orderCommand.setSkinsDelivered(0);
				log.info("Milk & Skins both not delivered");
			}
			else
				if(milk >= order.getMilk() && skins < order.getSkins()){
					orderCommand.setStatus(206);
					orderCommand.setMilkDelivered(order.getMilk());
					log.info("Milk delivered");
				}
				else
					{
						orderCommand.setStatus(206);
						orderCommand.setSkinsDelivered(order.getSkins());
						log.info("Skins delivered");
					}
		
		return new ModelAndView("ordersuccess","command",orderCommand);
	}

}
