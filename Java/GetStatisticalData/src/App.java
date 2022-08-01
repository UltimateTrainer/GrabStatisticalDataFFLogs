import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
/*adps
rdps
ndps
pdps

Eventually have the user input an encounter and a job name and what metric(aDPS,rDPS,nDPS,pDPS)
*/
class App {

	public static void main(String[] args) throws Exception {
        int page=1,encounterID=82;
        String jobName="",query="";      
		URL url = new URL("https://www.fflogs.com/api/v2/client");
        Scanner userInput = new Scanner(System.in);
        System.out.println("What job do you want to pull from statistics?(Use no spaces)");
        jobName=userInput.nextLine();
        userInput.close();
        query="{\"query\":\"{query : \\n\\tworldData {\\n\\t\\tencounter(id: "+encounterID+"){\\n\\t\\t\\tid:id\\n\\t\\t\\tname\\n\\t\\t\\tcharacterRankings(specName:\\\""+jobName+"\\\",metric:rdps,page:"+page+"includeCombatantInfo:false)\\n\\t\\t}\\n\\t\\t\\n\\t}\\n}\"}";
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("POST");

		httpConn.setRequestProperty("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI5NmUzNmZkNi04NTY3LTQ0YWUtYWUwZS1iOTc2YWE4M2Q5MjYiLCJqdGkiOiJlOGIwZGRhYjAzNWZhY2JiMzQxMjlmOGZhNDcyNmYxMDE5MzdiNTVmNzY1ZTNmMjVlMTNhYmNmZTI0ZjVhMmE4ZDkyMDU3MjNjMzdiMDljMiIsImlhdCI6MTY1OTE5Nzk0MC42ODAxOTUsIm5iZiI6MTY1OTE5Nzk0MC42ODAxOTgsImV4cCI6MTY5MDMwMTk0MC42NzI4NjcsInN1YiI6IiIsInNjb3BlcyI6WyJ2aWV3LXVzZXItcHJvZmlsZSIsInZpZXctcHJpdmF0ZS1yZXBvcnRzIl19.Yezzw3RYvPqDVywcEhCFTa-mX0GKlLuBkdmISpWKwwMwH3msYRpXyok_IP8CGJ4CcKc7tYp6gDtPCcSMc5KgLABuKheT8-ck955xl3xmL4z5N-zoVInvqMVaOVGYdngTB30qvO4kMn8YDB-HQ1MQn10xAf3WH3H-dUEFkB9SM55nxXyWvdEH47DUBLcVQmTAvD-BT0WPc35nZdNjxZsFXgI4FVLt6S5XDjNk-zgHOfdT53yA8nNvzQaYcroNSD7mH7F1gIzcbSuPlZh401dQfYAYzwMN-0khIG02uCJlbxB-omKVXfCGqbcYbAWoyCA74A9aYXtmBKJWVYms85SWQOj93_G4lOXlON2WQLnrm0Nyy08Qn5deWUTxEfhF_iHnF8Gxl4LWssqjrZOoK2iD0T7kTTwP7iu4HtM0cxHmTHOj8MibIKjroMOKz5n4ZSTUyRBMHZI40fjqM4RM61IPxxryKE-GIsCrb0xmeidcparmHssdyn4Oq7nIx-cIiU0wmhCMNheKooLShSHc9wWh4QVXbwNVP9UfFdV8DW5qUjuL8Xlc1t8cAwo3SQSpPJlNH2KiPc4C3-TQWjXSgvjfKvtsBsQlUNnk39Nu3zPNziV9Zq_XCLkIdPq7yV6UsyGlz0zYyj-zGOnSCjWa-oa95FY2WIktTgS6UClxB9rFbFA");
		httpConn.setRequestProperty("Content-Type", "application/json");

		httpConn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
		writer.write(query);
		writer.flush();
		writer.close();
		httpConn.getOutputStream().close();

		InputStream responseStream = httpConn.getResponseCode() / 100 == 2
				? httpConn.getInputStream()
				: httpConn.getErrorStream();
		Scanner s = new Scanner(responseStream).useDelimiter("\\A");
		String response = s.hasNext() ? s.next() : "";
        s.close();
		//System.out.println(response);
        try{
            fileWrite(page,response);
           
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        
	}
    public static void fileWrite (int page, String data)throws Exception{
        FileWriter json=new FileWriter("jsons/rankings"+page+".json"); 
        json.write(data);
        json.close();
    }
}
