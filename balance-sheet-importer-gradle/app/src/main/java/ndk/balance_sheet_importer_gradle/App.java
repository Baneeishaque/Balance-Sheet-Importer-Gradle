package ndk.balance_sheet_importer_gradle;

import com.github.miachm.sods.Range;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.io.FileReader;
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

            for(int i=1; i<=range.getLastRow(); i++)
            {
                for(int j=0; j<=range.getLastColumn(); j++)
                {
                    try {

                        System.out.print(range.getCell(i,j).getValue().toString()+"\t");
                    
                    } catch (NullPointerException ex) {

                        continue;
                    }
                }
                System.out.print("\n");
            }

        } catch (IOException | URISyntaxException e){

            e.printStackTrace();
        }
    }
}
