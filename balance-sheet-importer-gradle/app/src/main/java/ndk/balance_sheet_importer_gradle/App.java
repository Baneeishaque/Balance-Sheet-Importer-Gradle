package ndk.balance_sheet_importer_gradle;

import com.github.miachm.sods.Range;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.net.URISyntaxException;

public class App {

    public static void main(String[] args) {
        
        App app = new App();
        app.processSheet();
    }

    public void processSheet() {

        try {
            SpreadSheet spread = new SpreadSheet(new File(getClass().getClassLoader().getResource("Balance Sheet.ods").toURI()));
            
            // System.out.println("Number of sheets: " + spread.getNumSheets());

            // List<Sheet> sheets = spread.getSheets();

            // for (Sheet sheet : sheets) {
            //     System.out.println("In sheet " + sheet.getName());

            //     Range range = sheet.getDataRange();
            //     System.out.println(range.toString());
            // }

            Sheet walletFrom20112020 = spread.getSheet("Wallet from 20-11-2020");
            Range range = walletFrom20112020.getDataRange();
            // System.out.println(range.toString());

            for(int i=1; i<=274; i++)
            {
                Object eventDate = range.getCell(i,0).getValue();
                Object particulars = range.getCell(i,1).getValue();
                Object amount =range.getCell(i,2).getValue();

                if(particulars == null) {

                    continue;
                
                } else {

                    System.out.println((eventDate == null ? "\t" : eventDate.toString()) + "\t" + particulars.toString() + "\t" + amount.toString());
                }
            }
        } catch (IOException | URISyntaxException e){

            e.printStackTrace();
        }
    }
}
