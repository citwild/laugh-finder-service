package edu.uw.citw.service;

import edu.uw.citw.persistence.repository.NetworkDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;


@Service
public class LaughNetworkDataService {

    private static final Logger log = LoggerFactory.getLogger(LaughNetworkDataService.class);

    private static final String CSV_SEP = ",";

    private NetworkDataRepository repository;

    public LaughNetworkDataService(NetworkDataRepository repository) {
        this.repository = repository;
    }

    /**
     * Go through and append person laughter's from, laughter's to, and percentage of participation over presence
     *
     * Using a custom query, so no POJO to represent the values.
     */
    public String findEngagementEdges_CSV() {
        StringBuilder b = new StringBuilder("");
        b.append("from,to,weight\n");
        try {
            List<Object[]> vals = repository.getLaughEngagementRatios();
            for (Object[] val : vals) {
                b.append((BigInteger) val[0])
                        .append(CSV_SEP)
                        .append((BigInteger) val[1])
                        .append(CSV_SEP)
                        .append((BigDecimal) val[2])
                        .append('\n');
            }
        } catch (Exception e) {
            log.error("Failure retrieving from database", e);
        }
        return b.toString();
    }
}
