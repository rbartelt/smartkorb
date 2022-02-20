package de.xxlstrandkorbverleih.smartkorb.feature_korb.data.common;

import androidx.annotation.NonNull;

public class HexToStringConverter {
    public HexToStringConverter() {
    }

    public String convert(@NonNull byte[] bytes) {
        String string = new String();
        for (int i = 0; i < bytes.length; i++) {
            String x = Integer.toHexString(((int) bytes[i] & 0xff));
            if (x.length() == 1) {
                x = '0' + x;
            }
            string += x + ' ';
        }
        return string;
    }
}
