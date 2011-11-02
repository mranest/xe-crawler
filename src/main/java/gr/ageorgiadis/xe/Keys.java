package gr.ageorgiadis.xe;

import java.util.ListResourceBundle;

public class Keys extends ListResourceBundle {

	public static final String PRICE_KEY = "Τιμή";

	@Override
	protected Object[][] getContents() {
		return new Object[][] {
				{ "areaStorage", "Εμβαδόν αποθήκης" },
				{ "bathrooms", "Μπάνια/ WC" },
				{ "district", "Περιοχή" },
				{ "airConditioningAvailable", "Κλιματισμός" },
				{ "gardenAvailable", "Κήπος" },
				{ "bedrooms", "Υπνοδωμάτια" },
				{ "createdOn", "Δημιουργία αγγελίας" },
				{ "parkingType", "Είδος Πάρκιν" },
				{ "statusDetailed", "Λεπτομερής κατάσταση" },
				{ "furnished", "Επιπλωμένο" },
				{ "hits", "Η αγγελία έχει έως τώρα" },
				{ "fireplaceAvailable", "Τζάκι" },
				{ "storageAvailable", "Με αποθήκη" },
				{ "availability", "Διαθεσιμότητα" },
				{ "lastUpdatedOn", "Tελευταία ενημέρωση αγγελίας" },
				{ "area", "Εμβαδόν" },
				{ "status", "Κατάσταση" },
				{ "adOwner", "Αγγελία από" },
				{ "price", PRICE_KEY },
				{ "naturalGasAvailable", "Φυσικό αέριο" },
				{ "type", "Είδος" },
				{ "telephones", "Τηλέφωνα επικοινωνίας" },
				{ "availableOn", "Διαθέσιμο από" },
				{ "restoredOn", "Έτος Ανακαίνισης" },
				{ "sharedExpensesFree", "Χωρίς Κοινόχρηστα" },
				{ "parkingAvailable", "Πάρκιν" },
				{ "floor", "Όροφος" },
				{ "autonomousHeatingAvailable", "Αυτόνομη θέρμανση" },
				{ "solarWaterHeaterAvailable", "Ηλιακός θερμοσίφωνας" },
				{ "link", "Σύνδεσμος" },
				{ "orientation", "Προσανατολισμός" },
				{ "masterBedrooms", "Υπνοδωμάτια Master" },
				{ "builtOn", "Έτος Κατασκευής" },
				{ "safetyDoorAvailable", "Πόρτα ασφαλείας" },
				{ "view", "Θέα" },
				{ "tents", "Τέντες" },
				{ "configuration", "Διαμόρφωση Χώρων" }
		};
	}

	/* E.g. Παρασκευή, 07 Οκτωβρίου 2011 */
	public static boolean isFullDate(String key) {
		return key.equals("createdOn") || key.equals("lastUpdatedOn");
	}

	public static boolean isNumeric(String key) {
		return key.equals("builtOn") ||
				key.equals("area") ||
				key.equals("bathrooms") ||
				key.equals("masterBedrooms") ||
				key.equals("area") ||
				key.equals("restoredOn") ||
				key.equals("bedrooms") ||
				key.equals("areaStorage") ||
				key.equals("price");
	}
	
	public static boolean isSkipped(String key) {
		return key.equals("telephones");
	}
	
}
