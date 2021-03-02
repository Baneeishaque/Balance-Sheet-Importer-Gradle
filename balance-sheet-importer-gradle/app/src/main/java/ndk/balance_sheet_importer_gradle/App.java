package ndk.balance_sheet_importer_gradle;

import com.github.miachm.sods.Range;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import java.io.FileReader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class App {

    // one instance, reuse
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static void main(String[] args) {
        
        App app = new App();
        System.out.println("Wallet Transactions");
        app.processSheet("Wallet from 20-11-2020",6,275);
        System.out.println("\n\n");
        System.out.println("PNB Transactions");
        app.processSheet("PNB from 20-11-2020",11,305);
    }

    public void processSheet(String sheetName, int accountId, int lastRowNumber) {

        try {
            SpreadSheet spread = new SpreadSheet(new File(getClass().getClassLoader().getResource("Balance Sheet.ods").toURI()));
            
            Sheet walletFrom20112020 = spread.getSheet(sheetName);
            Range range = walletFrom20112020.getDataRange();
            
            String previousTransactionDate = "";

            String pnbTransactionDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
            DateTimeFormatter pnbTransactionDateFormatter = DateTimeFormatter.ofPattern(pnbTransactionDateTimeFormat);

            String mySqlDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
            DateTimeFormatter mySqlDateTimeFormatter = DateTimeFormatter.ofPattern(mySqlDateTimeFormat);

            String railwayTimeFormat = "HH:mm:ss";
            DateTimeFormatter railwayTimeFormatter = DateTimeFormatter.ofPattern(railwayTimeFormat);

            LocalTime initialTime = LocalTime.parse("09:00:00", railwayTimeFormatter);

            LocalTime nextTime = LocalTime.now();

            LocalDateTime currentTransactionDateTime;

            int userId = 3;
            int unProcessedCreditsId = 684;
            int pnbAccountId = accountId;
            int unProcessedDebitsId = 683;
            String serverApiUrl = "http://account-ledger-server.herokuapp.com/http_API/insert_Transaction_v2.php";
            
            for(int i=1; i<lastRowNumber; i++)
            {
                Object eventDate = range.getCell(i,0).getValue();
                Object particulars = range.getCell(i,1).getValue();
                Object amount =range.getCell(i,2).getValue();

                // System.out.println((eventDate == null ? "\t" : eventDate.toString()) + "\t" + particulars.toString() + "\t" + amount.toString());

                if (eventDate == null) {

                    currentTransactionDateTime = LocalDateTime.parse(previousTransactionDate + " " + nextTime.toString() + ":00", pnbTransactionDateFormatter);
                    nextTime = nextTime.plusMinutes(5);

                } else {

                    currentTransactionDateTime = LocalDateTime.parse(eventDate.toString() + " " + initialTime.toString() + ":00", pnbTransactionDateFormatter);
                    previousTransactionDate = eventDate.toString();
                    nextTime = initialTime.plusMinutes(5);
                }

                //- sign in amount means debit
                if (amount.toString().contains("-")) {

                    // performHttpPost(currentTransactionDateTime.format(mySqlDateTimeFormatter), userId, particulars.toString(), amount.toString().replace("-", ""), pnbAccountId, unProcessedDebitsId, serverApiUrl);
                    System.out.println("Debit\t"+ currentTransactionDateTime.format(mySqlDateTimeFormatter) + "\t" + particulars.toString() + "\t" + amount.toString().replace("-", ""));
                    
                } else {
                    
                    // performHttpPost(currentTransactionDateTime.format(mySqlDateTimeFormatter), userId, particulars.toString(), amount.toString(), unProcessedCreditsId, pnbAccountId, serverApiUrl);
                    System.out.println("Credit\t"+ currentTransactionDateTime.format(mySqlDateTimeFormatter) + "\t" + particulars.toString() + "\t" + amount.toString());
                }
            }
        } catch (IOException | URISyntaxException e){

            e.printStackTrace();
        }
    }

    public void performHttpPost(String eventDateTime, int userId, String particulars, String amount, int fromAccountId, int toAccountId, String serverApiUrl) throws IOException, InterruptedException {

//        $event_date_time = filter_input(INPUT_POST, 'event_date_time');
//        $user_id = filter_input(INPUT_POST, 'user_id');
//        $particulars = filter_input(INPUT_POST, 'particulars');
//        $amount = filter_input(INPUT_POST, 'amount');
//        $from_account_id = filter_input(INPUT_POST, 'from_account_id');
//        $to_account_id = filter_input(INPUT_POST, 'to_account_id');

        // form parameters
        Map<Object, Object> data = new HashMap<>();
        data.put("event_date_time", eventDateTime);
        data.put("user_id", userId);
        data.put("particulars", particulars);
        data.put("amount", amount);
        data.put("from_account_id", fromAccountId);
        data.put("to_account_id", toAccountId);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(data))
                .uri(URI.create(serverApiUrl))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
    //    System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());
    }

    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {

        var builder = new StringBuilder();
        data.entrySet().stream().map((entry) -> {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            return entry;
        }).forEachOrdered((entry) -> {
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        });
        System.out.println(builder.toString());
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
}
