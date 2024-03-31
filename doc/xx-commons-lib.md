# List of functions in jmelange.CommonUtil 

> for consider, this list should shorten over time and disappear completly
>
> functions should be put as luay extensions into the proper ext modules (maybe zdf?)

## TODO Luay-Hash Module

* `math.to_b32 ( long ) -> string`
* `math.to_b36 ( long ) -> string`
* `math.to_b62 ( long ) -> string`
* `math.to_b64 ( long ) -> string`

* `hash.sha512_long ( string ) -> long`
* `hash.sha512mac_long ( string , string ) -> long`



## ToDo List

* `abbreviate ( java.lang.String , int ) -> String`
* `abbreviate ( java.lang.String , int , int ) -> String`
* `abbreviate ( java.lang.String , int , int , java.lang.String ) -> String`
* `abbreviate ( java.lang.String , java.lang.String , int ) -> String`
* `abbreviate ( java.lang.String , java.lang.String , int , int ) -> String`
* `abbreviateMiddle ( java.lang.String , java.lang.String , int ) -> String`
* `addAndDeHump ( java.lang.String ) -> String`
* `appendIfMissing ( java.lang.String , java.lang.CharSequence , java.lang.CharSequence[] ) -> String`
* `appendIfMissingIgnoreCase ( java.lang.String , java.lang.CharSequence , java.lang.CharSequence[] ) -> String`
* `byteArray (  ) -> byte[]`
* `byteArray ( int ) -> byte[]`
* `bytesToLong ( byte[] ) -> long`
* `capitalise ( java.lang.String ) -> String`
* `capitaliseAllWords ( java.lang.String ) -> String`
* `capitalize ( java.lang.String ) -> String`
* `capitalize ( java.lang.String , char[] ) -> String`
* `capitalizeFirstLetter ( java.lang.String ) -> String`
* `capitalizeFully ( java.lang.String ) -> String`
* `capitalizeFully ( java.lang.String , char[] ) -> String`
* `center ( java.lang.String , int ) -> String`
* `center ( java.lang.String , int , char ) -> String`
* `center ( java.lang.String , int , java.lang.String ) -> String`
* `checkString ( java.lang.Object ) -> String`
* `checkString ( java.lang.String ) -> String`
* `checkStringDefaultIfBlank ( java.lang.Object , java.lang.String ) -> String`
* `checkStringDefaultIfBlank ( java.lang.String , java.lang.String ) -> String`
* `checkStringDefaultIfNull ( java.lang.Object , java.lang.String ) -> String`
* `checkStringDefaultIfNull ( java.lang.String , java.lang.String ) -> String`
* `checkStringDefaultIfNullOrBlank ( java.lang.Object , java.lang.String , java.lang.String ) -> String`
* `checkStringDefaultIfNullOrBlank ( java.lang.String , java.lang.String , java.lang.String ) -> String`
* `chomp ( java.lang.String ) -> String`
* `chomp ( java.lang.String , java.lang.String ) -> String`
* `chompLast ( java.lang.String ) -> String`
* `chompLast ( java.lang.String , java.lang.String ) -> String`
* `chop ( java.lang.String ) -> String`
* `chopNewline ( java.lang.String ) -> String`
* `clean ( java.lang.String ) -> String`
* `cleanString ( java.lang.String ) -> String`
* `compare ( boolean , boolean ) -> int`
* `compare ( byte , byte ) -> int`
* `compare ( int , int ) -> int`
* `compare ( java.lang.String , java.lang.String ) -> int`
* `compare ( java.lang.String , java.lang.String , boolean ) -> int`
* `compare ( long , long ) -> int`
* `compare ( short , short ) -> int`
* `compareIgnoreCase ( java.lang.String , java.lang.String ) -> int`
* `compareIgnoreCase ( java.lang.String , java.lang.String , boolean ) -> int`
* `consume ( java.io.InputStream ) -> long`
* `contains ( java.lang.CharSequence , int ) -> boolean`
* `contains ( java.lang.CharSequence , java.lang.CharSequence ) -> boolean`
* `contains ( java.lang.String , char ) -> boolean`
* `contains ( java.lang.String , java.lang.String ) -> boolean`
* `containsAllWords ( java.lang.CharSequence , java.lang.CharSequence[] ) -> boolean`
* `containsAny ( java.lang.CharSequence , char[] ) -> boolean`
* `containsAny ( java.lang.CharSequence , java.lang.CharSequence ) -> boolean`
* `containsAny ( java.lang.CharSequence , java.lang.CharSequence[] ) -> boolean`
* `containsIgnoreCase ( java.lang.CharSequence , java.lang.CharSequence ) -> boolean`
* `containsNone ( java.lang.CharSequence , char[] ) -> boolean`
* `containsNone ( java.lang.CharSequence , java.lang.String ) -> boolean`
* `containsOnly ( java.lang.CharSequence , char[] ) -> boolean`
* `containsOnly ( java.lang.CharSequence , java.lang.String ) -> boolean`
* `containsWhitespace ( java.lang.CharSequence ) -> boolean`
* `contentEquals ( java.io.InputStream , java.io.InputStream ) -> boolean`
* `contentEquals ( java.io.Reader , java.io.Reader ) -> boolean`
* `contentEqualsIgnoreEOL ( java.io.Reader , java.io.Reader ) -> boolean`
* `countMatches ( java.lang.CharSequence , char ) -> int`
* `countMatches ( java.lang.CharSequence , java.lang.CharSequence ) -> int`
* `countMatches ( java.lang.String , java.lang.String ) -> int`
* `countPrefix ( java.lang.String , char ) -> int`
* `countSuffix ( java.lang.String , char ) -> int`
* `createBigDecimal ( java.lang.String ) -> BigDecimal`
* `createBigInteger ( java.lang.String ) -> BigInteger`
* `createDouble ( java.lang.String ) -> Double`
* `createFloat ( java.lang.String ) -> Float`
* `createInteger ( java.lang.String ) -> Integer`
* `createLong ( java.lang.String ) -> Long`
* `createNumber ( java.lang.String ) -> Number`
* `dateID (  ) -> String`
* `dateIDX (  ) -> String`
* `dateToLong ( java.lang.String , java.lang.String ) -> Long`
* `dateToTime ( java.lang.String , java.lang.String ) -> Date`
* `debug ( java.lang.String ) -> void`
* `decimalToAscii ( java.lang.String ) -> String`
* `decimalToHex ( java.lang.String ) -> String`
* `decimalToHex ( java.lang.String , java.lang.String ) -> String`
* `decimalToHex ( java.lang.String , java.lang.String , java.lang.String ) -> String`
* `defaultIfBlank ( T , T ) -> CharSequence`
* `defaultIfEmpty ( T , T ) -> CharSequence`
* `defaultString ( java.lang.Object ) -> String`
* `defaultString ( java.lang.Object , java.lang.String ) -> String`
* `defaultString ( java.lang.String ) -> String`
* `defaultString ( java.lang.String , java.lang.String ) -> String`
* `deleteWhitespace ( java.lang.String ) -> String`
* `difference ( java.lang.String , java.lang.String ) -> String`
* `differenceAt ( java.lang.String , java.lang.String ) -> int`
* `equals ( java.lang.CharSequence , java.lang.CharSequence ) -> boolean`
* `equals ( java.lang.String , java.lang.String ) -> boolean`
* `equalsAny ( java.lang.CharSequence , java.lang.CharSequence[] ) -> boolean`
* `equalsAnyIgnoreCase ( java.lang.CharSequence , java.lang.CharSequence[] ) -> boolean`
* `equalsIgnoreCase ( java.lang.CharSequence , java.lang.CharSequence ) -> boolean`
* `equalsIgnoreCase ( java.lang.String , java.lang.String ) -> boolean`
* `error ( java.lang.String ) -> void`
* `escape ( java.lang.String ) -> String`
* `escape ( java.lang.String , char[] , char ) -> String`
* `escape ( java.lang.String , char[] , java.lang.String ) -> String`
* `escapeCsv ( java.lang.String ) -> String`
* `escapeEcmaScript ( java.lang.String ) -> String`
* `escapeHtml3 ( java.lang.String ) -> String`
* `escapeHtml4 ( java.lang.String ) -> String`
* `escapeJava ( java.lang.String ) -> String`
* `escapeJson ( java.lang.String ) -> String`
* `escapeXml ( java.lang.String ) -> String`
* `escapeXml10 ( java.lang.String ) -> String`
* `escapeXml11 ( java.lang.String ) -> String`
* `extract ( java.lang.String , java.lang.String ) -> String`
* `extractN ( java.lang.String , java.lang.String ) -> String[]`
* `firstNonBlank ( T[] ) -> CharSequence`
* `firstNonEmpty ( T[] ) -> CharSequence`
* `fnmatch ( java.lang.String , java.lang.String ) -> boolean`
* `format ( java.lang.String , java.lang.Object[] ) -> String`
* `formatMsg ( java.lang.String , java.lang.Object[] ) -> String`
* `from2CC ( int ) -> byte[]`
* `from2CC ( java.lang.String ) -> byte[]`
* `from4CC ( int ) -> byte[]`
* `from4CC ( java.lang.String ) -> byte[]`
* `getBytes ( java.lang.String , java.lang.String ) -> byte[]`
* `getBytes ( java.lang.String , java.nio.charset.Charset ) -> byte[]`
* `getChomp ( java.lang.String , java.lang.String ) -> String`
* `getCommonPrefix ( java.lang.String[] ) -> String`
* `getDate (  ) -> Date`
* `getDateLong (  ) -> Long`
* `getDigits ( java.lang.String ) -> String`
* `getFuzzyDistance ( java.lang.CharSequence , java.lang.CharSequence , java.util.Locale ) -> int`
* `getIfBlank ( T , java.util.function.Supplier<T> ) -> CharSequence`
* `getIfEmpty ( T , java.util.function.Supplier<T> ) -> CharSequence`
* `getJaroWinklerDistance ( java.lang.CharSequence , java.lang.CharSequence ) -> double`
* `getLevenshteinDistance ( java.lang.CharSequence , java.lang.CharSequence ) -> int`
* `getLevenshteinDistance ( java.lang.CharSequence , java.lang.CharSequence , int ) -> int`
* `getNestedString ( java.lang.String , java.lang.String ) -> String`
* `getNestedString ( java.lang.String , java.lang.String , java.lang.String ) -> String`
* `getPrechomp ( java.lang.String , java.lang.String ) -> String`
* `getUserConfigDirectory (  ) -> String`
* `getUserConfigDirectory ( java.lang.String ) -> String`
* `getUserDataDirectory (  ) -> String`
* `getUserDataDirectory ( java.lang.String ) -> String`
* `hexToIp ( java.lang.String ) -> String`
* `indexOfAnyBut ( java.lang.CharSequence , char[] ) -> int`
* `indexOfAnyBut ( java.lang.CharSequence , java.lang.CharSequence ) -> int`
* `indexOfDifference ( java.lang.CharSequence , java.lang.CharSequence ) -> int`
* `indexOfDifference ( java.lang.CharSequence[] ) -> int`
* `indexOfIgnoreCase ( java.lang.CharSequence , java.lang.CharSequence ) -> int`
* `indexOfIgnoreCase ( java.lang.CharSequence , java.lang.CharSequence , int ) -> int`
* `initials ( java.lang.String ) -> String`
* `initials ( java.lang.String , char[] ) -> String`
* `interpolate ( java.lang.String , java.util.Map<?, ?> ) -> String`
* `ipToHex ( java.lang.String ) -> String`
* `ipToHex ( long ) -> String`
* `ipToLong ( java.lang.String ) -> long`
* `isAllBlank ( java.lang.CharSequence[] ) -> boolean`
* `isAllEmpty ( java.lang.CharSequence[] ) -> boolean`
* `isAllLowerCase ( java.lang.CharSequence ) -> boolean`
* `isAllUpperCase ( java.lang.CharSequence ) -> boolean`
* `isAlpha ( java.lang.CharSequence ) -> boolean`
* `isAlpha ( java.lang.String ) -> boolean`
* `isAlphanumeric ( java.lang.CharSequence ) -> boolean`
* `isAlphanumeric ( java.lang.String ) -> boolean`
* `isAlphanumericSpace ( java.lang.CharSequence ) -> boolean`
* `isAlphanumericSpace ( java.lang.String ) -> boolean`
* `isAlphaSpace ( java.lang.CharSequence ) -> boolean`
* `isAlphaSpace ( java.lang.String ) -> boolean`
* `isAnyBlank ( java.lang.CharSequence[] ) -> boolean`
* `isAnyEmpty ( java.lang.CharSequence[] ) -> boolean`
* `isAsciiPrintable ( java.lang.CharSequence ) -> boolean`
* `isBlank ( java.lang.CharSequence ) -> boolean`
* `isBlank ( java.lang.String ) -> boolean`
* `isDelimiter ( char , char[] ) -> boolean`
* `isDelimiter ( int , char[] ) -> boolean`
* `isDigits ( java.lang.String ) -> boolean`
* `isEmpty ( java.lang.CharSequence ) -> boolean`
* `isEmpty ( java.lang.String ) -> boolean`
* `isMixedCase ( java.lang.CharSequence ) -> boolean`
* `isNoneBlank ( java.lang.CharSequence[] ) -> boolean`
* `isNoneEmpty ( java.lang.CharSequence[] ) -> boolean`
* `isNotBlank ( java.lang.CharSequence ) -> boolean`
* `isNotBlank ( java.lang.String ) -> boolean`
* `isNotEmpty ( java.lang.CharSequence ) -> boolean`
* `isNotEmpty ( java.lang.String ) -> boolean`
* `isNumber ( java.lang.String ) -> boolean`
* `isNumberCreatable ( java.lang.String ) -> boolean`
* `isNumeric ( java.lang.CharSequence ) -> boolean`
* `isNumeric ( java.lang.String ) -> boolean`
* `isNumericSpace ( java.lang.CharSequence ) -> boolean`
* `isNumericSpace ( java.lang.String ) -> boolean`
* `isParsable ( java.lang.String ) -> boolean`
* `isWhitespace ( java.lang.CharSequence ) -> boolean`
* `isWhitespace ( java.lang.String ) -> boolean`
* `join ( byte[] , char ) -> String`
* `join ( byte[] , char , int , int ) -> String`
* `join ( char[] , char ) -> String`
* `join ( char[] , char , int , int ) -> String`
* `join ( double[] , char ) -> String`
* `join ( double[] , char , int , int ) -> String`
* `join ( float[] , char ) -> String`
* `join ( float[] , char , int , int ) -> String`
* `join ( int[] , char ) -> String`
* `join ( int[] , char , int , int ) -> String`
* `join ( java.lang.Iterable<?> , char ) -> String`
* `join ( java.lang.Iterable<?> , java.lang.String ) -> String`
* `join ( java.lang.Object[] , char ) -> String`
* `join ( java.lang.Object[] , char , int , int ) -> String`
* `join ( java.lang.Object[] , java.lang.String ) -> String`
* `join ( java.lang.Object[] , java.lang.String , int , int ) -> String`
* `join ( java.util.Collection<?> , java.lang.String ) -> String`
* `join ( java.util.Iterator<?> , char ) -> String`
* `join ( java.util.Iterator<?> , java.lang.String ) -> String`
* `join ( java.util.List<?> , char , int , int ) -> String`
* `join ( java.util.List<?> , java.lang.String ) -> String`
* `join ( java.util.List<?> , java.lang.String , int , int ) -> String`
* `join ( java.util.Set<?> , java.lang.String ) -> String`
* `join ( long[] , char ) -> String`
* `join ( long[] , char , int , int ) -> String`
* `join ( short[] , char ) -> String`
* `join ( short[] , char , int , int ) -> String`
* `join ( T[] ) -> String`
* `joinWith ( java.lang.String , java.lang.Object[] ) -> String`
* `lastIndexOf ( java.lang.CharSequence , int ) -> int`
* `lastIndexOf ( java.lang.CharSequence , int , int ) -> int`
* `lastIndexOf ( java.lang.CharSequence , java.lang.CharSequence ) -> int`
* `lastIndexOf ( java.lang.CharSequence , java.lang.CharSequence , int ) -> int`
* `lastIndexOfAny ( java.lang.CharSequence , java.lang.CharSequence[] ) -> int`
* `lastIndexOfAny ( java.lang.String , java.lang.String[] ) -> int`
* `lastIndexOfIgnoreCase ( java.lang.CharSequence , java.lang.CharSequence ) -> int`
* `lastIndexOfIgnoreCase ( java.lang.CharSequence , java.lang.CharSequence , int ) -> int`
* `lastOrdinalIndexOf ( java.lang.CharSequence , java.lang.CharSequence , int ) -> int`
* `left ( java.lang.String , int ) -> String`
* `leftPad ( java.lang.String , int ) -> String`
* `leftPad ( java.lang.String , int , char ) -> String`
* `leftPad ( java.lang.String , int , java.lang.String ) -> String`
* `lineIterator ( java.io.InputStream , java.lang.String ) -> LineIterator`
* `lineIterator ( java.io.InputStream , java.nio.charset.Charset ) -> LineIterator`
* `lineIterator ( java.io.Reader ) -> LineIterator`
* `longToIp ( long ) -> String`
* `lowerCase ( java.lang.String ) -> String`
* `lowercaseFirstLetter ( java.lang.String ) -> String`
* `mformat ( java.lang.String , java.lang.Object[] ) -> String`

* `normalizeSpace ( java.lang.String ) -> String`
* `obfDecode ( java.lang.String ) -> String`
* `obfEncode ( java.lang.String ) -> String`
* `ordinalIndexOf ( java.lang.CharSequence , java.lang.CharSequence , int ) -> int`
* `overlay ( java.lang.String , java.lang.String , int , int ) -> String`
* `overlayString ( java.lang.String , java.lang.String , int , int ) -> String`
* `prechomp ( java.lang.String , java.lang.String ) -> String`
* `prependIfMissing ( java.lang.String , java.lang.CharSequence , java.lang.CharSequence[] ) -> String`
* `prependIfMissingIgnoreCase ( java.lang.String , java.lang.CharSequence , java.lang.CharSequence[] ) -> String`
* `quoteAndEscape ( java.lang.String , char ) -> String`
* `quoteAndEscape ( java.lang.String , char , char[] ) -> String`
* `quoteAndEscape ( java.lang.String , char , char[] , char , boolean ) -> String`
* `quoteAndEscape ( java.lang.String , char , char[] , char[] , char , boolean ) -> String`
* `quoteAndEscape ( java.lang.String , char , char[] , char[] , java.lang.String , boolean ) -> String`
* `randomGUID (  ) -> String`
* `randomUUID (  ) -> String`
* `regReplace ( java.lang.String , java.lang.String , java.lang.String ) -> String`
* `regReplace ( java.lang.String , java.lang.String , java.lang.String , int ) -> String`
* `remove ( java.lang.String , char ) -> String`
* `remove ( java.lang.String , java.lang.String ) -> String`
* `removeAll ( java.lang.String , java.lang.String ) -> String`
* `removeAndHump ( java.lang.String , java.lang.String ) -> String`
* `removeDuplicateWhitespace ( java.lang.String ) -> String`
* `removeEnd ( java.lang.String , java.lang.String ) -> String`
* `removeEndIgnoreCase ( java.lang.String , java.lang.String ) -> String`
* `removeFirst ( java.lang.String , java.lang.String ) -> String`
* `removeIgnoreCase ( java.lang.String , java.lang.String ) -> String`
* `removePattern ( java.lang.String , java.lang.String ) -> String`
* `removeStart ( java.lang.String , java.lang.String ) -> String`
* `removeStartIgnoreCase ( java.lang.String , java.lang.String ) -> String`
* `repeat ( char , int ) -> String`
* `repeat ( java.lang.String , int ) -> String`
* `repeat ( java.lang.String , java.lang.String , int ) -> String`
* `resourceToByteArray ( java.lang.String ) -> byte[]`
* `resourceToByteArray ( java.lang.String , java.lang.ClassLoader ) -> byte[]`
* `resourceToString ( java.lang.String , java.nio.charset.Charset ) -> String`
* `resourceToString ( java.lang.String , java.nio.charset.Charset , java.lang.ClassLoader ) -> String`
* `resourceToURL ( java.lang.String ) -> URL`
* `resourceToURL ( java.lang.String , java.lang.ClassLoader ) -> URL`
* `reverse ( java.lang.String ) -> String`
* `reverseDelimited ( java.lang.String , char ) -> String`
* `reverseDelimitedString ( java.lang.String , java.lang.String ) -> String`
* `right ( java.lang.String , int ) -> String`
* `rightPad ( java.lang.String , int ) -> String`
* `rightPad ( java.lang.String , int , char ) -> String`
* `rightPad ( java.lang.String , int , java.lang.String ) -> String`
* `rotate ( java.lang.String , int ) -> String`
* `sformat ( java.lang.String , java.lang.Object[] ) -> String`

* `splitByCharacterType ( java.lang.String ) -> String[]`
* `splitByCharacterTypeCamelCase ( java.lang.String ) -> String[]`
* `splitByWholeSeparator ( java.lang.String , java.lang.String ) -> String[]`
* `splitByWholeSeparator ( java.lang.String , java.lang.String , int ) -> String[]`
* `splitByWholeSeparatorPreserveAllTokens ( java.lang.String , java.lang.String ) -> String[]`
* `splitByWholeSeparatorPreserveAllTokens ( java.lang.String , java.lang.String , int ) -> String[]`
* `splitPreserveAllTokens ( java.lang.String ) -> String[]`
* `splitPreserveAllTokens ( java.lang.String , char ) -> String[]`
* `splitPreserveAllTokens ( java.lang.String , java.lang.String ) -> String[]`
* `splitPreserveAllTokens ( java.lang.String , java.lang.String , int ) -> String[]`
 
* `stringClean ( java.lang.String ) -> String`
* `stringEquals ( java.lang.String , java.lang.String ) -> boolean`
* `stringEqualsIgnoreCase ( java.lang.String , java.lang.String ) -> boolean`
* `strip ( java.lang.String ) -> String`
* `strip ( java.lang.String , java.lang.String ) -> String`
* `stripAccents ( java.lang.String ) -> String`
* `stripAll ( java.lang.String[] ) -> String[]`
* `stripAll ( java.lang.String[] , java.lang.String ) -> String[]`
* `stripEnd ( java.lang.String , java.lang.String ) -> String`
* `stripStart ( java.lang.String , java.lang.String ) -> String`
* `stripToEmpty ( java.lang.String ) -> String`
* `stripToNull ( java.lang.String ) -> String`
* `subSequence ( java.lang.CharSequence , int ) -> CharSequence`
* `substring ( java.lang.String , int ) -> String`
* `substring ( java.lang.String , int , int ) -> String`
* `substringAfter ( java.lang.String , int ) -> String`
* `substringAfter ( java.lang.String , java.lang.String ) -> String`
* `substringAfterLast ( java.lang.String , int ) -> String`
* `substringAfterLast ( java.lang.String , java.lang.String ) -> String`
* `substringBefore ( java.lang.String , java.lang.String ) -> String`
* `substringBeforeLast ( java.lang.String , java.lang.String ) -> String`
* `substringBetween ( java.lang.String , java.lang.String ) -> String`
* `substringBetween ( java.lang.String , java.lang.String , java.lang.String ) -> String`
* `substringsBetween ( java.lang.String , java.lang.String , java.lang.String ) -> String[]`
* `swapCase ( java.lang.String ) -> String`
* `timeToDate ( java.lang.String , java.util.Date ) -> String`
* `timeToDate ( java.lang.String , long ) -> String`
* `to2CC ( byte[] ) -> String`
* `to2CC ( int ) -> String`
* `to2CCInt ( byte[] ) -> int`
* `to4CC ( byte[] ) -> String`
* `to4CC ( int ) -> String`
* `to4CCInt ( byte[] ) -> int`
* `toArray ( java.lang.Object ) -> Object[]`
* `toArray ( java.lang.Object , java.lang.Object ) -> Object[]`
* `toArray ( java.lang.Object , java.lang.Object , java.lang.Object ) -> Object[]`
* `toArray ( java.lang.Object , java.lang.Object , java.lang.Object , java.lang.Object ) -> Object[]`
* `toArray ( java.lang.Object , java.lang.Object , java.lang.Object , java.lang.Object , java.lang.Object ) -> Object[]`
* `toArray ( java.lang.Object , java.lang.Object , java.lang.Object , java.lang.Object , java.lang.Object , java.lang.Object ) -> Object[]`
* `toArray ( java.lang.String ) -> String[]`
* `toArray ( java.lang.String , java.lang.String ) -> String[]`
* `toArray ( java.lang.String , java.lang.String , java.lang.String ) -> String[]`
* `toArray ( java.lang.String , java.lang.String , java.lang.String , java.lang.String ) -> String[]`
* `toArray ( java.lang.String , java.lang.String , java.lang.String , java.lang.String , java.lang.String ) -> String[]`
* `toArray ( java.lang.String , java.lang.String , java.lang.String , java.lang.String , java.lang.String , java.lang.String ) -> String[]`
* `toArray ( T[] ) -> Object[]`
* `toBase ( int , long ) -> String`
* `toBase ( int , long , int ) -> String`
* `toBoolean ( int ) -> boolean`
* `toBoolean ( int , int , int ) -> boolean`
* `toBoolean ( java.lang.Boolean ) -> boolean`
* `toBoolean ( java.lang.Integer , java.lang.Integer , java.lang.Integer ) -> boolean`
* `toBoolean ( java.lang.String ) -> boolean`
* `toBoolean ( java.lang.String , java.lang.String ) -> boolean`
* `toBoolean ( java.lang.String , java.lang.String , java.lang.String ) -> boolean`
* `toBooleanDefaultIfNull ( java.lang.Boolean , boolean ) -> boolean`
* `toBooleanDefaultIfNull ( java.lang.String , boolean ) -> boolean`
* `toBooleanObject ( int ) -> Boolean`
* `toBooleanObject ( int , int , int , int ) -> Boolean`
* `toBooleanObject ( java.lang.Integer ) -> Boolean`
* `toBooleanObject ( java.lang.Integer , java.lang.Integer , java.lang.Integer , java.lang.Integer ) -> Boolean`
* `toBooleanObject ( java.lang.String ) -> Boolean`
* `toBooleanObject ( java.lang.String , java.lang.String , java.lang.String , java.lang.String ) -> Boolean`
* `toCamelCase ( java.lang.String , boolean , char[] ) -> String`
* `toCharArray ( java.io.InputStream ) -> char[]`
* `toCharArray ( java.io.InputStream , java.lang.String ) -> char[]`
* `toCharArray ( java.io.InputStream , java.nio.charset.Charset ) -> char[]`
* `toCharArray ( java.io.Reader ) -> char[]`
* `toCharArray ( java.lang.CharSequence ) -> char[]`
* `toCodePoints ( java.lang.CharSequence ) -> int[]`
* `toDouble ( java.lang.String ) -> double`
* `toDouble ( java.lang.String , double ) -> double`
* `toDouble ( java.math.BigDecimal ) -> double`
* `toDouble ( java.math.BigDecimal , double ) -> double`
* `toEncodedString ( byte[] , java.nio.charset.Charset ) -> String`
* `toFloat ( java.lang.String ) -> float`
* `toFloat ( java.lang.String , float ) -> float`
* `toGuid ( java.lang.String ) -> String`
* `toGUID ( java.lang.String ) -> String`
* `toGuid ( java.lang.String , java.lang.String ) -> String`
* `toGUID ( java.lang.String , java.lang.String ) -> String`
* `toGUID ( java.lang.String , java.lang.String , java.lang.String ) -> String`
* `toGUID ( java.lang.String , java.lang.String , java.lang.String , long ) -> String`
* `toGUID ( java.lang.String , java.lang.String , long ) -> String`
* `toGUID ( java.lang.String , long ) -> String`
* `toGUID ( long , long ) -> String`
* `toGUID ( long , long , long ) -> String`
* `toGUID ( long , long , long , long ) -> String`
* `toGUID ( long , long , long , long , long ) -> String`
* `toHashGUID ( java.lang.String ) -> String`
* `toHashGUID ( java.lang.String , java.lang.String ) -> String`
* `toHex ( byte[] ) -> String`
* `toHex ( java.lang.Long ) -> String`
* `toHex ( java.lang.String ) -> String`
* `toInt ( java.lang.String ) -> int`
* `toInt ( java.lang.String , int ) -> int`
* `toInteger ( boolean ) -> int`
* `toInteger ( boolean , int , int ) -> int`
* `toInteger ( java.lang.Boolean , int , int , int ) -> int`
* `toIntegerObject ( boolean ) -> Integer`
* `toIntegerObject ( boolean , java.lang.Integer , java.lang.Integer ) -> Integer`
* `toIntegerObject ( java.lang.Boolean ) -> Integer`
* `toIntegerObject ( java.lang.Boolean , java.lang.Integer , java.lang.Integer , java.lang.Integer ) -> Integer`
* `toRootLowerCase ( java.lang.String ) -> String`
* `toRootUpperCase ( java.lang.String ) -> String`
* `toScaledBigDecimal ( java.lang.Double ) -> BigDecimal`
* `toScaledBigDecimal ( java.lang.Double , int , java.math.RoundingMode ) -> BigDecimal`
* `toScaledBigDecimal ( java.lang.Float ) -> BigDecimal`
* `toScaledBigDecimal ( java.lang.Float , int , java.math.RoundingMode ) -> BigDecimal`
* `toScaledBigDecimal ( java.lang.String ) -> BigDecimal`
* `toScaledBigDecimal ( java.lang.String , int , java.math.RoundingMode ) -> BigDecimal`
* `toScaledBigDecimal ( java.math.BigDecimal ) -> BigDecimal`
* `toScaledBigDecimal ( java.math.BigDecimal , int , java.math.RoundingMode ) -> BigDecimal`
* `toString ( boolean , java.lang.String , java.lang.String ) -> String`
* `toString ( byte[] ) -> String`
* `toString ( byte[] , int ) -> String`
* `toString ( byte[] , java.lang.String ) -> String`
* `toString ( byte[] , java.lang.String , int ) -> String`
* `toString ( java.io.InputStream ) -> String`
* `toString ( java.io.InputStream , int ) -> String`
* `toString ( java.io.InputStream , java.lang.String ) -> String`
* `toString ( java.io.InputStream , java.lang.String , int ) -> String`
* `toString ( java.io.InputStream , java.nio.charset.Charset ) -> String`
* `toString ( java.io.Reader ) -> String`
* `toString ( java.io.Reader , int ) -> String`
* `toString ( java.lang.Boolean , java.lang.String , java.lang.String , java.lang.String ) -> String`
* `toString ( java.lang.Object ) -> String`
* `toString ( java.net.URI ) -> String`
* `toString ( java.net.URI , java.lang.String ) -> String`
* `toString ( java.net.URI , java.nio.charset.Charset ) -> String`
* `toString ( java.net.URL ) -> String`
* `toString ( java.net.URL , java.lang.String ) -> String`
* `toString ( java.net.URL , java.nio.charset.Charset ) -> String`
* `toStringOnOff ( boolean ) -> String`
* `toStringOnOff ( java.lang.Boolean ) -> String`
* `toStringTrueFalse ( boolean ) -> String`
* `toStringTrueFalse ( java.lang.Boolean ) -> String`
* `toStringYesNo ( boolean ) -> String`
* `toStringYesNo ( java.lang.Boolean ) -> String`
* `toTUID ( java.lang.String ) -> String`
* `toTUID ( java.lang.String , java.lang.String ) -> String`
* `toTUUID (  ) -> String`
* `toTUUID ( long ) -> String`
* `toUUID ( java.lang.String ) -> String`
* `toXUID ( java.lang.String ) -> String`
* `toXUID ( java.lang.String , java.lang.String ) -> String`
* `toXUID ( java.lang.String , java.lang.String , java.lang.String ) -> String`
* `toXUID ( java.lang.String , java.lang.String , java.lang.String , long ) -> String`
* `toXUID ( java.lang.String , java.lang.String , long ) -> String`
* `toXUID ( java.lang.String , long ) -> String`
* `toXUID ( long , long ) -> String`
* `toXUID ( long , long , long ) -> String`
* `toXUID ( long , long , long , long ) -> String`
* `toXUID ( long , long , long , long , long ) -> String`
* `trim ( java.lang.String ) -> String`
* `trimToEmpty ( java.lang.String ) -> String`
* `trimToNull ( java.lang.String ) -> String`
* `truncate ( java.lang.String , int ) -> String`
* `truncate ( java.lang.String , int , int ) -> String`
* `uncapitalise ( java.lang.String ) -> String`
* `uncapitaliseAllWords ( java.lang.String ) -> String`
* `uncapitalize ( java.lang.String ) -> String`
* `uncapitalize ( java.lang.String , char[] ) -> String`
* `unescapeCsv ( java.lang.String ) -> String`
* `unescapeEcmaScript ( java.lang.String ) -> String`
* `unescapeHtml3 ( java.lang.String ) -> String`
* `unescapeHtml4 ( java.lang.String ) -> String`
* `unescapeJava ( java.lang.String ) -> String`
* `unescapeJson ( java.lang.String ) -> String`
* `unescapeXml ( java.lang.String ) -> String`
* `unifyLineSeparators ( java.lang.String ) -> String`
* `unifyLineSeparators ( java.lang.String , java.lang.String ) -> String`
* `unwrap ( java.lang.String , char ) -> String`
* `unwrap ( java.lang.String , java.lang.String ) -> String`
* `upperCase ( java.lang.String ) -> String`
* `wcmatch ( java.lang.String , java.lang.String ) -> boolean`
* `wrap ( java.lang.String , char ) -> String`
* `wrap ( java.lang.String , int ) -> String`
* `wrap ( java.lang.String , int , java.lang.String , boolean ) -> String`
* `wrap ( java.lang.String , int , java.lang.String , boolean , java.lang.String ) -> String`
* `wrap ( java.lang.String , java.lang.String ) -> String`
* `wrapIfMissing ( java.lang.String , char ) -> String`
* `wrapIfMissing ( java.lang.String , java.lang.String ) -> String`
