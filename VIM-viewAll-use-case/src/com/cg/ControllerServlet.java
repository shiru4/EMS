package com.cg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cg.dao.CarDAO;
import com.cg.dao.impl.JDBCCarDAO;
import com.cg.dto.CarDTO;
import com.cg.util.DBUtility;
import com.cg.util.ServiceLocatorException;

import sun.font.CreatedFontTracker;

//TODO 1 Import appropriate classes

//Follow TODOs (if available)
/**
 * 
 * This is a ControllerServlet class
 * @see java.lang.Object
 * @author Abhishek
 * 
 *
 */


public class ControllerServlet extends HttpServlet
{
    private static final String ACTION_KEY = "action";
    private static final String VIEW_CAR_LIST_ACTION = "viewCarList";
    private static final String ADD_CAR_ACTION = "addCar";
    private static final String SAVE_CAR_ACTION = "saveCar";
    private static final String EDIT_CAR_ACTION = "editCar";
    private static final String DELETE_CAR_ACTION = "deleteCar";
    private static final String ERROR_KEY = "errorMessage";
    List<CarDTO> list = new ArrayList<CarDTO>();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
		
        //TODO 2 Invoke processRequest
    	
    	try {
			processRequest(request, response);
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        //TODO 3 Invoke processRequest
    	try {
			processRequest(request, response);
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * This method will process request based on action performed on screen. 
     * @param request				HttpServletRequest
     * @param response				HttpServletResponse
     * @throws ServletException		if error occurs
     * @throws IOException			if error occurs
     * @throws NamingException 
     * @throws ServiceLocatorException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ServiceLocatorException, NamingException {
        String actionName = request.getParameter(ACTION_KEY);
        String destinationPage = null; 
        CarDAO carDAO = DBUtility.getCarDAO();
        List<CarDTO> car=null;
        CarDTO carddto=null;
        // perform action
        if(VIEW_CAR_LIST_ACTION.equals(actionName))
	        {    
        		
	            //TODO 4 
				//Use carDao to get the list of the cars
        	carDAO=new JDBCCarDAO();
        	car=carDAO.findAll();
	        	//Set the list in request with attribute name as 'carList'
			request.setAttribute("carList",car);
	        	//Set the destination page to carList.jsp
        	destinationPage="/carList.jsp";
			
        }
        else if(ADD_CAR_ACTION.equals(actionName))
        {
			//TODO 5 
			//Create a new CarDTO and set in request with attribute name as 'car'
        	carddto=new CarDTO();
			request.setAttribute("car",carddto);
        	//Set the destination page to carForm.jsp
            destinationPage="/carForm.jsp";
            System.out.println("in addcar");  
        }  
        else if(EDIT_CAR_ACTION.equals(actionName))
        {
			//TODO 6 
			//Get the car id from request, with parameter name as 'id'
			String carID=request.getParameter("id");
			System.out.println("id is  :"+carID);
			carDAO=new JDBCCarDAO();
			//Find the respective car (CarDTO) from carDAO using appropriate API of DAO
			carddto=carDAO.findById(Integer.parseInt(carID));
        	//Set the found car in request with name as 'car'
			request.setAttribute("car",carddto);
        	//Set the destination page to carForm.jsp
            destinationPage="/carForm.jsp";
            
        }        
        else if(SAVE_CAR_ACTION.equals(actionName))
        {
			//TODO 7 
			//Create a new CarDTO
        	int id;
        	carDAO = new JDBCCarDAO();
        	car = new ArrayList<CarDTO>();
        	System.out.println("in save car ");
        	carddto=new CarDTO();
			//set the properties of the DTO from request parameters
			id=Integer.parseInt(request.getParameter("id"));
        	carddto.setId(id);
			carddto.setMake(request.getParameter("make"));
			carddto.setModel(request.getParameter("model"));
			carddto.setModelYear(request.getParameter("modelYear"));
			if(id==-1){
				carDAO.create(carddto);
			}else{
				carDAO.update(carddto);
			}
			
			car.add(carddto);
			request.setAttribute("carList",car);
			destinationPage="/carList.jsp";
		   //   carDAO.create(carddto);//see
        	//If it is a new car then invoke create api of DAO else update api
			
        	//Get all the Cars using DAO
			
			
        	//Set the found cars in request with name as 'carList'
			
			
        	//Set the destination page to carList.jsp
            
        }  
        else if(DELETE_CAR_ACTION.equals(actionName))
        {
        	System.out.println("in delete car");
        	//TODO 8 
			//Get the ids of all cars to be deleted from request
        	String array[]=request.getParameterValues("id");
			//Use appropriate api of DAO to delete all cars 
			
        	System.out.println(array);
        	carDAO=new JDBCCarDAO();
        	carDAO.delete(array);
        	//Get all the Cars using DAO
			car=carDAO.findAll();
        	//Set the found cars in request with name as 'carList'
			for(int i=0;i<car.size();i++){
			request.setAttribute("carList",car.get(i));
			}
			//Set the destination page to carList.jsp
            destinationPage="/carList.jsp";
        }                    
        else
        {
            String errorMessage = "[" + actionName + "] is not a valid action.";
            request.setAttribute(ERROR_KEY, errorMessage);
        }

        System.out.println("outside all ifs and going from : "+actionName);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destinationPage);
        System.out.println("vavlue : +"+dispatcher);
        dispatcher.forward(request,response);
        //TODO 9 Use appropriate Servlet API to forward the request to 
		//appropriate destination page set in above if else blocks depending on action.
        
        
    }
}
