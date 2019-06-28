package de.slag.finance3.logic;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import de.slag.common.XiDataDao;
import de.slag.common.base.BaseException;
import de.slag.common.model.XiData;
import de.slag.finance.FinDataPointDao;
import de.slag.finance.model.FinPrice;

public class FinPriceImportRunner implements Runnable {

	private static final String DD_MM_YYYY = "dd.MM.yyyy";

	private final FinDataPointDao finDataPointDao;

	private final XiDataDao xiDataDao;

	public FinPriceImportRunner(FinDataPointDao finDataPointDao, XiDataDao xiDataDao) {
		super();
		this.finDataPointDao = finDataPointDao;
		this.xiDataDao = xiDataDao;
	}

	@Override
	public void run() {
		xiDataDao.findAllIds().forEach(t -> {
			try {
				importData(t);
			} catch (ParseException e) {
				throw new BaseException(e);
			}
		});
	}

	private void importData(Long xiId) throws ParseException {
		final XiData xiData = xiDataDao.loadById(xiId).get();
		final Properties properties = xiData.toProperties();
		final String valueAsString = Objects.requireNonNull(properties.getProperty("Schluss"));
		final String dateAsString = Objects.requireNonNull(properties.getProperty("Datum"));
		final String isin = Objects.requireNonNull(properties.getProperty("Isin"));

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DD_MM_YYYY);
		final Date date = simpleDateFormat.parse(dateAsString);

		if (finDataPointDao.loadPriceBy(isin, date).isPresent()) {
			return;
		}

		final String preformat = valueAsString.replace(".", "").replace(",", ".");
		final BigDecimal value = BigDecimal.valueOf(Double.valueOf(preformat));

		FinPrice build = new FinPrice.Builder().date(date).value(value).isin(isin).build();
		finDataPointDao.save(build);

	}
}
