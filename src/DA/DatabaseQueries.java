package DA;

import BL.QueryRecord;
import BL.Workplace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueries {
    private DatabaseQueries(){}

    public  static List<QueryRecord> importData() throws SQLException {
        List<QueryRecord> records = new ArrayList<>();
        String sql="SELECT S.ZAttribut04 as Kappstelle, ST.TATTRIBUT7 as Block, S.ArtikelNr, ART.ARTIKELBEZEICHNUNG, CONVERT(numeric(15, 2), AP.Fertdauer / 60 / 60) AS FertdauerPVM, Ap.FERTIGTERMINDATUM, S.AuftragsNr, S.AuftragsPos\n" +
                "FROM FLS_SCHRITTE S\n" +
                "LEFT JOIN FLS_Stueckliste ST on S.ID=ST.ID AND S.zattribut01 = ST.zattribut3\n" +
                "LEFT JOIN artikel ART ON S.artikelnr = ART.artikel\n" +
                "LEFT JOIN fls_arbeitsplan AP ON S.id = AP.id    AND S.fertauftragsnr = AP.fertauftragsnr AND S.fertauftragspos = AP.fertauftragspos AND S.artikelnr = AP.artikelnr AND s.KzTeil = AP.KzTeil AND s.Zattribut01 = AP.zattribut1 AND S.arbeitsgangnr = AP.arbeitsgangnr\n" +
                "WHERE S.ZAttribut04 IN(32301,32601,32801,32401,32103,32501,32804,32105,32901,32902,32106,32107,32304,32305)\n" +
                "AND ST.TATTRIBUT7 <>'' AND AP.FERTIGTERMINDATUM>DATEADD(MONTH,-6,GETDATE()) AND S.Status<40;";
        Connection conn = DatabaseConnection.setConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int workplaceID = rs.getInt("Kappstelle");
            String block = rs.getString("Block");
            String itemNumber = rs.getString("ArtikelNr");
            String itemName = rs.getString("ARTIKELBEZEICHNUNG");
            float productionTime = rs.getFloat("FertdauerPVM");
            java.sql.Timestamp sqlTimestamp = rs.getTimestamp("FERTIGTERMINDATUM");
            java.time.LocalDateTime productionDate = sqlTimestamp.toLocalDateTime();
            String orderNumber = rs.getString("AuftragsNr");
            int orderPos = rs.getInt("AuftragsPos");

            QueryRecord record = new QueryRecord(workplaceID, block, itemNumber, itemName, productionTime, productionDate, orderNumber, orderPos);
            records.add(record);
        }
        conn.close();
        return records;
    }
}
