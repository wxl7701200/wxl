package wxl.lt.utils;



import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  



/**
 * 
 * @author Yuan youlin 袁友林
 */
public class StringUtil {	

	public static final String EMPTY = "";

	public static final int INDEX_NOT_FOUND = -1;

	private static final int PAD_LIMIT = 8192;
	
	private static final double EARTH_RADIUS = 6378.137;

	private static double rad(double d){
		return d * Math.PI / 180.0;
	}

	public StringUtil() {
		super();
	}
	
	
	public static double convertLastDouble(double num){
		BigDecimal   b   =   new   BigDecimal(num);  
		return b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static double convertLastDouble_2(double num){
		BigDecimal   b   =   new   BigDecimal(num);  
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}


	public static String doubleFormatToString(double num){
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		return decimalFormat.format(num);
	}
	
	/**
	 * To judge whether or not a phone number
	 * 判断是否是电话号码
	 * @param num
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){  
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher m = p.matcher(mobiles);  
		return m.matches();  
	}
	
	
	
	/**
	 * convert bigDecimal To String 
	 * e.g 
	 * 1.00 -> 1
	 * 1.50 -> 1.5
	 * 1.99 -> 1.99
	 * @param bd
	 * @return
	 */
	public static String bigDecimalToString(BigDecimal bd) {
		if (bd != null) {
			bd = bd.setScale(2, BigDecimal.ROUND_DOWN);
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(0);
			df.setGroupingUsed(false);
			return df.format(bd);
		} else {
			return "0";
		}
		
	}
	

  
    /**
	 * java.sql.Timestamp convert to Date
	 * @param conStr
	 * @param conExpress "yyyy-MM-dd" etc.
	 * @return timestamp
	 */
	public static Date convertTimestampToDate(Timestamp ts) {
		Date date = new Date();
		try {
			date = ts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
    
	/**
	 * String convert to java.sql.Timestamp
	 * @param conStr
	 * @param conExpress "yyyy-MM-dd" etc.
	 * @return timestamp
	 */
	public static Timestamp convertStringToTimestamp(String conStr, String conExpress) {
		java.sql.Timestamp timestamp = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(conExpress);
			java.util.Date parsedDate;
			parsedDate = dateFormat.parse(conStr);
			timestamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}
	
	/**
	 * java.sql.Timestamp convert to String
	 * @param timestamp
	 * @param conExpress
	 * @return
	 */
	public static String convertTimestampToString(Timestamp timestamp, String conExpress) {
		return new SimpleDateFormat(conExpress).format(timestamp);
	}
	
	/**
	 * removing the json on both sides of "[" and "]".
	 * 去除json后两边的中括号“【”“】”
	 * @param str
	 * @return
	 */
	public static String delSign(String str) {
		str=StringUtil.replace(str, "[", "");
		str=StringUtil.replace(str, "]", "");
		return str;
	}

	// Empty checks
	// -----------------------------------------------------------------------

	/**
	 * Judgment is empty
	 * 判断为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * Judgment it is or not empty
	 * 非空判断
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !StringUtil.isEmpty(str);
	}

	/**
	 * Judgment it is or not a 'space' character
	 * 判断是否是空格
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Judgment it isn't a 'space' character.
	 * 非空格判断
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return !StringUtil.isBlank(str);
	}

	// Trim
	// -----------------------------------------------------------------------

	public static String clean(String str) {
		return str == null ? EMPTY : str.trim();
	}

	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	public static String trimToNull(String str) {
		String ts = trim(str);
		return isEmpty(ts) ? null : ts;
	}

	public static String trimToEmpty(String str) {
		return str == null ? EMPTY : str.trim();
	}

	// Stripping
	// -----------------------------------------------------------------------

	/**
	 * Split
	 * 分割
	 * 
	 * @param str
	 * @return
	 */
	public static String strip(String str) {
		return strip(str, null);
	}

	public static String stripToNull(String str) {
		if (str == null) {
			return null;
		}
		str = strip(str, null);
		return str.length() == 0 ? null : str;
	}

	public static String stripToEmpty(String str) {
		return str == null ? EMPTY : strip(str, null);
	}

	public static String strip(String str, String stripChars) {
		if (isEmpty(str)) {
			return str;
		}
		str = stripStart(str, stripChars);
		return stripEnd(str, stripChars);
	}

	public static String stripStart(String str, String stripChars) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		int start = 0;
		if (stripChars == null) {
			while ((start != strLen)
					&& Character.isWhitespace(str.charAt(start))) {
				start++;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((start != strLen)
					&& (stripChars.indexOf(str.charAt(start)) != -1)) {
				start++;
			}
		}
		return str.substring(start);
	}

	public static String stripEnd(String str, String stripChars) {
		int end;
		if (str == null || (end = str.length()) == 0) {
			return str;
		}
		if (stripChars == null) {
			while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
				end--;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((end != 0)
					&& (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
				end--;
			}
		}
		return str.substring(0, end);
	}

	// StripAll
	// -----------------------------------------------------------------------

	public static String[] stripAll(String[] strs) {
		return stripAll(strs, null);
	}

	public static String[] stripAll(String[] strs, String stripChars) {
		int strsLen;
		if (strs == null || (strsLen = strs.length) == 0) {
			return strs;
		}
		String[] newArr = new String[strsLen];
		for (int i = 0; i < strsLen; i++) {
		}
		return newArr;
	}

	// Equals
	// -----------------------------------------------------------------------

	public static boolean equals(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
	}

	// IndexOf
	// -----------------------------------------------------------------------

	public static int indexOf(String str, char searchChar) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.indexOf(searchChar);
	}

	public static int indexOf(String str, char searchChar, int startPos) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.indexOf(searchChar, startPos);
	}

	public static int indexOf(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return -1;
		}
		return str.indexOf(searchStr);
	}

	public static int ordinalIndexOf(String str, String searchStr, int ordinal) {
		if (str == null || searchStr == null || ordinal <= 0) {
			return INDEX_NOT_FOUND;
		}
		if (searchStr.length() == 0) {
			return 0;
		}
		int found = 0;
		int index = INDEX_NOT_FOUND;
		do {
			index = str.indexOf(searchStr, index + 1);
			if (index < 0) {
				return index;
			}
			found++;
		} while (found < ordinal);
		return index;
	}

	public static int indexOf(String str, String searchStr, int startPos) {
		if (str == null || searchStr == null) {
			return -1;
		}
		// JDK1.2/JDK1.3 have a bug, when startPos > str.length for "", hence
		if (searchStr.length() == 0 && startPos >= str.length()) {
			return str.length();
		}
		return str.indexOf(searchStr, startPos);
	}

	// LastIndexOf
	// -----------------------------------------------------------------------

	public static int lastIndexOf(String str, char searchChar) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.lastIndexOf(searchChar);
	}

	public static int lastIndexOf(String str, char searchChar, int startPos) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.lastIndexOf(searchChar, startPos);
	}

	public static int lastIndexOf(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return -1;
		}
		return str.lastIndexOf(searchStr);
	}

	public static int lastIndexOf(String str, String searchStr, int startPos) {
		if (str == null || searchStr == null) {
			return -1;
		}
		return str.lastIndexOf(searchStr, startPos);
	}

	// Contains
	// -----------------------------------------------------------------------

	public static boolean contains(String str, char searchChar) {
		if (isEmpty(str)) {
			return false;
		}
		return str.indexOf(searchChar) >= 0;
	}

	public static boolean contains(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return str.indexOf(searchStr) >= 0;
	}

	public static boolean containsIgnoreCase(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return contains(str.toUpperCase(), searchStr.toUpperCase());
	}

	


	// ContainsAny
	// -----------------------------------------------------------------------

	public static boolean containsAny(String str, char[] searchChars) {
		if (str == null || str.length() == 0 || searchChars == null
				|| searchChars.length == 0) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < searchChars.length; j++) {
				if (searchChars[j] == ch) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean containsAny(String str, String searchChars) {
		if (searchChars == null) {
			return false;
		}
		return containsAny(str, searchChars.toCharArray());
	}



	// ContainsNone
	// -----------------------------------------------------------------------

	public static boolean containsNone(String str, char[] invalidChars) {
		if (str == null || invalidChars == null) {
			return true;
		}
		int strSize = str.length();
		int validSize = invalidChars.length;
		for (int i = 0; i < strSize; i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < validSize; j++) {
				if (invalidChars[j] == ch) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean containsNone(String str, String invalidChars) {
		if (str == null || invalidChars == null) {
			return true;
		}
		return containsNone(str, invalidChars.toCharArray());
	}

	// IndexOfAny strings
	// -----------------------------------------------------------------------

	public static int indexOfAny(String str, String[] searchStrs) {
		if ((str == null) || (searchStrs == null)) {
			return -1;
		}
		int sz = searchStrs.length;
		// String's can't have a MAX_VALUEth index.
		int ret = Integer.MAX_VALUE;
		int tmp = 0;
		for (int i = 0; i < sz; i++) {
			String search = searchStrs[i];
			if (search == null) {
				continue;
			}
			tmp = str.indexOf(search);
			if (tmp == -1) {
				continue;
			}
			if (tmp < ret) {
				ret = tmp;
			}
		}
		return (ret == Integer.MAX_VALUE) ? -1 : ret;
	}

	public static int lastIndexOfAny(String str, String[] searchStrs) {
		if ((str == null) || (searchStrs == null)) {
			return -1;
		}
		int sz = searchStrs.length;
		int ret = -1;
		int tmp = 0;
		for (int i = 0; i < sz; i++) {
			String search = searchStrs[i];
			if (search == null) {
				continue;
			}
			tmp = str.lastIndexOf(search);
			if (tmp > ret) {
				ret = tmp;
			}
		}
		return ret;
	}

	// Substring
	// -----------------------------------------------------------------------

	public static String substring(String str, int start) {
		if (str == null) {
			return null;
		}
		// handle negatives, which means last n characters
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}
		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return EMPTY;
		}
		return str.substring(start);
	}

	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}
		// handle negatives
		if (end < 0) {
			end = str.length() + end; // remember end is negative
		}
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}
		// check length next
		if (end > str.length()) {
			end = str.length();
		}
		// if start is greater than end, return ""
		if (start > end) {
			return EMPTY;
		}
		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}
		return str.substring(start, end);
	}

	// Left/Right/Mid
	// -----------------------------------------------------------------------

	public static String left(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(0, len);
	}

	public static String right(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(str.length() - len);
	}

	public static String mid(String str, int pos, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0 || pos > str.length()) {
			return EMPTY;
		}
		if (pos < 0) {
			pos = 0;
		}
		if (str.length() <= (pos + len)) {
			return str.substring(pos);
		}
		return str.substring(pos, pos + len);
	}

	// SubStringAfter/SubStringBefore
	// -----------------------------------------------------------------------

	public static String substringBefore(String str, String separator) {
		if (isEmpty(str) || separator == null) {
			return str;
		}
		if (separator.length() == 0) {
			return EMPTY;
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String substringAfter(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (separator == null) {
			return EMPTY;
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}

	public static String substringBeforeLast(String str, String separator) {
		if (isEmpty(str) || isEmpty(separator)) {
			return str;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String substringAfterLast(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (isEmpty(separator)) {
			return EMPTY;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1 || pos == (str.length() - separator.length())) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}

	// Substring between
	// -----------------------------------------------------------------------

	public static String substringBetween(String str, String tag) {
		return substringBetween(str, tag, tag);
	}

	public static String substringBetween(String str, String open, String close) {
		if (str == null || open == null || close == null) {
			return null;
		}
		int start = str.indexOf(open);
		if (start != -1) {
			int end = str.indexOf(close, start + open.length());
			if (end != -1) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

	

	// Nested extraction
	// -----------------------------------------------------------------------

	public static String getNestedString(String str, String tag) {
		return substringBetween(str, tag, tag);
	}

	public static String getNestedString(String str, String open, String close) {
		return substringBetween(str, open, close);
	}

	


	// Joining
	// -----------------------------------------------------------------------

	public static String concatenate(Object[] array) {
		return join(array, null);
	}

	public static String join(Object[] array) {
		return join(array, null);
	}

	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	public static String join(Object[] array, char separator, int startIndex,
			int endIndex) {
		if (array == null) {
			return null;
		}
		int bufSize = (endIndex - startIndex);
		if (bufSize <= 0) {
			return EMPTY;
		}
		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex]
				.toString().length()) + 1);
		StringBuffer buf = new StringBuffer(bufSize);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	public static String join(Object[] array, String separator, int startIndex,
			int endIndex) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = EMPTY;
		}
		// endIndex - startIndex > 0: Len = NofStrings *(len(firstString) +
		// len(separator))
		// (Assuming that all Strings are roughly equally long)
		int bufSize = (endIndex - startIndex);
		if (bufSize <= 0) {
			return EMPTY;
		}
		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex]
				.toString().length()) + separator.length());
		StringBuffer buf = new StringBuffer(bufSize);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}





	// Remove
	// -----------------------------------------------------------------------

	public static String removeStart(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.startsWith(remove)) {
			return str.substring(remove.length());
		}
		return str;
	}

	public static String removeStartIgnoreCase(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (startsWithIgnoreCase(str, remove)) {
			return str.substring(remove.length());
		}
		return str;
	}

	public static String removeEnd(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.endsWith(remove)) {
			return str.substring(0, str.length() - remove.length());
		}
		return str;
	}

	public static String removeEndIgnoreCase(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (endsWithIgnoreCase(str, remove)) {
			return str.substring(0, str.length() - remove.length());
		}
		return str;
	}

	public static String remove(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		return replace(str, remove, EMPTY, -1);
	}

	public static String remove(String str, char remove) {
		if (isEmpty(str) || str.indexOf(remove) == -1) {
			return str;
		}
		char[] chars = str.toCharArray();
		int pos = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != remove) {
				chars[pos++] = chars[i];
			}
		}
		return new String(chars, 0, pos);
	}

	// Replacing
	// -----------------------------------------------------------------------

	public static String replaceOnce(String text, String searchString,
			String replacement) {
		return replace(text, searchString, replacement, 1);
	}

	public static String replace(String text, String searchString,
			String replacement) {
		return replace(text, searchString, replacement, -1);
	}

	public static String replace(String text, String searchString,
			String replacement, int max) {
		if (isEmpty(text) || isEmpty(searchString) || replacement == null
				|| max == 0) {
			return text;
		}
		int start = 0;
		int end = text.indexOf(searchString, start);
		if (end == -1) {
			return text;
		}
		int replLength = searchString.length();
		int increase = replacement.length() - replLength;
		increase = (increase < 0 ? 0 : increase);
		increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
		StringBuffer buf = new StringBuffer(text.length() + increase);
		while (end != -1) {
			buf.append(text.substring(start, end)).append(replacement);
			start = end + replLength;
			if (--max == 0) {
				break;
			}
			end = text.indexOf(searchString, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	public static String replaceEach(String text, String[] searchList,
			String[] replacementList) {
		return replaceEach(text, searchList, replacementList, false, 0);
	}

	public static String replaceEachRepeatedly(String text,
			String[] searchList, String[] replacementList) {
		// timeToLive should be 0 if not used or nothing to replace, else it's
		// the length of the replace array
		int timeToLive = searchList == null ? 0 : searchList.length;
		return replaceEach(text, searchList, replacementList, true, timeToLive);
	}

	private static String replaceEach(String text, String[] searchList,
			String[] replacementList, boolean repeat, int timeToLive) {
		// mchyzer Performance note: This creates very few new objects (one
		// major goal)
		// let me know if there are performance requests, we can create a
		// harness to measure
		if (text == null || text.length() == 0 || searchList == null
				|| searchList.length == 0 || replacementList == null
				|| replacementList.length == 0) {
			return text;
		}
		// if recursing, this shouldnt be less than 0
		if (timeToLive < 0) {
			throw new IllegalStateException("TimeToLive of " + timeToLive
					+ " is less than 0: " + text);
		}
		int searchLength = searchList.length;
		int replacementLength = replacementList.length;
		// make sure lengths are ok, these need to be equal
		if (searchLength != replacementLength) {
			throw new IllegalArgumentException(
					"Search and Replace array lengths don't match: "
							+ searchLength + " vs " + replacementLength);
		}
		// keep track of which still have matches
		boolean[] noMoreMatchesForReplIndex = new boolean[searchLength];
		// index on index that the match was found
		int textIndex = -1;
		int replaceIndex = -1;
		int tempIndex = -1;
		// index of replace array that will replace the search string found
		// NOTE: logic duplicated below START
		for (int i = 0; i < searchLength; i++) {
			if (noMoreMatchesForReplIndex[i] || searchList[i] == null
					|| searchList[i].length() == 0
					|| replacementList[i] == null) {
				continue;
			}
			tempIndex = text.indexOf(searchList[i]);
			// see if we need to keep searching for this
			if (tempIndex == -1) {
				noMoreMatchesForReplIndex[i] = true;
			} else {
				if (textIndex == -1 || tempIndex < textIndex) {
					textIndex = tempIndex;
					replaceIndex = i;
				}
			}
		}
		// NOTE: logic mostly below END
		// no search strings found, we are done
		if (textIndex == -1) {
			return text;
		}
		int start = 0;
		// get a good guess on the size of the result buffer so it doesnt have
		// to double if it goes over a bit
		int increase = 0;
		// count the replacement text elements that are larger than their
		// corresponding text being replaced
		for (int i = 0; i < searchList.length; i++) {
			int greater = replacementList[i].length() - searchList[i].length();
			if (greater > 0) {
				increase += 3 * greater; // assume 3 matches
			}
		}
		// have upper-bound at 20% increase, then let Java take over
		increase = Math.min(increase, text.length() / 5);
		StringBuffer buf = new StringBuffer(text.length() + increase);
		while (textIndex != -1) {
			for (int i = start; i < textIndex; i++) {
				buf.append(text.charAt(i));
			}
			buf.append(replacementList[replaceIndex]);
			start = textIndex + searchList[replaceIndex].length();
			textIndex = -1;
			replaceIndex = -1;
			tempIndex = -1;
			// find the next earliest match
			// NOTE: logic mostly duplicated above START
			for (int i = 0; i < searchLength; i++) {
				if (noMoreMatchesForReplIndex[i] || searchList[i] == null
						|| searchList[i].length() == 0
						|| replacementList[i] == null) {
					continue;
				}
				tempIndex = text.indexOf(searchList[i], start);
				// see if we need to keep searching for this
				if (tempIndex == -1) {
					noMoreMatchesForReplIndex[i] = true;
				} else {
					if (textIndex == -1 || tempIndex < textIndex) {
						textIndex = tempIndex;
						replaceIndex = i;
					}
				}
			}
			// NOTE: logic duplicated above END
		}
		int textLength = text.length();
		for (int i = start; i < textLength; i++) {
			buf.append(text.charAt(i));
		}
		String result = buf.toString();
		if (!repeat) {
			return result;
		}
		return replaceEach(result, searchList, replacementList, repeat,
				timeToLive - 1);
	}

	// Replace, character based
	// -----------------------------------------------------------------------

	public static String replaceChars(String str, char searchChar,
			char replaceChar) {
		if (str == null) {
			return null;
		}
		return str.replace(searchChar, replaceChar);
	}

	public static String replaceChars(String str, String searchChars,
			String replaceChars) {
		if (isEmpty(str) || isEmpty(searchChars)) {
			return str;
		}
		if (replaceChars == null) {
			replaceChars = EMPTY;
		}
		boolean modified = false;
		int replaceCharsLength = replaceChars.length();
		int strLength = str.length();
		StringBuffer buf = new StringBuffer(strLength);
		for (int i = 0; i < strLength; i++) {
			char ch = str.charAt(i);
			int index = searchChars.indexOf(ch);
			if (index >= 0) {
				modified = true;
				if (index < replaceCharsLength) {
					buf.append(replaceChars.charAt(index));
				}
			} else {
				buf.append(ch);
			}
		}
		if (modified) {
			return buf.toString();
		}
		return str;
	}

	// Overlay
	// -----------------------------------------------------------------------

	public static String overlayString(String text, String overlay, int start,
			int end) {
		return new StringBuffer(start + overlay.length() + text.length() - end
				+ 1).append(text.substring(0, start)).append(overlay)
				.append(text.substring(end)).toString();
	}

	public static String overlay(String str, String overlay, int start, int end) {
		if (str == null) {
			return null;
		}
		if (overlay == null) {
			overlay = EMPTY;
		}
		int len = str.length();
		if (start < 0) {
			start = 0;
		}
		if (start > len) {
			start = len;
		}
		if (end < 0) {
			end = 0;
		}
		if (end > len) {
			end = len;
		}
		if (start > end) {
			int temp = start;
			start = end;
			end = temp;
		}
		return new StringBuffer(len + start - end + overlay.length() + 1)
				.append(str.substring(0, start)).append(overlay)
				.append(str.substring(end)).toString();
	}

	

	public static String chomp(String str, String separator) {
		if (isEmpty(str) || separator == null) {
			return str;
		}
		if (str.endsWith(separator)) {
			return str.substring(0, str.length() - separator.length());
		}
		return str;
	}

	public static String chompLast(String str) {
		return chompLast(str, "\n");
	}

	public static String chompLast(String str, String sep) {
		if (str.length() == 0) {
			return str;
		}
		String sub = str.substring(str.length() - sep.length());
		if (sep.equals(sub)) {
			return str.substring(0, str.length() - sep.length());
		}
		return str;
	}

	public static String getChomp(String str, String sep) {
		int idx = str.lastIndexOf(sep);
		if (idx == str.length() - sep.length()) {
			return sep;
		} else if (idx != -1) {
			return str.substring(idx);
		} else {
			return EMPTY;
		}
	}

	public static String prechomp(String str, String sep) {
		int idx = str.indexOf(sep);
		if (idx == -1) {
			return str;
		}
		return str.substring(idx + sep.length());
	}

	public static String getPrechomp(String str, String sep) {
		int idx = str.indexOf(sep);
		if (idx == -1) {
			return EMPTY;
		}
		return str.substring(0, idx + sep.length());
	}



	// Padding
	// -----------------------------------------------------------------------

	public static String repeat(String str, int repeat) {
		// Performance tuned for 2.0 (JDK1.4)
		if (str == null) {
			return null;
		}
		if (repeat <= 0) {
			return EMPTY;
		}
		int inputLength = str.length();
		if (repeat == 1 || inputLength == 0) {
			return str;
		}
		if (inputLength == 1 && repeat <= PAD_LIMIT) {
			return padding(repeat, str.charAt(0));
		}
		int outputLength = inputLength * repeat;
		switch (inputLength) {
		case 1:
			char ch = str.charAt(0);
			char[] output1 = new char[outputLength];
			for (int i = repeat - 1; i >= 0; i--) {
				output1[i] = ch;
			}
			return new String(output1);
		case 2:
			char ch0 = str.charAt(0);
			char ch1 = str.charAt(1);
			char[] output2 = new char[outputLength];
			for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
				output2[i] = ch0;
				output2[i + 1] = ch1;
			}
			return new String(output2);
		default:
			StringBuffer buf = new StringBuffer(outputLength);
			for (int i = 0; i < repeat; i++) {
				buf.append(str);
			}
			return buf.toString();
		}
	}

	private static String padding(int repeat, char padChar)
			throws IndexOutOfBoundsException {
		if (repeat < 0) {
			throw new IndexOutOfBoundsException(
					"Cannot pad a negative amount: " + repeat);
		}
		final char[] buf = new char[repeat];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = padChar;
		}
		return new String(buf);
	}

	public static String rightPad(String str, int size) {
		return rightPad(str, size, ' ');
	}

	public static String rightPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return rightPad(str, size, String.valueOf(padChar));
		}
		return str.concat(padding(pads, padChar));
	}

	public static String rightPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return rightPad(str, size, padStr.charAt(0));
		}
		if (pads == padLen) {
			return str.concat(padStr);
		} else if (pads < padLen) {
			return str.concat(padStr.substring(0, pads));
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return str.concat(new String(padding));
		}
	}

	public static String leftPad(String str, int size) {
		return leftPad(str, size, ' ');
	}

	public static String leftPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return leftPad(str, size, String.valueOf(padChar));
		}
		return padding(pads, padChar).concat(str);
	}

	public static String leftPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return leftPad(str, size, padStr.charAt(0));
		}
		if (pads == padLen) {
			return padStr.concat(str);
		} else if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return new String(padding).concat(str);
		}
	}

	public static int length(String str) {
		return str == null ? 0 : str.length();
	}

	// Centering
	// -----------------------------------------------------------------------

	public static String center(String str, int size) {
		return center(str, size, ' ');
	}

	public static String center(String str, int size, char padChar) {
		if (str == null || size <= 0) {
			return str;
		}
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		str = leftPad(str, strLen + pads / 2, padChar);
		str = rightPad(str, size, padChar);
		return str;
	}

	public static String center(String str, int size, String padStr) {
		if (str == null || size <= 0) {
			return str;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		str = leftPad(str, strLen + pads / 2, padStr);
		str = rightPad(str, size, padStr);
		return str;
	}

	// Case conversion
	// -----------------------------------------------------------------------

	public static String upperCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toUpperCase();
	}

	public static String lowerCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toLowerCase();
	}

	public static String capitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuffer(strLen)
				.append(Character.toTitleCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	public static String capitalise(String str) {
		return capitalize(str);
	}

	public static String uncapitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuffer(strLen)
				.append(Character.toLowerCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	public static String uncapitalise(String str) {
		return uncapitalize(str);
	}

	public static String swapCase(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		StringBuffer buffer = new StringBuffer(strLen);
		char ch = 0;
		for (int i = 0; i < strLen; i++) {
			ch = str.charAt(i);
			if (Character.isUpperCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isTitleCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isLowerCase(ch)) {
				ch = Character.toUpperCase(ch);
			}
			buffer.append(ch);
		}
		return buffer.toString();
	}

	

	// Count matches
	// -----------------------------------------------------------------------

	public static int countMatches(String str, String sub) {
		if (isEmpty(str) || isEmpty(sub)) {
			return 0;
		}
		int count = 0;
		int idx = 0;
		while ((idx = str.indexOf(sub, idx)) != -1) {
			count++;
			idx += sub.length();
		}
		return count;
	}

	// Character Tests
	// -----------------------------------------------------------------------

	public static boolean isAlpha(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLetter(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAlphaSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isLetter(str.charAt(i)) == false)
					&& (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAlphanumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLetterOrDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAlphanumericSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isLetterOrDigit(str.charAt(i)) == false)
					&& (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}


	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumericSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isDigit(str.charAt(i)) == false)
					&& (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	public static boolean isWhitespace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	// Defaults
	// -----------------------------------------------------------------------

	public static String defaultString(String str) {
		return str == null ? EMPTY : str;
	}

	public static String defaultString(String str, String defaultStr) {
		return str == null ? defaultStr : str;
	}

	public static String defaultIfEmpty(String str, String defaultStr) {
		return StringUtil.isEmpty(str) ? defaultStr : str;
	}

	// Reversing
	// -----------------------------------------------------------------------

	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		return new StringBuffer(str).reverse().toString();
	}

	
	// Abbreviating
	// -----------------------------------------------------------------------

	public static String abbreviate(String str, int maxWidth) {
		return abbreviate(str, 0, maxWidth);
	}

	public static String abbreviate(String str, int offset, int maxWidth) {
		if (str == null) {
			return null;
		}
		if (maxWidth < 4) {
			throw new IllegalArgumentException(
					"Minimum abbreviation width is 4");
		}
		if (str.length() <= maxWidth) {
			return str;
		}
		if (offset > str.length()) {
			offset = str.length();
		}
		if ((str.length() - offset) < (maxWidth - 3)) {
			offset = str.length() - (maxWidth - 3);
		}
		if (offset <= 4) {
			return str.substring(0, maxWidth - 3) + "...";
		}
		if (maxWidth < 7) {
			throw new IllegalArgumentException(
					"Minimum abbreviation width with offset is 7");
		}
		if ((offset + (maxWidth - 3)) < str.length()) {
			return "..." + abbreviate(str.substring(offset), maxWidth - 3);
		}
		return "..." + str.substring(str.length() - (maxWidth - 3));
	}

	// Difference
	// -----------------------------------------------------------------------

	public static String difference(String str1, String str2) {
		if (str1 == null) {
			return str2;
		}
		if (str2 == null) {
			return str1;
		}
		int at = indexOfDifference(str1, str2);
		if (at == -1) {
			return EMPTY;
		}
		return str2.substring(at);
	}

	public static int indexOfDifference(String str1, String str2) {
		if (str1 == str2) {
			return -1;
		}
		if (str1 == null || str2 == null) {
			return 0;
		}
		int i;
		for (i = 0; i < str1.length() && i < str2.length(); ++i) {
			if (str1.charAt(i) != str2.charAt(i)) {
				break;
			}
		}
		if (i < str2.length() || i < str1.length()) {
			return i;
		}
		return -1;
	}

	public static int indexOfDifference(String[] strs) {
		if (strs == null || strs.length <= 1) {
			return -1;
		}
		boolean anyStringNull = false;
		boolean allStringsNull = true;
		int arrayLen = strs.length;
		int shortestStrLen = Integer.MAX_VALUE;
		int longestStrLen = 0;
		// find the min and max string lengths; this avoids checking to make
		// sure we are not exceeding the length of the string each time through
		// the bottom loop.
		for (int i = 0; i < arrayLen; i++) {
			if (strs[i] == null) {
				anyStringNull = true;
				shortestStrLen = 0;
			} else {
				allStringsNull = false;
				shortestStrLen = Math.min(strs[i].length(), shortestStrLen);
				longestStrLen = Math.max(strs[i].length(), longestStrLen);
			}
		}
		// handle lists containing all nulls or all empty strings
		if (allStringsNull || (longestStrLen == 0 && !anyStringNull)) {
			return -1;
		}
		// handle lists containing some nulls or some empty strings
		if (shortestStrLen == 0) {
			return 0;
		}
		// find the position with the first difference across all strings
		int firstDiff = -1;
		for (int stringPos = 0; stringPos < shortestStrLen; stringPos++) {
			char comparisonChar = strs[0].charAt(stringPos);
			for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
				if (strs[arrayPos].charAt(stringPos) != comparisonChar) {
					firstDiff = stringPos;
					break;
				}
			}
			if (firstDiff != -1) {
				break;
			}
		}
		if (firstDiff == -1 && shortestStrLen != longestStrLen) {
			// we compared all of the characters up to the length of the
			// shortest string and didn't find a match, but the string lengths
			// vary, so return the length of the shortest string.
			return shortestStrLen;
		}
		return firstDiff;
	}

	public static String getCommonPrefix(String[] strs) {
		if (strs == null || strs.length == 0) {
			return EMPTY;
		}
		int smallestIndexOfDiff = indexOfDifference(strs);
		if (smallestIndexOfDiff == -1) {
			// all strings were identical
			if (strs[0] == null) {
				return EMPTY;
			}
			return strs[0];
		} else if (smallestIndexOfDiff == 0) {
			// there were no common initial characters
			return EMPTY;
		} else {
			// we found a common initial character sequence
			return strs[0].substring(0, smallestIndexOfDiff);
		}
	}

	// Misc
	// -----------------------------------------------------------------------

	public static int getLevenshteinDistance(String s, String t) {
		if (s == null || t == null) {
			throw new IllegalArgumentException("Strings must not be null");
		}

		int n = s.length(); // length of s
		int m = t.length(); // length of t
		if (n == 0) {
			return m;
		} else if (m == 0) {
			return n;
		}
		if (n > m) {
			// swap the input strings to consume less memory
			String tmp = s;
			s = t;
			t = tmp;
			n = m;
			m = t.length();
		}
		int p[] = new int[n + 1]; // 'previous' cost array, horizontally
		int d[] = new int[n + 1]; // cost array, horizontally
		int _d[]; // placeholder to assist in swapping p and d
		// indexes into strings s and t
		int i; // iterates through s
		int j; // iterates through t
		char t_j; // jth character of t
		int cost; // cost
		for (i = 0; i <= n; i++) {
			p[i] = i;
		}
		for (j = 1; j <= m; j++) {
			t_j = t.charAt(j - 1);
			d[0] = j;
			for (i = 1; i <= n; i++) {
				cost = s.charAt(i - 1) == t_j ? 0 : 1;
				// minimum of cell to the left+1, to the top+1, diagonally left
				// and up +cost
				d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1]
						+ cost);
			}
			// copy current distance counts to 'previous row' distance counts
			_d = p;
			p = d;
			d = _d;
		}
		// our last action in the above loop was to switch d and p, so p now
		// actually has the most recent cost counts
		return p[n];
	}

	// startsWith
	// -----------------------------------------------------------------------

	public static boolean startsWith(String str, String prefix) {
		return startsWith(str, prefix, false);
	}

	public static boolean startsWithIgnoreCase(String str, String prefix) {
		return startsWith(str, prefix, true);
	}

	private static boolean startsWith(String str, String prefix,
			boolean ignoreCase) {
		if (str == null || prefix == null) {
			return (str == null && prefix == null);
		}
		if (prefix.length() > str.length()) {
			return false;
		}
		return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
	}

	// endsWith
	// -----------------------------------------------------------------------

	public static boolean endsWith(String str, String suffix) {
		return endsWith(str, suffix, false);
	}

	public static boolean endsWithIgnoreCase(String str, String suffix) {
		return endsWith(str, suffix, true);
	}

	private static boolean endsWith(String str, String suffix,
			boolean ignoreCase) {
		if (str == null || suffix == null) {
			return (str == null && suffix == null);
		}
		if (suffix.length() > str.length()) {
			return false;
		}
		int strOffset = str.length() - suffix.length();
		return str.regionMatches(ignoreCase, strOffset, suffix, 0,
				suffix.length());
	}

	/**
	 * md5 encryption
	 * MD5加密
	 * 
	 * @param plainText
	 * @return
	 */
	public static String md5(String plainText) {
		String str = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
			// System.out.println("result: " + buf.toString());// 32位的加密
			// System.out.println("result: " + buf.toString().substring(8,
			// 24));// 16位的加密
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return str;
	}
	
	/**Creat a additional code randomly include four character   随机生成四位的附加码**/
	public static String getRandom(){  
	   int i1 = (int)(java.lang.Math.random()*10);  
	   int i2 = (int)(java.lang.Math.random()*10);  
	   int i3 = (int)(java.lang.Math.random()*10);  
	   int i4 = (int)(java.lang.Math.random()*10);  
	   return String.valueOf(i1)+String.valueOf(i2)+String.valueOf(i3)+String.valueOf(i4);  
	 }  
	
	/**
	 * create different random number
	 * 生成不相同的随机数
	 * @param num
	 * @param size
	 * @return
	 */
	public static int[] getDifferentRandom(int num,int size){
//		  List<Integer> list=new ArrayList<Integer>();
		  int[] tmp=new int[num];
		  Random rd = new Random();        
	        HashSet set = new HashSet();        
	        while(true){
	            int i= rd.nextInt(size);
	            set.add(new Integer(i));
	            if(set.size()==num){
	                break;
	            }
	        }
	     
	        Iterator iter = set.iterator();
	        int i=0;
	        while (iter.hasNext()) {	        	
	            Integer item = (Integer) iter.next();	            
	            //System.out.println(item);
	            //list.add(item);
	            tmp[i]=item;
	            ++i;
	            if(num==i){
	        		break;
	        	}
	          
	        }
//		 List<Integer> list=new ArrayList<Integer>();
//		 Random rd = new Random();        
//	        HashSet set = new HashSet();        
//	        while(true){
//	            int i= rd.nextInt(size);
//	            set.add(new Integer(i));
//	            if(set.size()==num){
//	                break;
//	            }
//	        }
//	     
//	        Iterator iter = set.iterator();
//	        while (iter.hasNext()) {
//	            Integer item = (Integer) iter.next();
//	            System.out.println(item);
//	            list.add(item);
//	        }
		
	        return tmp;
	}

    /**
     * judeg is or not a Date type (yyyy-mm-dd)
     * 判断是否日期(yyyy-mm-dd)格式
     * @param str
     * @return
     */
    public static boolean isDateFormat(final String str) {
        boolean result = false;
        if (isNotEmpty(str)) {
            Pattern p = Pattern.compile("[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}");
            result = p.matcher(str).find();
        }
        return result;
    }
    
    /**
     * @Title: transformDateToAMPMDate
     * @Description: Date类型转换成AMPM格式的Date数据
     * @param: @param date
     * @param: @return
     * @return Date  
     */
    public static Date transformDateToAMPMDate(Date date){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa",Locale.CANADA);
		Date newDate = null;
		try {
			newDate = sdf.parse(date.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
    }
    
    /**
     * @Title: transformDateToAMPMString
     * @Description: Date类型转换成AMPM格式的String数据
     * @param: @param date
     * @param: @return
     * @return String  
     */
    public static String transformDateToAMPMString(Date date){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa",Locale.CANADA);
		String strDate = sdf.format(date);
    	return strDate;
    }
    
    public static String getUniqueCode(){
    	//生成唯一码
    			Date date = new Date(System.currentTimeMillis());
    			SimpleDateFormat fomat2 = new SimpleDateFormat("yyyyMMdd");
    			String mydate = fomat2.format(date);
    			int num=(int) ((Math.random()*9000000)+1000000);
    			return mydate+""+num;
    }
    
    /**
     * @Title: getRandomString
     * @Description: 生成指定长度的随机字符串
     * @param: @param length
     * @param: @return
     * @return String  
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度  
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
        Random random = new Random();     
        StringBuffer sb = new StringBuffer();     
        for (int i = 0; i < length; i++) {     
            int number = random.nextInt(base.length());     
            sb.append(base.charAt(number));     
        }     
        return sb.toString();     
     }
    
    /**  
     * 生成32位编码  
     * @return string  
     */    
    public static String getUUID(){    
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");    
        return uuid;    
    }    
    
    
}
