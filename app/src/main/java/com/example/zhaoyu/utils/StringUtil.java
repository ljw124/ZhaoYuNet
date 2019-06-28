package com.example.zhaoyu.utils;


import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Ljw on 2019/5/23.
 */
public class StringUtil {
    public static final String EMPTY = "";
    private static final int PAD_LIMIT = 8192;

    public StringUtil() {
    }

    public static final boolean isEmpty(String source) {
        return source == null || source.length() == 0 || "".equals(source);
    }

    public static final boolean isEmpty(Object source) {
        return source == null || isEmpty(source.toString());
    }

    public static final boolean isNotEmpty(String source) {
        return !isEmpty(source);
    }

    public static final char[] byteArrayToCharArray(byte[] byteArray) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(byteArray.length);
        bb.put(byteArray);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

    public static final char[] byteArrayToCharArray(byte[] byteArray, int offset, int count) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(count);
        bb.put(byteArray, offset, count);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

    public static final String byteArrayToString(byte[] byteArray) {
        return new String(byteArray);
    }

    public static final String byteArrayToString(byte[] byteArray, int offset, int count) {
        return new String(byteArray, offset, count);
    }

    public static final String byteArray16LEToString(byte[] byteArray) {
        return new String(byteArray, Charset.forName("UTF-16LE"));
    }

    public static final String byteArray16LEToString(byte[] byteArray, int offset, int count) {
        return new String(byteArray, offset, count, Charset.forName("UTF-16LE"));
    }

    public static final String byteArrayToHexString(byte[] byteArray) {
        String retString = "";
        byte[] var5 = byteArray;
        int var4 = byteArray.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            byte b = var5[var3];
            retString = retString + String.format("%02x", b) + " ";
        }

        return retString;
    }

    public static final String byteArrayToHexString(byte[] byteArray, int offset, int count) {
        String retString = "";

        for(int i = 0; i < count; ++i) {
            retString = retString + String.format("%02x", byteArray[offset + i]) + " ";
        }

        return retString;
    }

    public static final byte[] hexStringToByteArray(String hex) {
        if (hex.length() % 2 != 0) {
            hex = "0" + hex;
        }

        int len = hex.length() / 2;
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();

        for(int i = 0; i < len; ++i) {
            int pos = i * 2;
            result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }

        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte)"0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static String addQuote(String str) {
        str = "'" + encodeSingleQuotedString(str) + "'";
        return str;
    }

    public static String addQuote(Date date) {
        String str = "";
        if (date != null) {
            Calendar cc = Calendar.getInstance();
            cc.setTime(date);
            int day = cc.get(5);
            int hour = cc.get(10);
            int minute = cc.get(12);
            if (day == 0 && hour == 0 && minute == 0) {
                str = DateUtil.dateToString(date);
            } else {
                str = DateUtil.datetimeToString(date);
            }
        }

        return str;
    }

    public static String encodeSingleQuotedString(String str) {
        StringBuilder sb = new StringBuilder(64);
        if (isNotEmpty(str)) {
            char[] strarray = str.toCharArray();

            for(int i = 0; i < strarray.length; ++i) {
                char c = strarray[i];
                if (c == '\'') {
                    sb.append("''");
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        } else {
            return str;
        }
    }

    public static String createUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String reflectionToString(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Field[] fields = obj.getClass().getFields();
        int count = 0;
        Field[] var7 = fields;
        int var6 = fields.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            Field item = var7[var5];
            if (count > 0) {
                sb.append(",");
            }

            try {
                sb.append(item.getName()).append("=").append(item.get(obj));
            } catch (IllegalArgumentException var9) {
            } catch (IllegalAccessException var10) {
            }

            ++count;
        }

        sb.append("]");
        return sb.toString();
    }

    public static String formatMySQLDateSQL(Date date) {
        String sql = "";
        if (date != null) {
            sql = "STR_TO_DATE('" + DateUtil.dateToString(date) + "','%Y-%m-%d')";
        }

        return sql;
    }

    public static String formatMySQLDateTimeSQL(Date date) {
        String sql = "";
        if (date != null) {
            sql = "STR_TO_DATE('" + DateUtil.datetimeToString(date) + "','%Y-%m-%d %H:%i:%s')";
        }

        return sql;
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    public static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }

    public static boolean contains(String str, char searchChar) {
        if (isEmpty(str)) {
            return false;
        } else {
            return str.indexOf(searchChar) >= 0;
        }
    }

    public static boolean contains(String str, String searchStr) {
        return str != null && searchStr != null ? str.contains(searchStr) : false;
    }

    public static boolean containsIgnoreCase(String str, String searchStr) {
        return str != null && searchStr != null ? contains(str.toUpperCase(), searchStr.toUpperCase()) : false;
    }

    public static String left(String str, int len) {
        if (str == null) {
            return null;
        } else if (len < 0) {
            return "";
        } else {
            return str.length() <= len ? str : str.substring(0, len);
        }
    }

    public static String right(String str, int len) {
        if (str == null) {
            return null;
        } else if (len < 0) {
            return "";
        } else {
            return str.length() <= len ? str : str.substring(str.length() - len);
        }
    }

    public static String mid(String str, int pos, int len) {
        if (str == null) {
            return null;
        } else if (len >= 0 && pos <= str.length()) {
            if (pos < 0) {
                pos = 0;
            }

            return str.length() <= pos + len ? str.substring(pos) : str.substring(pos, pos + len);
        } else {
            return "";
        }
    }

    private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
        if (repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        } else {
            char[] buf = new char[repeat];

            for(int i = 0; i < buf.length; ++i) {
                buf[i] = padChar;
            }

            return new String(buf);
        }
    }

    public static String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }

    public static String rightPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        } else {
            int pads = size - str.length();
            if (pads <= 0) {
                return str;
            } else {
                return pads > 8192 ? rightPad(str, size, String.valueOf(padChar)) : str.concat(padding(pads, padChar));
            }
        }
    }

    public static String rightPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        } else {
            if (isEmpty(padStr)) {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0) {
                return str;
            } else if (padLen == 1 && pads <= 8192) {
                return rightPad(str, size, padStr.charAt(0));
            } else if (pads == padLen) {
                return str.concat(padStr);
            } else if (pads < padLen) {
                return str.concat(padStr.substring(0, pads));
            } else {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for(int i = 0; i < pads; ++i) {
                    padding[i] = padChars[i % padLen];
                }

                return str.concat(new String(padding));
            }
        }
    }

    public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }

    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        } else {
            int pads = size - str.length();
            if (pads <= 0) {
                return str;
            } else {
                return pads > 8192 ? leftPad(str, size, String.valueOf(padChar)) : padding(pads, padChar).concat(str);
            }
        }
    }

    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        } else {
            if (isEmpty(padStr)) {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0) {
                return str;
            } else if (padLen == 1 && pads <= 8192) {
                return leftPad(str, size, padStr.charAt(0));
            } else if (pads == padLen) {
                return padStr.concat(str);
            } else if (pads < padLen) {
                return padStr.substring(0, pads).concat(str);
            } else {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for(int i = 0; i < pads; ++i) {
                    padding[i] = padChars[i % padLen];
                }

                return (new String(padding)).concat(str);
            }
        }
    }

    public static String formatDouble(double value) {
        String str = "0.0";
        if (value != 0.0D) {
            DecimalFormat nf = new DecimalFormat("#.###");
            nf.setParseIntegerOnly(false);
            nf.setDecimalSeparatorAlwaysShown(false);
            str = nf.format(value);
        }

        return str;
    }

    public static String formatDate(Date date) {
        return getDateSQL4Mysql(date);
    }

    public static String getDateSQL4Mysql(Date date) {
        String dateStr = DateUtil.dateToString(date);
        return "STR_TO_DATE('" + dateStr + "','%Y-%m-%d')";
    }
}
