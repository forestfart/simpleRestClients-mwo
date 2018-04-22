// Kod klasy utworzony na podstawie tutoriala: http://www.tutorialspoint.com/restful/restful_first_application.htm

package mwo.sr.restclient;

import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;

import mwo.sr.google.G1;

//http://maps.googleapis.com/maps/api/directions/json?origin=LOC1&destination=LOC2&language=pl-PL


public class ClientJ2 {

	private Client client;
	private static final String SUCCESS_RESULT="<result>success</result>";
	private static final String PASS = "pass";
	private static final String FAIL = "fail";
	
	public static void main(String[] args) 
	{
		ClientJ2 c = new ClientJ2();
		c.init();
		c.getRawJson();
		c.interactiveUI(c);

	}

	public void interactiveUI(ClientJ2 client)
	{
		String line = null;
        java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in)); 
        
        do
        {
            try
            {
                System.out.print("==> ");
                System.out.flush();
                line = in.readLine();
                if(line == null)
                {
                    break;
                }
                else if(line.equals("raw"))
                {
                    client.getRawJson();
                }
                else if(line.startsWith("xx"))
                {
                	int val = Integer.parseInt(line.split(" ")[1]);
                	System.out.println(val);
                    //client.
                }

                else if(line.equals("x"))
                {
                }
                else
                {
                    System.out.println("unknown command `" + line + "'");
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        while(!line.equals("x"));
	}

	private void init(){
		client = ClientBuilder.newClient();
		//client.register(new Authenticator("mwo", "mwo123"));
		client.register(JacksonJsonProvider.class);
	}


	public void getRawJson()
	{
		System.out.println("\n\n*** Starting test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"'...");

		G1 res = client
				.target("http://www.ttss.krakow.pl/internetservice/services/passageInfo/stopPassages/stop?stop=365&mode=departure")
				.request(MediaType.APPLICATION_JSON).get().readEntity(G1.class);
		
		String result = PASS;
		if(res == null) result = FAIL;
		
		System.out.println("Result: " + res.getStopName() + " " + res.getRoutes().get(0).getDirections().get(0));
		System.out.println("\n\n*** Test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"' finished");
	}
	
	
	
	public void getUser()
	{
		System.out.println("\n\n*** Starting test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"'...");

		User user = client
				.target("http://..............")
				.request(MediaType.APPLICATION_XML)
				.get(User.class);
		
		String result = PASS;
		if(user == null) result = FAIL;
		
		System.out.println("Result: " + result );
		System.out.println("\n\n*** Test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"' finished");
	}
	
	
	public void getAllUsers()
	{
		System.out.println("\n\n*** Starting test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"'...");

		GenericType<List<User>> list = new GenericType<List<User>>() {};
		List<User> users = client
				.target("http://..................")
				.request(MediaType.APPLICATION_XML)
				.get(list);
		String result = PASS;
		if(users.isEmpty()){
			result = FAIL;
		}
		System.out.println("Result: " + result );
		System.out.println("\n\n*** Test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"' finished");
	}
	

	public void addUser()
	{
		System.out.println("\n\n*** Starting test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"'...");

		User u = new User(24, "Jozek", "Stroz");
		String callResult = client
				.target("http://..............")
				.request(MediaType.APPLICATION_XML)
				.put(Entity.entity(u,
						MediaType.APPLICATION_JSON),
						String.class);		
 		
		String result = PASS;
		if(!SUCCESS_RESULT.equals(callResult)) result = FAIL;
		
		System.out.println("Result: " + callResult );
		System.out.println("\n\n*** Test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"' finished");
	}

	
	private void updateUser()
	{
		System.out.println("\n\n*** Starting test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"'...");

		Form form = new Form();
		form.param("id", "15");
		form.param("name", "wacek");
		//form.param("profession", "entomolog");

		String callResult = client
				.target("http://...............")
				.request(MediaType.APPLICATION_XML)
				.post(Entity.entity(form,
						MediaType.APPLICATION_FORM_URLENCODED_TYPE),
						String.class);
		String result = PASS;
		if(!SUCCESS_RESULT.equals(callResult)){
			result = FAIL;
		}

		System.out.println("Result: " + result );
		System.out.println("\n\n*** Test case '" + Thread.currentThread().getStackTrace()[1].getMethodName() +"' finished");
	}

}
