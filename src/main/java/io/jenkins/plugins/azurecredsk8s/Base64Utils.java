package io.jenkins.plugins.azurecredsk8s;


import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.umd.cs.findbugs.annotations.CheckForNull;

public class Base64Utils {

    private static final Logger LOG = Logger.getLogger(Base64Utils.class.getName());

    /**
     * Convert a String representation of the base64 encoded bytes back to a byte[].
     * @param s the base64 encoded representation of the bytes.
     * @return the byte[] or {@code null} if the string could not be converted.
     */
    @CheckForNull // TODO: use the one from SecretUtils when allowed
    private static byte[] base64Decode(String s) {
        try {
            return Base64.getDecoder().decode(s);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.WARNING, "failed to base64decode Secret, is the format valid?  {0}", ex.getMessage());
        }
        return null;
    }

    /**
     * Convert a String representation of the base64 encoded bytes of a UTF-8 String back to a String.
     * @param s the base64 encoded String representation of the bytes.
     * @return the String or {@code null} if the string could not be converted.
     */
    @CheckForNull // TODO: use the one from SecretUtils when allowed
    public static String base64DecodeToString(String s) {
        byte[] bytes = base64Decode(s);
        if (bytes != null) {
            try {
                CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
                decoder.onMalformedInput(CodingErrorAction.REPORT);
                decoder.onUnmappableCharacter(CodingErrorAction.REPORT);
                CharBuffer decode = decoder.decode(ByteBuffer.wrap(bytes));
                return decode.toString();
            } catch (CharacterCodingException ex) {
                LOG.log(Level.WARNING, "failed to covert Secret, is this a valid UTF-8 string?  {0}", ex.getMessage());
            }
        }
        return null;
    }
	
}
