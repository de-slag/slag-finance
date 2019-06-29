package de.slag.finance3.logic;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.common.XiDataDao;
import de.slag.common.base.BaseException;
import de.slag.common.base.event.EventBus;
import de.slag.common.model.XiData;
import de.slag.finance.FinPriceDao;
import de.slag.finance.model.FinPrice;
import de.slag.finance3.events.DataImportedEvent;

public class FinPriceImportRunner implements Runnable {

	private static final Log LOG = LogFactory.getLog(FinPriceImportRunner.class);

	private static final String DD_MM_YYYY = "dd.MM.yyyy";

	private final FinPriceDao finPriceDao;

	private final XiDataDao xiDataDao;

	private int imported = 0;

	private long start;

	private long end;

	public FinPriceImportRunner(FinPriceDao finPriceDao, XiDataDao xiDataDao) {
		super();
		this.finPriceDao = finPriceDao;
		this.xiDataDao = xiDataDao;
	}

	@Override
	public void run() {
		start = System.currentTimeMillis();
		final Collection<Long> allIds = xiDataDao.findAllIds();

		allIds.forEach(t -> {
			try {
				importData(t);
			} catch (ParseException e) {
				throw new BaseException(e);
			}
		});
		end = System.currentTimeMillis();
		EventBus.occure(new DataImportedEvent("data imported: " + imported + ", took (ms) "+ (end - start)));
	}

	private void importData(Long xiId) throws ParseException {
		final XiData xiData = xiDataDao.loadById(xiId).get();
		final Properties properties = xiData.toProperties();
		final String closeAsString = Objects.requireNonNull(properties.getProperty("Schluss"));
		final String dateAsString = Objects.requireNonNull(properties.getProperty("Datum"));
		final String isin = Objects.requireNonNull(properties.getProperty("Isin"));

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DD_MM_YYYY);
		final Date date = simpleDateFormat.parse(dateAsString);

		if (finPriceDao.loadPriceBy(isin, date).isPresent()) {
			return;
		}

		final String preformatClose = closeAsString.replace(".", "").replace(",", ".");
		final BigDecimal close = BigDecimal.valueOf(Double.valueOf(preformatClose));

		FinPrice build = new FinPrice.Builder().date(date).close(close).isin(isin).build();
		finPriceDao.save(build);
		imported++;

	}
}
