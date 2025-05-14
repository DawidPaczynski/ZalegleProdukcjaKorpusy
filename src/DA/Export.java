package DA;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Export {
    private Export() {}

    public static void exportTableToExcel(JTable table, String path) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Dane");

        TableModel model = table.getModel();

        // Create header row
        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < model.getColumnCount(); col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(model.getColumnName(col));
        }

        // Populate data rows
        for (int row = 0; row < model.getRowCount(); row++) {
            Row dataRow = sheet.createRow(row + 1);
            for (int col = 0; col < model.getColumnCount(); col++) {
                Cell cell = dataRow.createCell(col);
                Object value = model.getValueAt(row, col);
                if (value != null) {
                    if (col == 0) {
                        cell.setCellValue((String) value);
                        continue;
                    }
                    try{
                        NumberFormat format = NumberFormat.getInstance(Locale.GERMANY);
                        Number number = format.parse(value.toString());
                        cell.setCellValue(number.doubleValue());
                    }
                    catch (NumberFormatException | ParseException e) {
                        cell.setCellValue(value.toString());
                    }
                }
            }
        }

        // Auto-size columns
        for (int col = 0; col < model.getColumnCount(); col++) {
            sheet.autoSizeColumn(col);
        }

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
            // Open the file
            File file = new File(path);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Błąd: " + e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}