/**
 * 
 */
package com.dad;

import java.util.Comparator;

/**
 * @author Daito
 * 
 */
public class ListComparator implements Comparator<Profile> {
	public static final String SORT_ITM_COMPANY = "COMPANY";
	public static final String SORT_ITM_NAME = "NAME";
	public static final String SORT_ITM_SEX = "SEX";

	private String sortItmId = "";

	public ListComparator(final String sortItm) {
		this.sortItmId = sortItm;
	}

	@Override
	public int compare(final Profile lhs, final Profile rhs) {
		if (SORT_ITM_COMPANY.equals(this.sortItmId)) {
			return lhs.getCompany().compareTo(rhs.getCompany());
		} else if (SORT_ITM_NAME.equals(this.sortItmId)) {
			return lhs.getNameKana().compareTo(rhs.getNameKana());
		} else if (SORT_ITM_SEX.equals(this.sortItmId)) {
			return lhs.getSex() - rhs.getSex();
		}
		return lhs.getCompany().compareTo(rhs.getCompany());
	}
}