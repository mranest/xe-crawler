package gr.ageorgiadis.xe;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class XeDateHelper {

	private static final String[] MONTHS = new String[] {
		"Ιανουαρίου",
		"Φεβρουαρίου",
		"Μαρτίου",
		"Απριλίου",
		"Μαΐου",
		"Ιουνίου",
		"Ιουλίου",
		"Αυγούστου",
		"Σεπτεμβρίου",
		"Οκτωβρίου",
		"Νοεμβρίου",
		"Δεκεμβρίου"
	};
	
	private static final DateFormatSymbols SYMBOLS;
	static {
		SYMBOLS = DateFormatSymbols.getInstance(new Locale("el"));
		SYMBOLS.setMonths(MONTHS);
	}
	
	
	private static final DateFormat IN_FORMAT = 
			new SimpleDateFormat("EEE, dd MMMM yyyy", SYMBOLS);
	
	private static final DateFormat OUT_FORMAT =
			new SimpleDateFormat("yyyy-MM-dd");
	
	public static String formatDate(String date) throws ParseException {
		return OUT_FORMAT.format(IN_FORMAT.parse(date));
	}
	
}
