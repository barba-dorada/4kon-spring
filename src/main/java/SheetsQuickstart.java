

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SheetsQuickstart {
    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

    /**
     * Directory to store user credentials for this application.
     */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");

    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the scopes required by this quickstart.
     * <p>
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = SheetsQuickstart.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     *
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Sheets service = getSheetsService();

        // Prints the names and majors of students in a sample spreadsheet:
        // https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
        String spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
        String range = "Class Data!A2:E";
        //print(service, spreadsheetId, range);
        spreadsheetId = "1EmCA5qbW0VnT09QwskgBag-jO4O4rDzYQlHRlHXnMpk";
        range = "'ф1'!A2:H";
        List<Fact> r = load(service, spreadsheetId, range, new FactMapper());
        print(r);

       /* range="'факты.мес'!A2:H";
        print(service, spreadsheetId, range);*/

        range = "'планы с Q4Y2016'!A3:L";
        List<TemplPlan> r2 = load(service, spreadsheetId, range, new TemplPlanMapper());
        print(r2);

    }
    private static void print(List<? extends Object> list) {
        String commaSeparatedNumbers = list.stream().map(i -> i.toString()).collect(Collectors.joining("\n"));
        System.out.println(commaSeparatedNumbers);
    }

/*    private static void print(Sheets service, String spreadsheetId, String range) throws IOException {
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.size() == 0) {
            System.out.println("No data found.");
        } else {
            System.out.println("DATA______________________________________");
            for (List row : values) {
                for (Object o : row) {
                    System.out.print(o + ",");
                }
                System.out.println();
            }
        }
    }*/

    private static List<Fact> load(Sheets service, String spreadsheetId, String range, FactMapper m) throws IOException {
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> values = response.getValues();
        List<Fact> result = new ArrayList<>();

        if (values != null && values.size() > 0) {
            for (List row : values) {
                Fact v = m.map(row);
                if(v.getDate()!=null&&v.getAmount()!=null) {
                    result.add(v);
                }
            }
        }
        return result;
    }

    private static List<TemplPlan> load(Sheets service, String spreadsheetId, String range, TemplPlanMapper m) throws IOException {
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> values = response.getValues();
        List<TemplPlan> result = new ArrayList<>();

        if (values != null && values.size() > 0) {
            for (List row : values) {
                TemplPlan v = m.map(row);
                //if(v.getDate()!=null&&v.getAmount()!=null) {
                result.add(v);
                //}
            }
        }
        return result;
    }
}

class TemplPlanMapper extends BaseMapper {
    public TemplPlan map(List row) {
        TemplPlan v = new TemplPlan();
        if (row.isEmpty()) {
            return v;
        }
        v.setCateory(row.get(0).toString());
        v.setComment(row.get(1).toString());
        v.setAmount(toBigDecimal(row.get(2).toString()));
        v.setFirstDate(toLocalDate(row.get(5).toString()));
        v.setTotalPerYear(toBigDecimal(row.get(7).toString()));
        return v;
    }
}

class FactMapper extends BaseMapper {
    // TODO: 26.10.2016 add filter
    public Fact map(List row) {
        Fact res = new Fact();
        res.setUser(row.get(0).toString());
        res.setDate(toLocalDate(row.get(1).toString()));
        res.setAccount(row.get(2).toString());
        res.setCateory(row.get(3).toString());
        String money = row.get(4).toString();
        res.setAmount(toBigDecimal(money));
        res.setComment(row.get(5).toString());
        res.setSubtotal(toBigDecimal(row.get(6).toString()));
        res.setMonth(row.get(7).toString());
        return res;
    }
}

class BaseMapper {
    BigDecimal toBigDecimal(String from) {
        String money = from;
        money = money.replaceAll(",", ".");
        money = money.replace(" ", "");

        if (money.length() > 0) {
            BigDecimal bd = new BigDecimal(money);
            return bd;
        }
        return null;
    }

    LocalDate toLocalDate(String from) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            final LocalDate dt = LocalDate.parse(from, dtf);
            return dt;
        } catch (Exception e) {
        }
        return null;
    }
}